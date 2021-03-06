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
import org.mule.api.MuleSession;
import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.api.endpoint.OutboundEndpoint;
import org.mule.api.transformer.TransformerException;
import org.mule.routing.filters.PayloadTypeFilter;
import org.mule.tck.AbstractMuleTestCase;
import org.mule.tck.MuleTestUtils;
import org.mule.transformer.AbstractTransformer;
import org.mule.util.CollectionUtils;

import com.mockobjects.dynamic.C;
import com.mockobjects.dynamic.Mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilteringOutboundRouterTestCase extends AbstractMuleTestCase
{
    public void testFilteringOutboundRouter() throws Exception
    {
        Mock session = MuleTestUtils.getMockSession();
        session.matchAndReturn("getService", getTestService());
        
        ImmutableEndpoint endpoint1 = getTestOutboundEndpoint("Test1Provider");
        assertNotNull(endpoint1);

        FilteringOutboundRouter router = new FilteringOutboundRouter();
        PayloadTypeFilter filter = new PayloadTypeFilter(String.class);
        router.setFilter(filter);
        List endpoints = new ArrayList();
        endpoints.add(endpoint1);
        router.setEndpoints(endpoints);

        assertFalse(router.isUseTemplates());
        assertEquals(filter, router.getFilter());

        MuleMessage message = new DefaultMuleMessage("test event");

        assertTrue(router.isMatch(message));

        session.expect("dispatchEvent", C.eq(message, endpoint1));
        router.route(message, (MuleSession)session.proxy(), false);
        session.verify();

        message = new DefaultMuleMessage("test event");

        session.expectAndReturn("sendEvent", C.eq(message, endpoint1), message);
        MuleMessage result = router.route(message, (MuleSession)session.proxy(), true);
        assertNotNull(result);
        assertEquals(message, result);
        session.verify();

        message = new DefaultMuleMessage(new Exception("test event"));

        assertTrue(!router.isMatch(message));

        router.setTransformers(
                CollectionUtils.singletonList(
                        new AbstractTransformer()
                        {
                            public Object doTransform(Object src, String encoding) throws TransformerException
                            {
                                return ((Exception)src).getMessage();
                            }
                        }
                )
        );

        assertTrue(router.isMatch(message));
    }

    public void testFilteringOutboundRouterWithTemplates() throws Exception
    {
        OutboundEndpoint endpoint1 = getTestOutboundEndpoint("Test1Provider", "test://foo?[barValue]");
        assertNotNull(endpoint1);

        FilteringOutboundRouter router = new FilteringOutboundRouter();
        PayloadTypeFilter filter = new PayloadTypeFilter(String.class);
        router.setFilter(filter);
        List endpoints = new ArrayList();
        endpoints.add(endpoint1);
        router.setEndpoints(endpoints);

        assertTrue(router.isUseTemplates());
        assertEquals(filter, router.getFilter());

        Map m = new HashMap();
        m.put("barValue", "bar");
        MuleMessage message = new DefaultMuleMessage("test event", m);

        assertTrue(router.isMatch(message));
        OutboundEndpoint ep = router.getEndpoint(0, message);
        // MULE-2690: assert that templated endpoints are not mutated
        assertNotSame(endpoint1, ep);
        // assert that the returned endpoint has a resolved URI
        assertEquals("test://foo?bar", ep.getEndpointURI().toString());
    }
}
