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

import org.mule.api.MuleEventContext;
import org.mule.api.model.InvocationResult;
import org.mule.model.resolvers.MethodHeaderPropertyEntryPointResolver;
import org.mule.tck.AbstractMuleTestCase;
import org.mule.tck.testmodels.fruit.Apple;
import org.mule.transport.NullPayload;

public class MethodHeaderEntryPointResolverTestCase extends AbstractMuleTestCase
{
    public void testMethodSetPass() throws Exception
    {
        MethodHeaderPropertyEntryPointResolver resolver = new MethodHeaderPropertyEntryPointResolver();
        MuleEventContext ctx = getTestEventContext("blah");
        ctx.getMessage().setProperty("method", "someBusinessMethod");
        InvocationResult result = resolver.invoke(new MultiplePayloadsTestObject(), ctx);
        assertEquals(result.getState(), InvocationResult.STATE_INVOKED_SUCESSFUL);
    }

    public void testMethodSetWithNoArgsPass() throws Exception
    {
        MethodHeaderPropertyEntryPointResolver resolver = new MethodHeaderPropertyEntryPointResolver();
        MuleEventContext ctx = getTestEventContext(NullPayload.getInstance());
        ctx.getMessage().setProperty("method", "wash");
        InvocationResult result = resolver.invoke(new Apple(), ctx);
        assertEquals(result.getState(), InvocationResult.STATE_INVOKED_SUCESSFUL);
        assertEquals("wash", result.getMethodCalled());
    }

    public void testCustomMethodProperty() throws Exception
    {
        MethodHeaderPropertyEntryPointResolver resolver = new MethodHeaderPropertyEntryPointResolver();
        resolver.setMethodProperty("serviceMethod");
        MuleEventContext ctx = getTestEventContext("blah");
        ctx.getMessage().setProperty("serviceMethod", "someBusinessMethod");
        InvocationResult result = resolver.invoke(new MultiplePayloadsTestObject(), ctx);
        assertEquals(result.getState(), InvocationResult.STATE_INVOKED_SUCESSFUL);
    }

    public void testCustomMethodPropertyFail() throws Exception
    {
        MethodHeaderPropertyEntryPointResolver resolver = new MethodHeaderPropertyEntryPointResolver();
        resolver.setMethodProperty("serviceMethod");
        MuleEventContext ctx = getTestEventContext("blah");
        ctx.getMessage().setProperty("serviceMethod", "noMethod");
        InvocationResult result = resolver.invoke(new MultiplePayloadsTestObject(), ctx);
        assertEquals(result.getState(), InvocationResult.STATE_INVOKED_FAILED);
    }

    public void testMethodPropertyFail() throws Exception
    {
        MethodHeaderPropertyEntryPointResolver resolver = new MethodHeaderPropertyEntryPointResolver();
        resolver.setMethodProperty("serviceMethod");
        MuleEventContext ctx = getTestEventContext("blah");
        ctx.getMessage().setProperty("myMethod", "someBusinessMethod");
        InvocationResult result = resolver.invoke(new MultiplePayloadsTestObject(), ctx);
        assertEquals(result.getState(), InvocationResult.STATE_INVOKED_FAILED);
    }

    public void testMethodPropertyMismatch() throws Exception
    {
        MethodHeaderPropertyEntryPointResolver resolver = new MethodHeaderPropertyEntryPointResolver();
        MuleEventContext ctx = getTestEventContext("blah");
        ctx.getMessage().setProperty("method", "noMethod");
        InvocationResult result = resolver.invoke(new MultiplePayloadsTestObject(), ctx);
        assertEquals(result.getState(), InvocationResult.STATE_INVOKED_FAILED);
    }

}
