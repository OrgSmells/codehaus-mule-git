/*
 * $Id$
 * ------------------------------------------------------------------------------------------------------
 *
 * Copyright (c) SymphonySoft Limited. All rights reserved.
 * http://www.symphonysoft.com
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 *
 */
package org.mule.impl.model.seda;

import java.util.NoSuchElementException;

import javax.resource.spi.work.Work;
import javax.resource.spi.work.WorkEvent;
import javax.resource.spi.work.WorkException;
import javax.resource.spi.work.WorkListener;
import javax.resource.spi.work.WorkManager;

import org.mule.MuleManager;
import org.mule.MuleRuntimeException;
import org.mule.config.PoolingProfile;
import org.mule.config.ThreadingProfile;
import org.mule.config.i18n.Message;
import org.mule.config.i18n.Messages;
import org.mule.impl.FailedToQueueEventException;
import org.mule.impl.MuleDescriptor;
import org.mule.impl.MuleEvent;
import org.mule.impl.model.AbstractAsynchronousComponent;
import org.mule.impl.model.DefaultMuleProxy;
import org.mule.impl.model.MuleProxy;
import org.mule.umo.ComponentException;
import org.mule.umo.UMOEvent;
import org.mule.umo.UMOException;
import org.mule.umo.UMOMessage;
import org.mule.umo.lifecycle.InitialisationException;
import org.mule.umo.lifecycle.LifecycleException;
import org.mule.umo.manager.UMOWorkManager;
import org.mule.util.ObjectPool;
import org.mule.util.queue.QueueSession;

/**
 * A Seda component runs inside a Seda Model and is responsible for managing a
 * Seda Queue and thread pool for a Mule sevice component. In Seda terms this is
 * equivilent to a stage.
 * 
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */
public class SedaComponent extends AbstractAsynchronousComponent implements
        Work, WorkListener
{
    /**
     * Serial version
     */
    private static final long serialVersionUID = 7711976708670893015L;

    /**
     * A pool of available Mule Proxies. Component pooling has been disabled on
     * the SEDAModel, this pool will be null anf the 'componentProxy' will be
     * used.
     */
    protected ObjectPool proxyPool = null;

    /**
     * Is created only if component pooling is turned off on the SEDAModel. in
     * this scenario all requests are serviced by this component, unless
     * 'componentPerRequest' flag is set on the model
     */
    protected MuleProxy componentProxy = null;

    protected UMOWorkManager workManager;

    protected String descriptorQueueName;

    /**
     * The time out used for taking from the Seda Queue
     */
    protected int queueTimeout = 0;

    /**
     * Whether component objects should be pooled or a single instance should be
     * used
     */
    protected boolean enablePooling = true;

    /**
     * If this is set to true a new component will be created for every request
     */
    protected boolean componentPerRequest = false;

    /**
     * Default constructor
     */
    public SedaComponent(MuleDescriptor descriptor, SedaModel model)
    {
        super(descriptor, model);
        descriptorQueueName = descriptor.getName() + ".component";
        queueTimeout = model.getQueueTimeout();
        enablePooling = model.isEnablePooling();
        componentPerRequest = model.isComponentPerRequest();
    }

    /**
     * Initialise the component. The component will first create a Mule UMO from
     * the UMODescriptor and then initialise a pool based on the attributes in
     * the UMODescriptor.
     * 
     * @throws org.mule.umo.lifecycle.InitialisationException
     *             if the component fails to initialise
     * @see org.mule.umo.UMODescriptor
     */
    public synchronized void doInitialise() throws InitialisationException
    {

        // Create thread pool
        ThreadingProfile tp = descriptor.getThreadingProfile();
        workManager = tp.createWorkManager(descriptor.getName());
        try
        {
            // Setup event Queue (used for VM execution)
            descriptor.getQueueProfile().configureQueue(descriptor.getName());
        } catch (InitialisationException e)
        {
            throw e;
        } catch (Throwable e)
        {
            throw new InitialisationException(new Message(
                    Messages.X_FAILED_TO_INITIALISE, "Component Queue"), e,
                    this);
        }
    }

    protected void initialisePool() throws InitialisationException
    {
        try
        {
            // Initialise the proxy pool
            proxyPool = descriptor.getPoolingProfile().getPoolFactory()
                    .createPool(descriptor);

            if (descriptor.getPoolingProfile().getInitialisationPolicy() == PoolingProfile.POOL_INITIALISE_ALL_COMPONENTS)
            {
                int threads = descriptor.getPoolingProfile().getMaxActive();
                for (int i = 0; i < threads; i++)
                {
                    proxyPool.returnObject(proxyPool.borrowObject());
                }
            }
            else if (descriptor.getPoolingProfile().getInitialisationPolicy() == PoolingProfile.POOL_INITIALISE_ONE_COMPONENT)
            {
                proxyPool.returnObject(proxyPool.borrowObject());
            }
            poolInitialised.set(true);
        } catch (Exception e)
        {
            throw new InitialisationException(new Message(
                    Messages.X_FAILED_TO_INITIALISE, "Proxy Pool"), e, this);
        }
    }

    protected MuleProxy createComponentProxy() throws InitialisationException
    {

        try
        {
            Object component = lookupComponent();
            MuleProxy componentProxy = new DefaultMuleProxy(component,
                    descriptor, null);
            getStatistics().setComponentPoolSize(-1);
            componentProxy.setStatistics(getStatistics());
            componentProxy.start();
            return componentProxy;
        } catch (UMOException e)
        {
            throw new InitialisationException(e, this);
        }
    }

    public void doForceStop() throws UMOException
    {
        doStop();
    }

    public void doStop() throws UMOException
    {
        workManager.stop();
        if (proxyPool != null)
        {
            try
            {
                proxyPool.stop();
                proxyPool.clearPool();
            } catch (Exception e)
            {
                logger.error("Failed to stop compoent pool: " + e.getMessage(),
                        e);
            }
            poolInitialised.set(false);
        }
        else if (componentProxy != null)
        {
            componentProxy.stop();
        }
    }

    public void doStart() throws UMOException
    {

        try
        {
            // Need to initialise the pool only after all listerner have
            // been registered and initialised so we need to delay until now
            if (!poolInitialised.get() && enablePooling)
            {
                initialisePool();
                proxyPool.start();
            }
            else if (!componentPerRequest)
            {
                componentProxy = createComponentProxy();
            }
            workManager.start();
            workManager.scheduleWork(this, WorkManager.INDEFINITE, null, this);
        } catch (Exception e)
        {
            throw new LifecycleException(new Message(
                    Messages.FAILED_TO_START_X, "Component: "
                            + descriptor.getName()), e, this);
        }
    }

    protected void doDispose()
    {

        try
        {
            // threadPool.awaitTerminationAfterShutdown();
            if (workManager != null)
            {
                workManager.dispose();
            }
        } catch (Exception e)
        {
            logger.error("Component Thread Pool did not close properly: " + e);
        }
        try
        {
            if (proxyPool != null)
            {
                proxyPool.clearPool();
            }
            else if (componentProxy != null)
            {
                componentProxy.dispose();
            }
        } catch (Exception e)
        {
            logger.error("Proxy Pool did not close properly: " + e);
        }
    }

    protected void doDispatch(UMOEvent event) throws UMOException
    {
        // Dispatching event to the component
        if (stats.isEnabled())
        {
            stats.incReceivedEventASync();
        }
        if (logger.isDebugEnabled())
        {
            logger.debug("Component: " + descriptor.getName()
                    + " has received asynchronous event on: "
                    + event.getEndpoint().getEndpointURI());
        }

        // Block until we can queue the next event
        try
        {
            enqueue(event);
            if (stats.isEnabled())
            {
                stats.incQueuedEvent();
            }
        } catch (Exception e)
        {
            FailedToQueueEventException e1 = new FailedToQueueEventException(
                    new Message(Messages.INTERRUPTED_QUEUING_EVENT_FOR_X,
                            getName()), event.getMessage(), this, e);
            handleException(e1);
        }

        if (logger.isTraceEnabled())
        {
            logger.trace("Event added to queue for: " + descriptor.getName());
        }
    }

    public UMOMessage doSend(UMOEvent event) throws UMOException
    {

        UMOMessage result = null;
        MuleProxy proxy = null;
        try
        {
            if (proxyPool != null)
            {
                proxy = (MuleProxy) proxyPool.borrowObject();
                getStatistics().setComponentPoolSize(proxyPool.getSize());
            }
            else if (componentPerRequest)
            {
                proxy = createComponentProxy();
            }
            else
            {
                proxy = componentProxy;
            }

            proxy.setStatistics(getStatistics());

            if (logger.isDebugEnabled())
            {
                logger.debug(this + " : got proxy for " + event.getId() + " = "
                        + proxy);
            }
            result = (UMOMessage) proxy.onCall(event);
        } catch (UMOException e)
        {
            throw e;
        } catch (Exception e)
        {
            throw new ComponentException(event.getMessage(), this, e);
        } finally
        {
            try
            {
                if (proxy != null)
                {
                    if (proxyPool != null)
                    {
                        proxyPool.returnObject(proxy);
                    }
                    else if (componentPerRequest)
                    {
                        proxy.dispose();
                    }
                }
            } catch (Exception e)
            {
                throw new ComponentException(event.getMessage(), this, e);
            }

            if (proxyPool != null)
            {
                getStatistics().setComponentPoolSize(proxyPool.getSize());
            }
        }
        return result;
    }

    /**
     * @return the pool of Mule UMOs initialised in this component
     */
    ObjectPool getProxyPool()
    {
        return proxyPool;
    }

    public int getQueueSize()
    {
        QueueSession queueSession = MuleManager.getInstance().getQueueManager()
                .getQueueSession();
        return queueSession.getQueue(descriptor.getName()).size();
    }

    /**
     * While the component isn't stopped this runs a continuous loop checking
     * for new events in the queue
     */
    public void run()
    {
        MuleEvent event = null;
        MuleProxy proxy = null;
        QueueSession queueSession = null;

        while (!stopped.get())
        {
            try
            {
                // Wait if the component is paused
                paused.whenFalse(null);

                // If we're doing a draining stop, read all events from the
                // queue
                // before stopping
                if (stopping.get())
                {
                    if (queueSession == null
                            || queueSession.getQueue(
                                    descriptor.getName() + ".component").size() == 0)
                    {
                        stopping.set(false);
                        break;
                    }
                }
                event = (MuleEvent) dequeue();
                if (event != null)
                {
                    if (stats.isEnabled())
                    {
                        stats.decQueuedEvent();
                    }
                    if (logger.isDebugEnabled())
                    {
                        logger.debug("Component: " + descriptor.getName()
                                + " dequeued event on: "
                                + event.getEndpoint().getEndpointURI());
                    }

                    if (proxyPool != null)
                    {
                        proxy = (MuleProxy) proxyPool.borrowObject();
                        getStatistics().setComponentPoolSize(
                                proxyPool.getSize());
                    }
                    else if (componentPerRequest)
                    {
                        proxy = createComponentProxy();
                    }
                    else
                    {
                        proxy = componentProxy;
                    }

                    proxy.setStatistics(getStatistics());
                    proxy.start();
                    proxy.onEvent(queueSession, event);
                    workManager.scheduleWork(proxy, WorkManager.INDEFINITE,
                            null, this);
                }
            } catch (Exception e)
            {
                if (proxy != null && proxyPool != null)
                {
                    try
                    {
                        proxyPool.returnObject(proxy);
                    } catch (Exception e2)
                    {
                        logger.info("Failed to return proxy to pool", e2);
                    }
                }

                if (e instanceof InterruptedException)
                {
                    stopping.set(false);
                    break;
                }
                else if (e instanceof NoSuchElementException)
                {
                    handleException(new ComponentException(new Message(
                            Messages.PROXY_POOL_TIMED_OUT),
                            (event == null ? null : event.getMessage()), this,
                            e));
                }
                else if (e instanceof UMOException)
                {
                    handleException(e);
                }
                else if (e instanceof WorkException)
                {
                    handleException(new ComponentException(new Message(
                            Messages.EVENT_PROCESSING_FAILED_FOR_X, descriptor
                                    .getName()), (event == null ? null : event
                            .getMessage()), this, e));
                }
                else
                {
                    handleException(new ComponentException(new Message(
                            Messages.FAILED_TO_GET_POOLED_OBJECT),
                            (event == null ? null : event.getMessage()), this,
                            e));
                }
            } finally
            {
                stopping.set(false);
                if (proxy != null && componentPerRequest)
                {
                    proxy.dispose();
                }
            }
        }
    }

    public void release()
    {
        stopping.set(false);
    }

    protected void enqueue(UMOEvent event) throws Exception
    {
        QueueSession session = MuleManager.getInstance().getQueueManager()
                .getQueueSession();
        session.getQueue(descriptorQueueName).put(event);
    }

    protected UMOEvent dequeue() throws Exception
    {
        // Wait until an event is available
        QueueSession queueSession = MuleManager.getInstance().getQueueManager()
                .getQueueSession();
        return (UMOEvent) queueSession.getQueue(descriptorQueueName).poll(
                queueTimeout);
    }

    public void workAccepted(WorkEvent event)
    {
        handleWorkException(event, "workAccepted");
    }

    public void workRejected(WorkEvent event)
    {
        handleWorkException(event, "workRejected");
    }

    public void workStarted(WorkEvent event)
    {
        handleWorkException(event, "workStarted");
    }

    public void workCompleted(WorkEvent event)
    {
        handleWorkException(event, "workCompleted");
    }

    protected void handleWorkException(WorkEvent event, String type)
    {
        Throwable e;
        if (event != null && event.getException() != null)
        {
            e = event.getException();
        }
        else
        {
            return;
        }
        if (event.getException().getCause() != null)
        {
            e = event.getException().getCause();
        }
        logger.error("Work caused exception on '" + type
                + "'. Work being executed was: " + event.getWork().toString());
        if (e instanceof Exception) {
            handleException((Exception) e);
        } else {
            throw new MuleRuntimeException(new Message(Messages.COMPONENT_CAUSED_ERROR_IS_X, getName()), e);
        }
    }
}
