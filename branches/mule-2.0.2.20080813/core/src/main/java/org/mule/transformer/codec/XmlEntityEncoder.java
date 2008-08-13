/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transformer.codec;

import org.mule.api.transformer.TransformerException;
import org.mule.config.i18n.CoreMessages;
import org.mule.transformer.AbstractTransformer;
import org.mule.util.XMLEntityCodec;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;

/**
 * Encodes a string with XML entities
 */
public class XmlEntityEncoder extends AbstractTransformer
{

    public XmlEntityEncoder()
    {
        registerSourceType(String.class);
        registerSourceType(byte[].class);
        registerSourceType(InputStream.class);
        setReturnClass(String.class);
    }

    public Object doTransform(Object src, String encoding) throws TransformerException
    {
        try
        {
            String data;

            if (src instanceof byte[])
            {
                data = new String((byte[]) src, encoding);
            }
            else if (src instanceof InputStream)
            {
                data = IOUtils.toString((InputStream)src);
            }
            else 
            {
                data = (String) src;
            }

            return XMLEntityCodec.encodeString(data);
        }
        catch (Exception ex)
        {
            throw new TransformerException(
                CoreMessages.transformFailed(src.getClass().getName(), "XML"), 
                this, ex);
        }
    }

}
