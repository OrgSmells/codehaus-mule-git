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

package org.mule.providers.http.servlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.providers.WriterMessageAdapter;
import org.mule.providers.http.HttpConstants;
import org.mule.providers.http.HttpResponse;
import org.mule.umo.UMOMessage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 *
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
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
        if (timeoutString != null) {
            timeout = Long.parseLong(timeoutString);
        }
        logger.info("Default request timeout for GET methods is: " + timeout);

        String feedbackString = servletConfig.getInitParameter(FEEDBACK_PROPERTY);
        if (feedbackString != null) {
            feedback = Boolean.valueOf(feedbackString).booleanValue();
        }
        logger.info("feedback is set to: " + feedback);

        String ct = servletConfig.getInitParameter(DEFAULT_CONTENT_TYPE_PROPERTY);
        if (ct != null) {
            defaultContentType = ct;
        }
        logger.info("Default content type is: " + defaultContentType);

        payloadParameterName = servletConfig.getInitParameter(PAYLOAD_PARAMETER_NAME);
        if (payloadParameterName == null) {
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

    protected void writeResponse(HttpServletResponse servletResponse, UMOMessage message)
            throws Exception
    {
        if (message == null) {
            servletResponse.setStatus(HttpServletResponse.SC_NO_CONTENT);
            if (feedback) {
                servletResponse.setStatus(HttpServletResponse.SC_OK);
                servletResponse.getWriter().write(
                        "Action was processed successfully. There was no result");
            }
        }
        else {
            HttpResponse httpResponse;
            if (message.getAdapter() instanceof WriterMessageAdapter) {
                WriterMessageAdapter adapter = (WriterMessageAdapter)message.getAdapter();
                httpResponse = new HttpResponse();
                httpResponse.setBodyString(adapter.getPayloadAsString());
            }
            else {
                httpResponse = (HttpResponse)message.getPayload();
            }

            String contentType = httpResponse.getFirstHeader(HttpConstants.HEADER_CONTENT_TYPE)
                    .getValue();
            if (contentType == null) {
                contentType = defaultContentType;
            }
            if (!contentType.startsWith("text")) {
                servletResponse.setContentType(contentType);
                servletResponse.getOutputStream().write(httpResponse.getBodyBytes());
            }
            else {

                servletResponse.setContentType(contentType);
                // Encoding: this method will check the charset on the content type
                servletResponse.getWriter().write(httpResponse.getBodyString());
            }
            if (!servletResponse.isCommitted()) {
                servletResponse.setStatus(HttpServletResponse.SC_OK);
            }
        }
        servletResponse.flushBuffer();
    }

    protected void handleException(Throwable exception, String message, HttpServletResponse response)
    {
        logger.error("message: " + exception.getMessage(), exception);
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        try {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message + ": "
                    + exception.getMessage());
        }
        catch (IOException e) {
            logger.error("Failed to sendError on response: " + e.getMessage(), e);
        }
    }
}