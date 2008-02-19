/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.container;

import org.mule.api.context.ContainerException;
import org.mule.api.context.ObjectNotFoundException;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.lifecycle.Lifecycle;
import org.mule.api.lifecycle.LifecycleTransitionResult;
import org.mule.config.i18n.CoreMessages;
import org.mule.util.ObjectUtils;

import java.io.Reader;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.Name;
import javax.naming.NamingException;

/**
 * <code>JndiContainerContext</code> is a container implementaiton that exposes a
 * jndi context. Whatever properties are set on the container in configuration will
 * be passed to the initial context.
 */
public class JndiContainerContext extends AbstractContainerContext
{
    protected volatile Context context;
    private volatile Map environment;

    public JndiContainerContext()
    {
        super("jndi");
    }

    protected JndiContainerContext(String name)
    {
        super(name);
    }

    public Map getEnvironment()
    {
        return environment;
    }

    public void setEnvironment(Map environment)
    {
        this.environment = environment;
    }

    public Context getContext()
    {
        return context;
    }

    public void setContext(InitialContext context)
    {
        this.context = context;
    }

    public Object getComponent(Object key) throws ObjectNotFoundException
    {
        try
        {
            if (key == null)
            {
                throw new ObjectNotFoundException("null");
            }
            if (key instanceof Name)
            {
                return context.lookup((Name) key);
            }
            else if (key instanceof Class)
            {
                return context.lookup(((Class) key).getName());
            }
            else
            {
                return context.lookup(key.toString());
            }
        }
        catch (NamingException e)
        {
            throw new ObjectNotFoundException(ObjectUtils.toString(key, "null"), e);
        }
    }

    public void configure(Reader configuration) throws ContainerException
    {
        throw new UnsupportedOperationException("configure(Reader)");
    }

    public LifecycleTransitionResult initialise() throws InitialisationException
    {
        try
        {
            if (context == null)
            {
                context = JndiContextHelper.initialise(getEnvironment());
            }
        }
        catch (NamingException e)
        {
            throw new InitialisationException(CoreMessages.failedToCreate("Jndi context"), e, this);
        }
        return LifecycleTransitionResult.OK;
    }
}
