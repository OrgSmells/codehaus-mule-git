/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.message;

import org.mule.api.MuleMessage;
import org.mule.api.transport.MessageAdapter;
import org.mule.api.transport.PropertyScope;
import org.mule.transport.DefaultMessageAdapter;

import java.io.Serializable;
import java.util.Iterator;

/**
 * A data transfer object representation of a {@link org.mule.api.MuleMessage}. THis object is used when ecoding Mule messages
 * over the wire using XML, JSON or other serialization.
 */
public class DefaultMuleMessageDTO extends BaseMessageDTO
{
    private String replyTo;

    public DefaultMuleMessageDTO()
    {
        super();
    }

    public DefaultMuleMessageDTO(Serializable message)
    {
        super(message);
    }

    public DefaultMuleMessageDTO(MuleMessage message)
    {
        super((Serializable) message.getPayload());
        encodePropertiesForScope(PropertyScope.INBOUND, message);
        encodePropertiesForScope(PropertyScope.OUTBOUND, message);
        encodePropertiesForScope(PropertyScope.INVOCATION, message);
        encodePropertiesForScope(PropertyScope.SESSION, message);
        if(message.getReplyTo()!=null)
        {
            setReplyTo(message.getReplyTo().toString());
        }
    }

    protected void encodePropertiesForScope(PropertyScope scope, MuleMessage message)
    {
        for (Iterator iterator = message.getPropertyNames(scope).iterator(); iterator.hasNext();)
        {
            String key = (String)iterator.next();
            setProperty(scope.getScopeName() + "#" + key, message.getProperty(key));
        }
    }

    public String getReplyTo()
    {
        return replyTo;
    }

    public void setReplyTo(String replyTo)
    {
        this.replyTo = replyTo;
    }

    public Object getData()
    {
        return getPayload();
    }

    public void setData(Object data)
    {
        this.setPayload(data);
    }

    public void addPropertiesTo(MessageAdapter message)
    {
        String prefix;
        int i;
        for (String s : properties.keySet())
        {
            i = s.indexOf("#");
            prefix = s.substring(0, i);
            if(prefix.equals(PropertyScope.INBOUND.getScopeName()))
            {
                message.setProperty(s.substring(i+1), getProperty(s), PropertyScope.INBOUND);
            }
            else if(prefix.equals(PropertyScope.OUTBOUND.getScopeName()))
            {
                message.setProperty(s.substring(i+1), getProperty(s), PropertyScope.OUTBOUND);
            }
            else if(prefix.equals(PropertyScope.SESSION.getScopeName()))
            {
                message.setProperty(s.substring(i+1), getProperty(s), PropertyScope.SESSION);
            }
            else
            {
                message.setProperty(s.substring(i+1), getProperty(s), PropertyScope.INVOCATION);
            }
        }
        message.setReplyTo(getReplyTo());
    }

    public MessageAdapter toMessageAdapter()
    {
        MessageAdapter message = new DefaultMessageAdapter(getPayload());
        addPropertiesTo(message);
        return message;
    }
}
