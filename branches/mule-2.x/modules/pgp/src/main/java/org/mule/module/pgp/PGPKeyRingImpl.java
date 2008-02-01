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

import org.mule.api.lifecycle.Initialisable;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.config.i18n.CoreMessages;
import org.mule.util.IOUtils;

import java.io.InputStream;
import java.security.Principal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;

import cryptix.pki.ExtendedKeyStore;
import cryptix.pki.KeyBundle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PGPKeyRingImpl implements PGPKeyRing, Initialisable
{
    protected static final Log logger = LogFactory.getLog(PGPKeyRingImpl.class);

    private String publicKeyRingFileName;

    private HashMap principalsKeyBundleMap;

    private String secretKeyRingFileName;

    private String secretAliasId;

    private KeyBundle secretKeyBundle;

    private String secretPassphrase;

    public PGPKeyRingImpl()
    {
        super();
    }

    public String getSecretKeyRingFileName()
    {
        return secretKeyRingFileName;
    }

    public void setSecretKeyRingFileName(String value)
    {
        this.secretKeyRingFileName = value;
    }

    public String getSecretAliasId()
    {
        return secretAliasId;
    }

    public void setSecretAliasId(String value)
    {
        this.secretAliasId = value;
    }

    public String getSecretPassphrase()
    {
        return secretPassphrase;
    }

    public void setSecretPassphrase(String value)
    {
        this.secretPassphrase = value;
    }

    private void readPrivateKeyBundle() throws Exception
    {
        InputStream in = IOUtils.getResourceAsStream(secretKeyRingFileName, getClass());

        ExtendedKeyStore ring = (ExtendedKeyStore) ExtendedKeyStore.getInstance("OpenPGP/KeyRing");
        ring.load(in, null);

        in.close();

        secretKeyBundle = ring.getKeyBundle(secretAliasId);
    }

    public KeyBundle getSecretKeyBundle()
    {
        return secretKeyBundle;
    }

    /** @return  */
    public String getPublicKeyRingFileName()
    {
        return publicKeyRingFileName;
    }

    /** @param value  */
    public void setPublicKeyRingFileName(String value)
    {
        this.publicKeyRingFileName = value;
    }

    public KeyBundle getKeyBundle(String principalId)
    {
        return (KeyBundle) principalsKeyBundleMap.get(principalId);
    }

    public void initialise() throws InitialisationException
    {
        try
        {
            java.security.Security.addProvider(new cryptix.jce.provider.CryptixCrypto());
            java.security.Security.addProvider(new cryptix.openpgp.provider.CryptixOpenPGP());

            principalsKeyBundleMap = new HashMap();

            readPublicKeyRing();
            readPrivateKeyBundle();
        }
        catch (Exception e)
        {
            logger.error("errore in inizializzazione:" + e.getMessage(), e);
            throw new InitialisationException(CoreMessages.failedToCreate("PGPKeyRingImpl"), e, this);
        }
    }

    private void readPublicKeyRing() throws Exception
    {
        logger.debug(System.getProperties().get("user.dir"));
        InputStream in = IOUtils.getResourceAsStream(publicKeyRingFileName, getClass());

        ExtendedKeyStore ring = (ExtendedKeyStore) ExtendedKeyStore.getInstance("OpenPGP/KeyRing");
        ring.load(in, null);
        in.close();

        for (Enumeration e = ring.aliases(); e.hasMoreElements();)
        {
            String aliasId = (String) e.nextElement();

            KeyBundle bundle = ring.getKeyBundle(aliasId);

            if (bundle != null)
            {
                for (Iterator users = bundle.getPrincipals(); users.hasNext();)
                {
                    Principal princ = (Principal) users.next();

                    principalsKeyBundleMap.put(princ.getName(), bundle);
                }
            }
        }
    }
}
