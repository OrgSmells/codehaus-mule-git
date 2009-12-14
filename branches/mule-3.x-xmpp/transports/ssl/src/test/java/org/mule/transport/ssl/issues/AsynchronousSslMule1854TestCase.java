/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.ssl.issues;

import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.FunctionalTestCase;
import org.mule.util.SystemUtils;

public class AsynchronousSslMule1854TestCase extends FunctionalTestCase 
{
    protected static String TEST_MESSAGE = "Test Request";

    @Override
    protected boolean isDisabledInThisEnvironment()
    {
        // MULE-4664
        return SystemUtils.isIbmJDK();
    }

    protected String getConfigResources()
    {
        return "ssl-functional-test.xml";
    }

    public void testAsynchronous() throws Exception
    {
        MuleClient client = new MuleClient();
        client.dispatch("asyncEndpoint", TEST_MESSAGE, null);
        // MULE-2754
        Thread.sleep(100);
        MuleMessage response = client.request("asyncEndpoint", 5000);
        assertNotNull("Response is null", response);
        assertEquals(TEST_MESSAGE + " Received Async", response.getPayloadAsString());
    }
}