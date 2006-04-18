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
package org.mule.impl.internal.admin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.MuleManager;
import org.mule.impl.internal.notifications.AdminNotificationListener;
import org.mule.impl.internal.notifications.ComponentNotificationListener;
import org.mule.impl.internal.notifications.ConnectionNotificationListener;
import org.mule.impl.internal.notifications.CustomNotificationListener;
import org.mule.impl.internal.notifications.ManagementNotificationListener;
import org.mule.impl.internal.notifications.ManagerNotificationListener;
import org.mule.impl.internal.notifications.MessageNotificationListener;
import org.mule.impl.internal.notifications.ModelNotificationListener;
import org.mule.impl.internal.notifications.NotificationException;
import org.mule.impl.internal.notifications.SecurityNotificationListener;
import org.mule.umo.UMOException;
import org.mule.umo.lifecycle.InitialisationException;
import org.mule.umo.manager.UMOAgent;
import org.mule.umo.manager.UMOManager;
import org.mule.umo.manager.UMOServerNotification;
import org.mule.umo.manager.UMOServerNotificationListener;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * <code>AbstractNotificationLoggerAgent</code> Receives Mule server notifications and logs
 * them and can optionally route them to an endpoint
 * 
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */
public abstract class AbstractNotificationLoggerAgent implements UMOAgent
{
    /**
     * The logger used for this class
     */
    protected transient Log logger = LogFactory.getLog(getClass());


    private String name;

    private boolean ignoreManagerNotifications = false;
    private boolean ignoreModelNotifications = false;
    private boolean ignoreComponentNotifications = false;
    private boolean ignoreConnectionNotifications = false;
    private boolean ignoreSecurityNotifications = false;
    private boolean ignoreManagementNotifications = false;
    private boolean ignoreCustomNotifications = false;
    private boolean ignoreAdminNotifications = false;
    private boolean ignoreMessageNotifications = false;

    private Set listeners = new HashSet();

    /**
     * Gets the name of this agent
     * 
     * @return the agent name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the name of this agent
     * 
     * @param name the name of the agent
     */
    public void setName(String name)
    {
        this.name = name;
    }

    public void start() throws UMOException
    {
        // nothing to do
    }

    public void stop() throws UMOException
    {
        // nothing to do
    }

    public void dispose()
    {
        // nothing to do
    }

    public void registered()
    {
        // nothing to do
    }

    public void unregistered()
    {
        for (Iterator iterator = listeners.iterator(); iterator.hasNext();) {
            UMOServerNotificationListener listener = (UMOServerNotificationListener) iterator.next();
            MuleManager.getInstance().unregisterListener(listener);
        }
    }

    public boolean isIgnoreManagerNotifications()
    {
        return ignoreManagerNotifications;
    }

    public void setIgnoreManagerNotifications(boolean ignoreManagerNotifications)
    {
        this.ignoreManagerNotifications = ignoreManagerNotifications;
    }

    public boolean isIgnoreModelNotifications()
    {
        return ignoreModelNotifications;
    }

    public void setIgnoreModelNotifications(boolean ignoreModelNotifications)
    {
        this.ignoreModelNotifications = ignoreModelNotifications;
    }

    public boolean isIgnoreComponentNotifications()
    {
        return ignoreComponentNotifications;
    }

    public void setIgnoreComponentNotifications(boolean ignoreComponentNotifications)
    {
        this.ignoreComponentNotifications = ignoreComponentNotifications;
    }

    public boolean isIgnoreSecurityNotifications()
    {
        return ignoreSecurityNotifications;
    }

    public void setIgnoreSecurityNotifications(boolean ignoreSecurityNotifications)
    {
        this.ignoreSecurityNotifications = ignoreSecurityNotifications;
    }

    public boolean isIgnoreManagementNotifications()
    {
        return ignoreManagementNotifications;
    }

    public void setIgnoreManagementNotifications(boolean ignoreManagementNotifications)
    {
        this.ignoreManagementNotifications = ignoreManagementNotifications;
    }

    public boolean isIgnoreCustomNotifications()
    {
        return ignoreCustomNotifications;
    }

    public void setIgnoreCustomNotifications(boolean ignoreCustomNotifications)
    {
        this.ignoreCustomNotifications = ignoreCustomNotifications;
    }

    public boolean isIgnoreAdminNotifications()
    {
        return ignoreAdminNotifications;
    }

    public void setIgnoreAdminNotifications(boolean ignoreAdminNotifications)
    {
        this.ignoreAdminNotifications = ignoreAdminNotifications;
    }

    public boolean isIgnoreConnectionNotifications()
    {
        return ignoreConnectionNotifications;
    }

    public void setIgnoreConnectionNotifications(boolean ignoreConnectionNotifications)
    {
        this.ignoreConnectionNotifications = ignoreConnectionNotifications;
    }

    public final void initialise() throws InitialisationException
    {
        doInitialise();
        UMOManager manager = MuleManager.getInstance();
        if (!ignoreManagerNotifications) {
            UMOServerNotificationListener l = new ManagerNotificationListener() {
                public void onNotification(UMOServerNotification notification)
                {
                    logEvent(notification);
                }
            };
            try {
                manager.registerListener(l);
            } catch (NotificationException e) {
                throw new InitialisationException(e, this);
            }
            listeners.add(l);
        }
        if (!ignoreModelNotifications) {
            UMOServerNotificationListener l = new ModelNotificationListener() {
                public void onNotification(UMOServerNotification notification)
                {
                    logEvent(notification);
                }
            };
            try {
                manager.registerListener(l);
            } catch (NotificationException e) {
                throw new InitialisationException(e, this);
            }
            listeners.add(l);
        }
        if (!ignoreComponentNotifications) {
            UMOServerNotificationListener l = new ComponentNotificationListener() {
                public void onNotification(UMOServerNotification notification)
                {
                    logEvent(notification);
                }
            };
            try {
                manager.registerListener(l);
            } catch (NotificationException e) {
                throw new InitialisationException(e, this);
            }
            listeners.add(l);
        }
        if (!ignoreSecurityNotifications) {
            UMOServerNotificationListener l = new SecurityNotificationListener() {
                public void onNotification(UMOServerNotification notification)
                {
                    logEvent(notification);
                }
            };
            try {
                manager.registerListener(l);
            } catch (NotificationException e) {
                throw new InitialisationException(e, this);
            }
            listeners.add(l);
        }

        if (!ignoreManagementNotifications) {
            UMOServerNotificationListener l = new ManagementNotificationListener() {
                public void onNotification(UMOServerNotification notification)
                {
                    logEvent(notification);
                }
            };
            try {
                manager.registerListener(l);
            } catch (NotificationException e) {
                throw new InitialisationException(e, this);
            }
            listeners.add(l);
        }

        if (!ignoreCustomNotifications) {
            UMOServerNotificationListener l = new CustomNotificationListener() {
                public void onNotification(UMOServerNotification notification)
                {
                    logEvent(notification);
                }
            };
            try {
                manager.registerListener(l);
            } catch (NotificationException e) {
                throw new InitialisationException(e, this);
            }
            listeners.add(l);
        }

        if (!ignoreConnectionNotifications) {
            UMOServerNotificationListener l = new ConnectionNotificationListener() {
                public void onNotification(UMOServerNotification notification)
                {
                    logEvent(notification);
                }
            };
            try {
                manager.registerListener(l);
            } catch (NotificationException e) {
                throw new InitialisationException(e, this);
            }
            listeners.add(l);
        }

        if (!ignoreAdminNotifications) {
            UMOServerNotificationListener l = new AdminNotificationListener() {
                public void onNotification(UMOServerNotification notification)
                {
                    logEvent(notification);
                }
            };
            try {
                manager.registerListener(l);
            } catch (NotificationException e) {
                throw new InitialisationException(e, this);
            }
            listeners.add(l);
        }

        if(!ignoreMessageNotifications && !MuleManager.getConfiguration().isEnableMessageEvents()) {
            logger.warn("EventLogger agent has been asked to log message notifications, but the MuleManager is configured not to fire Message notifications");
        } else if (!ignoreMessageNotifications) {
            UMOServerNotificationListener l = new MessageNotificationListener() {
                public void onNotification(UMOServerNotification notification)
                {
                    logEvent(notification);
                }
            };
            try {
                manager.registerListener(l);
            } catch (NotificationException e) {
                throw new InitialisationException(e, this);
            }
            listeners.add(l);
        }

    }

    protected abstract void doInitialise() throws InitialisationException;

    protected abstract void logEvent(UMOServerNotification e);
}
