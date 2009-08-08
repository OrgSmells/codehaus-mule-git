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

import org.mule.api.MuleException;
import org.mule.api.endpoint.EndpointURI;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.transport.AbstractConnector;

import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

/**
 * <code>XmppConnector</code> TODO
 */
public class XmppConnector extends AbstractConnector
{

    public static final String XMPP = "xmpp";
    public static final String XMPP_PROPERTY_PREFIX = "";
    public static final String XMPP_RESOURCE = XMPP_PROPERTY_PREFIX + "resource";
    public static final String XMPP_SUBJECT = XMPP_PROPERTY_PREFIX + "subject";
    public static final String XMPP_THREAD = XMPP_PROPERTY_PREFIX + "thread";
    public static final String XMPP_TO = XMPP_PROPERTY_PREFIX + "to";
    public static final String XMPP_FROM = XMPP_PROPERTY_PREFIX + "from";
    public static final String XMPP_GROUP_CHAT = XMPP_PROPERTY_PREFIX + "groupChat";
    public static final String XMPP_NICKNAME = XMPP_PROPERTY_PREFIX + "nickname";


    protected void doInitialise() throws InitialisationException
    {
        // template method, nothing to do
    }

    protected void doDispose()
    {
        // template method
    }

    protected void doConnect() throws Exception
    {
        // template method
    }

    protected void doDisconnect() throws Exception
    {
        // template method
    }

    protected void doStart() throws MuleException
    {
        // template method
    }

    protected void doStop() throws MuleException
    {
        // template method
    }

    public String getProtocol()
    {
        return XMPP;
    }

    public XMPPConnection createXmppConnection(EndpointURI endpointURI) throws XMPPException
    {
        logger.info("Trying to find XMPP connection for uri: " + endpointURI);

        String username = endpointURI.getUser();
        String hostname = endpointURI.getHost();
        String password = endpointURI.getPassword();
        String resource = (String)endpointURI.getParams().get("resource");

        XMPPConnection xmppConnection = this.doCreateXmppConnection(endpointURI);

        if (!xmppConnection.isAuthenticated())
        {
            // Make sure we have an account. If we don't, make one.
            try
            {
                AccountManager accManager = new AccountManager(xmppConnection);
                accManager.createAccount(username, password);
            }
            catch (XMPPException ex)
            {
                // User probably already exists, throw away...
                logger.info("*** account (" + username + ") already exists ***");
            }

            if (logger.isDebugEnabled())
            {
                logger.debug("Logging in as: " + username);
                logger.debug("pw is        : " + password);
                logger.debug("server       : " + hostname);
                logger.debug("resource     : " + resource);
            }

            if (resource == null)
            {
                xmppConnection.login(username, password);
            }
            else
            {
                xmppConnection.login(username, password, resource);
            }
        }
        else
        {
            if (logger.isDebugEnabled())
            {
                logger.debug("Already authenticated on this connection, no need to log in again.");
            }
        }
        return xmppConnection;
    }

    /**
     * This method creates and returns the {@link XMPPConnection} object that's uses to talk to
     * the Jabber server.
     * <p/>
     * Subclasses can override this method to create a more specialized {@link XMPPConnection}.
     * 
     * @param endpointURI
     * @throws XMPPException
     */
    protected XMPPConnection doCreateXmppConnection(EndpointURI endpointURI) throws XMPPException
    {
        XMPPConnection xmppConnection = null;
        
        if (endpointURI.getPort() != -1)
        {
            xmppConnection = new XMPPConnection(endpointURI.getHost(), endpointURI.getPort());
        }
        else
        {
            xmppConnection = new XMPPConnection(endpointURI.getHost());
        }
        
        return xmppConnection;
    }

    public boolean isResponseEnabled()
    {
        return true;
    }
}
