/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.extras.acegi.config;

import org.mule.api.config.MuleProperties;
import org.mule.config.spring.parsers.collection.ChildMapEntryDefinitionParser;
import org.mule.config.spring.parsers.generic.ChildDefinitionParser;
import org.mule.config.spring.parsers.generic.DescendentDefinitionParser;
import org.mule.config.spring.parsers.generic.NamedDefinitionParser;
import org.mule.extras.acegi.AcegiProviderAdapter;
import org.mule.extras.acegi.filters.http.HttpBasicAuthenticationFilter;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Registers a Bean Definition Parser for handling Acegi related elements.
 */
public class AcegiNamespaceHandler extends NamespaceHandlerSupport
{
    public void init()
    {
        registerBeanDefinitionParser("security-manager", new NamedDefinitionParser(MuleProperties.OBJECT_SECURITY_MANAGER));
        registerBeanDefinitionParser("delegate-security-provider", new ChildDefinitionParser("provider", AcegiProviderAdapter.class));
        registerBeanDefinitionParser("http-security-filter", new DescendentDefinitionParser("securityFilter", HttpBasicAuthenticationFilter.class));
        registerBeanDefinitionParser("security-property", new ChildMapEntryDefinitionParser("securityProperty", "name", "value"));
    }

}