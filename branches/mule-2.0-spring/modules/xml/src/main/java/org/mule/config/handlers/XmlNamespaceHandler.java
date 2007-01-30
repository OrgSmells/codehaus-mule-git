/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.config.handlers;

import org.mule.config.parsers.JXPathFilterParser;
import org.mule.config.parsers.MapEntryDefinitionParser;
import org.mule.routing.filters.xml.JXPathFilter;

/**
 * TODO
 */
public class XmlNamespaceHandler extends AbstractHierarchicalNamespaceHandler
{

    public void init()
    {
        registerBeanDefinitionParser("jxpath-filter", new JXPathFilterParser("filter", JXPathFilter.class));
        registerBeanDefinitionParser("namespace", new MapEntryDefinitionParser("namespaces"));

    }
}
