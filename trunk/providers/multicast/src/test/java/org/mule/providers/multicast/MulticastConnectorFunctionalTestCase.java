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
package org.mule.providers.multicast;

import org.mule.impl.endpoint.MuleEndpointURI;
import org.mule.providers.udp.UdpMessageAdapter;
import org.mule.tck.functional.AbstractProviderFunctionalTestCase;
import org.mule.umo.endpoint.MalformedEndpointException;
import org.mule.umo.endpoint.UMOEndpointURI;
import org.mule.umo.provider.UMOConnector;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.URI;

/**
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */

public class MulticastConnectorFunctionalTestCase extends AbstractProviderFunctionalTestCase
{
    private MulticastSocket s1 = null;
    private MulticastSocket s2 = null;
    private InetAddress inet = null;
    private URI uri;


    protected void setUp() throws Exception
    {
        super.setUp();
        uri = getInDest().getUri();
        inet = InetAddress.getByName(uri.getHost());

    }

    protected void tearDown() throws Exception
    {
        try {
            s1.close();
        } catch (Exception e) {
        }
        try {
            s2.close();
        } catch (Exception e) {
        }
        super.tearDown();
    }

//    public void testX() throws Exception
//    {
//        String msg = "Hello";
//        InetAddress group = InetAddress.getByName("228.5.6.7");
//        MulticastSocket s = new MulticastSocket(6789);
//        s.joinGroup(group);
//        DatagramPacket hi = new DatagramPacket(msg.getBytes(), msg.length(),
//                group, 6789);
//        s.send(hi);
//        // get their responses!
//        byte[] buf = new byte[1000];
//        DatagramPacket recv = new DatagramPacket(buf, buf.length);
//        s.setBroadcast(true);
//        s.receive(recv);
//        // OK, I'm done talking - leave the group...
//        System.out.println(new String(recv.getData()));
//        s.leaveGroup(group);
//    }

    protected void sendTestData(int iterations) throws Exception
    {

        s1 = new MulticastSocket(uri.getPort());
        s1.joinGroup(inet);
		
        s2 = new MulticastSocket(uri.getPort());
        s2.joinGroup(inet);
		
        for (int i = 0; i < iterations; i++)
        {
            String msg = "Hello" + i;

            DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.length(), inet, uri.getPort());

            System.out.println("sending message: " + i);
            s1.send(packet);
        }
    }

    protected void receiveAndTestResults() throws Exception
    {
        s2.setSoTimeout(2000);
        for (int i = 0; i < 100; i++)
        {

            DatagramPacket packet = new DatagramPacket(new byte[32], 32, inet, uri.getPort());

            s2.receive(packet);
            UdpMessageAdapter adapter = new UdpMessageAdapter(packet);
            System.out.println("Received message: " + adapter.getPayloadAsString());

        }
		Thread.sleep(3000);
    }

    protected UMOEndpointURI getInDest()
    {
        try
        {
            return new MuleEndpointURI("multicast://228.8.9.10:6677");
        } catch (MalformedEndpointException e)
        {
            fail(e.getMessage());
            return null;
        }
    }

    protected UMOEndpointURI getOutDest()
    {
        return getInDest();
    }

    public UMOConnector createConnector() throws Exception
    {
        MulticastConnector connector = new MulticastConnector();
        connector.setName("testMulticast");
        connector.getDispatcherThreadingProfile().setDoThreading(false);
        
        connector.setBufferSize(1024);
        return connector;
    }
}