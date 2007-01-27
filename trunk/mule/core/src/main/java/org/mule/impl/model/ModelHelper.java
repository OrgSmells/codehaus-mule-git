/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.impl.model;

import org.mule.MuleManager;
import org.mule.umo.model.UMOModel;
import org.mule.umo.UMOComponent;
import org.mule.umo.UMODescriptor;
import org.mule.umo.UMOException;

import java.util.Iterator;

/**
 * @deprecated This functionality should be moved to the registry
 */
public class ModelHelper
{
    public static final String SYSTEM_MODEL = "_system";

    public static boolean isComponentRegistered(String name)
    {
        for (Iterator iterator = MuleManager.getInstance().getModels().values().iterator(); iterator.hasNext();)
        {
            UMOModel m =  (UMOModel)iterator.next();
            if(m.isComponentRegistered(name))
            {
                return true;
            }
        }
        return false;
    }

    public static UMOComponent getComponent(String name)
    {
        for (Iterator iterator = MuleManager.getInstance().getModels().values().iterator(); iterator.hasNext();)
        {
            UMOModel m =  (UMOModel)iterator.next();
            if(m.isComponentRegistered(name))
            {
                return m.getComponent(name);
            }
        }
        return null;
    }

    public static UMODescriptor getDescriptor(String name)
    {
        for (Iterator iterator = MuleManager.getInstance().getModels().values().iterator(); iterator.hasNext();)
        {
            UMOModel m =  (UMOModel)iterator.next();
            if(m.isComponentRegistered(name))
            {
                return m.getDescriptor(name);
            }
        }
        return null;
    }

    //TODO RM*: Move this method
    public static void registerSystemComponent(UMODescriptor d) throws UMOException
    {
        UMOModel model = MuleManager.getInstance().lookupModel(SYSTEM_MODEL);
        if(model==null)
        {
            model = ModelFactory.createModel("seda");
            model.setName(SYSTEM_MODEL);
            MuleManager.getInstance().registerModel(model);
        }
        model.registerComponent(d);
    }
}
