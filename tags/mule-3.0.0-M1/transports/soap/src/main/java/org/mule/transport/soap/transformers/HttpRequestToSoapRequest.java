/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.soap.transformers;

import org.mule.api.MuleMessage;
import org.mule.api.config.MuleProperties;
import org.mule.api.transformer.TransformerException;
import org.mule.config.i18n.CoreMessages;
import org.mule.transformer.AbstractMessageAwareTransformer;
import org.mule.util.IOUtils;
import org.mule.util.PropertiesUtils;
import org.mule.util.StringMessageUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * A simple transformer for converting an Http GET request into a SOAP request.
 * Usually, you would POST a SOAP document, but this Transformer can be useful for
 * making simple SOAP requests
 */
public class HttpRequestToSoapRequest extends AbstractMessageAwareTransformer
{
    public static final String SOAP_HEADER = "<?xml version=\"1.0\" encoding=\"{0}\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><soap:Body>";
    public static final String SOAP_FOOTER = "</soap:Body></soap:Envelope>";
    public static final String DEFAULT_NAMESPACE = "http://www.muleumo.org/soap";

    public HttpRequestToSoapRequest()
    {
        registerSourceType(String.class);
        registerSourceType(InputStream.class);
        registerSourceType(byte[].class);
        setReturnClass(String.class);
    }

    public Object transform(MuleMessage message, String outputEncoding) throws TransformerException
    {
        Object src = message.getPayload();

        String data = src.toString();
        if (src instanceof InputStream)
        {
            InputStream is = (InputStream)src;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            try
            {
                try
                {
                    IOUtils.copy(is, bos);
                }
                finally
                {
                    is.close();
                }
            }
            catch (IOException e)
            {
                throw new TransformerException(this, e);
            }
            
            src = bos.toByteArray();
        }
        
        if (src instanceof byte[])
        {
            try
            {
                data = new String((byte[])src, outputEncoding);
            }
            catch (UnsupportedEncodingException e)
            {
                throw new TransformerException(this, e);
            }
            // Data is already Xml
            if (data.startsWith("<") || data.startsWith("&lt;"))
            {
                return data;
            }
        }
        
        String httpMethod = message.getStringProperty("http.method", "GET");
        String request = message.getStringProperty("http.request", null);

        int i = request.indexOf('?');
        String query = request.substring(i + 1);
        Properties p = PropertiesUtils.getPropertiesFromQueryString(query);

        String method = (String)p.remove(MuleProperties.MULE_METHOD_PROPERTY);
        if (method == null)
        {
            throw new TransformerException(
                CoreMessages.propertiesNotSet(MuleProperties.MULE_METHOD_PROPERTY), this);
        }

        if (httpMethod.equals("POST"))
        {
            p.setProperty(method, data);
        }

        StringBuffer result = new StringBuffer(8192);
        String header = StringMessageUtils.getFormattedMessage(SOAP_HEADER, new Object[]{outputEncoding});

        result.append(header);
        result.append('<').append(method).append(" xmlns=\"");
        result.append(DEFAULT_NAMESPACE).append("\">");
        for (Iterator iterator = p.entrySet().iterator(); iterator.hasNext();)
        {
            Map.Entry entry = (Map.Entry)iterator.next();
            result.append('<').append(entry.getKey()).append('>');
            result.append(entry.getValue());
            result.append("</").append(entry.getKey()).append('>');
        }
        result.append("</").append(method).append('>');
        result.append(SOAP_FOOTER);

        return result.toString();
    }
}
