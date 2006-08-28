/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the BSD style
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.soap.axis.functional;

import java.util.HashMap;
import java.util.Map;

import org.mule.extras.client.MuleClient;
import org.mule.impl.MuleMessage;
import org.mule.tck.FunctionalTestCase;
import org.mule.umo.UMOMessage;

public class AxisSoapHeadersTestCase extends FunctionalTestCase{

	protected String getConfigResources() {
		return "axis-soapheader-test.xml";
	}
	
	public void testSoapRequest() throws Exception{
		
		MuleClient client = new MuleClient();
		Map properties = new HashMap();
		properties.put("http.method", "POST");
		
		MuleMessage soapRequest = null;
		soapRequest = new MuleMessage("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				
				"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:mule=\"http://www.muleumo.org/providers/soap/1.0\">" +
                    
                    "<soapenv:Header>" +
                        "<Action>storeModuleInformation</Action>"+ //this should be ignored
                        "<mule:header>" +
                            "<mule:MULE_REPLYTO>http://localhost:82/reply</mule:MULE_REPLYTO>" + 
                        "</mule:header>" +
                    "</soapenv:Header>" +
					
					"<soapenv:Body><echo soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\"><value0 xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">Test Message</value0></echo></soapenv:Body>" +
				"</soapenv:Envelope>");
		
		UMOMessage reply = client.send("http://localhost:81/services/component", soapRequest, properties);
		
		assertEquals(reply.getPayloadAsString(),"<?xml version=\"1.0\" encoding=\"UTF-8\"?><soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><soapenv:Body><echoResponse soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\"><echoReturn xsi:type=\"xsd:string\">Test Message</echoReturn></echoResponse></soapenv:Body></soapenv:Envelope>");
	}

}
