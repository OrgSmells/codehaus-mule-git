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
package org.mule.tck.functional;

import org.mule.impl.internal.notifications.CustomNotification;
import org.mule.umo.UMOEventContext;
import org.mule.umo.transformer.TransformerException;

/**
 * todo document
 *
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */
public class FunctionalTestNotification extends CustomNotification {

    public static final int EVENT_RECEIVED = -999999;
    private Object replyMessage = null;
    private UMOEventContext eventContext;

    public FunctionalTestNotification(UMOEventContext context, Object replyMessage, int action) throws TransformerException {
        super(context.getTransformedMessage(), action);
        resourceIdentifier = context.getComponentDescriptor().getName();
        this.replyMessage = replyMessage;
        this.eventContext = context;
    }

    public Object getReplyMessage() {
        return replyMessage;
    }

    public UMOEventContext getEventContext() {
        return eventContext;
    }
}
