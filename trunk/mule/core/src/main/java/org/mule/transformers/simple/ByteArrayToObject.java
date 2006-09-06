/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the BSD style
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transformers.simple;

import java.io.ObjectStreamConstants;

import org.mule.umo.transformer.TransformerException;

/**
 * 
 * <code>ByteArrayToObject</code> works in the same way as <code>ByteArrayToSerializable</code>
 * but checks if th byte array is a serialised object and if not will return a String created from 
 * the bytes is the returnType on the transformer.
 * 
 * @author Ross Mason
 *
 */
public class ByteArrayToObject extends ByteArrayToSerializable {

	/**
     * Serial version
     */
    private static final long serialVersionUID = 2105641786358330597L;

	public Object doTransform(Object src, String encoding) throws TransformerException {
		
		byte[] bytes = (byte[])src;
		if(bytes[0] == (byte)((ObjectStreamConstants.STREAM_MAGIC >>> 8) & 0xFF)) {
			return super.doTransform(src, encoding);
		} else {
			try {
				return new String(bytes, encoding);
			} catch (Exception e) {
				throw new TransformerException(this, e);
			}			
		}
	}

	
}
