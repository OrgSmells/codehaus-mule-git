/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.impl.security;

import org.mule.MuleManager;
import org.mule.config.i18n.CoreMessages;
import org.mule.umo.UMOEvent;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.endpoint.UMOImmutableEndpoint;
import org.mule.umo.lifecycle.InitialisationException;
import org.mule.umo.security.CryptoFailureException;
import org.mule.umo.security.EncryptionStrategyNotFoundException;
import org.mule.umo.security.SecurityException;
import org.mule.umo.security.SecurityProviderNotFoundException;
import org.mule.umo.security.UMOCredentialsAccessor;
import org.mule.umo.security.UMOEndpointSecurityFilter;
import org.mule.umo.security.UMOSecurityManager;
import org.mule.umo.security.UMOSecurityProvider;
import org.mule.umo.security.UnknownAuthenticationTypeException;
import org.mule.util.StringUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <code>AbstractEndpointSecurityFilter</code> provides basic initialisation for
 * all security filters, namely configuring the SecurityManager for this instance
 */

public abstract class AbstractEndpointSecurityFilter implements UMOEndpointSecurityFilter
{
    /**
     * logger used by this class
     */
    protected transient Log logger = LogFactory.getLog(getClass());

    private UMOSecurityManager securityManager;
    private String securityProviders;
    private UMOImmutableEndpoint endpoint;
    private boolean inbound = false;
    private boolean authenticate;
    private UMOCredentialsAccessor credentialsAccessor;

    public final void initialise() throws InitialisationException
    {
        if (securityManager == null)
        {
            securityManager = MuleManager.getInstance().getSecurityManager();
        }
        if (securityManager == null)
        {
            throw new InitialisationException(CoreMessages.authSecurityManagerNotSet(), this);
        }
        if (endpoint == null)
        {
            throw new InitialisationException(CoreMessages.objectIsNull("Endpoint"), this);
        }
        // This filter may only allow authentication on a subset of registered
        // security providers
        if (securityProviders != null)
        {
            UMOSecurityManager localManager = new MuleSecurityManager();
            String[] sp = StringUtils.splitAndTrim(securityProviders, ",");
            for (int i = 0; i < sp.length; i++)
            {
                UMOSecurityProvider provider = securityManager.getProvider(sp[i]);
                if (provider != null)
                {
                    localManager.addProvider(provider);
                }
                else
                {
                    throw new InitialisationException(
                        CoreMessages.objectNotRegisteredWithManager(
                            "Security Provider '" + sp[i] + "'"), this);
                }
            }
            securityManager = localManager;
        }
        if (endpoint.getType().equals(UMOEndpoint.ENDPOINT_TYPE_RECEIVER))
        {
            inbound = true;
        }
        else if (endpoint.getType().equals(UMOEndpoint.ENDPOINT_TYPE_SENDER))
        {
            inbound = false;
        }
        else
        {
            throw new InitialisationException(
                CoreMessages.authEndpointTypeForFilterMustBe(
                    UMOEndpoint.ENDPOINT_TYPE_SENDER + " or " + UMOEndpoint.ENDPOINT_TYPE_RECEIVER,
                    endpoint.getType()), this);
        }
        doInitialise();
    }

    public boolean isAuthenticate()
    {
        return authenticate;
    }

    public void setAuthenticate(boolean authenticate)
    {
        this.authenticate = authenticate;
    }

    /**
     * @param manager
     */
    public void setSecurityManager(UMOSecurityManager manager)
    {
        securityManager = manager;
    }

    public UMOSecurityManager getSecurityManager()
    {
        return securityManager;
    }

    public String getSecurityProviders()
    {
        return securityProviders;
    }

    public void setSecurityProviders(String providers)
    {
        securityProviders = providers;
    }

    public UMOImmutableEndpoint getEndpoint()
    {
        return endpoint;
    }

    public void setEndpoint(UMOImmutableEndpoint endpoint)
    {
        this.endpoint = endpoint;
    }

    public void authenticate(UMOEvent event)
        throws SecurityException, UnknownAuthenticationTypeException, CryptoFailureException,
        SecurityProviderNotFoundException, EncryptionStrategyNotFoundException
    {
        if (inbound)
        {
            authenticateInbound(event);
        }
        else
        {
            authenticateOutbound(event);
        }
    }

    public UMOCredentialsAccessor getCredentialsAccessor()
    {
        return credentialsAccessor;
    }

    public void setCredentialsAccessor(UMOCredentialsAccessor credentialsAccessor)
    {
        this.credentialsAccessor = credentialsAccessor;
    }

    protected abstract void authenticateInbound(UMOEvent event)
        throws SecurityException, CryptoFailureException, SecurityProviderNotFoundException,
        EncryptionStrategyNotFoundException, UnknownAuthenticationTypeException;

    protected abstract void authenticateOutbound(UMOEvent event)
        throws SecurityException, SecurityProviderNotFoundException, CryptoFailureException;

    protected abstract void doInitialise() throws InitialisationException;

}
