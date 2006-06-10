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
 *
 */

package org.mule.impl.endpoint;

import org.mule.impl.ImmutableMuleEndpoint;
import org.mule.providers.service.ConnectorFactory;
import org.mule.umo.UMOException;
import org.mule.umo.UMOFilter;
import org.mule.umo.UMOTransactionConfig;
import org.mule.umo.endpoint.EndpointException;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.endpoint.UMOEndpointURI;
import org.mule.umo.endpoint.UMOImmutableEndpoint;
import org.mule.umo.lifecycle.InitialisationException;
import org.mule.umo.provider.UMOConnector;
import org.mule.umo.security.UMOEndpointSecurityFilter;
import org.mule.umo.transformer.UMOTransformer;
import org.mule.config.i18n.Message;
import org.mule.config.i18n.Messages;

import java.util.Map;

/**
 * <code>MuleEndpoint</code> describes a Provider in the Mule Server. A
 * endpoint is a grouping of an endpoint, an endpointUri and a transformer.
 * 
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */
public class MuleEndpoint extends ImmutableMuleEndpoint implements UMOEndpoint
{
    /**
	 * Serial version 
	 */
	private static final long serialVersionUID = 2028442057178326047L;
	
	public static final String ALWAYS_CREATE_STRING = "ALWAYS_CREATE";
    public static final String NEVER_CREATE_STRING = "NEVER_CREATE";
    /**
     * Default constructor This is required right now for the Mule digester to
     * set the properties through the classes mutators
     */
    public MuleEndpoint()
    {
        super(null, null, null, null, ENDPOINT_TYPE_SENDER_AND_RECEIVER, 0, null, null);
    }

    public MuleEndpoint(String name,
                        UMOEndpointURI endpointUri,
                        UMOConnector connector,
                        UMOTransformer transformer,
                        String type,
                        int createConnector,
                        String endpointEncoding,
                        Map props)
    {
        super(name, endpointUri, connector, transformer, type, createConnector, endpointEncoding, props);
    }

    public MuleEndpoint(UMOImmutableEndpoint endpoint)
    {
        super(endpoint);
    }

    public MuleEndpoint(String endpointName, boolean receiver) throws UMOException
    {
        super(endpointName, receiver);
    }

    public Object clone()
    {
        UMOEndpoint clone = new MuleEndpoint(name, endpointUri, connector, transformer, type,
                createConnector, endpointEncoding, properties);

        clone.setTransactionConfig(transactionConfig);
        clone.setFilter(filter);
        clone.setSecurityFilter(securityFilter);

        if (remoteSync != null) {
            clone.setRemoteSync(isRemoteSync());
        }
        if (remoteSyncTimeout != null) {
            clone.setRemoteSyncTimeout(getRemoteSyncTimeout());
        }

        if (synchronous != null) {
            clone.setSynchronous(synchronous.booleanValue());
        }

        clone.setDeleteUnacceptedMessages(deleteUnacceptedMessages);

        clone.setInitialState(initialState);
        if (initialised.get()) {
            try {
                clone.initialise();
            }
            catch (InitialisationException e) {
                // this really should never happen as the endpoint is already
                // initialised
                logger.error(e.getMessage(), e);
            }
        }

        return clone;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.umo.endpoint.UMOEndpoint#setEndpointURI(java.lang.String)
     */
    public void setEndpointURI(UMOEndpointURI endpointUri) throws EndpointException
    {
        if (connector != null && endpointUri != null
                && !connector.supportsProtocol(endpointUri.getFullScheme())) {
            throw new IllegalArgumentException(new Message(
                    Messages.CONNECTOR_SCHEME_X_INCOMPATIBLE_WITH_ENDPOINT_SCHEME_X, connector
                            .getProtocol(), endpointUri).getMessage());
        }
        this.endpointUri = endpointUri;
        if (endpointUri != null) {
            properties.putAll(endpointUri.getParams());
        }
    }

    public void setEncoding(String endpointEncoding)
    {
        this.endpointEncoding = endpointEncoding;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.umo.endpoint.UMOEndpoint#setType(String)
     */
    public void setType(String type)
    {
        this.type = type;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.umo.endpoint.UMOEndpoint#setConnector(org.mule.umo.provider.UMOConnector)
     */
    public void setConnector(UMOConnector connector)
    {
        if (connector != null && endpointUri != null
                && !connector.supportsProtocol(endpointUri.getFullScheme())) {
            throw new IllegalArgumentException(new Message(Messages.CONNECTOR_SCHEME_X_INCOMPATIBLE_WITH_ENDPOINT_SCHEME_X,
                    connector.getProtocol(), endpointUri).getMessage());
        }
        this.connector = connector;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.umo.endpoint.UMOEndpoint#setName(java.lang.String)
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.umo.endpoint.UMOEndpoint#setTransformer(org.mule.umo.transformer.UMOTransformer)
     */
    public void setTransformer(UMOTransformer trans)
    {
        transformer = trans;
        if (transformer != null) {
            transformer.setEndpoint(this);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.umo.endpoint.UMOEndpoint#getPropertiesForURI(java.util.Map)
     */
    public void setProperties(Map props)
    {
        properties.clear();
        properties.putAll(props);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.umo.endpoint.UMOEndpoint#isReadOnly()
     */
    public boolean isReadOnly()
    {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.umo.endpoint.UMOEndpoint#setTransactionConfig(org.mule.umo.UMOTransactionConfig)
     */
    public void setTransactionConfig(UMOTransactionConfig config)
    {
        transactionConfig = config;
    }

    public void setFilter(UMOFilter filter)
    {
        this.filter = filter;
    }

    /**
     * If a filter is configured on this endpoint, this property will determine
     * if message that are not excepted by the filter are deleted
     * 
     * @param delete
     *            if message should be deleted, false otherwise
     */
    public void setDeleteUnacceptedMessages(boolean delete)
    {
        deleteUnacceptedMessages = delete;
    }

    /**
     * Sets an UMOEndpointSecurityFilter for this endpoint. If a filter is set
     * all traffice via this endpoint with be subject to authentication.
     * 
     * @param filter
     *            the UMOSecurityFilter responsible for authenticating message
     *            flow via this endpoint.
     * @see org.mule.umo.security.UMOEndpointSecurityFilter
     */
    public void setSecurityFilter(UMOEndpointSecurityFilter filter)
    {
        securityFilter = filter;
        if (securityFilter != null) {
            securityFilter.setEndpoint(this);
        }
    }

    /**
     * Determines if requests originating from this endpoint should be
     * synchronous i.e. execute in a single thread and possibly return an
     * result. This property is only used when the endpoint is of type
     * 'receiver'.
     * 
     * @param synchronous
     *            whether requests on this endpoint should execute in a single
     *            thread. This property is only used when the endpoint is of
     *            type 'receiver'
     */
    public void setSynchronous(boolean synchronous)
    {
        this.synchronous = Boolean.valueOf(synchronous);
    }

    public void setCreateConnector(int action)
    {
        createConnector = action;
    }

    public void setCreateConnectorAsString(String action)
    {
        if (ALWAYS_CREATE_STRING.equals(action)) {
            createConnector = ConnectorFactory.ALWAYS_CREATE_CONNECTOR;
        }
        else if (NEVER_CREATE_STRING.equals(action)) {
            createConnector = ConnectorFactory.NEVER_CREATE_CONNECTOR;
        }
        else {
            createConnector = ConnectorFactory.GET_OR_CREATE_CONNECTOR;
        }
    }

    /**
     * For certain providers that support the notion of a backchannel such as
     * sockets (outputStream) or Jms (ReplyTo) Mule can automatically wait for a
     * response from a backchannel when dispatching over these protocols. This
     * is different for synchronous as synchronous behavior only applies to in
     * 
     * @param value
     *            whether the endpoint should perfrom sync receives
     */
    public void setRemoteSync(boolean value)
    {
        this.remoteSync = Boolean.valueOf(value);
        if (value) {
            this.synchronous = Boolean.TRUE;
        }
    }

    /**
     * The timeout value for remoteSync invocations
     * 
     * @param timeout
     *            the timeout in milliseconds
     */
    public void setRemoteSyncTimeout(int timeout)
    {
        this.remoteSyncTimeout = new Integer(timeout);
    }

    /**
     * Sets the state the endpoint will be loaded in. The States are 'stopped'
     * and 'started' (default)
     * 
     * @param state
     */
    public void setInitialState(String state)
    {
        this.initialState = state;
    }

    public void setResponseTransformer(UMOTransformer trans)
    {
        responseTransformer = trans;
    }

    /**
     * Determines whether the endpoint should deal with requests as streams
     * 
     * @param stream
     *            true if the request should be streamed
     */
    public void setStreaming(boolean stream)
    {
        this.streaming = stream;
    }

    /**
     * Sets a property on the endpoint
     *
     * @param key   the property key
     * @param value the value of the property
     */
    public void setProperty(String key, Object value) {
        properties.put(key, value);
    }
}
