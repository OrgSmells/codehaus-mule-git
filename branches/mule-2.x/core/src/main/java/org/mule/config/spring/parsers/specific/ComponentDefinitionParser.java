/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.spring.parsers.specific;

import org.mule.config.spring.parsers.AbstractMuleBeanDefinitionParser;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class ComponentDefinitionParser extends AbstractMuleBeanDefinitionParser
{
    Class beanClass;
    
    public ComponentDefinitionParser(Class beanClass)
    {
        this.beanClass = beanClass;
    }
    
    protected Class getBeanClass(Element element)
    {
        return beanClass;
    }

    //@java.lang.Override
    protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder)
    {
        // The model for this component should theoretically be the grandparent node.
        Element parent = (Element) element.getParentNode();
        String modelName = parent.getAttribute(ATTRIBUTE_NAME);
        builder.addPropertyReference("model", modelName);

        super.doParse(element, parserContext, builder);
    }

}
