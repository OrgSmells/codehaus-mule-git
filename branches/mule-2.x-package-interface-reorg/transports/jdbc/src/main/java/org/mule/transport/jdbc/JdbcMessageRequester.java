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

import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.transport.AbstractMessageRequester;


public class JdbcMessageRequester extends AbstractMessageRequester
{

    private JdbcConnector connector;

    public JdbcMessageRequester(ImmutableEndpoint endpoint)
    {
        super(endpoint);
        this.connector = (JdbcConnector) endpoint.getConnector();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.mule.transport.AbstractMessageDispatcher#doDispose()
     */
    protected void doDispose()
    {
        // template method
    }

    /**
     * Make a specific request to the underlying transport
     *
     * @param timeout the maximum time the operation should block before returning.
     *            The call should return immediately if there is data available. If
     *            no data becomes available before the timeout elapses, null will be
     *            returned
     * @return the result of the request wrapped in a MuleMessage object. Null will be
     *         returned if no data was available
     * @throws Exception if the call to the underlying protocol causes an exception
     */
    protected MuleMessage doRequest(long timeout) throws Exception
    {
        return doRequest(timeout, null);
    }

    /**
     * Make a specific request to the underlying transport
     * Special case: The event is need when doReceive was called from doSend
     * @param timeout only for compatibility with doRequest(long timeout)
     * @param event There is a need to get params from message
     * @return the result of the request wrapped in a MuleMessage object. Null will be
     *         returned if no data was available
     * @throws Exception if the call to the underlying protocol causes an exception
     */
    protected MuleMessage doRequest(long timeout, MuleEvent event) throws Exception
    {
        return JdbcMessageDispatcher.executeRequest(timeout, event, connector, endpoint);
    }


    protected void doConnect() throws Exception
    {
        // template method
    }

    protected void doDisconnect() throws Exception
    {
        // template method
    }

}