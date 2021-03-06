/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.transport.file.config;

import org.mule.config.spring.handlers.AbstractMuleNamespaceHandler;
import org.mule.config.spring.parsers.generic.ChildDefinitionParser;
import org.mule.config.spring.parsers.generic.MuleOrphanDefinitionParser;
import org.mule.endpoint.URIBuilder;
import org.mule.transport.file.FileConnector;
import org.mule.transport.file.FilenameParser;
import org.mule.transport.file.filters.FilenameWildcardFilter;
import org.mule.transport.file.transformers.FileToByteArray;
import org.mule.transport.file.transformers.FileToString;

/**
 * Reigsters a Bean Definition Parser for handling <code><tcp:connector></code> elements.
 *
 */
public class FileNamespaceHandler extends AbstractMuleNamespaceHandler
{

    public void init()
    {
        registerStandardTransportEndpoints(FileConnector.FILE, URIBuilder.PATH_ATTRIBUTES);
        registerConnectorDefinitionParser(FileConnector.class);
        registerBeanDefinitionParser("filename-parser",
                    new ChildDefinitionParser("filenameParser", null, FilenameParser.class));
        registerBeanDefinitionParser("file-to-byte-array-transformer", new MuleOrphanDefinitionParser(FileToByteArray.class, false));
        registerBeanDefinitionParser("file-to-string-transformer", new MuleOrphanDefinitionParser(FileToString.class, false));
        registerBeanDefinitionParser("filter-filename-wildcard", new ChildDefinitionParser("filter", FilenameWildcardFilter.class));
    }

}