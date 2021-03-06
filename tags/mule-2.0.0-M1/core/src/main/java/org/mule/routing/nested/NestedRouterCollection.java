/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.routing.nested;

import org.mule.management.stats.RouterStatistics;
import org.mule.routing.AbstractRouterCollection;
import org.mule.umo.routing.UMONestedRouterCollection;

/**
 * TODO
 */
public class NestedRouterCollection extends AbstractRouterCollection implements UMONestedRouterCollection
{

    public NestedRouterCollection()
    {
        super(RouterStatistics.TYPE_INBOUND);
    }

}
