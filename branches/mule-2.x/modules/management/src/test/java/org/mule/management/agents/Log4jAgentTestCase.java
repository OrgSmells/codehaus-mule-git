/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.management.agents;

import org.mule.management.AbstractMuleJmxTestCase;

import javax.management.ObjectName;

import org.apache.log4j.jmx.HierarchyDynamicMBean;

public class Log4jAgentTestCase extends AbstractMuleJmxTestCase
{
    public void testRedeploy() throws Exception
    {
        mBeanServer.registerMBean(new HierarchyDynamicMBean(),
                                  ObjectName.getInstance(Log4jAgent.JMX_OBJECT_NAME));

        Log4jAgent agent = new Log4jAgent();
        agent.initialise();
    }
    
    protected void doTearDown() throws Exception
    {
        // This MBean was registered manually so needs to be unregistered manually in tearDown()
        unregisterMBeansByMask(Log4jAgent.JMX_OBJECT_NAME);
        super.doTearDown();
    }
}
