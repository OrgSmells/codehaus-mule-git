/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.http.transformers;

import org.mule.MuleManager;
import org.mule.config.MuleProperties;
import org.mule.providers.NullPayload;
import org.mule.providers.http.HttpConnector;
import org.mule.providers.http.HttpConstants;
import org.mule.providers.http.i18n.HttpMessages;
import org.mule.transformers.AbstractEventAwareTransformer;
import org.mule.transformers.simple.SerializableToByteArray;
import org.mule.umo.UMOEventContext;
import org.mule.umo.UMOMessage;
import org.mule.umo.transformer.TransformerException;
import org.mule.util.StringUtils;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Iterator;

import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpVersion;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.EntityEnclosingMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.HeadMethod;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.OptionsMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.methods.TraceMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

/**
 * <code>ObjectToHttpClientMethodRequest</code> transforms a UMOMessage into a
 * HttpClient HttpMethod that represents an HttpRequest.
 */

public class ObjectToHttpClientMethodRequest extends AbstractEventAwareTransformer
{
    private final SerializableToByteArray serializableToByteArray;

    public ObjectToHttpClientMethodRequest()
    {
        setReturnClass(HttpMethod.class);
        serializableToByteArray = new SerializableToByteArray();
    }

    private int addParameters(String queryString, PostMethod postMethod)
    {
        // Parse the HTTP argument list and convert to a NameValuePair
        // collection

        if (StringUtils.isEmpty(queryString))
        {
            return 0;
        }

        String currentParam;
        int equals;
        equals = queryString.indexOf("&");
        if (equals > -1)
        {
            currentParam = queryString.substring(0, equals);
            queryString = queryString.substring(equals + 1);
        }
        else
        {
            currentParam = queryString;
            queryString = StringUtils.EMPTY;
        }
        int parameterIndex = -1;
        while (StringUtils.isNotBlank(currentParam))
        {
            String paramName, paramValue;
            equals = currentParam.indexOf("=");
            if (equals > -1)
            {
                paramName = currentParam.substring(0, equals);
                paramValue = currentParam.substring(equals + 1);
                parameterIndex++;
                postMethod.addParameter(paramName, paramValue);
            }
            equals = queryString.indexOf("&");
            if (equals > -1)
            {
                currentParam = queryString.substring(0, equals);
                queryString = queryString.substring(equals + 1);
            }
            else
            {
                currentParam = queryString;
                queryString = StringUtils.EMPTY;
            }
        }
        return parameterIndex + 1;
    }

    public Object transform(Object src, String encoding, UMOEventContext context) throws TransformerException
    {
        UMOMessage msg = context.getMessage();

        String endpoint = msg.getStringProperty(MuleProperties.MULE_ENDPOINT_PROPERTY, null);
        if (endpoint == null)
        {
            throw new TransformerException(
                HttpMessages.eventPropertyNotSetCannotProcessRequest(MuleProperties.MULE_ENDPOINT_PROPERTY),
                this);
        }

        String method = msg.getStringProperty(HttpConnector.HTTP_METHOD_PROPERTY, "POST");
        try
        {
            URI uri = new URI(endpoint);
            HttpMethod httpMethod = null;

            if (HttpConstants.METHOD_GET.equals(method))
            {
                httpMethod = new GetMethod(uri.toString());

                String paramName = URLEncoder.encode(msg.getStringProperty(
                    HttpConnector.HTTP_GET_BODY_PARAM_PROPERTY,
                    HttpConnector.DEFAULT_HTTP_GET_BODY_PARAM_PROPERTY), encoding);
                String paramValue = URLEncoder.encode(src.toString(), encoding);

                // MULE-3236: use the raw query here so that properly encoded parameters from the 
                // config file stay intact
                String query = uri.getRawQuery();
                if (!(src instanceof NullPayload) && !StringUtils.EMPTY.equals(src))
                {
                    if (query == null)
                    {
                        query = paramName + "=" + paramValue;
                    }
                    else
                    {
                        query += "&" + paramName + "=" + paramValue;
                    }
                }
                httpMethod.setQueryString(query);
            }
            else if (HttpConstants.METHOD_POST.equalsIgnoreCase(method))
            {
                PostMethod postMethod = new PostMethod(uri.toString());

                String paramName = msg.getStringProperty(HttpConnector.HTTP_POST_BODY_PARAM_PROPERTY, null);

                if (paramName == null)
                {
                    // Call method to manage the parameter array
                    addParameters(uri.getQuery(), postMethod);
                    setupEntityMethod(src, encoding, context, msg, uri, postMethod);
                }
                else
                {
                    postMethod.addParameter(paramName, src.toString());
                }

                httpMethod = postMethod;
            }
            else if (HttpConstants.METHOD_PUT.equalsIgnoreCase(method))
            {
                PutMethod putMethod = new PutMethod(uri.toString());

                setupEntityMethod(src, encoding, context, msg, uri, putMethod);

                httpMethod = putMethod;
            }
            else if (HttpConstants.METHOD_DELETE.equalsIgnoreCase(method))
            {
                httpMethod = new DeleteMethod(uri.toString());
            }
            else if (HttpConstants.METHOD_HEAD.equalsIgnoreCase(method))
            {
                httpMethod = new HeadMethod(uri.toString());
            }
            else if (HttpConstants.METHOD_OPTIONS.equalsIgnoreCase(method))
            {
                httpMethod = new OptionsMethod(uri.toString());
            }
            else if (HttpConstants.METHOD_TRACE.equalsIgnoreCase(method))
            {
                httpMethod = new TraceMethod(uri.toString());
            }
            else
            {
                throw new TransformerException(HttpMessages.unsupportedMethod(method));
            }

            // Allow the user to set HttpMethodParams as an object on the message
            HttpMethodParams params = (HttpMethodParams) msg.removeProperty(HttpConnector.HTTP_PARAMS_PROPERTY);
            if (params != null)
            {
                httpMethod.setParams(params);
            }
            else
            {
                // TODO we should propbably set other propserties here
                String httpVersion = msg.getStringProperty(HttpConnector.HTTP_VERSION_PROPERTY,
                    HttpConstants.HTTP11);
                if (HttpConstants.HTTP10.equals(httpVersion))
                {
                    httpMethod.getParams().setVersion(HttpVersion.HTTP_1_0);
                }
                else
                {
                    httpMethod.getParams().setVersion(HttpVersion.HTTP_1_1);
                }
            }
            setHeaders(httpMethod, context);

            return httpMethod;
        }
        catch (Exception e)
        {
            throw new TransformerException(this, e);
        }
    }

    private void setupEntityMethod(Object src,
                                   String encoding,
                                   UMOEventContext context,
                                   UMOMessage msg,
                                   URI uri,
                                   EntityEnclosingMethod postMethod)
        throws UnsupportedEncodingException, TransformerException
    {
        // Dont set a POST payload if the body is a Null Payload.
        // This way client calls
        // can control if a POST body is posted explicitly
        if (!(context.getMessage().getPayload() instanceof NullPayload))
        {
            // See if we have a MIME type set
            String mimeType = msg.getStringProperty(HttpConstants.HEADER_CONTENT_TYPE, null);

            if (src instanceof String)
            {
                // Ensure that we strip the encoding information from the
                // encoding type
                if (mimeType != null)
                {
                    int parameterIndex = mimeType.indexOf(";");
                    if (parameterIndex > 0)
                    {
                        mimeType = mimeType.substring(0, parameterIndex);
                    }
                }
                if (mimeType == null) mimeType = HttpConstants.DEFAULT_CONTENT_TYPE;
                if (encoding == null) encoding = MuleManager.getConfiguration().getEncoding();
                postMethod.setRequestEntity(new StringRequestEntity(src.toString(), mimeType, encoding));
            }
            else if (src instanceof InputStream)
            {
                // TODO Danger here! We don't know if the content is
                // really text or not
                if (mimeType == null) mimeType = HttpConstants.DEFAULT_CONTENT_TYPE;
                postMethod.setRequestEntity(new InputStreamRequestEntity((InputStream) src, mimeType));
            }
            else
            {
                // TODO Danger here! We don't know if the content is
                // really text or not
                if (mimeType == null) mimeType = HttpConstants.DEFAULT_CONTENT_TYPE;
                byte[] buffer = (byte[]) serializableToByteArray.doTransform(src, encoding);
                postMethod.setRequestEntity(new ByteArrayRequestEntity(buffer, mimeType));
            }
        }
    }

    protected void setHeaders(HttpMethod httpMethod, UMOEventContext context)
    {
        // Standard requestHeaders
        String headerValue;
        String headerName;
        UMOMessage msg = context.getMessage();
        for (Iterator iterator = msg.getPropertyNames().iterator(); iterator.hasNext();)
        {
            headerName = (String) iterator.next();

            headerValue = msg.getStringProperty(headerName, null);
            if (HttpConstants.REQUEST_HEADER_NAMES.get(headerName) == null)
            {
                if (headerName.startsWith(MuleProperties.PROPERTY_PREFIX))
                {
                    headerName = new StringBuffer(30).append("X-").append(headerName).toString();
                }

                httpMethod.addRequestHeader(headerName, headerValue);
            }
        }

        if (context.getMessage().getPayload() instanceof InputStream)
        {
            // must set this for receiver to properly parse attachments
            httpMethod.addRequestHeader(HttpConstants.HEADER_CONTENT_TYPE, "multipart/related");
        }
    }
}
