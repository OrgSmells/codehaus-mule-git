/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.routing.response;

import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.routing.RoutingException;
import org.mule.routing.inbound.EventGroup;

/**
 * Handles single event responses from a replyTo address. If multiple responses will
 * be received for a single invocation, the {@link ResponseCorrelationAggregator}
 * should be used.
 */
public class SingleResponseRouter extends AbstractResponseAggregator
{
    /**
     * The <code>SingleResponseRouter</code> will return true if the event group
     * size is 1. If the group size is greater than 1, a warning will be logged.
     * 
     * @param events
     * @return true if the event group size is 1 or greater
     * @see {@link AbstractResponseAggregator#shouldAggregateEvents(EventGroup)}
     */
    protected boolean shouldAggregateEvents(EventGroup events)
    {
        if (events.expectedSize() > 1)
        {
            logger.warn("CorrelationGroup's expected size is not 1."
                            + " The SingleResponseAggregator will only handle single replyTo events;"
                            + " if there will be multiple events for a single request, "
                            + " use the 'ResponseCorrelationAggregator'");
        }

        return (events.size() != 0);
    }

    /**
     * The <code>SingleResponseRouter</code> will always return the first event of
     * an event group.
     * 
     * @param events the event group for this request
     * @return an aggregated message
     * @throws org.mule.api.routing.RoutingException if the aggregation fails. in
     *             this scenario the whole event group is removed and passed to the
     *             exception handler for this componenet
     * @see {@link AbstractResponseAggregator#aggregateEvents(EventGroup)}
     */
    protected MuleMessage aggregateEvents(EventGroup events) throws RoutingException
    {
        return ((MuleEvent) events.iterator().next()).getMessage();
    }

}
