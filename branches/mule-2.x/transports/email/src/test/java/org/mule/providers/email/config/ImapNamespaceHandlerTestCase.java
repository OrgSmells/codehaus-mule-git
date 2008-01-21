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

import org.mule.providers.email.ImapConnector;
import org.mule.providers.email.ImapsConnector;
import org.mule.umo.UMOException;

public class ImapNamespaceHandlerTestCase extends AbstractEmailNamespaceHandlerTestCase
{
    protected String getConfigResources()
    {
        return "imap-namespace-config.xml";
    }

    public void testConfig() throws Exception
    {
        ImapConnector c = (ImapConnector)muleContext.getRegistry().lookupConnector("imapConnector");
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
        ImapsConnector c = (ImapsConnector)muleContext.getRegistry().lookupConnector("imapsConnector");
        assertNotNull(c);

        assertFalse(c.isBackupEnabled());
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
        testEndpoint("global1", ImapConnector.IMAP);
        testEndpoint("global2", ImapConnector.IMAP);
        testEndpoint("global1s", ImapsConnector.IMAPS);
        testEndpoint("global2s", ImapsConnector.IMAPS);
    }

}