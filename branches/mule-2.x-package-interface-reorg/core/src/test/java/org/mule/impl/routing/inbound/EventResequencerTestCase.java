/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.impl.routing.inbound;

import org.mule.api.Event;
import org.mule.api.AbstractMuleException;
import org.mule.api.MuleMessage;
import org.mule.api.Session;
import org.mule.api.component.Component;
import org.mule.api.endpoint.Endpoint;
import org.mule.api.routing.InboundRouterCollection;
import org.mule.impl.MuleEvent;
import org.mule.impl.DefaultMuleMessage;
import org.mule.impl.routing.LoggingCatchAllStrategy;
import org.mule.impl.routing.inbound.AbstractEventResequencer;
import org.mule.impl.routing.inbound.EventGroup;
import org.mule.impl.routing.inbound.DefaultInboundRouterCollection;
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
        Component testComponent = getTestComponent("test", Apple.class);
        assertNotNull(testComponent);

        InboundRouterCollection messageRouter = new DefaultInboundRouterCollection();
        SimpleEventResequencer router = new SimpleEventResequencer(3);
        messageRouter.addRouter(router);
        messageRouter.setCatchAllStrategy(new LoggingCatchAllStrategy());

        MuleMessage message1 = new DefaultMuleMessage("test event A");
        MuleMessage message2 = new DefaultMuleMessage("test event B");
        MuleMessage message3 = new DefaultMuleMessage("test event C");

        Endpoint endpoint = getTestEndpoint("Test1Provider", Endpoint.ENDPOINT_TYPE_SENDER);
        Event event1 = new MuleEvent(message1, endpoint, (Session)session.proxy(), false);
        Event event2 = new MuleEvent(message2, endpoint, (Session)session.proxy(), false);
        Event event3 = new MuleEvent(message3, endpoint, (Session)session.proxy(), false);
        assertTrue(router.isMatch(event1));
        assertTrue(router.isMatch(event2));
        assertTrue(router.isMatch(event3));

        assertNull(router.process(event2));
        assertNull(router.process(event3));

        Event[] results = router.process(event1);
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
                return ((Event)o1).getMessageAsString().compareTo(((Event)o2).getMessageAsString());
            }
            catch (AbstractMuleException e)
            {
                throw new IllegalArgumentException(e.getMessage());
            }

        }
    }
}
