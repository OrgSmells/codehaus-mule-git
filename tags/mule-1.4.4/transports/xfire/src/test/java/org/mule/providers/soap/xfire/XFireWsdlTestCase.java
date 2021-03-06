/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.soap.xfire;

import org.mule.MuleManager;
import org.mule.extras.client.MuleClient;
import org.mule.impl.MuleEvent;
import org.mule.impl.MuleMessage;
import org.mule.impl.MuleSession;
import org.mule.impl.endpoint.MuleEndpoint;
import org.mule.providers.AbstractConnector;
import org.mule.tck.AbstractMuleTestCase;
import org.mule.umo.UMOMessage;
import org.mule.umo.UMOSession;
import org.mule.umo.endpoint.UMOEndpoint;

import org.custommonkey.xmlunit.XMLAssert;
import org.w3c.dom.Document;

public class XFireWsdlTestCase extends AbstractMuleTestCase
{
    public static final String TEST_URL = "wsdl-xfire:http://localhost:63080/mule-tests-external-xfire/services/TestService?WSDL&method=getTest";
    public static final String TEST_URL_NOWSDL = "wsdl-xfire:http://localhost:63080/mule-tests-external-xfire/services/TestService?method=getTest";
    public static final String TEST_URL_WSDL = "http://localhost:63080/mule-tests-external-xfire/services/TestService?wsdl";

    public void testXFireWsdlService() throws Exception
    {
        MuleClient client = new MuleClient();

        UMOMessage message = new MuleMessage("test1");
        UMOMessage reply = client.send(TEST_URL, message);
        assertNotNull(reply);

        Document response = (Document)reply.getPayload();
        assertNotNull(response);

        XMLAssert.assertXpathEvaluatesTo("test1", "//*[namespace-uri()='http://applications.external.tck.mule.org' and local-name()='key']", response);
    }

    /**
     * This tests the endpoint propery of wsdlUrl which specifies an alternative WSDL
     * location (see MULE-1368)
     */
    public void testXFireWsdlServiceWithEndpointParam() throws Exception
    {
        // make sure the Mule is up when not using MuleClient
        MuleManager.getInstance().start();

        UMOEndpoint endpoint = MuleEndpoint.getOrCreateEndpointForUri(TEST_URL_NOWSDL,
            UMOEndpoint.ENDPOINT_TYPE_SENDER);
        endpoint.setProperty("wsdlUrl", TEST_URL_WSDL);

        UMOMessage message = new MuleMessage("test1");
        UMOSession session = new MuleSession(message, ((AbstractConnector) endpoint.getConnector())
            .getSessionHandler());
        MuleEvent event = new MuleEvent(message, endpoint, session, true);
        UMOMessage reply = session.sendEvent(event);

        assertNotNull(reply);

        Document response = (Document)reply.getPayload();
        assertNotNull(response);

        XMLAssert.assertXpathEvaluatesTo("test1", "//*[namespace-uri()='http://applications.external.tck.mule.org' and local-name()='key']", response);
        
        // bye-bye
        MuleManager.getInstance().dispose();
    }
}
