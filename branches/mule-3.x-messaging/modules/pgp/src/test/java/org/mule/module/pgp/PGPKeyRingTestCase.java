/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.pgp;

import org.mule.tck.AbstractMuleTestCase;

import cryptix.pki.KeyBundle;

import java.net.URL;

public class PGPKeyRingTestCase extends AbstractMuleTestCase
{
    private PGPKeyRing keyManager;

    @Override
    protected void doSetUp() throws Exception
    {
        PGPKeyRingImpl keyM = new PGPKeyRingImpl();
        URL url;

        url = Thread.currentThread().getContextClassLoader().getResource("./serverPublic.gpg");
        keyM.setPublicKeyRingFileName(url.getFile());

        url = Thread.currentThread().getContextClassLoader().getResource("./serverPrivate.gpg");
        keyM.setSecretKeyRingFileName(url.getFile());

        keyM.setSecretAliasId("0x6168F39C");
        keyM.setSecretPassphrase("TestingPassphrase");
        keyM.initialise();

        keyManager = keyM;
    }

    @Override
    protected void doTearDown() throws Exception
    {
        keyManager = null;
    }

    public void testClientKey()
    {
        KeyBundle clientKey = keyManager.getKeyBundle("Mule client <mule_client@mule.com>");
        assertNotNull(clientKey);
    }

    public void testServerKey()
    {
        KeyBundle serverKey = keyManager.getSecretKeyBundle();
        assertNotNull(serverKey);
    }
}
