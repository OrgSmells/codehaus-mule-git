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

import org.mule.config.parsers.FilterDefinitionParser;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * TODO document
 */
public class FilterNamespaceHandler extends NamespaceHandlerSupport
{


    public void init()
    {
        FilterDefinitionParser parser = new FilterDefinitionParser();
        registerBeanDefinitionParser("and", parser);
        registerBeanDefinitionParser("or", parser);
        registerBeanDefinitionParser("not", parser);
        registerBeanDefinitionParser("regex", parser);
        registerBeanDefinitionParser("jxpath", parser);
        registerBeanDefinitionParser("message-property", parser);
        registerBeanDefinitionParser("payload-type", parser);
        registerBeanDefinitionParser("wildcard", parser);
        //TODO
        registerBeanDefinitionParser("ognl", parser);
        //TODO
        //registerBeanDefinitionParser("xpath", parser);
        //registerBeanDefinitionParser("xquery", parser);
    }


}
