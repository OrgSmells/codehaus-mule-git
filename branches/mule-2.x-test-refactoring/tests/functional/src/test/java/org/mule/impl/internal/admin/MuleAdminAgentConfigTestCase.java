/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.impl.internal.admin;

import org.mule.agent.MuleAdminAgent;
import org.mule.tck.FunctionalTestCase;
import org.mule.transformer.wire.TransformerPairWireFormat;

public class MuleAdminAgentConfigTestCase extends FunctionalTestCase
{
    protected String getConfigResources()
    {
        return "mule-admin-agent.xml";
    }

    public void testNonEmptyProperties() throws Exception
    {
        MuleAdminAgent agent = (MuleAdminAgent) muleContext.getRegistry().lookupAgent("MuleAdmin");
        assertNotNull(agent.getServerUri());
        assertEquals("test://12345",agent.getServerUri());
        assertNotNull(agent.getWireFormat());
        assertTrue(agent.getWireFormat() instanceof TransformerPairWireFormat);
    }
    
}
