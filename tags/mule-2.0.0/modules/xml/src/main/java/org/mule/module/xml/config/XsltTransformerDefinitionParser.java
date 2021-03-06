/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.xml.config;

import org.mule.config.spring.parsers.generic.MuleOrphanDefinitionParser;
import org.mule.module.xml.transformer.XsltTransformer;

public class XsltTransformerDefinitionParser extends MuleOrphanDefinitionParser
{
    public XsltTransformerDefinitionParser()
    {
        super(XsltTransformer.class, false);
        addAlias("transformerFactoryClass", "xslTransformerFactory");
    }

}
