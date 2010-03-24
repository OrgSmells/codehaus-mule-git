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

import org.mule.api.context.notification.ServerNotification;

public class ExceptionNotification extends ServerNotification
{
    /**
     * Serial version.
     */
    private static final long serialVersionUID = -43091546451476239L;
    public static final int EXCEPTION_ACTION = EXCEPTION_EVENT_ACTION_START_RANGE + 1;

    static
    {
        registerAction("exception", EXCEPTION_ACTION);
    }

    private Throwable exception;

    public ExceptionNotification(Throwable exception)
    {
        super(exception, EXCEPTION_ACTION);
        this.exception = exception;
    }

    public Throwable getException()
    {
        return this.exception;
    }

    @Override
    public String getType()
    {
        return TYPE_ERROR;
    }
}
