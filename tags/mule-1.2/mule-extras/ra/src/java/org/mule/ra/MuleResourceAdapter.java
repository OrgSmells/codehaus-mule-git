/*
 * $Header$
 * $Revision$
 * $Date$
 * ------------------------------------------------------------------------------------------------------
 *
 * Copyright (c) SymphonySoft Limited. All rights reserved.
 * http://www.symphonysoft.com
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.mule.ra;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.MuleManager;
import org.mule.config.ConfigurationBuilder;
import org.mule.config.ConfigurationException;
import org.mule.config.ThreadingProfile;
import org.mule.impl.MuleDescriptor;
import org.mule.impl.endpoint.MuleEndpointURI;
import org.mule.providers.AbstractConnector;
import org.mule.providers.service.ConnectorFactory;
import org.mule.umo.UMODescriptor;
import org.mule.umo.UMOException;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.endpoint.UMOEndpointURI;
import org.mule.umo.manager.UMOManager;
import org.mule.umo.manager.UMOWorkManager;
import org.mule.util.ClassHelper;

import javax.resource.NotSupportedException;
import javax.resource.ResourceException;
import javax.resource.spi.ActivationSpec;
import javax.resource.spi.BootstrapContext;
import javax.resource.spi.ResourceAdapter;
import javax.resource.spi.ResourceAdapterInternalException;
import javax.resource.spi.endpoint.MessageEndpoint;
import javax.resource.spi.endpoint.MessageEndpointFactory;
import javax.transaction.xa.XAResource;
import java.util.HashMap;
import java.util.Map;

/**
 * <code>MuleResourceAdapter</code> TODO
 * 
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */
public class MuleResourceAdapter implements ResourceAdapter
{
    /**
     * logger used by this class
     */
    protected static transient Log logger = LogFactory.getLog(MuleResourceAdapter.class);

    private BootstrapContext bootstrapContext;

    private MuleConnectionRequestInfo info = new MuleConnectionRequestInfo();

    private UMOManager manager;

    private Map endpoints = new HashMap();

    public MuleResourceAdapter()
    {
    }

    /**
     * @see javax.resource.spi.ResourceAdapter#start(javax.resource.spi.BootstrapContext)
     */
    public void start(BootstrapContext bootstrapContext) throws ResourceAdapterInternalException
    {
        this.bootstrapContext = bootstrapContext;
        MuleManager.getConfiguration().setModelType("jca");
        if (info.getConfigurations() != null) {
            if (MuleManager.isInstanciated()) {
                throw new ResourceAdapterInternalException("A manageris already configured, cannot configure a new one using the configurations set on the Resource Adapter");
            } else {
                ConfigurationBuilder builder = null;
                try {
                    builder = (ConfigurationBuilder) ClassHelper.instanciateClass(info.getConfigurationBuilder(),
                                                                                  ClassHelper.NO_ARGS);

                } catch (Exception e) {
                    throw new ResourceAdapterInternalException("Failed to instanciate configurationBuilder class: "
                            + info.getConfigurationBuilder(), e);
                }

                try {

                    manager = builder.configure(info.getConfigurations());
                } catch (ConfigurationException e) {
                    throw new ResourceAdapterInternalException("Failed to load configurations: "
                            + info.getConfigurations(), e);
                }
            }
        }
        manager = MuleManager.getInstance();
    }

    /**
     * @see javax.resource.spi.ResourceAdapter#stop()
     */
    public void stop()
    {
        manager.dispose();
        manager = null;
        bootstrapContext = null;
    }

    /**
     * @return the bootstrap context for this adapter
     */
    public BootstrapContext getBootstrapContext()
    {
        return bootstrapContext;
    }

    /**
     * @see javax.resource.spi.ResourceAdapter#endpointActivation(javax.resource.spi.endpoint.MessageEndpointFactory,
     *      javax.resource.spi.ActivationSpec)
     */
    public void endpointActivation(MessageEndpointFactory endpointFactory, ActivationSpec activationSpec)
            throws ResourceException
    {
        if (activationSpec.getResourceAdapter() != this) {
            throw new ResourceException("ActivationSpec not initialized with this ResourceAdapter instance");
        }

        if (activationSpec.getClass().equals(MuleActivationSpec.class)) {

            try {
                UMOEndpointURI uri = new MuleEndpointURI(((MuleActivationSpec) activationSpec).getEndpoint());
                UMOEndpoint endpoint = ConnectorFactory.createEndpoint(uri, UMOEndpoint.ENDPOINT_TYPE_RECEIVER);

                ((AbstractConnector) endpoint.getConnector()).getReceiverThreadingProfile()
                                                             .setWorkManagerFactory(new ThreadingProfile.WorkManagerFactory() {
                                                                 public UMOWorkManager createWorkManager(ThreadingProfile profile,
                                                                                                         String name)
                                                                 {
                                                                     return new DelegateWorkManager(bootstrapContext.getWorkManager());

                                                                 }
                                                             });
                // todo manage transactions
                MessageEndpoint messageEndpoint = null;
                messageEndpoint = endpointFactory.createEndpoint(null);

                String name = "JcaComponent#" + messageEndpoint.hashCode();
                MuleDescriptor descriptor = new MuleDescriptor(name);
                descriptor.getInboundRouter().addEndpoint(endpoint);
                descriptor.setImplementationInstance(messageEndpoint);
                MuleManager.getInstance().getModel().registerComponent(descriptor);

                MuleEndpointKey key = new MuleEndpointKey(endpointFactory, (MuleActivationSpec) activationSpec);

                endpoints.put(key, descriptor);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        } else {
            throw new NotSupportedException("That type of ActicationSpec not supported: " + activationSpec.getClass());
        }

    }

    /**
     * @see javax.resource.spi.ResourceAdapter#endpointDeactivation(javax.resource.spi.endpoint.MessageEndpointFactory,
     *      javax.resource.spi.ActivationSpec)
     */
    public void endpointDeactivation(MessageEndpointFactory endpointFactory, ActivationSpec activationSpec)
    {

        if (activationSpec.getClass().equals(MuleActivationSpec.class)) {
            MuleEndpointKey key = new MuleEndpointKey(endpointFactory, (MuleActivationSpec) activationSpec);
            UMODescriptor descriptor = (UMODescriptor) endpoints.get(key);
            if (descriptor == null) {
                logger.warn("No endpoint was rgistered with key: " + key);
                return;
            }
            try {
                manager.getModel().unregisterComponent(descriptor);
            } catch (UMOException e) {
                logger.error(e.getMessage(), e);
            }

        }

    }

    /**
     * We only connect to one resource manager per ResourceAdapter instance, so
     * any ActivationSpec will return the same XAResource.
     * 
     * @see javax.resource.spi.ResourceAdapter#getXAResources(javax.resource.spi.ActivationSpec[])
     */
    public XAResource[] getXAResources(ActivationSpec[] activationSpecs) throws ResourceException
    {
        return new XAResource[] {};
    }

    /**
     * @return
     */
    public String getPassword()
    {
        return info.getPassword();
    }

    /**
     * @return
     */
    public String getConfigurations()
    {
        return info.getConfigurations();
    }

    /**
     * @return
     */
    public String getUserName()
    {
        return info.getUserName();
    }

    /**
     * @param password
     */
    public void setPassword(String password)
    {
        info.setPassword(password);
    }

    /**
     * @param configurations
     */
    public void setConfigurations(String configurations)
    {
        info.setConfigurations(configurations);
    }

    /**
     * @param userid
     */
    public void setUserName(String userid)
    {
        info.setUserName(userid);
    }

    public String getConfiguratinbuilder()
    {
        return info.getConfigurationBuilder();
    }

    public void setConfigurationBuilder(String configbuilder)
    {
        info.setConfigurationBuilder(configbuilder);
    }

    /**
     * @return Returns the info.
     */
    public MuleConnectionRequestInfo getInfo()
    {
        return info;
    }

    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (!(o instanceof MuleResourceAdapter))
            return false;

        final MuleResourceAdapter muleResourceAdapter = (MuleResourceAdapter) o;

        if (info != null ? !info.equals(muleResourceAdapter.info) : muleResourceAdapter.info != null)
            return false;

        return true;
    }

    public int hashCode()
    {
        return (info != null ? info.hashCode() : 0);
    }

}
