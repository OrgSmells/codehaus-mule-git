/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.transformer;

import org.mule.api.transformer.DiscoverableTransformer;
import org.mule.api.transformer.Transformer;

import java.util.Iterator;
import java.util.List;

/**
 * Given a {@link org.mule.api.transformer.Transformer} instance, an input class and output class
 * this object will create a weighting for a transformer. This weighthing can be used compare one transformer with
 * another, which can be useful for choosing a transformer to use given the input class and required output class.
 */
public class TransformerWeighting implements Comparable
{
    private Transformer transformer;
    private int inputWeighting;
    private int outputWeighting;
    private Class inputClass;
    private Class outputClass;

    public TransformerWeighting(Class inputClass, Class outputClass, Transformer transformer)
    {
        this.inputClass = inputClass;
        this.outputClass = outputClass;
        this.transformer = transformer;
        init();
    }

    private void init()
    {
        inputWeighting = Integer.MAX_VALUE;
        List sourceTypes = transformer.getSourceTypes();

        for (Iterator iterator = sourceTypes.iterator(); iterator.hasNext();)
        {
            Class aClass = (Class) iterator.next();
            int weighting = getWeighting(-1, inputClass, aClass);
            if (weighting < inputWeighting && weighting != -1)
            {
                inputWeighting = weighting;
            }
        }

        outputWeighting = getWeighting(-1, outputClass, transformer.getReturnClass());

        inputWeighting = (inputWeighting == Integer.MAX_VALUE ? -1 : inputWeighting);
        outputWeighting = (outputWeighting == Integer.MAX_VALUE ? -1 : outputWeighting);

    }

    /**
     * THis is a very basic algorithm for creating a match rating for two classes.  An offset weighting
     * can also be passed in. Where w is weighting,
     * if the classes match exactly, w+1 is returned,
     * if the classes are assignable and there is a direct equality to an interface on the class, w+2  is returned,
     * if the classes are assignable but no direct interface match, w+3 is returned
     * If the dest type is Object.class then w+4is returned. This is because matching on object will yeild a lot of options
     * putting those that use the Object.class generic type should get pushed down the list
     * If there a super class, that will get matched using the above criteria
     * If there is no match -1 is returned
     *
     * @param weighting an offset weighting, by defualt -1 should be used
     * @param src the src class being matched
     * @param dest the destination class to match to
     * @return a weighting where 0 would be an exact match, -1 would be no match and a positive integer that defines how close the match is
     */
    protected int getWeighting(int weighting, Class src, Class dest)
    {
        int x = weighting + 1;
        //If we are getting a match for object.class, we need to put it at the bottom of
        //the matching list
        if(dest.equals(Object.class))
        {
            return x + 3;
        }

        if (dest.equals(src))
        {
            return x;
        }
        else if (!dest.isAssignableFrom(src))
        {
            return -1;
        }
        else if (src.getInterfaces().length > 0)
        {
            for (int i = 0; i < src.getInterfaces().length; i++)
            {
                Class aClass = src.getInterfaces()[i];
                if (dest.equals(aClass))
                {
                    return x + 1;
                }
            }
            return x + 2;
        }
        else if (src.getSuperclass() != null)
        {
            return getWeighting(x, src.getSuperclass(), dest);

        }
        return -1;
    }

    public Class getInputClass()
    {
        return inputClass;
    }

    public int getInputWeighting()
    {
        return inputWeighting;
    }

    public Class getOutputClass()
    {
        return outputClass;
    }

    public int getOutputWeighting()
    {
        return outputWeighting;
    }

    public Transformer getTransformer()
    {
        return transformer;
    }

    public boolean isExactMatch()
    {
        return inputWeighting == 0 && outputWeighting == 0;
    }

    public boolean isNotMatch()
    {
        return inputWeighting == -1 || outputWeighting == -1;
    }

    public int compareTo(Object o)
    {
        TransformerWeighting weighting = (TransformerWeighting) o;
        if (weighting.getInputWeighting() == getInputWeighting() &&
                weighting.getOutputWeighting() == getOutputWeighting())
        {
            //We only check the weighting if we have an exact match
            //These transformers should always implement DiscoverableTransformer, but jic we check here
            if (weighting.getTransformer() instanceof DiscoverableTransformer
                    && this.getTransformer() instanceof DiscoverableTransformer)
            {
                int x = ((DiscoverableTransformer) weighting.getTransformer()).getPriorityWeighting();
                int y = ((DiscoverableTransformer) this.getTransformer()).getPriorityWeighting();
                if (x > y)
                {
                    return -1;
                }
                if (x < y)
                {
                    return 1;
                }
                return 0;
            }
            else
            {
                return 0;
            }
        }
        else
        {
            if (isNotMatch())
            {
                return -1;
            }
            else if (weighting.isNotMatch() && !isNotMatch())
            {
                return 1;
            }
            else if (weighting.isExactMatch() && !isExactMatch())
            {
                return -1;
            }
            else if (weighting.getInputWeighting() < getInputWeighting() &&
                    weighting.getOutputWeighting() < getOutputWeighting())
            {
                return -1;
            }
            //If the outputWeighting is closer to 0 its a better match
            else if (weighting.getInputWeighting() == getInputWeighting() &&
                    weighting.getOutputWeighting() < getOutputWeighting())
            {
                return -1;
            }

            else if (weighting.getInputWeighting() < getInputWeighting() &&
                    weighting.getOutputWeighting() == getOutputWeighting())
            {
                return -1;
            }
            return 1;
        }
    }

    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        TransformerWeighting that = (TransformerWeighting) o;

        if (inputClass != null ? !inputClass.equals(that.inputClass) : that.inputClass != null)
        {
            return false;
        }
        if (outputClass != null ? !outputClass.equals(that.outputClass) : that.outputClass != null)
        {
            return false;
        }

        return true;
    }

    public int hashCode()
    {
        int result;
        result = (transformer != null ? transformer.hashCode() : 0);
        result = 31 * result + inputWeighting;
        result = 31 * result + outputWeighting;
        result = 31 * result + (inputClass != null ? inputClass.hashCode() : 0);
        result = 31 * result + (outputClass != null ? outputClass.hashCode() : 0);
        return result;
    }


    public String toString()
    {
        final StringBuffer sb = new StringBuffer();
        sb.append("TransformerWeighting");
        sb.append("{inputClass=").append(inputClass);
        sb.append(", inputWeighting=").append(inputWeighting);
        sb.append(", outputClass=").append(outputClass);
        sb.append(", outputWeighting=").append(outputWeighting);
        sb.append(", transformer=").append(transformer.getName());
        sb.append('}');
        return sb.toString();
    }
}
