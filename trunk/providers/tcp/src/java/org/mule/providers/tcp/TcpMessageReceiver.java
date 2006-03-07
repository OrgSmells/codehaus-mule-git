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
 *
 */
package org.mule.providers.tcp;

import edu.emory.mathcs.backport.java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang.StringUtils;
import org.mule.config.i18n.Message;
import org.mule.config.i18n.Messages;
import org.mule.impl.MuleMessage;
import org.mule.impl.ResponseOutputStream;
import org.mule.providers.AbstractMessageReceiver;
import org.mule.providers.ConnectException;
import org.mule.umo.UMOComponent;
import org.mule.umo.UMOMessage;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.lifecycle.Disposable;
import org.mule.umo.lifecycle.DisposeException;
import org.mule.umo.lifecycle.InitialisationException;
import org.mule.umo.provider.UMOConnector;
import org.mule.umo.provider.UMOMessageAdapter;

import javax.resource.spi.work.Work;
import javax.resource.spi.work.WorkException;
import javax.resource.spi.work.WorkManager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;

/**
 * <code>TcpMessageReceiver</code> acts like a tcp server to receive socket
 * requests.
 *
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @author <a href="mailto:tsuppari@yahoo.co.uk">P.Oikari</a>
 * @version $Revision$
 */
public class TcpMessageReceiver extends AbstractMessageReceiver implements Work {
    protected ServerSocket serverSocket = null;

    public TcpMessageReceiver(UMOConnector connector, UMOComponent component, UMOEndpoint endpoint)
            throws InitialisationException {
        super(connector, component, endpoint);
    }

    public void doConnect() throws ConnectException {
        disposing.set(false);
        URI uri = endpoint.getEndpointURI().getUri();
        try {
            serverSocket = createSocket(uri);
        } catch (Exception e) {
            throw new org.mule.providers.ConnectException(new Message("tcp", 1, uri), e, this);
        }

        try {
            getWorkManager().scheduleWork(this, WorkManager.INDEFINITE, null, null);
        } catch (WorkException e) {
            throw new ConnectException(new Message(Messages.FAILED_TO_SCHEDULE_WORK), e, this);
        }
    }

    public void doDisconnect() throws ConnectException {
        // this will cause the server thread to quit
        disposing.set(true);
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            logger.warn("Failed to close server socket: " + e.getMessage(), e);
        }
    }

    protected ServerSocket createSocket(URI uri) throws Exception {
        String host = StringUtils.defaultIfEmpty(uri.getHost(), "localhost");
        int backlog = ((TcpConnector) connector).getBacklog();
        InetAddress inetAddress = InetAddress.getByName(host);
        if (inetAddress.equals(InetAddress.getLocalHost()) || inetAddress.isLoopbackAddress()
                || host.trim().equals("localhost")) {
            return new ServerSocket(uri.getPort(), backlog);
        } else {
            return new ServerSocket(uri.getPort(), backlog, inetAddress);
        }
    }

    /**
     * Obtain the serverSocket
     */
    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void run() {
        while (!disposing.get()) {
            if (connector.isStarted() && !disposing.get()) {
                Socket socket = null;
                try {
                    socket = serverSocket.accept();
                    TcpConnector connector = (TcpConnector) this.connector;
                    if (connector.getBufferSize() != UMOConnector.INT_VALUE_NOT_SET && socket.getReceiveBufferSize() != connector.getBufferSize()) {
                        socket.setReceiveBufferSize(connector.getBufferSize());
                    }
                    if (connector.getBufferSize() != UMOConnector.INT_VALUE_NOT_SET && socket.getSendBufferSize() != connector.getBufferSize()) {
                        socket.setSendBufferSize(connector.getBufferSize());
                    }
                    if (connector.getReceiveTimeout() != UMOConnector.INT_VALUE_NOT_SET && socket.getSoTimeout() != connector.getReceiveTimeout()) {
                        socket.setSoTimeout(connector.getReceiveTimeout());
                    }
                    socket.setTcpNoDelay(true);
                    if (logger.isTraceEnabled()) {
                        logger.trace("Server socket Accepted on: " + serverSocket.getLocalPort());
                    }
                } catch (java.io.InterruptedIOException iie) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Interupted IO doing serverSocket.accept: " + iie.getMessage());
                    }
                } catch (Exception e) {
                    if (!connector.isDisposed() && !disposing.get()) {
                        logger.warn("Accept failed on socket: " + e, e);
                        handleException(new ConnectException(e, this));
                    }
                }
                if (socket != null) {
                    try {
                        Work work = createWork(socket);
                        try {
                            getWorkManager().scheduleWork(work, WorkManager.IMMEDIATE, null, null);
                        } catch (WorkException e) {
                            logger.error("Tcp Server receiver Work was not processed: " + e.getMessage(), e);
                        }
                    } catch (IOException e) {
                        handleException(e);
                    }

                }
            }
        }
    }

    public void release() {
    }

    public void doDispose() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            serverSocket = null;

        } catch (Exception e) {
            logger.error(new DisposeException(new Message("tcp", 2), e));
        }
        logger.info("Closed Tcp port");
    }

    protected Work createWork(Socket socket) throws IOException {
        return new TcpWorker(socket);
    }

    protected class TcpWorker implements Work, Disposable {
        protected Socket socket = null;
        protected DataInputStream dataIn;
        protected DataOutputStream dataOut;
        protected AtomicBoolean closed = new AtomicBoolean(false);
        protected TcpProtocol protocol;

        public TcpWorker(Socket socket) {
            this.socket = socket;

            final TcpConnector tcpConnector = ((TcpConnector) connector);
            this.protocol = tcpConnector.getTcpProtocol();
            tcpConnector.updateReceiveSocketsCount(true);
        }

        public void release() {
            dispose();
        }

        public void dispose() {
            closed.set(true);
            try {
                if (socket != null && !socket.isClosed()) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Closing listener: " + socket.getLocalSocketAddress().toString());
                    }
                    socket.close();
                }
            } catch (IOException e) {
                logger.error("Socket close failed with: " + e);
            } finally {
                ((TcpConnector) connector).updateReceiveSocketsCount(false);
            }
        }

        /**
         * Accept requests from a given TCP port
         */
        public void run() {
            try {
                dataIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                dataOut = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

                while (!socket.isClosed() && !disposing.get()) {

                    byte[] b = protocol.read(dataIn);
                    // end of stream
                    if (b == null) {
                        break;
                    }

                    byte[] result = processData(b);
                    if (result != null) {
                        protocol.write(dataOut, result);
                    }
                    dataOut.flush();
                }
            } catch (Exception e) {
                handleException(e);
            } finally {
                dispose();
            }
        }

        protected byte[] processData(byte[] data) throws Exception {
            UMOMessageAdapter adapter = connector.getMessageAdapter(data);
            OutputStream os = new ResponseOutputStream(socket.getOutputStream(), socket);
            UMOMessage returnMessage = routeMessage(new MuleMessage(adapter), endpoint.isSynchronous(), os);
            if (returnMessage != null) {
                return returnMessage.getPayloadAsBytes();
            } else {
                return null;
            }
        }

    }
}
