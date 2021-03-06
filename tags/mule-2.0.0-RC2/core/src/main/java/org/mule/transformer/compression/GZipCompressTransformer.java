/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transformer.compression;

import org.mule.api.transformer.TransformerException;
import org.mule.util.compression.GZipCompression;

import java.io.IOException;
import java.io.Serializable;

import org.apache.commons.lang.SerializationUtils;

/**
 * <code>GZipCompressTransformer</code> is a transformer compressing objects into
 * byte arrays.
 */
public class GZipCompressTransformer extends AbstractCompressionTransformer
{

    public GZipCompressTransformer()
    {
        super();
        this.setStrategy(new GZipCompression());
        this.registerSourceType(Serializable.class);
        this.registerSourceType(byte[].class);
        this.setReturnClass(byte[].class);
    }

    public Object doTransform(Object src, String encoding) throws TransformerException
    {
        try
        {
            byte[] data = null;
            if (src instanceof byte[])
            {
                data = (byte[]) src;
            }
            else
            {
                data = SerializationUtils.serialize((Serializable) src);
            }
            return this.getStrategy().compressByteArray(data);
        }
        catch (IOException ioex)
        {
            throw new TransformerException(this, ioex);
        }
    }

}
