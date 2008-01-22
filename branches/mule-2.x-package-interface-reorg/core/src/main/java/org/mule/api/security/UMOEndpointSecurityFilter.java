/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.api.security;

import org.mule.api.UMOEvent;
import org.mule.api.endpoint.UMOImmutableEndpoint;
import org.mule.api.lifecycle.Initialisable;
import org.mule.api.lifecycle.InitialisationException;

/**
 * <code>UMOEndpointSecurityFilter</code> is a base filter for secure filtering of
 * inbound and outbout event flow
 */
public interface UMOEndpointSecurityFilter extends Initialisable
{
    /**
     * @param manager
     */
    void setSecurityManager(UMOSecurityManager manager);

    UMOSecurityManager getSecurityManager();

    String getSecurityProviders();

    void setSecurityProviders(String providers);

    void setEndpoint(UMOImmutableEndpoint endpoint);

    UMOImmutableEndpoint getEndpoint();

    void setCredentialsAccessor(UMOCredentialsAccessor accessor);

    UMOCredentialsAccessor getCredentialsAccessor();

    void authenticate(UMOEvent event)
            throws SecurityException, UnknownAuthenticationTypeException, CryptoFailureException,
            SecurityProviderNotFoundException, EncryptionStrategyNotFoundException, InitialisationException;
}
