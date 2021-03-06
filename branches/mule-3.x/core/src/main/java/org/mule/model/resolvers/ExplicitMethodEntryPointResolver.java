/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.model.resolvers;

import org.mule.api.MuleEventContext;
import org.mule.api.model.InvocationResult;
import org.mule.config.i18n.CoreMessages;
import org.mule.util.ClassUtils;
import org.mule.util.StringMessageUtils;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * An Entrypoint resolver that allows the user to set one or more acceptiple methd names to look for.
 * For each method reflection will be used to see if the method accepts the current payload types 
 * (the results are cached to improve performance). There has to be at least one method name set 
 * on this resolver
 */
public class ExplicitMethodEntryPointResolver extends AbstractEntryPointResolver
{

    private Set methods = new LinkedHashSet(2);

    public void setMethods(Collection methods)
    {
        this.methods = new LinkedHashSet(methods);
    }

    public void addMethod(String name)
    {
        this.methods.add(name);
    }

    public boolean removeMethod(String name)
    {
        return this.methods.remove(name);
    }

    public InvocationResult invoke(Object component, MuleEventContext context) throws Exception
    {
        if (methods == null || methods.size() == 0)
        {
            throw new IllegalStateException(CoreMessages.objectIsNull("methods").toString());
        }

        Object[] payload = getPayloadFromMessage(context);
        Class<?>[] classTypes = ClassUtils.getClassTypes(payload);
        Method method = null;
        for (Iterator iterator = methods.iterator(); iterator.hasNext();)
        {
            String methodName = (String) iterator.next();
            method = getMethodByName(methodName, context);

            if (method == null)
            {
                method = ClassUtils.getMethod(component.getClass(), methodName, classTypes);
            }
            if (method != null)
            {
                addMethodByName(method, context);
                
                // check if the current payload can be handled by this method
                Class[] parameterTypes = method.getParameterTypes();
                if (ClassUtils.compare(parameterTypes, classTypes, false))
                {
                    // we found a matching method, let's invoke it
                    break;
                }
                else
                {
                    // zero out the reference to the method, it doesn't match
                    method = null;
                }
            }
        }

        if (method == null)
        {
            InvocationResult result = new InvocationResult(InvocationResult.STATE_INVOKED_FAILED);
            result.setErrorNoMatchingMethods(component, classTypes, this);
            return result;
        }
        return invokeMethod(component, method, payload);
    }

    public String toString()
    {
        final StringBuffer sb = new StringBuffer();
        sb.append("ExplicitMethodEntryPointResolver");
        sb.append("{methods=").append(StringMessageUtils.toString(methods));
        sb.append("{transformFirst=").append(isTransformFirst());
        sb.append(", acceptVoidMethods=").append(isAcceptVoidMethods());
        sb.append('}');
        return sb.toString();
    }

}
