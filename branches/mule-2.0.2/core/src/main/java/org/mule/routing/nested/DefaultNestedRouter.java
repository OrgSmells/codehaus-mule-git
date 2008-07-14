/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.routing.nested;

import org.mule.api.MessagingException;
import org.mule.api.MuleMessage;
import org.mule.api.MuleRuntimeException;
import org.mule.api.MuleSession;
import org.mule.api.endpoint.OutboundEndpoint;
import org.mule.api.routing.NestedRouter;
import org.mule.api.routing.OutboundRouter;
import org.mule.config.i18n.CoreMessages;
import org.mule.management.stats.RouterStatistics;
import org.mule.routing.AbstractRouter;
import org.mule.routing.outbound.OutboundPassThroughRouter;

import java.lang.reflect.Proxy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DefaultNestedRouter extends AbstractRouter implements NestedRouter
{

    private static final Log logger = LogFactory.getLog(DefaultNestedRouter.class);

    private Class interfaceClass;

    private String methodName;

    // The router used to actually dispatch the message
    protected OutboundRouter outboundRouter;

    public DefaultNestedRouter()
    {
        setRouterStatistics(new RouterStatistics(RouterStatistics.TYPE_NESTED));
    }

    public MuleMessage route(MuleMessage message, MuleSession session, boolean synchronous) throws MessagingException
    {
        return outboundRouter.route(message, session, synchronous);
    }

    public void setInterface(Class interfaceClass)
    {
        this.interfaceClass = interfaceClass;
    }

    public Class getInterface()
    {
        return interfaceClass;
    }

    public String getMethod()
    {
        return methodName;
    }

    public void setMethod(String methodName)
    {
        this.methodName = methodName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.api.routing.NestedRouter#createProxy(java.lang.Object,
     *      UMODescriptor descriptor java.lang.Class)
     */
    public Object createProxy(Object target)
    {
        try
        {
            Object proxy = Proxy.newProxyInstance(getInterface().getClassLoader(), new Class[]{getInterface()},
                new NestedInvocationHandler(this));
            logger.debug("Have proxy?: " + (null != proxy));
            return proxy;

        }
        catch (Exception e)
        {
            throw new MuleRuntimeException(CoreMessages.failedToCreateProxyFor(target), e);
        }
    }

    public void setEndpoint(OutboundEndpoint e)
    {
        outboundRouter = new OutboundPassThroughRouter();
        outboundRouter.addEndpoint(e);
        outboundRouter.setTransactionConfig(e.getTransactionConfig());
    }

    public Class getInterfaceClass()
    {
        return interfaceClass;
    }

    public String toString()
    {
        final StringBuffer sb = new StringBuffer();
        sb.append("DefaultNestedRouter");
        sb.append("{method='").append(methodName).append('\'');
        sb.append(", interface=").append(interfaceClass);
        sb.append('}');
        return sb.toString();
    }

    public OutboundEndpoint getEndpoint()
    {
        if (outboundRouter != null)
        {
            return (OutboundEndpoint) outboundRouter.getEndpoints().get(0);
        }
        else
        {
            return null;
        }
    }
}
