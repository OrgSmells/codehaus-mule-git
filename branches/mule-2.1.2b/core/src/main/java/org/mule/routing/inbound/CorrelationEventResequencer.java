/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.routing.inbound;

import org.mule.api.MuleEvent;


/**
 * <code>CorrelationEventResequencer</code> is used to resequence events according
 * to their dispatch sequence in the correlation group. When the MessageSplitter
 * router splits an event it assigns a correlation sequence to the individual message
 * parts so that another router such as the <i>CorrelationEventResequencer</i> can
 * receive the parts and reorder or merge them.
 */
public class CorrelationEventResequencer extends AbstractEventResequencer
{

    public CorrelationEventResequencer()
    {
        super();
        this.setComparator(new CorrelationSequenceComparator());
    }

    protected boolean shouldResequenceEvents(EventGroup events)
    {
        MuleEvent event = (MuleEvent) events.iterator().next();

        if (event == null)
        {
            // nothing to resequence
            return false;
        }

        int size = event.getMessage().getCorrelationGroupSize();
        if (size == -1)
        {
            logger.warn("Correlation Group Size not set, but CorrelationResequencer is being used.  This can cause messages to be held indefinitely");
        }

        return size == events.size();
    }

}
