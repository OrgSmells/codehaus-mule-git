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
package org.mule.routing;

import org.mule.impl.MuleEvent;
import org.mule.umo.UMOEvent;
import org.mule.umo.UMOMessage;
import org.mule.umo.UMOSession;
import org.mule.umo.provider.UMOMessageDispatcher;
import org.mule.umo.routing.RoutingException;
import org.mule.umo.routing.ComponentRoutingException;
import org.mule.config.i18n.Message;
import org.mule.config.i18n.Messages;

/**
 * <code>ForwardingCatchAllStrategy</code> acts as a catch and forward router for any events
 * not caught by the router this strategy is associated with.  Users can assign an endpoint to this strategy to
 * forward all events to.  This can be used as a dead letter/error queue.
 *
 * @author <a href="mailto:ross.mason@cubis.co.uk">Ross Mason</a>
 * @version $Revision$
 */

public class ForwardingCatchAllStrategy extends AbstractCatchAllStrategy
{
    private boolean sendTransformed = true;

    public UMOMessage catchMessage(UMOMessage message, UMOSession session, boolean synchronous) throws RoutingException
    {
        if(getEndpoint()==null) {
            throw new ComponentRoutingException(new Message(Messages.NO_CATCH_ALL_ENDPOINT_SET), message, getEndpoint(), session.getComponent());
        }
        try
        {
            UMOMessageDispatcher dispatcher = getEndpoint().getConnector().getDispatcher(getEndpoint().getEndpointURI().getAddress());
            UMOEvent newEvent = new MuleEvent(message, getEndpoint(), session, synchronous);

            if(synchronous) {
                UMOMessage result = dispatcher.send(newEvent);
                if(statistics!=null) statistics.incrementRoutedMessage(getEndpoint());
                return result;
            } else {
                dispatcher.dispatch(newEvent);
                if(statistics!=null) statistics.incrementRoutedMessage(getEndpoint());
                return null;
            }
        } catch (Exception e)
        {
            throw new RoutingException(message, getEndpoint(), e);

        }
    }

    public boolean isSendTransformed()
    {
        return sendTransformed;
    }

    public void setSendTransformed(boolean sendTransformed)
    {
        this.sendTransformed = sendTransformed;
    }
}
