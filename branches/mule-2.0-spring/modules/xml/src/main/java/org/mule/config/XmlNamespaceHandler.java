/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.config;

import org.mule.extras.spring.config.handlers.AbstractHierarchicalNamespaceHandler;
import org.mule.extras.spring.config.parsers.SimpleChildDefinitionParser;
import org.mule.config.parsers.NamespaceDefinitionParser;
import org.mule.routing.filters.xml.JXPathFilter;

/**
 * TODO
 */
public class XmlNamespaceHandler extends AbstractHierarchicalNamespaceHandler
{

    public void init()
    {
        registerBeanDefinitionParser("jxpath-filter", new SimpleChildDefinitionParser("filter", JXPathFilter.class));
        registerBeanDefinitionParser("namespace", new NamespaceDefinitionParser());
    }
}
