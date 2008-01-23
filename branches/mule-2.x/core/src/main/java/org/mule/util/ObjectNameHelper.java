/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.util;

import org.mule.RegistryContext;
import org.mule.api.endpoint.EndpointURI;
import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.api.transport.Connector;

/**
 * Generates consistent objects names for Mule components
 */
// @ThreadSafe
public final class ObjectNameHelper
{
    public static final String SEPARATOR = ".";
    //public static final char HASH = '#';
    public static final String CONNECTOR_PREFIX = "connector";
    public static final String ENDPOINT_PREFIX = "endpoint";

    /** Do not instanciate. */
    protected ObjectNameHelper ()
    {
        // no-op
    }

    public static String getEndpointName(ImmutableEndpoint endpoint)
    {
        String name = endpoint.getName();
        final EndpointURI endpointUri = endpoint.getEndpointURI();
        if (name != null)
        {
            // If the name is the same as the address, we need to add the scheme
            if (name.equals(endpointUri.getAddress()))
            {
                name = endpointUri.getScheme() + SEPARATOR + name;
            }
            name = replaceObjectNameChars(name);
            // This causes a stack overflow because we call lookup endpoint
            // Which causes a clone of the endpoint which in turn valudates the
            // endpoint name with this method
            return name;
            // return ensureUniqueEndpoint(name);

        }
        else
        {
            String address = endpointUri.getAddress();
            if (StringUtils.isBlank(address))
            {
                // for some endpoints in TCK like test://xxx
                address = endpointUri.toString();
            }
            // Make sure we include the endpoint scheme in the name
            address = (address.indexOf(":/") > -1 ? address : endpointUri.getScheme()
                            + SEPARATOR + address);
            name = ENDPOINT_PREFIX + SEPARATOR + replaceObjectNameChars(address);

            return ensureUniqueEndpoint(name);
        }
    }

    protected static String ensureUniqueEndpoint(String name)
    {
        int i = 0;
        String tempName = name;
        // Check that the generated name does not conflict with an existing global
        // endpoint.
        // We can't check local edpoints right now but the chances of conflict are
        // very small and will be
        // reported during JMX object registration
        while (RegistryContext.getRegistry().lookupEndpoint(tempName) != null)
        {
            i++;
            tempName = name + SEPARATOR + i;
        }
        return tempName;
    }

    protected static String ensureUniqueConnector(String name)
    {
        int i = 0;
        String tempName = name;
        // Check that the generated name does not conflict with an existing global
        // endpoint.
        // We can't check local edpoints right now but the chances of conflict are
        // very small and will be
        // reported during JMX object registration
        try
        {
            while (RegistryContext.getRegistry().lookupConnector(tempName) != null)
            {
                i++;
                tempName = name + SEPARATOR + i;
            }
        }
        catch (Exception e)
        {
            //ignore
        }
        return tempName;
    }

    public static String getConnectorName(Connector connector)
    {
        if (connector.getName() != null && connector.getName().indexOf('#') == -1)
        {
            String name = replaceObjectNameChars(connector.getName());
            return ensureUniqueConnector(name);
        }
        else
        {
            int i = 0;
            String name = CONNECTOR_PREFIX + SEPARATOR + connector.getProtocol() + SEPARATOR + i;
            return ensureUniqueConnector(name);
        }
    }

    public static String replaceObjectNameChars(String name)
    {
        String value = name.replaceAll("//", SEPARATOR);
        value = value.replaceAll("\\p{Punct}", SEPARATOR);
        value = value.replaceAll("\\" + SEPARATOR + "{2,}", SEPARATOR);
        if (value.endsWith(SEPARATOR))
        {
            value = value.substring(0, value.length() - 1);
        }
        return value;
    }

}
