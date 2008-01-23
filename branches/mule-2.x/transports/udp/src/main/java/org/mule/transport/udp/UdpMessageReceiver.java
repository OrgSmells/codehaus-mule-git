/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.udp;

import org.mule.DefaultMuleMessage;
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.api.component.Component;
import org.mule.api.config.MuleProperties;
import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.api.lifecycle.CreateException;
import org.mule.api.lifecycle.Disposable;
import org.mule.api.transport.Connector;
import org.mule.api.transport.MessageAdapter;
import org.mule.config.i18n.CoreMessages;
import org.mule.transport.AbstractMessageReceiver;
import org.mule.transport.ConnectException;
import org.mule.transport.udp.i18n.UdpMessages;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.List;

import javax.resource.spi.work.Work;
import javax.resource.spi.work.WorkException;
import javax.resource.spi.work.WorkManager;

/** <code>UdpMessageReceiver</code> receives UDP message packets. */
public class UdpMessageReceiver extends AbstractMessageReceiver implements Work
{
    protected DatagramSocket socket = null;
    protected InetAddress inetAddress;
    protected int bufferSize;
    private URI uri;
    protected List responseTransformers = null;

    public UdpMessageReceiver(Connector connector, Component component, ImmutableEndpoint endpoint)
            throws CreateException
    {

        super(connector, component, endpoint);

        bufferSize = ((UdpConnector) connector).getReceiveBufferSize();

        uri = endpoint.getEndpointURI().getUri();

        try
        {
            if (!"null".equalsIgnoreCase(uri.getHost()))
            {
                inetAddress = InetAddress.getByName(uri.getHost());
            }
        }
        catch (UnknownHostException e)
        {
            throw new CreateException(UdpMessages.failedToLocateHost(uri), e, this);
        }

        responseTransformers = getResponseTransformers();
    }

    protected void doConnect() throws Exception
    {
        try
        {
            socket = ((UdpConnector) connector).getSocket(endpoint);
        }
        catch (Exception e)
        {
            throw new ConnectException(UdpMessages.failedToBind(uri), e, this);
        }

        try
        {
            getWorkManager().scheduleWork(this, WorkManager.INDEFINITE, null, connector);
        }
        catch (WorkException e)
        {
            throw new ConnectException(CoreMessages.failedToScheduleWork(), e, this);
        }
    }

    protected void doDisconnect() throws Exception
    {
        // this will cause the server thread to quit
        disposing.set(true);
        if (socket != null)
        {
            socket.close();
        }

    }

    protected void doStart() throws MuleException
    {
        // nothing to do
    }

    protected void doStop() throws MuleException
    {
        // nothing to do
    }

    protected List getResponseTransformers()
    {
        List transformers = endpoint.getResponseTransformers();
        if (transformers == null)
        {
            return connector.getDefaultResponseTransformers();
        }
        return transformers;
    }

    protected DatagramSocket createSocket(URI uri, InetAddress inetAddress) throws IOException
    {
        return new DatagramSocket(uri.getPort(), inetAddress);
    }

    /** Obtain the serverSocket */
    public DatagramSocket getSocket()
    {
        return socket;
    }

    protected DatagramPacket createPacket()
    {
        DatagramPacket packet = new DatagramPacket(new byte[bufferSize], bufferSize);
//        if (uri.getPort() > 0)
//        {
//            packet.setPort(uri.getPort());
//        }
//        packet.setAddress(inetAddress);
        return packet;
    }

    public void run()
    {
        while (!disposing.get())
        {
            if (connector.isStarted())
            {

                try
                {
                    DatagramPacket packet = createPacket();
                    try
                    {
                        if (logger.isDebugEnabled())
                        {
                            logger.debug("Receiving packet on " + uri);
                        }
                        socket.receive(packet);

                        if (logger.isTraceEnabled())
                        {
                            logger.trace("Received packet on: " + uri);
                        }

                        Work work = createWork(packet);
                        try
                        {
                            getWorkManager().scheduleWork(work, WorkManager.INDEFINITE, null, connector);
                        }
                        catch (WorkException e)
                        {
                            logger.error("Udp receiver interrupted: " + e.getMessage(), e);
                        }
                    }
                    catch (SocketTimeoutException e)
                    {
                        // ignore
                    }

                }
                catch (Exception e)
                {
                    if (!connector.isDisposed() && !disposing.get())
                    {
                        logger.debug("Accept failed on socket: " + e, e);
                        handleException(e);
                    }
                }
            }
        }
    }

    public void release()
    {
        dispose();
    }

    protected void doDispose()
    {
        if (socket != null && !socket.isClosed())
        {
            logger.debug("Closing Udp connection: " + uri);
            socket.close();
            logger.info("Closed Udp connection: " + uri);
        }
    }

    protected Work createWork(DatagramPacket packet) throws IOException
    {
        return new UdpWorker(new DatagramSocket(0), packet);
    }

    protected class UdpWorker implements Work, Disposable
    {
        private DatagramSocket socket = null;
        private DatagramPacket packet;

        public UdpWorker(DatagramSocket socket, DatagramPacket packet)
        {
            this.socket = socket;
            this.packet = packet;
        }

        public void release()
        {
            dispose();
        }

        public void dispose()
        {
            if (socket != null && !socket.isClosed())
            {
                try
                {
                    socket.close();
                }
                catch (Exception e)
                {
                    logger.error("Socket close failed", e);
                }
            }
            socket = null;
        }

        /** Accept requests from a given Udp address */
        public void run()
        {
            MuleMessage returnMessage = null;
            try
            {
                MessageAdapter adapter = connector.getMessageAdapter(packet);
                final SocketAddress clientAddress = socket.getRemoteSocketAddress();
                if (clientAddress != null)
                {
                    adapter.setProperty(MuleProperties.MULE_REMOTE_CLIENT_ADDRESS, clientAddress);
                }
                returnMessage = routeMessage(new DefaultMuleMessage(adapter), endpoint.isSynchronous());

                if (returnMessage != null)
                {
                    byte[] data;
                    if (responseTransformers != null)
                    {
                        returnMessage.applyTransformers(responseTransformers);
                        Object response = returnMessage.getPayload();
                        if (response instanceof byte[])
                        {
                            data = (byte[]) response;
                        }
                        else
                        {
                            data = response.toString().getBytes();
                        }
                    }
                    else
                    {
                        data = returnMessage.getPayloadAsBytes();
                    }
                    DatagramPacket result = new DatagramPacket(data, data.length, packet.getAddress(),
                            packet.getPort());
                    socket.send(result);
                }
            }
            catch (Exception e)
            {
                if (!disposing.get())
                {
                    handleException(e);
                }
            }
            finally
            {
                dispose();
            }
        }
    }
}
