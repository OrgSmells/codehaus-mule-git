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
package org.mule.management.agents;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.InitialisationException;
import org.mule.MuleManager;
import org.mule.MuleRuntimeException;
import org.mule.impl.internal.events.ModelEvent;
import org.mule.impl.internal.events.ModelEventListener;
import org.mule.management.mbeans.ComponentService;
import org.mule.management.mbeans.ComponentServiceMBean;
import org.mule.management.mbeans.ModelService;
import org.mule.management.mbeans.ModelServiceMBean;
import org.mule.management.mbeans.MuleConfigurationService;
import org.mule.management.mbeans.MuleConfigurationServiceMBean;
import org.mule.management.mbeans.MuleService;
import org.mule.management.mbeans.MuleServiceMBean;
import org.mule.management.mbeans.StatisticsService;
import org.mule.umo.UMOAgent;
import org.mule.umo.UMOException;
import org.mule.umo.UMOServerEvent;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <code>JmxAgent</code> registers MUle Jmx management beans
 * with an MBean server.
 *
 * @author Guillaume Nodet
 * @version $Revision$
 */
public class JmxAgent implements UMOAgent
{
    /**
     * logger used by this class
     */
    protected static transient Log logger = LogFactory.getLog(JmxAgent.class);

    private String name;
    protected boolean locateServer = true;
    private boolean createServer = true;
    private String connectorServerUrl;
    private MBeanServer mBeanServer;
    private JMXConnectorServer connectorServer;
    private boolean enableStatistics = true;
    private List registeredMBeans = new ArrayList();

    /* (non-Javadoc)
     * @see org.mule.umo.UMOAgent#getName()
     */
    public String getName()
    {
        return this.name;
    }

    /* (non-Javadoc)
     * @see org.mule.umo.UMOAgent#setName(java.lang.String)
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /* (non-Javadoc)
     * @see org.mule.umo.UMOAgent#getDescription()
     */
    public String getDescription()
    {
        if (connectorServerUrl != null)
        {
            return "JMX Agent: " + connectorServerUrl;
        } else
        {
            return "JMX Agent";
        }
    }

    /* (non-Javadoc)
     * @see org.mule.umo.lifecycle.Initialisable#initialise()
     */
    public void initialise() throws InitialisationException
    {
        if (!locateServer && !createServer)
        {
            throw new InitialisationException("At least one of createServer and locateServer property should be set to true");
        }
        if (mBeanServer == null && locateServer)
        {
            List l = MBeanServerFactory.findMBeanServer(null);
            if (l != null && l.size() > 0)
            {
                mBeanServer = (MBeanServer) l.get(0);
            }
        }
        if (mBeanServer == null && createServer)
        {
            mBeanServer = MBeanServerFactory.createMBeanServer();
        }
        if (mBeanServer == null)
        {
            throw new InitialisationException("Could not locate nor create the MBeanServer");
        }
        if (connectorServerUrl != null)
        {
            try
            {
                JMXServiceURL url = new JMXServiceURL(connectorServerUrl);
                connectorServer = JMXConnectorServerFactory.newJMXConnectorServer(url, null, mBeanServer);
            } catch (Exception e)
            {
                throw new InitialisationException("Could not create jmx connector", e);
            }
        }

        //We need to register all the services once the server has initialised
        MuleManager.getInstance().registerListener(new
        ModelEventListener()
        {
            public void onEvent(UMOServerEvent event)
            {
                if (event.getAction() == ModelEvent.MODEL_INITIALISED)
                {
                    try
                    {
                        registerStatisticsService();
                        registerMuleService();
                        //registerConfigurationService();
                        registerModelService();
                        registerComponentServices();
                    } catch (Exception e)
                    {
                        throw new MuleRuntimeException("Could not register mbeans", e);
                    }
                }
            }
        });

    }

    /* (non-Javadoc)
     * @see org.mule.umo.lifecycle.Startable#start()
     */
    public void start() throws UMOException
    {
        if (connectorServer != null)
        {
            try
            {
                connectorServer.start();
            } catch (Exception e)
            {
                throw new JmxManagementException("Could not start jmx connector", e);
            }
        }
    }

    /* (non-Javadoc)
     * @see org.mule.umo.lifecycle.Stoppable#stop()
     */
    public void stop() throws UMOException
    {
        if (connectorServer != null)
        {
            try
            {
                connectorServer.stop();
            } catch (Exception e)
            {
                throw new JmxManagementException("Could not stop jmx connector", e);
            }
        }
    }

    /* (non-Javadoc)
     * @see org.mule.umo.lifecycle.Disposable#dispose()
     */
    public void dispose() throws UMOException
    {
        if(mBeanServer!=null) {
            for (Iterator iterator = registeredMBeans.iterator(); iterator.hasNext();)
            {
                ObjectName objectName = (ObjectName) iterator.next();
                try
                {
                    mBeanServer.unregisterMBean(objectName);
                } catch (Exception e)
                {
                    logger.warn("Failed to unregister MBean: " + objectName + ". Error is: " + e.getMessage());
                }
            }
            MBeanServerFactory.releaseMBeanServer(mBeanServer);
            mBeanServer = null;
        }
    }

/* (non-Javadoc)
	 * @see org.mule.umo.UMOAgent#registered()
	 */
    public void registered()
    {
    }

    /* (non-Javadoc)
	 * @see org.mule.umo.UMOAgent#unregistered()
	 */
    public void unregistered()
    {
    }
    

    protected void registerStatisticsService() throws NotCompliantMBeanException, MBeanRegistrationException, InstanceAlreadyExistsException, MalformedObjectNameException
    {
        ObjectName on = new ObjectName("Mule:type=statistics");
        StatisticsService mBean = new StatisticsService();
        mBean.setManager(MuleManager.getInstance());
        mBean.setEnabled(isEnableStatistics());
        mBeanServer.registerMBean(mBean, on);
        registeredMBeans.add(on);
    }

    protected void registerModelService() throws NotCompliantMBeanException, MBeanRegistrationException, InstanceAlreadyExistsException, MalformedObjectNameException
    {
        ObjectName on = new ObjectName("Mule:type=control,name=ModelService");
        ModelServiceMBean serviceMBean = new ModelService();
        mBeanServer.registerMBean(serviceMBean, on);
        registeredMBeans.add(on);
    }

    protected void registerMuleService() throws NotCompliantMBeanException, MBeanRegistrationException, InstanceAlreadyExistsException, MalformedObjectNameException
    {
        ObjectName on = new ObjectName("Mule:type=control,name=MuleService");
        MuleServiceMBean serviceMBean = new MuleService();
        mBeanServer.registerMBean(serviceMBean, on);
        registeredMBeans.add(on);
    }

    protected void registerConfigurationService() throws NotCompliantMBeanException, MBeanRegistrationException, InstanceAlreadyExistsException, MalformedObjectNameException
    {
        ObjectName on = new ObjectName("Mule:type=control,name=ConfigurationService");
        MuleConfigurationServiceMBean serviceMBean = new MuleConfigurationService();
        mBeanServer.registerMBean(serviceMBean, on);
        registeredMBeans.add(on);
    }

    protected void registerComponentServices() throws NotCompliantMBeanException, MBeanRegistrationException, InstanceAlreadyExistsException, MalformedObjectNameException
    {
        Iterator iter = MuleManager.getInstance().getModel().getComponentNames();
        String name;
        while (iter.hasNext())
        {
            name = iter.next().toString();
            ObjectName on = new ObjectName("Mule:type=control,name=" + name + "ComponentService");
            ComponentServiceMBean serviceMBean = new ComponentService(name);
            mBeanServer.registerMBean(serviceMBean, on);
            registeredMBeans.add(on);
        }

    }


    /**
     * @return Returns the createServer.
     */
    public boolean isCreateServer()
    {
        return createServer;
    }

    /**
     * @param createServer The createServer to set.
     */
    public void setCreateServer(boolean createServer)
    {
        this.createServer = createServer;
    }

    /**
     * @return Returns the locateServer.
     */
    public boolean isLocateServer()
    {
        return locateServer;
    }

    /**
     * @param locateServer The locateServer to set.
     */
    public void setLocateServer(boolean locateServer)
    {
        this.locateServer = locateServer;
    }

    /**
     * @return Returns the connectorServerUrl.
     */
    public String getConnectorServerUrl()
    {
        return connectorServerUrl;
    }

    /**
     * @param connectorServerUrl The connectorServerUrl to set.
     */
    public void setConnectorServerUrl(String connectorServerUrl)
    {
        this.connectorServerUrl = connectorServerUrl;
    }

    /**
     * @return Returns the enableStatistics.
     */
    public boolean isEnableStatistics()
    {
        return enableStatistics;
    }

    /**
     * @param enableStatistics The enableStatistics to set.
     */
    public void setEnableStatistics(boolean enableStatistics)
    {
        this.enableStatistics = enableStatistics;
    }

    /**
     * @return Returns the mBeanServer.
     */
    public MBeanServer getMBeanServer()
    {
        return mBeanServer;
    }

    /**
     * @param mBeanServer The mBeanServer to set.
     */
    public void setMBeanServer(MBeanServer mBeanServer)
    {
        this.mBeanServer = mBeanServer;
    }
}
