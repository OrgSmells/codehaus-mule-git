/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.impl.lifecycle;

import org.mule.MuleRuntimeException;
import org.mule.config.i18n.CoreMessages;
import org.mule.impl.internal.notifications.ManagerNotification;
import org.mule.umo.UMOManagementContext;
import org.mule.umo.manager.UMOServerNotification;
import org.mule.util.ClassUtils;

import java.lang.reflect.Constructor;

/**
 * TODO
 */
public class NotificationLifecycleObject extends LifecycleObject
{
    private String preNotificationName;
    private String postNotificationName;
    private Constructor ctor;

    public NotificationLifecycleObject(Class type)
    {
        super(type);
    }

    public NotificationLifecycleObject(Class type, Class notificationClass)
    {
        super(type);

        if (notificationClass==null)
        {
            throw new IllegalArgumentException(CoreMessages.objectIsNull("notificationClass").toString());
        }

        // MULE-2903: make sure the notifiactionClass is properly loaded and initialized
        notificationClass = ClassUtils.initializeClass(notificationClass);

        if (!UMOServerNotification.class.isAssignableFrom(notificationClass))
        {
            throw new IllegalArgumentException("Notification class must be of type: " + UMOServerNotification.class.getName());
        }

        ctor = ClassUtils.getConstructor(notificationClass, new Class[]{Object.class, String.class});
        if(ctor==null)
        {
            throw new IllegalArgumentException("No constructor defined in Notification class: " + notificationClass + " with arguments (Object.class, String.class)");
        }
    }

    public NotificationLifecycleObject(Class type, Class notificationClass, int preNotification, int postNotification)
    {
        this(type, notificationClass);
        setPreNotificationName(ManagerNotification.getActionName(preNotification));
        setPostNotificationName(ManagerNotification.getActionName(postNotification));
    }

    public String getPostNotificationName()
    {
        return postNotificationName;
    }

    public void setPostNotificationName(String postNotificationName)
    {
        this.postNotificationName = postNotificationName;
    }

    public String getPreNotificationName()
    {
        return preNotificationName;
    }

    public void setPreNotificationName(String preNotificationName)
    {
        this.preNotificationName = preNotificationName;
    }

    //@java.lang.Override
    public void firePreNotification(UMOManagementContext context)
    {
        if(getPreNotificationName()!=null)
        {
            setPreNotification(createNotification(context, getPreNotificationName()));
        }
        super.firePreNotification(context);

    }

    //@java.lang.Override
    public void firePostNotification(UMOManagementContext context)
    {
        if(getPostNotificationName()!=null)
        {
            setPostNotification(createNotification(context, getPostNotificationName()));
        }
        super.firePostNotification(context);
    }

    protected UMOServerNotification createNotification(UMOManagementContext context, String action)
    {
        try
        {
            return (UMOServerNotification)ctor.newInstance(new Object[]{context, action});
        }
        catch (Exception e)
        {
            throw new MuleRuntimeException(CoreMessages.failedToCreate("Notification:" + action) ,e);
        }
    }
}