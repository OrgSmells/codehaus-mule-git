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

import org.mule.impl.ManagementContextAware;
import org.mule.umo.UMOManagementContext;
import org.mule.umo.endpoint.EndpointException;
import org.mule.umo.endpoint.UMOEndpointURI;
import org.mule.umo.endpoint.UMOImmutableEndpoint;

public class EndpointURIEndpointBuilder extends AbstractEndpointBuilder implements ManagementContextAware
{

    public EndpointURIEndpointBuilder()
    {
        // blank
    }

    public EndpointURIEndpointBuilder(EndpointURIEndpointBuilder global) throws EndpointException
    {
        // can't (concisely) use setters where null is a possibility
        // for consistency, set directly on all fields (this also avoids logic in getters)
        uriBuilder = global.uriBuilder;
        connector = global.connector;
        transformers = global.transformers;
        responseTransformers = global.responseTransformers;
        name = global.name; // this seems a bit odd, but is tested for in the big spring config test case
        properties = global.properties;
        transactionConfig = global.transactionConfig;
        filter = global.filter;
        deleteUnacceptedMessages = global.deleteUnacceptedMessages;
        securityFilter = global.securityFilter;
        synchronous = global.synchronous;
        remoteSync = global.remoteSync;
        remoteSyncTimeout = global.remoteSyncTimeout;
        encoding = global.encoding;
        connectionStrategy = global.connectionStrategy;
    }

    public EndpointURIEndpointBuilder(URIBuilder URIBuilder, UMOManagementContext managementContext)
    {
        this.managementContext = managementContext;
        this.uriBuilder = URIBuilder;
    }

    /**
     * @deprecated
     */
    public EndpointURIEndpointBuilder(String address, UMOManagementContext managementContext)
    {
        this(new URIBuilder(address), managementContext);
    }

    /**
     * @deprecated
     */
    public EndpointURIEndpointBuilder(UMOEndpointURI endpointURI, UMOManagementContext managementContext)
    {
        this(new URIBuilder(endpointURI), managementContext);
    }

    /**
     * @deprecated
     */
    public EndpointURIEndpointBuilder(UMOImmutableEndpoint source, UMOManagementContext managementContext)
    {
        this(source.getEndpointURI(), managementContext);
        setName(source.getName());
        setEncoding(source.getEncoding());
        setConnector(source.getConnector());
        setTransformers(source.getTransformers());
        setResponseTransformers(source.getResponseTransformers());
        setProperties(source.getProperties());
        setTransactionConfig(source.getTransactionConfig());
        setDeleteUnacceptedMessages(source.isDeleteUnacceptedMessages());
        setInitialState(source.getInitialState());
        setRemoteSyncTimeout(source.getRemoteSyncTimeout());
        setRemoteSync(source.isRemoteSync());
        setFilter(source.getFilter());
        setSecurityFilter(source.getSecurityFilter());
        setConnectionStrategy(source.getConnectionStrategy());
        setSynchronous(source.isSynchronous());
        setManagementContext(source.getManagementContext());
    }

}
