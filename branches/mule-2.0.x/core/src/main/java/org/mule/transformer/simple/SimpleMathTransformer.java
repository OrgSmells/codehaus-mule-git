/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transformer.simple;

import org.mule.api.transformer.TransformerException;
import org.mule.config.i18n.MessageFactory;
import org.mule.transformer.AbstractTransformer;
import org.mule.util.NumberUtils;

/** 
 * A simple transformer which adds/subtracts/multiplies/divides a constant factor to numeric messages. 
 */
public class SimpleMathTransformer extends AbstractTransformer 
{
    /** Operation to perform: "add", "subtract", "multiply", "divide" */
    private String operation = "add";
    
    /** Factor to be applied */
    private double factor;
    
    public SimpleMathTransformer()
    {
        registerSourceType(Number.class);
        setReturnClass(Number.class);
    }

    public Object doTransform(Object src, String encoding) throws TransformerException
    {         
        double data = NumberUtils.toDouble(src);
        if (data == NumberUtils.DOUBLE_ERROR)
        {
            throw new TransformerException(MessageFactory.createStaticMessage("Unable to convert message to double: " + src));
        }
        
        if (operation.equalsIgnoreCase("add"))
        {
            return data + factor;
        }
        else if (operation.equalsIgnoreCase("subtract"))
        {
            return data - factor;
        }
        else if (operation.equalsIgnoreCase("multiply"))
        {
            return data * factor;
        }
        else if (operation.equalsIgnoreCase("divide"))
        {
            return data / factor;
        }
        else 
        {
            throw new TransformerException(MessageFactory.createStaticMessage("Operation " + operation + " not recognized"));
        }
    }

    public String getOperation()
    {
        return operation;
    }

    public void setOperation(String operation)
    {
        this.operation = operation;
    }

    public double getFactor()
    {
        return factor;
    }

    public void setFactor(double factor)
    {
        this.factor = factor;
    }
}
