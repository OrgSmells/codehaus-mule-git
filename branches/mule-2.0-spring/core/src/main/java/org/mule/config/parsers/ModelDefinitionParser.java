/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.config.parsers;

import org.mule.impl.model.ModelFactory;
import org.mule.registry.ServiceException;

import org.w3c.dom.Element;

/**
 * TODO
 */
public class ModelDefinitionParser extends AbstractMuleSingleBeanDefinitionParser
{
    private String type;

    public ModelDefinitionParser(String type)
    {
        this.type = type;
    }

    protected Class getBeanClass(Element element)
    {
        try
        {
            return ModelFactory.getModelClass(type);
        }
        catch (ServiceException e)
        {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}
