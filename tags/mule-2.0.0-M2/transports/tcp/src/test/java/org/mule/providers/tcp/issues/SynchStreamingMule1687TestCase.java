/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.tcp.issues;

import org.mule.extras.client.MuleClient;
import org.mule.providers.streaming.StreamMessageAdapter;
import org.mule.tck.FunctionalTestCase;
import org.mule.umo.provider.UMOStreamMessageAdapter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;


public class SynchStreamingMule1687TestCase extends FunctionalTestCase
{

    public static final String TEST_MESSAGE = "Test TCP Request";

    public SynchStreamingMule1687TestCase()
    {
        setDisposeManagerPerSuite(true);
    }

    protected String getConfigResources()
    {
        return "tcp-synch-streaming-test.xml";
    }

    public void testSendAndReceive() throws Exception
    {
        MuleClient client = new MuleClient();
        UMOStreamMessageAdapter message = client.sendStream("tcp://localhost:65432", new StreamMessageAdapter(new ByteArrayInputStream(TEST_MESSAGE.getBytes())));
        assertNotNull(message);
        String response = new BufferedReader(new InputStreamReader(message.getInputStream())).readLine();
        assertNotNull(response);
        // StreamingBridgeComponent simply copies input to output
        assertEquals(response, TEST_MESSAGE);
    }

}

