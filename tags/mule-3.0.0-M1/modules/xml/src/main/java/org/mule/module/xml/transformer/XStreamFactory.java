/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.xml.transformer;

import org.mule.util.ClassUtils;
import org.mule.config.i18n.CoreMessages;
import org.mule.module.xml.i18n.XmlMessages;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.collections.MapConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;
import com.thoughtworks.xstream.mapper.Mapper;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Initializes the XStream utility for converting Objects to XML and XML to Objects.
 */
// @Immutable
public class XStreamFactory
{
    public static final String XSTREAM_DOM_DRIVER = "com.thoughtworks.xstream.io.xml.DomDriver";
    public static final String XSTREAM_DOM4J_DRIVER = "com.thoughtworks.xstream.io.xml.Dom4JDriver";
    public static final String XSTREAM_JDOM_DRIVER = "com.thoughtworks.xstream.io.xml.JDomDriver";
    public static final String XSTREAM_STAX_DRIVER = "com.thoughtworks.xstream.io.xml.StaxDriver";
    public static final String XSTREAM_XPP_DRIVER = "com.thoughtworks.xstream.io.xml.XppDriver";

    private final XStream xstream;

    public XStreamFactory() throws ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        this(XSTREAM_XPP_DRIVER, null, null);
    }

    public XStreamFactory(String driverClassName, Map<String, Class> aliases, List<Class> converters)
        throws ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        Class driverClass = ClassUtils.loadClass(driverClassName, this.getClass());
        xstream = new XStream((HierarchicalStreamDriver)driverClass.newInstance());

        // We must always register this converter as the Mule Message uses
        // ConcurrentHashMaps, but XStream currently does not support them out of the
        // box.
        xstream.registerConverter(new XStreamFactory.ConcurrentHashMapConverter(xstream.getMapper()), -1);

        if (aliases != null)
        {
            for (Map.Entry<String, Class> entry : aliases.entrySet())
            {
                xstream.alias(entry.getKey(), entry.getValue());
            }
        }

        if (converters != null)
        {
            for (Class converter : converters)
            {
                xstream.registerConverter((Converter)converter.newInstance());
            }
        }
    }

    public final XStream getInstance()
    {
        return xstream;
    }

    private class ConcurrentHashMapConverter extends MapConverter
    {
        public ConcurrentHashMapConverter(Mapper mapper) throws ClassNotFoundException
        {
            super(mapper);
        }

        public boolean canConvert(Class aClass)
        {
            String className = aClass.getName();
            return className.equals("java.util.concurrent.ConcurrentHashMap")
                            || className.equals("edu.emory.mathcs.backport.java.util.concurrent.ConcurrentHashMap");
        }
    }

}
