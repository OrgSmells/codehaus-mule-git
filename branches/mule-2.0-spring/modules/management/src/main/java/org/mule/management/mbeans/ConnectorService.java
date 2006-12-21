/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.management.mbeans;

import java.beans.ExceptionListener;

import org.mule.umo.UMOException;
import org.mule.umo.lifecycle.InitialisationException;
import org.mule.umo.provider.UMOConnector;
import org.mule.umo.provider.UMOMessageDispatcherFactory;
import org.mule.util.ObjectNameHelper;

public class ConnectorService implements ConnectorServiceMBean
{
    private final UMOConnector connector;
    private final String name;

    public ConnectorService(final UMOConnector connector)
    {
        this.connector = connector;
        name = ObjectNameHelper.getConnectorName(connector);
    }

    public boolean isStarted()
    {
        return connector.isStarted();
    }

    public boolean isDisposed()
    {
        return connector.isDisposed();
    }

    public boolean isDisposing()
    {
        return connector.isDisposing();
    }

    public String getName()
    {
        return name;
    }

    public String getProtocol()
    {
        return connector.getProtocol();
    }

    public ExceptionListener getExceptionListener()
    {
        return connector.getExceptionListener();
    }

    // TODO HH: we should probably get rid of this, the factory is nobody's business
    public UMOMessageDispatcherFactory getDispatcherFactory()
    {
        return connector.getDispatcherFactory();
    }

    public void startConnector() throws UMOException
    {
        connector.startConnector();
    }

    public void stopConnector() throws UMOException
    {
        connector.stopConnector();
    }

    public void dispose()
    {
        connector.dispose();
    }

    public void initialise() throws InitialisationException
    {
        connector.initialise();
    }
}
