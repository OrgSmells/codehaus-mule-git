/*
 * $Id$
 * ------------------------------------------------------------------------------------------------------
 *
 * Copyright (c) SymphonySoft Limited. All rights reserved.
 * http://www.symphonysoft.com
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.mule.management.agents;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.MuleManager;
import org.mule.MuleRuntimeException;
import org.mule.config.i18n.Message;
import org.mule.config.i18n.Messages;
import org.mule.impl.internal.notifications.ModelNotification;
import org.mule.impl.internal.notifications.ModelNotificationListener;
import org.mule.impl.internal.notifications.NotificationException;
import org.mule.management.mbeans.ComponentService;
import org.mule.management.mbeans.ComponentServiceMBean;
import org.mule.management.mbeans.ConnectorService;
import org.mule.management.mbeans.ConnectorServiceMBean;
import org.mule.management.mbeans.EndpointServiceMBean;
import org.mule.management.mbeans.ModelService;
import org.mule.management.mbeans.ModelServiceMBean;
import org.mule.management.mbeans.MuleConfigurationService;
import org.mule.management.mbeans.MuleConfigurationServiceMBean;
import org.mule.management.mbeans.MuleService;
import org.mule.management.mbeans.MuleServiceMBean;
import org.mule.management.mbeans.StatisticsService;
import org.mule.providers.AbstractConnector;
import org.mule.umo.UMOException;
import org.mule.umo.lifecycle.InitialisationException;
import org.mule.umo.manager.UMOAgent;
import org.mule.umo.manager.UMOServerNotification;
import org.mule.umo.provider.UMOConnector;

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
import java.util.Map;

/**
 * <code>JmxAgent</code> registers MUle Jmx management beans with an MBean
 * server.
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

    private String name = "JMX Agent";
    protected boolean locateServer = true;
    private boolean createServer = true;
    private String connectorServerUrl;
    private MBeanServer mBeanServer;
    private JMXConnectorServer connectorServer;
    private Map connectorServerProperties = null;
    private boolean enableStatistics = true;
    private List registeredMBeans = new ArrayList();
    private boolean serverCreated = false;
    private boolean initialized = false;
    private boolean useInstanceIdAsDomain = true;

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.umo.manager.UMOAgent#getName()
     */
    public String getName()
    {
        return this.name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.umo.manager.UMOAgent#setName(java.lang.String)
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.umo.manager.UMOAgent#getDescription()
     */
    public String getDescription()
    {
        if (connectorServerUrl != null) {
            return "JMX Agent: " + connectorServerUrl;
        } else {
            return "JMX Agent";
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.umo.lifecycle.Initialisable#initialise()
     */
    public void initialise() throws InitialisationException
    {
        if (initialized) {
            return;
        }
        if (!locateServer && !createServer) {
            throw new InitialisationException(new Message(Messages.JMX_CREATE_OR_LOCATE_SHOULD_BE_SET), this);
        }
        if (mBeanServer == null && locateServer) {
            List l = MBeanServerFactory.findMBeanServer(null);
            if (l != null && l.size() > 0) {
                mBeanServer = (MBeanServer) l.get(0);
            }
        }
        if (mBeanServer == null && createServer) {
            mBeanServer = MBeanServerFactory.createMBeanServer();
            serverCreated = true;
        }
        if (mBeanServer == null) {
            throw new InitialisationException(new Message(Messages.JMX_CANT_LOCATE_CREATE_SERVER), this);
        }
        if (connectorServerUrl != null) {
            try {
                JMXServiceURL url = new JMXServiceURL(connectorServerUrl);
                connectorServer = JMXConnectorServerFactory.newJMXConnectorServer(url, connectorServerProperties, mBeanServer);
            } catch (Exception e) {
                throw new InitialisationException(new Message(Messages.FAILED_TO_CREATE_X, "Jmx Connector"), e, this);
            }
        }

        // We need to register all the services once the server has initialised
        ModelNotificationListener l = new ModelNotificationListener() {
            public void onNotification(UMOServerNotification notification)
            {
                if (notification.getAction() == ModelNotification.MODEL_STARTED) {
                    try {
                        registerStatisticsService();
                        registerMuleService();
                        registerConfigurationService();
                        registerModelService();
                        registerComponentServices();
                        registerEndpointServices();
                        registerConnectorServices();
                    } catch (Exception e) {
                        throw new MuleRuntimeException(new Message(Messages.X_FAILED_TO_INITIALISE, "MBeans"), e);
                    }
                }
            }
        };

        try {
                MuleManager.getInstance().registerListener(l);
        } catch (NotificationException e) {
            throw new InitialisationException(e, this);
        }
        initialized = true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.umo.lifecycle.Startable#start()
     */
    public void start() throws UMOException
    {
        if (connectorServer != null) {
            try {
                logger.info("Starting JMX agent connector Server");
                connectorServer.start();
            } catch (Exception e) {
                throw new JmxManagementException(new Message(Messages.FAILED_TO_START_X, "Jmx Connector"), e);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.umo.lifecycle.Stoppable#stop()
     */
    public void stop() throws UMOException
    {
        if (connectorServer != null) {
            try {
                connectorServer.stop();
            } catch (Exception e) {
                throw new JmxManagementException(new Message(Messages.FAILED_TO_STOP_X, "Jmx Connector"), e);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.umo.lifecycle.Disposable#dispose()
     */
    public void dispose()
    {
        if (mBeanServer != null) {
            for (Iterator iterator = registeredMBeans.iterator(); iterator.hasNext();) {
                ObjectName objectName = (ObjectName) iterator.next();
                try {
                    mBeanServer.unregisterMBean(objectName);
                } catch (Exception e) {
                    logger.warn("Failed to unregister MBean: " + objectName + ". Error is: " + e.getMessage());
                }
            }
            if (serverCreated) {
                MBeanServerFactory.releaseMBeanServer(mBeanServer);
            }
            mBeanServer = null;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.umo.manager.UMOAgent#registered()
     */
    public void registered()
    {
        // nothing to do
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.umo.manager.UMOAgent#unregistered()
     */
    public void unregistered()
    {
        // nothing to do
    }

    protected String getDomainName()
    {
        if (MuleManager.getInstance().getId() != null && isUseInstanceIdAsDomain()) {
            return MuleManager.getInstance().getId();
        } else {
            return "org.mule";
        }
    }

    protected void registerStatisticsService() throws NotCompliantMBeanException, MBeanRegistrationException,
            InstanceAlreadyExistsException, MalformedObjectNameException
    {
        ObjectName on = ObjectName.getInstance(getDomainName() + ":type=org.mule.Statistics,name=AllStatistics");
        StatisticsService mBean = new StatisticsService();
        mBean.setManager(MuleManager.getInstance());
        mBean.setEnabled(isEnableStatistics());
        logger.debug("Registering statistics with name: " + on);
        mBeanServer.registerMBean(mBean, on);
        registeredMBeans.add(on);
    }

    protected void registerModelService() throws NotCompliantMBeanException, MBeanRegistrationException,
            InstanceAlreadyExistsException, MalformedObjectNameException
    {
        ModelServiceMBean serviceMBean = new ModelService();
        String name = serviceMBean.getName() + "(" + serviceMBean.getType() + ")";
        ObjectName on = ObjectName.getInstance(getDomainName() + ":type=org.mule.Model,name=" + name);
        logger.debug("Registering model with name: " + on);
        mBeanServer.registerMBean(serviceMBean, on);
        registeredMBeans.add(on);
    }

    protected void registerMuleService() throws NotCompliantMBeanException, MBeanRegistrationException,
            InstanceAlreadyExistsException, MalformedObjectNameException
    {
        ObjectName on = ObjectName.getInstance(getDomainName() + ":type=org.mule.ManagementContext,name=MuleServerInfo");
        MuleServiceMBean serviceMBean = new MuleService();
        logger.debug("Registering mule with name: " + on);
        mBeanServer.registerMBean(serviceMBean, on);
        registeredMBeans.add(on);
    }

    protected void registerConfigurationService() throws NotCompliantMBeanException, MBeanRegistrationException,
            InstanceAlreadyExistsException, MalformedObjectNameException
    {
        ObjectName on = ObjectName.getInstance(getDomainName() + ":type=org.mule.Configuration,name=GlobalConfiguration");
        MuleConfigurationServiceMBean serviceMBean = new MuleConfigurationService();
        logger.debug("Registering configuration with name: " + on);
        mBeanServer.registerMBean(serviceMBean, on);
        registeredMBeans.add(on);
    }

    protected void registerComponentServices() throws NotCompliantMBeanException, MBeanRegistrationException,
            InstanceAlreadyExistsException, MalformedObjectNameException
    {
        Iterator iter = MuleManager.getInstance().getModel().getComponentNames();
        String name;
        while (iter.hasNext()) {
            name = iter.next().toString();
            ObjectName on = ObjectName.getInstance(getDomainName() + ":type=org.mule.Component,name=" + name);
            ComponentServiceMBean serviceMBean = new ComponentService(name);
            logger.debug("Registering component with name: " + on);
            mBeanServer.registerMBean(serviceMBean, on);
            registeredMBeans.add(on);
        }
    }

    protected void registerEndpointServices() throws NotCompliantMBeanException, MBeanRegistrationException,
            InstanceAlreadyExistsException, MalformedObjectNameException
    {
        Iterator iter = MuleManager.getInstance().getConnectors().values().iterator();
        UMOConnector connector;
        List endpointMBeans;
        while (iter.hasNext()) {
            connector = (UMOConnector) iter.next();
            if (connector instanceof AbstractConnector) {
                endpointMBeans = ((AbstractConnector) connector).getEndpointMBeans();
                for (Iterator iterator = endpointMBeans.iterator(); iterator.hasNext();) {
                    EndpointServiceMBean mBean = (EndpointServiceMBean) iterator.next();
                    if (logger.isDebugEnabled()) {
                        logger.debug("Attempting to register service with name: " + getDomainName()
                                + ":type=org.mule.umo.UMOEndpoint,name=" + mBean.getName());
                    }
                    ObjectName on = ObjectName.getInstance(getDomainName() + ":type=org.mule.Endpoint,component=" + mBean.getComponentName()+ ",name=" + mBean.getName());
                    mBeanServer.registerMBean(mBean, on);
                    registeredMBeans.add(on);
                    logger.info("Registered Endpoint Service with name: " + on);
                }
            } else {
                logger.warn("Connector: " + connector
                        + " is not an istance of AbstractConnector, cannot obtain Endpoint MBeans from it");
            }

        }
    }

    protected void registerConnectorServices() throws
                                                MalformedObjectNameException,
                                                NotCompliantMBeanException,
                                                MBeanRegistrationException,
                                                InstanceAlreadyExistsException
    {
        Iterator iter = MuleManager.getInstance().getConnectors().values().iterator();
        while (iter.hasNext()) {
            UMOConnector connector = (UMOConnector) iter.next();
            ConnectorServiceMBean mBean = new ConnectorService(connector);
            final String stringName = getDomainName() + ":type=org.mule.Connector,name=" + mBean.getName();
            if (logger.isDebugEnabled()) {
                logger.debug("Attempting to register service with name: " + stringName);
            }
            ObjectName oName = ObjectName.getInstance(stringName);
            mBeanServer.registerMBean(mBean, oName);
            registeredMBeans.add(oName);
            logger.info("Registered Connector Service with name " + oName);
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

    public Map getConnectorServerProperties() {
        return connectorServerProperties;
    }

    public void setConnectorServerProperties(Map connectorServerProperties) {
        this.connectorServerProperties = connectorServerProperties;
    }

    public boolean isUseInstanceIdAsDomain() {
        return useInstanceIdAsDomain;
    }

    public void setUseInstanceIdAsDomain(boolean useInstanceIdAsDomain) {
        this.useInstanceIdAsDomain = useInstanceIdAsDomain;
    }
}
