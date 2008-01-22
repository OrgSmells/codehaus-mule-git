/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.http.jetty;

import org.mule.api.endpoint.EndpointException;
import org.mule.api.transport.MessageReceiver;
import org.mule.transport.http.i18n.HttpMessages;
import org.mule.transport.http.servlet.MuleReceiverServlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class JettyReceiverServlet extends MuleReceiverServlet
{
    /**
     * Serial version
     */
    private static final long serialVersionUID = 238326861089137293L;

    private MessageReceiver receiver;

    //@Override
    protected void doInit(ServletConfig servletConfig) throws ServletException
    {
        final ServletContext servletContext = servletConfig.getServletContext();
        synchronized (servletContext)
        {
            receiver = (MessageReceiver) servletContext.getAttribute("messageReceiver");
        }
        if (receiver == null)
        {
            throw new ServletException(HttpMessages.receiverPropertyNotSet().toString());
        }
    }

    //@Override
    protected MessageReceiver getReceiverForURI(HttpServletRequest httpServletRequest)
        throws EndpointException
    {
        return receiver;
    }
}
