/*
 * $Id: AuthComponentAsynchFunctionalTestCase.java 10662 2008-02-01 13:10:14Z romikk $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.spring.security;

import org.mule.api.EncryptionStrategy;
import org.mule.api.MuleMessage;
import org.mule.api.config.MuleProperties;
import org.mule.module.client.MuleClient;
import org.mule.security.MuleCredentials;
import org.mule.tck.FunctionalTestCase;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.context.SecurityContextImpl;

public class AuthComponentAsynchFunctionalTestCase extends FunctionalTestCase
{

    protected String getConfigResources()
    {
        return "auth-component-asynch-test.xml";
    }

    // @Override
    // Clear the security context after each test.
    public void doTearDown()
    {
        SecurityContextHolder.setContext(new SecurityContextImpl());
    }

    public void testCaseGoodAuthenticationGoodAuthorisation() throws Exception
    {
        MuleClient client = new MuleClient();
        Map props = new HashMap();

        EncryptionStrategy strategy = muleContext
            .getSecurityManager()
            .getEncryptionStrategy("PBE");
        String header = MuleCredentials.createHeader("marie", "marie", "PBE", strategy);
        props.put(MuleProperties.MULE_USER_PROPERTY, header);
        client.dispatch("vm://test", "Marie", props);
        MuleMessage m = client.request("vm://output", 3000);
        assertNotNull(m);
        assertEquals((String)m.getPayload(), "Marie");
    }

    public void testCaseGoodAuthenticationBadAuthorisation() throws Exception
    {
        MuleClient client = new MuleClient();
        Map props = new HashMap();

        EncryptionStrategy strategy = muleContext
            .getSecurityManager()
            .getEncryptionStrategy("PBE");
        String header = MuleCredentials.createHeader("anon", "anon", "PBE", strategy);
        props.put(MuleProperties.MULE_USER_PROPERTY, header);
        client.dispatch("vm://test", "Marie", props);
        MuleMessage m = client.request("vm://output", 3000);
        assertNull(m);
    }

    public void testCaseBadAuthentication() throws Exception
    {
        MuleClient client = new MuleClient();
        Map props = new HashMap();

        EncryptionStrategy strategy = muleContext
            .getSecurityManager()
            .getEncryptionStrategy("PBE");
        String header = MuleCredentials.createHeader("anonX", "anonX", "PBE", strategy);
        props.put(MuleProperties.MULE_USER_PROPERTY, header);
        client.dispatch("vm://test", "USD,MTL", props);
        MuleMessage m = client.request("vm://output", 3000);
        assertNull(m);
    }

}
