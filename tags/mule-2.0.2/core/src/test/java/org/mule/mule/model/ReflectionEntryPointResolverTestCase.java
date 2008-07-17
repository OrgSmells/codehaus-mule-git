/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.mule.model;

import org.mule.RequestContext;
import org.mule.api.model.InvocationResult;
import org.mule.model.resolvers.ReflectionEntryPointResolver;
import org.mule.tck.AbstractMuleTestCase;
import org.mule.tck.testmodels.fruit.Apple;
import org.mule.tck.testmodels.fruit.Banana;
import org.mule.tck.testmodels.fruit.Fruit;
import org.mule.tck.testmodels.fruit.FruitBowl;
import org.mule.tck.testmodels.fruit.FruitLover;
import org.mule.tck.testmodels.fruit.Kiwi;
import org.mule.tck.testmodels.fruit.Orange;
import org.mule.tck.testmodels.fruit.WaterMelon;
import org.mule.transport.NullPayload;

public class ReflectionEntryPointResolverTestCase extends AbstractMuleTestCase
{

    public void testExplicitMethodMatch() throws Exception
    {
        ReflectionEntryPointResolver resolver = new ReflectionEntryPointResolver();
        InvocationResult result = resolver.invoke(new WaterMelon(), getTestEventContext("blah"));
        assertEquals(result.getState(), InvocationResult.STATE_INVOKED_SUCESSFUL);
    }

    public void testExplicitMethodMatchComplexObject() throws Exception
    {
        ReflectionEntryPointResolver resolver = new ReflectionEntryPointResolver();
        InvocationResult result = resolver.invoke(new FruitBowl(), getTestEventContext(new FruitLover("Mmmm")));
        assertEquals(result.getState(), InvocationResult.STATE_INVOKED_SUCESSFUL);
    }

    public void testMethodMatchWithArguments() throws Exception
    {
        ReflectionEntryPointResolver resolver = new ReflectionEntryPointResolver();
        InvocationResult result = resolver.invoke(new FruitBowl(), getTestEventContext(new Object[]{new Apple(), new Banana()}));
        assertEquals(result.getState(), InvocationResult.STATE_INVOKED_SUCESSFUL);
        assertTrue(result.getResult() instanceof Fruit[]);
        //test that the correct methd was called
        assertTrue(((Fruit[]) result.getResult())[0] instanceof Apple);
        assertTrue(((Fruit[]) result.getResult())[1] instanceof Banana);
        assertEquals("addAppleAndBanana", result.getMethodCalled());

        result = resolver.invoke(new FruitBowl(), getTestEventContext(new Object[]{new Banana(), new Apple()}));
        assertEquals(result.getState(), InvocationResult.STATE_INVOKED_SUCESSFUL);
        assertTrue(result.getResult() instanceof Fruit[]);
        assertTrue(((Fruit[]) result.getResult())[0] instanceof Banana);
        assertTrue(((Fruit[]) result.getResult())[1] instanceof Apple);
        assertEquals("addBananaAndApple", result.getMethodCalled());
    }

    public void testExplicitMethodMatchSetArrayFail() throws Exception
    {
        ReflectionEntryPointResolver resolver = new ReflectionEntryPointResolver();
        InvocationResult result = resolver.invoke(new FruitBowl(), getTestEventContext(new Fruit[]{new Apple(), new Orange()}));
        assertEquals("Test should have failed because the arguments were not wrapped properly: ",
                result.getState(), InvocationResult.STATE_INVOKED_FAILED);
    }

    public void testExplicitMethodMatchSetArrayPass() throws Exception
    {
        ReflectionEntryPointResolver resolver = new ReflectionEntryPointResolver();
        InvocationResult result = resolver.invoke(new FruitBowl(), getTestEventContext(new Object[]{new Fruit[]{new Apple(), new Orange()}}));
        assertEquals(result.getState(), InvocationResult.STATE_INVOKED_SUCESSFUL);
    }

    /**
     * Tests entrypoint discovery when there is more than one discoverable method
     * with MuleEventContext parameter.
     */
    public void testFailEntryPointMultiplePayloadMatches() throws Exception
    {
        ReflectionEntryPointResolver resolver = new ReflectionEntryPointResolver();
        RequestContext.setEvent(getTestEvent("Hello"));
        InvocationResult result = resolver.invoke(new MultiplePayloadsTestObject(), RequestContext.getEventContext());
        assertEquals(result.getState(), InvocationResult.STATE_INVOKED_FAILED);
    }

    public void testMatchOnNoArgs() throws Exception
    {
        ReflectionEntryPointResolver resolver = new ReflectionEntryPointResolver();
        //This should fail because the Kiwi.bite() method has a void return type, and by default
        //void methods are ignorred
        InvocationResult result = resolver.invoke(new Kiwi(), getTestEventContext(NullPayload.getInstance()));
        assertEquals(result.getState(), InvocationResult.STATE_INVOKED_FAILED);

        resolver.setAcceptVoidMethods(true);
        result = resolver.invoke(new Kiwi(), getTestEventContext(NullPayload.getInstance()));
        assertEquals(result.getState(), InvocationResult.STATE_INVOKED_SUCESSFUL);
        assertEquals("bite", result.getMethodCalled());
    }
}
