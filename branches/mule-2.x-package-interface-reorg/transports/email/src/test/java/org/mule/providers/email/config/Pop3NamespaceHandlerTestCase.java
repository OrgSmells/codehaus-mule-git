/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.providers.email.config;

import org.mule.providers.email.Pop3Connector;
import org.mule.providers.email.Pop3sConnector;
import org.mule.umo.UMOException;

/**
 * TODO
 */
public class Pop3NamespaceHandlerTestCase extends AbstractEmailNamespaceHandlerTestCase
{
    protected String getConfigResources()
    {
        return "pop3-namespace-config.xml";
    }

    public void testConfig() throws Exception
    {
        Pop3Connector c = (Pop3Connector)muleContext.getRegistry().lookupConnector("pop3Connector");
        assertNotNull(c);

        assertTrue(c.isBackupEnabled());
        assertEquals("newBackup", c.getBackupFolder());
        assertEquals(1234, c.getCheckFrequency());
        assertEquals("newMailbox", c.getMailboxFolder());
        assertEquals(false, c.isDeleteReadMessages());

        // authenticator?

        assertTrue(c.isConnected());
        assertTrue(c.isStarted());

    }

    public void testSecureConfig() throws Exception
    {
        Pop3sConnector c = (Pop3sConnector)muleContext.getRegistry().lookupConnector("pop3sConnector");
        assertNotNull(c);

        assertTrue(c.isBackupEnabled());
        assertEquals("newBackup", c.getBackupFolder());
        assertEquals(1234, c.getCheckFrequency());
        assertEquals("newMailbox", c.getMailboxFolder());
        assertEquals(false, c.isDeleteReadMessages());

        // authenticator?

        //The full path gets resolved, we're just checkng that the property got set
        assertTrue(c.getClientKeyStore().endsWith("/greenmail-truststore"));
        assertEquals("password", c.getClientKeyStorePassword());
        //The full path gets resolved, we're just checkng that the property got set
        assertTrue(c.getTrustStore().endsWith("/greenmail-truststore"));
        assertEquals("password", c.getTrustStorePassword());

        assertTrue(c.isConnected());
        assertTrue(c.isStarted());
    }

    public void testEndpoint() throws UMOException
    {
        testEndpoint("global1", Pop3Connector.POP3);
        testEndpoint("global2", Pop3Connector.POP3);
        testEndpoint("global1s", Pop3sConnector.POP3S);
        testEndpoint("global2s", Pop3sConnector.POP3S);
    }

}