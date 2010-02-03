/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.session;

import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.api.MuleSession;
import org.mule.api.config.MuleProperties;
import org.mule.api.model.SessionException;
import org.mule.config.i18n.MessageFactory;
import org.mule.util.Base64;

import java.io.IOException;

import org.apache.commons.lang.SerializationUtils;

/**
 * A session handler used to store and retrieve session information on an
 * event. The DefaultMuleSession information is stored as a header on the message (does not
 * support Tcp, Udp, etc. unless the MuleMessage object is serialised across the
 * wire). The session is stored in the "MULE_SESSION" property as Base64 encoded
 * byte array.
 */
public class SerializeAndEncodeSessionHandler extends SerializeOnlySessionHandler
{
    public MuleSession retrieveSessionInfoFromMessage(MuleMessage message) throws MuleException
    {
        MuleSession session = null;
        String serializedEncodedSession = (String) message.removeProperty(MuleProperties.MULE_SESSION_PROPERTY);
        
        if (serializedEncodedSession != null)
        {
            byte[] serializedSession = Base64.decode(serializedEncodedSession);            
            if (serializedSession != null)
            {
                session = (MuleSession) SerializationUtils.deserialize(serializedSession);
            }
        }
        return session;
    }

    /**
     * @deprecated Use retrieveSessionInfoFromMessage(MuleMessage message) instead
     */
    public void retrieveSessionInfoFromMessage(MuleMessage message, MuleSession session) throws MuleException
    {
        session = retrieveSessionInfoFromMessage(message);
    }

    public void storeSessionInfoToMessage(MuleSession session, MuleMessage message) throws MuleException
    {
        
        byte[] serializedSession = SerializationUtils.serialize(removeNonSerializableProperties(session));
        String serializedEncodedSession;
        try
        {
            serializedEncodedSession = Base64.encodeBytes(serializedSession, Base64.DONT_BREAK_LINES);
        }
        catch (IOException e)
        {
            throw new SessionException(MessageFactory.createStaticMessage("Unable to serialize MuleSession"), e);
        }
        
        if (logger.isDebugEnabled())
        {
            logger.debug("Adding serialized and base64-encoded Session header to message: " + serializedEncodedSession);
        }
        message.setProperty(MuleProperties.MULE_SESSION_PROPERTY, serializedEncodedSession);
    }
}
