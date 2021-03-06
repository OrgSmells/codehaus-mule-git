/*
 * $Header$
 * $Revision$
 * $Date$
 * ------------------------------------------------------------------------------------------------------
 *
 * Copyright (c) SymphonySoft Limited. All rights reserved.
 * http://www.symphonysoft.com
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.mule.providers.http.transformers;

import org.mule.MuleManager;
import org.mule.config.MuleProperties;
import org.mule.config.i18n.Message;
import org.mule.config.i18n.Messages;
import org.mule.providers.http.HttpConnector;
import org.mule.providers.http.HttpConstants;
import org.mule.transformers.AbstractEventAwareTransformer;
import org.mule.umo.UMOEventContext;
import org.mule.umo.UMOMessage;
import org.mule.umo.transformer.TransformerException;
import org.mule.util.Utility;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <code>UMOMessageToResponseString</code> converts a UMOMEssage into an Http
 * response.
 * 
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */

public class UMOMessageToResponseString extends AbstractEventAwareTransformer
{
    private List headers = null;
    private SimpleDateFormat format = null;
    private String server = null;

    public UMOMessageToResponseString()
    {
        registerSourceType(Object.class);
        setReturnClass(Object.class);

        headers = new ArrayList(Arrays.asList(HttpConstants.RESPONSE_HEADER_NAMES));
        format = new SimpleDateFormat(HttpConstants.DATE_FORMAT);
        server = MuleManager.getConfiguration().getProductName() + "/"
                + MuleManager.getConfiguration().getProductVersion();
    }

    public Object transform(Object src, UMOEventContext context) throws TransformerException
    {
        int status = context.getIntProperty(HttpConnector.HTTP_STATUS_PROPERTY, HttpConstants.SC_OK);
        String version = (String) context.getProperty(HttpConnector.HTTP_VERSION_PROPERTY, HttpConstants.HTTP11);
        String date = format.format(new Date());
        byte[] response = null;
        String contentType = (String) context.getProperty(HttpConstants.HEADER_CONTENT_TYPE);
        if (src instanceof byte[]) {
            response = (byte[]) src;
        } else if (contentType != null && contentType.startsWith("text/")) {
            response = src.toString().getBytes();
        } else {
            try {
                response = Utility.objectToByteArray(src);
            } catch (IOException e) {
                throw new TransformerException(new Message(Messages.TRANSFORM_FAILED_FROM_X_TO_X, "Object", "byte[]"),
                                               this,
                                               e);
            }
        }

        StringBuffer httpMessage = new StringBuffer();

        httpMessage.append(version).append(" ");
        httpMessage.append(status).append(HttpConstants.CRLF);
        httpMessage.append(HttpConstants.HEADER_DATE);
        httpMessage.append(": ").append(date).append(HttpConstants.CRLF);
        httpMessage.append(HttpConstants.HEADER_SERVER);
        httpMessage.append(": ").append(server).append(HttpConstants.CRLF);
        if (context.getProperty(HttpConstants.HEADER_EXPIRES) == null) {
            httpMessage.append(HttpConstants.HEADER_EXPIRES);
            httpMessage.append(": ").append(date).append(HttpConstants.CRLF);
        }

        httpMessage.append(HttpConstants.HEADER_CONTENT_TYPE);
        if (contentType == null) {
            httpMessage.append(": ").append("text/xml").append(HttpConstants.CRLF);
        } else {
            httpMessage.append(": ").append(contentType).append(HttpConstants.CRLF);
        }

        httpMessage.append(HttpConstants.HEADER_CONTENT_LENGTH);
        httpMessage.append(": ").append(response.length).append(HttpConstants.CRLF);

        String headerName;
        String value;
        for (Iterator iterator = headers.iterator(); iterator.hasNext();) {
            headerName = (String) iterator.next();
            value = context.getStringProperty(headerName);
            if (value != null) {
                httpMessage.append(headerName).append(": ").append(value);
                httpMessage.append(HttpConstants.CRLF);
            }
        }
        // Custom headers
        Map customHeaders = (Map) context.getProperty(HttpConnector.HTTP_CUSTOM_HEADERS_MAP_PROPERTY);
        if (customHeaders != null) {
            Map.Entry entry;
            for (Iterator iterator = customHeaders.entrySet().iterator(); iterator.hasNext();) {
                entry = (Map.Entry) iterator.next();
                httpMessage.append(entry.getKey()).append(": ").append(entry.getValue());
                httpMessage.append(HttpConstants.CRLF);
            }
        }

        // Mule properties
        UMOMessage m = context.getMessage();
        String user = (String) m.getProperty(MuleProperties.MULE_USER_PROPERTY);
        if (user != null) {
            httpMessage.append("X-" + MuleProperties.MULE_USER_PROPERTY).append(": ").append(user);
            httpMessage.append(HttpConstants.CRLF);
        }
        if (m.getCorrelationId() != null) {
            httpMessage.append("X-" + MuleProperties.MULE_CORRELATION_ID_PROPERTY)
                       .append(": ")
                       .append(m.getCorrelationId());
            httpMessage.append(HttpConstants.CRLF);
            httpMessage.append("X-" + MuleProperties.MULE_CORRELATION_GROUP_SIZE_PROPERTY)
                       .append(": ")
                       .append(m.getCorrelationGroupSize());
            httpMessage.append(HttpConstants.CRLF);
            httpMessage.append("X-" + MuleProperties.MULE_CORRELATION_SEQUENCE_PROPERTY)
                       .append(": ")
                       .append(m.getCorrelationSequence());
            httpMessage.append(HttpConstants.CRLF);
        }
        if (m.getReplyTo() != null) {
            httpMessage.append("X-" + MuleProperties.MULE_REPLY_TO_PROPERTY).append(": ").append(m.getReplyTo()
                                                                                                  .toString());
            httpMessage.append(HttpConstants.CRLF);
        }

        // End header
        httpMessage.append(HttpConstants.CRLF);
        byte[] resultPayload = new byte[httpMessage.length() + response.length];
        System.arraycopy(httpMessage.toString().getBytes(), 0, resultPayload, 0, httpMessage.length());
        System.arraycopy(response, 0, resultPayload, httpMessage.length(), response.length);
        return resultPayload;
    }
}
