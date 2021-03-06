/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.endpoint;

import org.mule.api.MuleContext;
import org.mule.api.MuleMessage;
import org.mule.api.endpoint.EndpointURI;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.api.retry.RetryPolicyTemplate;
import org.mule.api.routing.filter.Filter;
import org.mule.api.security.EndpointSecurityFilter;
import org.mule.api.transaction.TransactionConfig;
import org.mule.api.transport.Connector;

import java.util.List;
import java.util.Map;

/**
 * Allow's EndpointURI to be set and changed dynamically by wrapping up an immutable endpoint instance.
 */
public class DynamicURIInboundEndpoint implements InboundEndpoint
{

    private static final long serialVersionUID = -2814979100270307813L;

    private InboundEndpoint endpoint;
    private EndpointURI dynamicEndpointURI;

    public DynamicURIInboundEndpoint(InboundEndpoint endpoint)
    {
        this(endpoint, null);
    }

    public DynamicURIInboundEndpoint(InboundEndpoint endpoint, EndpointURI dynamicEndpointURI)
    {
//        if (endpoint instanceof DynamicURIInboundEndpoint) 
//        {
//            throw new IllegalArgumentException("Dynamic endpoints can only wrap immuntable InboundEndpoint instances!");
//        }
//        
        this.endpoint = endpoint;
        setEndpointURI(dynamicEndpointURI);
    }

    public EndpointURI getEndpointURI()
    {
        if (dynamicEndpointURI != null)
        {
            return dynamicEndpointURI;
        }
        else
        {
            return endpoint.getEndpointURI();
        }
    }

    public void setEndpointURI(EndpointURI dynamicEndpointURI)
    {
        this.dynamicEndpointURI = dynamicEndpointURI;
    }

    public RetryPolicyTemplate getRetryPolicyTemplate()
    {
        return endpoint.getRetryPolicyTemplate();
    }

    public Connector getConnector()
    {
        return endpoint.getConnector();
    }

    public String getEncoding()
    {
        return endpoint.getEncoding();
    }

    public Filter getFilter()
    {
        return endpoint.getFilter();
    }

    public String getInitialState()
    {
        return endpoint.getInitialState();
    }

    public MuleContext getMuleContext()
    {
        return endpoint.getMuleContext();
    }

    public String getName()
    {
        return endpoint.getName();
    }

    public Map getProperties()
    {
        return endpoint.getProperties();
    }

    public Object getProperty(Object key)
    {
        return endpoint.getProperty(key);
    }

    public String getProtocol()
    {
        return endpoint.getProtocol();
    }

    public int getResponseTimeout()
    {
        return endpoint.getResponseTimeout();
    }

    public List getResponseTransformers()
    {
        return endpoint.getResponseTransformers();
    }

    public EndpointSecurityFilter getSecurityFilter()
    {
        return endpoint.getSecurityFilter();
    }

    public TransactionConfig getTransactionConfig()
    {
        return endpoint.getTransactionConfig();
    }

    public List getTransformers()
    {
        return endpoint.getTransformers();
    }

    public boolean isDeleteUnacceptedMessages()
    {
        return endpoint.isDeleteUnacceptedMessages();
    }

    public boolean isReadOnly()
    {
        return endpoint.isReadOnly();
    }

    public boolean isSynchronous()
    {
        return endpoint.isSynchronous();
    }


    public MuleMessage request(long timeout) throws Exception
    {
        return getConnector().request(this, timeout);
    }

    public String getEndpointBuilderName()
    {
        return endpoint.getEndpointBuilderName();
    }

    public boolean isProtocolSupported(String protocol)
    {
        return getConnector().supportsProtocol(protocol);
    }

    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dynamicEndpointURI == null) ? 0 : dynamicEndpointURI.hashCode());
        result = prime * result + ((endpoint == null) ? 0 : endpoint.hashCode());
        return result;
    }

    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final DynamicURIInboundEndpoint other = (DynamicURIInboundEndpoint) obj;
        if (dynamicEndpointURI == null)
        {
            if (other.dynamicEndpointURI != null)
            {
                return false;
            }
        }
        else if (!dynamicEndpointURI.equals(other.dynamicEndpointURI))
        {
            return false;
        }
        if (endpoint == null)
        {
            if (other.endpoint != null)
            {
                return false;
            }
        }
        else if (!endpoint.equals(other.endpoint))
        {
            return false;
        }
        return true;
    }

}
