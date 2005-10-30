/*
 * $Header$ 
 * $Revision$ 
 * $Date$
 * ------------------------------------------------------------------------------------------------------
 * 
 * Copyright (c) SymphonySoft Limited. All rights reserved. http://www.symphonysoft.com
 *
 * The software in this package is published under the terms of the BSD style
 * license a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 *  
 */

package org.mule.providers.http.functional;

import org.apache.commons.httpclient.HttpConnection;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.methods.PostMethod;
import org.mule.impl.endpoint.MuleEndpointURI;
import org.mule.providers.http.jetty.JettyConnector;
import org.mule.umo.endpoint.MalformedEndpointException;
import org.mule.umo.endpoint.UMOEndpointURI;
import org.mule.umo.provider.UMOConnector;
import org.mule.util.Utility;

import java.net.URI;

/**
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */
public class JettyHttpFunctionalTestCase extends HttpFunctionalTestCase
{
    protected static final String TEST_MESSAGE = "Test Http Request";

    private HttpConnection cnn;

    protected UMOEndpointURI getInDest()
    {
        try {
            return new MuleEndpointURI("jetty:http://localhost:60198");
        } catch (MalformedEndpointException e) {
            fail(e.getMessage());
            return null;
        }
    }

    protected UMOEndpointURI getOutDest()
    {
        try {
            return new MuleEndpointURI("jetty:http://localhost:60199");
        } catch (MalformedEndpointException e) {
            fail(e.getMessage());
            return null;
        }
    }

    protected UMOConnector createConnector() throws Exception
    {
        JettyConnector connector = new JettyConnector();
        connector.setName("testJettyHttp");

        return connector;
    }

    protected void sendTestData(int iterations) throws Exception
    {
        URI uri = getInDest().getUri();
        PostMethod postMethod = new PostMethod(uri.toString());
        postMethod.setRequestBody(TEST_MESSAGE);
        postMethod.setRequestContentLength(TEST_MESSAGE.length());
        cnn = new HttpConnection(uri.getHost(), uri.getPort());
        postMethod.execute(new HttpState(), cnn);
    }
//
    protected void receiveAndTestResults() throws Exception
    {
        Thread.sleep(1000);
        byte[] buf = new byte[1024 * 4];

        int len = cnn.getResponseInputStream().read(buf);
        if (len < 1) {
            fail("Nothing was sent back in the response");
        }
        String msg = new String(buf, 0, len);

        assertNotNull(msg);
        assertTrue(msg.endsWith(TEST_MESSAGE + Utility.CRLF + " Received"));
    }
}
