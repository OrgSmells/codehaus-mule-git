/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transformer.simple;

import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractTransformer;
import org.mule.transformer.types.DataTypeFactory;
import org.mule.util.ArrayUtils;
import org.mule.util.StringUtils;

/**
 * Converts a Hex String to a Byte array
 */
public class HexStringToByteArray extends AbstractTransformer
{

    public HexStringToByteArray()
    {
        registerSourceType(String.class);
        setReturnDataType(DataTypeFactory.create(byte[].class));
    }

    protected Object doTransform(Object src, String encoding) throws TransformerException
    {
        if (src == null)
        {
            return ArrayUtils.EMPTY_BYTE_ARRAY;
        }

        try
        {
            return StringUtils.hexStringToByteArray((String) src);
        }
        catch (Exception ex)
        {
            throw new TransformerException(this, ex);
        }
    }

}
