/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.object;

import org.mule.api.lifecycle.InitialisationException;
import org.mule.tck.AbstractMuleTestCase;

public abstract class AbstractObjectFactoryTestCase extends AbstractMuleTestCase
{

    public void testInitialisationFailure() throws Exception
    {
        AbstractObjectFactory factory = getUninitialisedObjectFactory();

        try
        {
            factory.initialise();
            fail("expected InitialisationException");
        }
        catch (InitialisationException iex)
        {
            // OK
        }

        try
        {
            factory.getInstance();
            fail("expected InitialisationException");
        }
        catch (InitialisationException iex)
        {
            // OK
        }
    }

    public void testInitialiseWithClass() throws Exception
    {
        AbstractObjectFactory factory = getUninitialisedObjectFactory();
        factory.setObjectClass(Object.class);

        factory.initialise();
        assertNotNull(factory.getInstance());
    }

    public void testInitialiseWithClassName() throws Exception
    {
        AbstractObjectFactory factory = getUninitialisedObjectFactory();
        factory.setObjectClassName(Object.class.getName());

        factory.initialise();
        assertNotNull(factory.getInstance());
    }

    public void testDispose() throws Exception
    {
        AbstractObjectFactory factory = getUninitialisedObjectFactory();
        factory.setObjectClass(Object.class);

        factory.initialise();
        factory.dispose();

        assertNull(factory.getObjectClass());

        try
        {
            factory.getInstance();
            fail("expected InitialisationException");
        }
        catch (InitialisationException iex)
        {
            // OK
        }
    }

    public abstract AbstractObjectFactory getUninitialisedObjectFactory();

    public abstract void testGetObjectClass() throws Exception;

    public abstract void testGet() throws Exception;

}
