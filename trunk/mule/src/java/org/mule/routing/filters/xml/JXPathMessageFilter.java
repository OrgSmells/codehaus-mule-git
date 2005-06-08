/*
 * $Header$
 * $Revision$
 * $Date$
 * ------------------------------------------------------------------------------------------------------
 *
 * Copyright (c) SymphonySoft Limited. All rights reserved.
 * http://www.symphonysoft.com
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.mule.routing.filters.xml;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.log4j.Logger;
import org.mule.umo.UMOFilter;
import org.mule.umo.UMOMessage;

/**
 * <code>JXPathMessageFilter</code> evaluates an XPath expression against an 
 * UMOMessage object.
 * 
 * @author <a href="mailto:S.Vanmeerhaege@gfdi.be">Vanmeerhaeghe St�phane</a>
 * @version $Revision$
 */
public class JXPathMessageFilter implements UMOFilter
{

    private static final Logger logger = Logger.getLogger(JXPathMessageFilter.class);

    private String expression;

    private String value;

    public boolean accept(UMOMessage message)
    {
        if (expression == null) {
            logger.warn("Expression for JXPathMessageFilter is not set");
            return false;
        }

        if (value == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("Value for JXPathMessageFilter is not set : true by default");
            }
            value = Boolean.TRUE.toString();
        }

        boolean res = false;

        try {
            Object o = null;

            JXPathContext context = JXPathContext.newContext(message);
            o = context.getValue(expression);

            if (logger.isDebugEnabled()) {
                logger.debug("JXPathMessageFilter Expression result='" + o + "' -  Expected value='" + value + "'");
            }

            if (o != null) {
                res = value.equals(o.toString());
            } else {
                res = false;
                logger.warn("JXPathMessageFilter Expression result is null (" + expression + ")");
            }
        } catch (Exception e) {
            logger.warn("JXPathMessageFilter cannot evaluate expression (" + expression + ") :" + e.getMessage(), e);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("JXPathMessageFilter accept object  : " + res);
        }
        return res;

    }

    /**
     * @return XPath expression
     */
    public String getExpression()
    {
        return expression;
    }

    /**
     * @param expression The XPath expression
     */
    public void setExpression(String expression)
    {
        this.expression = expression;
    }

    /**
     * @return The expected result value of the XPath expression
     */
    public String getValue()
    {
        return value;
    }

    /**
     * @param value The expected result value of the XPath expression
     */
    public void setValue(String value)
    {
        this.value = value;
    }
}
