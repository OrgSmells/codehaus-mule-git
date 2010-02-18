/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.module.scripting.expression;

import org.mule.DefaultMuleMessage;
import org.mule.tck.AbstractMuleTestCase;
import org.mule.tck.testmodels.fruit.Apple;
import org.mule.tck.testmodels.fruit.Banana;
import org.mule.tck.testmodels.fruit.FruitBowl;

public class GroovyExpressionEvaluatorTestCase extends AbstractMuleTestCase
{

    public void testWithExpressions()
    {
        Apple apple = new Apple();
        apple.wash();
        Banana banana = new Banana();
        banana.bite();
        FruitBowl payload = new FruitBowl(apple, banana);
        DefaultMuleMessage msg = new DefaultMuleMessage(payload);
        GroovyExpressionEvaluator e = new GroovyExpressionEvaluator();
        Object value = e.evaluate("payload.apple.washed", msg);
        assertNotNull(value);
        assertTrue(value instanceof Boolean);
        assertTrue((Boolean) value);

        value = e.evaluate("message.payload.banana.bitten", msg);
        assertNotNull(value);
        assertTrue(value instanceof Boolean);
        assertTrue((Boolean) value);

        value = e.evaluate("bar", msg);
        assertNull(value);
    }

    public void testRegistrySyntax() throws Exception
    {
        Apple apple = new Apple();
        muleContext.getRegistry().registerObject("name.with.dots", apple);
        Object result = muleContext.getExpressionManager().evaluate(
                "#[groovy:registry.lookupObject('name.with.dots')]", null);

        assertNotNull(result);
        assertSame(apple, result);

        // try various map-style access in groovy for simpler syntax
        result = muleContext.getExpressionManager().evaluate(
            "#[groovy:registry.'name.with.dots']", null);
        assertNotNull(result);
        assertSame(apple, result);

        result = muleContext.getExpressionManager().evaluate(
                "#[groovy:registry['name.with.dots']]", null);
        assertNotNull(result);
        assertSame(apple, result);

        result = muleContext.getExpressionManager().evaluate(
                "#[groovy:registry.'name.with.dots'.washed]", null);
        assertNotNull(result);
        assertEquals(false, result);
    }
}
