/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.http;

import org.mule.impl.MuleMessage;
import org.mule.impl.message.ExceptionPayload;
import org.mule.providers.AbstractMessageDispatcher;
import org.mule.providers.http.i18n.HttpMessages;
import org.mule.providers.http.transformers.HttpClientMethodResponseToObject;
import org.mule.providers.http.transformers.ObjectToHttpClientMethodRequest;
import org.mule.umo.UMOEvent;
import org.mule.umo.UMOMessage;
import org.mule.umo.endpoint.UMOImmutableEndpoint;
import org.mule.umo.provider.DispatchException;
import org.mule.umo.provider.OutputHandler;
import org.mule.umo.provider.ReceiveException;
import org.mule.umo.transformer.TransformerException;
import org.mule.umo.transformer.UMOTransformer;
import org.mule.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.EntityEnclosingMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;

/**
 * <code>HttpClientMessageDispatcher</code> dispatches Mule events over HTTP.
 */
public class HttpClientMessageDispatcher extends AbstractMessageDispatcher
{
    /**
     * Range start for http error status codes.
     */
    public static final int ERROR_STATUS_CODE_RANGE_START = 400;
    private final HttpConnector connector;
    private volatile HttpClient client = null;
    private final UMOTransformer receiveTransformer;
    private final UMOTransformer sendTransformer;

    public HttpClientMessageDispatcher(UMOImmutableEndpoint endpoint)
    {
        super(endpoint);
        this.connector = (HttpConnector) endpoint.getConnector();
        this.receiveTransformer = new HttpClientMethodResponseToObject();
        this.sendTransformer = new ObjectToHttpClientMethodRequest();
    }
    
    protected void doConnect() throws Exception
    {
        if (client == null)
        {
            client = connector.doClientConnect();
        }
    }

    protected void doDisconnect() throws Exception
    {
        client = null;
    }

    protected void doDispatch(UMOEvent event) throws Exception
    {
        HttpMethod httpMethod = getMethod(event);
        try
        {
            execute(event, httpMethod);
            
            if (httpMethod.getStatusCode() >= ERROR_STATUS_CODE_RANGE_START)
            {
                logger.error(httpMethod.getResponseBodyAsString());
                throw new DispatchException(event.getMessage(), event.getEndpoint(), new Exception(
                    "Http call returned a status of: " + httpMethod.getStatusCode() + " "
                                    + httpMethod.getStatusText()));
            }
        }
        finally
        {
            httpMethod.releaseConnection();
        }
    }

    protected HttpMethod execute(UMOEvent event, HttpMethod httpMethod)
        throws Exception
    {
        // TODO set connection timeout buffer etc
        try
        {
            URI uri = event.getEndpoint().getEndpointURI().getUri();

            this.processCookies(event);

            // TODO can we use the return code for better reporting?
            client.executeMethod(getHostConfig(uri), httpMethod);

            return httpMethod;
        }
        catch (IOException e)
        {
            // TODO employ dispatcher reconnection strategy at this point
            throw new DispatchException(event.getMessage(), event.getEndpoint(), e);
        }
        catch (Exception e)
        {
            throw new DispatchException(event.getMessage(), event.getEndpoint(), e);
        }
        
    }

    protected void processCookies(UMOEvent event)
    {
        UMOMessage msg = event.getMessage();
        Cookie[] cookies = (Cookie[]) msg.removeProperty(HttpConnector.HTTP_COOKIES_PROPERTY);
        if (cookies != null && cookies.length > 0)
        {
            String policy = (String) msg.removeProperty(HttpConnector.HTTP_COOKIE_SPEC_PROPERTY);
            client.getParams().setCookiePolicy(CookieHelper.getCookiePolicy(policy));
            client.getState().addCookies(cookies);
        }
    }

    protected HttpMethod getMethod(UMOEvent event) throws TransformerException
    {
        UMOMessage msg = event.getMessage();
        setPropertyFromEndpoint(event, msg, HttpConnector.HTTP_CUSTOM_HEADERS_MAP_PROPERTY);
        
        HttpMethod httpMethod;
        Object body = event.transformMessage();

        if (body instanceof HttpMethod)
        {
            httpMethod = (HttpMethod)body;
        }
        else 
        {
            httpMethod = (HttpMethod) sendTransformer.transform(msg);
        }
        
        
        return httpMethod;
    }

    protected void setPropertyFromEndpoint(UMOEvent event, UMOMessage msg, String prop)
    {
        Object o = msg.getProperty(prop, null);
        if (o == null) {
            
            o = event.getEndpoint().getProperty(prop);
            if (o != null) {
                msg.setProperty(prop, o);
            }
        }
    }

    protected HttpMethod createEntityMethod(UMOEvent event, Object body, EntityEnclosingMethod postMethod)
        throws TransformerException
    {
        HttpMethod httpMethod;
        if (body instanceof String)
        {
            ObjectToHttpClientMethodRequest trans = new ObjectToHttpClientMethodRequest();
            httpMethod = (HttpMethod)trans.transform(body.toString());
        }
        else if (body instanceof byte[])
        {
            byte[] buffer = event.transformMessageToBytes();
            postMethod.setRequestEntity(new ByteArrayRequestEntity(buffer, event.getEncoding()));
            httpMethod = postMethod;
        }
        else 
        {
            if (!(body instanceof OutputHandler)) 
            {
                body = event.transformMessage(OutputHandler.class);
            }
            
            OutputHandler outputHandler = (OutputHandler)body;
            postMethod.setRequestEntity(new StreamPayloadRequestEntity(outputHandler, event));
            postMethod.setContentChunked(true);
            httpMethod = postMethod;
        }
        
        return httpMethod;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.umo.provider.UMOConnector#send(org.mule.umo.UMOEvent)
     */
    protected UMOMessage doSend(UMOEvent event) throws Exception
    {        
        HttpMethod httpMethod = getMethod(event);
        connector.setupClientAuthorization(event, httpMethod, client, endpoint);
        
        httpMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new MuleHttpMethodRetryHandler());

        Object body = null;
        boolean releaseConn = false;
        try
        {
            httpMethod = execute(event, httpMethod);

            ExceptionPayload ep = null;
            if (httpMethod.getStatusCode() >= ERROR_STATUS_CODE_RANGE_START)
            {
                ep = new ExceptionPayload(new DispatchException(event.getMessage(), event.getEndpoint(),
                    new Exception("Http call returned a status of: " + httpMethod.getStatusCode() + " "
                                  + httpMethod.getStatusText())));
            }
            
            
            InputStream is = httpMethod.getResponseBodyAsStream();
            if (is == null)
            {
                body = StringUtils.EMPTY;
                releaseConn = true;
            }            
            else
            {
                is = new ReleasingInputStream(is, httpMethod);
                body = is;
            }
            
            Header[] headers = httpMethod.getResponseHeaders();
            HttpMessageAdapter adapter = new HttpMessageAdapter(new Object[]{body, headers});

            String status = String.valueOf(httpMethod.getStatusCode());

            adapter.setProperty(HttpConnector.HTTP_STATUS_PROPERTY, status);
            if (logger.isDebugEnabled())
            {
                logger.debug("Http response is: " + status);
            }
            
            UMOMessage m = new MuleMessage(adapter);
          
            m.setExceptionPayload(ep);
            return m;
        }
        catch (Exception e)
        {
            releaseConn = true;
            if (e instanceof DispatchException)
            {
                throw (DispatchException) e;
            }
            
            throw new DispatchException(event.getMessage(), event.getEndpoint(), e);
        }
        finally
        {
            if (releaseConn) 
            {
                httpMethod.releaseConnection();
            }
        }
    }

    protected HostConfiguration getHostConfig(URI uri) throws URISyntaxException
    {
        Protocol protocol = Protocol.getProtocol(uri.getScheme().toLowerCase());

        String host = uri.getHost();
        int port = uri.getPort();
        HostConfiguration config = new HostConfiguration();
        config.setHost(host, port, protocol);
        if (StringUtils.isNotBlank(connector.getProxyHostname()))
        {
            // add proxy support
            config.setProxy(connector.getProxyHostname(), connector.getProxyPort());
        }
        return config;
    }

    protected void doDispose()
    {
        // template method
    }
}
