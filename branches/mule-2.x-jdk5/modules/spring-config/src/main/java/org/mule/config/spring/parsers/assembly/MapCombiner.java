/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.spring.parsers.assembly;

import java.util.Map;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.List;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This is used internally by {@link org.mule.config.spring.parsers.assembly.DefaultBeanAssembler}.
 * It allows a collection (list) of maps to be defined in Spring, via the "list" property, and
 * then presents all the maps as a single combine map at run time.  For efficiency the combination
 * of maps is done once and then cached.
 */
public class MapCombiner implements Map
{

    public static final String LIST = "list"; // the setter/getter
    public static final int UNLIMITED_DEPTH = -1;

    private Log logger = LogFactory.getLog(getClass());
    private int maxDepth = UNLIMITED_DEPTH;
    private List list;
    private Map cachedMerge = new HashMap();
    private boolean isMerged = false;

    private synchronized Map getCachedMerge()
    {
        if (!isMerged)
        {
            for (Iterator maps = list.iterator(); maps.hasNext();)
            {
                merge(maxDepth, cachedMerge, (Map) maps.next());
            }
            isMerged = true;
        }
        return cachedMerge;
    }

    public void setMaxDepth(int maxDepth)
    {
        this.maxDepth = maxDepth;
    }

    private void merge(int headroom, Map accumulator, Map extra)
    {
        for (Iterator keys = extra.keySet().iterator(); keys.hasNext();)
        {
            Object key = keys.next();
            Object valueExtra = extra.get(key);
            if (accumulator.containsKey(key))
            {
                Object valueOriginal = accumulator.get(key);
                if (valueExtra instanceof Map && valueOriginal instanceof Map && headroom != 0)
                {
                    merge(headroom - 1, (Map) valueOriginal, (Map) valueExtra);
                }
                else
                {
                    if (logger.isDebugEnabled())
                    {
                        logger.debug("Overwriting " + valueOriginal + " for " + key + " during map merge");
                    }
                    accumulator.put(key, valueExtra);
                }
            }
            else
            {
                accumulator.put(key, valueExtra);
            }
        }
    }

    public void setList(List list)
    {
        assertNotMerged();
        this.list = list;
    }

    public List getList()
    {
        assertNotMerged();
        return list;
    }

    private synchronized void assertNotMerged()
    {
        if (isMerged)
        {
            throw new IllegalStateException("Maps have already been merged");
        }
    }

    // hashcode and equals don't trigger merge

    public int hashCode()
    {
        return cachedMerge.hashCode();
    }

    public boolean equals(Object o)
    {
        return cachedMerge.equals(o);
    }

    public int size()
    {
        return getCachedMerge().size();
    }

    public void clear()
    {
        getCachedMerge().clear();
    }

    public boolean isEmpty()
    {
        return getCachedMerge().isEmpty();
    }

    public boolean containsKey(Object key)
    {
        return getCachedMerge().containsKey(key);
    }

    public boolean containsValue(Object value)
    {
        return getCachedMerge().containsValue(value);
    }

    public Collection values()
    {
        return getCachedMerge().values();
    }

    public void putAll(Map t)
    {
        getCachedMerge().putAll(t);
    }

    public Set entrySet()
    {
        return getCachedMerge().entrySet();
    }

    public Set keySet()
    {
        return getCachedMerge().keySet();
    }

    public Object get(Object key)
    {
        return getCachedMerge().get(key);
    }

    public Object remove(Object key)
    {
        return getCachedMerge().remove(key);
    }

    public Object put(Object key, Object value)
    {
        return getCachedMerge().put(key, value);
    }

}