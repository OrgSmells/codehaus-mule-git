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
package org.mule.providers.file;

import org.mule.impl.endpoint.AbstractEndpointBuilder;
import org.mule.umo.endpoint.MalformedEndpointException;

import java.net.URI;
import java.util.Properties;

/**
 * <code>FileEndpointBuilder</code> File uris need some special processing because
 * the uri path can be any length, and the default resolver relies on a particular path
 * format
 *
 * @author <a href="mailto:ross.mason@cubis.co.uk">Ross Mason</a>
 * @version $Revision$
 */

public class FileEndpointBuilder extends AbstractEndpointBuilder
{
    protected void setEndpoint(URI uri, Properties props) throws MalformedEndpointException
    {
         address = uri.getSchemeSpecificPart();
        if(address.startsWith("///")) {
            address = address.substring(2);
        }
        if(address.startsWith("//")) {
            address = address.substring(1);
        }
        int i = address.indexOf("?");
        if(i > -1) {
            address = address.substring(0, i);
        }

    }
}
