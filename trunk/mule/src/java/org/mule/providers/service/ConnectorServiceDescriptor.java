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
package org.mule.providers.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.config.MuleProperties;
import org.mule.impl.endpoint.EndpointBuilder;
import org.mule.impl.endpoint.UrlEndpointBuilder;
import org.mule.providers.NullPayload;
import org.mule.umo.UMOComponent;
import org.mule.umo.UMOTransactionFactory;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.provider.UMOConnector;
import org.mule.umo.provider.UMOMessageAdapter;
import org.mule.umo.provider.UMOMessageDispatcherFactory;
import org.mule.umo.provider.UMOMessageReceiver;
import org.mule.umo.transformer.UMOTransformer;
import org.mule.util.ClassHelper;
import org.mule.util.ObjectFactory;

import java.util.Properties;

/**
 * <code>ConnectorServiceDescriptor</code> describes the necessery information for creating a connector
 * from a service descriptor.
 * A service descriptor should be located at META-INF/services/org/mule/providers/<protocol>
 * where protocol is the protocol of the connector to be created
 * The service descriptor is on the form ok key value pairs and supports the following properties
 *
 * <b>connector</b>=org.mule.umo.providers.AbstractServiceEnabledConnector - The connector class
 * <b>conector.factory</b>=org.mule.util.ObjectFactory - A connector factory class to use, this is used instead of the 'connector' property if set
 * <b>dispatcher.factory</b>=org.mule.umo.providers.UMOMessageDispatcherFactory - tHe dispatcher factory class to use
 * <b>message.adapter</b>=org.mule.umo.providers.UMOMessageAdapter - The message adater class to use
 * <b>message.receiver</b>=org.mule.umo.providers.UMOMessageReceiver - The message receiver class to use
 * <b>service.error</b>= This should only be set if the connector described cannot be created directly from this descriptor. In the case
 * of Jms this would be set as the JmsConnector also needs Jndi information.
 * <b>inbound.transformer</b>=org.mule.umo.UMOTransformer - The default inbound transformer to use by endpoints if no other is set
 * <b>outbound.transformer</b>=org.mule.umo.UMOTransformer - The default outbound transformer to use by endpoints if no other is set
 *
 * Any other properties set in the descriptor are made available using the getParams() method on this
 * discriptor.
 *
 * @author <a href="mailto:ross.mason@cubis.co.uk">Ross Mason</a>
 * @version $Revision$
 */

public class ConnectorServiceDescriptor
{
    /**
     * logger used by this class
     */
    protected static transient Log logger = LogFactory.getLog(ConnectorServiceDescriptor.class);

    private String protocol;
    private String serviceLocation;
    private String serviceError;
    private String serviceFinder;
    private String connector;
    private String connectorFactory;
    private String dispatcherFactory;
    private String transactionFactory;
    private String messageAdapter;
    private String messageReceiver;
    private String endpointBuilder;
    private String defaultInboundTransformer;
    private String defaultOutboundTransformer;
    private String defaultResponseTransformer;
    private Properties properties;

    private UMOTransformer inboundTransformer;
    private UMOTransformer outboundTransformer;
    private UMOTransformer responseTransformer;
    private EndpointBuilder endpointBuilderImpl;
    private ConnectorServiceFinder connectorServiceFinder;

    public ConnectorServiceDescriptor(String protocol, String serviceLocation, Properties props) {
        this.protocol = protocol;
        this.serviceLocation = serviceLocation;
        this.properties = props;

        serviceError = removeProperty(MuleProperties.CONNECTOR_SERVICE_ERROR);
        connector = removeProperty(MuleProperties.CONNECTOR_CLASS);
        connectorFactory = removeProperty(MuleProperties.CONNECTOR_FACTORY);
        dispatcherFactory = removeProperty(MuleProperties.CONNECTOR_DISPATCHER_FACTORY);
        transactionFactory = removeProperty(MuleProperties.CONNECTOR_DISPATCHER_FACTORY);
        messageReceiver = removeProperty(MuleProperties.CONNECTOR_MESSAGE_RECEIVER_CLASS);
        messageAdapter = removeProperty(MuleProperties.CONNECTOR_MESSAGE_ADAPTER);
        defaultInboundTransformer= removeProperty(MuleProperties.CONNECTOR_INBOUND_TRANSFORMER);
        defaultOutboundTransformer= removeProperty(MuleProperties.CONNECTOR_OUTBOUND_TRANSFORMER);
        defaultResponseTransformer= removeProperty(MuleProperties.CONNECTOR_RESPONSE_TRANSFORMER);
        endpointBuilder= removeProperty(MuleProperties.CONNECTOR_ENDPOINT_BUILDER);
        serviceFinder = removeProperty(MuleProperties.CONNECTOR_SERVICE_FINDER);
    }

    void setOverrides(Properties props) {
        if(props==null || props.size()==0) return;
        serviceError = props.getProperty(MuleProperties.CONNECTOR_SERVICE_ERROR, serviceError);
        connector = props.getProperty(MuleProperties.CONNECTOR_CLASS, connector);
        connectorFactory = props.getProperty(MuleProperties.CONNECTOR_FACTORY, connectorFactory);
        dispatcherFactory = props.getProperty(MuleProperties.CONNECTOR_DISPATCHER_FACTORY, dispatcherFactory);
        messageReceiver = props.getProperty(MuleProperties.CONNECTOR_MESSAGE_RECEIVER_CLASS, messageReceiver);
        messageAdapter = props.getProperty(MuleProperties.CONNECTOR_MESSAGE_ADAPTER, messageAdapter);

        String temp = props.getProperty(MuleProperties.CONNECTOR_INBOUND_TRANSFORMER);
        if(temp!=null) {
            defaultInboundTransformer = temp;
            inboundTransformer=null;
        }
        
        temp = props.getProperty(MuleProperties.CONNECTOR_OUTBOUND_TRANSFORMER);
        if(temp!=null) {
            defaultOutboundTransformer = temp;
            outboundTransformer=null;
        }

        temp = props.getProperty(MuleProperties.CONNECTOR_RESPONSE_TRANSFORMER);
        if(temp!=null) {
            defaultResponseTransformer = temp;
            responseTransformer=null;
        }

        temp = props.getProperty(MuleProperties.CONNECTOR_ENDPOINT_BUILDER);
        if(temp!=null) {
            endpointBuilder = temp;
            endpointBuilderImpl=null;
        }

        temp = props.getProperty(MuleProperties.CONNECTOR_SERVICE_FINDER);
        if(temp!=null) {
            serviceFinder = temp;
            connectorServiceFinder=null;
        }
    }
  
    private String removeProperty(String name) {
        String temp = (String)properties.remove(name);
        if(temp!=null && temp.trim().length()==0) {
            return null;
        } else {
            return temp;
        }
    }

    public String getProtocol()
    {
        return protocol;
    }

    public String getServiceLocation()
    {
        return serviceLocation;
    }

    public String getServiceError()
    {
        return serviceError;
    }

    public String getConnector()
    {
        return connector;
    }

    public String getConnectorFactory()
    {
        return connectorFactory;
    }

    public String getDispatcherFactory()
    {
        return dispatcherFactory;
    }

    public String getMessageReceiver()
    {
        return messageReceiver;
    }

    public String getDefaultInboundTransformer()
    {
        return defaultInboundTransformer;
    }

    public String getDefaultOutboundTransformer()
    {
        return defaultOutboundTransformer;
    }

    public String getMessageAdapter()
    {
        return messageAdapter;
    }

    public Properties getProperties()
    {
        return properties;
    }

    public String getEndpointBuilder()
    {
        return endpointBuilder;
    }

    public String getServiceFinder()
    {
        return serviceFinder;
    }

    public ConnectorServiceFinder createServiceFinder() throws ConnectorServiceException {
        if(serviceFinder==null) return null;

        if(connectorServiceFinder==null) {
            try
            {
                connectorServiceFinder = (ConnectorServiceFinder)ClassHelper.instanciateClass(
                        serviceFinder, ClassHelper.NO_ARGS);
            } catch (Exception e)
            {
                throw new ConnectorServiceException("Failed to instanciate finder class: " + serviceFinder, e);
            }
        }
        return connectorServiceFinder;
    }

    public String getDefaultResponseTransformer()
    {
        return defaultResponseTransformer;
    }

    public UMOMessageAdapter createMessageAdapter(Object message) throws ConnectorServiceException {

        if(message==null) message = new NullPayload();

        if(messageAdapter!=null) {
            try
            {
                return (UMOMessageAdapter)ClassHelper.instanciateClass(messageAdapter, new Object[]{message});
            } catch (Exception e)
            {
                throw new ConnectorServiceException("Failed to create Message adapter with: " + messageAdapter + ". Error is: " + e.getMessage(), e);
            }
        } else {
            throw new ConnectorServiceException("Message Adapter class not set in service");
        }
    }

    public UMOMessageReceiver createMessageReceiver(
                                UMOConnector connector,
                               UMOComponent component,
                               UMOEndpoint endpoint) throws ConnectorServiceException {

        return createMessageReceiver(connector, component, endpoint, null);
    }

     public UMOMessageReceiver createMessageReceiver(
                                UMOConnector connector,
                               UMOComponent component,
                               UMOEndpoint endpoint, Object[] args) throws ConnectorServiceException {
        if(messageReceiver!=null) {
             try
            {
                Object[] newArgs = null;
                if(args!=null && args.length!=0) {
                    newArgs = new Object[3 + args.length];
                } else {
                    newArgs = new Object[3];
                }
                newArgs[0] = connector;
                newArgs[1] = component;
                newArgs[2] = endpoint;
                if(args!=null && args.length!=0) {
                    System.arraycopy(args, 0, newArgs, 3, newArgs.length-3);
                }

                return (UMOMessageReceiver)ClassHelper.instanciateClass(messageReceiver, newArgs);
            } catch (Exception e)
            {
                throw new ConnectorServiceException("Failed to create Message messageReceiver with: " + messageReceiver + ". Error is: " + e.getMessage(), e);
            }
        } else {
            throw new ConnectorServiceException("Message Receiver class not set in service");
        }
     }

    public UMOMessageDispatcherFactory createDispatcherFactory() throws ConnectorServiceException {
        if(dispatcherFactory!=null) {
            try
            {
                return (UMOMessageDispatcherFactory)ClassHelper.instanciateClass(dispatcherFactory, ClassHelper.NO_ARGS);
            } catch (Exception e)
            {
                throw new ConnectorServiceException("Failed to create Message dispatcher factory with: " + dispatcherFactory + ". Error is: " + e.getMessage(), e);
            }
        } else {
            throw new ConnectorServiceException("Message Dispatcher Factory class not set in service");
        }
    }

    public UMOTransactionFactory createTransactionFactory() throws ConnectorServiceException {
        if(transactionFactory!=null) {
            try
            {
                return (UMOTransactionFactory)ClassHelper.instanciateClass(transactionFactory, ClassHelper.NO_ARGS);
            } catch (Exception e)
            {
                throw new ConnectorServiceException("Failed to create transaction factory with: " + dispatcherFactory + ". Error is: " + e.getMessage(), e);
            }
        } else {
            return null;
        }
    }

    public UMOConnector createConnector(String protocol) throws ConnectorServiceException {

        UMOConnector connector;
        //Make sure we can create the endpoint/connector using this service method
        if(getServiceError()!=null) {
            throw new ConnectorServiceException(getServiceError());
        }
        //if there is a factory, use it
        try
        {
            if (getConnectorFactory() != null)
            {
                ObjectFactory factory = (ObjectFactory) ClassHelper.loadClass(getConnectorFactory(), ConnectorFactory.class).newInstance();
                connector = (UMOConnector) factory.create();
            } else
            {
                if (getConnector() != null)
                {
                    connector = (UMOConnector) ClassHelper.loadClass(getConnector(), ConnectorFactory.class).newInstance();
                } else
                {
                    throw new ConnectorServiceException("connector property not set for endpoint service factory: " + protocol);
                }
            }
        } catch (ConnectorServiceException e)
        {
            throw e;
        } catch(Exception e) {
            throw new ConnectorServiceException("failed to create connector for protocol: " + protocol + ". Error was: " + e.getMessage(), e);
        }

        if(connector.getName()==null) {
            connector.setName("_" + protocol + "Connector#" + connector.hashCode());
        }
        return connector;
    }

    public UMOTransformer createInboundTransformer() throws ConnectorFactoryException
    {
        if(inboundTransformer!=null) return inboundTransformer;

        if (getDefaultInboundTransformer() != null)
        {
            logger.info("Loading default inbound transformer: " + getDefaultInboundTransformer());
            try
            {
                inboundTransformer = (UMOTransformer) ClassHelper.instanciateClass(getDefaultInboundTransformer(), ClassHelper.NO_ARGS);
                return inboundTransformer;
            } catch (Exception e)
            {
                throw new ConnectorFactoryException("Failed to load inbound transformer: " + getDefaultInboundTransformer() + ". Error is: " + e.getMessage(), e);
            }
        }
        return null;
    }

    public UMOTransformer createOutboundTransformer() throws ConnectorFactoryException
    {
        if(outboundTransformer!=null) return outboundTransformer;
        if (getDefaultOutboundTransformer() != null)
        {
            logger.info("Loading default outbound transformer: " + getDefaultInboundTransformer());
            try
            {
                outboundTransformer = (UMOTransformer) ClassHelper.instanciateClass(getDefaultOutboundTransformer(), ClassHelper.NO_ARGS);
                return outboundTransformer;
            } catch (Exception e)
            {
                throw new ConnectorFactoryException("Failed to load outbound transformer: " + getDefaultOutboundTransformer() + ". Error is: " + e.getMessage(), e);
            }
        }
        return null;
    }

    public UMOTransformer createResponseTransformer() throws ConnectorFactoryException
    {
        if(responseTransformer!=null) return responseTransformer;

        if (getDefaultResponseTransformer() != null)
        {
            logger.info("Loading default response transformer: " + getDefaultResponseTransformer());
            try
            {
                responseTransformer = (UMOTransformer) ClassHelper.instanciateClass(getDefaultResponseTransformer(), ClassHelper.NO_ARGS);
                return responseTransformer;
            } catch (Exception e)
            {
                throw new ConnectorFactoryException("Failed to load response transformer: " + getDefaultResponseTransformer() + ". Error is: " + e.getMessage(), e);
            }
        }
        return null;
    }

    public EndpointBuilder createEndpointBuilder() throws ConnectorFactoryException
    {
        if(endpointBuilderImpl!=null) return endpointBuilderImpl;

        if(endpointBuilder==null) {
            logger.debug("Endpoint resolver not set, Loading default resolver: " + UrlEndpointBuilder.class.getName());
            return new UrlEndpointBuilder();
        } else {
            logger.debug("Loading endpointUri resolver: " + getEndpointBuilder());
            try
            {
                endpointBuilderImpl = (EndpointBuilder) ClassHelper.instanciateClass(getEndpointBuilder(), ClassHelper.NO_ARGS);
                return endpointBuilderImpl;
            } catch (Exception e)
            {
                throw new ConnectorFactoryException("Failed to load endpointUri resolver: " + getEndpointBuilder() + ". Error is: " + e.getMessage(), e);
            }
        }
    }

    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof ConnectorServiceDescriptor)) return false;

        final ConnectorServiceDescriptor connectorServiceDescriptor = (ConnectorServiceDescriptor) o;

        if (connector != null ? !connector.equals(connectorServiceDescriptor.connector) : connectorServiceDescriptor.connector != null) return false;
        if (connectorFactory != null ? !connectorFactory.equals(connectorServiceDescriptor.connectorFactory) : connectorServiceDescriptor.connectorFactory != null) return false;
        if (defaultInboundTransformer != null ? !defaultInboundTransformer.equals(connectorServiceDescriptor.defaultInboundTransformer) : connectorServiceDescriptor.defaultInboundTransformer != null) return false;
        if (defaultOutboundTransformer != null ? !defaultOutboundTransformer.equals(connectorServiceDescriptor.defaultOutboundTransformer) : connectorServiceDescriptor.defaultOutboundTransformer != null) return false;
        if (defaultResponseTransformer != null ? !defaultResponseTransformer.equals(connectorServiceDescriptor.defaultResponseTransformer) : connectorServiceDescriptor.defaultResponseTransformer != null) return false;
        if (dispatcherFactory != null ? !dispatcherFactory.equals(connectorServiceDescriptor.dispatcherFactory) : connectorServiceDescriptor.dispatcherFactory != null) return false;
        if (endpointBuilder != null ? !endpointBuilder.equals(connectorServiceDescriptor.endpointBuilder) : connectorServiceDescriptor.endpointBuilder != null) return false;
        if (messageAdapter != null ? !messageAdapter.equals(connectorServiceDescriptor.messageAdapter) : connectorServiceDescriptor.messageAdapter != null) return false;
        if (messageReceiver != null ? !messageReceiver.equals(connectorServiceDescriptor.messageReceiver) : connectorServiceDescriptor.messageReceiver != null) return false;
        if (protocol != null ? !protocol.equals(connectorServiceDescriptor.protocol) : connectorServiceDescriptor.protocol != null) return false;
        if (serviceError != null ? !serviceError.equals(connectorServiceDescriptor.serviceError) : connectorServiceDescriptor.serviceError != null) return false;
        if (serviceFinder != null ? !serviceFinder.equals(connectorServiceDescriptor.serviceFinder) : connectorServiceDescriptor.serviceFinder != null) return false;
        if (serviceLocation != null ? !serviceLocation.equals(connectorServiceDescriptor.serviceLocation) : connectorServiceDescriptor.serviceLocation != null) return false;
        if (transactionFactory != null ? !transactionFactory.equals(connectorServiceDescriptor.transactionFactory) : connectorServiceDescriptor.transactionFactory != null) return false;

        return true;
    }

    public int hashCode()
    {
        int result;
        result = (protocol != null ? protocol.hashCode() : 0);
        result = 29 * result + (serviceLocation != null ? serviceLocation.hashCode() : 0);
        result = 29 * result + (serviceError != null ? serviceError.hashCode() : 0);
        result = 29 * result + (serviceFinder != null ? serviceFinder.hashCode() : 0);
        result = 29 * result + (connector != null ? connector.hashCode() : 0);
        result = 29 * result + (connectorFactory != null ? connectorFactory.hashCode() : 0);
        result = 29 * result + (dispatcherFactory != null ? dispatcherFactory.hashCode() : 0);
        result = 29 * result + (transactionFactory != null ? transactionFactory.hashCode() : 0);
        result = 29 * result + (messageAdapter != null ? messageAdapter.hashCode() : 0);
        result = 29 * result + (messageReceiver != null ? messageReceiver.hashCode() : 0);
        result = 29 * result + (endpointBuilder != null ? endpointBuilder.hashCode() : 0);
        result = 29 * result + (defaultInboundTransformer != null ? defaultInboundTransformer.hashCode() : 0);
        result = 29 * result + (defaultOutboundTransformer != null ? defaultOutboundTransformer.hashCode() : 0);
        result = 29 * result + (defaultResponseTransformer != null ? defaultResponseTransformer.hashCode() : 0);
        return result;
    }
}
