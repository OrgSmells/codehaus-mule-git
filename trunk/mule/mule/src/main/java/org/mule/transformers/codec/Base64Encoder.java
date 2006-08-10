/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the BSD style
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transformers.codec;

import org.mule.config.i18n.Message;
import org.mule.config.i18n.Messages;
import org.mule.transformers.AbstractTransformer;
import org.mule.umo.transformer.TransformerException;
import org.mule.util.Base64;

/**
 * <code>Base64Encoder</code> transforms strings or byte arrays into Base64
 * encoded string
 * 
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */
public class Base64Encoder extends AbstractTransformer
{
    /**
     * Serial version
     */
    private static final long serialVersionUID = 7742396053840854720L;

    public Base64Encoder()
    {
        registerSourceType(String.class);
        registerSourceType(byte[].class);
        setReturnClass(String.class);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.umo.transformer.UMOTransformer#transform(java.lang.Object)
     */
    public Object doTransform(Object src, String encoding) throws TransformerException
    {
        byte[] buf;

        try {
            if (src instanceof String) {
                buf = ((String)src).getBytes(encoding);
            }
            else {
                buf = (byte[])src;
            }

            String result = Base64.encodeBytes(buf, Base64.DONT_BREAK_LINES);

            if (getReturnClass().equals(byte[].class)) {
                return result.getBytes(encoding);
            }
            else {
                return result;
            }
        }
        catch (Exception ex) {
            throw new TransformerException(new Message(Messages.TRANSFORM_FAILED_FROM_X_TO_X,
                    src.getClass().getName(), "base64"), this, ex);
        }
    }

}
