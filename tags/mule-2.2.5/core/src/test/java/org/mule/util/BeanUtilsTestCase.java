/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.util;

import org.mule.tck.AbstractMuleTestCase;
import org.mule.tck.testmodels.fruit.Orange;
import org.mule.tck.testmodels.fruit.OrangeInterface;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 */
public class BeanUtilsTestCase extends AbstractMuleTestCase
{
    public void testBeanPropertiesOnAProxy() throws Exception
    {
        Map map = new HashMap();
        map.put("brand", "Juicy!");
        map.put("radius", "2.32");
        map.put("segments", "22");

        OrangeInterface o = (OrangeInterface)Proxy.newProxyInstance(getClass().getClassLoader(),
                new Class[]{OrangeInterface.class}, new OrangeInvocationHandler(new Orange()));

        BeanUtils.populateWithoutFail(o, map, true);

        assertNotNull(o);
        assertEquals("Juicy!", o.getBrand());
        assertEquals(new Double(2.32), o.getRadius());
        assertEquals(new Integer(22), o.getSegments());
    }

    public void testBeanPropertiesWithoutFail() throws Exception
    {
        Map map = new HashMap();
        map.put("brand", "Juicy!");
        map.put("radius", "2.32");
        map.put("segments", "22");
        map.put("trombones", "3");

        Orange o = new Orange();

        BeanUtils.populateWithoutFail(o, map, true);

        assertNotNull(o);
        assertEquals("Juicy!", o.getBrand());
        assertEquals(new Double(2.32), o.getRadius());
        assertEquals(new Integer(22), o.getSegments());
    }


    public void testBeanPropertiesWithFail() throws Exception
    {
        Map map = new HashMap();
        map.put("brand", "Juicy!");
        map.put("radius", "2.32");
        map.put("segments", "22");
        map.put("trombones", "3");

        Orange o = new Orange();

        try
        {
            BeanUtils.populate(new Orange(), map);
            fail("Trombones is not a valid property");
        }
        catch (IllegalArgumentException e)
        {
            //expected
            assertTrue(e.getMessage().indexOf("trombone") > -1);
        }
    }

    private class OrangeInvocationHandler implements InvocationHandler
    {
        private Orange orange;

        private OrangeInvocationHandler(Orange orange)
        {
            this.orange = orange;
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
        {
            return method.invoke(orange, args); 
        }
    }
}
