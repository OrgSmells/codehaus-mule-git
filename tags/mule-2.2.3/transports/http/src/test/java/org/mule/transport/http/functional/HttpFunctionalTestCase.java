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

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.FunctionalTestCase;
import org.mule.tck.functional.EventCallback;
import org.mule.tck.functional.FunctionalTestComponent;
import org.mule.transport.http.HttpConnector;
import org.mule.transport.http.HttpConstants;

import java.util.HashMap;
import java.util.Map;

public class HttpFunctionalTestCase extends FunctionalTestCase
{
    protected static String TEST_MESSAGE = "Test Http Request (R�dgr�d), 57 = \u06f7\u06f5 in Arabic";
    protected boolean checkPathProperties = true;
    
    protected String getConfigResources()
    {
        return "http-functional-test.xml";
    }

    public void testSend() throws Exception
    {        
        FunctionalTestComponent testComponent = (FunctionalTestComponent) getComponent("testComponent");
        assertNotNull(testComponent);

        if (checkPathProperties) 
        {
            EventCallback callback = new EventCallback()
            {
                public void eventReceived(final MuleEventContext context, final Object component) throws Exception
                {
                    MuleMessage msg = context.getMessage();
                    assertEquals("/", msg.getProperty(HttpConnector.HTTP_REQUEST_PROPERTY));
                    assertEquals("/", msg.getProperty(HttpConnector.HTTP_REQUEST_PATH_PROPERTY));
                    assertEquals("/", msg.getProperty(HttpConnector.HTTP_CONTEXT_PATH_PROPERTY));
                }
            };
        
            testComponent.setEventCallback(callback);
        }
        
        MuleClient client = new MuleClient();
        Map<String, String> props = new HashMap<String, String>();
        props.put(HttpConstants.HEADER_CONTENT_TYPE, "text/plain;charset=UTF-8");
        client.dispatch("clientEndpoint", TEST_MESSAGE, props);
        //assertEquals(TEST_MESSAGE + " Received", result.getPayloadAsString());
    }
}
