/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.http.functional;

import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.FunctionalTestCase;

public class HttpPollingFunctionalTestCase extends FunctionalTestCase
{

    public void testPollingHttpConnector() throws Exception
    {
        MuleClient client = new MuleClient();
        MuleMessage result = client.request("vm://toclient", 5000);
        assertNotNull(result.getPayload());
        assertEquals("foo", result.getPayloadAsString());
    }
    
    protected String getConfigResources()
    {
        return "mule-http-polling-config.xml";
    }

}