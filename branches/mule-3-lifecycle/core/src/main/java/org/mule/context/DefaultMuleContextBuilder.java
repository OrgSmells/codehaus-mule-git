/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.context;

import org.mule.DefaultMuleContext;
import org.mule.api.MuleContext;
import org.mule.api.config.MuleConfiguration;
import org.mule.api.config.ThreadingProfile;
import org.mule.api.context.MuleContextBuilder;
import org.mule.api.context.WorkManager;
import org.mule.api.context.notification.ConnectionNotificationListener;
import org.mule.api.context.notification.CustomNotificationListener;
import org.mule.api.context.notification.ExceptionNotificationListener;
import org.mule.api.context.notification.ManagementNotificationListener;
import org.mule.api.context.notification.ModelNotificationListener;
import org.mule.api.context.notification.MuleContextNotificationListener;
import org.mule.api.context.notification.RegistryNotificationListener;
import org.mule.api.context.notification.RoutingNotificationListener;
import org.mule.api.context.notification.SecurityNotificationListener;
import org.mule.api.context.notification.ServiceNotificationListener;
import org.mule.api.context.notification.TransactionNotificationListener;
import org.mule.api.lifecycle.LifecycleManager;
import org.mule.config.DefaultMuleConfiguration;
import org.mule.context.notification.ConnectionNotification;
import org.mule.context.notification.CustomNotification;
import org.mule.context.notification.ExceptionNotification;
import org.mule.context.notification.ManagementNotification;
import org.mule.context.notification.ModelNotification;
import org.mule.context.notification.MuleContextNotification;
import org.mule.context.notification.RegistryNotification;
import org.mule.context.notification.RoutingNotification;
import org.mule.context.notification.SecurityNotification;
import org.mule.context.notification.ServerNotificationManager;
import org.mule.context.notification.ServiceNotification;
import org.mule.context.notification.TransactionNotification;
import org.mule.lifecycle.DefaultLifecyclePair;
import org.mule.lifecycle.MuleContextLifecycleManager;
import org.mule.lifecycle.phases.MuleContextDisposePhase;
import org.mule.lifecycle.phases.MuleContextInitialisePhase;
import org.mule.lifecycle.phases.MuleContextStartPhase;
import org.mule.lifecycle.phases.MuleContextStopPhase;
import org.mule.util.ClassUtils;
import org.mule.util.SplashScreen;
import org.mule.work.DefaultWorkListener;
import org.mule.work.MuleWorkManager;

import javax.resource.spi.work.WorkListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Implementation of {@link MuleContextBuilder} that uses {@link DefaultMuleContext}
 * as the default {@link MuleContext} implementation and builds it with defaults
 * values for {@link MuleConfiguration}, {@link LifecycleManager}, {@link WorkManager}, 
 * {@link WorkListener} and {@link ServerNotificationManager}.
 */
public class DefaultMuleContextBuilder implements MuleContextBuilder
{
    protected static final Log logger = LogFactory.getLog(DefaultMuleContextBuilder.class);
    
    protected MuleConfiguration config;

    protected MuleContextLifecycleManager lifecycleManager;

    protected WorkManager workManager;

    protected WorkListener workListener;

    protected ServerNotificationManager notificationManager;

    protected SplashScreen startupScreen;

    protected SplashScreen shutdownScreen;

    /**
     * {@inheritDoc}
     */
    public MuleContext buildMuleContext()
    {
        logger.debug("Building new DefaultMuleContext instance with MuleContextBuilder: " + this);
        MuleContextLifecycleManager lifecycleManager = getLifecycleManager();
        DefaultMuleContext muleContext = new DefaultMuleContext(getMuleConfiguration(),
                                                         getWorkManager(),
                                                         getWorkListener(),
                                                         lifecycleManager,
                                                         getNotificationManager());
        lifecycleManager.setMuleContext(muleContext);
        muleContext.setSplash(startupScreen, shutdownScreen);
        return muleContext;
    }

    public void setMuleConfiguration(MuleConfiguration config)
    {
        this.config = config;
    }
    
    public void setWorkManager(WorkManager workManager)
    {
        this.workManager = workManager;
    }

    public void setWorkListener(WorkListener workListener)
    {
        this.workListener = workListener;
    }
    
    public void setNotificationManager(ServerNotificationManager notificationManager)
    {
        this.notificationManager = notificationManager;
    }

    public void setLifecycleManager(LifecycleManager lifecycleManager)
    {
        this.lifecycleManager = (MuleContextLifecycleManager)lifecycleManager;
    }
    
    protected MuleConfiguration getMuleConfiguration()
    {
        if (config != null)
        {
            return config;
        }
        else
        {
            return new DefaultMuleConfiguration();
        }
    }

    protected MuleContextLifecycleManager getLifecycleManager()
    {
        if (lifecycleManager != null)
        {
            return lifecycleManager;
        }
        else
        {
            MuleContextLifecycleManager lifecycleManager = new MuleContextLifecycleManager();
            //lifecycleManager.registerLifecycle(new NotInLifecyclePhase(), "c");
            lifecycleManager.registerLifecycle(new DefaultLifecyclePair(new MuleContextInitialisePhase(), new MuleContextDisposePhase()));
            lifecycleManager.registerLifecycle(new DefaultLifecyclePair(new MuleContextStartPhase(), new MuleContextStopPhase()));
            return lifecycleManager;
        }
    }

    protected WorkManager getWorkManager()
    {
        if (workManager != null)
        {
            return workManager;
        }
        else
        {
            return new MuleWorkManager(ThreadingProfile.DEFAULT_THREADING_PROFILE, "MuleServer", getMuleConfiguration().getShutdownTimeout());
        }
    }

    protected WorkListener getWorkListener()
    {
        if (workListener != null)
        {
            return workListener;
        }
        else
        {
            return new DefaultWorkListener();
        }
    }

    protected ServerNotificationManager getNotificationManager()
    {
        if (notificationManager != null)
        {
            return notificationManager;
        }
        else
        {
            ServerNotificationManager notificationManager = new ServerNotificationManager();
            notificationManager.addInterfaceToType(MuleContextNotificationListener.class,
                MuleContextNotification.class);
            notificationManager.addInterfaceToType(ModelNotificationListener.class, ModelNotification.class);
            notificationManager.addInterfaceToType(RoutingNotificationListener.class, RoutingNotification.class);
            notificationManager.addInterfaceToType(ServiceNotificationListener.class,
                ServiceNotification.class);
            notificationManager.addInterfaceToType(SecurityNotificationListener.class,
                SecurityNotification.class);
            notificationManager.addInterfaceToType(ManagementNotificationListener.class,
                ManagementNotification.class);
            notificationManager.addInterfaceToType(CustomNotificationListener.class, CustomNotification.class);
            notificationManager.addInterfaceToType(ConnectionNotificationListener.class,
                ConnectionNotification.class);
            notificationManager.addInterfaceToType(RegistryNotificationListener.class,
                RegistryNotification.class);
            notificationManager.addInterfaceToType(ExceptionNotificationListener.class,
                ExceptionNotification.class);
            notificationManager.addInterfaceToType(TransactionNotificationListener.class,
                TransactionNotification.class);
            return notificationManager;
        }
    }

    public SplashScreen getStartupScreen()
    {
        return startupScreen;
    }

    public void setStartupScreen(SplashScreen startupScreen)
    {
        this.startupScreen = startupScreen;
    }

    public SplashScreen getShutdownScreen()
    {
        return shutdownScreen;
    }

    public void setShutdownScreen(SplashScreen shutdownScreen)
    {
        this.shutdownScreen = shutdownScreen;
    }

    public String toString()
    {
        return ClassUtils.getClassName(getClass()) + 
            "{muleConfiguration=" + config +
            ", lifecycleManager=" + lifecycleManager + 
            ", workManager=" + workManager + 
            ", workListener=" + workListener + 
            ", notificationManager=" + notificationManager + "}";
    }
}
