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

import org.mule.DefaultMuleEvent;
import org.mule.DefaultMuleMessage;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.api.MuleSession;
import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.api.routing.InboundRouterCollection;
import org.mule.api.service.Service;
import org.mule.routing.LoggingCatchAllStrategy;
import org.mule.tck.AbstractMuleTestCase;
import org.mule.tck.MuleTestUtils;
import org.mule.tck.testmodels.fruit.Apple;

import com.mockobjects.dynamic.Mock;

import java.util.Comparator;

public class EventResequencerTestCase extends AbstractMuleTestCase
{

    public void testMessageResequencer() throws Exception
    {
        Mock session = MuleTestUtils.getMockSession();
        Service testService = getTestService("test", Apple.class);
        assertNotNull(testService);

        InboundRouterCollection messageRouter = new DefaultInboundRouterCollection();
        SimpleEventResequencer router = new SimpleEventResequencer(3);
        messageRouter.addRouter(router);
        messageRouter.setCatchAllStrategy(new LoggingCatchAllStrategy());

        MuleMessage message1 = new DefaultMuleMessage("test event A");
        MuleMessage message2 = new DefaultMuleMessage("test event B");
        MuleMessage message3 = new DefaultMuleMessage("test event C");

        ImmutableEndpoint endpoint = getTestOutboundEndpoint("Test1Provider");
        MuleEvent event1 = new DefaultMuleEvent(message1, endpoint, (MuleSession)session.proxy(), false);
        MuleEvent event2 = new DefaultMuleEvent(message2, endpoint, (MuleSession)session.proxy(), false);
        MuleEvent event3 = new DefaultMuleEvent(message3, endpoint, (MuleSession)session.proxy(), false);
        assertTrue(router.isMatch(event1));
        assertTrue(router.isMatch(event2));
        assertTrue(router.isMatch(event3));

        assertNull(router.process(event2));
        assertNull(router.process(event3));

        MuleEvent[] results = router.process(event1);
        assertNotNull(results);
        assertEquals(3, results.length);

        assertEquals("test event B", results[0].getMessageAsString());
        assertEquals("test event C", results[1].getMessageAsString());
        assertEquals("test event A", results[2].getMessageAsString());

        // set a resequencing comparator
        router.setComparator(new EventPayloadComparator());

        assertNull(router.process(event2));
        assertNull(router.process(event3));

        results = router.process(event1);
        assertNotNull(results);
        assertEquals(3, results.length);

        assertEquals("test event A", results[0].getMessageAsString());
        assertEquals("test event B", results[1].getMessageAsString());
        assertEquals("test event C", results[2].getMessageAsString());
    }

    public static class SimpleEventResequencer extends AbstractEventResequencer
    {
        private int eventCount = 0;
        private int eventthreshold = 1;

        public SimpleEventResequencer(int eventthreshold)
        {
            this.eventthreshold = eventthreshold;
        }

        protected boolean shouldResequenceEvents(EventGroup events)
        {
            eventCount++;
            if (eventCount == eventthreshold)
            {
                eventCount = 0;
                return true;
            }
            return false;
        }
    }

    public static class EventPayloadComparator implements Comparator
    {
        public int compare(Object o1, Object o2)
        {
            try
            {
                return ((MuleEvent)o1).getMessageAsString().compareTo(((MuleEvent)o2).getMessageAsString());
            }
            catch (MuleException e)
            {
                throw new IllegalArgumentException(e.getMessage());
            }

        }
    }
}
