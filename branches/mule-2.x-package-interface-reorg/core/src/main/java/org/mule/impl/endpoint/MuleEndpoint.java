/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.impl.endpoint;

import org.mule.api.MuleContext;
import org.mule.config.i18n.CoreMessages;
import org.mule.impl.ImmutableMuleEndpoint;
import org.mule.impl.MuleContextAware;
import org.mule.providers.ConnectionStrategy;
import org.mule.transformers.TransformerUtils;
import org.mule.umo.UMOFilter;
import org.mule.umo.UMOTransactionConfig;
import org.mule.umo.endpoint.EndpointException;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.endpoint.UMOEndpointURI;
import org.mule.umo.lifecycle.InitialisationException;
import org.mule.umo.provider.UMOConnector;
import org.mule.umo.security.UMOEndpointSecurityFilter;

import java.util.List;
import java.util.Map;

import edu.emory.mathcs.backport.java.util.concurrent.atomic.AtomicReference;

/**
 * <code>MuleEndpoint</code> describes a Provider in the Mule Server. A endpoint is
 * a grouping of an endpoint, an endpointUri and a transformer.
 */
public class MuleEndpoint extends ImmutableMuleEndpoint implements UMOEndpoint, MuleContextAware
{
    private static final long serialVersionUID = 3883445445846168147L;
    
    public static final String ALWAYS_CREATE_STRING = "ALWAYS_CREATE";
    public static final String NEVER_CREATE_STRING = "NEVER_CREATE";

    protected MuleEndpoint()
    {
        super();
    }

    public void setEndpointURI(UMOEndpointURI endpointUri) throws EndpointException
    {
        if (connector != null && endpointUri != null
            && !connector.supportsProtocol(endpointUri.getFullScheme()))
        {
            throw new IllegalArgumentException(
                CoreMessages.connectorSchemeIncompatibleWithEndpointScheme(connector.getProtocol(),
                endpointUri).getMessage());
        }
        if (endpointUri==null)
        {
            throw new NullPointerException(CoreMessages.objectIsNull("endpointURI").getMessage());
        }

        this.endpointUri = endpointUri;
        if (endpointUri != null)
        {
            properties.putAll(endpointUri.getParams());
        }
        try
        {
            endpointUri.initialise();
        }
        catch (InitialisationException e)
        {
            throw new EndpointException(e);
        }
    }

    public void setEncoding(String endpointEncoding)
    {
        this.endpointEncoding = endpointEncoding;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public void setConnector(UMOConnector connector)
    {
        if (connector != null && endpointUri != null
            && !connector.supportsProtocol(endpointUri.getFullScheme()))
        {
            throw new IllegalArgumentException(
                CoreMessages.connectorSchemeIncompatibleWithEndpointScheme(connector.getProtocol(),
                endpointUri).getMessage());
        }
        this.connector = connector;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setTransformers(List transformers)
    {
        setTransformers(this.transformers, transformers);
    }

    protected void setTransformers(AtomicReference reference, List transformers)
    {
        TransformerUtils.discourageNullTransformers(transformers);
        reference.set(transformers);
        updateTransformerEndpoints(reference);
    }

    public void setProperties(Map props)
    {
        properties.clear();
        properties.putAll(props);
    }

    public boolean isReadOnly()
    {
        return false;
    }

    public void setTransactionConfig(UMOTransactionConfig config)
    {
        transactionConfig = config;
    }

    public void setFilter(UMOFilter filter)
    {
        this.filter = filter;
    }

    /**
     * If a filter is configured on this endpoint, this property will determine if
     * message that are not excepted by the filter are deleted
     * 
     * @param delete if message should be deleted, false otherwise
     */
    public void setDeleteUnacceptedMessages(boolean delete)
    {
        deleteUnacceptedMessages = delete;
    }

    /**
     * Sets an UMOEndpointSecurityFilter for this endpoint. If a filter is set all
     * traffice via this endpoint with be subject to authentication.
     * 
     * @param filter the UMOSecurityFilter responsible for authenticating message
     *            flow via this endpoint.
     * @see org.mule.umo.security.UMOEndpointSecurityFilter
     */
    public void setSecurityFilter(UMOEndpointSecurityFilter filter)
    {
        securityFilter = filter;
        if (securityFilter != null)
        {
            securityFilter.setEndpoint(this);
        }
    }

    /**
     * Determines if requests originating from this endpoint should be synchronous
     * i.e. execute in a single thread and possibly return an result. This property
     * is only used when the endpoint is of type 'receiver'.
     * 
     * @param synchronous whether requests on this endpoint should execute in a
     *            single thread. This property is only used when the endpoint is of
     *            type 'receiver'
     */
    public void setSynchronous(boolean synchronous)
    {
        this.synchronous = synchronous;
    }

    /**
     * For certain providers that support the notion of a backchannel such as sockets
     * (outputStream) or Jms (ReplyTo) Mule can automatically wait for a response
     * from a backchannel when dispatching over these protocols. This is different
     * for synchronous as synchronous behavior only applies to in
     * 
     * @param value whether the endpoint should perfrom sync receives
     */
    public void setRemoteSync(boolean value)
    {
        this.remoteSync = value;
        if (value)
        {
            this.synchronous = true;
        }
    }

    /**
     * The timeout value for remoteSync invocations
     * 
     * @param timeout the timeout in milliseconds
     */
    public void setRemoteSyncTimeout(int timeout)
    {
        this.remoteSyncTimeout = new Integer(timeout);
    }

    /**
     * Sets the state the endpoint will be loaded in. The States are 'stopped' and
     * 'started' (default)
     * 
     * @param state
     */
    public void setInitialState(String state)
    {
        this.initialState = state;
    }

    public void setResponseTransformers(List transformers)
    {
        setTransformers(responseTransformers, transformers);
        updateTransformerEndpoints(responseTransformers);
    }

    /**
     * Sets a property on the endpoint
     * 
     * @param key the property key
     * @param value the value of the property
     */
    public void setProperty(String key, Object value)
    {
        properties.put(key, value);
    }


    /**
     * Setter for property 'connectionStrategy'.
     *
     * @param connectionStrategy Value to set for property 'connectionStrategy'.
     */
    public void setConnectionStrategy(ConnectionStrategy connectionStrategy)
    {
        this.connectionStrategy = connectionStrategy;
    }


    public void setMuleContext(MuleContext context)
    {
        this.muleContext = context;
    }
}
