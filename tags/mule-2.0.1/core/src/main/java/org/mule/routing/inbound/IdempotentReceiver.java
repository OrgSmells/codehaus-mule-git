/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.routing.inbound;

import org.mule.api.MessagingException;
import org.mule.api.MuleEvent;
import org.mule.api.routing.IdempotentMessageIdStore;
import org.mule.api.routing.RoutingException;
import org.mule.config.i18n.CoreMessages;

/**
 * <code>IdempotentReceiver</code> ensures that only unique messages are received by a
 * service. It does this by checking the unique ID of the incoming message. Note that
 * the underlying endpoint must support unique message IDs for this to work, otherwise a
 * <code>UniqueIdNotSupportedException</code> is thrown.<br>
 * By default this implementation uses an instance of
 * {@link IdempotentInMemoryMessageIdStore}.
 */
public class IdempotentReceiver extends SelectiveConsumer
{
    protected volatile IdempotentMessageIdStore idStore;
    protected volatile String assignedComponentName;

    // The maximum number of messages to keep in the store; exact interpretation of this
    // limit is up to the store implementation. By default the store is unbounded.
    protected volatile int maxMessages = -1;

    // The number of seconds each message ID is kept in the store;
    // by default each entry is kept for 5 minutes
    protected volatile int messageTTL = (60 * 5);

    // The number of seconds between expiration intervals;
    // by default we expire every minute
    protected volatile int expirationInterval = 60;

    public IdempotentReceiver()
    {
        super();
    }

    public int getMaxMessages()
    {
        return maxMessages;
    }

    public void setMaxMessages(int maxMessages)
    {
        this.maxMessages = maxMessages;
    }

    public int getMessageTTL()
    {
        return messageTTL;
    }

    public void setMessageTTL(int messageTTL)
    {
        this.messageTTL = messageTTL;
    }

    public int getExpirationInterval()
    {
        return expirationInterval;
    }

    public void setExpirationInterval(int expirationInterval)
    {
        if (expirationInterval <= 0)
        {
            throw new IllegalArgumentException(CoreMessages.propertyHasInvalidValue("expirationInterval",
                new Integer(expirationInterval)).toString());
        }

        this.expirationInterval = expirationInterval;
    }

    protected void initialize(MuleEvent event) throws RoutingException
    {
        if (assignedComponentName == null && idStore == null)
        {
            this.assignedComponentName = event.getService().getName();
            this.idStore = this.createMessageIdStore();
        }
    }

    protected IdempotentMessageIdStore createMessageIdStore()
    {
        return new IdempotentInMemoryMessageIdStore(assignedComponentName, maxMessages, messageTTL,
            expirationInterval);
    }

    // @Override
    public boolean isMatch(MuleEvent event) throws MessagingException
    {
        if (idStore == null)
        {
            // we need to load this on the first request as we need the service name
            synchronized (this)
            {
                this.initialize(event);
            }
        }

        try
        {
            return !idStore.containsId(this.getIdForEvent(event));
        }
        catch (Exception ex)
        {
            throw new RoutingException(event.getMessage(), event.getEndpoint(), ex);
        }
    }

    // @Override
    public MuleEvent[] process(MuleEvent event) throws MessagingException
    {
        String eventComponentName = event.getService().getName();
        if (!assignedComponentName.equals(eventComponentName))
        {
            IllegalArgumentException iex = new IllegalArgumentException(
                "This receiver is assigned to service: " + assignedComponentName
                                + " but has received an event for service: " + eventComponentName
                                + ". Please check your config to make sure each service"
                                + "has its own instance of IdempotentReceiver.");
            throw new RoutingException(event.getMessage(), event.getEndpoint(), iex);
        }

        Object id = this.getIdForEvent(event);

        try
        {
            if (idStore.storeId(id))
            {
                return new MuleEvent[]{event};
            }
            else
            {
                return null;
            }
        }
        catch (Exception e)
        {
            throw new RoutingException(CoreMessages.failedToWriteMessageToStore(id, assignedComponentName),
                event.getMessage(), event.getEndpoint(), e);
        }
    }

    protected Object getIdForEvent(MuleEvent event) throws MessagingException
    {
        return event.getMessage().getUniqueId();
    }

}
