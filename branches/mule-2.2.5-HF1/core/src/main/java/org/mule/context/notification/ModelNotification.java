/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.context.notification;

import org.mule.api.context.notification.BlockingServerEvent;
import org.mule.api.context.notification.ServerNotification;
import org.mule.api.model.Model;

/**
 * <code>ModelNotification</code> is fired when an event such as the model starting
 * occurs. The payload of this event will always be a reference to the model.
 * 
 * @see org.mule.api.model.Model
 */
public class ModelNotification extends ServerNotification implements BlockingServerEvent
{
    /**
     * Serial version
     */
    private static final long serialVersionUID = -1954880336427554435L;

    public static final int MODEL_INITIALISING = MODEL_EVENT_ACTION_START_RANGE + 1;
    public static final int MODEL_INITIALISED = MODEL_EVENT_ACTION_START_RANGE + 2;
    public static final int MODEL_INITIALISING_LISTENERS = MODEL_EVENT_ACTION_START_RANGE + 3;
    public static final int MODEL_INITIALISED_LISTENERS = MODEL_EVENT_ACTION_START_RANGE + 4;
    public static final int MODEL_STARTING = MODEL_EVENT_ACTION_START_RANGE + 5;
    public static final int MODEL_STARTED = MODEL_EVENT_ACTION_START_RANGE + 6;
    public static final int MODEL_STOPPING = MODEL_EVENT_ACTION_START_RANGE + 7;
    public static final int MODEL_STOPPED = MODEL_EVENT_ACTION_START_RANGE + 8;
    public static final int MODEL_DISPOSING = MODEL_EVENT_ACTION_START_RANGE + 9;
    public static final int MODEL_DISPOSED = MODEL_EVENT_ACTION_START_RANGE + 10;

    static {
        registerAction("model initialising", MODEL_INITIALISING);
        registerAction("model initialised", MODEL_INITIALISED);
        registerAction("model initialising listener", MODEL_INITIALISING_LISTENERS);
        registerAction("model initialised listener", MODEL_INITIALISED_LISTENERS);
        registerAction("model starting", MODEL_STARTING);
        registerAction("model started", MODEL_STARTED);
        registerAction("model stopping", MODEL_STOPPING);
        registerAction("model stopped", MODEL_STOPPED);
        registerAction("model disposing", MODEL_DISPOSING);
        registerAction("model disposed", MODEL_DISPOSED);
    }

    public ModelNotification(Model model, int action)
    {
        super(model, action);
        resourceIdentifier = model.getName();
    }

    @Override
    protected String getPayloadToString()
    {
        return ((Model) source).getName();
    }
}
