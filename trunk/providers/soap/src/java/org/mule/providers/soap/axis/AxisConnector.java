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
package org.mule.providers.soap.axis;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.axis.configuration.FileProvider;
import org.apache.axis.configuration.SimpleProvider;
import org.apache.axis.deployment.wsdd.WSDDConstants;
import org.apache.axis.deployment.wsdd.WSDDProvider;
import org.apache.axis.server.AxisServer;
import org.mule.MuleManager;
import org.mule.config.i18n.Message;
import org.mule.config.i18n.Messages;
import org.mule.impl.ImmutableMuleEndpoint;
import org.mule.impl.MuleDescriptor;
import org.mule.impl.endpoint.MuleEndpoint;
import org.mule.impl.internal.events.ModelEvent;
import org.mule.impl.internal.events.ModelEventListener;
import org.mule.providers.AbstractServiceEnabledConnector;
import org.mule.providers.soap.axis.extensions.MuleConfigProvider;
import org.mule.providers.soap.axis.extensions.WSDDJavaMuleProvider;
import org.mule.umo.UMOComponent;
import org.mule.umo.UMOException;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.endpoint.UMOEndpointURI;
import org.mule.umo.lifecycle.InitialisationException;
import org.mule.umo.manager.UMOServerEvent;
import org.mule.umo.provider.UMOMessageReceiver;
import org.mule.util.ClassHelper;

/**
 * <code>AxisConnector</code> is used to maintain one or more Services for
 * Axis server instance.
 * 
 * Some of the Axis specific service initialisation code was adapted from the
 * Ivory project (http://ivory.codehaus.org). Thanks guys :)
 * 
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */
public class AxisConnector extends AbstractServiceEnabledConnector implements ModelEventListener
{
    public static final QName QNAME_MULERPC_PROVIDER = new QName(WSDDConstants.URI_WSDD_JAVA, "Mule");
    public static final QName QNAME_MULE_TYPE_MAPPINGS = new QName("http://www.muleumo.org/ws/mappings", "Mule");

    public static final String DEFAULT_MULE_AXIS_SERVER_CONFIG = "mule-axis-server-config.wsdd";
    public static final String DEFAULT_MULE_AXIS_CLIENT_CONFIG = "mule-axis-client-config.wsdd";
    public static final String AXIS_SERVICE_COMPONENT_NAME = "_axisServiceComponent";

    public static final String SERVICE_PROPERTY_COMPONENT_NAME = "componentName";
    public static final String SERVICE_PROPERTY_SERVCE_PATH = "servicePath";

    public static final String ENDPOINT_COUNTERS_PROPERTY = "endpointCounters";

    private String serverConfig;
    private AxisServer axisServer;
    private SimpleProvider serverProvider;
    // Client configuration currently not used but the endpoint should
    // probably support configuration of the client too
    private String clientConfig;
    private SimpleProvider clientProvider;

    private List beanTypes;
    private MuleDescriptor axisDescriptor;

    public AxisConnector()
    {
        super();
    }

    public void doInitialise() throws InitialisationException
    {
        super.doInitialise();
        MuleManager.getInstance().registerListener(this);

        if (serverConfig == null)
            serverConfig = DEFAULT_MULE_AXIS_SERVER_CONFIG;
        if (clientConfig == null)
            clientConfig = DEFAULT_MULE_AXIS_CLIENT_CONFIG;
        serverProvider = createAxisProvider(serverConfig);
        clientProvider = createAxisProvider(clientConfig);

        // Create the AxisServer
        axisServer = new AxisServer(serverProvider);

        // Register the Mule service serverProvider
        WSDDProvider.registerProvider(QNAME_MULERPC_PROVIDER, new WSDDJavaMuleProvider(this));
    }

    protected SimpleProvider createAxisProvider(String config) throws InitialisationException
    {
        InputStream is = null;
        File f = new File(config);
        if (f.exists()) {
            try {
                is = new FileInputStream(f);
            } catch (FileNotFoundException e) {
                // ignore
            }
        } else {
            is = ClassHelper.getResourceAsStream(config, getClass());
        }
        FileProvider fileProvider = new FileProvider(config);
        if (is != null) {
            fileProvider.setInputStream(is);
        } else {
            throw new InitialisationException(new Message(Messages.FAILED_LOAD_X, "Axis Configuration: " + config),
                                              this);
        }
        /*
         * Wrap the FileProvider with a SimpleProvider so we can prgrammatically
         * configure the Axis server (you can only use wsdd descriptors with the
         * FileProvider)
         */
        return new MuleConfigProvider(fileProvider);
    }

    public String getProtocol()
    {
        return "axis";
    }

    public AxisMessageReceiver getReceiver(String name)
    {
        return (AxisMessageReceiver) receivers.get(name);
    }

    /**
     * The method determines the key used to store the receiver against.
     * 
     * @param component the component for which the endpoint is being registered
     * @param endpoint the endpoint being registered for the component
     * @return the key to store the newly created receiver against. In this case
     *         it is the component name, which is equivilent to the Axis service
     *         name.
     */
    protected Object getReceiverKey(UMOComponent component, UMOEndpoint endpoint)
    {
        return component.getDescriptor().getName();
    }

    public UMOMessageReceiver createReceiver(UMOComponent component, UMOEndpoint endpoint) throws Exception
    {
        // this is always initialisaed as synchronous as ws invocations should
        // always execute in a single thread unless the endpont has explicitly
        // been set to run asynchronously
        if (endpoint instanceof ImmutableMuleEndpoint
                && !((ImmutableMuleEndpoint) endpoint).isSynchronousExplicitlySet()) {
            if (!endpoint.isSynchronous()) {
                if (logger.isDebugEnabled()) {
                    logger.debug("overriding endpoint synchronicity and setting it to true. Web service requests are executed in a single thread");
                }
                endpoint.setSynchronous(true);
            }
        }

        return super.createReceiver(component, endpoint);
    }

    protected void unregisterReceiverWithMuleService(UMOMessageReceiver receiver, UMOEndpointURI ep)
            throws UMOException
    {
        String endpointKey = getCounterEndpointKey(receiver.getEndpointURI());

        Map endpointCounters = (Map) axisDescriptor.getProperties().get(ENDPOINT_COUNTERS_PROPERTY);
        if (endpointCounters == null) {
            logger.error("There are no endpoints registered on the Axis service descriptor");
            return;
        }

        Integer count = (Integer) endpointCounters.get(endpointKey);
        if (count == null) {
            logger.error("There are no services registered on: " + endpointKey);
            return;
        }

        if (count.intValue() > 1) {
            logger.warn("There are '" + count.intValue() + "' services registered on endpoint: " + endpointKey
                    + ". Not unregistering the endpoint at this time");
            count = new Integer(count.intValue() - 1);
            endpointCounters.put(endpointKey, count);
            return;
        } else {
            endpointCounters.remove(endpointKey);

            for (Iterator iterator = axisDescriptor.getInboundRouter().getEndpoints().iterator(); iterator.hasNext();) {
                UMOEndpoint umoEndpoint = (UMOEndpoint) iterator.next();
                if (endpointKey.startsWith(umoEndpoint.getEndpointURI().getAddress()))
                    logger.info("Unregistering Axis endpoint: " + endpointKey + " for service: "
                            + receiver.getComponent().getDescriptor().getName());
                try {
                    umoEndpoint.getConnector().unregisterListener(receiver.getComponent(), receiver.getEndpoint());
                } catch (Exception e) {
                    logger.error("Failed to unregistering Axis endpoint: " + endpointKey + " for service: "
                            + receiver.getComponent().getDescriptor().getName() + ". Error is: " + e.getMessage(), e);
                }
                break;
            }
        }
    }

    protected void registerReceiverWithMuleService(UMOMessageReceiver receiver, UMOEndpointURI ep) throws UMOException
    {
        // If this is the first receiver we need to create the Axis service
        // component
        // this will be registered with Mule when the Connector starts
        if (axisDescriptor == null) {
            // See if the axis descriptor has already been added. This allows
            // developers to override the default configuration, say to increase
            // the threadpool
            axisDescriptor = (MuleDescriptor) MuleManager.getInstance()
                                                         .getModel()
                                                         .getDescriptor(AXIS_SERVICE_COMPONENT_NAME);
            if (axisDescriptor == null) {
                axisDescriptor = new MuleDescriptor(AXIS_SERVICE_COMPONENT_NAME);
                axisDescriptor.setImplementation(AxisServiceComponent.class.getName());
            } else {
                // Lets unregister the 'template' instance, configure it and
                // then register
                // again later
                MuleManager.getInstance().getModel().unregisterComponent(axisDescriptor);
            }
            // if the axis server hasn't been set, set it now. The Axis server
            // may be set
            // externally
            if (axisDescriptor.getProperties().get("axisServer") == null) {
                axisDescriptor.getProperties().put("axisServer", axisServer);
            }
            axisDescriptor.setContainerManaged(false);
        }
        // No determine if the endpointUri requires a new connector to be
        // registed
        // in the case of http we only need to register the new endpointUri if
        // the
        // port is different
        String endpoint = receiver.getEndpointURI().getAddress();
        boolean startsWith = false;
        String scheme = ep.getScheme().toLowerCase();
        if (scheme.equals("http") || scheme.equals("tcp")) {
            endpoint = scheme + "://" + ep.getHost() + ":" + ep.getPort();
            startsWith = true;
            // if we are using a socket based endpointUri make sure it is
            // running
            // synchronously by default
            String sync = "synchronous=" + receiver.getEndpoint().isSynchronous();
            if (endpoint.indexOf("?") > -1) {
                endpoint += "&" + sync;
            } else {
                endpoint += "?" + sync;
            }
        }

        Map endpointCounters = (Map) axisDescriptor.getProperties().get(ENDPOINT_COUNTERS_PROPERTY);
        if (endpointCounters == null) {
            endpointCounters = new HashMap();
        }

        String endpointKey = getCounterEndpointKey(receiver.getEndpointURI());

        Integer count = (Integer) endpointCounters.get(endpointKey.toString());
        if (count == null)
            count = new Integer(0);

        if (count.intValue() == 0) {
            UMOEndpoint serviceEndpoint = new MuleEndpoint(endpoint, true);
            serviceEndpoint.setName(ep.getScheme() + ":" + receiver.getComponent().getDescriptor().getName());
            // set the filter on the axis endpoint on the real receiver endpoint
            serviceEndpoint.setFilter(receiver.getEndpoint().getFilter());
            axisDescriptor.getInboundRouter().addEndpoint(serviceEndpoint);
        }

        // Update the counter for this endpoint
        count = new Integer(count.intValue() + 1);
        endpointCounters.put(endpointKey.toString(), count);
        axisDescriptor.getProperties().put(ENDPOINT_COUNTERS_PROPERTY, endpointCounters);
    }

    private String getCounterEndpointKey(UMOEndpointURI endpointURI)
    {
        StringBuffer endpointKey = new StringBuffer();

        endpointKey.append(endpointURI.getScheme());
        endpointKey.append("://");
        endpointKey.append(endpointURI.getHost());
        if (endpointURI.getPort() > -1) {
            endpointKey.append(":");
            endpointKey.append(endpointURI.getPort());
        }
        return endpointKey.toString();
    }

    /**
     * Template method to perform any work when starting the connectoe
     * 
     * @throws org.mule.umo.UMOException if the method fails
     */
    protected void doStart() throws UMOException
    {
        axisServer.start();
    }

    /**
     * Template method to perform any work when stopping the connectoe
     * 
     * @throws org.mule.umo.UMOException if the method fails
     */
    protected void doStop() throws UMOException
    {
        axisServer.stop();
        // UMOModel model = MuleManager.getInstance().getModel();
        // model.unregisterComponent(model.getDescriptor(AXIS_SERVICE_COMPONENT_NAME));
    }

    public String getServerConfig()
    {
        return serverConfig;
    }

    public void setServerConfig(String serverConfig)
    {
        this.serverConfig = serverConfig;
    }

    public List getBeanTypes()
    {
        return beanTypes;
    }

    public void setBeanTypes(List beanTypes)
    {
        this.beanTypes = beanTypes;
    }

    public void onEvent(UMOServerEvent event)
    {
        if (event.getAction() == ModelEvent.MODEL_STARTED) {
            // We need to register the Axis service component once the model
            // starts because
            // when the model starts listeners on components are started, thus
            // all listener
            // need to be registered for this connector before the Axis service
            // component
            // is registered.
            // The implication of this is that to add a new service and a
            // different http port the
            // model needs to be restarted before the listener is available
            if (!MuleManager.getInstance().getModel().isComponentRegistered(AXIS_SERVICE_COMPONENT_NAME)) {
                try {
                    MuleManager.getInstance().getModel().registerComponent(axisDescriptor);
                } catch (UMOException e) {
                    handleException(e);
                }
            }
        }
    }

    public String getClientConfig()
    {
        return clientConfig;
    }

    public void setClientConfig(String clientConfig)
    {
        this.clientConfig = clientConfig;
    }

    public AxisServer getAxisServer()
    {
        return axisServer;
    }

    public void setAxisServer(AxisServer axisServer)
    {
        this.axisServer = axisServer;
    }

    public SimpleProvider getServerProvider()
    {
        return serverProvider;
    }

    public void setServerProvider(SimpleProvider serverProvider)
    {
        this.serverProvider = serverProvider;
    }

    public SimpleProvider getClientProvider()
    {
        return clientProvider;
    }

    public void setClientProvider(SimpleProvider clientProvider)
    {
        this.clientProvider = clientProvider;
    }
}
