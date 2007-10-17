/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.soap.axis;

import org.mule.config.ExceptionHelper;
import org.mule.config.i18n.CoreMessages;
import org.mule.impl.endpoint.EndpointURIEndpointBuilder;
import org.mule.impl.endpoint.MuleEndpoint;
import org.mule.impl.internal.notifications.ManagerNotification;
import org.mule.impl.internal.notifications.ManagerNotificationListener;
import org.mule.impl.model.seda.SedaComponent;
import org.mule.providers.AbstractConnector;
import org.mule.providers.http.servlet.ServletConnector;
import org.mule.providers.service.TransportFactory;
import org.mule.providers.soap.axis.extensions.MuleConfigProvider;
import org.mule.providers.soap.axis.extensions.MuleTransport;
import org.mule.providers.soap.axis.extensions.WSDDFileProvider;
import org.mule.providers.soap.axis.extensions.WSDDJavaMuleProvider;
import org.mule.providers.soap.axis.i18n.AxisMessages;
import org.mule.umo.UMOComponent;
import org.mule.umo.UMOException;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.endpoint.UMOEndpointBuilder;
import org.mule.umo.endpoint.UMOEndpointURI;
import org.mule.umo.endpoint.UMOImmutableEndpoint;
import org.mule.umo.lifecycle.InitialisationException;
import org.mule.umo.manager.UMOServerNotification;
import org.mule.umo.provider.UMOMessageReceiver;
import org.mule.util.ClassUtils;
import org.mule.util.MuleUrlStreamHandlerFactory;
import org.mule.util.object.SingletonObjectFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.configuration.SimpleProvider;
import org.apache.axis.deployment.wsdd.WSDDConstants;
import org.apache.axis.deployment.wsdd.WSDDProvider;
import org.apache.axis.encoding.TypeMappingRegistryImpl;
import org.apache.axis.encoding.ser.BeanDeserializerFactory;
import org.apache.axis.encoding.ser.BeanSerializerFactory;
import org.apache.axis.handlers.soap.SOAPService;
import org.apache.axis.server.AxisServer;
import org.apache.axis.wsdl.fromJava.Namespaces;
import org.apache.axis.wsdl.fromJava.Types;

/**
 * <code>AxisConnector</code> is used to maintain one or more Services for Axis
 * server instance.
 * <p>
 * Some of the Axis specific service initialisation code was adapted from the Ivory
 * project (http://ivory.codehaus.org). Thanks guys :)
 */
public class AxisConnector extends AbstractConnector implements ManagerNotificationListener
{
    /* Register the AxisFault Exception reader if this class gets loaded */
    static
    {
        ExceptionHelper.registerExceptionReader(new AxisFaultExceptionReader());
    }

    public static final QName QNAME_MULE_PROVIDER = new QName(WSDDConstants.URI_WSDD_JAVA, "Mule");
    public static final QName QNAME_MULE_TYPE_MAPPINGS = new QName("http://www.muleumo.org/ws/mappings",
        "Mule");
    public static final String DEFAULT_MULE_NAMESPACE_URI = "http://www.muleumo.org";

    public static final String DEFAULT_MULE_AXIS_SERVER_CONFIG = "mule-axis-server-config.wsdd";
    public static final String DEFAULT_MULE_AXIS_CLIENT_CONFIG = "mule-axis-client-config.wsdd";
    public static final String AXIS_SERVICE_COMPONENT_NAME = "_axisServiceComponent";
    public static final String AXIS_SERVICE_PROPERTY = "_axisService";
    public static final String AXIS_CLIENT_CONFIG_PROPERTY = "clientConfig";

    public static final String SERVICE_PROPERTY_COMPONENT_NAME = "componentName";
    public static final String SERVICE_PROPERTY_SERVCE_PATH = "servicePath";

    public static final String WSDL_URL_PROPERTY = "wsdlUrl";
    public static final String AXIS = "axis";

    private String serverConfig = DEFAULT_MULE_AXIS_SERVER_CONFIG;
    private AxisServer axis = null;
    private SimpleProvider serverProvider = null;
    private String clientConfig = DEFAULT_MULE_AXIS_CLIENT_CONFIG;
    private SimpleProvider clientProvider = null;

    private List beanTypes;
    private UMOComponent axisComponent;

    //this will store the name of the descriptor of the current connector's AxisServiceComponent
    //private String specificAxisServiceComponentName;

    /**
     * These protocols will be set on client invocations. By default Mule uses it's
     * own transports rather that Axis's. This is only because it gives us more
     * flexibility inside Mule and simplifies the code
     */
    private Map axisTransportProtocols = null;

    /**
     * A store of registered servlet services that need to have their endpoints
     * re-written with the 'real' http url instead of the servlet:// one. This is
     * only required to ensure wsdl is generated correctly. I would like a clearer
     * way of doing this so I can remove this workaround
     */
    private List servletServices = new ArrayList();

    private List supportedSchemes = null;

    private boolean doAutoTypes = true;

    private boolean treatMapAsNamedParams = true;

    public AxisConnector()
    {
        super();
        this.registerProtocols();
    }

    protected void registerProtocols()
    {
        if (supportedSchemes == null)
        {
            // Default supported schemes, these can be restricted
            // through configuration
            supportedSchemes = new ArrayList();
            supportedSchemes.add("http");
            supportedSchemes.add("https");
            supportedSchemes.add("servlet");
            supportedSchemes.add("vm");
            supportedSchemes.add("jms");
            supportedSchemes.add("xmpp");
            supportedSchemes.add("smtp");
            supportedSchemes.add("smtps");
            supportedSchemes.add("pop3");
            supportedSchemes.add("pop3s");
            supportedSchemes.add("imap");
            supportedSchemes.add("imaps");
            supportedSchemes.add("ssl");
            supportedSchemes.add("tcp");
        }

        for (Iterator iterator = supportedSchemes.iterator(); iterator.hasNext();)
        {
            String s = (String) iterator.next();
            registerSupportedProtocol(s);
        }
    }

    protected void doInitialise() throws InitialisationException
    {
        axisTransportProtocols = new HashMap();
        //specificAxisServiceComponentName = AXIS_SERVICE_COMPONENT_NAME + "_" + name;

        axisTransportProtocols = new HashMap();
        try
        {
            for (Iterator iterator = supportedSchemes.iterator(); iterator.hasNext();)
            {
                String s = (String) iterator.next();
                axisTransportProtocols.put(s, MuleTransport.getTransportClass(s));
                registerSupportedProtocol(s);
            }
            managementContext.registerListener(this);
        }
        catch (Exception e)
        {
            throw new InitialisationException(e, this);
        }
        // TODO DO: call registerSupportedProtocol if axisTransportProtocols are set from external?

        if (clientProvider == null)
        {
            clientProvider = createAxisProvider(clientConfig);
        }
        else
        {
            if (!DEFAULT_MULE_AXIS_CLIENT_CONFIG.equals(clientConfig))
            {
                logger.warn(AxisMessages.clientProviderAndClientConfigConfigured());
            }
        }

        if (axis == null)
        {
            if (serverProvider == null)
            {
                serverProvider = this.createAxisProvider(serverConfig);
            }
            else
            {
                if (!DEFAULT_MULE_AXIS_SERVER_CONFIG.equals(serverConfig))
                {
                    logger.warn(AxisMessages.serverProviderAndServerConfigConfigured());
                }
            }

            // Create the AxisServer
            axis = new AxisServer(serverProvider);
            // principle of least surprise: doAutoTypes only has effect on our self-configured AxisServer
            axis.setOption("axis.doAutoTypes", Boolean.valueOf(doAutoTypes));
        }

        // Register the Mule service serverProvider
        WSDDProvider.registerProvider(QNAME_MULE_PROVIDER, new WSDDJavaMuleProvider(this));

        try
        {
            registerTransportTypes();
        }
        catch (ClassNotFoundException e)
        {
            throw new InitialisationException(
                CoreMessages.cannotLoadFromClasspath(e.getMessage()), e, this);
        }

        // Register all our UrlStreamHandlers here so they can be resolved. This is necessary
        // to make Mule work in situations where modification of system properties at runtime
        // is not reliable, e.g. when running in maven's surefire test executor.
        MuleUrlStreamHandlerFactory.registerHandler("jms", new org.mule.providers.soap.axis.transport.jms.Handler());
        MuleUrlStreamHandlerFactory.registerHandler("pop3", new org.mule.providers.soap.axis.transport.pop3.Handler());
        MuleUrlStreamHandlerFactory.registerHandler("smtp", new org.mule.providers.soap.axis.transport.smtp.Handler());
        MuleUrlStreamHandlerFactory.registerHandler("vm", new org.mule.providers.soap.axis.transport.vm.Handler());

        try
        {
            registerTypes((TypeMappingRegistryImpl)axis.getTypeMappingRegistry(), beanTypes);
        }
        catch (ClassNotFoundException e)
        {
            throw new InitialisationException(e, this);
        }
   }

    protected void registerTransportTypes() throws ClassNotFoundException
    {
        // Register Transport handlers
        // By default these will all be handled by Mule, however some companies may
        // have
        // their own they wish to use
        for (Iterator iterator = getAxisTransportProtocols().keySet().iterator(); iterator.hasNext();)
        {
            String protocol = (String)iterator.next();
            Object temp = getAxisTransportProtocols().get(protocol);
            Class clazz;
            if (temp instanceof String)
            {
                clazz = ClassUtils.loadClass(temp.toString(), getClass());
            }
            else
            {
                clazz = (Class) temp;
            }
            Call.setTransportForProtocol(protocol, clazz);
        }
    }

    protected SimpleProvider createAxisProvider(String config) throws InitialisationException
    {
        // Use our custom file provider that does not require services to be declared
        // in the WSDD. This only affects the
        // client side as the client will fallback to the FileProvider when invoking
        // a service.
        WSDDFileProvider fileProvider = new WSDDFileProvider(config);
        fileProvider.setSearchClasspath(true);
        /*
         * Wrap the FileProvider with a SimpleProvider so we can programmatically
         * configure the Axis server (you can only use wsdd descriptors with the
         * FileProvider)
         */
        return new MuleConfigProvider(fileProvider);
    }

    public String getProtocol()
    {
        return AXIS;
    }

    /**
     * The method determines the key used to store the receiver against.
     *
     * @param component the component for which the endpoint is being registered
     * @param endpoint the endpoint being registered for the component
     * @return the key to store the newly created receiver against. In this case it
     *         is the component name, which is equivalent to the Axis service name.
     */
    protected Object getReceiverKey(UMOComponent component, UMOImmutableEndpoint endpoint)
    {
        if (endpoint.getEndpointURI().getPort() == -1)
        {
            return component.getName();
        }
        else
        {
            return endpoint.getEndpointURI().getAddress() + "/" + component.getName();
        }
    }

    public UMOMessageReceiver createReceiver(UMOComponent component, UMOImmutableEndpoint endpoint) throws Exception
    {
        // this is always initialised as synchronous as ws invocations should
        // always execute in a single thread unless the endpoint has explicitly
        // been set to run asynchronously
        if (!endpoint.isSynchronousSet() && !endpoint.isSynchronous())
        {
            if (logger.isDebugEnabled())
            {
                logger.debug("overriding endpoint synchronicity and setting it to true. Web service requests are executed in a single thread");
            }
            // TODO DF: MULE-2291 Resolve pending endpoint mutability issues
            ((MuleEndpoint) endpoint).setSynchronous(true);
        }

        return super.createReceiver(component, endpoint);
    }

    protected void unregisterReceiverWithMuleService(UMOMessageReceiver receiver, UMOEndpointURI ep)
        throws UMOException
    {
        String endpointKey = getCounterEndpointKey(receiver.getEndpointURI());

        for (Iterator iterator = axisComponent.getInboundRouter().getEndpoints().iterator(); iterator.hasNext();)
        {
            UMOEndpoint umoEndpoint = (UMOEndpoint)iterator.next();
            if (endpointKey.startsWith(umoEndpoint.getEndpointURI().getAddress()))
            {
                logger.info("Unregistering Axis endpoint: " + endpointKey + " for service: "
                            + receiver.getComponent().getName());
            }
            try
            {
                umoEndpoint.getConnector()
                    .unregisterListener(receiver.getComponent(), receiver.getEndpoint());
            }
            catch (Exception e)
            {
                logger.error("Failed to unregister Axis endpoint: " + endpointKey + " for service: "
                             + receiver.getComponent().getName() + ". Error is: "
                             + e.getMessage(), e);
            }
            // TODO why break? For does not loop.
            break;
        }
    }

    protected void registerReceiverWithMuleService(UMOMessageReceiver receiver, UMOEndpointURI ep)
        throws UMOException
    {
        // If this is the first receiver we need to create the Axis service
        // component
        // this will be registered with Mule when the Connector starts
        if (axisComponent == null)
        {
            // See if the axis descriptor has already been added. This allows
            // developers to override the default configuration, say to increase
            // the threadpool
            if (axisComponent == null)
            {
                axisComponent = getOrCreateAxisComponent();
            }
            else
            {
                // Lets unregister the 'template' instance, configure it and
                // then register
                // again later
                managementContext.getRegistry().unregisterComponent(AXIS_SERVICE_PROPERTY + getName());

                //if the descriptor still contains the original AXIS_SERVICE_COMPONENT_NAME name,
                //we need to change it to the specific name which is AXIS_SERVICE_COMPONENT_NAME_<connector_name>
//                if (AXIS_SERVICE_COMPONENT_NAME.equals(axisComponent.getName()))
//                {
//                    axisComponent.setName(specificAxisServiceComponentName);
//                }
            }
            // if the axis server hasn't been set, set it now. The Axis server
            // may be set
            // externally
            if (axisComponent.getProperties().get(AXIS) == null)
            {
                axisComponent.getProperties().put(AXIS, axis);
            }
        }
        String serviceName = receiver.getComponent().getName();
        // No determine if the endpointUri requires a new connector to be
        // registed in the case of http we only need to register the new endpointUri
        // if
        // the port is different
        // If we're using VM or Jms we just use the resource infor directly without
        // appending a service name
        String endpoint;
        String scheme = ep.getScheme().toLowerCase();
        if (scheme.equals("jms") || scheme.equals("vm"))
        {
            endpoint = ep.toString();
        }
        else
        {
            endpoint = receiver.getEndpointURI().getAddress() + "/" + serviceName;
        }
        if (logger.isDebugEnabled())
        {
            logger.debug("Modified endpoint with " + scheme + " scheme to " + endpoint);
        }
        
        // TODO DF: MULE-2291 Resolve pending endpoint mutability issues
        UMOEndpoint receiverEndpoint=(UMOEndpoint) receiver.getEndpoint();

        // Default to using synchronous for socket based protocols unless the
        // synchronous property has been set explicitly
        boolean sync = false;
        if (!receiverEndpoint.isSynchronousSet())
        {
            if (scheme.equals("http") || scheme.equals("https") || scheme.equals("ssl")
                || scheme.equals("tcp"))
            {
                sync = true;
            }
        }
        else
        {
            sync = receiverEndpoint.isSynchronous();
        }

        UMOEndpointBuilder builder = new EndpointURIEndpointBuilder(endpoint, managementContext);
        builder.setSynchronous(sync);
        builder.setName(ep.getScheme() + ":" + serviceName);
        
        // Set the transformers on the endpoint too
        builder.setTransformers(receiver.getEndpoint().getTransformers());
        // TODO DF: MULE-2291 Resolve pending endpoint mutability issues
        ((MuleEndpoint) receiver.getEndpoint()).setTransformers(new LinkedList());

        builder.setResponseTransformers(receiver.getEndpoint().getResponseTransformers());
        // TODO DF: MULE-2291 Resolve pending endpoint mutability issues
        ((MuleEndpoint) receiver.getEndpoint()).setResponseTransformers(new LinkedList());

        // set the filter on the axis endpoint on the real receiver endpoint
        builder.setFilter(receiver.getEndpoint().getFilter());
        // Remove the Axis filter now
        // TODO DF: MULE-2291 Resolve pending endpoint mutability issues
        ((MuleEndpoint) receiver.getEndpoint()).setFilter(null);

        // set the Security filter on the axis endpoint on the real receiver
        // endpoint
        builder.setSecurityFilter(receiver.getEndpoint().getSecurityFilter());
        // Remove the Axis Receiver Security filter now
        // TODO DF: MULE-2291 Resolve pending endpoint mutability issues
        ((MuleEndpoint) receiver.getEndpoint()).setSecurityFilter(null);

        // propagate properties to the service endpoint
        builder.setProperties(receiverEndpoint.getProperties());
        
        UMOImmutableEndpoint serviceEndpoint = managementContext.getRegistry()
            .lookupEndpointFactory()
            .getInboundEndpoint(builder, managementContext);
        
        axisComponent.getInboundRouter().addEndpoint(serviceEndpoint);

    }

    private String getCounterEndpointKey(UMOEndpointURI endpointURI)
    {
        StringBuffer endpointKey = new StringBuffer(64);

        endpointKey.append(endpointURI.getScheme());
        endpointKey.append("://");
        endpointKey.append(endpointURI.getHost());
        if (endpointURI.getPort() > -1)
        {
            endpointKey.append(":");
            endpointKey.append(endpointURI.getPort());
        }
        return endpointKey.toString();
    }

    // This initialization could be performed in the initialize() method.  Putting it here essentially makes
    // it a lazy-create/lazy-init
    // Another option would be to put it in the default-axis-config.xml (MULE-2102) with lazy-init="true" 
    // but that makes us depend on Spring.
    // Another consideration is how/when this implicit component gets disposed.
    protected UMOComponent getOrCreateAxisComponent() throws UMOException
    {
        UMOComponent c = managementContext.getRegistry().lookupComponent(AXIS_SERVICE_PROPERTY + getName());

        if (c == null)
        {
            // TODO MULE-2228 Simplify this API
            c = new SedaComponent();
            c.setName(AXIS_SERVICE_PROPERTY + getName());
            c.setModel(managementContext.getRegistry().lookupSystemModel());
            c.setManagementContext(managementContext);
            c.initialise();
            
            Map props = new HashMap();
            props.put(AXIS, axis);
            SingletonObjectFactory of = new SingletonObjectFactory(AxisServiceComponent.class, props);
            of.initialise();
            c.setServiceFactory(of);
        }
        return c;
    }

    /**
     * Template method to perform any work when starting the connectoe
     *
     * @throws org.mule.umo.UMOException if the method fails
     */
    protected void doStart() throws UMOException
    {
        axis.start();
    }

    /**
     * Template method to perform any work when stopping the connectoe
     *
     * @throws org.mule.umo.UMOException if the method fails
     */
    protected void doStop() throws UMOException
    {
        axis.stop();
        // UMOModel model = managementContext.getRegistry().lookupModel();
        // model.unregisterComponent(model.getDescriptor(AXIS_SERVICE_COMPONENT_NAME));
    }

    protected void doConnect() throws Exception
    {
        // template method
    }

    protected void doDisconnect() throws Exception
    {
        // template method
    }

    protected void doDispose()
    {
        // template method
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

    public String getClientConfig()
    {
        return clientConfig;
    }

    public void setClientConfig(String clientConfig)
    {
        this.clientConfig = clientConfig;
    }

    public AxisServer getAxis()
    {
        return axis;
    }

    public void setAxis(AxisServer axisServer)
    {
        this.axis = axisServer;
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

    public Map getAxisTransportProtocols()
    {
        return axisTransportProtocols;
    }

    public void setAxisTransportProtocols(Map axisTransportProtocols)
    {
        this.axisTransportProtocols.putAll(axisTransportProtocols);
    }

    void addServletService(SOAPService service)
    {
        servletServices.add(service);
    }

    public List getSupportedSchemes()
    {
        return supportedSchemes;
    }

    public void setSupportedSchemes(List supportedSchemes)
    {
        this.supportedSchemes = supportedSchemes;
    }

    public boolean isDoAutoTypes()
    {
        return doAutoTypes;
    }

    public void setDoAutoTypes(boolean doAutoTypes)
    {
        this.doAutoTypes = doAutoTypes;
    }

    void registerTypes(TypeMappingRegistryImpl registry, List types) throws ClassNotFoundException
    {
        if (types != null)
        {
            Class clazz;
            for (Iterator iterator = types.iterator(); iterator.hasNext();)
            {
                clazz = ClassUtils.loadClass(iterator.next().toString(), getClass());
                String localName = Types.getLocalNameFromFullName(clazz.getName());
                QName xmlType = new QName(Namespaces.makeNamespace(clazz.getName()), localName);

                registry.getDefaultTypeMapping().register(clazz, xmlType,
                    new BeanSerializerFactory(clazz, xmlType), new BeanDeserializerFactory(clazz, xmlType));
            }
        }
    }

    public boolean isTreatMapAsNamedParams()
    {
        return treatMapAsNamedParams;
    }

    public void setTreatMapAsNamedParams(boolean treatMapAsNamedParams)
    {
        this.treatMapAsNamedParams = treatMapAsNamedParams;
    }

    public void onNotification(UMOServerNotification notification)
    {
        if (notification.getAction() == ManagerNotification.MANAGER_STARTED)
        {
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
            if (managementContext.getRegistry().lookupComponent(AXIS_SERVICE_PROPERTY + getName()) == null)
            {
                try
                {
                    // Descriptor might be null if no inbound endpoints have been
                    // register for the Axis connector
                    if (axisComponent == null)
                    {
                        axisComponent = getOrCreateAxisComponent();
                    }

                    managementContext.getRegistry().registerComponent(axisComponent, managementContext);
                    axisComponent.start();
                    
                    // We have to perform a small hack here to rewrite servlet://
                    // endpoints with the
                    // real http:// address
                    for (Iterator iterator = servletServices.iterator(); iterator.hasNext();)
                    {
                        SOAPService service = (SOAPService)iterator.next();
                        ServletConnector servletConnector = (ServletConnector) TransportFactory.getConnectorByProtocol("servlet");
                        String url = servletConnector.getServletUrl();
                        if (url != null)
                        {
                            service.getServiceDescription().setEndpointURL(url + "/" + service.getName());
                        }
                        else
                        {
                            logger.error("The servletUrl property on the ServletConntector has not been set this means that wsdl generation for service '"
                                         + service.getName() + "' may be incorrect");
                        }
                    }
                    servletServices.clear();
                    //servletServices = null;

                }
                catch (UMOException e)
                {
                    handleException(e);
                }
            }
        }
    }
}
