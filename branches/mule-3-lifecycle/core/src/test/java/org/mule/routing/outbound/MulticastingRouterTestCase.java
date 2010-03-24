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

import org.mule.DefaultMuleMessage;
import org.mule.api.MuleMessage;
import org.mule.api.MuleMessageCollection;
import org.mule.api.MuleSession;
import org.mule.api.endpoint.OutboundEndpoint;
import org.mule.routing.filters.RegExFilter;
import org.mule.tck.AbstractMuleTestCase;
import org.mule.tck.MuleTestUtils;
import org.mule.util.mock.PayloadConstraint;

import com.mockobjects.dynamic.C;
import com.mockobjects.dynamic.Mock;

import java.util.ArrayList;
import java.util.List;

public class MulticastingRouterTestCase extends AbstractMuleTestCase
{
    public MulticastingRouterTestCase()
    {
        setStartContext(true);
    }

    public void testMulticastingRouterAsync() throws Exception
    {
        Mock session = MuleTestUtils.getMockSession();
        session.matchAndReturn("getService", getTestService());
        RegExFilter filter = new RegExFilter("(.*) Message");

        OutboundEndpoint endpoint1 = getTestOutboundEndpoint("Test1Provider", "test://test1", null, filter, null);
        assertNotNull(endpoint1);

        OutboundEndpoint endpoint2 = getTestOutboundEndpoint("Test2Provider", "test://test2", null, filter, null);
        assertNotNull(endpoint2);

        MulticastingRouter router = createObject(MulticastingRouter.class);

        List<OutboundEndpoint> endpoints = new ArrayList<OutboundEndpoint>();
        endpoints.add(endpoint1);
        endpoints.add(endpoint2);
        router.setEndpoints(endpoints);

        MuleMessage message = new DefaultMuleMessage(TEST_MESSAGE, muleContext);

        assertTrue(router.isMatch(message));

        session.expect("dispatchEvent", C.args(new PayloadConstraint(TEST_MESSAGE), C.eq(endpoint1)));
        session.expect("dispatchEvent", C.args(new PayloadConstraint(TEST_MESSAGE), C.eq(endpoint2)));
        router.route(message, (MuleSession)session.proxy());
        session.verify();

    }

    public void testMulticastingRouterSync() throws Exception
    {
        Mock session = MuleTestUtils.getMockSession();
        session.matchAndReturn("getService", getTestService());

        OutboundEndpoint endpoint1 = getTestOutboundEndpoint("Test1Provider", "test://Test1Provider?synchronous=true");
        assertNotNull(endpoint1);

        OutboundEndpoint endpoint2 = getTestOutboundEndpoint("Test2Provider", "test://Test2Provider?synchronous=true");
        assertNotNull(endpoint2);

        MulticastingRouter router = createObject(MulticastingRouter.class);
        RegExFilter filter = new RegExFilter("(.*) Message");
        router.setFilter(filter);
        List<OutboundEndpoint> endpoints = new ArrayList<OutboundEndpoint>();
        endpoints.add(endpoint1);
        endpoints.add(endpoint2);
        router.setEndpoints(endpoints);

        assertEquals(filter, router.getFilter());

        MuleMessage message = new DefaultMuleMessage(TEST_MESSAGE, muleContext);

        assertTrue(router.isMatch(message));

        session.expectAndReturn("sendEvent",  C.args(new PayloadConstraint(TEST_MESSAGE), C.eq(endpoint1)), message);
        session.expectAndReturn("sendEvent", C.args(new PayloadConstraint(TEST_MESSAGE), C.eq(endpoint2)), message);
        MuleMessage result = router.route(message, (MuleSession)session.proxy());
        assertNotNull(result);
        assertTrue(result instanceof MuleMessageCollection);
        assertEquals(2, ((MuleMessageCollection)result).size());
        session.verify();
    }

    public void testMulticastingRouterMixedSyncAsync() throws Exception
    {
        Mock session = MuleTestUtils.getMockSession();
        session.matchAndReturn("getService", getTestService());

        OutboundEndpoint endpoint1 = getTestOutboundEndpoint("Test1Provider", "test://Test1Provider?synchronous=true");
        assertNotNull(endpoint1);

        OutboundEndpoint endpoint2 = getTestOutboundEndpoint("Test2Provider", "test://Test2Provider?synchronous=false");
        assertNotNull(endpoint2);

        MulticastingRouter router = createObject(MulticastingRouter.class);
        
        List<OutboundEndpoint> endpoints = new ArrayList<OutboundEndpoint>();
        endpoints.add(endpoint1);
        endpoints.add(endpoint2);
        router.setEndpoints(endpoints);


        MuleMessage message = new DefaultMuleMessage(TEST_MESSAGE, muleContext);

        assertTrue(router.isMatch(message));

        session.expectAndReturn("sendEvent",  C.args(new PayloadConstraint(TEST_MESSAGE), C.eq(endpoint1)), message);
        session.expectAndReturn("dispatchEvent", C.args(new PayloadConstraint(TEST_MESSAGE), C.eq(endpoint2)), message);
        MuleMessage result = router.route(message, (MuleSession)session.proxy());
        assertNotNull(result);
        assertEquals(message, result);
        session.verify();
    }
}
