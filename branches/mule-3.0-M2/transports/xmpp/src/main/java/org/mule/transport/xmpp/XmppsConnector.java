/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.xmpp;

import org.mule.api.endpoint.EndpointURI;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

public class XmppsConnector extends XmppConnector
{

    public static final String XMPPS = "xmpps";

    public String getProtocol()
    {
        return XMPPS;
    }

    /**
     * This method creates a {@link SSLXMPPConnection} to allow secure communication to the Jabber
     * server.
     */
    protected XMPPConnection doCreateXmppConnection(EndpointURI endpointURI) throws XMPPException
    {
        XMPPConnection xmppConnection = null;
        
        if (endpointURI.getPort() != -1)
        {
            // TODO xmpp: the XMPPConnection can handle SSL connections now
//            xmppConnection = new SSLXMPPConnection(endpointURI.getHost(), endpointURI.getPort());
        }
        else
        {
//            xmppConnection = new SSLXMPPConnection(endpointURI.getHost());
        }
        
        return xmppConnection;
    }
    
}
