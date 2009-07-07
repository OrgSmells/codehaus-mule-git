/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.email;

import org.mule.api.MessagingException;
import org.mule.transport.AbstractMessageAdapter;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.mail.Header;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;

/**
 * <code>MailMessageAdapter</code> is a wrapper for a javax.mail.Message that 
 * separates multi-part mail messages, storing all but the first part as attachments 
 * to the underlying {@link AbstractMessageAdapter}.  Alternatively, you can use
 * {@link SimpleMailMessageAdapter}, which stores the message as a single
 * entity.
 */
public class MailMessageAdapter extends SimpleMailMessageAdapter
{

    private static final long serialVersionUID = -6013198455030918360L;
    public static final String ATTACHMENT_HEADERS_PROPERTY_POSTFIX = "Headers";

    public MailMessageAdapter(Object object) throws MessagingException
    {
        super(object);
    }

    /**
     * Store only the first body part directly; add further parts as attachments.
     */
    // @Override
    protected void handleMessage(Message message) throws Exception
    {
        Object content = message.getContent();

        if (content instanceof Multipart)
        {
            TreeMap attachments = new TreeMap();
            MailUtils.getAttachments((Multipart)content, attachments);

            logger.debug("Received Multipart message");
            int i = 0;
            for (Iterator iterator = attachments.entrySet().iterator(); iterator.hasNext();i++)
            {
                Map.Entry entry = (Map.Entry) iterator.next();
                Part part = (Part)entry.getValue();
                String name = entry.getKey().toString();
                if(i==0)
                {
                    setMessage(part);
                }
                else
                {
                    addAttachment(name, part.getDataHandler());
                    addAttachmentHeaders(name, part);
                }

            }
        }
        else
        {
            setMessage(message);
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
