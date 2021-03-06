/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.providers.rmi;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.impl.MuleMessage;
import org.mule.providers.AbstractMessageDispatcher;
import org.mule.umo.UMOEvent;
import org.mule.umo.UMOException;
import org.mule.umo.UMOMessage;
import org.mule.umo.endpoint.UMOEndpointURI;
import org.mule.umo.endpoint.UMOImmutableEndpoint;
import org.mule.umo.lifecycle.InitialisationException;
import org.mule.umo.provider.UMOConnector;
import org.mule.umo.transformer.TransformerException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collections;
import java.util.List;

/**
 *
 * <code>RmiMessageDispatcher</code> will send transformed mule events over
 * RMI-JRMP.
 *
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @author <a href="mailto:fsweng@bass.com.my">fs Weng</a>
 * @version $Revision$
 */
public class RmiMessageDispatcher extends AbstractMessageDispatcher {

    protected static transient Log logger = LogFactory.getLog(RmiMessageDispatcher.class);

    private RmiConnector connector;

    protected InetAddress inetAddress;

    protected int port;

    protected String serviceName;

    protected Remote remoteObject;

    protected Method invokedMethod;

    public RmiMessageDispatcher(UMOImmutableEndpoint endpoint) {
        super(endpoint);
        this.connector = (RmiConnector)endpoint.getConnector();
    }

    protected void doConnect(UMOImmutableEndpoint endpoint) throws Exception {
        if (remoteObject==null) {

            remoteObject = getRemoteObject();
            invokedMethod = getMethodObject(remoteObject);
        }
    }

    protected void doDisconnect() throws Exception {
        remoteObject = null;
        invokedMethod = null;
    }

    protected Remote getRemoteObject() throws RemoteException, MalformedURLException,
            NotBoundException, UnknownHostException {
        Remote remoteObj;

        UMOEndpointURI endpointUri = endpoint.getEndpointURI();

        port = endpointUri.getPort();
        if (port < 1) {
            if(logger.isWarnEnabled()) {
                logger.warn("RMI port not set on URI: " + endpointUri + ". Using default port: " + RmiConnector.DEFAULT_RMI_REGISTRY_PORT);
            }
            port = RmiConnector.DEFAULT_RMI_REGISTRY_PORT;
        }

        inetAddress = InetAddress.getByName(endpointUri.getHost());
        serviceName = endpointUri.getPath();

        String name = "//" + inetAddress.getHostAddress() + ":" + port + serviceName;

        remoteObj = Naming.lookup(name);

        return remoteObj;
    }

    protected  Method getMethodObject(Remote remoteObject) throws InitialisationException,
            NoSuchMethodException, ClassNotFoundException {
        Method method;
        UMOEndpointURI endpointUri = endpoint.getEndpointURI();

        String methodName = MapUtils.getString(endpointUri.getParams(),
                RmiConnector.PARAM_SERVICE_METHOD, null);

        if (methodName == null) {
            methodName = (String) endpoint.getProperties().get(RmiConnector.PARAM_SERVICE_METHOD);
            if (methodName == null) {
                throw new InitialisationException(new org.mule.config.i18n.Message("rmi",
                        RmiConnector.MSG_PARAM_SERVICE_METHOD_NOT_SET), this);
            }
        }


        List methodArgumentTypes = (List) endpoint.getProperty(RmiConnector.PROPERTY_SERVICE_METHOD_PARAM_TYPES);
        if (methodArgumentTypes != null) {
            connector.setMethodArgumentTypes(methodArgumentTypes);
        }

        Class [] argTypes = connector.getArgumentClasses();
        method = remoteObject.getClass().getMethod(methodName, argTypes);

        return method;
    }

    private Object[] getArgs(UMOEvent event) throws TransformerException {
        Object payload = event.getTransformedMessage();
        Object[] args;
        if (payload instanceof Object[]) {
            args = (Object[]) payload;
        } else {
            args = new Object[]{payload};
        }
        return args;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.umo.provider.UMOConnectorSession#dispatch(org.mule.umo.UMOEvent)
     */
    protected void doDispatch(UMOEvent event) throws Exception {

        Object[] arguments = getArgs(event);
        invokedMethod.invoke(remoteObject, arguments);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.umo.provider.UMOConnectorSession#send(org.mule.umo.UMOEvent)
     */
    public UMOMessage doSend(UMOEvent event) throws IllegalAccessException, InvocationTargetException, Exception {

        UMOMessage resultMessage;

        Object[] arguments = getArgs(event);
        Object result = invokedMethod.invoke(remoteObject, arguments);

        if (result == null) {
            return null;
        } else {
            resultMessage = new MuleMessage(connector.getMessageAdapter(result).getPayload(), Collections.EMPTY_MAP);
        }

        return resultMessage;
    }

    /**
     * Make a specific request to the underlying transport
     *
     * @param endpoint the endpoint to use when connecting to the resource
     * @param timeout  the maximum time the operation should block before returning. The call should
     *                 return immediately if there is data available. If no data becomes available before the timeout
     *                 elapses, null will be returned
     * @return the result of the request wrapped in a UMOMessage object. Null will be returned if no data was
     *         avaialable
     * @throws Exception if the call to the underlying protocal cuases an exception
     */
    protected UMOMessage doReceive(UMOImmutableEndpoint endpoint, long timeout) throws Exception {
        throw new UnsupportedOperationException("doReceive");
    }

    /**
     * There is no associated session for a RMI connector
     *
     * @return
     * @throws UMOException
     */
    public Object getDelegateSession() throws UMOException {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.umo.provider.UMOConnectorSession#getConnector()
     */
    public UMOConnector getConnector() {
        return connector;
    }

    protected void doDispose() {
        // template method
    }

}
