/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.expression;

import org.mule.api.MuleMessage;
import org.mule.api.expression.ExpressionEvaluator;
import org.mule.api.expression.RequiredValueException;
import org.mule.config.i18n.CoreMessages;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Looks up the property on the message using the expression given. The expression can contain a comma-separated list
 * of header names to lookup. A {@link java.util.Map} of key value pairs is returned.
 *
 * @see MessageHeadersListExpressionEvaluator
 * @see org.mule.api.expression.ExpressionEvaluator
 * @see DefaultExpressionManager
 */
public class MessageHeadersExpressionEvaluator implements ExpressionEvaluator, ExpressionConstants
{
    public static final String NAME = "headers";

    public Object evaluate(String expression, MuleMessage message)
    {
        boolean required;

        Map result;
        if (ALL_ARGUMENT.equals(expression))
        {
            result = new HashMap(message.getPropertyNames().size());
            for (Iterator iterator = message.getPropertyNames().iterator(); iterator.hasNext();)
            {
                String name = (String) iterator.next();
                result.put(name, message.getProperty(name));
            }
        }
        else if(COUNT_ARGUMENT.equals(expression))
        {
            return message.getPropertyNames().size();
        }
        else
        {
            StringTokenizer tokenizer = new StringTokenizer(expression, DELIM);
            result = new HashMap(tokenizer.countTokens());
            while (tokenizer.hasMoreTokens())
            {
                String s = tokenizer.nextToken();
                s = s.trim();
                if (s.endsWith(OPTIONAL_ARGUMENT))
                {
                    s = s.substring(0, s.length() - OPTIONAL_ARGUMENT.length());
                    required = false;
                }
                else
                {
                    required = true;
                }
                
                Object val = message.getProperty(s);
                if (val != null)
                {
                    result.put(s, val);
                }
                else if (required)
                {
                    throw new RequiredValueException(CoreMessages.expressionEvaluatorReturnedNull(NAME, expression));
                }
            }
        }
        if (result.size() == 0)
        {
            return null;
        }
        else
        {
            return result;
        }
    }

    /**
     * {@inheritDoc}
     */
    public String getName()
    {
        return NAME;
    }

    /**
     * {@inheritDoc}
     */
    public void setName(String name)
    {
        throw new UnsupportedOperationException();
    }
}
