/*
 * $Header$
 * $Revision$
 * $Date$
 * ------------------------------------------------------------------------------------------------------
 *
 * Copyright (c) Cubis Limited. All rights reserved.
 * http://www.cubis.co.uk
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.mule.test.config;

import org.mule.MuleManager;
import org.mule.config.ConfigurationBuilder;
import org.mule.config.builders.MuleXmlConfigurationBuilder;
import org.mule.tck.NamedTestCase;
import org.mule.umo.UMODescriptor;
import org.mule.umo.endpoint.UMOEndpoint;

/**
 * @author <a href="mailto:ross.mason@cubis.co.uk">Ross Mason</a>
 * @version $Revision$
 */
public class PropertyTemplatesTestCase extends NamedTestCase
{
    protected void setUp() throws Exception
    {
        if (MuleManager.isInstanciated()) MuleManager.getInstance().dispose();
        ConfigurationBuilder configBuilder = new MuleXmlConfigurationBuilder();
        configBuilder.configure("test-xml-property-templates.xml");
    }

    protected void tearDown() throws Exception {
        MuleManager.getInstance().dispose();
    }

    public void testProperties() {
        assertEquals("blah", MuleManager.getConfiguration().getModel());
        assertEquals("blah", MuleManager.getInstance().getModel().getName());

        assertNotNull(MuleManager.getInstance().lookupConnector("myTestConnector"));
        UMODescriptor d = MuleManager.getInstance().getModel().getDescriptor("test-from-env-props");
        assertNotNull(d);
        assertEquals(d.getInboundEndpoint().getEndpointURI().toString(), "test://test.1");
        assertEquals(d.getOutboundEndpoint().getEndpointURI().toString(), "test://test.2");
    }

    public void testPropertyWithoutOverrides() {

        UMODescriptor d = MuleManager.getInstance().getModel().getDescriptor("test2");
        assertNotNull(d);
        UMOEndpoint endpoint = d.getInboundRouter().getEndpoint("ep1");
        assertNotNull(endpoint);
        assertNotNull(endpoint.getProperties());
        assertEquals(3, endpoint.getProperties().size());
        assertEquals("value1", endpoint.getProperties().get("prop1"));
        assertEquals("overrideValue2", endpoint.getProperties().get("prop2"));
        assertEquals("value3", endpoint.getProperties().get("prop3"));
    }

    public void testPropertyWithOverrides() {

        UMODescriptor d = MuleManager.getInstance().getModel().getDescriptor("test3");
        assertNotNull(d);
        UMOEndpoint endpoint = d.getInboundRouter().getEndpoint("ep2");
        assertNotNull(endpoint);
        assertNotNull(endpoint.getProperties());
        assertEquals(3, endpoint.getProperties().size());
        assertEquals("value1", endpoint.getProperties().get("prop1"));
        assertEquals("value2", endpoint.getProperties().get("prop2"));
        assertEquals("value3", endpoint.getProperties().get("prop3"));
    }
}
