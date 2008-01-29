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

import org.mule.api.transformer.DiscoverableTransformer;
import org.mule.api.transformer.TransformerException;
import org.mule.config.i18n.CoreMessages;
import org.mule.transformer.AbstractTransformer;

import java.io.InputStream;

import org.apache.commons.lang.SerializationUtils;

/**
 * <code>ByteArrayToSerializable</code> converts a serialized object to its object
 * representation
 */
public class ByteArrayToSerializable extends AbstractTransformer implements DiscoverableTransformer
{

    /** Give core transformers a slighty higher priority */
    private int priorityWeighting = DiscoverableTransformer.DEFAULT_PRIORITY_WEIGHTING + 1;

    public ByteArrayToSerializable()
    {
        registerSourceType(byte[].class);
        registerSourceType(InputStream.class);
    }

    public Object doTransform(Object src, String encoding) throws TransformerException
    {
        try
        {
            if (src instanceof byte[])
            {
                return SerializationUtils.deserialize((byte[]) src);
            }
            else
            {
                return SerializationUtils.deserialize((InputStream) src);

            }
        }
        catch (Exception e)
        {
            throw new TransformerException(
                    CoreMessages.transformFailed("byte[]", "Object"), this, e);
        }
    }

    public int getPriorityWeighting()
    {
        return priorityWeighting;
    }

    public void setPriorityWeighting(int priorityWeighting)
    {
        this.priorityWeighting = priorityWeighting;
    }
}
