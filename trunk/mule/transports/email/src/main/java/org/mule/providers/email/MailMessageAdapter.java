/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.email;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.mail.Header;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang.SystemUtils;
import org.mule.config.i18n.Messages;
import org.mule.providers.AbstractMessageAdapter;
import org.mule.umo.MessagingException;
import org.mule.umo.provider.MessageTypeNotSupportedException;
import org.mule.util.StringUtils;

/**
 * <code>MailMessageAdapter</code> is a wrapper for a javax.mail.Message.
 */
public class MailMessageAdapter extends AbstractMessageAdapter
{
    /**
     * Serial version
     */
    private static final long serialVersionUID = -6013198455030918360L;

    public static final String ATTACHMENT_HEADERS_PROPERTY_POSTFIX = "Headers";

    private Part messagePart = null;
    private byte[] contentBuffer;

    public MailMessageAdapter(Object message) throws MessagingException
    {
        setMessage(message);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.providers.UMOMessageAdapter#getPayload()
     */
    public Object getPayload()
    {
        return messagePart;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.providers.UMOMessageAdapter#getPayloadAsBytes()
     */
    public byte[] getPayloadAsBytes() throws Exception
    {
        if (contentBuffer == null)
        {
            String contentType = messagePart.getContentType();

            if (contentType.startsWith("text/"))
            {
                getPayloadAsString();
            }
            else
            {
                InputStream is = messagePart.getInputStream();
                // If the stream is not already buffered, wrap a BufferedInputStream
                // around it.
                if (!(is instanceof BufferedInputStream))
                {
                    is = new BufferedInputStream(is);
                }

                ByteArrayOutputStream baos = new ByteArrayOutputStream(32768);
                IOUtils.copy(is, baos);
                contentBuffer = baos.toByteArray();
            }
        }
        return contentBuffer;
    }

    /**
     * Converts the message implementation into a String representation
     * 
     * @param encoding The encoding to use when transforming the message (if
     *            necessary). The parameter is used when converting from a byte array
     * @return String representation of the message payload
     * @throws Exception Implementation may throw an endpoint specific exception
     */
    public String getPayloadAsString(String encoding) throws Exception
    {
        if (contentBuffer == null)
        {
            String contentType = messagePart.getContentType();

            if (contentType.startsWith("text/"))
            {
                InputStream is = messagePart.getInputStream();
                // If the stream is not already buffered, wrap a BufferedInputStream
                // around it.
                if (!(is instanceof BufferedInputStream))
                {
                    is = new BufferedInputStream(is);
                }

                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuffer buffer = new StringBuffer(32768);

                String line = reader.readLine();
                buffer.append(line).append(SystemUtils.LINE_SEPARATOR);
                while (line != null)
                {
                    line = reader.readLine();
                    buffer.append(line).append(SystemUtils.LINE_SEPARATOR);
                }

                contentBuffer = buffer.toString().getBytes();
            }
            else
            {
                contentBuffer = getPayloadAsBytes();
            }
        }
        return new String(contentBuffer, encoding);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.providers.UMOMessageAdapter#setMessage(java.lang.Object)
     */
    protected void setMessage(Object message) throws MessagingException
    {
        Message msg;
        if (message instanceof Message)
        {
            msg = (Message)message;
        }
        else
        {
            throw new MessageTypeNotSupportedException(message, MailMessageAdapter.class);
        }

        try
        {
            Object content = msg.getContent();

            if (content instanceof Multipart)
            {
                this.messagePart = ((Multipart)content).getBodyPart(0);
                logger.debug("Received Multipart message");

                for (int i = 1; i < ((Multipart)content).getCount(); i++)
                {
                    Part part = ((Multipart)content).getBodyPart(i);
                    String name = part.getFileName();
                    if (name == null)
                    {
                        name = String.valueOf(i - 1);
                    }
                    addAttachment(name, part.getDataHandler());
                    addAttachmentHeaders(name, part);
                }
            }
            else
            {
                messagePart = msg;
            }

            // Set message attrributes as properties
            setProperty(MailProperties.TO_ADDRESSES_PROPERTY,
                MailUtils.mailAddressesToString(msg.getRecipients(Message.RecipientType.TO)));
            setProperty(MailProperties.CC_ADDRESSES_PROPERTY,
                MailUtils.mailAddressesToString(msg.getRecipients(Message.RecipientType.CC)));
            setProperty(MailProperties.BCC_ADDRESSES_PROPERTY,
                MailUtils.mailAddressesToString(msg.getRecipients(Message.RecipientType.BCC)));
            setProperty(MailProperties.REPLY_TO_ADDRESSES_PROPERTY,
                MailUtils.mailAddressesToString(msg.getReplyTo()));
            setProperty(MailProperties.FROM_ADDRESS_PROPERTY, MailUtils.mailAddressesToString(msg.getFrom()));
            setProperty(MailProperties.SUBJECT_PROPERTY, StringUtils.defaultIfEmpty(msg.getSubject(), "(no subject)"));
            setProperty(MailProperties.CONTENT_TYPE_PROPERTY, StringUtils.defaultIfEmpty(msg.getContentType(), "text/plain"));

            Date sentDate = msg.getSentDate();
            if (sentDate == null)
            {
                sentDate = new Date();
            }
            setProperty(MailProperties.SENT_DATE_PROPERTY, sentDate);

            for (Enumeration e = msg.getAllHeaders(); e.hasMoreElements();)
            {
                Header h = (Header)e.nextElement();
                setProperty(h.getName(), h.getValue());
            }

        }
        catch (Exception e)
        {
            throw new MessagingException(new org.mule.config.i18n.Message(Messages.FAILED_TO_CREATE_X,
                "Message Adapter"), e);
        }
    }

    protected void addAttachmentHeaders(String name, Part part) throws javax.mail.MessagingException
    {
        Map headers = new HashMap(4);
        for (Enumeration e = part.getAllHeaders(); e.hasMoreElements();)
        {
            Header h = (Header)e.nextElement();
            headers.put(h.getName(), h.getValue());
        }
        if (headers.size() > 0)
        {
            setProperty(name + ATTACHMENT_HEADERS_PROPERTY_POSTFIX, headers);
        }
    }
}
