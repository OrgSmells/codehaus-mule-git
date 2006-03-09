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

package org.mule.transformers.compression;

import org.mule.transformers.AbstractTransformer;
import org.mule.util.compression.CompressionStrategy;

/**
 * <code>AbstractCompressionTransformer</code> Is a base class for all
 * transformers. Transformations transform one object into another. This base
 * class provides facilities for compressing and uncompressing messages.
 * 
 * @author Ross Mason
 * @version $Revision$
 */

public abstract class AbstractCompressionTransformer extends AbstractTransformer
{
    private CompressionStrategy strategy;

    /**
     * default constructor required for discovery
     */
    public AbstractCompressionTransformer()
    {
        super();
    }

    public CompressionStrategy getStrategy()
    {
        return strategy;
    }

    public void setStrategy(CompressionStrategy strategy)
    {
        this.strategy = strategy;
    }

}
