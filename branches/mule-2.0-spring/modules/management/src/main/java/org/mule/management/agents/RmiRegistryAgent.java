/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.management.agents;

import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.umo.UMOException;
import org.mule.umo.lifecycle.InitialisationException;
import org.mule.umo.manager.UMOAgent;

/**
 * Binds to an existing RMI registry or creates a new one on a defined URI. The
 * default is <code>rmi://localhost:1099</code>
 */
public class RmiRegistryAgent implements UMOAgent
{
    /**
     * logger used by this class
     */
    protected transient Log logger = LogFactory.getLog(getClass());

    public static final String DEFAULT_SERVER_URI = "rmi://localhost:1099";
    private String name = "RMI Agent";
    private Registry rmiRegistry;
    private String serverUri = DEFAULT_SERVER_URI;
    private boolean createRegistry = true;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return "Rmi Registry: " + serverUri;
    }

    public void registered()
    {
        // nothing to do
    }

    public void unregistered()
    {
        // nothing to do
    }

    public void start() throws UMOException
    {
        URI uri = null;

        try
        {
            uri = new URI(serverUri);
        }
        catch (URISyntaxException e)
        {
            throw new InitialisationException(e, this);
        }

        if (rmiRegistry == null)
        {
            try
            {
                if (createRegistry)
                {
                    try
                    {
                        rmiRegistry = LocateRegistry.createRegistry(uri.getPort());
                    }
                    catch (ExportException e)
                    {
                        logger.info("Registry on " + serverUri
                                    + " already bound. Attempting to use that instead");
                        rmiRegistry = LocateRegistry.getRegistry(uri.getHost(), uri.getPort());
                    }
                }
                else
                {
                    rmiRegistry = LocateRegistry.getRegistry(uri.getHost(), uri.getPort());
                }
            }
            catch (RemoteException e)
            {
                throw new InitialisationException(e, this);
            }
        }
    }

    public void stop() throws UMOException
    {
        // TODO how do you unbind a registry??
        rmiRegistry = null;
    }

    public void dispose()
    {
        // nothing to do
    }

    public void initialise() throws InitialisationException {
        // nothing to do
    }

    public Registry getRmiRegistry()
    {
        return rmiRegistry;
    }

    public void setRmiRegistry(Registry rmiRegistry)
    {
        this.rmiRegistry = rmiRegistry;
    }

    public String getServerUri()
    {
        return serverUri;
    }

    public void setServerUri(String serverUri)
    {
        this.serverUri = serverUri;
    }

    public boolean isCreateRegistry()
    {
        return createRegistry;
    }

    public void setCreateRegistry(boolean createRegistry)
    {
        this.createRegistry = createRegistry;
    }
}
