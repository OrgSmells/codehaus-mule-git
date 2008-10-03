/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.http.transformers;

import org.mule.api.MuleMessage;
import org.mule.api.config.MuleProperties;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.transformer.TransformerException;
import org.mule.config.MuleManifest;
import org.mule.transformer.AbstractMessageAwareTransformer;
import org.mule.transport.NullPayload;
import org.mule.transport.http.HttpConnector;
import org.mule.transport.http.HttpConstants;
import org.mule.transport.http.HttpResponse;
import org.mule.util.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpVersion;

/**
 * <code>UMOMessageToHttpResponse</code> converts a UMOMEssage into an Http
 * response.
 */

public class MuleMessageToHttpResponse extends AbstractMessageAwareTransformer
{
    public static final String CUSTOM_HEADER_PREFIX = "";

    // @GuardedBy("itself")
    private SimpleDateFormat format;
    private String server;

    public MuleMessageToHttpResponse()
    {
        registerSourceType(Object.class);
        setReturnClass(HttpResponse.class);
    }


    //@Override
    public void initialise() throws InitialisationException
    {
        format = new SimpleDateFormat(HttpConstants.DATE_FORMAT, Locale.US);

        // When running with the source code, Meta information is not set
        // so product name and version are not available, hence we hard code
        if (MuleManifest.getProductName() == null)
        {
            server = "Mule/SNAPSHOT";
        }
        else
        {
            server = MuleManifest.getProductName() + "/"
                     + MuleManifest.getProductVersion();
        }
    }

    public Object transform(MuleMessage msg, String outputEncoding) throws TransformerException
    {
        Object src = msg.getPayload();
        // Send back the exception payload if one has been set
        if (msg.getExceptionPayload() != null)
        {
            // src = context.getMessage().getExceptionPayload();
        }

        // Note this transformer excepts Null as we must always return a result
        // from the Http
        // connector if a response transformer is present
        if (src instanceof NullPayload)
        {
            src = StringUtils.EMPTY;
        }

        try
        {
            HttpResponse response;
            if (src instanceof HttpResponse)
            {
                response = (HttpResponse)src;
            }
            else
            {
                response = createResponse(src, outputEncoding, msg);
            }

            // Ensure there's a content type header
            if (!response.containsHeader(HttpConstants.HEADER_CONTENT_TYPE))
            {
                response.addHeader(new Header(HttpConstants.HEADER_CONTENT_TYPE,
                    HttpConstants.DEFAULT_CONTENT_TYPE));
            }

            // Ensure there's a content length or transfer encoding header
            if (!response.containsHeader(HttpConstants.HEADER_CONTENT_LENGTH)
                && !response.containsHeader(HttpConstants.HEADER_TRANSFER_ENCODING))
            {
                if (response.hasBody())
                {
                    long len = response.getContentLength();
                    if (len < 0)
                    {
                        if (response.getHttpVersion().lessEquals(HttpVersion.HTTP_1_0))
                        {
                            throw new IOException("Chunked encoding not supported for HTTP version "
                                                  + response.getHttpVersion());
                        }
                        Header header = new Header(HttpConstants.HEADER_TRANSFER_ENCODING, "chunked");
                        response.addHeader(header);
                    }
                    else
                    {
                        Header header = new Header(HttpConstants.HEADER_CONTENT_LENGTH, Long.toString(len));
                        response.setHeader(header);
                    }
                }
                else
                {
                    Header header = new Header(HttpConstants.HEADER_CONTENT_LENGTH, "0");
                    response.addHeader(header);
                }
            }

            // See if the the client explicitly handles connection persistence
            String connHeader = msg.getStringProperty(HttpConstants.HEADER_CONNECTION, null);
            if (connHeader != null)
            {
                if (connHeader.equalsIgnoreCase("keep-alive"))
                {
                    response.setKeepAlive(true);
                }
                if (connHeader.equalsIgnoreCase("close"))
                {
                    response.setKeepAlive(false);
                }
            }
            
            if ("HEAD".equalsIgnoreCase(msg.getStringProperty(HttpConnector.HTTP_METHOD_PROPERTY, null)))
            {
                // this is a head request, we don't want to send the actual content
                response.setBody((MuleMessage) null);
            }
            return response;
        }
        catch (IOException e)
        {
            throw new TransformerException(this, e);
        }

    }

    protected HttpResponse createResponse(Object src, String encoding, MuleMessage msg)
        throws IOException, TransformerException
    {
        HttpResponse response = new HttpResponse();

        int status = msg.getIntProperty(HttpConnector.HTTP_STATUS_PROPERTY, HttpConstants.SC_OK);
        String version = msg.getStringProperty(HttpConnector.HTTP_VERSION_PROPERTY, HttpConstants.HTTP11);
        String etag = msg.getStringProperty(HttpConstants.HEADER_ETAG, null);
        
        String date;
        synchronized (format)
        {
            date = format.format(new Date());
        }

        String contentType = msg.getStringProperty(HttpConstants.HEADER_CONTENT_TYPE,
            HttpConstants.DEFAULT_CONTENT_TYPE);

        response.setStatusLine(HttpVersion.parse(version), status);
        response.setHeader(new Header(HttpConstants.HEADER_CONTENT_TYPE, contentType));
        response.setHeader(new Header(HttpConstants.HEADER_DATE, date));
        response.setHeader(new Header(HttpConstants.HEADER_SERVER, server));
        if (msg.getProperty(HttpConstants.HEADER_EXPIRES) == null)
        {
            response.setHeader(new Header(HttpConstants.HEADER_EXPIRES, date));
        }
        if (etag != null)
        {
            response.setHeader(new Header(HttpConstants.HEADER_ETAG, etag));
        }
        response.setFallbackCharset(encoding);

        Collection headerNames = HttpConstants.RESPONSE_HEADER_NAMES.values();
        String headerName, value;
        for (Iterator iterator = headerNames.iterator(); iterator.hasNext();)
        {
            headerName = (String)iterator.next();
            value = msg.getStringProperty(headerName, null);
            if (value != null)
            {
                response.setHeader(new Header(headerName, value));
            }
        }

        // Custom responseHeaderNames
        Map customHeaders = (Map)msg.getProperty(HttpConnector.HTTP_CUSTOM_HEADERS_MAP_PROPERTY);
        if (customHeaders != null)
        {
            Map.Entry entry;
            for (Iterator iterator = customHeaders.entrySet().iterator(); iterator.hasNext();)
            {
                entry = (Map.Entry)iterator.next();
                if (entry.getValue() != null)
                {
                    response.setHeader(new Header(entry.getKey().toString(), entry.getValue().toString()));
                }
            }
        }

        // Mule properties
        String user = msg.getStringProperty(MuleProperties.MULE_USER_PROPERTY, null);
        if (user != null)
        {
            response.setHeader(new Header(CUSTOM_HEADER_PREFIX + MuleProperties.MULE_USER_PROPERTY, user));
        }
        if (msg.getCorrelationId() != null)
        {
            response.setHeader(new Header(CUSTOM_HEADER_PREFIX + MuleProperties.MULE_CORRELATION_ID_PROPERTY,
                msg.getCorrelationId()));
            response.setHeader(new Header(CUSTOM_HEADER_PREFIX
                                          + MuleProperties.MULE_CORRELATION_GROUP_SIZE_PROPERTY,
                String.valueOf(msg.getCorrelationGroupSize())));
            response.setHeader(new Header(CUSTOM_HEADER_PREFIX
                                          + MuleProperties.MULE_CORRELATION_SEQUENCE_PROPERTY,
                String.valueOf(msg.getCorrelationSequence())));
        }
        if (msg.getReplyTo() != null)
        {
            response.setHeader(new Header(CUSTOM_HEADER_PREFIX + MuleProperties.MULE_REPLY_TO_PROPERTY,
                msg.getReplyTo().toString()));
        }
        
        response.setBody(msg);
        
        return response;
    }

    public boolean isAcceptNull()
    {
        return true;
    }
}
