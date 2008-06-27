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

import org.mule.api.MessagingException;
import org.mule.api.ThreadSafeAccess;
import org.mule.api.config.MuleProperties;
import org.mule.api.transport.MessageTypeNotSupportedException;
import org.mule.api.transport.UniqueIdNotSupportedException;
import org.mule.config.i18n.CoreMessages;
import org.mule.transport.AbstractMessageAdapter;
import org.mule.transport.http.HttpConstants;
import org.mule.util.UUID;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <code>HttpRequestMessageAdapter</code> is a Mule message adapter for
 * javax.servletHttpServletRequest objects.
 */

public class HttpRequestMessageAdapter extends AbstractMessageAdapter
{
    /**
     * Serial version
     */
    private static final long serialVersionUID = -4238448252206941125L;

    private HttpServletRequest request;

    public HttpRequestMessageAdapter(Object message) throws MessagingException
    {
        if (message instanceof HttpServletRequest)
        {
            setPayload((HttpServletRequest)message);
            final Map parameterMap = request.getParameterMap();
            if (parameterMap != null && parameterMap.size() > 0)
            {
                for (Iterator iterator = parameterMap.entrySet().iterator(); iterator.hasNext();)
                {
                    Map.Entry entry = (Map.Entry)iterator.next();
                    String key = (String)entry.getKey();
                    Object value = entry.getValue();
                    if (value != null)
                    {
                        if (value.getClass().isArray() && ((Object[])value).length == 1)
                        {
                            setProperty(key, ((Object[])value)[0]);
                        }
                        else
                        {
                            setProperty(key, value);
                        }
                    }
                }
            }
            String key;
            for (Enumeration e = request.getAttributeNames(); e.hasMoreElements();)
            {
                key = (String)e.nextElement();
                properties.setProperty(key, request.getAttribute(key));
            }
            String realKey;
            for (Enumeration e = request.getHeaderNames(); e.hasMoreElements();)
            {
                key = (String)e.nextElement();
                realKey = key;
                if (key.startsWith(HttpConstants.X_PROPERTY_PREFIX))
                {
                    realKey = key.substring(2);
                }
                setProperty(realKey, request.getHeader(key));
            }
        }
        else
        {
            throw new MessageTypeNotSupportedException(message, getClass());
        }
    }

    protected HttpRequestMessageAdapter(HttpRequestMessageAdapter template)
    {
        super(template);
        request = template.request;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.api.providers.UMOMessageAdapter#getMessage()
     */
    public Object getPayload()
    {
        return request;
    }

    public boolean isBinary()
    {
        return !request.getContentType().startsWith("text");
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.api.providers.UMOMessageAdapter#setMessage(java.lang.Object)
     */
    private void setPayload(HttpServletRequest message) throws MessagingException
    {
//        try
//        {

            request = message;
            // String httpRequest = null;
            // httpRequest = request.getScheme() + "://" + request.getServerName() +
            // ":" + request.getServerPort() + request.getServletPath();
            // httpRequest += request.getPathInfo();
            // if(StringUtils.isNotBlank(request.getQueryString())) {
            // httpRequest += "?" + request.getQueryString();
            // }
            // setProperty(HttpConnector.HTTP_REQUEST_PROPERTY, httpRequest);
            // this.message = httpRequest;

            // Check if a payload parameter has been set, if so use it
            // otherwise we'll use the request payload
//            String payloadParam = (String)request
//                .getAttribute(AbstractReceiverServlet.PAYLOAD_PARAMETER_NAME);
//
//            if (payloadParam == null)
//            {
//                payloadParam = AbstractReceiverServlet.DEFAULT_PAYLOAD_PARAMETER_NAME;
//            }
//
//            String payload = request.getParameter(payloadParam);
//            if (payload == null)
//            {
//                if (isText(request.getContentType()))
//                {
//                    BufferedReader reader = request.getReader();
//                    StringBuffer buffer = new StringBuffer(8192);
//                    String line = reader.readLine();
//                    while (line != null)
//                    {
//                        buffer.append(line);
//                        line = reader.readLine();
//                        if (line != null) buffer.append(SystemUtils.LINE_SEPARATOR);
//                    }
//                    this.message = buffer.toString();
//                }
//                else
//                {
//                    ByteArrayOutputStream baos = new ByteArrayOutputStream(8192);
//                    IOUtils.copy(request.getInputStream(), baos);
//                    this.message = baos.toByteArray();
//                }
//            }
//            else
//            {
//                this.message = payload;
//            }
//        }
//        catch (IOException e)
//        {
//            throw new MessagingException(
//                ServletMessages.failedToReadPayload(request.getRequestURL().toString()), e);
//        }
    }

    public HttpServletRequest getRequest()
    {
        return request;
    }

    public String getUniqueId()
    {
        HttpSession session = null;

        try
        {
            // We wrap this call as on some App Servers (Websfear) it can cause an
            // NPE
            session = getRequest().getSession();
        }
        catch (Exception e)
        {
            return UUID.getUUID();
            //throw new UniqueIdNotSupportedException(this, CoreMessages.objectIsNull("Http session"));
        }
        if (session == null)
        {
            throw new UniqueIdNotSupportedException(this, CoreMessages.objectIsNull("Http session"));
        }
        return session.getId();
    }

    /**
     * Sets a replyTo address for this message. This is useful in an asynchronous
     * environment where the caller doesn't wait for a response and the response
     * needs to be routed somewhere for further processing. The value of this field
     * can be any valid endpointUri url.
     * 
     * @param replyTo the endpointUri url to reply to
     */
    public void setReplyTo(Object replyTo)
    {
        if (replyTo != null && replyTo.toString().startsWith("http"))
        {
            setProperty(HttpConstants.HEADER_LOCATION, replyTo);
        }
        setProperty(MuleProperties.MULE_CORRELATION_ID_PROPERTY, replyTo);
    }

    /**
     * Sets a replyTo address for this message. This is useful in an asynchronous
     * environment where the caller doesn't wait for a response and the response
     * needs to be routed somewhere for further processing. The value of this field
     * can be any valid endpointUri url.
     * 
     * @return the endpointUri url to reply to or null if one has not been set
     */
    public Object getReplyTo()
    {
        String replyto = (String)getProperty(MuleProperties.MULE_REPLY_TO_PROPERTY);
        if (replyto == null)
        {
            replyto = (String)getProperty(HttpConstants.HEADER_LOCATION);
        }
        return replyto;
    }

    public ThreadSafeAccess newThreadCopy()
    {
        return new HttpRequestMessageAdapter(this);
    }

}
