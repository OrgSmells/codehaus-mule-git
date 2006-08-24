/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the BSD style
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.extras.spring.config;

import java.io.IOException;
import java.util.Properties;

import org.mule.MuleManager;
import org.mule.config.ConfigurationBuilder;
import org.mule.config.ConfigurationException;
import org.mule.config.MuleProperties;
import org.mule.config.ReaderResource;
import org.mule.config.i18n.Message;
import org.mule.config.i18n.Messages;
import org.mule.umo.UMOException;
import org.mule.umo.manager.UMOManager;
import org.mule.util.PropertiesUtils;
import org.springframework.util.StringUtils;

/**
 * <code>SpringConfigurationBuilder</code> Enables Mule to be loaded from as
 * Spring context. Multiple configuration files can be loaded from this builder
 * (specified as a comma-separated list) the files can be String Beans documents
 * or Mule Xml Documents or a combination of both.
 *
 * Any Mule Xml documents will be transformed at run-time in to Spring Bean
 * documents before the bean definitions are loaded. Make sure that the DTD
 * definitions for each of the document types are declared in the documents.
 *
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 */
public class SpringConfigurationBuilder implements ConfigurationBuilder
{
    /**
     *
     * Will configure a UMOManager based on the configurations made available
     * through Readers
     *
     * @param configResources an array of Readers
     * @return A configured UMOManager
     * @throws org.mule.config.ConfigurationException
     *
     */
    public UMOManager configure(ReaderResource[] configResources, Properties startupProperties) throws ConfigurationException
    {
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * @deprecated Please use configure(String configResources, String startupPropertiesFile) instead.
     */
    public UMOManager configure(String configResources) throws ConfigurationException {
        return configure(configResources, null);
    }

    public UMOManager configure(String configResource, String startupPropertiesFile) throws ConfigurationException {
        // Load startup properties if any.
        if (startupPropertiesFile != null) {
            try {
                Properties startupProperties = PropertiesUtils.loadProperties(startupPropertiesFile, getClass());
                ((MuleManager) MuleManager.getInstance()).addProperties(startupProperties);
            } catch (IOException e) {
                throw new ConfigurationException(new Message(Messages.FAILED_TO_START_X, "Mule server from builder"), e);
            }
        }

        if (configResource == null) {
            throw new ConfigurationException(new Message(Messages.X_IS_NULL, "Configuration Resource"));
        }
        String[] resources = StringUtils.tokenizeToStringArray(configResource, ",;", true, true);

        MuleManager.getConfiguration().setConfigResources(resources);
        new MuleApplicationContext(resources);
        try {
            if(System.getProperty(MuleProperties.MULE_START_AFTER_CONFIG_SYSTEM_PROPERTY, "true").equalsIgnoreCase("true")) {
                 MuleManager.getInstance().start();
            }
        } catch (UMOException e) {
            throw new ConfigurationException(new Message(Messages.FAILED_TO_START_X, "Mule server from builder"), e);
        }
        return MuleManager.getInstance();
    }

    /**
     * Indicate whether this ConfigurationBulder has been configured yet
     *
     * @return <code>true</code> if this ConfigurationBulder has been
     *         configured
     */
    public boolean isConfigured()
    {
        return MuleManager.isInstanciated();
    }
}
