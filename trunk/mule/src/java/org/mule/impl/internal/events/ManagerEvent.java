/*
 * $Header$
 * $Revision$
 * $Date$
 * ------------------------------------------------------------------------------------------------------
 *
 * Copyright (c) Cubis Limited. All rights reserved.
 * http://www.cubis.co.uk
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.mule.impl.internal.events;

import org.mule.MuleManager;
import org.mule.umo.UMOServerEvent;

/**
 * <code>ManagerEvent</code> is fired when an event such as the manager starting
 * occurs.  The payload of this event will always be a reference to the manager.
 *
 * @see org.mule.MuleManager
 * @see org.mule.umo.UMOManager
 * @author <a href="mailto:ross.mason@cubis.co.uk">Ross Mason</a>
 * @version $Revision$
 */
public class ManagerEvent extends UMOServerEvent implements BlockingServerEvent
{
    public static final int MANAGER_INITIALISNG = MANAGER_EVENT_ACTION_START_RANGE + 1;
    public static final int MANAGER_INITIALISED = MANAGER_EVENT_ACTION_START_RANGE + 2;
    public static final int MANAGER_STARTING = MANAGER_EVENT_ACTION_START_RANGE + 3;
    public static final int MANAGER_STARTED = MANAGER_EVENT_ACTION_START_RANGE + 4;
    public static final int MANAGER_STOPPING = MANAGER_EVENT_ACTION_START_RANGE + 5;
    public static final int MANAGER_STOPPED = MANAGER_EVENT_ACTION_START_RANGE + 6;
    public static final int MANAGER_DISPOSING = MANAGER_EVENT_ACTION_START_RANGE + 7;
    public static final int MANAGER_DISPOSED = MANAGER_EVENT_ACTION_START_RANGE + 8;
    public static final int MANAGER_DISPOSING_CONNECTORS = MANAGER_EVENT_ACTION_START_RANGE + 9;
    public static final int MANAGER_DISPOSED_CONNECTORS = MANAGER_EVENT_ACTION_START_RANGE + 10;

    private String[] actions = new String[]{
        "initialising","initialised","starting","started","stopping","stopped",
        "disposing","disposed","disposing connectors", "disposed connectors"};

    public ManagerEvent(Object message, int action)
    {
        super(message, action);
    }

    protected String getPayloadToString()
    {
        return ((MuleManager)source).getId();
    }

    protected String getActionName(int action)
    {
        int i = action - MANAGER_EVENT_ACTION_START_RANGE;
        if(i-1 > actions.length) return String.valueOf(action);
        return actions[i-1];
    }
}
