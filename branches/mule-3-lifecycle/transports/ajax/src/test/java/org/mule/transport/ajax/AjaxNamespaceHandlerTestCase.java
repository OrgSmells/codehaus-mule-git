/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.transport.ajax;

import org.mule.api.endpoint.EndpointBuilder;
import org.mule.tck.FunctionalTestCase;
import org.mule.transport.ajax.container.AjaxServletConnector;
import org.mule.transport.ajax.embedded.AjaxConnector;

public class AjaxNamespaceHandlerTestCase extends FunctionalTestCase
{
    protected String getConfigResources()
    {
        return "ajax-namespace-config.xml";
    }

    public void testElements() throws Exception
    {
        AjaxConnector connector =
                (AjaxConnector) muleContext.getRegistry().lookupConnector("connector1");

        assertNotNull(connector);

        assertTrue(connector.isJsonCommented());
        assertEquals(1000, connector.getInterval());
        assertEquals(1, connector.getLogLevel());
        assertEquals(10000, connector.getMaxInterval());
        assertEquals(3000, connector.getMultiFrameInterval());
        assertEquals(4000, connector.getRefsThreshold());
        assertEquals(50000, connector.getTimeout());

        AjaxServletConnector connector2 = (AjaxServletConnector) muleContext.getRegistry().lookupConnector("connector2");

        assertNotNull(connector2);
        //No properties

        EndpointBuilder b = muleContext.getRegistry().lookupEndpointBuilder("endpoint1");
        assertNotNull(b);
        assertEquals("http://0.0.0.0:58080/service/request", b.buildInboundEndpoint().getEndpointURI().getAddress());

        EndpointBuilder b2 = muleContext.getRegistry().lookupEndpointBuilder("endpoint2");
        assertNotNull(b2);
        assertEquals("service/response", b2.buildInboundEndpoint().getEndpointURI().getAddress());
    }
}
