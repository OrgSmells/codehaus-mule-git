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
package org.mule.umo.transformer;

import org.mule.umo.lifecycle.Initialisable;

import java.io.Serializable;

/**
 * <code>UMOSimpleTransformer</code> manages the transformation and or translation
 * of one type of data to the other. Source data is received, then processed and
 * returned via the <code>transform()</code> method.
 * <p/>
 * The return Class is specifed so that the return message is validated defore it
 * is returned.
 *
 * @author <a href="mailto:ross.mason@cubis.co.uk">Ross Mason</a>
 * @version $Revision$
 */

public interface UMOSimpleTransformer extends Initialisable, Serializable, Cloneable
{
    /**
     * JDK1.3+ 'Service Provider' specification
     * ( http://java.sun.com/j2se/1.3/docs/guide/jar/jar.html )
     */
    public static final String SERVICE_ID = "META-INF/services/org.mule.umo.transformer.UMOTransformer";

    /**
     * Thransforms the supllied data and returns the result
     *
     * @param src the data to transform
     * @return the transformed data
     * @throws TransformerException if a error occurs transforming the data
     *                              or if the expected returnClass isn't the same as the transformed data
     */
    public Object transform(Object src) throws TransformerException;

    /**
     * @param newName the logical name for the transformer
     */
    public void setName(String newName);

    /**
     * @return the logical name of the transformer
     */
    public String getName();

    /**
     * Sets the expected return type for the transformed data.  If the transformed data is not of this
     * class type a <code>TransformerException</code> will be thrown.
     *
     * @param theClass the expected return type class
     */
    public void setReturnClass(Class theClass);

    /**
     * @return the exceptedreturn type
     */
    public Class getReturnClass();

    //FIX shouldn't have to declare this but eclipse?? throws an error
    public Object clone() throws CloneNotSupportedException;

    public UMOTransformer getTransformer();

    /* (non-Javadoc)
     * @see org.mule.umo.transformer.UMOTransformer#setTransformer(org.mule.umo.transformer.UMOTransformer)
     */
    public void setTransformer(UMOTransformer transformer);

}