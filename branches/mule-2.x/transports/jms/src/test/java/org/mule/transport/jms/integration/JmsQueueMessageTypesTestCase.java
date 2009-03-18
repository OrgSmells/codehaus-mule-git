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

import org.mule.tck.testmodels.fruit.Apple;

import java.util.ArrayList;

import org.junit.Test;

/**
 * Message is sent to and received from simple queue.
 */
public class JmsQueueMessageTypesTestCase extends AbstractJmsFunctionalTestCase
{
    protected String getConfigResources()
    {
        return "integration/jms-queue-message-types.xml";
    }

    @Test
    public void testTextMessage() throws Exception
    {
        dispatchMessage("TEST MESSAGE");
        receiveMessage("TEST MESSAGE");
        receive(scenarioNotReceive);
    }

    @Test
    public void testNumberMessage() throws Exception
    {
        dispatchMessage(25.75);
        receiveMessage(25.75);
        receive(scenarioNotReceive);
    }

    @Test
    public void testObjectMessage() throws Exception
    {
        dispatchMessage(new ArrayList());
        receiveMessage(new ArrayList());
        receive(scenarioNotReceive);
    }

    @Test
    public void testCustomObjectMessage() throws Exception
    {
        dispatchMessage(new Apple());
        receiveMessage(new Apple());
        receive(scenarioNotReceive);
    }

}
