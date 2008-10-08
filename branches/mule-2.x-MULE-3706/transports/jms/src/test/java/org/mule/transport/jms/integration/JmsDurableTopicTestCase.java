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

import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;

public class JmsDurableTopicTestCase extends AbstractJmsFunctionalTestCase
{
    public static final String TOPIC_QUEUE_NAME = "durable.broadcast";
    private String clientId;

    protected String getConfigResources()
    {
        return "providers/activemq/jms-durable-topic.xml";
    }

    public void testProviderDurableSubscriber() throws Exception
    {
        setClientId("Client1");
        receive(scenarioNotReceive);
        setClientId("Client2");
        receive(scenarioNotReceive);

        setClientId("Sender");
        send(scenarioNonTx);

        setClientId("Client1");
        receive(scenarioNonTx);
        receive(scenarioNotReceive);
        setClientId("Client2");
        receive(scenarioNonTx);
        receive(scenarioNotReceive);
    }

    Scenario scenarioNonTx = new NonTransactedScenario()
    {
        public String getOutputQueue()
        {
            return TOPIC_QUEUE_NAME;
        }
    };

    Scenario scenarioNotReceive = new ScenarioNotReceive()
    {
        public String getOutputQueue()
        {
            return TOPIC_QUEUE_NAME;
        }
    };

    public Message receive(Scenario scenario) throws Exception
    {
        TopicConnection connection = null;
        try
        {
            TopicConnectionFactory factory = new ActiveMQConnectionFactory(scenario.getBrokerUrl());
            connection = factory.createTopicConnection();
            connection.setClientID(getClientId());
            connection.start();
            TopicSession session = null;
            try
            {
                session = connection.createTopicSession(scenario.isTransacted(), scenario.getAcknowledge());
                ActiveMQTopic destination = new ActiveMQTopic(scenario.getOutputQueue());
                MessageConsumer consumer = null;
                try
                {
                    consumer = session.createDurableSubscriber(destination, getClientId());
                    return scenario.receive(session, consumer);
                }
                catch (Exception e)
                {
                    throw e;
                }
                finally
                {
                    if (consumer != null)
                    {
                        consumer.close();
                    }
                }
            }
            catch (Exception e)
            {
                throw e;
            }
            finally
            {
                if (session != null)
                {
                    session.close();
                }
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (connection != null)
            {
                connection.close();
            }
        }
    }

    public String getClientId()
    {
        return clientId;
    }

    public void setClientId(String clientId)
    {
        this.clientId = clientId;
    }
}
