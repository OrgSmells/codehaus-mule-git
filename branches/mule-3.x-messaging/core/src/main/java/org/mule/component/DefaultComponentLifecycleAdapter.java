/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.component;

import org.mule.DefaultMuleEventContext;
import org.mule.RequestContext;
import org.mule.api.DefaultMuleException;
import org.mule.api.MuleContext;
import org.mule.api.MuleEvent;
import org.mule.api.MuleEventContext;
import org.mule.api.MuleException;
import org.mule.api.component.JavaComponent;
import org.mule.api.component.LifecycleAdapter;
import org.mule.api.lifecycle.Disposable;
import org.mule.api.lifecycle.Initialisable;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.lifecycle.Startable;
import org.mule.api.lifecycle.Stoppable;
import org.mule.api.model.EntryPointResolverSet;
import org.mule.api.registry.MuleRegistry;
import org.mule.api.routing.InterfaceBinding;
import org.mule.api.service.ServiceException;
import org.mule.config.i18n.CoreMessages;
import org.mule.model.resolvers.LegacyEntryPointResolverSet;
import org.mule.model.resolvers.NoSatisfiableMethodsException;
import org.mule.model.resolvers.TooManySatisfiableMethodsException;
import org.mule.routing.binding.BindingInvocationHandler;
import org.mule.util.ClassUtils;

import java.lang.ref.SoftReference;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <code>DefaultComponentLifecycleAdapter</code> is a default implementation of
 * {@link LifecycleAdapter} for use with {@link JavaComponent} that expects component
 * instances to implement Mule lifecycle interfaces in order to receive lifecycle.
 * Custom implementations should be used in order to adapt Mule lifecycle to other
 * custom lifecycle methods that are used by a component instance.
 */
public class DefaultComponentLifecycleAdapter implements LifecycleAdapter
{
    /** logger used by this class */
    protected static final Log logger = LogFactory.getLog(DefaultComponentLifecycleAdapter.class);

    protected SoftReference<?> componentObject;

    /**
     * name under which the componentObject is registered in the registry
     */
    private String componentObjectRegistryKey;

    protected JavaComponent component;
    protected EntryPointResolverSet entryPointResolver;
    
    private boolean isStoppable = false;
    private boolean isStartable = false;
    private boolean isDisposable = false;

    private boolean started = false;
    private boolean disposed = false;

    protected MuleContext muleContext;

    public DefaultComponentLifecycleAdapter(Object componentObject, JavaComponent component, MuleContext muleContext) throws MuleException
    {
        if (componentObject == null)
        {
            throw new IllegalArgumentException("POJO Service cannot be null");
        }
        if (entryPointResolver == null)
        {
            entryPointResolver = new LegacyEntryPointResolverSet();
        }
        this.componentObject = new SoftReference<Object>(componentObject);
        this.component = component;

        if (muleContext == null)
        {
            throw new IllegalStateException("No muleContext provided");
        }

        // save a ref for later disposal call
        this.muleContext = muleContext;
        // store a hard ref to the component object in the registry, so it's not GC'ed too early
        MuleRegistry r = muleContext.getRegistry();
        componentObjectRegistryKey = createRegistryHardRefName(componentObject);
        if (r.lookupObject(componentObjectRegistryKey) == null)
        {
            r.registerObject(componentObjectRegistryKey, new ComponentObjectHolder(componentObject));
        }
    }

    public DefaultComponentLifecycleAdapter(Object componentObject,
                                   JavaComponent component,
                                   EntryPointResolverSet entryPointResolver, MuleContext muleContext) throws MuleException
    {

        this(componentObject, component, muleContext);
        this.entryPointResolver = entryPointResolver;
        
        isStartable = Startable.class.isInstance(componentObject);
        isStoppable = Stoppable.class.isInstance(componentObject);
        isDisposable = Disposable.class.isInstance(componentObject);

        configureBinding();
    }

    /**
     * Propagates start() life-cycle to component object implementations if they
     * implement the mule {@link Startable} interface. NOT: It is up to component
     * implementations to ensure their implementation of start() is thread-safe.
     */
    public void start() throws MuleException
    {
        if (isStartable)
        {
            try
            {
                ((Startable) componentObject.get()).start();
                started = true;
            }
            catch (Exception e)
            {
                throw new DefaultMuleException(CoreMessages.failedToStart("Service: "
                                                                          + component.getService().getName()), e);
            }
        }
        else
        {
            started = true;
        }
    }

    /**
     * Propagates stop() life-cycle to component object implementations if they
     * implement the mule {@link Stoppable} interface. NOT: It is up to component
     * implementations to ensure their implementation of stop() is thread-safe.
     */
    public void stop() throws MuleException
    {
        if (isStoppable)
        {
            try
            {
                ((Stoppable) componentObject.get()).stop();
                started = false;
            }
            catch (Exception e)
            {
                throw new DefaultMuleException(CoreMessages.failedToStop("Service: "
                                                                         + component.getService().getName()), e);
            }
        }
        else
        {
            started = false;
        }
    }

    /**
     * Propagates dispose() life-cycle to component object implementations if they
     * implement the mule {@link Disposable} interface. NOT: It is up to component
     * implementations to ensure their implementation of dispose() is thread-safe.
     */
    public void dispose()
    {
        try
        {
            if (isDisposable)
            {
                // make sure we haven't lost the reference to the object
                Object o = componentObject.get();
                if (o != null)
                {
                    ((Disposable) o).dispose();
                }
            }

            // unregister a hard ref to the component object
            muleContext.getRegistry().unregisterObject(componentObjectRegistryKey);
            componentObject.clear();
            componentObject.enqueue();

        }
        catch (Exception e)
        {
            logger.error("failed to dispose: " + component.getService().getName(), e);
        }
        disposed = true;
    }

    /** @return true if the service has been started */
    public boolean isStarted()
    {
        return started;
    }

    /** @return whether the service managed by this lifecycle has been disposed */
    public boolean isDisposed()
    {
        return disposed;
    }

    public Object invoke(MuleEvent event) throws MuleException
    {
        // Invoke method
        MuleEventContext eventContext = new DefaultMuleEventContext(event);
        Object result;
        try
        {
            // Use the overriding entrypoint resolver if one is set
            if (component.getEntryPointResolverSet() != null)
            {
                result = component.getEntryPointResolverSet().invoke(componentObject.get(), eventContext);
            }
            else
            {
                result = entryPointResolver.invoke(componentObject.get(), eventContext);
            }
        }
        catch (Exception e)
        {
            throw new ServiceException(RequestContext.getEventContext().getMessage(), component.getService(), e);
        }

        return result;
    }

    /**
     * Propagates initialise() life-cycle to component object implementations if they
     * implement the mule {@link Initialisable} interface.
     * <p/> 
     * <b>NOTE:</b> It is up to component implementations to ensure their implementation of 
     * <code>initialise()</code> is thread-safe.
     */
    public void initialise() throws InitialisationException
    {
        if (Initialisable.class.isInstance(componentObject.get()))
        {
            ((Initialisable) componentObject.get()).initialise();
        }
    }

    protected void configureBinding() throws MuleException
    {
        // Initialise the nested router and bind the endpoints to the methods using a
        // Proxy
        if (component.getBindingCollection() != null)
        {
            Map<Class<?>, Object> bindings = new HashMap<Class<?>, Object>();
            for (Iterator<?> it = component.getBindingCollection().getRouters().iterator(); it.hasNext();)
            {
                InterfaceBinding interfaceBinding = (InterfaceBinding) it.next();
                Object proxy = bindings.get(interfaceBinding.getInterface());

                if (proxy == null)
                {
                    // Create a proxy that implements this interface
                    // and just routes away using a mule client
                    // ( using the high level Mule client is probably
                    // a bit agricultural but this is just POC stuff )
                    proxy = interfaceBinding.createProxy(componentObject.get());
                    bindings.put(interfaceBinding.getInterface(), proxy);

                    // Now lets set the proxy on the Service object
                    Method setterMethod;

                    List methods = ClassUtils.getSatisfiableMethods(componentObject.get().getClass(),
                        new Class[]{interfaceBinding.getInterface()}, true, false, null);
                    if (methods.size() == 1)
                    {
                        setterMethod = (Method) methods.get(0);
                    }
                    else if (methods.size() > 1)
                    {
                        throw new TooManySatisfiableMethodsException(componentObject.get().getClass(),
                            new Class[]{interfaceBinding.getInterface()});
                    }
                    else
                    {
                        throw new NoSatisfiableMethodsException(componentObject.get().getClass(),
                            new Class[]{interfaceBinding.getInterface()});
                    }

                    try
                    {
                        setterMethod.invoke(componentObject.get(), proxy);
                    }
                    catch (Exception e)
                    {
                        throw new InitialisationException(CoreMessages.failedToSetProxyOnService(interfaceBinding,
                            componentObject.get().getClass()), e, this);
                    }
                }
                else
                {
                    BindingInvocationHandler handler = (BindingInvocationHandler) Proxy.getInvocationHandler(proxy);
                    handler.addRouterForInterface(interfaceBinding);
                }
            }
        }
    }

    /**
     * Generate a registry key name for this component. Used to bind component's hard reference to 
     * the Mule's lifecycle and prevent the garbage collector from kicking in too early.
     */
    protected String createRegistryHardRefName(Object object)
    {
        return "_component.hardref." + component.getService().getName() + "." + System.identityHashCode(object);
    }
    
    /**
     * Holder class used only to crate reference to component instance from registry
     * without if receiving muleContext injection or lifecycle for a second time.
     */
    private class ComponentObjectHolder
    {
        Object componentObject;

        ComponentObjectHolder(Object componentObject)
        {
            this.componentObject = componentObject;
        }
    }
}
