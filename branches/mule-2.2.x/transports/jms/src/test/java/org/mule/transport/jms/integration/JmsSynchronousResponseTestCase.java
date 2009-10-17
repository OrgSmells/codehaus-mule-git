/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.jms.integration;

import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.transport.NullPayload;
import org.mule.util.StringUtils;


/**
 * @see EE-1688/MULE-3059
 */
public class JmsSynchronousResponseTestCase extends AbstractJmsFunctionalTestCase
{
    @Override
    protected String getConfigResources()
    {
        return "integration/jms-synchronous-response.xml";
    }

    public void testResponseWithoutReplyTo() throws Exception
    {
        MuleClient client = new MuleClient();
        
        MuleMessage response = client.send("out1", "TEST_MESSAGE", null);
        assertNotNull(response);
        assertTrue("Response is not a JMS Message", response.getPayload() instanceof javax.jms.Message);
        String messageId = ((javax.jms.Message) response.getPayload()).getJMSMessageID();
        assertTrue("JMSMessageID is missing", StringUtils.isNotBlank(messageId));
    }

    public void testResponseWithoutReplyToEndointProperties() throws Exception
    {
        MuleClient client = new MuleClient();
        
        MuleMessage response = client.send("out2", "TEST_MESSAGE", null);
        assertNotNull(response);
        assertTrue("Response is not a JMS Message", response.getPayload() instanceof javax.jms.Message);
        String messageId = ((javax.jms.Message) response.getPayload()).getJMSMessageID();
        assertTrue("JMSMessageID is missing", StringUtils.isNotBlank(messageId));
    }

    public void testResponseWithReplyTo() throws Exception
    {
        MuleClient client = new MuleClient();
        
        MuleMessage response = client.send("out3", "TEST_MESSAGE", null);
        assertNotNull(response);
        assertTrue("Response should be NullPayload", response.getPayload() instanceof NullPayload);
    }
}
