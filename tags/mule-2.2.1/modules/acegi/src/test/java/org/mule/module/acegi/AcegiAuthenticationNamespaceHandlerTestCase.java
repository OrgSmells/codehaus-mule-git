/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.acegi;

import org.mule.api.config.MuleProperties;
import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.api.security.SecurityProvider;
import org.mule.api.service.Service;
import org.mule.module.acegi.filters.http.HttpBasicAuthenticationFilter;
import org.mule.security.MuleSecurityManager;
import org.mule.tck.FunctionalTestCase;

import java.util.Collection;

public class AcegiAuthenticationNamespaceHandlerTestCase extends FunctionalTestCase
{

    protected String getConfigResources()
    {
        return "acegi-authentication-config.xml";
    }

    public void testSecurityManagerConfigured()
    {
        MuleSecurityManager securityManager = 
            (MuleSecurityManager) muleContext.getRegistry().lookupObject(MuleProperties.OBJECT_SECURITY_MANAGER);
        assertNotNull(securityManager);
        
        Collection providers = securityManager.getProviders();
        assertEquals(1, providers.size());
        SecurityProvider provider = (SecurityProvider) providers.iterator().next();
        assertEquals(AcegiProviderAdapter.class, provider.getClass());
    }
    
    public void testEndpointConfiguration()
    {
        Service service = muleContext.getRegistry().lookupService("echo");
        assertNotNull(service);
        assertEquals(1, service.getInboundRouter().getEndpoints().size());

        ImmutableEndpoint endpoint = (ImmutableEndpoint) service.getInboundRouter().getEndpoints().get(0);
        assertNotNull(endpoint.getSecurityFilter());
        assertEquals(HttpBasicAuthenticationFilter.class, endpoint.getSecurityFilter().getClass());
    }

}
