/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.tcp;

import org.mule.providers.AbstractConnector;
import org.mule.tck.providers.AbstractMessageReceiverTestCase;
import org.mule.umo.UMOComponent;
import org.mule.umo.endpoint.UMOImmutableEndpoint;
import org.mule.umo.provider.UMOMessageReceiver;

import com.mockobjects.dynamic.Mock;

public class TcpMessageReceiverTestCase extends AbstractMessageReceiverTestCase
{

    public UMOMessageReceiver getMessageReceiver() throws Exception
    {
        Mock mockComponent = new Mock(UMOComponent.class);
        mockComponent.expectAndReturn("getResponseTransformer", null);
        mockComponent.expectAndReturn("getResponseRouter", null);
        return new TcpMessageReceiver((AbstractConnector)endpoint.getConnector(),
            (UMOComponent)mockComponent.proxy(), endpoint);
    }

    public UMOImmutableEndpoint getEndpoint() throws Exception
    {
        return managementContext.getRegistry().lookupEndpointFactory().getInboundEndpoint("tcp://localhost:1234");
    }
}
