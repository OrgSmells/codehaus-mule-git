/*
 * $Header$
 * $Revision$
 * $Date$
 * ------------------------------------------------------------------------------------------------------
 *
 * Copyright (c) Cubis Limited. All rights reserved.
 * http://www.cubis.co.uk
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.mule.providers.ssl;

import org.mule.impl.MuleDescriptor;
import org.mule.impl.endpoint.MuleEndpointURI;
import org.mule.tck.providers.AbstractConnectorTestCase;
import org.mule.tck.testmodels.fruit.Orange;
import org.mule.umo.UMOComponent;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.provider.UMOConnector;
import org.mule.InitialisationException;

import java.io.IOException;

/**
 * @author <a href="mailto:ross.mason@cubis.co.uk">Ross Mason</a>
 * @version $Revision$
 */

public class SslConnectorTestCase extends AbstractConnectorTestCase
{
    public UMOConnector getConnector() throws Exception
    {
        return createConnector(true);
    }

    public static SslConnector createConnector(boolean initialised) throws InitialisationException, IOException
    {
        SslConnector cnn = new SslConnector();
        cnn.setName("SslConnector");
        cnn.setKeyStore("serverKeystore");
        cnn.setClientKeyStore("clientKeystore");
        cnn.setClientKeyStorePassword("mulepassword");
        cnn.setKeyPassword("mulepassword");
        cnn.setStorePassword("mulepassword");
        cnn.getDispatcherThreadingProfile().setDoThreading(false);
        if(initialised) cnn.initialise();
        return cnn;
    }
    public String getTestEndpointURI()
    {
        return "ssl://localhost:56801";
    }

    public Object getValidMessage() throws Exception
    {
        return "Hello".getBytes();
    }

    public void testValidListener() throws Exception
    {
        MuleDescriptor d = getTestDescriptor("orange", Orange.class.getName());
        UMOComponent component = getTestComponent(d);
        UMOEndpoint endpoint = getTestEndpoint("Test", UMOEndpoint.ENDPOINT_TYPE_RECEIVER);
        endpoint.setEndpointURI(null);
        endpoint.setConnector(connector);

        try
        {
            connector.registerListener(component, endpoint);
            fail("cannot register with null endpointUri");
        }
        catch (Exception e)
        { /* expected */
        }
        endpoint.setEndpointURI(null);
        try
        {
            connector.registerListener(component, endpoint);
            fail("cannot register with empty endpointUri");
        }
        catch (Exception e)
        { /* expected */
        }

        endpoint.setEndpointURI(new MuleEndpointURI("ssl://localhost:30303"));
        connector.registerListener(component, endpoint);
        try
        {
//            connector.registerListener(component, endpoint);
//            fail("cannot register on the same endpointUri");
        }
        catch (Exception e)
        { /* expected */
        }
    }

    public void testProperties() throws Exception
    {
        SslConnector c = (SslConnector)connector;

        c.setBufferSize(1024);
        assertEquals(1024, c.getBufferSize());
        c.setBufferSize(0);
        assertEquals(SslConnector.DEFAULT_BUFFER_SIZE, c.getBufferSize());
        c.setTimeout(-1);
        assertEquals(SslConnector.DEFAULT_SOCKET_TIMEOUT, c.getTimeout());

    }
}
