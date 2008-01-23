/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.soap.xfire;

import org.mule.api.MuleMessage;
import org.mule.api.config.MuleProperties;
import org.mule.api.context.notification.MessageNotificationListener;
import org.mule.api.context.notification.ServerNotification;
import org.mule.context.notification.MessageNotification;
import org.mule.extras.client.MuleClient;
import org.mule.tck.FunctionalTestCase;

import java.util.HashMap;

import edu.emory.mathcs.backport.java.util.concurrent.CountDownLatch;
import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;

public class XFireCustomHttpHeaderTestCase extends FunctionalTestCase implements MessageNotificationListener
{
    protected static final String endpointAddress = "http://localhost:63181/services/TestComponent?method=onReceive";

    private MuleMessage notificationMsg = null;
    private CountDownLatch latch = null;

    // @Override
    protected void doSetUp() throws Exception
    {
        latch = new CountDownLatch(1);
        muleContext.registerListener(this);
    }

    // @Override
    protected void doTearDown() throws Exception
    {
        muleContext.unregisterListener(this);
    }

    public void testXfire() throws Exception
    {
        Object payload = new Object[]{"Test String"};
        String myProperty = "myProperty";

        muleContext.registerListener(this);
        MuleClient client = new MuleClient();

        HashMap props = new HashMap();
        props.put(MuleProperties.MULE_USER_PROPERTY, "alan");
        props.put(MuleProperties.MULE_METHOD_PROPERTY, "sayHello");
        props.put(myProperty, myProperty);

        MuleMessage reply = client.send("xfire:" + endpointAddress, payload, props);

        assertNotNull(reply);
        assertNotNull(reply.getPayload());
        assertEquals("Test String Received", reply.getPayloadAsString());

        // make sure all notifications have trickled in
        latch.await(20000, TimeUnit.MILLISECONDS);

        // make sure we received a notification on xfire
        assertNotNull(notificationMsg);

        // MULE_USER should be allowed in
        assertEquals("alan", notificationMsg.getProperty(MuleProperties.MULE_USER_PROPERTY));

        // mule properties should be removed
        assertNull(notificationMsg.getProperty(MuleProperties.MULE_IGNORE_METHOD_PROPERTY));

        // custom properties should be allowed in
        assertEquals(myProperty, notificationMsg.getProperty(myProperty));
    }

    public void onNotification(ServerNotification notification)
    {
        if (notification instanceof MessageNotification)
        {
            String uri = ((MessageNotification)notification).getEndpoint().getEndpointURI().toString();
            if (endpointAddress.equals(uri))
            {
                notificationMsg = (MuleMessage)notification.getSource();
                latch.countDown();
            }            
        }
        else
        {
            fail("invalid notification: " + notification);
        }
    }

    protected String getConfigResources()
    {
        return "xfire-headers-conf.xml";
    }
}
