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
import org.mule.util.Base64;

/**
 * <code>Base64Encoder</code> transforms strings or byte arrays into Base64 encoded
 * string.
 */
public class Base64Encoder extends AbstractTransformer
{

    public Base64Encoder()
    {
        registerSourceType(String.class);
        registerSourceType(byte[].class);
        setReturnClass(String.class);
    }

    public Object doTransform(Object src, String encoding) throws TransformerException
    {
        try
        {
            byte[] buf;

            if (src instanceof String)
            {
                buf = ((String) src).getBytes(encoding);
            }
            else
            {
                buf = (byte[]) src;
            }

            String result = Base64.encodeBytes(buf, Base64.DONT_BREAK_LINES);

            if (getReturnClass().equals(byte[].class))
            {
                return result.getBytes(encoding);
            }
            else
            {
                return result;
            }
        }
        catch (Exception ex)
        {
            throw new TransformerException(
                CoreMessages.transformFailed(src.getClass().getName(), "base64"), this, ex);
        }
    }

}
