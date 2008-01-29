/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.jms;

import org.mule.api.transport.Connector;
import org.mule.tck.providers.AbstractConnectorTestCase;
import org.mule.transport.jms.JmsConnector;
import org.mule.transport.jms.JmsConstants;

import com.mockobjects.dynamic.C;
import com.mockobjects.dynamic.Mock;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.ExceptionListener;
import javax.jms.TextMessage;

import org.apache.commons.collections.IteratorUtils;

public class JmsConnectorTestCase extends AbstractConnectorTestCase
{

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.tck.providers.AbstractConnectorTestCase#getConnectorName()
     */
    // @Override
    public Connector createConnector() throws Exception
    {
        JmsConnector newConnector = new JmsConnector();
        newConnector.setName("TestConnector");
        newConnector.setSpecification(JmsConstants.JMS_SPECIFICATION_11);

        Mock connectionFactory = new Mock(ConnectionFactory.class);
        Mock connection = new Mock(Connection.class);
        connectionFactory.expectAndReturn("createConnection", connection.proxy());
        connection.expect("setExceptionListener", C.isA(ExceptionListener.class));
        connection.expect("close");
        connection.expect("start");
        connection.expect("stop");
        connection.expect("stop");
        connection.expect("setClientID", "mule.TestConnector");
        newConnector.setConnectionFactory((ConnectionFactory) connectionFactory.proxy());

        return newConnector;
    }

    public String getTestEndpointURI()
    {
        return "jms://test.queue";
    }

    public Object getValidMessage() throws Exception
    {
        return getMessage();
    }

    public static Object getMessage() throws Exception
    {
        Mock message = new Mock(TextMessage.class);

        message.expectAndReturn("getText", "Test JMS Message");
        message.expectAndReturn("getText", "Test JMS Message");

        message.expectAndReturn("getJMSCorrelationID", null);
        message.expectAndReturn("getJMSMessageID", "1234567890");
        message.expectAndReturn("getJMSDeliveryMode", new Integer(1));
        message.expectAndReturn("getJMSDestination", null);
        message.expectAndReturn("getJMSPriority", new Integer(4));
        message.expectAndReturn("getJMSRedelivered", Boolean.FALSE);
        message.expectAndReturn("getJMSReplyTo", null);
        message.expectAndReturn("getJMSExpiration", new Long(0));
        message.expectAndReturn("getJMSTimestamp", new Long(0));
        message.expectAndReturn("getJMSType", null);

        message.expect("toString");

        message.expectAndReturn("getPropertyNames",
            IteratorUtils.asEnumeration(IteratorUtils.emptyIterator()));

        return message.proxy();
    }

}
