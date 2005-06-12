/*
 * $Header$
 * $Revision$
 * $Date$
 * ------------------------------------------------------------------------------------------------------
 *
 * Copyright (c) SymphonySoft Limited. All rights reserved.
 * http://www.symphonysoft.com
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.mule.transformers.simple;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.mule.config.i18n.Message;
import org.mule.config.i18n.Messages;
import org.mule.transformers.AbstractTransformer;
import org.mule.umo.transformer.TransformerException;

/**
 * <code>ByteArrayToSerialisable</code> converts a Serialised object to its
 * object representation
 * 
 * @deprecated use ByteArrayToSerializable
 * 
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */
public class ByteArrayToSerialisable extends AbstractTransformer
{
    public ByteArrayToSerialisable()
    {
        registerSourceType(byte[].class);
    }

    public Object doTransform(Object src) throws TransformerException
    {
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try {
            bais = new ByteArrayInputStream((byte[]) src);
            ois = new ObjectInputStream(bais);
            Object result = ois.readObject();
            bais.close();

            return result;
        } catch (Exception e) {
            throw new TransformerException(new Message(Messages.TRANSFORM_FAILED_FROM_X_TO_X, "byte[]", "Object"),
                                           this,
                                           e);
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                // ignore
            }
        }
    }
}
