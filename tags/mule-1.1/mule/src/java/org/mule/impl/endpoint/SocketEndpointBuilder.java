/*
 * $Header$
 * $Revision$
 * $Date$
 * ------------------------------------------------------------------------------------------------------
 *
 * Copyright (c) SymphonySoft Limited. All rights reserved.
 * http://www.symphonysoft.com
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.mule.impl.endpoint;

import java.net.URI;
import java.util.Properties;

import org.mule.umo.endpoint.MalformedEndpointException;

/**
 * <code>SocketEndpointBuilder</code> builds an endpointUri based on host and
 * port only
 * 
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */
public class SocketEndpointBuilder extends AbstractEndpointBuilder
{
    protected void setEndpoint(URI uri, Properties props) throws MalformedEndpointException
    {
        // set the endpointUri to be a proper url if host and port are set
        if (uri.getPort() == -1) {
            // try the form tcp://6666
            try {
                int port = Integer.parseInt(uri.getHost());
                this.address = uri.getScheme() + "://localhost:" + port;
            } catch (NumberFormatException e) {
                // ignore
            }
        }

        if (address == null) {
            this.address = uri.getScheme() + "://" + uri.getHost();
            if (uri.getPort() != -1) {
                this.address += ":" + uri.getPort();
            }
        }
    }
}
