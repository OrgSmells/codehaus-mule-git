/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the BSD style
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.routing.inbound;

import org.mule.transformers.simple.ByteArrayToHexString;
import org.mule.transformers.simple.SerializableToByteArray;
import org.mule.umo.MessagingException;
import org.mule.umo.UMOEvent;
import org.mule.umo.routing.RoutingException;
import org.mule.umo.transformer.TransformerException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <code>IdempotentSecureHashReceiver</code> ensures that only unique
 * messages are received by a component. It does this by calculating the
 * SHA-256 hash of the message itself.  This provides a value with an
 * infinitesimally small chance of a collision.  This can be used to
 * filter message duplicates.  Please keep in mind that the hash is
 * calculated over the entire byte array representing the message, so
 * any leading or trailing spaces or extraneous bytes (like padding) can
 * produce different hash values for the same semantic message content.
 * Care should be taken to ensure that messages do not contain extraneous
 * bytes.
 *
 * This class is useful when the message does not support unique identifiers.
 *
 * This implementation provides for a persistent store of message hash values
 * via the underlying file system and is suitable in failover environments.
 *
 * @author <a href="mailto:rlucente@xecu.net">Rich Lucente</a>
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */

public class IdempotentSecureHashReceiver extends IdempotentReceiver
{
    private static final String messageDigestAlgorithm = "SHA-256";

    private SerializableToByteArray objectToByteArray = new SerializableToByteArray();
    private ByteArrayToHexString byteArrayToHexString = new ByteArrayToHexString();


    protected String getIdForEvent(UMOEvent event) throws MessagingException {
        try {
        MessageDigest md = MessageDigest.getInstance(messageDigestAlgorithm);
            return (String)byteArrayToHexString.transform(
                    md.digest((byte[])objectToByteArray.transform(event.getMessage().getPayload())));
        } catch (NoSuchAlgorithmException nsa) {
            throw new RoutingException(event.getMessage(), event.getEndpoint(), nsa);
        } catch (TransformerException te) {
            throw new RoutingException(event.getMessage(), event.getEndpoint(), te);
        }
    }
}