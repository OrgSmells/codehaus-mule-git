/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.container;

import org.mule.api.context.ObjectNotFoundException;
import org.mule.api.context.ContainerContext;
import org.mule.container.EjbContainerContext;
import org.mule.context.AbstractContainerContextTestCase;
import org.mule.jndi.MuleInitialContextFactory;
import org.mule.tck.testmodels.fruit.Apple;

import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;

public class EjbContainerContextTestCase extends AbstractContainerContextTestCase
{
    public static final String EJB_NAME = "DummyEjb";

    EjbContainerContext context;

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.tck.model.AbstractComponentResolverTestCase#getConfiguredResolver()
     */
    public ContainerContext getContainerContext()
    {
        return context;
    }

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void doSetUp() throws Exception
    {
        context = new EjbContainerContext();
        Map env = new HashMap();
        env.put(Context.INITIAL_CONTEXT_FACTORY, MuleInitialContextFactory.class.getName());
        context.setEnvironment(env);
        context.initialise();
        Context ic = context.getContext();
        ic.bind(EJB_NAME, new DummyEjbHomeProxy());
        ic.bind(Apple.class.toString(), new Apple());
    }

    // have to override base class here because the EJB container expects only beans,
    // and the fruit example classes are not
    // @Override
    protected void doContentTest(ContainerContext container) throws Exception
    {
        Object result = container.getComponent(EJB_NAME);
        assertNotNull("Component should exist in container", result);
    }

    public void testInvalidObjectLookup() throws Exception
    {
        ContainerContext container = getContainerContext();
        container.initialise();
        assertNotNull(container);

        try
        {
            container.getComponent(Apple.class.getName());
            fail("Should throw ObjectNotFoundException for non-ejb object");
        }
        catch (ObjectNotFoundException e)
        {
            // expected
        }
    }

    // again, need a real bean
    public void testExternalUMOReference() throws Exception
    {
// see MULE-1789
//        getAndVerifyExternalReference(getTestDescriptor("Some EJB service", EJB_NAME));
//        getAndVerifyExternalReference(
//                getTestDescriptor("Some EJB service", DummyEjbHomeProxy.class.getName()));
    }

    protected void verifyExternalReference(Object object)
    {
        assertNotNull(object);
    }

}