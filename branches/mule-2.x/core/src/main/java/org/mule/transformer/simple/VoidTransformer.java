/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transformer.simple;

import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.transformer.Transformer;
import org.mule.api.transformer.TransformerException;

import java.util.List;

/**
 * A unique transformer instance than indicates an error (typically that the transformer
 * in question has not been initialised).
 */
public class VoidTransformer implements Transformer
{

    private static class SingletonHolder
    {
        private static final VoidTransformer instance = new VoidTransformer();
    }

    public static VoidTransformer getInstance()
    {
        return SingletonHolder.instance;
    }

    private VoidTransformer()
    {
        // no-op
    }

    public boolean isSourceTypeSupported(Class aClass)
    {
        throw new IllegalStateException("Void transformer");
    }

    public boolean isAcceptNull()
    {
        throw new IllegalStateException("Void transformer");
    }

    public boolean isIgnoreBadInput()
    {
        throw new IllegalStateException("Void transformer");
    }

    public Object transform(Object src) throws TransformerException
    {
        throw new IllegalStateException("Void transformer");
    }

    public void setReturnClass(Class theClass)
    {
        throw new IllegalStateException("Void transformer");
    }

    public Class getReturnClass()
    {
        throw new IllegalStateException("Void transformer");
    }

    public ImmutableEndpoint getEndpoint()
    {
        throw new IllegalStateException("Void transformer");
    }

    public void setEndpoint(ImmutableEndpoint endpoint)
    {
        throw new IllegalStateException("Void transformer");
    }

    public void setName(String newName)
    {
        throw new IllegalStateException("Void transformer");
    }

    public List getSourceTypes()
    {
        throw new IllegalStateException("Void transformer");
    }

    public String getName()
    {
        throw new IllegalStateException("Void transformer");
    }

    public void initialise() throws InitialisationException
    {
        throw new IllegalStateException("Void transformer");
    }
}
