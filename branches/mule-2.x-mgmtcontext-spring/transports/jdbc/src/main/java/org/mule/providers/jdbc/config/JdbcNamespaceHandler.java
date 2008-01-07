/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.providers.jdbc.config;

import org.mule.config.spring.handlers.AbstractMuleNamespaceHandler;
import org.mule.config.spring.parsers.collection.ChildMapDefinitionParser;
import org.mule.config.spring.parsers.generic.MuleOrphanDefinitionParser;
import org.mule.config.spring.parsers.generic.ParentDefinitionParser;
import org.mule.config.spring.parsers.specific.ObjectFactoryDefinitionParser;
import org.mule.impl.endpoint.URIBuilder;
import org.mule.providers.jdbc.JdbcConnector;

/** Registers Bean Definition Parsers for the "jdbc" namespace. */
public class JdbcNamespaceHandler extends AbstractMuleNamespaceHandler
{

    public static final String QUERY_KEY = "queryKey";
    public static final String[] ADDRESS_ATTRIBUTES = new String[]{QUERY_KEY};

    public void init()
    {
        registerStandardTransportEndpoints(JdbcConnector.JDBC, ADDRESS_ATTRIBUTES).addAlias(QUERY_KEY, URIBuilder.PATH);
        registerBeanDefinitionParser("connector", new MuleOrphanDefinitionParser(JdbcConnector.class, true));
        registerBeanDefinitionParser("dataSource", new ObjectFactoryDefinitionParser("dataSourceFactory"));
        registerBeanDefinitionParser("queries", new ChildMapDefinitionParser("queries"));
        registerBeanDefinitionParser("extractors", new ParentDefinitionParser());
    }

}
