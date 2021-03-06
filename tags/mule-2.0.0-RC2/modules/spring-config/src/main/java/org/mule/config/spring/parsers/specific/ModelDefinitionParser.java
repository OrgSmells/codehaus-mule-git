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

import org.mule.api.model.Model;
import org.mule.api.registry.ServiceException;
import org.mule.config.spring.parsers.AbstractMuleBeanDefinitionParser;
import org.mule.model.ModelFactory;
import org.mule.util.ClassUtils;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.w3c.dom.Element;

/**
 * Creates a FactoryBean that will discover the Model class to instantiate from the class path.
 */
public class ModelDefinitionParser extends AbstractMuleBeanDefinitionParser
{

    private String type;

    public ModelDefinitionParser(String type)
    {
        this.type = type;
        this.singleton = true;
    }

    protected Class getBeanClass(Element element)
    {
        return ModelFactoryBean.class;
    }


    public class ModelFactoryBean extends AbstractFactoryBean
    {

        private Model model;


        public Class getObjectType()
        {
            try
            {
                return ModelFactory.getModelClass(type);
            }
            catch (ServiceException e)
            {
                throw new BeanCreationException("Failed to load model class", e);
            }
        }

        protected Object createInstance() throws Exception
        {
            model = (Model) ClassUtils.instanciateClass(getObjectType(), ClassUtils.NO_ARGS);
            return model;
        }

        //@java.lang.Override
        public void afterPropertiesSet() throws Exception
        {
            super.afterPropertiesSet();
            model.initialise();
        }

        //@java.lang.Override
        public void destroy() throws Exception
        {
            super.destroy();
            model.dispose();
        }
    }

}
