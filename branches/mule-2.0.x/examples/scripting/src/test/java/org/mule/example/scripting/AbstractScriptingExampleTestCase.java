/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.example.scripting;

import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.FunctionalTestCase;

import java.util.Properties;

public abstract class AbstractScriptingExampleTestCase extends FunctionalTestCase
{    
    //@Override
    protected String getConfigResources()
    {
        return "change-machine.xml";
    }

    abstract protected String getScriptFile();
    
    //@Override
    protected Properties getStartUpProperties()
    {
        Properties props = new Properties();
        props.put("scriptfile", getScriptFile());
        return props;
    }

    public void testChangeAlgorithm() throws Exception
    {
        MuleClient client = new MuleClient();
        MuleMessage reply = client.send("vm://input", new Double(1.18), null);
        
        assertNotNull(reply);
        assertNotNull(reply.getPayload());
        assertEquals("[4 quarters, 1 dimes, 1 nickels, 3 pennies]", reply.getPayloadAsString()); 
    }

    public void testAccumulator() throws Exception
    {
        MuleClient client = new MuleClient();
        client.send("vm://input", new Double(0.09), null);
        client.send("vm://input", new Double(0.09), null);
        MuleMessage reply = client.send("vm://input", new Double(1.00), null);
        
        assertNotNull(reply);
        assertNotNull(reply.getPayload());
        assertEquals("[4 quarters, 1 dimes, 1 nickels, 3 pennies]", reply.getPayloadAsString()); 
    }
}
