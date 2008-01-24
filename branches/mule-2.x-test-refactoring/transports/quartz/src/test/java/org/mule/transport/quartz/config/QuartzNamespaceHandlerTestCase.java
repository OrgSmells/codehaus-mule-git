/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.transport.quartz.config;

import org.mule.tck.FunctionalTestCase;
import org.mule.transport.quartz.QuartzConnector;

import org.quartz.impl.StdScheduler;


/**
 * Tests the "quartz" namespace.
 */
public class QuartzNamespaceHandlerTestCase extends FunctionalTestCase
{
    protected String getConfigResources()
    {
        return "quartz-namespace-config.xml";
    }

    public void testDefaultConfig() throws Exception
    {
        QuartzConnector c = (QuartzConnector)muleContext.getRegistry().lookupConnector("quartzConnectorDefaults");
        assertNotNull(c);
        
        assertNotNull(c.getQuartzScheduler());
        assertEquals(StdScheduler.class, c.getQuartzScheduler().getClass());
        StdScheduler scheduler = (StdScheduler) c.getQuartzScheduler();
        assertEquals("DefaultQuartzScheduler", scheduler.getSchedulerName());
        
        assertTrue(c.isConnected());
        assertTrue(c.isStarted());
    }
    
    public void testInjectedSchedulerBean() throws Exception
    {
        QuartzConnector c = (QuartzConnector)muleContext.getRegistry().lookupConnector("quartzConnector1");
        assertNotNull(c);
        
        assertNotNull(c.getQuartzScheduler());
        assertEquals(StdScheduler.class, c.getQuartzScheduler().getClass());
        StdScheduler scheduler = (StdScheduler) c.getQuartzScheduler();
        assertEquals("MuleScheduler1", scheduler.getSchedulerName());
        
        assertTrue(c.isConnected());
        assertTrue(c.isStarted());
    }
    
    public void testFactoryProperties() throws Exception
    {
        QuartzConnector c = (QuartzConnector)muleContext.getRegistry().lookupConnector("quartzConnector2");
        assertNotNull(c);
        
        assertNotNull(c.getQuartzScheduler());
        assertEquals(StdScheduler.class, c.getQuartzScheduler().getClass());
        StdScheduler scheduler = (StdScheduler) c.getQuartzScheduler();
        assertEquals("MuleScheduler2", scheduler.getSchedulerName());
        
        assertTrue(c.isConnected());
        assertTrue(c.isStarted());
    }
}
