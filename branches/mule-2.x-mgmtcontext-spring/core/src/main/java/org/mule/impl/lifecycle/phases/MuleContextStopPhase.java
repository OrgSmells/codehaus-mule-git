/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.impl.lifecycle.phases;

import org.mule.api.MuleContext;
import org.mule.impl.internal.notifications.ManagerNotification;
import org.mule.impl.lifecycle.LifecyclePhase;
import org.mule.impl.lifecycle.NotificationLifecycleObject;
import org.mule.registry.Registry;
import org.mule.umo.UMOComponent;
import org.mule.umo.lifecycle.Initialisable;
import org.mule.umo.lifecycle.Startable;
import org.mule.umo.lifecycle.Stoppable;
import org.mule.umo.manager.UMOAgent;
import org.mule.umo.model.UMOModel;
import org.mule.umo.provider.UMOConnector;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The Stop phase for the Management context LifecycleManager. Calling {@link org.mule.api.MuleContext#stop()}
 * with initiate this phase via the {@link org.mule.umo.lifecycle.UMOLifecycleManager}.
 * This phase controls the order in which objects should be stopped.
 *
 * @see org.mule.api.MuleContext
 * @see org.mule.umo.lifecycle.UMOLifecycleManager
 * @see org.mule.umo.lifecycle.Stoppable
 */
public class MuleContextStopPhase extends LifecyclePhase
{
    public MuleContextStopPhase()
    {
        this(new Class[]{Registry.class, MuleContext.class});
    }

    public MuleContextStopPhase(Class[] ignorredObjects)
    {
        super(Stoppable.PHASE_NAME, Stoppable.class, Startable.PHASE_NAME);

        Set stopOrderedObjects = new LinkedHashSet();
        stopOrderedObjects.add(new NotificationLifecycleObject(UMOConnector.class));
        stopOrderedObjects.add(new NotificationLifecycleObject(UMOAgent.class));
        stopOrderedObjects.add(new NotificationLifecycleObject(UMOModel.class, ManagerNotification.class,
                ManagerNotification.getActionName(ManagerNotification.MANAGER_STOPPING_MODELS),
                ManagerNotification.getActionName(ManagerNotification.MANAGER_STOPPED_MODELS)));
        stopOrderedObjects.add(new NotificationLifecycleObject(UMOComponent.class));
        stopOrderedObjects.add(new NotificationLifecycleObject(Stoppable.class));

        setIgnorredObjectTypes(ignorredObjects);
        setOrderedLifecycleObjects(stopOrderedObjects);
        registerSupportedPhase(Startable.PHASE_NAME);
        registerSupportedPhase(Initialisable.PHASE_NAME);
    }
}