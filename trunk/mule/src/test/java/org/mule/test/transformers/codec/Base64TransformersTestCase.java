/*
 * $Header:
 * /cvsroot/mule/mule/src/test/org/mule/test/transformers/DefaultTransformersTestCase.java,v
 * 1.3 2003/11/26 06:45:02 rossmason Exp $ $Revision$ $Date: 2003/11/26
 * 06:45:02 $
 * ------------------------------------------------------------------------------------------------------
 * 
 * Copyright (c) Cubis Limited. All rights reserved. http://www.cubis.co.uk
 * 
 * The software in this package is published under the terms of the BSD style
 * license a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 *  
 */

package org.mule.test.transformers.codec;

import org.mule.tck.AbstractTransformerTestCase;
import org.mule.umo.transformer.UMOTransformer;
import org.mule.test.transformers.ReverseCompressionTransformer;
import org.mule.transformers.codec.Base64Encoder;
import org.mule.transformers.codec.Base64Decoder;

/**
 *
 * @author <a href="mailto:ross.mason@cubis.co.uk">Ross Mason</a>
 * @version $Revision$
 */
public class Base64TransformersTestCase extends AbstractTransformerTestCase
{
    /* (non-Javadoc)
     * @see org.mule.tck.AbstractTransformerTestCase#getResultData()
     */
    public Object getResultData()
    {
        return new sun.misc.BASE64Encoder().encode(getTestData().toString().getBytes());
    }

    /* (non-Javadoc)
     * @see org.mule.tck.AbstractTransformerTestCase#getTestData()
     */
    public Object getTestData()
    {
        return "the quick brown fox jumped over the lazy dog";
    }

    /* (non-Javadoc)
     * @see org.mule.tck.AbstractTransformerTestCase#getTransformers()
     */
    public UMOTransformer getTransformer()
    {
        return new Base64Encoder();
    }

    /* (non-Javadoc)
     * @see org.mule.tck.AbstractTransformerTestCase#getRoundTripTransformer()
     */
    public UMOTransformer getRoundTripTransformer()
    {
        return new Base64Decoder();
    }

}
