/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.impl.component.builder;

import org.mule.api.EventContext;
import org.mule.api.MuleMessage;
import org.mule.api.component.Component;
import org.mule.api.component.ComponentAware;
import org.mule.api.component.builder.MessageBuilder;
import org.mule.api.config.ConfigurationException;
import org.mule.api.config.MuleProperties;
import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.api.lifecycle.Callable;
import org.mule.api.routing.OutboundRouter;
import org.mule.impl.DefaultMuleMessage;
import org.mule.util.StringMessageUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A component that will invoke all outbound endpoints configured on the component
 * allow the result of each endpoint invocation to be aggregated to a single message.
 */
public abstract class AbstractMessageBuilder implements ComponentAware, Callable, MessageBuilder
{

    /**
     * logger used by this class
     */
    protected transient Log logger = LogFactory.getLog(getClass());

    protected Component component;

    public void setComponent(Component component) throws ConfigurationException
    {
        this.component = component;
    }

    public Object onCall(EventContext eventContext) throws Exception
    {

        MuleMessage requestMessage = new DefaultMuleMessage(eventContext.transformMessage(),
            eventContext.getMessage());

        MuleMessage responseMessage = null;
        Object builtMessage;

        if (component.getOutboundRouter().hasEndpoints())
        {
            List endpoints = new ArrayList();
            for (Iterator iterator = component.getOutboundRouter().getRouters().iterator(); iterator.hasNext();)
            {
                OutboundRouter router = (OutboundRouter) iterator.next();
                endpoints.addAll(router.getEndpoints());
            }

            for (Iterator iterator = endpoints.iterator(); iterator.hasNext();)
            {
                ImmutableEndpoint endpoint = (ImmutableEndpoint) iterator.next();
                Object request = requestMessage.getPayload();

                boolean rsync = eventContext.getMessage().getBooleanProperty(
                    MuleProperties.MULE_REMOTE_SYNC_PROPERTY, endpoint.isRemoteSync());
                if (!rsync)
                {
                    logger.info("Endpoint: " + endpoint
                                + " is not remoteSync enabled. Message builder finishing");
                    if (eventContext.isSynchronous())
                    {
                        responseMessage = eventContext.sendEvent(requestMessage, endpoint);
                    }
                    else
                    {
                        eventContext.dispatchEvent(requestMessage, endpoint);
                        responseMessage = null;
                    }
                    break;
                }
                else
                {
                    responseMessage = eventContext.sendEvent(requestMessage, endpoint);
                    if (logger.isDebugEnabled())
                    {
                        logger.debug("Response Message Received from: " + endpoint.getEndpointURI());
                    }
                    if (logger.isTraceEnabled())
                    {
                        try
                        {
                            logger.trace("Message Payload: \n"
                                         + StringMessageUtils.truncate(
                                             StringMessageUtils.toString(responseMessage.getPayload()), 200,
                                             false));
                        }
                        catch (Exception e)
                        {
                            // ignore
                        }
                    }
                    builtMessage = buildMessage(new DefaultMuleMessage(request, requestMessage), responseMessage);
                    responseMessage = new DefaultMuleMessage(builtMessage, responseMessage);
                    requestMessage = new DefaultMuleMessage(responseMessage.getPayload(), responseMessage);
                }
            }
        }
        else
        {
            logger.info("There are currently no endpoints configured on component: " + component.getName());
        }
        eventContext.setStopFurtherProcessing(true);
        return responseMessage;
    }
}
