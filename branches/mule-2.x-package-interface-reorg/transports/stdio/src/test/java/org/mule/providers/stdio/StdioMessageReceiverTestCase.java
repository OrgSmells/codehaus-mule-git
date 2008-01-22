/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.stdio;

import org.mule.api.component.Component;
import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.api.lifecycle.CreateException;
import org.mule.api.transport.MessageReceiver;
import org.mule.tck.providers.AbstractMessageReceiverTestCase;
import org.mule.tck.testmodels.fruit.Orange;

public class StdioMessageReceiverTestCase extends AbstractMessageReceiverTestCase
{

    public void testReceiver() throws Exception
    {
        // FIX A bit hard testing receive from a unit test as we need to reg
        // listener etc
        // file endpoint functions tests for this
    }

    public void testOtherProperties() throws Exception
    {
        StdioMessageReceiver receiver = (StdioMessageReceiver) getMessageReceiver();

        Component component = getTestComponent("orange", Orange.class);
        assertNotNull(component);

        endpoint.getConnector().start();

        receiver.setFrequency(1001);
        receiver.setInputStream(System.in);

        assertTrue(receiver.getFrequency() == 1001);
        receiver.setFrequency(0);
        assertTrue(receiver.getFrequency() == StdioMessageReceiver.DEFAULT_POLL_FREQUENCY);
    }

    public MessageReceiver getMessageReceiver() throws CreateException
    {
        return new StdioMessageReceiver(endpoint.getConnector(), component, endpoint, 1000);
    }

    public ImmutableEndpoint getEndpoint() throws Exception
    {
        return muleContext.getRegistry().lookupEndpointFactory().getInboundEndpoint("stdio://System");
    }
}
