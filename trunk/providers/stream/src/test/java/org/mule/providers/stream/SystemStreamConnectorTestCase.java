/*
 * $Header$ $Revision$ $Date$
 * ------------------------------------------------------------------------------------------------------
 *
 * Copyright (c) Cubis Limited. All rights reserved. http://www.cubis.co.uk
 *
 * The software in this package is published under the terms of the BSD style
 * license a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 *
 */

package org.mule.providers.stream;

import com.mockobjects.dynamic.C;
import com.mockobjects.dynamic.Mock;
import org.mule.impl.MuleMessage;
import org.mule.providers.stream.SystemStreamConnector;
import org.mule.tck.providers.AbstractConnectorTestCase;
import org.mule.umo.UMOComponent;
import org.mule.umo.UMOEvent;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.endpoint.UMOImmutableEndpoint;
import org.mule.umo.provider.UMOConnector;

/**
 * @author <a href="mailto:ross.mason@cubis.co.uk">Ross Mason</a>
 * @version $Revision$
 */
public class SystemStreamConnectorTestCase extends AbstractConnectorTestCase
{
    /*
	 * (non-Javadoc)
	 *
	 * @see org.mule.tck.providers.AbstractConnectorTestCase#createConnector()
	 */
    public UMOConnector createConnector() throws Exception
    {
        UMOConnector connector = new SystemStreamConnector();
        connector.setName("TestStream");
        connector.initialise();
        return connector;
    }

    public String getTestEndpointURI()
    {
        return "stream://System.out";
    }

    /*
	 * (non-Javadoc)
	 *
	 * @see org.mule.tck.providers.AbstractConnectorTestCase#testDispatch()
	 */
    public void testDispatch() throws Exception
    {
        UMOConnector connector = createConnector();

        UMOEndpoint endpoint = getTestEndpoint("test", UMOImmutableEndpoint.ENDPOINT_TYPE_SENDER);
        UMOComponent component = getTestComponent(descriptor);
        Mock event = getMockEvent();


        //event.expectAndReturn("getMessage", new MuleMessage("Hello", null));
        event.expect("setSynchronous", C.IS_FALSE);
        event.expectAndReturn("isSynchronous", false);
        event.expectAndReturn("getEndpoint", endpoint);
        event.expect("setProperty", C.ANY_ARGS);
        event.expectAndReturn("getTransformedMessageAsBytes", new byte[0]);
        connector.registerListener(component, endpoint);
        connector.start();
        connector.getDispatcher("dummy").dispatch((UMOEvent) event.proxy());
        //running async we need to wait for everything to load
        Thread.sleep(1000);
        event.verify();
    }

    /*
	 * (non-Javadoc)
	 *
	 * @see org.mule.tck.providers.AbstractConnectorTestCase#testSend()
	 */
    public void testSend() throws Exception
    {
        UMOConnector connector = createConnector();

        UMOComponent component = getTestComponent(descriptor);
        UMOEndpoint endpoint = getTestEndpoint("test", UMOImmutableEndpoint.ENDPOINT_TYPE_SENDER);
        Mock event = getMockEvent();

        event.expectAndReturn("getMessage", new MuleMessage("Hello", null));

        event.expectAndReturn("getTransformedMessageAsBytes", "Hello".getBytes());
        event.expect("setSynchronous", C.IS_TRUE);
        event.expectAndReturn("getEndpoint", endpoint);
        event.expect("setProperty", C.ANY_ARGS);
        connector.registerListener(component, endpoint);
        connector.start();
        connector.getDispatcher("dummy").send((UMOEvent) event.proxy());

        event.verify();
    }

    public void testGetStreams() throws Exception
    {
        SystemStreamConnector connector = (SystemStreamConnector) createConnector();
        assertNotNull(connector.getInputStream());
        assertNotNull(connector.getOutputStream());
    }

    public UMOConnector getConnector() throws Exception
    {
        UMOConnector cnn = new SystemStreamConnector();
        cnn.setName("TestStream");
        cnn.initialise();
        return cnn;
    }

    public Object getValidMessage() throws Exception
    {
        return "Test Message";
    }
}
