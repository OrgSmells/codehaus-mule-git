/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.spring.parsers.processors;

import org.mule.config.spring.parsers.assembly.BeanAssembler;
import org.mule.config.spring.parsers.assembly.BeanAssemblerFactory;
import org.mule.config.spring.parsers.assembly.configuration.PropertyConfiguration;

import org.springframework.beans.factory.config.BeanDefinition;
import org.w3c.dom.Element;

/**
 * This iterates over the child elements, generates beans, and sets them on the current bean via the
 * setter given.  So presumably there's either a single child or the destination is a collection.
 *
 * <p>Since this handles the iteratino over children explicitly you need to set the flag
 * {@link org.mule.config.spring.MuleHierarchicalBeanDefinitionParserDelegate#MULE_NO_RECURSE}
 * on the parser.
 */
public class NamedSetterChildElementIterator extends AbstractChildElementIterator
{

    private String setter;

    public NamedSetterChildElementIterator(String setter, BeanAssemblerFactory beanAssemblerFactory, PropertyConfiguration configuration)
    {
        super(beanAssemblerFactory, configuration);
        this.setter = setter;
    }

    protected void insertBean(BeanAssembler targetAssembler, BeanDefinition childBean, Element parent, Element child)
    {
        targetAssembler.extendTarget(setter, setter, childBean);
    }

}
