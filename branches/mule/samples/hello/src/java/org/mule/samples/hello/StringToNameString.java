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
 
package org.mule.samples.hello;

import org.mule.transformers.DefaultTransformer;
import org.mule.umo.transformer.TransformerException;

/**
 *  <code>StringToNameString</code> TODO (document class)
 *
 * @author <a href="mailto:ross.mason@cubis.co.uk">Ross Mason</a>
 * @version $Revision$
 */
public class StringToNameString extends DefaultTransformer
{
    /**
     * 
     */
    public StringToNameString()
    {
        super();
        this.registerSourceType(String.class);
    }
    

    /* (non-Javadoc)
     * @see org.mule.transformers.AbstractTransformer#doTransform(java.lang.Object)
     */
    public Object doTransform(Object src) throws TransformerException
    {
        String name = (String)src;
        int i = name.indexOf("\r");
        if(i > -1) name = name.substring(0, i);
        return new NameString(name);
    }

}
