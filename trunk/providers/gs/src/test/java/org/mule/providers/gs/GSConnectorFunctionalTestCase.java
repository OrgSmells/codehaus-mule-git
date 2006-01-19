/*
 * $Header$
 * $Revision$
 * $Date$
 * ------------------------------------------------------------------------------------------------------
 *
 * Copyright (c) SymphonySoft Limited. All rights reserved.
 * http://www.symphonysoft.com
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.mule.providers.gs;

import org.mule.extras.client.MuleClient;
import org.mule.impl.endpoint.MuleEndpointURI;
import org.mule.tck.functional.AbstractProviderFunctionalTestCase;
import org.mule.umo.UMOMessage;
import org.mule.umo.endpoint.MalformedEndpointException;
import org.mule.umo.endpoint.UMOEndpointURI;
import org.mule.umo.provider.UMOConnector;

/**
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */
public class GSConnectorFunctionalTestCase extends AbstractProviderFunctionalTestCase {

    private String IN_URL = "gs:java://localhost/mule-space_container/mule-space?schema=cache";
    private String OUT_URL = "gs:java://localhost/mule-space2_container/mule-space2?schema=cache";
    //private String URL = "jini:java://localhost/?address=/./mule-space?schema=cache";
    //private String URL = "jini:java://localhost:10098/ross-laptop/JavaSpaces";

    public GSConnectorFunctionalTestCase() {
        System.setProperty("java.security.policy", "C:\\java\\mule-1.2\\samples\\space\\bin\\policy.all");
        System.setProperty("com.gs.home", "C:\\java\\mule-1.2\\samples\\space");
        System.setProperty("com.gs.security.enabled", "false");
    }

    protected void sendTestData(int iterations) throws Exception {
        MuleClient client = new MuleClient();
        client.send(IN_URL, "hello", null);
    }

    protected void receiveAndTestResults() throws Exception {
        MuleClient client = new MuleClient();
        UMOMessage message = client.receive(OUT_URL, 10000L);
        assertNotNull(message);
        assertEquals("hello Received", message.getPayloadAsString());
    }

    protected UMOEndpointURI getInDest() {
        try {
            return new MuleEndpointURI(IN_URL);
        } catch (MalformedEndpointException e) {
            e.printStackTrace();
            fail(e.getMessage());
            return null;
        }
    }

    protected UMOEndpointURI getOutDest() {
        try {
            return new MuleEndpointURI(OUT_URL);
        } catch (MalformedEndpointException e) {
            e.printStackTrace();
            fail(e.getMessage());
            return null;
        }
    }

    protected UMOConnector createConnector() throws Exception {
        GSConnector con = new GSConnector();
        con.setName("spaceConnector");
        return con;
    }
}
