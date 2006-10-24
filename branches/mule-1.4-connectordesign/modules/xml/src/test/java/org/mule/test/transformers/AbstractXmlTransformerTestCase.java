/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.test.transformers;

import org.custommonkey.xmlunit.XMLAssert;
import org.mule.tck.AbstractTransformerTestCase;

/**
 * Use this superclass if you intend to compare Xml contents.
 */
public abstract class AbstractXmlTransformerTestCase extends AbstractTransformerTestCase
{

    /**
     * Different JVMs serialize fields to XML in a different order. Make sure we DO
     * NOT use direct (and too strict String comparison). Instead, compare xml
     * contents, while the position of the node may differ in scope of the same node
     * level (still, it does not violate xml spec). Overridden from the superclass.
     * 
     * @throws Exception if any error
     */
    public void testTransform() throws Exception
    {
        Object result = getTransformer().transform(getTestData());
        assertNotNull(result);
        XMLAssert.assertXMLEqual("Xml documents have different data.", (String)getResultData(),
            (String)result);
    }

    protected String normalizeString(String rawString)
    {
        return rawString.replaceAll("\r\n", "").replaceAll("\n", "");
    }

}
