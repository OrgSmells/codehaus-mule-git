/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.tck.testmodels.fruit;

import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractTransformer;

public class ObjectToFruitLover extends AbstractTransformer
{

    public ObjectToFruitLover()
    {
        this.setReturnClass(FruitLover.class);
        this.registerSourceType(String.class);
        this.registerSourceType(FruitLover.class);
    }

    public Object doTransform(Object src, String encoding) throws TransformerException
    {
        if (src instanceof FruitLover)
        {
            return src;
        }
        else
        {
            return new FruitLover((String) src);
        }
    }

}
