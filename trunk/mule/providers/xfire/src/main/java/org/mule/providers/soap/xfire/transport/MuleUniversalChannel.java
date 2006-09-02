/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the BSD style
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.soap.xfire.transport;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.activation.DataHandler;
import javax.mail.MessagingException;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.xfire.MessageContext;
import org.codehaus.xfire.XFireException;
import org.codehaus.xfire.XFireRuntimeException;
import org.codehaus.xfire.attachments.Attachments;
import org.codehaus.xfire.attachments.ByteDataSource;
import org.codehaus.xfire.attachments.JavaMailAttachments;
import org.codehaus.xfire.attachments.SimpleAttachment;
import org.codehaus.xfire.exchange.AbstractMessage;
import org.codehaus.xfire.exchange.InMessage;
import org.codehaus.xfire.exchange.OutMessage;
import org.codehaus.xfire.soap.Soap12;
import org.codehaus.xfire.soap.SoapConstants;
import org.codehaus.xfire.soap.SoapVersion;
import org.codehaus.xfire.transport.AbstractChannel;
import org.codehaus.xfire.transport.Channel;
import org.codehaus.xfire.util.STAXUtils;
import org.mule.config.MuleProperties;
import org.mule.extras.client.MuleClient;
import org.mule.impl.RequestContext;
import org.mule.providers.http.HttpConnector;
import org.mule.providers.http.HttpConstants;
import org.mule.providers.streaming.StreamMessageAdapter;
import org.mule.umo.UMOEvent;
import org.mule.umo.UMOMessage;
import org.mule.umo.provider.OutputHandler;
import org.mule.umo.provider.UMOStreamMessageAdapter;

/**
 * TODO explain what this does and how, at least briefly :)
 */
public class MuleUniversalChannel extends AbstractChannel
{
    /**
     * logger used by this class
     */
    protected transient Log logger = LogFactory.getLog(getClass());

    public MuleUniversalChannel(String uri, MuleUniversalTransport transport)
    {
        setTransport(transport);
        setUri(uri);
    }

    public void open()
    {
        // template method
    }

    public void send(MessageContext context, OutMessage message) throws XFireException
    {
        if (message.getUri().equals(Channel.BACKCHANNEL_URI))
        {
            final OutputStream out = (OutputStream)context.getProperty(Channel.BACKCHANNEL_URI);
            if (out != null)
            {
                final XMLStreamWriter writer = STAXUtils.createXMLStreamWriter(out, message.getEncoding(),
                    context);

                message.getSerializer().writeMessage(message, writer, context);
            }
            else
            {
                throw new XFireRuntimeException("No backchannel exists for message");
            }

            try
            {
                Attachments atts = message.getAttachments();
                if (atts != null && atts.size() > 0)
                {
                    writeAttachmentBody(context, message);
                    // TODO response.setContentType(atts.getContentType());

                    atts.write(out);
                }
                else
                {
                    // TODO response.setContentType(getSoapMimeType(message));

                    writeWithoutAttachments(context, message, out);
                }
            }
            catch (IOException e)
            {
                throw new XFireException("Couldn't send message.", e);
            }
        }
        else
        {
            try
            {
                sendViaClient(context, message);
            }
            catch (Exception e)
            {
                throw new XFireException("Failed to Send via MuleUniversalChnnel: " + e.getMessage(), e);
            }
        }
    }

    void writeWithoutAttachments(MessageContext context, OutMessage message, OutputStream out)
        throws XFireException
    {
        XMLStreamWriter writer = STAXUtils.createXMLStreamWriter(out, message.getEncoding(), context);

        message.getSerializer().writeMessage(message, writer, context);

        try
        {
            writer.flush();
        }
        catch (XMLStreamException e)
        {
            logger.error(e);
            throw new XFireException("Couldn't send message.", e);
        }
    }

    void writeAttachmentBody(MessageContext context, OutMessage message) throws XFireException
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        writeWithoutAttachments(context, message, bos);

        Attachments atts = message.getAttachments();

        ByteDataSource ds = new ByteDataSource(bos.toByteArray());
        ds.setContentType(getSoapMimeType(message));
        DataHandler dh = new DataHandler(ds);

        SimpleAttachment att = new SimpleAttachment("soap-message.xml", dh);

        atts.setSoapMessage(att);
    }

    String getMimeType(AbstractMessage msg)
    {
        Attachments atts = msg.getAttachments();

        if (atts != null && atts.size() > 0)
        {
            return atts.getContentType();
        }
        else
        {
            return getSoapMimeType(msg);
        }
    }

    static String getSoapMimeType(AbstractMessage msg)
    {
        SoapVersion soap = msg.getSoapVersion();
        String encoding = msg.getEncoding();
        StringBuffer soapMimeType = new StringBuffer(40);

        if (soap instanceof Soap12)
        {
            soapMimeType.append("application/soap+xml; charset=");
        }
        else
        {
            // SOAP 1.1 & default
            soapMimeType.append("text/xml; charset=");
        }

        return soapMimeType.append(encoding).toString();
    }

    private void sendViaClient(final MessageContext context, final OutMessage message) throws Exception
    {
        MuleClient client = new MuleClient();
        OutputHandler handler = new OutputHandler()
        {
            public void write(UMOEvent event, OutputStream out) throws IOException
            {
                try
                {
                    Attachments atts = message.getAttachments();
                    if (atts != null && atts.size() > 0)
                    {
                        atts.write(out);
                    }
                    else
                    {
                        XMLStreamWriter writer = STAXUtils.createXMLStreamWriter(out, message.getEncoding(),
                            context);
                        message.getSerializer().writeMessage(message, writer, context);
                        try
                        {
                            writer.flush();
                        }
                        catch (XMLStreamException e)
                        {
                            logger.error(e);
                            throw new XFireException("Couldn't send message.", e);
                        }
                    }
                }
                catch (XFireException e)
                {
                    logger.error("Couldn't send message.", e);
                    throw new IOException(e.getMessage());
                }
            }

            public Map getHeaders(UMOEvent event)
            {
                Map headers = new HashMap();
                headers.put(HttpConstants.HEADER_CONTENT_TYPE, getSoapMimeType(message));
                headers.put(SoapConstants.SOAP_ACTION, message.getProperty(SoapConstants.SOAP_ACTION));
                UMOMessage msg = event.getMessage();
                for (Iterator iterator = msg.getPropertyNames().iterator(); iterator.hasNext();)
                {
                    String headerName = (String)iterator.next();
                    Object headerValue = msg.getStringProperty(headerName, null);

                    // let us filter only MULE properties except MULE_USER,
                    // Content-Type and Content-Lenght; all other properties are
                    // allowed through including custom headers
                    if ((!headerName.startsWith(MuleProperties.PROPERTY_PREFIX) || (MuleProperties.MULE_USER_PROPERTY.compareTo(headerName) == 0))
                        && (!HttpConstants.HEADER_CONTENT_TYPE.equalsIgnoreCase(headerName))
                        && (!HttpConstants.HEADER_CONTENT_LENGTH.equalsIgnoreCase(headerName)))
                    {
                        headers.put(headerName, headerValue);
                    }
                }

                return headers;
            }
        };

        // We can create a generic StreamMessageAdapter here as the underlying
        // transport will create one specific to the transport
        UMOStreamMessageAdapter sp = new StreamMessageAdapter(handler);
        sp.setProperty(HttpConnector.HTTP_METHOD_PROPERTY, HttpConstants.METHOD_POST);

        // set all properties on the message adapter
        UMOMessage msg = RequestContext.getEvent().getMessage();
        for (Iterator i = msg.getPropertyNames().iterator(); i.hasNext();)
        {
            String propertyName = (String)i.next();
            sp.setProperty(propertyName, msg.getProperty(propertyName));
        }

        UMOStreamMessageAdapter result = null;

        try
        {
            result = client.sendStream(getUri(), sp);
            if (result != null)
            {
                InMessage inMessage;
                String contentType = sp.getStringProperty(HttpConstants.HEADER_CONTENT_TYPE, "text/xml");
                InputStream in = result.getInputStream();
                if (contentType.toLowerCase().indexOf("multipart/related") != -1)
                {
                    try
                    {
                        Attachments atts = new JavaMailAttachments(in, contentType);
                        InputStream msgIs = atts.getSoapMessage().getDataHandler().getInputStream();
                        inMessage = new InMessage(STAXUtils.createXMLStreamReader(msgIs,
                            message.getEncoding(), context), getUri());
                        inMessage.setAttachments(atts);
                    }
                    catch (MessagingException e)
                    {
                        throw new IOException(e.getMessage());
                    }
                }
                else
                {
                    inMessage = new InMessage(STAXUtils.createXMLStreamReader(in, message.getEncoding(),
                        context), getUri());
                }
                getEndpoint().onReceive(context, inMessage);
            }
        }
        finally
        {
            sp.release();
            if (result != null)
            {
                result.release();
            }
        }
    }

    public void close()
    {
        // template method
    }

    public boolean isAsync()
    {
        return false;
    }

}
