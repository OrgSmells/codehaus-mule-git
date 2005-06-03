/*
 * $Header$
 * $Revision$
 * $Date$
 * ------------------------------------------------------------------------------------------------------
 *
 * Copyright (c) SymphonySoft Limited. All rights reserved.
 * http://www.symphonysoft.com
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.mule.test.routing.outbound;

import java.util.ArrayList;
import java.util.List;

import org.mule.impl.MuleMessage;
import org.mule.routing.LoggingCatchAllStrategy;
import org.mule.routing.filters.PayloadTypeFilter;
import org.mule.routing.outbound.ChainingRouter;
import org.mule.routing.outbound.OutboundMessageRouter;
import org.mule.tck.AbstractMuleTestCase;
import org.mule.umo.UMOMessage;
import org.mule.umo.UMOSession;
import org.mule.umo.endpoint.UMOEndpoint;

import com.mockobjects.dynamic.C;
import com.mockobjects.dynamic.Mock;

/**
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */

public class ChainingRouterTestCase extends AbstractMuleTestCase
{
    public void testChainingOutboundRouter() throws Exception
    {
        Mock session = getMockSession();
        OutboundMessageRouter messageRouter = new OutboundMessageRouter();
        messageRouter.setCatchAllStrategy(new LoggingCatchAllStrategy());

        UMOEndpoint endpoint1 = getTestEndpoint("Test1Provider", UMOEndpoint.ENDPOINT_TYPE_SENDER);

        UMOEndpoint endpoint2 = getTestEndpoint("Test1Provider", UMOEndpoint.ENDPOINT_TYPE_SENDER);
        OutboundMessageRouter outboundRouter = new OutboundMessageRouter();

        ChainingRouter router = new ChainingRouter();
        PayloadTypeFilter filter = new PayloadTypeFilter(String.class);
        router.setFilter(filter);
        List endpoints = new ArrayList();
        endpoints.add(endpoint1);
        endpoints.add(endpoint1);
        router.setEndpoints(endpoints);

        assertEquals(filter, router.getFilter());

        UMOMessage message = new MuleMessage("test event", null);

        assertTrue(router.isMatch(message));

        session.expect("dispatchEvent", C.eq(message, endpoint1));
        router.route(message, (UMOSession) session.proxy(), false);
        session.verify();

        message = new MuleMessage("test event", null);

        session.expectAndReturn("sendEvent", C.eq(message, endpoint1), message);
        session.expectAndReturn("sendEvent", C.eq(message, endpoint2), message);
        UMOMessage result = router.route(message, (UMOSession) session.proxy(), true);
        assertNotNull(result);
        assertEquals(message, result);
        session.verify();
    }
}
