/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.ejb;

import java.lang.reflect.Method;
import java.net.UnknownHostException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import org.mule.providers.rmi.RmiConnector;
import org.mule.umo.endpoint.UMOImmutableEndpoint;
import org.mule.util.ClassUtils;

/**
 * Provides Connection configurstion for EJB endpoints
 */

public class EjbConnector extends RmiConnector
{
    // Errorcodes
    public static final int EJB_SERVICECLASS_INVOCATION_FAILED = 2;

    public String getProtocol()
    {
        return "ejb";
    }

    public Remote getRemoteObject(UMOImmutableEndpoint endpoint) throws RemoteException, UnknownHostException
    {
        EJBObject remoteObj;

        try
        {
            Object ref = getRemoteRef(endpoint);

            Method method = ClassUtils.getMethod(ref.getClass(), "create", null);

            remoteObj = (EJBObject)method.invoke(ref, ClassUtils.NO_ARGS);
        }
        catch (Exception e)
        {
            throw new RemoteException("Remote EJBObject lookup failed for '" + endpoint.getEndpointURI(), e);
        }

        return remoteObj;
    }
}
