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

import org.mule.tck.FunctionalTestCase;
import org.mule.umo.endpoint.UMOImmutableEndpoint;

public class StdioNamespaceHandlerTestCase extends FunctionalTestCase
{
    protected String getConfigResources()
    {
        return "stdio-namespace-config.xml";
    }

    public void testConfig() throws Exception
    {
        PromptStdioConnector c =
                (PromptStdioConnector) muleContext.getRegistry().lookupConnector("stdioConnector");
        assertNotNull(c);

        assertEquals(1234, c.getMessageDelayTime());
        assertEquals("abc", c.getOutputMessage());
        assertEquals("edc", c.getPromptMessage());
        assertEquals("456", c.getPromptMessageCode());
        assertEquals("dummy-messages", c.getResourceBundle());

        assertTrue(c.isConnected());
        assertTrue(c.isStarted());
    }

    public void testNoBundle() throws Exception
    {
        PromptStdioConnector c =
                (PromptStdioConnector)muleContext.getRegistry().lookupConnector("noBundleConnector");
        assertNotNull(c);

        assertEquals(1234, c.getMessageDelayTime());
        assertEquals("abc", c.getOutputMessage());
        assertEquals("bcd", c.getPromptMessage());

        assertTrue(c.isConnected());
        assertTrue(c.isStarted());
    }

    public void testSystemAttributeMap()
    {
        testEndpointAttribute("in", "system.in");
        testEndpointAttribute("out", "system.out");
        testEndpointAttribute("err", "system.err");
    }

    protected void testEndpointAttribute(String name, String address)
    {
        UMOImmutableEndpoint endpoint = muleContext.getRegistry().lookupEndpoint(name);
        assertNotNull("Null " + name, endpoint);
        assertEquals(address, endpoint.getEndpointURI().getAddress());
    }

}