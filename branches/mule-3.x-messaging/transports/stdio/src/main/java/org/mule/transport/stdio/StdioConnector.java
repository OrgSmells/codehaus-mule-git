/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.stdio;

import org.mule.api.endpoint.InboundEndpoint;
import org.mule.api.service.Service;
import org.mule.api.transport.MessageReceiver;
import org.mule.transport.AbstractConnector;
import org.mule.transport.AbstractPollingMessageReceiver;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;

/**
 * <code>StdioConnector</code> can send and receive Mule events over IO streams.
 */

public abstract class StdioConnector extends AbstractConnector
{

    public static final String STDIO = "stdio";
    public static final String STREAM_SYSTEM_IN = "system.in";
    public static final String STREAM_SYSTEM_OUT = "system.out";
    public static final String STREAM_SYSTEM_ERR = "system.err";

    protected OutputStream outputStream;
    protected InputStream inputStream;

    @Override
    public MessageReceiver createReceiver(Service service, InboundEndpoint endpoint) throws Exception
    {
        return serviceDescriptor.createMessageReceiver(this, service, endpoint,
            new Object[]{new Long(AbstractPollingMessageReceiver.DEFAULT_POLL_FREQUENCY)});
    }

    @Override
    public void doStop()
    {
        // template method
    }

    @Override
    protected void doDispose()
    {
        IOUtils.closeQuietly(inputStream);
        IOUtils.closeQuietly(outputStream);
    }

    @Override
    public void doStart()
    {
        // template method
    }

    public String getProtocol()
    {
        return STDIO;
    }

    public InputStream getInputStream()
    {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream)
    {
        this.inputStream = inputStream;
    }

    public OutputStream getOutputStream()
    {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream)
    {
        this.outputStream = outputStream;
    }

}
