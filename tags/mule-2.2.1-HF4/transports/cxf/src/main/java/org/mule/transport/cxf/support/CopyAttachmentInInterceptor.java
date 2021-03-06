/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.cxf.support;

import static org.mule.api.config.MuleProperties.MULE_EVENT_PROPERTY;

import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.transport.PropertyScope;
import org.mule.transport.cxf.CxfConstants;
import org.mule.transport.http.HttpConstants;

import java.util.Collection;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Attachment;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

public class CopyAttachmentInInterceptor extends AbstractPhaseInterceptor
{
    public CopyAttachmentInInterceptor()
    {
        super(Phase.PRE_INVOKE);
    }

    public void handleMessage(Message message) throws Fault
    {
        MuleEvent event = (MuleEvent) message.get(MULE_EVENT_PROPERTY);
        MuleMessage muleMsg;
        if (event == null) 
        {
            muleMsg = (MuleMessage) message.get(CxfConstants.MULE_MESSAGE);
        } 
        else
        {
            muleMsg = event.getMessage();
        }
        
        Collection<Attachment> atts = message.getAttachments();
        if (atts != null)
        {
            muleMsg.setProperty(CxfConstants.ATTACHMENTS, atts, 
                PropertyScope.OUTBOUND);
            muleMsg.setProperty(HttpConstants.HEADER_CONTENT_TYPE, 
                muleMsg.getProperty(HttpConstants.HEADER_CONTENT_TYPE), 
                PropertyScope.OUTBOUND);
        }
    }

}


