/* 
 * $Header$
 * $Revision$
 * $Date$
 * ------------------------------------------------------------------------------------------------------
 * 
 * Copyright (c) Cubis Limited. All rights reserved.
 * http://www.cubis.co.uk 
 * 
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file. 
 *
 */

package org.mule.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.InitialisationException;
import org.mule.MuleException;
import org.mule.config.MuleProperties;
import org.mule.model.DynamicEntryPoint;
import org.mule.model.DynamicEntryPointResolver;
import org.mule.umo.Invocation;
import org.mule.umo.UMODescriptor;
import org.mule.umo.UMOEvent;
import org.mule.umo.UMOException;
import org.mule.umo.UMOMessage;
import org.mule.umo.lifecycle.Disposable;
import org.mule.umo.lifecycle.Initialisable;
import org.mule.umo.lifecycle.Startable;
import org.mule.umo.lifecycle.Stoppable;
import org.mule.umo.lifecycle.UMOLifecycleAdapter;
import org.mule.umo.model.UMOEntryPointResolver;
import org.mule.util.ClassHelper;

import java.lang.reflect.Method;

/**
 * <code>DefaultLifecycleAdapter</code> provides lifecycle methods for all
 * Mule managed components.  It's possible to plugin custom lifecycle adapters,
 * this can provide additional lifecycle methods triggered by an external source.
 *
 * @author <a href="mailto:ross.mason@cubis.co.uk">Ross Mason</a>
 * @version $Revision$
 */
public class DefaultLifecycleAdapter implements UMOLifecycleAdapter
{
    /**
     * logger used by this class
     */
    protected static transient Log logger = LogFactory.getLog(DefaultLifecycleAdapter.class);

    private Object component;
    private UMODescriptor descriptor;
    private boolean isStoppable = false;
    private boolean isStartable = false;
    private boolean isDisposable = false;

    private boolean started = false;
    private boolean disposed = false;

    private DynamicEntryPoint entryPoint;

    private Object lock = new Object();

    public DefaultLifecycleAdapter(Object component, UMODescriptor descriptor) throws UMOException
    {
        this(component, descriptor, new DynamicEntryPointResolver());
    }

    public DefaultLifecycleAdapter(Object component, UMODescriptor descriptor, UMOEntryPointResolver epResolver) throws UMOException
    {
        initialise(component, descriptor, epResolver);
    }

    protected void initialise(Object component, UMODescriptor descriptor, UMOEntryPointResolver epDiscovery) throws UMOException
    {
        if (component == null)
            throw new IllegalArgumentException("Component cannot be null");

        if (descriptor == null)
            throw new IllegalArgumentException("Descriptor cannot be null");

        if (epDiscovery == null)
            epDiscovery = new DynamicEntryPointResolver();

        this.component = component;
        this.entryPoint = (DynamicEntryPoint)epDiscovery.resolveEntryPoint(descriptor);
        this.descriptor = descriptor;

        isStartable = Startable.class.isInstance(component);
        isStoppable = Stoppable.class.isInstance(component);
        isDisposable = Disposable.class.isInstance(component);

        if(component instanceof UMODescriptorAware) {
            ((UMODescriptorAware)component).setDescriptor(descriptor);
        }
    }

    /* (non-Javadoc)
     * @see org.mule.umo.lifecycle.Startable#start()
     */
    public void start() throws UMOException
    {
        if (isStartable)
        {
            try
            {
                ((Startable) component).start();
            }
            catch (Exception e)
            {
                throw new MuleException("Failed to start Mule UMO: " + descriptor.getName(), e);
            }
        }
        started = true;
    }

    /* (non-Javadoc)
     * @see org.mule.umo.lifecycle.Stoppable#stop()
     */
    public void stop() throws UMOException
    {
        if (isStoppable)
        {
            try
            {
                ((Stoppable) component).stop();
            }
            catch (Exception e)
            {
                throw new MuleException("Failed to stop Mule UMO: " + descriptor.getName(), e);
            }
        }
        started = false;
    }

    /* (non-Javadoc)
     * @see org.mule.umo.lifecycle.Disposable#dispose()
     */
    public void dispose() throws UMOException
    {
        if (isDisposable)
        {
            try
            {
                ((Disposable) component).dispose();
            }
            catch (Exception e)
            {
                throw new MuleException("Failed to dispose Mule UMO: " + descriptor.getName(), e);
            }
        }
        disposed = true;
    }

    /* (non-Javadoc)
     * @see org.mule.umo.lifecycle.Callable#onCall(org.mule.umo.UMOEvent)
     */
    public Object onCall(UMOEvent event) throws UMOException
    {
        Object result = null;
        try
        {
            //Check for method override
            Object methodOverride = event.removeProperty(MuleProperties.MULE_METHOD_PROPERTY);
            Method method = null;
            if(methodOverride instanceof Method) {
                method = (Method)methodOverride;
            } else if(methodOverride!=null){
                method = ClassHelper.getMethod(methodOverride.toString(), component.getClass());
            }
            //Invoke method
            result = entryPoint.invoke(component, RequestContext.getEventContext(), method);

            if(result==null && entryPoint.isVoid()) {
                   return new MuleMessage(event.getTransformedMessage(), event.getProperties());
            } else if(descriptor.getResponseRouter()!=null) {
                if(result==null) {
                    result = descriptor.getResponseRouter().getResponse(event.getMessage());
                } else {
                    result = descriptor.getResponseRouter().getResponse(new MuleMessage(result, event.getProperties()));
                }
                return result;
            } else {
                return result;
            }
        }
        catch (Exception e)
        {
            throw new MuleException("Failed to invoke event: " + e.getMessage(), e);
        }
    }


    /**
     * @return
     */
    public boolean isStarted()
    {
        return started;
    }

    /**
     * @return
     */
    public boolean isDisposed()
    {
        return disposed;
    }

    public UMODescriptor getDescriptor()
    {
        return descriptor;
    }

    public Throwable handleException(Object message, Throwable t)
    {
        return descriptor.getExceptionStrategy().handleException(message, t);
    }

    /* (non-Javadoc)
     * @see org.mule.umo.UMOInterceptor#intercept(org.mule.umo.UMOEvent)
     */
    public UMOMessage intercept(Invocation invocation) throws UMOException
    {
        UMOEvent currentEvent = RequestContext.getEvent();
        Object result = onCall(currentEvent);
        //If the component returns an event we'll route it directly
        if(result instanceof UMOMessage) {
            return (UMOMessage)result;
        } else if (result instanceof UMOEvent){
            return ((UMOEvent)result).getMessage();
        } else if (result != null) {
            return new MuleMessage(result, currentEvent.getProperties());
        } else {
            return null;
        }
    }

    /* (non-Javadoc)
     * @see org.mule.umo.lifecycle.Initialisable#initialise()
     */
    public void initialise() throws InitialisationException
    {
        if (Initialisable.class.isInstance(component))
        {
            ((Initialisable) component).initialise();
        }
    }
}