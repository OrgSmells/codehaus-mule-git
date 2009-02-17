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


/**
 * Testing durable topic with external subscriber
 */
public abstract class JmsDurableTopicSingleTxTestCase extends JmsDurableTopicTestCase
{

    public JmsDurableTopicSingleTxTestCase(JmsVendorConfiguration config)
    {
        super(config);
    }

    protected String getConfigResources()
    {
        return "integration/jms-durable-topic-single-tx.xml";
    }

    /**
     * @throws Exception
     */
    public void testProviderDurableSubscriber() throws Exception
    {
        setClientId("Client1");
        receive(scenarioNotReceive);
        setClientId("Client2");
        receive(scenarioNotReceive);

        setClientId("Sender");
        send(scenarioCommit);

        setClientId("Client1");
        receive(scenarioCommit);
        receive(scenarioNotReceive);
        setClientId("Client2");
        receive(scenarioRollback);
        receive(scenarioCommit);
        receive(scenarioNotReceive);

    }

    Scenario scenarioCommit = new ScenarioCommit()
    {
        public String getOutputDestinationName()
        {
            return getJmsConfig().getBroadcastDestinationName();
        }
    };

    Scenario scenarioRollback = new ScenarioRollback()
    {
        public String getOutputDestinationName()
        {
            return getJmsConfig().getBroadcastDestinationName();
        }
    };

    Scenario scenarioNotReceive = new ScenarioNotReceive()
    {
        public String getOutputDestinationName()
        {
            return getJmsConfig().getBroadcastDestinationName();
        }
    };
}
