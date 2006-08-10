/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the BSD style
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transformers;

import org.mule.umo.transformer.TransformerException;

/**
 * <code>NoActionTransformer</code> doesn't do any transformation on the source
 * object and returns the source as the result.
 *
 * This can be used to overload the default transform for an endpoint
 * 
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */
public final class NoActionTransformer extends AbstractTransformer
{
    /**
     * Serial version
     */
    private static final long serialVersionUID = -3777682742145612394L;

    public NoActionTransformer()
    {
        registerSourceType(Object.class);
        setReturnClass(Object.class);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.umo.transformer.UMOTransformer#transform(java.lang.Object)
     */
    public Object doTransform(Object src, String encoding) throws TransformerException
    {
        return src;
    }

    public boolean isAcceptNull() {
        return true;
    }
}
