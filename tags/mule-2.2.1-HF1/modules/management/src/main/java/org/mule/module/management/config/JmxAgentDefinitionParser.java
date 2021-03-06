/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.module.management.config;

import org.mule.config.spring.parsers.AbstractMuleBeanDefinitionParser;
import org.mule.config.spring.parsers.assembly.BeanAssembler;
import org.mule.config.spring.parsers.processors.ProvideDefaultNameFromElement;
import org.mule.module.management.agent.JmxAgent;
import org.mule.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class JmxAgentDefinitionParser extends AbstractMuleBeanDefinitionParser
{
    public static final String CONNECTOR_SERVER = "connector-server";

    public JmxAgentDefinitionParser()
    {
        singleton = true;
        addAlias("server", "mBeanServer");
        registerPreProcessor(new ProvideDefaultNameFromElement());
    }

    protected Class getBeanClass(Element element)
    {
        return JmxAgent.class;
    }

    protected void postProcess(ParserContext context, BeanAssembler assembler, Element element)
    {
        NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);
            if (CONNECTOR_SERVER.equals(node.getLocalName())) {
                assembler.extendBean("connectorServerUrl", ((Element) node).getAttribute("url"), false);
                String rebind = ((Element) node).getAttribute("rebind");
                if (!StringUtils.isEmpty(rebind)) {
                    Map csProps = new HashMap();
                    csProps.put("jmx.remote.jndi.rebind", rebind);
                    assembler.extendBean("connectorServerProperties", csProps, false);
                }
            }
        }
        super.postProcess(context, assembler, element);
    }

}
