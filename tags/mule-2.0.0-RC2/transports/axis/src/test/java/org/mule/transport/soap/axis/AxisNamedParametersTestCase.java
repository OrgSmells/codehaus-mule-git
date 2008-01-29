/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.soap.axis;

import org.mule.api.MuleMessage;
import org.mule.api.config.MuleProperties;
import org.mule.extras.client.MuleClient;
import org.mule.tck.FunctionalTestCase;
import org.mule.transport.soap.NamedParameter;
import org.mule.transport.soap.SoapMethod;

import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

public class AxisNamedParametersTestCase extends FunctionalTestCase
{

    protected String getConfigResources()
    {
        return "axis-named-param-mule-config.xml";
    }

    public void testNamedParameters() throws Exception
    {
        MuleClient client = new MuleClient();
        // The service itself will throw an exception if the parameters in the
        // request SOAP message are not named
        MuleMessage result = client.send("vm://mycomponent1", "Hello Named", null);
        assertEquals("Hello Named", result.getPayload());
    }

    public void testNamedParametersViaClient() throws Exception
    {
        MuleClient client = new MuleClient();
        Map props = new HashMap();
        // create the soap method passing in the method name and return type
        SoapMethod soapMethod = new SoapMethod(new QName("echo"), NamedParameter.XSD_STRING);
        // add one or more parameters
        soapMethod.addNamedParameter(new QName("value"), NamedParameter.XSD_STRING, ParameterMode.IN);
        // set the soap method as a property and pass the properties
        // when making the call
        props.put(MuleProperties.MULE_SOAP_METHOD, soapMethod);

        MuleMessage result = client.send("axis:http://localhost:62111/mule/mycomponent2?method=echo",
            "Hello Named", props);
        assertEquals("Hello Named", result.getPayload());
    }
}
