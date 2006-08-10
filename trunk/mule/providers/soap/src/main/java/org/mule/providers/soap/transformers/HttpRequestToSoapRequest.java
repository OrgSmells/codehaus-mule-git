/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the BSD style
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.soap.transformers;

import org.mule.config.i18n.Message;
import org.mule.config.i18n.Messages;
import org.mule.providers.soap.SoapConstants;
import org.mule.transformers.AbstractEventAwareTransformer;
import org.mule.umo.UMOEventContext;
import org.mule.umo.UMOException;
import org.mule.umo.transformer.TransformerException;
import org.mule.util.PropertiesUtils;
import org.mule.util.StringMessageUtils;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * A simple transformer for converting an Http GET request into a SOAP request.
 * Usually, you would POST a soap document, but this Transformer can be useful
 * for making simple soap requests
 * 
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */
public class HttpRequestToSoapRequest extends AbstractEventAwareTransformer
{
    /**
     * Serial version
     */
    private static final long serialVersionUID = -4958196124440362142L;

    public static final String SOAP_HEADER = "<?xml version=\"1.0\" encoding=\"{0}\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><soap:Body>";
    public static final String SOAP_FOOTER = "</soap:Body></soap:Envelope>";
    public static final String DEFAULT_NAMESPACE = "http://www.muleumo.org/soap";

    public HttpRequestToSoapRequest()
    {
        registerSourceType(String.class);
        registerSourceType(byte[].class);
    }

    public Object transform(Object src, String encoding, UMOEventContext context)
            throws TransformerException
    {
        String data = src.toString();
        if (src instanceof byte[]) {
            try {
                data = new String((byte[])src, encoding);
            }
            catch (UnsupportedEncodingException e) {
                throw new TransformerException(this, e);
            }
            // Data is already Xml
            if (data.startsWith("<") || data.startsWith("&lt;")) {
                return data;
            }
        }
        String httpMethod = context.getMessage().getStringProperty("http.method", "GET");
        String request = context.getMessage().getStringProperty("http.request", null);

        int i = request.indexOf('?');
        String query = request.substring(i + 1);
        Properties p = PropertiesUtils.getPropertiesFromQueryString(query);

        String method = (String)p.remove(SoapConstants.SOAP_METHOD_PROPERTY);
        if (method == null) {
            throw new TransformerException(new Message(Messages.PROPERTIES_X_NOT_SET,
                    SoapConstants.SOAP_METHOD_PROPERTY), this);
        }

        if (httpMethod.equals("POST")) {

            try {
                p.setProperty(method, context.getMessageAsString());
            }
            catch (UMOException e) {
                throw new TransformerException(this, e);
            }
        }

        StringBuffer result = new StringBuffer(8192);
        String header = StringMessageUtils.getFormattedMessage(SOAP_HEADER, new Object[]{encoding});

        if (p.size() > 0) {
            result.append(header);
            result.append('<').append(method).append(" xmlns=\"");
            result.append(DEFAULT_NAMESPACE).append("\">");
            for (Iterator iterator = p.entrySet().iterator(); iterator.hasNext();) {
                Map.Entry entry = (Map.Entry)iterator.next();
                result.append('<').append(entry.getKey()).append('>');
                result.append(entry.getValue());
                result.append("</").append(entry.getKey()).append('>');
            }
            result.append("</").append(method).append('>');
            result.append(SOAP_FOOTER);
        }
        else {
            throw new TransformerException(new Message(Messages.PROPERTIES_X_NOT_SET,
                    SoapConstants.SOAP_METHOD_PROPERTY), this);
        }

        return result.toString();
    }
}
