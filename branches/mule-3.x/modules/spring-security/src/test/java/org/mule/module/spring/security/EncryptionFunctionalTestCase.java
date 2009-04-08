/*
 * $Id: EncryptionFunctionalTestCase.java 10662 2008-02-01 13:10:14Z romikk $
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
import org.mule.api.security.CredentialsNotSetException;
import org.mule.api.security.UnauthorisedException;
import org.mule.config.ExceptionHelper;
import org.mule.module.client.MuleClient;
import org.mule.security.MuleCredentials;
import org.mule.tck.FunctionalTestCase;
import org.mule.transport.http.HttpConnector;
import org.mule.transport.http.HttpConstants;

import java.util.HashMap;
import java.util.Map;

public class EncryptionFunctionalTestCase extends FunctionalTestCase
{

    protected String getConfigResources()
    {
        return "encryption-test.xml";
    }

    public void testAuthenticationFailureNoContext() throws Exception
    {
        MuleClient client = new MuleClient();
        MuleMessage m = client.send("vm://my.queue", "foo", null);
        assertNotNull(m);
        assertNotNull(m.getExceptionPayload());
        assertEquals(ExceptionHelper.getErrorCode(CredentialsNotSetException.class), m.getExceptionPayload()
            .getCode());
    }

    public void testAuthenticationFailureBadCredentials() throws Exception
    {
        MuleClient client = new MuleClient();
        Map props = new HashMap();
        EncryptionStrategy strategy = muleContext
            .getSecurityManager()
            .getEncryptionStrategy("PBE");
        String header = MuleCredentials.createHeader("anonX", "anonX", "PBE", strategy);
        props.put(MuleProperties.MULE_USER_PROPERTY, header);

        MuleMessage m = client.send("vm://my.queue", "foo", props);
        assertNotNull(m);
        assertNotNull(m.getExceptionPayload());
        assertEquals(ExceptionHelper.getErrorCode(UnauthorisedException.class), m.getExceptionPayload()
            .getCode());
    }

    public void testAuthenticationAuthorised() throws Exception
    {
        MuleClient client = new MuleClient();

        Map props = new HashMap();
        EncryptionStrategy strategy = muleContext
            .getSecurityManager()
            .getEncryptionStrategy("PBE");
        String header = MuleCredentials.createHeader("anon", "anon", "PBE", strategy);
        props.put(MuleProperties.MULE_USER_PROPERTY, header);

        MuleMessage m = client.send("vm://my.queue", "foo", props);
        assertNotNull(m);
        assertNull(m.getExceptionPayload());
    }

    public void testAuthenticationFailureBadCredentialsHttp() throws Exception
    {
        MuleClient client = new MuleClient();
        Map props = new HashMap();
        EncryptionStrategy strategy = muleContext
            .getSecurityManager()
            .getEncryptionStrategy("PBE");
        String header = MuleCredentials.createHeader("anonX", "anonX", "PBE", strategy);
        props.put(MuleProperties.MULE_USER_PROPERTY, header);

        MuleMessage m = client.send("http://localhost:4567/index.html", "", props);
        assertNotNull(m);

        int status = m.getIntProperty(HttpConnector.HTTP_STATUS_PROPERTY, -1);
        assertEquals(HttpConstants.SC_UNAUTHORIZED, status);
    }

    public void testAuthenticationAuthorisedHttp() throws Exception
    {
        MuleClient client = new MuleClient();

        Map props = new HashMap();
        EncryptionStrategy strategy = muleContext
            .getSecurityManager()
            .getEncryptionStrategy("PBE");
        String header = MuleCredentials.createHeader("anon", "anon", "PBE", strategy);
        props.put(MuleProperties.MULE_USER_PROPERTY, header);

        MuleMessage m = client.send("http://localhost:4567/index.html", "", props);
        assertNotNull(m);
        int status = m.getIntProperty(HttpConnector.HTTP_STATUS_PROPERTY, -1);
        assertEquals(HttpConstants.SC_OK, status);
    }

}
