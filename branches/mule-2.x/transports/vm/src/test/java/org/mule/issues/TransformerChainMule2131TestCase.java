/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.issues;

public class TransformerChainMule2131TestCase extends TransformerChainMule2063TestCase
{

    public void testOutputTransformers() throws Exception
    {
        doTest("test3", TEST3_OUT);
    }

}