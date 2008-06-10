/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.util;

import org.mule.api.MuleContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Implements singleton pattern to allow different splash-screen implementations
 * following the concept of header, body, and footer. Header and footer are
 * reserved internally to Mule but body can be used to customize splash-screen
 * output. External code can e.g. hook into the start-up splash-screen as follows:
 * <pre><code>
 *   SplashScreen splashScreen = SplashScreen.getInstance(ServerStartupSplashScreen.class);
 *   splashScreen.addBody("Some extra text");
 * </code></pre>
 */
public abstract class SplashScreen
{
    protected List header = new ArrayList();
    protected List body = new ArrayList();
    protected List footer = new ArrayList();
    
    private static Log logger = LogFactory.getLog(SplashScreen.class);
    
    private static final Map instances = new HashMap();    
    
    public static synchronized SplashScreen getInstance(Class clazz)
    {
        SplashScreen splashScreen = (SplashScreen) instances.get(clazz);
        
        if (splashScreen == null)
        {
            try
            {
                splashScreen = (SplashScreen) clazz.newInstance();
                instances.put(clazz, splashScreen);
            }
            catch (Exception ignore)
            {
                logger.debug(ignore);
            }
        }
        
        return splashScreen;
    }

    public void setHeader(MuleContext context)
    {
        // default reserved for mule core info
    }   
    
    public void addBody(String line)
    {
        body.add(line);
    }

    public void setFooter(MuleContext context)
    {
        // default reserved for mule core info
    }    
    
    public String toString()
    {
        header.addAll(body);
        header.addAll(footer);
        return StringMessageUtils.getBoilerPlate(header, '*', 70);
    }
    
    protected SplashScreen()
    {
        // make sure no one else creates an instance
    }
}
