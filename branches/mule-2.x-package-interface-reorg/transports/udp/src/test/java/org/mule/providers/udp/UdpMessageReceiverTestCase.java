/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.udp;

import org.mule.impl.endpoint.EndpointURIEndpointBuilder;
import org.mule.tck.providers.AbstractMessageReceiverTestCase;
import org.mule.umo.UMOComponent;
import org.mule.umo.endpoint.UMOEndpointBuilder;
import org.mule.umo.endpoint.UMOImmutableEndpoint;
import org.mule.umo.provider.UMOMessageReceiver;

import com.mockobjects.dynamic.Mock;

public class UdpMessageReceiverTestCase extends AbstractMessageReceiverTestCase
{

    public UMOMessageReceiver getMessageReceiver() throws Exception
    {
        endpoint = muleContext.getRegistry().lookupEndpointFactory().getInboundEndpoint(
            "udp://localhost:10100");
        Mock mockComponent = new Mock(UMOComponent.class);
        mockComponent.expectAndReturn("getResponseTransformer", null);
        mockComponent.expectAndReturn("getResponseRouter", null);

        return new UdpMessageReceiver(endpoint.getConnector(), (UMOComponent) mockComponent.proxy(), endpoint);
    }

    public UMOImmutableEndpoint getEndpoint() throws Exception
    {
        UMOEndpointBuilder builder = new EndpointURIEndpointBuilder("udp://localhost:10100", muleContext);
        builder.setConnector(new UdpConnector());
        return muleContext.getRegistry()
            .lookupEndpointFactory()
            .getInboundEndpoint(builder);
    }

}
