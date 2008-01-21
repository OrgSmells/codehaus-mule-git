/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.test.components;

import org.mule.tck.FunctionalTestCase;
import org.mule.tck.testmodels.fruit.Orange;
import org.mule.umo.UMOComponent;

/**
 * Tests the Spring Beans config which could be generated by the 
 * mule-1-to-spring-migration.xsl transformation for Mule 1.x configs.  
 */
public class ServiceDescriptorLegacyTestCase extends FunctionalTestCase
{
    protected String getConfigResources()
    {
        return "org/mule/test/components/service-factory-legacy-config.xml";
    }

    public void testClassName() throws Exception
    {
        UMOComponent c = muleContext.getRegistry().lookupComponent("orange1");
        Object service =  c.getServiceFactory().getOrCreate();
        assertTrue("Service should be an Orange", service instanceof Orange);
        // Default values
        assertEquals(new Integer(10), ((Orange) service).getSegments());
    }

    public void testContainerReference() throws Exception
    {
        UMOComponent c = muleContext.getRegistry().lookupComponent("orange2");
        Object service =  c.getServiceFactory().getOrCreate();
        assertTrue("Service should be an Orange", service instanceof Orange);
        // Default values
        assertEquals(new Integer(10), ((Orange) service).getSegments());
    }
}
