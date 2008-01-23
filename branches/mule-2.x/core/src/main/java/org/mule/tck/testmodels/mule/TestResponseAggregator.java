/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.tck.testmodels.mule;

import org.mule.api.MuleMessage;
import org.mule.api.routing.RoutingException;
import org.mule.routing.inbound.EventGroup;
import org.mule.routing.response.ResponseCorrelationAggregator;

/**
 * <code>TestResponseAggregator</code> is a mock response Agrregator object used
 * for testing configuration
 * 
 */
public class TestResponseAggregator extends ResponseCorrelationAggregator
{
    /**
     * This method is invoked if the shouldAggregate method is called and returns
     * true. Once this method returns an aggregated message the event group is
     * removed from the router
     * 
     * @param events the event group for this request
     * @return an aggregated message
     * @throws org.mule.api.routing.RoutingException if the aggregation fails. in
     *             this scenario the whole event group is removed and passed to the
     *             exception handler for this componenet
     */
    protected MuleMessage aggregateEvents(EventGroup events) throws RoutingException
    {
        return null;
    }
}
