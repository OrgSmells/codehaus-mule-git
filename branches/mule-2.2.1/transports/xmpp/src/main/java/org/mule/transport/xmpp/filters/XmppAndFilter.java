/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.xmpp.filters;

import static org.mule.util.ClassUtils.equal;
import static org.mule.util.ClassUtils.hash;

import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.PacketFilter;

/**
 * <code>XmppAndFilter</code> an Xmpp AND filter
 */
public class XmppAndFilter extends AbstractXmppFilter
{
    private volatile PacketFilter leftFilter;
    private volatile PacketFilter rightFilter;

    public XmppAndFilter()
    {
        super();
    }

    public XmppAndFilter(PacketFilter left, PacketFilter right)
    {
        this.leftFilter = left;
        this.rightFilter = right;
    }

    public void setLeftFilter(PacketFilter left)
    {
        this.leftFilter = left;
    }

    public void setRightFilter(PacketFilter right)
    {
        this.leftFilter = right;
    }

    public PacketFilter getLeftFilter()
    {
        return leftFilter;
    }

    public PacketFilter getRightFilter()
    {
        return rightFilter;
    }

    protected PacketFilter createFilter()
    {
        return new AndFilter(leftFilter, rightFilter);
    }

    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        final XmppAndFilter other = (XmppAndFilter) obj;
        return equal(leftFilter, other.leftFilter)
            && equal(rightFilter, other.rightFilter);
    }

    public int hashCode()
    {
        return hash(new Object[]{this.getClass(), leftFilter, rightFilter});
    }
}
