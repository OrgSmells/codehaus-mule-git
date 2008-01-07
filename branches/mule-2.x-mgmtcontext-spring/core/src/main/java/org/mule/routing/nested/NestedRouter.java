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

import org.mule.management.stats.RouterStatistics;
import org.mule.routing.AbstractRouter;
import org.mule.routing.outbound.OutboundPassThroughRouter;
import org.mule.umo.MessagingException;
import org.mule.umo.UMOMessage;
import org.mule.umo.UMOSession;
import org.mule.umo.endpoint.UMOImmutableEndpoint;
import org.mule.umo.routing.UMONestedRouter;
import org.mule.umo.routing.UMOOutboundRouter;

import java.lang.reflect.Proxy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class NestedRouter extends AbstractRouter implements UMONestedRouter
{

    protected static Log logger = LogFactory.getLog(NestedRouter.class);

    private Class interfaceClass;

    private String methodName;

    // The router used to actually dispatch the message
    protected UMOOutboundRouter outboundRouter;

    public NestedRouter()
    {
        setRouterStatistics(new RouterStatistics(RouterStatistics.TYPE_NESTED));
    }

    public UMOMessage route(UMOMessage message, UMOSession session, boolean synchronous) throws MessagingException
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
     * @see org.mule.umo.routing.UMONestedRouter#createProxy(java.lang.Object,
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
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    public void setEndpoint(UMOImmutableEndpoint e)
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
        sb.append("NestedRouter");
        sb.append("{method='").append(methodName).append('\'');
        sb.append(", interface=").append(interfaceClass);
        sb.append('}');
        return sb.toString();
    }

    public UMOImmutableEndpoint getEndpoint()
    {
        if (outboundRouter != null)
        {
            return (UMOImmutableEndpoint) outboundRouter.getEndpoints().get(0);
        }
        else
        {
            return null;
        }
    }
}
