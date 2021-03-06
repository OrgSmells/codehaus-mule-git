/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.xml.filters;

import org.mule.api.MuleMessage;
import org.mule.api.routing.filter.Filter;
import org.mule.util.StringMessageUtils;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.jxpath.AbstractFactory;
import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.XPath;

/**
 * <code>JXPathFilter</code> evaluates an XPath expression against a W3C Document,
 * XML string, or Java bean and returns true if the result is as expected.
 */
public class JXPathFilter implements Filter
{

    protected transient Log logger = LogFactory.getLog(getClass());

    private String pattern;
    private String expectedValue;
    private Map namespaces = null;
    private Map contextProperties = null;
    private AbstractFactory factory;
    private boolean lenient = true;

    public JXPathFilter()
    {
        super();
    }

    public JXPathFilter(String pattern)
    {
        this.pattern = pattern;
    }

    public JXPathFilter(String pattern, String expectedValue)
    {
        this.pattern = pattern;
        this.expectedValue = expectedValue;
    }

    public boolean accept(MuleMessage obj)
    {
        if (obj.getPayload() instanceof byte[])
        {
            try
            {
                return accept(obj.getPayloadAsString());
            }
            catch (Exception e)
            {
                logger.warn("JxPath filter rejected message because it could not convert from byte[] to String" + e.getMessage(), e);
                return false;
            }
        }
        return accept(obj.getPayload());
    }

    private boolean accept(Object obj)
    {
        if (obj == null)
        {
            logger.warn("Applying JXPathFilter to null object.");
            return false;
        }
        if (pattern == null)
        {
            logger.warn("Expression for JXPathFilter is not set.");
            return false;
        }
        if (expectedValue == null)
        {
            // Handle the special case where the expected value really is null.
            if (pattern.endsWith("= null") || pattern.endsWith("=null"))
            {
                expectedValue = "null";
                pattern = pattern.substring(0, pattern.lastIndexOf("="));
            }
            else
            {
                if (logger.isInfoEnabled())
                {
                    logger.info("Expected value for JXPathFilter is not set, using 'true' by default");
                }
                expectedValue = Boolean.TRUE.toString();
            }
        }

        Object xpathResult = null;
        boolean accept = false;

        // Payload is a DOM Document
        if (obj instanceof Document)
        {
            if (namespaces == null)
            {
                // no namespace defined, let's perform a direct evaluation
                xpathResult = ((Document) obj).valueOf(pattern);
            }
            else
            {
                // create an xpath expression with namespaces and evaluate it
                XPath xpath = DocumentHelper.createXPath(pattern);
                xpath.setNamespaceURIs(namespaces);
                xpathResult = xpath.valueOf(obj);
            }

        }
        // Payload is a String of XML
        else if (obj instanceof String)
        {
            try
            {
                return accept(DocumentHelper.parseText((String) obj));
            }
            catch (DocumentException e)
            {
                logger.warn("JXPathFilter unable to parse XML document: " + e.getMessage(), e);
                if (logger.isDebugEnabled())
                {
                    logger.debug("XML = " + StringMessageUtils.truncate((String) obj, 200, false));
                }
                return false;
            }
        }
        // Payload is a Java object
        else
        {
            if (logger.isDebugEnabled())
            {
                logger.debug("Passing object of type " + obj.getClass().getName() + " to JXPathContext");
            }
            JXPathContext context = JXPathContext.newContext(obj);
            initialise(context);
            xpathResult = context.getValue(pattern);
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("JXPathFilter Expression result = '" + xpathResult + "' -  Expected value = '"
                    + expectedValue + "'");
        }
        // Compare the XPath result with the expected result.
        if (xpathResult != null)
        {
            accept = xpathResult.toString().equals(expectedValue);
        }
        else
        {
            // A null result was actually expected.
            if (expectedValue.equals("null"))
            {
                accept = true;
            }
            // A null result was not expected, something probably went wrong.
            else
            {
                logger.warn("JXPathFilter expression evaluates to null: " + pattern);
            }
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("JXPathFilter accept object  : " + accept);
        }

        return accept;
    }

    /**
     * Initializes the JXPathContext based on any relevant properties set for the
     * filter.
     *
     * @param context the JXPathContext to initialize
     */
    protected void initialise(JXPathContext context)
    {
        Map.Entry entry;
        if (namespaces != null)
        {
            if (logger.isDebugEnabled())
            {
                logger.debug("Initializing JXPathContext with namespaces: " + namespaces);
            }

            for (Iterator iterator = namespaces.entrySet().iterator(); iterator.hasNext();)
            {
                entry = (Map.Entry) iterator.next();
                context.registerNamespace(entry.getKey().toString(), entry.getValue().toString());
            }
        }

        if (contextProperties != null)
        {
            if (logger.isDebugEnabled())
            {
                logger.debug("Initializing JXPathContext with properties: " + contextProperties);
            }

            for (Iterator iterator = contextProperties.entrySet().iterator(); iterator.hasNext();)
            {
                entry = (Map.Entry) iterator.next();
                context.setValue(entry.getKey().toString(), entry.getValue());
            }
        }

        if (factory != null)
        {
            context.setFactory(factory);
        }

        context.setLenient(lenient);
    }

    /** @return XPath expression */
    public String getPattern()
    {
        return pattern;
    }

    /** @param pattern The XPath expression */
    public void setPattern(String pattern)
    {
        this.pattern = pattern;
    }

    /** @return The expected result value of the XPath expression */
    public String getExpectedValue()
    {
        return expectedValue;
    }

    /** Sets the expected result value of the XPath expression */
    public void setExpectedValue(String expectedValue)
    {
        this.expectedValue = expectedValue;
    }

    /**
     * @return The expected result value of the XPath expression
     * @deprecated Use <code>getExpectedValue()</code>.
     */
    public String getValue()
    {
        return getExpectedValue();
    }

    /**
     * Sets the expected result value of the XPath expression
     *
     * @deprecated Use <code>setExpectedValue(String expectedValue)</code>.
     */
    public void setValue(String value)
    {
        setExpectedValue(value);
    }

    public Map getNamespaces()
    {
        return namespaces;
    }

    public void setNamespaces(Map namespaces)
    {
        this.namespaces = namespaces;
    }

    public Map getContextProperties()
    {
        return contextProperties;
    }

    public void setContextProperties(Map contextProperties)
    {
        this.contextProperties = contextProperties;
    }

    public AbstractFactory getFactory()
    {
        return factory;
    }

    public void setFactory(AbstractFactory factory)
    {
        this.factory = factory;
    }

    public boolean isLenient()
    {
        return lenient;
    }

    public void setLenient(boolean lenient)
    {
        this.lenient = lenient;
    }
}
