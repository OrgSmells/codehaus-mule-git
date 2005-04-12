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
package org.mule.tck.functional;

import org.mule.MuleManager;
import org.mule.config.PoolingProfile;
import org.mule.impl.DefaultExceptionStrategy;
import org.mule.impl.MuleDescriptor;
import org.mule.impl.MuleModel;
import org.mule.impl.endpoint.MuleEndpoint;
import org.mule.tck.AbstractMuleTestCase;
import org.mule.umo.UMOComponent;
import org.mule.umo.UMODescriptor;
import org.mule.umo.UMOEventContext;
import org.mule.umo.UMOTransactionConfig;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.endpoint.UMOEndpointURI;
import org.mule.umo.manager.UMOManager;
import org.mule.umo.provider.UMOConnector;

import java.util.HashMap;

/**
 * @author <a href="mailto:ross.mason@cubis.co.uk">Ross Mason</a>
 * @version $Revision$
 */

public abstract class AbstractProviderFunctionalTestCase extends AbstractMuleTestCase
{
    protected UMOConnector connector;
    protected static UMOManager manager;
    protected boolean callbackCalled = false;
    protected int callbackCount = 0;
    protected MuleDescriptor descriptor;

    protected void setUp() throws Exception
    {
        super.setUp();
        if(MuleManager.isInstanciated()) MuleManager.getInstance().dispose();
        manager = MuleManager.getInstance();
        //Make sure we are running synchronously
        MuleManager.getConfiguration().setSynchronous(true);
        MuleManager.getConfiguration().getPoolingProfile().setInitialisationPolicy(PoolingProfile.POOL_INITIALISE_ONE_COMPONENT);

        manager.setModel(new MuleModel());
        callbackCalled = false;
        callbackCount=0;
        connector = createConnector();
    }

    protected void tearDown() throws Exception
    {
        connector.dispose();
    }

    public void testSend() throws Exception
    {
        descriptor = getTestDescriptor("testComponent", FunctionalTestComponent.class.getName());

        initialiseComponent(descriptor, UMOTransactionConfig.ACTION_NONE, UMOTransactionConfig.ACTION_NONE, this.createEventCallback());
        //Start the server
        MuleManager.getInstance().start();

        sendTestData(100);

        afterInitialise();

        receiveAndTestResults();

        assertTrue(callbackCalled);
    }

    public UMOComponent initialiseComponent(UMODescriptor descriptor, byte txBeginAction, byte txCommitAction,
                                            EventCallback callback) throws Exception
    {

        UMOEndpoint endpoint = new MuleEndpoint("testIn", getInDest(), connector, null,
                UMOEndpoint.ENDPOINT_TYPE_RECEIVER, 0, null);

//        UMOEndpoint outProvider = new MuleEndpoint("testOut", getOutDest(), connector, null,
//                UMOEndpoint.PROVIDER_TYPE_SENDER, true, null);
//
//
//        descriptor.setOutboundEndpoint(outProvider);
        descriptor.setInboundEndpoint(endpoint);
        HashMap props = new HashMap();
        props.put("eventCallback", callback);
        descriptor.setProperties(props);
        MuleManager.getInstance().registerConnector(connector);
        UMOComponent component = MuleManager.getInstance().getModel().registerComponent(descriptor);
        ((MuleDescriptor)descriptor).initialise();
        return component;
    }

    public static MuleDescriptor getTestDescriptor(String name, String implementation)
    {
        MuleDescriptor descriptor = new MuleDescriptor();
        descriptor.setExceptionListener(new DefaultExceptionStrategy());
        descriptor.setName(name);
        descriptor.setImplementation(implementation);
        return descriptor;
    }

    public void afterInitialise() throws Exception
    {

    }

    public EventCallback createEventCallback() {
        EventCallback callback = new EventCallback()
        {
            public void eventReceived(UMOEventContext context, Object Component)
            {
                callbackCalled = true;
                callbackCount++;
                assertNull(context.getCurrentTransaction());

            }
        };
        return callback;
    }

    protected abstract void sendTestData(int iterations) throws Exception;

    protected abstract void receiveAndTestResults() throws Exception;

    protected abstract UMOEndpointURI getInDest();

    protected abstract UMOEndpointURI getOutDest();

    protected abstract UMOConnector createConnector() throws Exception;
}
