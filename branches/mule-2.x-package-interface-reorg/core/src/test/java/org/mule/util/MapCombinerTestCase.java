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

import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;

public class MapCombinerTestCase extends AbstractMuleTestCase
{

    public void testBasicMerge()
    {
        doTestMerge(new MapCombiner(), "[a:[b:B,c:C]]", "[a:[d:D]]", "[a:[b:B,c:C,d:D]]");
    }

    public void testOverwrite()
    {
        MapCombiner combiner = new MapCombiner();
        combiner.setMaxDepth(0);
        doTestMerge(combiner, "[a:[b:B,c:C]]", "[a:[d:D]]", "[a:[d:D]]");
    }

    public void testDeepMerge()
    {
        doTestMerge(new MapCombiner(), "[a:[b:B,c:C,d:[e:E,f:F]]]", "[a:[d:[g:G]]]", "[a:[b:B,c:C,d:[e:E,f:F,g:G]]]");
    }

    public void testRestrictedMerge()
    {
        MapCombiner combiner = new MapCombiner();
        combiner.setMaxDepth(1);
        doTestMerge(combiner, "[a:[b:B,c:C,d:[e:E,f:F]]]", "[a:[d:[g:G]]]", "[a:[b:B,c:C,d:[g:G]]]");
    }

    public void testMergeLists()
    {
        doTestMerge(new MapCombiner(), "[a:(b,c)]", "[a:(d)]", "[a:(b,c,d)]");
    }

    protected void doTestMerge(MapCombiner combiner, String spec1, String spec2, String specResult)
    {
        Map map1 = buildMap(spec1);
        Map map2 = buildMap(spec2);
        Map map3 = buildMap(specResult);
        combiner.setList(new LinkedList());
        combiner.getList().add(map1);
        combiner.getList().add(map2);
        Assert.assertFalse(combiner.isEmpty()); // trigger merge
        Assert.assertEquals(combiner, map3);
    }

    public void testInfrastructure()
    {
        Map map = buildMap("[a:(b,c)]");
        assertTrue(map.get("a") instanceof List);
        List list = (List) map.get("a");
        assertTrue(list.contains("b"));
        assertTrue(list.contains("c"));
    }

    public static Map buildMap(String spec)
    {
        Map map = new HashMap();
        String empty = fillMap(map, spec);
        Assert.assertTrue("after parsing " + spec + " left with " + empty, empty.equals(""));
        return map;
    }

    protected static String fillMap(Map map, String spec)
    {
        spec = drop(spec, "[");
        while (! spec.startsWith("]"))
        {
            Assert.assertTrue("spec finished early (missing ']'?)", spec.length() > 1);
            String key = spec.substring(0, 1);
            spec = drop(spec, key);
            spec = drop(spec, ":");
            if (spec.startsWith("["))
            {
                Map value = new HashMap();
                spec = fillMap(value, spec);
                map.put(key, value);
            }
            else if (spec.startsWith("("))
            {
                List value = new LinkedList();
                spec = fillList(value, spec);
                map.put(key, value);
            }
            else
            {
                String value = spec.substring(0, 1);
                spec = drop(spec, value);
                map.put(key, value);
            }
            if (spec.startsWith(","))
            {
                spec = drop(spec, ",");
            }
        }
        return drop(spec, "]");
    }

    protected static String fillList(List list, String spec)
    {
        spec = drop(spec, "(");
        while (! spec.startsWith(")"))
        {
            Assert.assertTrue("spec finished early (missing ')'?)", spec.length() > 1);
            if (spec.startsWith("["))
            {
                Map value = new HashMap();
                spec = fillMap(value, spec);
                list.add(value);
            }
            else if (spec.startsWith("("))
            {
                List value = new LinkedList();
                spec = fillList(value, spec);
                list.add(value);
            }
            else
            {
                String value = spec.substring(0, 1);
                spec = drop(spec, value);
                list.add(value);
            }
            if (spec.startsWith(","))
            {
                spec = drop(spec, ",");
            }
        }
        return drop(spec, ")");
    }

    protected static String drop(String spec, String delim)
    {
        Assert.assertTrue("expected " + delim + " but spec is " + spec, spec.startsWith(delim));
        return spec.substring(1);
    }

}