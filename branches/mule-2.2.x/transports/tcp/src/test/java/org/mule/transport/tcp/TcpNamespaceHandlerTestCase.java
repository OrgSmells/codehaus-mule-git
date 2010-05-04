/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.transport.tcp;

import org.mule.tck.FunctionalTestCase;
import org.mule.transport.tcp.TcpConnector;

/**
 * TODO
 */
public class TcpNamespaceHandlerTestCase extends FunctionalTestCase
{
    protected String getConfigResources()
    {
        return "tcp-namespace-config.xml";
    }

    public void testConfig() throws Exception
    {
        TcpConnector c = (TcpConnector)muleContext.getRegistry().lookupConnector("tcpConnector");
        assertNotNull(c);
        assertEquals(1024, c.getReceiveBufferSize());
        assertEquals(2048, c.getSendBufferSize());
        assertEquals(50, c.getReceiveBacklog());
        assertFalse(c.isReuseAddress().booleanValue());
        // this is what we want - i was worried that the client was used as default if the server
        // wasn't set, but that's not the case
        assertEquals(-1, c.getServerSoTimeout());
        assertEquals(3000, c.getClientSoTimeout());
        assertTrue(c.isKeepAlive());
        assertTrue(c.isConnected());
        assertTrue(c.isStarted());

    }
    
    public void testSeparateTimeouts() throws Exception
    {
        TcpConnector c = (TcpConnector)muleContext.getRegistry().lookupConnector("separateTimeouts");
        assertNotNull(c);
        assertEquals(4000, c.getServerSoTimeout());
        assertEquals(3000, c.getClientSoTimeout());
        assertTrue(c.isConnected());
        assertTrue(c.isStarted());
    }
    
    public void testPollingConnector()
    {
        PollingTcpConnector c = (PollingTcpConnector)muleContext.getRegistry().lookupConnector("pollingConnector");
        assertNotNull(c);
        assertEquals(4000, c.getPollingFrequency());
        assertEquals(3000, c.getClientSoTimeout());
        assertTrue(c.isConnected());
        assertTrue(c.isStarted());
    }
}
