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
 *
 */

package org.mule.test.mule.model;

import org.mule.model.MuleContainerContext;
import org.mule.tck.model.AbstractComponentResolverTestCase;
import org.mule.tck.testmodels.fruit.FruitBowl;
import org.mule.umo.UMODescriptor;
import org.mule.umo.model.UMOContainerContext;

/**
 * <p><code>MuleContainerContextTestCase</code> TODO (document class)
 *
 * @author <a href="mailto:ross.mason@cubis.co.uk">Ross Mason</a>
 * @version $Revision$
 */
public class MuleContainerContextTestCase extends AbstractComponentResolverTestCase
{

    MuleContainerContext context;

    /* (non-Javadoc)
     * @see org.mule.tck.model.AbstractComponentResolverTestCase#getConfiguredResolver()
     */
    public UMOContainerContext getContainerContext()
    {
        return context;
    }

    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception
    {
        super.setUp();
        context = new MuleContainerContext();
    }

//    /* (non-Javadoc)
//     * @see org.mule.tck.model.AbstractComponentResolverTestCase#testDescriptorExternalReferences()
//     */
//    public void testDescriptorExternalReferences() throws Exception
//    {
//        UMODescriptor d = getTestDescriptor("friutbowl", FruitBowl.class.getName());
//        resolver.resolveComponents(new Object(), d.getObjectReferences());
//
//        d.addObjectReference(new ObjectReference("foo", "bar", true));
//        try
//        {
//            resolver.resolveComponents(new Object(), d.getObjectReferences());
//            fail("Should barf if component isn't a class name");
//        }
//        catch (ComponentResolverException e)
//        {
//            // expected
//        }
//        d.getObjectReferences().clear();
//        //Should work as the ref is not required
//        d.addObjectReference(new ObjectReference("foo", "bar", false));
//        resolver.resolveComponents(new Object(), d.getObjectReferences());
//
//        d.getObjectReferences().clear();
//        FruitBowl fb = new FruitBowl();
//        d.addObjectReference(new ObjectReference("banana", Banana.class.getName(), true));
//        resolver.resolveComponents(fb, d.getObjectReferences());
//        assertNotNull(fb.getBanana());
//        assertNull(fb.getApple());
//    }
//
//     public void testDescriptorExternalReferenceAsFactory() throws Exception
//    {
//        UMODescriptor d = getTestDescriptor("friutbowl", FruitBowl.class.getName());
//        resolver.resolveComponents(new Object(), d.getObjectReferences());
//
//        FruitBowl fb = new FruitBowl();
//
//        d.addObjectReference(new ObjectReference("banana", BananaFactory.class.getName(), true));
//        resolver.resolveComponents(fb, d.getObjectReferences());
//        assertNotNull(fb.getBanana());
//        assertNull(fb.getApple());
//    }


    /* (non-Javadoc)
     * @see org.mule.tck.model.AbstractComponentResolverTestCase#testExternalUMOReference()
     */
//    public void testExternalUMOReference() throws Exception
//    {
//        //The Mule component resolver doesn't support autowiring of components
//        context.resolveComponents(null, null);
//    }

     public void testExternalUMOReference() throws Exception
    {
        UMOContainerContext ctx = getContainerContext();
        assertNotNull(ctx);

        UMODescriptor descriptor = getTestDescriptor("fruit Bowl", "org.mule.tck.testmodels.fruit.FruitBowl");
        FruitBowl fruitBowl = (FruitBowl) ctx.getComponent(descriptor.getImplementation());

        assertNotNull(fruitBowl);
    }

}
