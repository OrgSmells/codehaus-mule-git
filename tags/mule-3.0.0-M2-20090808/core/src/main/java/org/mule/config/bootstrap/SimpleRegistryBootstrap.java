/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.config.bootstrap;

import org.mule.api.MuleContext;
import org.mule.api.context.MuleContextAware;
import org.mule.api.lifecycle.Initialisable;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.registry.MuleRegistry;
import org.mule.api.registry.ObjectProcessor;
import org.mule.api.registry.Registry;
import org.mule.api.transformer.DiscoverableTransformer;
import org.mule.api.transformer.Transformer;
import org.mule.api.transformer.TransformerException;
import org.mule.api.util.StreamCloser;
import org.mule.config.i18n.CoreMessages;
import org.mule.util.ClassUtils;
import org.mule.util.ExceptionUtils;
import org.mule.util.PropertiesUtils;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This object will load objects defined in a file called <code>registry-bootstrap.properties</code> into the local registry.
 * This allows modules and transports to make certain objects available by default.  The most common use case is for a
 * module or transport to load stateless transformers into the registry.
 * For this file to be located it must be present in the modules META-INF directory under
 * <pre>META-INF/services/org/mule/config/</pre>
 * <p/>
 * The format of this file is a simple key / value pair. i.e.
 * <pre>
 * myobject=org.foo.MyObject
 * </pre>
 * Will register an instance of MyObject with a key of 'myobject'. If you don't care about the object name and want to
 * ensure that the ojbect gets a unique name you can use -
 * <pre>
 * object.1=org.foo.MyObject
 * object.2=org.bar.MyObject
 * </pre>
 * or
 * <pre>
 * myFoo=org.foo.MyObject
 * myBar=org.bar.MyObject
 * </pre>
 * Loading transformers has a slightly different notation since you can define the 'returnClass' and 'name'of
 * the transformer as parameters i.e.
 * <pre>
 * transformer.1=org.mule.transport.jms.transformers.JMSMessageToObject,returnClass=byte[]
 * transformer.2=org.mule.transport.jms.transformers.JMSMessageToObject,returnClass=java.lang.String, name=JMSMessageToString
 * transformer.3=org.mule.transport.jms.transformers.JMSMessageToObject,returnClass=java.util.Hashtable)
 * </pre>
 * Note that the key used for transformers must be 'transformer.x' where 'x' is a sequential number.  The transformer name will be
 * automatically generated as JMSMessageToXXX where XXX is the return class name i.e. JMSMessageToString unless a 'name'
 * parameter is specified. If no 'returnClass' is specified the defualt in the transformer will be used.
 * <p/>
 * Note that all objects defined have to have a default constructor. They can implement injection interfaces such as
 * {@link org.mule.api.context.MuleContextAware} and lifecylce interfaces such as {@link org.mule.api.lifecycle.Initialisable}.
 */
public class SimpleRegistryBootstrap implements Initialisable, MuleContextAware
{
    public static final String SERVICE_PATH = "META-INF/services/org/mule/config/";

    public static final String REGISTRY_PROPERTIES = "registry-bootstrap.properties";

    public String TRANSFORMER_PREFIX = "transformer.";
    public String OBJECT_PREFIX = "object.";

    protected final transient Log logger = LogFactory.getLog(getClass());

    protected MuleContext context;

    /** {@inheritDoc} */
    public void setMuleContext(MuleContext context)
    {
        this.context = context;
    }

    /** {@inheritDoc} */
    public void initialise() throws InitialisationException
    {
        Enumeration e = ClassUtils.getResources(SERVICE_PATH + REGISTRY_PROPERTIES, getClass());
        while (e.hasMoreElements())
        {
            try
            {
                URL url = (URL) e.nextElement();
                if(logger.isInfoEnabled()) logger.info("Reading bootstrap file: " + url.toString());
                Properties p = new Properties();
                p.load(url.openStream());
                process(p);
            }
            catch (Exception e1)
            {
                throw new InitialisationException(e1, this);
            }
        }
    }

    protected void process(Properties props) throws Exception
    {
        registerTransformers(props, context.getRegistry());
        registerUnnamedObjects(props, context.getRegistry());
        //this must be called last as it clears the properties map
        registerObjects(props, context.getRegistry());

    }

    private void registerTransformers(Properties props, MuleRegistry registry) throws Exception
    {
        int i = 1;
        String transString = props.getProperty(TRANSFORMER_PREFIX + i);
        String name = null;
        String returnClassString = null;
        boolean optional = false;

        while (transString != null)
        {
            Class returnClass = null;
            int x = transString.indexOf(",");
            if (x > -1)
            {
                Properties p = PropertiesUtils.getPropertiesFromString(transString.substring(x + 1), ',');
                name = p.getProperty("name", null);
                returnClassString = p.getProperty("returnClass", null);
                optional = p.containsKey("optional");
            }

            final String transClass = (x == -1 ? transString : transString.substring(0, x));
            try
            {

                if (returnClassString != null)
                {
                    if (returnClassString.equals("byte[]"))
                    {
                        returnClass = byte[].class;
                    }
                    else
                    {
                        returnClass = ClassUtils.loadClass(returnClassString, getClass());
                    }
                }
                Transformer trans = (Transformer) ClassUtils.instanciateClass(transClass);
                if (!(trans instanceof DiscoverableTransformer))
                {
                    throw new TransformerException(CoreMessages.transformerNotImplementDiscoverable(trans));
                }
                if (returnClass != null)
                {
                    trans.setReturnClass(returnClass);
                }
                if (name != null)
                {
                    trans.setName(name);
                }
                else
                {
                    //This will generate a default name for the transformer
                    name = trans.getName();
                    //We then prefix the name to ensure there is less chance of conflict if the user registers
                    // the transformer with the same name
                    trans.setName("_" + name);
                }
                registry.registerTransformer(trans);
            }
            catch (InvocationTargetException itex)
            {
                Throwable cause = ExceptionUtils.getCause(itex);
                if (cause instanceof NoClassDefFoundError && optional)
                {
                    if (logger.isDebugEnabled())
                    {
                        logger.debug("Ignoring optional transformer: " + transClass);
                    }
                }
                else
                {
                    throw new Exception(cause);
                }
            }
            catch (NoClassDefFoundError ncdfe)
            {
                if (optional)
                {
                    if (logger.isDebugEnabled())
                    {
                        logger.debug("Ignoring optional transformer: " + transClass);
                    }
                }
                else
                {
                    throw ncdfe;
                }
            }
            catch (ClassNotFoundException cnfe)
            {
                if (optional)
                {
                    if (logger.isDebugEnabled())
                    {
                        logger.debug("Ignoring optional transformer: " + transClass);
                    }
                }
                else
                {
                    throw cnfe;
                }
            }

            props.remove(TRANSFORMER_PREFIX + i++);
            name = null;
            returnClass = null;
            transString = props.getProperty(TRANSFORMER_PREFIX + i);
        }
    }

    private void registerObjects(Properties props, Registry registry) throws Exception
    {
        // Note that calling the other register methods first will have removed any processed entries
        for (Iterator iterator = props.entrySet().iterator(); iterator.hasNext();)
        {
            Map.Entry entry = (Map.Entry) iterator.next();
            final String className = entry.getValue().toString();
            boolean optional = false;

            try
            {
                int x = className.indexOf(",");
                if (x > -1)
                {
                    Properties p = PropertiesUtils.getPropertiesFromString(className.substring(x + 1), ',');
                    optional = p.containsKey("optional");
                }
                Object object = ClassUtils.instanciateClass(className);
                String key = entry.getKey().toString();
                Class meta = Object.class;
                if (object instanceof ObjectProcessor)
                {
                    meta = ObjectProcessor.class;
                }
                registry.registerObject(key, object, meta);
            }
            catch (InvocationTargetException itex)
            {
                Throwable cause = ExceptionUtils.getCause(itex);
                if (cause instanceof NoClassDefFoundError && optional)
                {
                    if (logger.isDebugEnabled())
                    {
                        logger.debug("Ignoring optional object: " + className);
                    }
                }
                else
                {
                    throw new Exception(cause);
                }
            }
            catch (NoClassDefFoundError ncdfe)
            {
                if (optional)
                {
                    if (logger.isDebugEnabled())
                    {
                        logger.debug("Ignoring optional object: " + className);
                    }
                }
                else
                {
                    throw ncdfe;
                }
            }
            catch (ClassNotFoundException cnfe)
            {
                if (optional)
                {
                    if (logger.isDebugEnabled())
                    {
                        logger.debug("Ignoring optional object: " + className);
                    }
                }
                else
                {
                    throw cnfe;
                }
            }
        }
        props.clear();
    }

    private void registerUnnamedObjects(Properties props, Registry registry) throws Exception
    {
        int i = 1;
        String objectString = props.getProperty(OBJECT_PREFIX + i);
        while (objectString != null)
        {
            boolean optional = false;
            try
            {
                int x = objectString.indexOf(",");
                if (x > -1)
                {
                    Properties p = PropertiesUtils.getPropertiesFromString(objectString.substring(x + 1), ',');
                    optional = p.containsKey("optional");
                }
                Object o = ClassUtils.instanciateClass(objectString);
                Class meta = Object.class;
                if (o instanceof ObjectProcessor)
                {
                    meta = ObjectProcessor.class;
                }
                else if (o instanceof StreamCloser)
                {
                    meta = StreamCloser.class;
                }
                registry.registerObject(OBJECT_PREFIX + i + "#" + o.hashCode(), o, meta);
            }
            catch (InvocationTargetException itex)
            {
                Throwable cause = ExceptionUtils.getCause(itex);
                if (cause instanceof NoClassDefFoundError && optional)
                {
                    if (logger.isDebugEnabled())
                    {
                        logger.debug("Ignoring optional unnamed object: " + objectString);
                    }
                }
                else
                {
                    throw new Exception(cause);
                }
            }
            catch (NoClassDefFoundError ncdfe)
            {
                if (optional)
                {
                    if (logger.isDebugEnabled())
                    {
                        logger.debug("Ignoring optional unnamed object: " + objectString);
                    }
                }
                else
                {
                    throw ncdfe;
                }
            }
            catch (ClassNotFoundException cnfe)
            {
                if (optional)
                {
                    if (logger.isDebugEnabled())
                    {
                        logger.debug("Ignoring optional unnamed object: " + objectString);
                    }
                }
                else
                {
                    throw cnfe;
                }
            }

            props.remove(OBJECT_PREFIX + i++);
            objectString = props.getProperty(OBJECT_PREFIX + i);
        }
    }
}
