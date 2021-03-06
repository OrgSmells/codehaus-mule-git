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

import java.util.Comparator;

/**
 * <code>CorrelationSequenceComparator</code> is a {@link Comparator} for
 * {@link MuleEvent}s using their respective correlation sequences.
 */
public final class CorrelationSequenceComparator implements Comparator
{

    public CorrelationSequenceComparator()
    {
        super();
    }

    public int compare(Object o1, Object o2)
    {
        int val1 = ((MuleEvent)o1).getMessage().getCorrelationSequence();
        int val2 = ((MuleEvent)o2).getMessage().getCorrelationSequence();

        if (val1 == val2)
        {
            return 0;
        }
        else if (val1 > val2)
        {
            return 1;
        }
        else
        {
            return -1;
        }
    }

}
