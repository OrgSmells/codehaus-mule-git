/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transformers.simple;

import org.mule.impl.MuleMessage;
import org.mule.tck.AbstractMuleTestCase;
import org.mule.umo.UMOEventContext;
import org.mule.umo.UMOMessage;
import org.mule.RegistryContext;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MessagePropertiesTransformerTestCase extends AbstractMuleTestCase
{
    protected String getConfigurationResources()
    {
        return "message-properties-transformer-config.xml";
    }

    public void testOverwriteFlagEnabledByDefault() throws Exception
    {
        MessagePropertiesTransformer t = new MessagePropertiesTransformer();
        Map add = new HashMap();
        add.put("addedProperty", "overwrittenValue");
        t.setAddProperties(add);

        UMOMessage msg = new MuleMessage("message");
        msg.setProperty("addedProperty", "originalValue");
        UMOEventContext ctx = getTestEventContext(msg);
        // context clones message
        msg = ctx.getMessage();
        MuleMessage transformed = (MuleMessage) t.transform(msg, null);
        assertSame(msg, transformed);
        assertEquals(msg.getUniqueId(), transformed.getUniqueId());
        assertEquals(msg.getPayload(), transformed.getPayload());
        // property values will be different
        assertEquals(msg.getPropertyNames(), transformed.getPropertyNames());

        assertEquals("overwrittenValue", transformed.getProperty("addedProperty"));
    }

    public void testOverwriteFalsePreservesOriginal() throws Exception
    {
        MessagePropertiesTransformer t = new MessagePropertiesTransformer();
        Map add = new HashMap();
        add.put("addedProperty", "overwrittenValue");
        t.setAddProperties(add);
        t.setOverwrite(false);

        MuleMessage msg = new MuleMessage("message");
        msg.setProperty("addedProperty", "originalValue");
        UMOEventContext ctx = getTestEventContext(msg);
        MuleMessage transformed = (MuleMessage) t.transform(msg, null);
        assertSame(msg, transformed);
        assertEquals(msg.getUniqueId(), transformed.getUniqueId());
        assertEquals(msg.getPayload(), transformed.getPayload());
        assertEquals(msg.getPropertyNames(), transformed.getPropertyNames());

        assertEquals("originalValue", transformed.getProperty("addedProperty"));
    }

    public void testDelete() throws Exception
    {
        MessagePropertiesTransformer t = new MessagePropertiesTransformer();
        t.setDeleteProperties(Collections.singletonList("badProperty"));

        MuleMessage msg = new MuleMessage("message");
        msg.setProperty("badProperty", "badValue");
        UMOEventContext ctx = getTestEventContext(msg);
        MuleMessage transformed = (MuleMessage) t.transform(msg, null);
        assertSame(msg, transformed);
        assertEquals(msg.getUniqueId(), transformed.getUniqueId());
        assertEquals(msg.getPayload(), transformed.getPayload());
        assertEquals(msg.getPropertyNames(), transformed.getPropertyNames());
        assertFalse(transformed.getPropertyNames().contains("badValue"));
    }

    public void testTransformerConfig() throws Exception
    {
        MessagePropertiesTransformer transformer = (MessagePropertiesTransformer) RegistryContext.getRegistry().lookupTransformer("testTransformer");
        assertNotNull(transformer);
        assertNotNull(transformer.getAddProperties());
        assertNotNull(transformer.getDeleteProperties());
        assertEquals(2, transformer.getAddProperties().size());
        assertEquals(2, transformer.getDeleteProperties().size());
        assertTrue(transformer.isOverwrite());
        assertEquals("text/baz;charset=UTF-16BE", transformer.getAddProperties().get("Content-Type"));
        assertEquals("value", transformer.getAddProperties().get("key"));
        assertEquals("test-property1", transformer.getDeleteProperties().get(0));
        assertEquals("test-property2", transformer.getDeleteProperties().get(1));
    }

}
