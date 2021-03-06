/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.issues;

import org.mule.api.MuleMessage;
import org.mule.api.config.MuleProperties;
import org.mule.extras.client.MuleClient;
import org.mule.tck.FunctionalTestCase;

import java.util.HashMap;
import java.util.Map;

public class PropertyScribblingMule893TestCase extends FunctionalTestCase
{

    protected String getConfigResources()
    {
        return "issues/property-scribbling-mule-893-test.xml";
    }

    public void testSingleMessage() throws Exception
    {
        MuleClient client = new MuleClient();
        Map properties = new HashMap();
        properties.put(MuleProperties.MULE_REPLY_TO_PROPERTY, "receive");
        client.dispatch("dispatch", "Message", properties);
        MuleMessage response = client.request("receive", 3000L);
        assertNotNull("Response is null", response);
        assertEquals("Message Received", response.getPayload());
    }

    public void testManyMessage() throws Exception
    {
        for (int i = 0; i < 1000; i++)
        {
            testSingleMessage();
        }
    }

}
