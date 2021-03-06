/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.service;

import org.mule.DefaultMuleMessage;
import org.mule.OptimizedRequestContext;
import org.mule.api.MessagingException;
import org.mule.api.MuleContext;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.api.MuleRuntimeException;
import org.mule.api.component.Component;
import org.mule.api.config.MuleProperties;
import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.api.endpoint.OutboundEndpoint;
import org.mule.api.lifecycle.Disposable;
import org.mule.api.lifecycle.Initialisable;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.lifecycle.LifecycleException;
import org.mule.api.lifecycle.LifecycleManager;
import org.mule.api.lifecycle.Startable;
import org.mule.api.lifecycle.Stoppable;
import org.mule.api.model.Model;
import org.mule.api.model.ModelException;
import org.mule.api.routing.InboundRouterCollection;
import org.mule.api.routing.OutboundRouterCollection;
import org.mule.api.routing.ResponseRouterCollection;
import org.mule.api.service.Service;
import org.mule.api.service.ServiceException;
import org.mule.api.transport.DispatchException;
import org.mule.api.transport.MessageReceiver;
import org.mule.api.transport.ReplyToHandler;
import org.mule.component.simple.PassThroughComponent;
import org.mule.config.i18n.CoreMessages;
import org.mule.config.i18n.MessageFactory;
import org.mule.management.stats.ServiceStatistics;
import org.mule.routing.inbound.DefaultInboundRouterCollection;
import org.mule.routing.inbound.InboundPassThroughRouter;
import org.mule.routing.outbound.DefaultOutboundRouterCollection;
import org.mule.routing.outbound.OutboundPassThroughRouter;
import org.mule.routing.response.DefaultResponseRouterCollection;
import org.mule.transport.AbstractConnector;
import org.mule.transport.NullPayload;
import org.mule.util.ClassUtils;

import java.beans.ExceptionListener;
import java.util.ArrayList;
import java.util.List;

import edu.emory.mathcs.backport.java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A base implementation for all Services in Mule
 */
public abstract class AbstractService implements Service
{
    
    /**
     * logger used by this class
     */
    protected transient Log logger = LogFactory.getLog(getClass());

    protected ServiceStatistics stats;
    /**
     * The model in which this service is registered
     */
    protected Model model;

    protected MuleContext muleContext;

    protected ServiceLifecycleManager lifecycleManager;

    /**
     * The initial states that the service can be started in
     */
    public static final String INITIAL_STATE_STOPPED = "stopped";
    public static final String INITIAL_STATE_STARTED = "started";
    public static final String INITIAL_STATE_PAUSED = "paused";

    /**
     * The exception strategy used by the service.
     */
    protected ExceptionListener exceptionListener;

    /**
     * The service's name
     */
    protected String name;

    protected InboundRouterCollection inboundRouter = new DefaultInboundRouterCollection();

    protected OutboundRouterCollection outboundRouter = new DefaultOutboundRouterCollection();

    protected ResponseRouterCollection responseRouter = new DefaultResponseRouterCollection();

    /**
     * Determines the initial state of this service when the model starts. Can be
     * 'stopped' or 'started' (default)
     */
    protected String initialState = INITIAL_STATE_STARTED;

    /**
     * Indicates whether a service has passed its initial startup state.
     */
    private AtomicBoolean beyondInitialState = new AtomicBoolean(false);

    // Default component to use if one is not configured.
    // TODO MULE-3113 This should not really be needed as to implement bridging we should
    // should just increment a 'bridged' counter and sent the event straight to
    // outbound router collection. Currently it's the Component that routes events
    // onto the outbound router collection so this default implementation is needed.
    // It would be beneficial to differenciate between component invocations and
    // events that are bridged but currently everything is an invocation.
    protected Component component = new PassThroughComponent();
    
    public AbstractService(MuleContext muleContext)
    {
        this.muleContext = muleContext;
        try
        {
            lifecycleManager = new ServiceLifecycleManager(this, muleContext.getLifecycleManager());
        }
        catch (MuleException e)
        {
            throw new MuleRuntimeException(CoreMessages.failedToCreate("Service Lifecycle Manager"), e);
        }
        
    }

    /**
     * Initialise the service. The service will first create a component from the
     * ServiceDescriptor and then initialise a pool based on the attributes in the
     * ServiceDescriptor .
     *
     * @see org.mule.api.registry.ServiceDescriptor
     * @throws org.mule.api.lifecycle.InitialisationException
     *          if the service fails
     *          to initialise
     */
    public final synchronized void initialise() throws InitialisationException
    {
        if (inboundRouter == null)
        {
            // Create Default routes that route to the default inbound and
            // outbound endpoints
            inboundRouter = new DefaultInboundRouterCollection();
            // TODO MULE-2102 This should be configured in the default template.
            inboundRouter.addRouter(new InboundPassThroughRouter());
        }
        if (outboundRouter == null)
        {
            outboundRouter = new DefaultOutboundRouterCollection();
            // TODO MULE-2102 This should be configured in the default template.
            outboundRouter.addRouter(new OutboundPassThroughRouter());
        }
        if (responseRouter == null)
        {
            responseRouter = new DefaultResponseRouterCollection();
        }

        if (exceptionListener == null)
        {
            //By default us the model Exception Listener
            exceptionListener = getModel().getExceptionListener();
//            // TODO MULE-2102 This should be configured in the default template.
//            exceptionListener = new DefaultServiceExceptionStrategy(this);
//            ((MuleContextAware) exceptionListener).setMuleContext(muleContext);
//            ((Initialisable) exceptionListener).initialise();
        }

        try
        {
            lifecycleManager.fireLifecycle(Initialisable.PHASE_NAME);
        }
        catch (LifecycleException e)
        {
            throw new InitialisationException(e, this);
        }
        // Ensure Component has service instance and is initialised. If the component
        // was configured with spring and is therefore in the registry it will get
        // started automatically, if it was set on the service directly then it won't
        // be started automatically. So to be sure we start it here.
        component.setService(this);
        component.initialise();

        // initialise statistics
        stats = createStatistics();

        stats.setEnabled(muleContext.getStatistics().isEnabled());
        muleContext.getStatistics().add(stats);
        stats.setOutboundRouterStat(outboundRouter.getStatistics());
        stats.setInboundRouterStat(inboundRouter.getStatistics());
        stats.setComponentStat(component.getStatistics());
    }

    protected ServiceStatistics createStatistics()
    {
        return new ServiceStatistics(name);
    }

    public void forceStop() throws MuleException
    {
        //Kepping this here since I don't understand why this method exists.  AFAICS this just says the service is stopped
        //without actually stopping it
//        if (!stopped.get())
//        {
//            logger.debug("Stopping Service");
//            stopping.set(true);
//            fireServiceNotification(ServiceNotification.SERVICE_STOPPING);
//            doForceStop();
//            stopped.set(true);
//            stopping.set(false);
//            fireServiceNotification(ServiceNotification.SERVICE_STOPPED);
//        }
        doForceStop();
        stop();
    }

    public void stop() throws MuleException
    {
        //TODO this wasn't being called stopListeners();
        // Unregister Listeners for the service
        unregisterListeners();

        // Stop component.  We do this here in case there are any queues that need to be consumed first.
        component.stop();
        lifecycleManager.fireLifecycle(Stoppable.PHASE_NAME);
    }

    public void start() throws MuleException
    {
        lifecycleManager.checkPhase(Startable.PHASE_NAME);

        if (!beyondInitialState.get() && initialState.equals(AbstractService.INITIAL_STATE_STOPPED))
        {
            logger.info("Service " + name + " has not been started (initial state = 'stopped')");
            beyondInitialState.set(true);
            return;
        }

        // Ensure Component is started. If component was configured with spring and
        // is therefore in the registry it will get started automatically, if it was
        // set on the service directly then it won't be started automatically. So to
        // be sure we start it here.
        component.start();

        // Create the receivers for the service but do not start them yet.
        registerListeners();

        // We connect the receivers _before_ starting the service because there may
        // be
        // some initialization required for the service which needs to have them
        // connected.
        // For example, the org.mule.transport.soap.glue.GlueMessageReceiver adds
        // InitialisationCallbacks within its doConnect() method (see MULE-804).
        connectListeners();


        if (!beyondInitialState.get() && initialState.equals(AbstractService.INITIAL_STATE_PAUSED))
        {
            lifecycleManager.fireLifecycle(Startable.PHASE_NAME);
            lifecycleManager.fireLifecycle(Pausable.PHASE_NAME);
            logger.info("Service " + name + " has been started and paused (initial state = 'paused')");
        }
        else
        {
            lifecycleManager.fireLifecycle(Startable.PHASE_NAME);
            logger.info("Service " + name + " has been started successfully");
        }
        beyondInitialState.set(true);

        // We start the receivers _after_ starting the service because if a message
        // gets routed to the service before it is started,
        // org.mule.model.AbstractComponent.dispatchEvent() will throw a
        // ServiceException with message COMPONENT_X_IS_STOPPED (see MULE-526).
        startListeners();
    }


    /**
     * Pauses event processing for a single Mule Service. Unlike stop(), a paused
     * service will still consume messages from the underlying transport, but those
     * messages will be queued until the service is resumed.
     */
    public final void pause() throws MuleException
    {
        lifecycleManager.fireLifecycle(Pausable.PHASE_NAME);
    }

    /**
     * Resumes a single Mule Service that has been paused. If the service is not
     * paused nothing is executed.
     */
    public final void resume() throws MuleException
    {
        lifecycleManager.fireLifecycle(Resumable.PHASE_NAME);
    }

    /**
     * Determines if the service is in a paused state
     *
     * @return True if the service is in a paused state, false otherwise
     */
    public boolean isPaused()
    {
        return lifecycleManager.getCurrentPhase().equals(Pausable.PHASE_NAME);
    }

    /**
     * Custom components can execute code necessary to put the service in a paused
     * state here. If a developer overloads this method the doResume() method MUST
     * also be overloaded to avoid inconsistent state in the service
     *
     * @throws MuleException
     */
    protected void doPause() throws MuleException
    {
        // template method
    }

    /**
     * Custom components can execute code necessary to resume a service once it has
     * been paused If a developer overloads this method the doPause() method MUST
     * also be overloaded to avoid inconsistent state in the service
     *
     * @throws MuleException
     */
    protected void doResume() throws MuleException
    {
        // template method
    }

    public final void dispose()
    {
        try
        {
            lifecycleManager.fireLifecycle(Disposable.PHASE_NAME);
        }
        catch (MuleException e)
        {
            logger.error("Failed to stop service: " + name, e);
        }
        component.dispose();
        muleContext.getStatistics().remove(stats);
    }

    public ServiceStatistics getStatistics()
    {
        return stats;
    }

    public void dispatchEvent(MuleEvent event) throws MuleException
    {
        if (!isStarted() && !isPaused())
        {
            throw new ServiceException( CoreMessages.componentIsStopped(name), event.getMessage(), this);
        }

        // Dispatching event to an inbound endpoint
        // in the DefaultMuleSession#dispatchEvent
        ImmutableEndpoint endpoint = event.getEndpoint();

        if (endpoint instanceof OutboundEndpoint)
        {
            try
            {
                ((OutboundEndpoint) endpoint).dispatch(event);
            }
            catch (Exception e)
            {
                throw new DispatchException(event.getMessage(), event.getEndpoint(), e);
            }

            return;
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("Service: " + name + " has received asynchronous event on: "
                    + event.getEndpoint().getEndpointURI());
        }

        // Dispatching event to the service
        if (stats.isEnabled())
        {
            stats.incReceivedEventASync();
        }
        doDispatch(event);
    }

    public MuleMessage sendEvent(MuleEvent event) throws MuleException
    {
        if (!isStarted() && !isPaused())
        {
            throw new ServiceException( CoreMessages.componentIsStopped(name), event.getMessage(), this);
        }

        try
        {
            waitIfPaused(event);
        }
        catch (InterruptedException e)
        {
            throw new ServiceException(event.getMessage(), this, e);
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("Service: " + name + " has received synchronous event on: "
                    + event.getEndpoint().getEndpointURI());
        }
        if (stats.isEnabled())
        {
            stats.incReceivedEventSync();
        }
        event = OptimizedRequestContext.unsafeSetEvent(event);
        return doSend(event);
    }

    /**
     * Called before an event is sent or dispatched to a service, it will block
     * until resume() is called. Users can override this method if they want to
     * handle pausing differently e.g. implement a store and forward policy
     *
     * @param event the current event being passed to the service
     * @throws InterruptedException if the thread is interrupted
     */
    protected void waitIfPaused(MuleEvent event) throws InterruptedException
    {
        if (logger.isDebugEnabled() && lifecycleManager.getCurrentPhase().equals(Pausable.PHASE_NAME))
        {
            logger.debug("Service: " + name + " is paused. Blocking call until resume is called");
        }
        while(lifecycleManager.getCurrentPhase().equals(Pausable.PHASE_NAME))
        {
            Thread.sleep(500);
        }
    }

    /**
     * @return the Mule descriptor name which is associated with the service
     */
    public String getName()
    {
        return name;
    }

    @Override
    public String toString()
    {
        return String.format("%s{%s}", ClassUtils.getSimpleName(this.getClass()), getName());
    }

    public boolean isStopped()
    {
        return lifecycleManager.getState().isStopped();
    }

    public boolean isStopping()
    {
        return lifecycleManager.getState().isStopping();
    }

    protected void handleException(Exception e)
    {
        exceptionListener.exceptionThrown(e);
    }

    protected void doForceStop() throws MuleException
    {
        // template method
    }

    protected void doStop() throws MuleException
    {
        // template method
    }

    protected void doStart() throws MuleException
    {
        // template method
    }

    protected void doDispose()
    {
        // template method
    }

    protected void doInitialise() throws InitialisationException
    {
        // template method
    }

    public boolean isStarted()
    {
        return lifecycleManager.getState().isStarted();
    }

    protected abstract MuleMessage doSend(MuleEvent event) throws MuleException;

    protected abstract void doDispatch(MuleEvent event) throws MuleException;

    protected void registerListeners() throws MuleException
    {
        @SuppressWarnings("unchecked")
        List<InboundEndpoint> endpoints = getIncomingEndpoints();

        for (InboundEndpoint endpoint : endpoints)
        {
            try
            {
                endpoint.getConnector().registerListener(this, endpoint);
            }
            catch (MuleException e)
            {
                throw e;
            }
            catch (Exception e)
            {
                throw new ModelException(
                        CoreMessages.failedtoRegisterOnEndpoint(name, endpoint.getEndpointURI()), e);
            }
        }
    }

    protected void unregisterListeners() throws MuleException
    {
        @SuppressWarnings("unchecked")
        List<InboundEndpoint> endpoints = getIncomingEndpoints();

        for (InboundEndpoint endpoint : endpoints)
        {
            try
            {
                endpoint.getConnector().unregisterListener(this, endpoint);
            }
            catch (MuleException e)
            {
                throw e;
            }
            catch (Exception e)
            {
                throw new ModelException(
                        CoreMessages.failedToUnregister(name, endpoint.getEndpointURI()), e);
            }
        }
    }

    protected void startListeners() throws MuleException
    {
        @SuppressWarnings("unchecked")
        List<InboundEndpoint> endpoints = getIncomingEndpoints();

        for (InboundEndpoint endpoint : endpoints)
        {
            MessageReceiver receiver = ((AbstractConnector) endpoint.getConnector()).getReceiver(this,
                    endpoint);
            if (receiver != null && endpoint.getConnector().isStarted()
                    && endpoint.getInitialState().equals(ImmutableEndpoint.INITIAL_STATE_STARTED))
            {
                receiver.start();
            }
        }
    }

    // This is not called by anything?!
    protected void stopListeners() throws MuleException
    {
        @SuppressWarnings("unchecked")
        List<InboundEndpoint> endpoints = getIncomingEndpoints();

        for (InboundEndpoint endpoint : endpoints)
        {
            MessageReceiver receiver = ((AbstractConnector) endpoint.getConnector()).getReceiver(this, endpoint);
            if (receiver != null)
            {
                receiver.stop();
            }
        }
    }

    protected void connectListeners() throws MuleException
    {
        @SuppressWarnings("unchecked")
        List<InboundEndpoint> endpoints = getIncomingEndpoints();

        for (InboundEndpoint endpoint : endpoints)
        {
            AbstractConnector connector = (AbstractConnector) endpoint.getConnector();
            MessageReceiver receiver = connector.getReceiver(this, endpoint);
            if (receiver != null && connector.isConnected())
            {
                try
                {
                    receiver.connect();
                }
                catch (Exception e)
                {
                    throw new ModelException(
                            MessageFactory.createStaticMessage("Failed to connect listener "
                                    + receiver + " for endpoint " + endpoint.getName()), e);
                }
            }
        }
    }

    protected void disconnectListeners() throws MuleException
    {
        @SuppressWarnings("unchecked")
        List<InboundEndpoint> endpoints = getIncomingEndpoints();

        for (InboundEndpoint endpoint : endpoints)
        {
            MessageReceiver receiver = ((AbstractConnector) endpoint.getConnector()).getReceiver(this,
                    endpoint);
            if (receiver != null)
            {
                try
                {
                    receiver.disconnect();
                }
                catch (Exception e)
                {
                    throw new ModelException(
                            MessageFactory.createStaticMessage("Failed to disconnect listener "
                                    + receiver + " for endpoint " + endpoint.getName()),
                            e);
                }
            }
        }
    }

    /**
     * Returns a list of all incoming endpoints on a service.
     * TODO generify
     */
    protected List<InboundEndpoint> getIncomingEndpoints()
    {
        List<InboundEndpoint> endpoints = new ArrayList<InboundEndpoint>();

        // Add inbound endpoints
        endpoints.addAll(inboundRouter.getEndpoints());

        // Add response endpoints
        if (responseRouter != null
                && responseRouter.getEndpoints() != null)
        {
            endpoints.addAll(responseRouter.getEndpoints());
        }
        return endpoints;
    }

    // /////////////////////////////////////////////////////////////////////////////////////////
    // Getters and Setters
    // /////////////////////////////////////////////////////////////////////////////////////////

    public Model getModel()
    {
        return model;
    }

    public void setModel(Model model)
    {
        this.model = model;
    }

    public ExceptionListener getExceptionListener()
    {
        return exceptionListener;
    }

    public void setExceptionListener(ExceptionListener exceptionListener)
    {
        this.exceptionListener = exceptionListener;
    }

    public InboundRouterCollection getInboundRouter()
    {
        return inboundRouter;
    }

    public void setInboundRouter(InboundRouterCollection inboundRouter)
    {
        this.inboundRouter = inboundRouter;
    }

    public OutboundRouterCollection getOutboundRouter()
    {
        return outboundRouter;
    }

    public void setOutboundRouter(OutboundRouterCollection outboundRouter)
    {
        this.outboundRouter = outboundRouter;
    }

    public ResponseRouterCollection getResponseRouter()
    {
        return responseRouter;
    }

    public void setResponseRouter(ResponseRouterCollection responseRouter)
    {
        this.responseRouter = responseRouter;
    }

    public String getInitialState()
    {
        return initialState;
    }

    public void setInitialState(String initialState)
    {
        this.initialState = initialState;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Component getComponent()
    {
        return component;
    }

    public void setComponent(Component component)
    {
        this.component = component;
        this.component.setService(this);
    }

    protected void processReplyTo(MuleEvent event, MuleMessage result, ReplyToHandler replyToHandler, Object replyTo)
        throws MuleException
    {
        if (result != null && replyToHandler != null)
        {
            String requestor = (String) result.getProperty(MuleProperties.MULE_REPLY_TO_REQUESTOR_PROPERTY);
            if ((requestor != null && !requestor.equals(getName())) || requestor == null)
            {
                replyToHandler.processReplyTo(event, result, replyTo);
            }
        }
    }

    protected ReplyToHandler getReplyToHandler(MuleMessage message, InboundEndpoint endpoint)
    {
        Object replyTo = message.getReplyTo();
        ReplyToHandler replyToHandler = null;
        if (replyTo != null)
        {
            replyToHandler = ((AbstractConnector) endpoint.getConnector()).getReplyToHandler();
            // Use the response transformer for the event if one is set
            if (endpoint.getResponseTransformers() != null)
            {
                replyToHandler.setTransformers(endpoint.getResponseTransformers());
            }
        }
        return replyToHandler;
    }

    // This method is used when the service invoked asynchronously. It should really
    // be used independently of if the service is invoked asynchronously when we are
    // using an out-in or out-optional-in outbound message exchange pattern
    protected void dispatchToOutboundRouter(MuleEvent event, MuleMessage result) throws MessagingException
    {
        if (event.isStopFurtherProcessing())
        {
            logger.debug("MuleEvent stop further processing has been set, no outbound routing will be performed.");
        }
        if (result != null && !event.isStopFurtherProcessing())
        {
            if (getOutboundRouter().hasEndpoints())
            {
                // Here we can use the same message instance because there is no inbound response.
                if (stats.isEnabled())
                {
                    stats.incSentEventASync();
                }
                getOutboundRouter().route(result, event.getSession());
            }
        }
    }

    // This method is used when the service invoked synchronously. It should really
    // be used independantly of if the service is invoked synchronously when we are
    // using an out-only outbound message exchange pattern
    protected MuleMessage sendToOutboundRouter(MuleEvent event, MuleMessage result) throws MessagingException
    {
        if (event.isStopFurtherProcessing())
        {
            logger.debug("MuleEvent stop further processing has been set, no outbound routing will be performed.");
        }
        if (result != null && !event.isStopFurtherProcessing() && !(result.getPayload() instanceof NullPayload))
        {
            if (getOutboundRouter().hasEndpoints())
            {
                // Here we need to use a copy of the message instance because there
                // is an inbound response so that transformers executed as part of
                // the outbound phase do not affect the inbound response. MULE-3307
                if (stats.isEnabled())
                {
                    stats.incSentEventSync();
                }
                MuleMessage outboundReturnMessage = getOutboundRouter().route(new DefaultMuleMessage(result.getPayload(), result, muleContext), event.getSession());
                if (outboundReturnMessage != null)
                {
                    result = outboundReturnMessage;
                }
                else if (getComponent() instanceof PassThroughComponent)
                {
                    // If there was no component, then we really want to return the response from
                    // the outbound router as the actual payload - even if it's null.
                    return new DefaultMuleMessage(NullPayload.getInstance(), result, muleContext);
                }
            }
            else
            {
                logger.debug("Outbound router on service '" + getName() + "' doesn't have any endpoints configured.");
            }
        }
        return result;
    }

    protected MuleMessage processAsyncReplyRouter(MuleMessage result) throws MuleException
    {
        if (result != null && getResponseRouter() != null)
        {
            logger.debug("Waiting for response router message");
            result = getResponseRouter().getResponse(result);
        }
        return result;
    }
    
    protected MuleMessage invokeComponent(MuleEvent event) throws MuleException
    {
        return component.invoke(event);
    }

    public MuleContext getMuleContext()
    {
        return muleContext;
    }

    public LifecycleManager getLifecycleManager()
    {
        return lifecycleManager;
    }
}
