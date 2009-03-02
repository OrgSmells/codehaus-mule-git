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
import org.mule.transport.http.HttpConnector;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpVersion;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.lang.time.StopWatch;

public class HttpContinueFunctionalTestCase extends FunctionalTestCase
{

    public static final String TEST_MESSAGE = "Foo Bar";

    protected String getConfigResources()
    {
        return "http-functional-test.xml";
    }

    /**
     * HttpClient has default 3 seconds wait for Expect-Continue calls.
     */
    public static final int DEFAULT_HTTP_CLIENT_CONTINUE_WAIT = 3000;

    protected StopWatch stopWatch;

    public void testSendWithContinue() throws Exception
    {
        MuleClient client = new MuleClient();
        Map props = new HashMap();
        //Need to use Http1.1 for Expect: Continue
        HttpClientParams params = new HttpClientParams();
        params.setVersion(HttpVersion.HTTP_1_1);
        params.setBooleanParameter(HttpClientParams.USE_EXPECT_CONTINUE, true);
        props.put(HttpConnector.HTTP_PARAMS_PROPERTY, params);
        stopWatch = new StopWatch();
        stopWatch.start();
        MuleMessage result = client.send("clientEndpoint", TEST_MESSAGE, props);
        stopWatch.stop();

        assertNotNull(result);
        assertEquals(TEST_MESSAGE + " Received", result.getPayloadAsString());

        if (stopWatch.getTime() > DEFAULT_HTTP_CLIENT_CONTINUE_WAIT)
        {
            fail("Server did not handle Expect=100-continue header properly,");
        }
    }
}
