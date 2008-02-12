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

import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.security.CryptoFailureException;
import org.mule.config.i18n.CoreMessages;
import org.mule.security.AbstractNamedEncryptionStrategy;

import java.io.ByteArrayInputStream;
import java.util.Collection;

import cryptix.message.EncryptedMessage;
import cryptix.message.EncryptedMessageBuilder;
import cryptix.message.LiteralMessageBuilder;
import cryptix.message.Message;
import cryptix.message.MessageFactory;
import cryptix.message.SignedMessageBuilder;
import cryptix.openpgp.PGPArmouredMessage;
import cryptix.pki.KeyBundle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class KeyBasedEncryptionStrategy extends AbstractNamedEncryptionStrategy
{
    /**
     * logger used by this class
     */
    protected static final Log logger = LogFactory.getLog(KeyBasedEncryptionStrategy.class);

    private PGPKeyRing keyManager;

    public byte[] encrypt(byte[] data, Object cryptInfo) throws CryptoFailureException
    {
        try
        {
            PGPCryptInfo pgpCryptInfo = (PGPCryptInfo)cryptInfo;
            KeyBundle publicKey = pgpCryptInfo.getKeyBundle();

            LiteralMessageBuilder lmb = LiteralMessageBuilder.getInstance("OpenPGP");

            lmb.init(data);

            Message msg = lmb.build();

            if (pgpCryptInfo.isSignRequested())
            {
                SignedMessageBuilder smb = SignedMessageBuilder.getInstance("OpenPGP");

                smb.init(msg);
                smb.addSigner(keyManager.getSecretKeyBundle(), keyManager.getSecretPassphrase().toCharArray());

                msg = smb.build();
            }

            EncryptedMessageBuilder emb = EncryptedMessageBuilder.getInstance("OpenPGP");
            emb.init(msg);
            emb.addRecipient(publicKey);
            msg = emb.build();

            return new PGPArmouredMessage(msg).getEncoded();
        }
        catch (Exception e)
        {
            throw new CryptoFailureException(this, e);
        }
    }

    public byte[] decrypt(byte[] data, Object cryptInfo) throws CryptoFailureException
    {
        try
        {
            MessageFactory mf = MessageFactory.getInstance("OpenPGP");

            ByteArrayInputStream in = new ByteArrayInputStream(data);

            Collection msgs = mf.generateMessages(in);

            Message msg = (Message)msgs.iterator().next();

            if (msg instanceof EncryptedMessage)
            {
                msg = ((EncryptedMessage)msg).decrypt(keyManager.getSecretKeyBundle(),
                    keyManager.getSecretPassphrase().toCharArray());

                return new PGPArmouredMessage(msg).getEncoded();
            }
        }
        catch (Exception e)
        {
            throw new CryptoFailureException(this, e);
        }

        return data;
    }

    public void initialise() throws InitialisationException
    {
        try
        {
            java.security.Security.addProvider(new cryptix.jce.provider.CryptixCrypto());
            java.security.Security.addProvider(new cryptix.openpgp.provider.CryptixOpenPGP());
        }
        catch (Exception e)
        {
            throw new InitialisationException(
                CoreMessages.failedToCreate("KeyBasedEncryptionStrategy"), e, this);
        }
    }

    public PGPKeyRing getKeyManager()
    {
        return keyManager;
    }

    public void setKeyManager(PGPKeyRing keyManager)
    {
        this.keyManager = keyManager;
    }
}
