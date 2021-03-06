/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.tcp.issues;

import org.mule.transport.tcp.TcpConnector;
import org.mule.transport.tcp.TcpFunctionalTestCase;

/**
 * This is just to check that the Boolean (rather than boolean) doesn't cause any problems
 */
public class ReuseMule2069TestCase extends TcpFunctionalTestCase
{

    protected String getConfigResources()
    {
        return "reuse-mule-2069.xml";
    }

    public void testReuseSetOnConnector()
    {
        assertTrue(((TcpConnector) muleContext.getRegistry().lookupConnector(TcpConnector.TCP)).isReuseAddress().booleanValue());
    }
    
}
