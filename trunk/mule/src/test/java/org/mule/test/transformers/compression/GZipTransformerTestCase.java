/* 
 * $Header$
 * $Revision$
 * $Date$
 * ------------------------------------------------------------------------------------------------------
 * 
 * Copyright (c) SymphonySoft Limited. All rights reserved.
 * http://www.symphonysoft.com
 * 
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file. 
 *
 */

package org.mule.test.transformers.compression;

import java.util.Arrays;

import org.mule.tck.AbstractTransformerTestCase;
import org.mule.transformers.compression.GZipCompressTransformer;
import org.mule.transformers.compression.GZipUncompressTransformer;
import org.mule.umo.lifecycle.InitialisationException;
import org.mule.umo.transformer.UMOTransformer;
import org.mule.util.compression.GZipCompression;

/**
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */
public class GZipTransformerTestCase extends AbstractTransformerTestCase
{
    private GZipCompression strat;

    protected void setUp() throws Exception
    {
        super.setUp();
        strat = new GZipCompression();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.tck.AbstractTransformerTestCase#getResultData()
     */
    public Object getResultData()
    {
        try {
            return strat.compressByteArray(getTestData().toString().getBytes());
        } catch (Exception e) {
            fail(e.getMessage());
            return null;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.tck.AbstractTransformerTestCase#getTestData()
     */
    public Object getTestData()
    {
        return "the quick brown fox jumped over the lazy dog the quick brown fox jumped over the lazy dog the quick brown fox jumped over the lazy dog";
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.tck.AbstractTransformerTestCase#getTransformers()
     */
    public UMOTransformer getTransformer()
    {
        GZipCompressTransformer transformer = new GZipCompressTransformer();
        return transformer;
    }

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#setUp()
     */

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.tck.AbstractTransformerTestCase#getRoundTripTransformer()
     */
    public UMOTransformer getRoundTripTransformer()
    {
        GZipUncompressTransformer transformer = new GZipUncompressTransformer();
        transformer.setReturnClass(String.class);
        try {
            transformer.initialise();
        } catch (InitialisationException e) {
            fail(e.getMessage());
        }
        return transformer;
    }

    public boolean compareResults(Object src, Object result)
    {
        if (src instanceof byte[]) {
            if (src == null && result == null)
                return true;
            if (src == null || result == null)
                return false;
            return Arrays.equals((byte[]) src, (byte[]) result);
        } else {
            return super.compareResults(src, result);
        }
    }
}
