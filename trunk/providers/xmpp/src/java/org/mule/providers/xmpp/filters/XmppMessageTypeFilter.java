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
package org.mule.providers.xmpp.filters;

import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
/**
 * <code>XmppMessageTypeFilter</code> is an Xmpp MessageTypeFilter
 * adapter.
 *
 * @author <a href="mailto:ross.mason@cubis.co.uk">Ross Mason</a>
 * @version $Revision$
 */
public class XmppMessageTypeFilter extends XmppFromContainsFilter 
{
    public XmppMessageTypeFilter() {
    }

    public XmppMessageTypeFilter(String expression) {
        super(expression);
    }

    protected PacketFilter createFilter() {
        return new org.jivesoftware.smack.filter.MessageTypeFilter(
                Message.Type.fromString(pattern));
    }
}
