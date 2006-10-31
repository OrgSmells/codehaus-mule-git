/*
 * $Id: 
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.jms.filters;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.mule.umo.UMOFilter;
import org.mule.umo.UMOMessage;
import org.mule.util.StringUtils;

import javax.jms.Message;
import java.util.regex.Pattern;

public class JmsPropertyFilter implements UMOFilter 
{
    /**
     * Logger used by this class
     */
    private static transient Log logger = LogFactory.getLog(JmsPropertyFilter.class);

    /**
     * Name of the JMS property to filter on
     */
    private String propertyName = null;

    /**
     * Class type of the JMS property
     */
    private String propertyClass = null;

    /**
     * Expression value to match on
     */
    private Object expression = null;

    /**
     * Optional regular expression pattern to search on
     */
    private Pattern pattern = null;

    public boolean accept(UMOMessage message)
    {
        if (StringUtils.isBlank(propertyName)) {
            logger.warn("No property name was specified");
            return false;
        }
    	
        if (expression == null && pattern == null) {
            logger.warn("Either no expression or pattern was specified");
            return false;
        }
    	
    	if (message.getPayload() instanceof javax.jms.Message) {
    	    try 
	    {
                Message m = (javax.jms.Message)message.getPayload();

		if (StringUtils.isBlank(propertyClass)) {
		    Object object = m.getObjectProperty(propertyName);
		    if (object == null) return false;
		    String value = object.toString();

		    if (pattern != null) 
		    {
			return pattern.matcher(value).find();
		    } 
		    else 
		    {
			return value.equals(expression.toString());
		    }
		}
		else if (propertyClass.equals("java.lang.String"))
		{
		    String value = m.getStringProperty(propertyName);
		    if (value == null) return false;

		    if (pattern != null) 
		    {
			return pattern.matcher(value).find();
		    } 
		    else 
		    {
			return value.equals(expression.toString());
		    }
		}
	    } 
	    catch (Exception e) 
	    {
		logger.warn("Error filtering on property " + propertyName
		    + ": " + e.toString());
	    }
        } else {
            logger.warn("Expected a payload of javax.jms.Message but instead received " + message.getPayload().getClass().getName());
    	}
    	
     	return false;
    }

    /**
     * Sets the match expression
     */
    public void setExpression(Object expression) 
    {
        this.expression = expression;
    }
    
    /**
     * Returns the match expression
     */
    public Object getExpression() 
    {
        return expression;
    }

    /**
     * Sets the name of the property
     */
    public void setPropertyName(String propertyName) 
    {
        this.propertyName = propertyName;
    }
    
    /**
     * Returns the name of the property
     */
    public String getPropertyName() 
    {
        return propertyName;
    }

    /**
     * Sets the class type of the property
     */
    public void setPropertyClass(String propertyClass) 
    {
        this.propertyClass = propertyClass;
    }
    
    /**
     * Returns the class type of the property
     */
    public String getPropertyClass() 
    {
        return propertyClass;
    }

    /**
     * Sets the regex pattern to match on
     */
    public String getPattern()
    {
        return (pattern == null ? null : pattern.pattern());
    }

    /**
     * Return the regex pattern to match on
     */
    public void setPattern(String pattern)
    {
        this.pattern = Pattern.compile(pattern);
    }

}
