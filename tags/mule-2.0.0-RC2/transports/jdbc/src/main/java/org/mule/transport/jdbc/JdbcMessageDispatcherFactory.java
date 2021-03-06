/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.jdbc;

import org.mule.api.MuleException;
import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.api.transport.MessageDispatcher;
import org.mule.transport.AbstractMessageDispatcherFactory;

/**
 * Creates JdbcMessageDispatchers.
 */
public class JdbcMessageDispatcherFactory extends AbstractMessageDispatcherFactory
{

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.api.transport.MessageDispatcherFactory#create(org.mule.api.transport.Connector)
     */
    public MessageDispatcher create(ImmutableEndpoint endpoint) throws MuleException
    {
        return new JdbcMessageDispatcher(endpoint);
    }

}
