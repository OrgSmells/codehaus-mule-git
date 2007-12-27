/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.spring.parsers.delegate;

import org.mule.config.spring.parsers.generic.AutoIdUtils;
import org.mule.config.spring.parsers.MuleDefinitionParser;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * This encapsulates several definition parsers, selected depending on the parent element
 * in the DOM.
 */
public class ParentContextDefinitionParser extends AbstractParallelDelegatingDefinitionParser
{

    private Map parsers = new HashMap();
    private MuleDefinitionParser otherwise = null;

    public ParentContextDefinitionParser(String context, MuleDefinitionParser parser)
    {
        and(context, parser);
    }

    public ParentContextDefinitionParser and(String context, MuleDefinitionParser parser)
    {
        StringTokenizer names = new StringTokenizer(context);
        while (names.hasMoreTokens())
        {
            parsers.put(names.nextToken(), parser);
        }
        addDelegate(parser);
        return this;
    }

    public ParentContextDefinitionParser otherwise(MuleDefinitionParser otherwise)
    {
        this.otherwise = otherwise;
        return this;
    }

    protected MuleDefinitionParser getDelegate(Element element, ParserContext parserContext)
    {
        String context = element.getParentNode().getLocalName();
        if (parsers.containsKey(context))
        {
            return (MuleDefinitionParser) parsers.get(context);
        }
        else if (null != otherwise)
        {
            return otherwise;
        }
        else
        {
            throw new IllegalStateException("No parser defined for " + element.getLocalName()
                    + " in the context " + context);
        }
    }

    protected MuleDefinitionParser getOtherwise()
    {
        return otherwise;
    }

}