/* 
 * $Header$
 * $Revision$
 * $Date$
 * ------------------------------------------------------------------------------------------------------
 * 
 * Copyright (c) Cubis Limited. All rights reserved.
 * http://www.cubis.co.uk 
 * 
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file. 
 *
 */
package org.mule.util.compression;

import java.io.IOException;

/**
 * <code>CompressionStrategy</code> TODO -document class
 *
 * @author <a href="mailto:ross.mason@cubis.co.uk">Ross Mason</a>
 * @version $Revision$
 */
public interface CompressionStrategy
{
    /**
     * The fully qualified class name of the fallback <code>CompressionStrategy</code>
     * implementation class to use, if no other can be found. the default is
     * <code>org.mule.util.compression.GZipCompression</code>
     */
    public static final String COMPRESSION_DEFAULT = "org.mule.util.compression.GZipCompression";

    /**
     * JDK1.3+ 'Service Provider' specification
     * ( http://java.sun.com/j2se/1.3/docs/guide/jar/jar.html )
     */
    public static final String SERVICE_ID = "META-INF/services/org.mule.util.compression.CompressionStrategy";

    public byte[] compressByteArray(byte[] bytes) throws IOException;

    public byte[] uncompressByteArray(byte[] bytes) throws IOException;

    public boolean isCompressed(byte[] bytes) throws IOException;
}