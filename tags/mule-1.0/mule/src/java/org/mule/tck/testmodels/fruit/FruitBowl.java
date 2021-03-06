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
 *
 */

package org.mule.tck.testmodels.fruit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class FruitBowl
{
    /**
     * logger used by this class
     */
    private static transient Log logger = LogFactory.getLog(FruitBowl.class);

    private Map bowl = new HashMap();

    public FruitBowl()
    {
    }

    public FruitBowl(Fruit fruit[])
    {
        for (int i = 0; i < fruit.length; i++)
        {
            bowl.put(fruit[i].getClass(), fruit[i]);
        }
    }

    public FruitBowl(Apple apple, Banana banana)
    {
        bowl.put(apple.getClass(), apple);
        bowl.put(banana.getClass(), banana);
    }

    public boolean hasApple()
    {
        return bowl.get(Apple.class) != null;
    }

    public boolean hasBanana()
    {
        return bowl.get(Banana.class) != null;
    }

    public Object consumeFruit(FruitLover fruitlover)
    {
        logger.debug("Got a fruit lover who says: " + fruitlover.speak());
        for (Iterator iter = bowl.values().iterator(); iter.hasNext();)
        {
            ((Fruit) iter.next()).bite();
        }
        return fruitlover;
    }

    public void setFruit(Fruit[] fruit)
    {
        for (int i = 0; i < fruit.length; i++)
        {
            bowl.put(fruit[i].getClass(), fruit[i]);
        }
    }

    public Apple getApple()
    {
        return (Apple) bowl.get(Apple.class);
    }

    public void setApple(Apple apple)
    {
        bowl.put(apple.getClass(), apple);
    }

    public Banana getBanana()
    {
        return (Banana) bowl.get(Banana.class);
    }

    public void setBanana(Banana banana)
    {
        bowl.put(banana.getClass(), banana);
    }
}