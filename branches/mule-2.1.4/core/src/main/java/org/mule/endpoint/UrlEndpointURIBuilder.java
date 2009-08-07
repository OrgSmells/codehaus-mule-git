/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.endpoint;

import org.mule.api.endpoint.MalformedEndpointException;
import org.mule.util.StringUtils;

import java.net.URI;
import java.util.Properties;

/**
 * <code>UrlEndpointBuilder</code> is the default endpointUri strategy suitable for
 * most connectors
 */

public class UrlEndpointURIBuilder extends AbstractEndpointURIBuilder
{

    protected void setEndpoint(URI uri, Properties props) throws MalformedEndpointException
    {
        address = "";
        if (uri.getHost() != null)
        {
            // set the endpointUri to be a proper url if host and port are set
            this.address = uri.getScheme() + "://" + uri.getHost();
            if (uri.getPort() != -1)
            {
                address += ":" + uri.getPort();
            }
        }
        if (StringUtils.isNotBlank(uri.getPath()))
        {
            address += uri.getPath();
        }

        if (StringUtils.isNotBlank(uri.getQuery()))
        {
            address += "?" + uri.getQuery();
        }
    }
}
