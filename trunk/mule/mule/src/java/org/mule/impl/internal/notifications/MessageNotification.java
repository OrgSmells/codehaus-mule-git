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
package org.mule.impl.internal.notifications;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.impl.MuleMessage;
import org.mule.umo.UMOMessage;
import org.mule.umo.endpoint.UMOImmutableEndpoint;
import org.mule.umo.manager.UMOServerNotification;
import org.mule.umo.provider.UMOConnectable;

/**
 * These notifications are fire when either a message is received via an endpoint, or dispatcher of if
 * a receive call is made on a dispatcher.
 * 
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */
public class MessageNotification extends UMOServerNotification
{
    /**
     * logger used by this class
     */
    protected static transient Log logger = LogFactory.getLog(MessageNotification.class);

    public static final int MESSAGE_RECEIVED = MESSAGE_EVENT_ACTION_START_RANGE + 1;
    public static final int MESSAGE_DISPATCHED = MESSAGE_EVENT_ACTION_START_RANGE + 2;
    public static final int MESSAGE_SENT = MESSAGE_EVENT_ACTION_START_RANGE + 3;
    public static final int MESSAGE_REQUESTED = MESSAGE_EVENT_ACTION_START_RANGE + 4;

    private static final transient String[] ACTIONS = new String[] { "received", "dispatched", "sent", "requested" };

    private UMOImmutableEndpoint endpoint;

    public MessageNotification(UMOMessage resource, UMOImmutableEndpoint endpoint, String identifier, int action)
    {
        super(cloneMessage(resource), action);
        resourceIdentifier = identifier;
        this.endpoint = endpoint;

    }

    protected static UMOMessage cloneMessage(UMOMessage message) {
        //Todo we probably need to support deep cloning here
        Object clonePayload = message.getPayload();
        UMOMessage clone = new MuleMessage(clonePayload, message);
        return clone;
    }

    protected String getPayloadToString()
    {
        if (source instanceof UMOConnectable) {
            return ((UMOConnectable) source).getConnectionDescription();
        }
        return source.toString();
    }

    protected String getActionName(int action)
    {
        int i = action - MESSAGE_EVENT_ACTION_START_RANGE;
        if (i - 1 > ACTIONS.length) {
            return String.valueOf(action);
        }
        return ACTIONS[i - 1];
    }

    public String toString()
    {
        return EVENT_NAME + "{action=" + getActionName(action) + ", endpoint: " + endpoint.getEndpointURI()
                + ", resourceId=" + resourceIdentifier + ", timestamp=" + timestamp +  ", serverId=" + serverId + ", message: " + source + "}";
    }

    public UMOImmutableEndpoint getEndpoint() {
        return endpoint;
    }

}
