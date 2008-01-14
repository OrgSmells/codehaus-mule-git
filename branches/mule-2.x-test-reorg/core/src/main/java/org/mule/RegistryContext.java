/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule;

import org.mule.config.MuleConfiguration;
import org.mule.impl.registry.TransientRegistry;
import org.mule.registry.Registry;

/** 
 * A handle to the Mule Registry.  We should make no assumptions about the location of the actual Registry
 * implementation.  It might be simply a singleton object in the same JVM, or it might be in another JVM or 
 * even running remotely on another machine.
 */
public class RegistryContext
{
    protected static Registry registry;
    
    public static Registry getRegistry()
    {
        return registry;
    }

    public static synchronized void setRegistry(Registry registry)
    {
        RegistryContext.registry = registry;
    }

    public static MuleConfiguration getConfiguration()
    {
        // TODO Migrate uses to obtain configuration from ManagementContext
        return MuleServer.getManagementContext().getConfiguration();
    }

    public static Registry getOrCreateRegistry()
    {
        if(registry==null || registry.isDisposed())
        {
            registry = new TransientRegistry();
        }
        return registry;
    }
}
