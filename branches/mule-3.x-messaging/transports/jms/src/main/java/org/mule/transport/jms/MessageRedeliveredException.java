/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.jms;

import org.mule.api.MessagingException;
import org.mule.api.MuleMessage;
import org.mule.config.i18n.Message;
import org.mule.transport.jms.i18n.JmsMessages;

public class MessageRedeliveredException extends MessagingException
{
    /**
     * Serial version
     */
    private static final long serialVersionUID = 9013890402770563931L;

    public MessageRedeliveredException(MuleMessage muleMessage)
    {
        super(JmsMessages.messageMarkedForRedelivery(muleMessage), muleMessage);
    }

    public MessageRedeliveredException(Message message, MuleMessage muleMessage)
    {
        super(message.setNextMessage(JmsMessages.messageMarkedForRedelivery(muleMessage)), muleMessage);
    }
}
