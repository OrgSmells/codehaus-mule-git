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

import org.mule.module.client.MuleClient;
import org.mule.tck.FunctionalTestCase;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.emory.mathcs.backport.java.util.concurrent.CountDownLatch;
import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;

public class HttpCookieTestCase extends FunctionalTestCase
{
    private static final int LISTEN_PORT = 60212;
    private static final String COOKIE_HEADER = "Cookie:";

    private CountDownLatch simpleServerLatch = new CountDownLatch(1);
    private CountDownLatch latch = new CountDownLatch(1);
    private boolean cookieFound = false;
    private List<String> cookieHeaders  = new ArrayList<String>();

    protected String getConfigResources()
    {
        return "http-cookie-test.xml";
    }

    protected void doSetUp() throws Exception
    {
        super.doSetUp();

        // start a simple HTTP server that parses the request sent from Mule
        new Thread(new SimpleHttpServer(LISTEN_PORT, simpleServerLatch, latch)).start();
    }

    public void testCookies() throws Exception
    {
        // wait for the simple server thread started in doSetUp to come up
        assertTrue(simpleServerLatch.await(RECEIVE_TIMEOUT, TimeUnit.MILLISECONDS));

        Map<String, String> properties = new HashMap<String, String>();
        properties.put("COOKIE_HEADER","MYCOOKIE");
        MuleClient client = new MuleClient();
        client.send("vm://vm-in", "foobar", properties);

        assertTrue(latch.await(RECEIVE_TIMEOUT, TimeUnit.MILLISECONDS));
        assertTrue(cookieFound);

        assertTrue(cookieHeaders.size() == 2);
        assertEquals("Cookie: $Version=0; customCookie=yes", cookieHeaders.get(0));
        assertEquals("Cookie: $Version=0; expressionCookie=MYCOOKIE", cookieHeaders.get(1));
    }

    private class SimpleHttpServer extends MockHttpServer
    {   
        public SimpleHttpServer(int listenPort, CountDownLatch startupLatch, CountDownLatch testCompleteLatch)
        {
            super(listenPort, startupLatch, testCompleteLatch);
        }

        @Override
        protected void readHttpRequest(BufferedReader reader) throws Exception
        {
            String line = reader.readLine();
            while (line != null)
            {
                // Check that we receive a 'Cookie:' header as it would be 
                // send by a regular http client
                if (line.indexOf(COOKIE_HEADER) > -1)
                {
                    cookieFound = true;
                    cookieHeaders.add(line);
                }

                line = reader.readLine();
                // only read the header, i.e. if we encounter an empty line 
                // stop reading (we're only interested in the headers anyway)
                if (line.trim().length() == 0)
                {
                    line = null;
                }
            }
        }
    }
}
