/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.soap.axis;

import org.mule.tck.AbstractMuleTestCase;
import org.mule.umo.UMOEvent;
import org.mule.umo.endpoint.UMOImmutableEndpoint;

import javax.xml.namespace.QName;

/**
 * TODO document me
 */
public class SoapActionTemplateTestCase extends AbstractMuleTestCase
{
    public void testHostInfoReplace() throws Exception
    {
        UMOImmutableEndpoint ep = managementContext.getRegistry().lookupEndpointFactory().getOutboundEndpoint(
            "axis:http://mycompany.com:8080/services/myService?method=foo");
        
        AxisMessageDispatcher dispatcher = new AxisMessageDispatcher(ep);
        UMOEvent event = getTestEvent("test,", ep);
        String result = dispatcher.parseSoapAction("[hostInfo]/[method]", new QName("foo"), event);

        assertEquals("http://mycompany.com:8080/foo", result);
    }

    public void testHostReplace() throws Exception
    {
        UMOImmutableEndpoint ep = managementContext.getRegistry().lookupEndpointFactory().getOutboundEndpoint(
            "axis:http://mycompany.com:8080/services/myService?method=foo");
        AxisMessageDispatcher dispatcher = new AxisMessageDispatcher(ep);
        UMOEvent event = getTestEvent("test,", ep);
        event.getComponent().setName("myService");
        String result = dispatcher.parseSoapAction("[scheme]://[host]:[port]/[serviceName]/[method]",
            new QName("foo"), event);

        assertEquals("http://mycompany.com:8080/myService/foo", result);
    }
}
