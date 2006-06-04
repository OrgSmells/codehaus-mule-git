/*
 * $Id$
 * ------------------------------------------------------------------------------------------------------
 *
 * Copyright (c) SymphonySoft Limited. All rights reserved.
 * http://www.symphonysoft.com
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.mule.components.builder;

import org.mule.config.i18n.Message;
import org.mule.config.i18n.Messages;
import org.mule.umo.MessagingException;
import org.mule.umo.UMOMessage;

/**
 * If thrown by a MEssageBuilder implementation if it cannot build the current message
 * or some other error occurs
 *
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */
public class MessageBuilderException extends MessagingException
{
    public MessageBuilderException(Message message, UMOMessage umoMessage) {
        super(message, umoMessage);
    }

    public MessageBuilderException(Message message, UMOMessage umoMessage, Throwable cause) {
        super(message, umoMessage, cause);
    }


    public MessageBuilderException(UMOMessage umoMessage, Throwable cause) {
        super(new Message(Messages.FAILED_TO_BUILD_MESSAGE), umoMessage, cause);
    }
}
