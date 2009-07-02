/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.api.transport;

import org.mule.api.MuleException;
import org.mule.config.i18n.CoreMessages;

/**
 * <code>MessageTypeNotSupportedException</code> is thrown when a message payload
 * is set on a Message implementation of MessageAdapter which is not of supported
 * type for that message or adapter.
 */

public class MessageTypeNotSupportedException extends MuleException
{
    /**
     * Serial version
     */
    private static final long serialVersionUID = -3954838511333933643L;

    public MessageTypeNotSupportedException(Object message, Class adapterClass)
    {
        super(CoreMessages.messageNotSupportedByAdapter(
            (message != null ? message.getClass().getName() : "null"),
            (adapterClass != null ? adapterClass.getName() : "null class")));
    }

    public MessageTypeNotSupportedException(Object message, Class adapterClass, Throwable cause)
    {
        super(CoreMessages.messageNotSupportedByAdapter(
            (message != null ? message.getClass().getName() : "null"), 
            (adapterClass != null ? adapterClass.getName() : "null class")), cause);
    }
}
