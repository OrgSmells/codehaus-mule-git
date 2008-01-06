/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.providers.multicast.config;

import org.mule.config.spring.parsers.generic.MuleOrphanDefinitionParser;
import org.mule.config.spring.parsers.specific.URIBuilder;
import org.mule.config.spring.handlers.AbstractMuleNamespaceHandler;
import org.mule.providers.multicast.MulticastConnector;
import org.mule.providers.udp.UdpConnector;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Reigsters a Bean Definition Parser for handling <code><udp:connector></code> elements.
 *
 */
public class MulticastNamespaceHandler extends AbstractMuleNamespaceHandler
{

    public void init()
    {
        registerStandardTransportEndpoints(MulticastConnector.MULTICAST, URIBuilder.SOCKET_ATTRIBUTES);
        registerBeanDefinitionParser("connector", new MuleOrphanDefinitionParser(MulticastConnector.class, true));
    }

}