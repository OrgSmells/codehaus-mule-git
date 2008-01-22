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

import org.mule.api.MuleMessage;
import org.mule.api.component.Component;
import org.mule.api.component.ComponentException;
import org.mule.config.i18n.Message;

/**
 * <code>FailedToQueueEventException</code> is thrown when an event cannot be put
 * on an internal component queue.
 */

public class FailedToQueueEventException extends ComponentException
{
    /**
     * Serial version
     */
    private static final long serialVersionUID = -8368283988424746098L;

    public FailedToQueueEventException(Message message, MuleMessage umoMessage, Component component)
    {
        super(message, umoMessage, component);
    }

    public FailedToQueueEventException(Message message,
                                       MuleMessage umoMessage,
                                       Component component,
                                       Throwable cause)
    {
        super(message, umoMessage, component, cause);
    }

    public FailedToQueueEventException(MuleMessage umoMessage, Component component, Throwable cause)
    {
        super(umoMessage, component, cause);
    }
}
