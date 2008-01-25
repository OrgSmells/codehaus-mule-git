/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.http.servlet;

import org.mule.api.MuleMessage;
import org.mule.transport.http.HttpConnector;
import org.mule.transport.http.HttpConstants;
import org.mule.transport.http.HttpResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.Header;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A base servlet used to receive requests from a servlet container and route
 * them into Mule
 */

public abstract class AbstractReceiverServlet extends HttpServlet
{
    /**
     * logger used by this class
     */
    protected transient Log logger = LogFactory.getLog(getClass());

    public static final String REQUEST_TIMEOUT_PROPERTY = "org.mule.servlet.timeout";
    public static final String FEEDBACK_PROPERTY = "org.mule.servlet.feedback";
    public static final String DEFAULT_CONTENT_TYPE_PROPERTY = "org.mule.servlet.default.content.type";

    /** The name of the servlet connector to use with this Servlet */
    public static final String SERVLET_CONNECTOR_NAME_PROPERTY = "org.mule.servlet.connector.name";

    public static final String PAYLOAD_PARAMETER_NAME = "org.mule.servlet.payload.param";
    public static final String DEFAULT_PAYLOAD_PARAMETER_NAME = "payload";

    public static final long DEFAULT_GET_TIMEOUT = 10000L;

    protected String payloadParameterName;
    protected long timeout = DEFAULT_GET_TIMEOUT;
    protected boolean feedback = true;
    protected String defaultContentType = HttpConstants.DEFAULT_CONTENT_TYPE;

    public final void init() throws ServletException
    {
        doInit();
    }

    public final void init(ServletConfig servletConfig) throws ServletException
    {
        String timeoutString = servletConfig.getInitParameter(REQUEST_TIMEOUT_PROPERTY);
        if (timeoutString != null)
        {
            timeout = Long.parseLong(timeoutString);
        }
        logger.info("Default request timeout for GET methods is: " + timeout);

        String feedbackString = servletConfig.getInitParameter(FEEDBACK_PROPERTY);
        if (feedbackString != null)
        {
            feedback = Boolean.valueOf(feedbackString).booleanValue();
        }
        logger.info("feedback is set to: " + feedback);

        String ct = servletConfig.getInitParameter(DEFAULT_CONTENT_TYPE_PROPERTY);
        if (ct != null)
        {
            defaultContentType = ct;
        }
        logger.info("Default content type is: " + defaultContentType);

        payloadParameterName = servletConfig.getInitParameter(PAYLOAD_PARAMETER_NAME);
        if (payloadParameterName == null)
        {
            payloadParameterName = DEFAULT_PAYLOAD_PARAMETER_NAME;
        }
        logger.info("Using payload param name: " + payloadParameterName);

        doInit(servletConfig);
    }

    protected void doInit(ServletConfig servletConfig) throws ServletException
    {
        // nothing to do
    }

    protected void doInit() throws ServletException
    {
        // nothing to do
    }

    protected void writeResponse(HttpServletResponse servletResponse, MuleMessage message) throws Exception
    {
        if (message == null)
        {
            servletResponse.setStatus(HttpServletResponse.SC_NO_CONTENT);
            if (feedback)
            {
                servletResponse.setStatus(HttpServletResponse.SC_OK);
                servletResponse.getWriter().write("Action was processed successfully. There was no result");
            }
        }
        else
        {
            HttpResponse httpResponse;

            if (message.getPayload() instanceof HttpResponse)
            {
                httpResponse = (HttpResponse)message.getPayload();
            }
            else
            {
                httpResponse = new HttpResponse();
                httpResponse.setBody(new ByteArrayInputStream(message.getPayloadAsBytes()));
                String ct = message.getStringProperty(HttpConstants.HEADER_CONTENT_TYPE, null);
                if(ct!=null)
                {
                    httpResponse.setHeader(new Header(HttpConstants.HEADER_CONTENT_TYPE, ct));
                }
                httpResponse.setStatusLine(httpResponse.getHttpVersion(), 
                    message.getIntProperty(HttpConnector.HTTP_STATUS_PROPERTY, HttpServletResponse.SC_OK));
            }

            Header contentTypeHeader = httpResponse.getFirstHeader(HttpConstants.HEADER_CONTENT_TYPE);
            String contentType = null;
            if (contentTypeHeader == null)
            {
                contentType = defaultContentType;
            }
            else
            {
                contentType = contentTypeHeader.getValue();
            }

            if (!servletResponse.isCommitted())
            {
                servletResponse.setStatus(httpResponse.getStatusCode());
            }
            if (!contentType.startsWith("text"))
            {
                servletResponse.setContentType(contentType);
                servletResponse.getOutputStream().write(httpResponse.getBodyBytes());
            }
            else
            {

                servletResponse.setContentType(contentType);
                // Encoding: this method will check the charset on the content type
                servletResponse.getWriter().write(httpResponse.getBodyString());
            }
        }
        servletResponse.flushBuffer();
    }

    protected void handleException(Throwable exception, String message, HttpServletResponse response)
    {
        logger.error("message: " + exception.getMessage(), exception);
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        try
        {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message + ": "
                                                                             + exception.getMessage());
        }
        catch (IOException e)
        {
            logger.error("Failed to sendError on response: " + e.getMessage(), e);
        }
    }
}
