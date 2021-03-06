/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.extras.acegi;

import org.mule.api.security.SecurityManager;
import org.mule.api.security.SecurityProvider;
import org.mule.tck.FunctionalTestCase;

import java.util.Iterator;

import org.acegisecurity.providers.dao.DaoAuthenticationProvider;

public class AcegiNamespaceHandlerTestCase extends FunctionalTestCase
{

    protected String getConfigResources()
    {
        return "acegi-namespace-config.xml";
    }

    public void testAcegi()
    {
        knownProperties(getProvider("memory-dao"));
    }

    protected SecurityProvider getProvider(String name)
    {
        SecurityManager securityManager = muleContext.getSecurityManager();
        return securityManager.getProvider(name);
    }

    public void testCustom()
    {
        Iterator providers = muleContext.getSecurityManager().getProviders().iterator();
        while (providers.hasNext())
        {
            SecurityProvider provider = (SecurityProvider) providers.next();
            logger.debug(provider);
            logger.debug(provider.getName());
        }
        knownProperties(getProvider("customProvider"));
        knownProperties(getProvider("willOverwriteName"));
    }

    protected void knownProperties(SecurityProvider provider)
    {
        assertNotNull(provider);
        assertTrue(provider instanceof AcegiProviderAdapter);
        AcegiProviderAdapter adapter = (AcegiProviderAdapter) provider;
        assertNotNull(adapter.getDelegate());
        assertTrue(adapter.getDelegate() instanceof DaoAuthenticationProvider);
    }

}