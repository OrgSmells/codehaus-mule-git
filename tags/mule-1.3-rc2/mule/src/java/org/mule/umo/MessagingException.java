/*
 * $Header$
 * $Revision$
 * $Date$
 * ------------------------------------------------------------------------------------------------------
 *
 * Copyright (c) SymphonySoft Limited. All rights reserved.
 * http://www.symphonysoft.com
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */

package org.mule.umo;

import org.mule.config.i18n.Message;
import org.mule.config.i18n.Messages;
import org.mule.impl.MuleMessage;
import org.mule.impl.RequestContext;
import org.mule.providers.NullPayload;

import java.util.Map;

/**
 * <code>MessagingException</code> is a general message exception thrown when
 * errors specific to Message processing occur.
 * 
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */

public class MessagingException extends UMOException
{
    /**
     * The UMOMessage being processed when the error occurred
     */
    protected transient UMOMessage umoMessage = null;

    public MessagingException(Message message, UMOMessage umoMessage)
    {
        super();
        this.umoMessage = umoMessage;
        setMessage(generateMessage(message));
    }

    public MessagingException(Message message, UMOMessage umoMessage, Throwable cause)
    {
        super(cause);
        this.umoMessage = umoMessage;
        setMessage(generateMessage(message));
    }

    public MessagingException(Message message, Object payload)
    {
        super();
        if (payload == null) {
            this.umoMessage = RequestContext.getEventContext().getMessage();
        }
        else {
            this.umoMessage = new MuleMessage(payload, (Map)null);
        }
        setMessage(generateMessage(message));
    }

    public MessagingException(Message message, Object payload, Throwable cause)
    {
        super(cause);
        if (payload == null) {
            this.umoMessage = RequestContext.getEventContext().getMessage();
        }
        else {
            this.umoMessage = new MuleMessage(payload, (Map)null);
        }
        setMessage(generateMessage(message));
    }

    private String generateMessage(Message message)
    {
        StringBuffer buf = new StringBuffer(80);

        if (message != null) {
            buf.append(message.getMessage()).append(". ");
        }

        if (umoMessage != null) {
            Object payload = umoMessage.getPayload();
            if (payload == null) {
                payload = new NullPayload();
            }

            buf.append(Messages.get(Messages.MESSAGE_IS_OF_TYPE_X, payload.getClass().getName()));
            addInfo("Payload", payload.toString());
        }
        else {
            buf.append("The current UMOMessage is null! Please report this to dev@mule.codehaus.org.");
            addInfo("Payload", new NullPayload().toString());
        }

        return buf.toString();
    }

    public UMOMessage getUmoMessage()
    {
        return umoMessage;
    }

}
