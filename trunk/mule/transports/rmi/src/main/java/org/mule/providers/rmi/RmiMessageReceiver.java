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
import org.mule.impl.MuleMessage;
import org.mule.providers.ConnectException;
import org.mule.providers.PollingMessageReceiver;
import org.mule.umo.UMOComponent;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.lifecycle.InitialisationException;
import org.mule.umo.provider.UMOConnector;
import org.mule.util.ClassUtils;

import java.lang.reflect.Method;
import java.rmi.RMISecurityManager;
import java.rmi.Remote;
import java.util.List;

/**
 * Will repeatedly call a method on a Remote object. If the method takes parameters
 * A List of objects can be specified on the endpoint called <code>methodArgumentsList</code>,
 * If this property is ommitted it is assumed that the method takes no parameters
 */

public class RmiMessageReceiver extends PollingMessageReceiver
{
    protected RmiConnector connector;

    protected Remote remoteObject;

    protected Method invokeMethod;

    protected Object[] methodArguments = null;


    public RmiMessageReceiver(UMOConnector connector, UMOComponent component, UMOEndpoint endpoint, Long frequency) throws InitialisationException
    {
        super(connector, component, endpoint, frequency);

        this.connector = (RmiConnector) connector;
    }

    public void doConnect() throws Exception
    {
        System.setProperty("java.security.policy", connector.getSecurityPolicy());

        // Set security manager
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }

        String methodName = MapUtils.getString(endpoint.getEndpointURI().getParams(),
                        RmiConnector.PARAM_SERVICE_METHOD, null);

        if (null == methodName) {
            methodName = (String) endpoint.getProperty(RmiConnector.PARAM_SERVICE_METHOD);

            if (null == methodName) {
                throw new ConnectException(new org.mule.config.i18n.Message("rmi",
                        RmiConnector.MSG_PARAM_SERVICE_METHOD_NOT_SET), this);
            }
        }


        remoteObject = connector.getRemoteObject(getEndpoint());

        List args = (List)endpoint.getProperty(RmiConnector.PROPERTY_SERVICE_METHOD_PARAMS_LIST);

        Class[] argTypes = new Class[]{};

        if(args==null) {
            logger.info(RmiConnector.PROPERTY_SERVICE_METHOD_PARAMS_LIST + " not set on endpoint, assuming method call has no arguments");
            methodArguments = ClassUtils.NO_ARGS;
        } else {
            argTypes = ClassUtils.getClassTypes(methodArguments);
            methodArguments = new Object[args.size()];
            methodArguments = args.toArray(methodArguments);
        }
        invokeMethod = remoteObject.getClass().getMethod(methodName, argTypes);
    }

    public void doDisconnect()
    {
        invokeMethod = null;
        remoteObject = null;
    }

    public void poll()
    {
        try {
            Object result = invokeMethod.invoke(remoteObject, getMethodArguments());

            if (null != result) {
                final Object payload = connector.getMessageAdapter(result).getPayload();
                routeMessage(new MuleMessage(payload), endpoint.isSynchronous());
            }
        }
        catch (Exception e) {
            handleException(e);
        }
    }

    /**
     * Returns the method arguments to use when invoking the method on the Remote object.
     * This method can be overloaded to enable dynamic method arguments
     * @return
     */
    protected Object[] getMethodArguments() {
        return methodArguments;
    }
}
