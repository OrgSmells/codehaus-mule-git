/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.impl.model.resolvers;

import org.mule.config.i18n.CoreMessages;
import org.mule.config.i18n.MessageFactory;
import org.mule.umo.UMOEventContext;
import org.mule.umo.lifecycle.Callable;
import org.mule.umo.model.InvocationResult;
import org.mule.umo.model.UMOEntryPointResolver;
import org.mule.util.ClassUtils;
import org.mule.MuleRuntimeException;

import java.lang.reflect.Method;

/**
 * An entrypoint resolver that only allows Service objects that implmement the
 * Callable interface
 *
 * @see org.mule.umo.lifecycle.Callable
 */
public class CallableEntryPointResolver implements UMOEntryPointResolver
{
    protected static final Method callableMethod;

    static
    {
        try
        {
            callableMethod = Callable.class.getMethod("onCall", new Class[] {UMOEventContext.class});
        }
        catch (NoSuchMethodException e)
        {
            throw new MuleRuntimeException(
                    MessageFactory.createStaticMessage("Panic! No onCall(UMOEventContext) method found in the Callable interface."));
        }
    }


    public InvocationResult invoke(Object component, UMOEventContext context) throws Exception
    {
        if (component instanceof Callable)
        {
            Object result = ((Callable) component).onCall(context);
            return new InvocationResult(result, callableMethod);
        }
        else
        {
            InvocationResult result = new InvocationResult(InvocationResult.STATE_INVOKE_NOT_SUPPORTED);
            result.setErrorMessage(ClassUtils.getClassName(getClass()) + ":" +
                    CoreMessages.objectDoesNotImplementInterface(component, Callable.class).toString());
            return result;
        }
    }


    public String toString()
    {
        final StringBuffer sb = new StringBuffer();
        sb.append("CallableEntryPointResolver");
        sb.append("{}");
        return sb.toString();
    }
}
