/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.impl.lifecycle;

import org.mule.api.MuleContext;
import org.mule.impl.lifecycle.phases.MuleContextStartPhase;
import org.mule.impl.lifecycle.phases.MuleContextStopPhase;
import org.mule.impl.lifecycle.phases.TransientRegistryDisposePhase;
import org.mule.impl.lifecycle.phases.TransientRegistryInitialisePhase;
import org.mule.registry.Registry;
import org.mule.umo.lifecycle.Initialisable;
import org.mule.umo.lifecycle.Startable;
import org.mule.umo.lifecycle.Stoppable;

/**
 * Creates the default Mule lifecycleManager with {@link Initialisable#initialise()}, {@link Startable#start()},
 * {@link Stoppable#stop()} and {@link org.mule.umo.lifecycle.Disposable#dispose()}.
 *
 * @see org.mule.umo.lifecycle.Initialisable
 * @see org.mule.umo.lifecycle.Startable
 * @see org.mule.umo.lifecycle.Stoppable
 * @see org.mule.umo.lifecycle.Disposable
 */
public class DefaultLifecycleManager extends GenericLifecycleManager
{
    public DefaultLifecycleManager()
    {
        //Create Lifecycle phases
        Class[] ignorredObjects = new Class[]{Registry.class, MuleContext.class};

        registerLifecycle(new TransientRegistryInitialisePhase(ignorredObjects));
        registerLifecycle(new MuleContextStartPhase(ignorredObjects));
        registerLifecycle(new MuleContextStopPhase(ignorredObjects));
        registerLifecycle(new TransientRegistryDisposePhase(ignorredObjects));
    }
}
