/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.impl.endpoint;

import org.mule.umo.endpoint.UMOImmutableEndpoint;

public class InboundEndpoint extends MuleEndpoint
{

    private static final long serialVersionUID = -4752772777414636142L;

    public InboundEndpoint()
    {
        super();
    }

    public String getType()
    {
        return UMOImmutableEndpoint.ENDPOINT_TYPE_RECEIVER;
    }
    
}