/*
 * $Header$
 * $Revision$
 * $Date$
 * ------------------------------------------------------------------------------------------------------
 *
 * Copyright (c) Cubis Limited. All rights reserved.
 * http://www.cubis.co.uk
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.mule.providers.soap.axis.extensions;

import org.apache.axis.AxisEngine;
import org.apache.axis.ConfigurationException;
import org.apache.axis.EngineConfiguration;
import org.apache.axis.configuration.SimpleProvider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <code>MuleConfigProvider</code> is needed because the Simple Provider does
 * not list services in the defaultConfiguration
 *
 * @author <a href="mailto:ross.mason@cubis.co.uk">Ross Mason</a>
 * @version $Revision$
 */
public class MuleConfigProvider extends SimpleProvider
{
    private EngineConfiguration engineConfiguration;

    public MuleConfigProvider(EngineConfiguration engineConfiguration)
    {
        super(engineConfiguration);
        this.engineConfiguration = engineConfiguration;
    }

    /**
     * Configure an AxisEngine.  Right now just calls the default
     * configuration if there is one, since we don't do anything special.
     */
    public void configureEngine(AxisEngine engine) throws ConfigurationException
    {
        engineConfiguration.configureEngine(engine);
        super.configureEngine(engine);
    }

    public Iterator getAxisDeployedServices() throws ConfigurationException
    {
        return engineConfiguration.getDeployedServices();
    }

    public Iterator getAllDeployedServices() throws ConfigurationException
    {
        List services = new ArrayList();
        Iterator iter = engineConfiguration.getDeployedServices();
        while(iter.hasNext()) {
            services.add(iter.next());
        }
        iter = super.getDeployedServices();
        while(iter.hasNext()) {
            services.add(iter.next());
        }
        return services.iterator();
    }
}
