/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.routing.outbound;

import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.api.MuleSession;
import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.api.endpoint.OutboundEndpoint;
import org.mule.api.routing.CouldNotRouteOutboundMessageException;
import org.mule.api.routing.RoutePathNotFoundException;
import org.mule.api.routing.RoutingException;
import org.mule.config.i18n.CoreMessages;

/**
 * <code>MulticastingRouter</code> will broadcast the current message to every endpoint
 * registed with the router.
 */

public class MulticastingRouter extends FilteringOutboundRouter
{

    public MuleMessage route(MuleMessage message, MuleSession session, boolean synchronous)
        throws RoutingException
    {
        MuleMessage result = null;
        if (endpoints == null || endpoints.size() == 0)
        {
            throw new RoutePathNotFoundException(CoreMessages.noEndpointsForRouter(), message, null);
        }
        if (enableCorrelation != ENABLE_CORRELATION_NEVER)
        {
            boolean correlationSet = message.getCorrelationId() != null;
            if (correlationSet && (enableCorrelation == ENABLE_CORRELATION_IF_NOT_SET))
            {
                logger.debug("CorrelationId is already set, not setting Correlation group size");
            }
            else
            {
                // the correlationId will be set by the AbstractOutboundRouter
                message.setCorrelationGroupSize(endpoints.size());
            }
        }

        try
        {
            OutboundEndpoint endpoint;
            synchronized (endpoints)
            {
                for (int i = 0; i < endpoints.size(); i++)
                {
                    endpoint = (OutboundEndpoint) endpoints.get(i);
                    if (synchronous)
                    {
                        // Were we have multiple outbound endpoints
                        if (result == null)
                        {
                            result = send(session, message, endpoint);
                        }
                        else
                        {
                            String def = (String) endpoint.getProperties().get("default");
                            if (def != null)
                            {
                                result = send(session, message, endpoint);
                            }
                            else
                            {
                                send(session, message, endpoint);
                            }
                        }
                    }
                    else
                    {
                        dispatch(session, message, endpoint);
                    }
                }
            }
        }
        catch (MuleException e)
        {
            throw new CouldNotRouteOutboundMessageException(message, (ImmutableEndpoint) endpoints.get(0), e);
        }
        return result;
    }
}
