/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.config.spring.handlers;

import org.mule.config.spring.parser.specific.AnnotatedServiceDefinitionParser;
import org.mule.config.spring.parsers.generic.OrphanDefinitionParser;
import org.mule.impl.annotations.AnnotationsModel;

/** TODO */
public class AnnotationsNamespaceHandler extends AbstractIgnorableNamespaceHandler
{

    public void init()
    {
        registerBeanDefinitionParser("model", new OrphanDefinitionParser(AnnotationsModel.class, true));
        registerBeanDefinitionParser("service", new AnnotatedServiceDefinitionParser());
        //registerBeanDefinitionParser("annotated-entrypoint-resolver", new ChildDefinitionParser("filter", IsXmlFilter.class));
    }
}
