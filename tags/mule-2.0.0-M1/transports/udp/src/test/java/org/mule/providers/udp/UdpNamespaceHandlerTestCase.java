/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.providers.udp;

import org.mule.tck.FunctionalTestCase;

import junit.framework.Assert;

/**
 * TODO
 */
public class UdpNamespaceHandlerTestCase extends FunctionalTestCase
{
    protected String getConfigResources()
    {
        return "udp-namespace-config.xml";
    }

    public void testConfig() throws Exception
    {
        UdpConnector c = (UdpConnector)managementContext.getRegistry().lookupConnector("udpConnector");
        assertNotNull(c);

        Assert.assertEquals(1234, c.getReceiveBufferSize());
        Assert.assertEquals(2345, c.getReceiveTimeout());
        Assert.assertEquals(3456, c.getSendBufferSize());
        Assert.assertEquals(4567, c.getSendTimeout());
        Assert.assertEquals(true, c.isBroadcast());
        Assert.assertEquals(false, c.isKeepSendSocketOpen());

        assertTrue(c.isConnected());
        assertTrue(c.isStarted());
    }

}