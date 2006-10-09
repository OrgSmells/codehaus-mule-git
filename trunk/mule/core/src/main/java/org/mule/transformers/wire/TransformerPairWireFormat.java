/*
 * $Id: $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.transformers.wire;

import org.mule.umo.UMOException;
import org.mule.umo.transformer.UMOTransformer;
import org.mule.umo.transformer.TransformerException;
import org.mule.config.i18n.Message;
import org.mule.config.i18n.Messages;
import org.mule.util.IOUtils;
import org.mule.MuleException;
import org.mule.MuleManager;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * TODO
 */
public class TransformerPairWireFormat implements WireFormat
{
    protected UMOTransformer inboundTransformer;
    protected UMOTransformer outboundTransformer;

    public Object read(InputStream in) throws UMOException
    {
        if (inboundTransformer == null)
        {
            throw new NullPointerException(new Message(Messages.X_IS_NULL, "inboundTransformer").getMessage());
        }
        if (inboundTransformer.isSourceTypeSupported(InputStream.class))
        {
            return inboundTransformer.transform(in);
        } else
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try
            {
                IOUtils.copy(in, baos);
                return inboundTransformer.transform(baos.toByteArray());
            } catch (IOException e)
            {
                throw new MuleException(new Message(Messages.FAILED_TO_READ_PAYLOAD, e));
            }
        }
    }

    public void write(OutputStream out, Object o) throws UMOException
    {
        if (outboundTransformer == null)
        {
            throw new NullPointerException(new Message(Messages.X_IS_NULL, "outboundTransformer").getMessage());
        }
        try
        {
            Class returnClass = outboundTransformer.getReturnClass();
            if (returnClass == null)
            {
                Object result = outboundTransformer.transform(o);
                out.write(result.toString().getBytes(MuleManager.getConfiguration().getEncoding()));
            } else if (returnClass.equals(byte[].class))
            {
                byte[] b = (byte[]) outboundTransformer.transform(o);
                out.write(b);
            } else
            {
                if (returnClass != null && returnClass.equals(String.class))
                {
                    String s = (String) outboundTransformer.transform(o);
                    out.write(s.getBytes(MuleManager.getConfiguration().getEncoding()));
                } else
                {
                    throw new TransformerException(new Message(Messages.TRANSFORM_FAILED_FROM_X, o.getClass()));
                }
            }
        } catch (IOException e)
        {
            throw new TransformerException(new Message(Messages.TRANSFORM_FAILED_FROM_X, o.getClass(), e));
        }
    }


    public UMOTransformer getInboundTransformer()
    {
        return inboundTransformer;
    }

    public void setInboundTransformer(UMOTransformer inboundTransformer)
    {
        this.inboundTransformer = inboundTransformer;
    }

    public UMOTransformer getOutboundTransformer()
    {
        return outboundTransformer;
    }

    public void setOutboundTransformer(UMOTransformer outboundTransformer)
    {
        this.outboundTransformer = outboundTransformer;
    }
}
