/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.management;

import org.mule.api.context.notification.ServiceNotificationListener;
import org.mule.api.context.notification.CustomNotificationListener;
import org.mule.api.context.notification.ManagerNotificationListener;
import org.mule.api.context.notification.ModelNotificationListener;
import org.mule.api.context.notification.ServerNotification;
import org.mule.context.notification.ServiceNotification;
import org.mule.context.notification.CustomNotification;
import org.mule.context.notification.ManagerNotification;
import org.mule.context.notification.ModelNotification;
import org.mule.tck.AbstractMuleTestCase;
import org.mule.tck.testmodels.fruit.Apple;

import edu.emory.mathcs.backport.java.util.concurrent.CountDownLatch;
import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;
import edu.emory.mathcs.backport.java.util.concurrent.atomic.AtomicBoolean;
import edu.emory.mathcs.backport.java.util.concurrent.atomic.AtomicInteger;

public class ServerNotificationsTestCase extends AbstractMuleTestCase
        implements ModelNotificationListener, ManagerNotificationListener
{

    private final AtomicBoolean managerStopped = new AtomicBoolean(false);
    private final AtomicInteger managerStoppedEvents = new AtomicInteger(0);
    private final AtomicBoolean modelStopped = new AtomicBoolean(false);
    private final AtomicInteger componentStartedCount = new AtomicInteger(0);
    private final AtomicInteger customNotificationCount = new AtomicInteger(0);

    public ServerNotificationsTestCase()
    {
        setStartContext(true);
    }
    
    // @Override
    protected void doTearDown() throws Exception
    {
        managerStopped.set(true);
        managerStoppedEvents.set(0);
    }

    public void testStandardNotifications() throws Exception
    {
        muleContext.registerListener(this);
        muleContext.stop();
        assertTrue(modelStopped.get());
        assertTrue(managerStopped.get());
    }

    public void testMultipleRegistrations() throws Exception
    {
        muleContext.registerListener(this);
        muleContext.registerListener(this);
        muleContext.stop();
        assertTrue(managerStopped.get());
        assertEquals(2, managerStoppedEvents.get());
    }

    public void testUnregistering() throws Exception
    {
        muleContext.registerListener(this);
        muleContext.unregisterListener(this);
        muleContext.stop();
        // these should still be false because we unregistered ourselves
        assertFalse(modelStopped.get());
        assertFalse(managerStopped.get());
    }

    public void testMismatchingUnregistrations() throws Exception
    {
        // this has changed in 2.x.  now, unregistering removes all related entries
        muleContext.registerListener(this);
        DummyListener dummy = new DummyListener();
        muleContext.registerListener(dummy);
        muleContext.registerListener(dummy);
        muleContext.unregisterListener(dummy);
        muleContext.stop();

        assertTrue(managerStopped.get());
        assertEquals(1, managerStoppedEvents.get());
    }

    public void testStandardNotificationsWithSubscription() throws Exception
    {
        final CountDownLatch latch = new CountDownLatch(1);
        muleContext.registerListener(new ServiceNotificationListener()
        {
            public void onNotification(ServerNotification notification)
            {
                if (notification.getAction() == ServiceNotification.SERVICE_STARTED)
                {
                    componentStartedCount.incrementAndGet();
                    assertEquals("component1", notification.getResourceIdentifier());
                    latch.countDown();
                }
            }
        }, "component1");

        getTestService("component2", Apple.class);
        getTestService("component1", Apple.class);


        // Wait for the notifcation event to be fired as they are queued
        latch.await(20000, TimeUnit.MILLISECONDS);
        assertEquals(1, componentStartedCount.get());
    }

    public void testStandardNotificationsWithWildcardSubscription() throws Exception
    {
        final CountDownLatch latch = new CountDownLatch(2);

        muleContext.registerListener(new ServiceNotificationListener()
        {
            public void onNotification(ServerNotification notification)
            {
                if (notification.getAction() == ServiceNotification.SERVICE_STARTED)
                {
                    componentStartedCount.incrementAndGet();
                    assertFalse("noMatchComponent".equals(notification.getResourceIdentifier()));
                    latch.countDown();
                }
            }
        }, "component*");

        //Components automatically get registered
        getTestService("component2", Apple.class);
        getTestService("component1", Apple.class);
        getTestService("noMatchComponent", Apple.class);

        // Wait for the notifcation event to be fired as they are queued
        latch.await(2000, TimeUnit.MILLISECONDS);
        assertEquals(2, componentStartedCount.get());
    }

    public void testCustomNotifications() throws Exception
    {
        final CountDownLatch latch = new CountDownLatch(2);

        muleContext.registerListener(new DummyNotificationListener()
        {
            public void onNotification(ServerNotification notification)
            {
                if (notification.getAction() == DummyNotification.EVENT_RECEIVED)
                {
                    customNotificationCount.incrementAndGet();
                    assertEquals("hello", notification.getSource());
                    latch.countDown();
                }
            }
        });

        muleContext.fireNotification(new DummyNotification("hello", DummyNotification.EVENT_RECEIVED));
        muleContext.fireNotification(new DummyNotification("hello", DummyNotification.EVENT_RECEIVED));

        // Wait for the notifcation event to be fired as they are queued
        latch.await(2000, TimeUnit.MILLISECONDS);
        assertEquals(2, customNotificationCount.get());
    }

    public void testCustomNotificationsWithWildcardSubscription() throws Exception
    {

        final CountDownLatch latch = new CountDownLatch(2);

        muleContext.registerListener(new DummyNotificationListener()
        {
            public void onNotification(ServerNotification notification)
            {
                if (notification.getAction() == DummyNotification.EVENT_RECEIVED)
                {
                    customNotificationCount.incrementAndGet();
                    assertFalse("e quick bro".equals(notification.getResourceIdentifier()));
                    latch.countDown();
                }
            }
        }, "* quick brown*");

        muleContext.fireNotification(new DummyNotification("the quick brown fox jumped over the lazy dog",
                                                                 DummyNotification.EVENT_RECEIVED));
        muleContext.fireNotification(new DummyNotification("e quick bro", DummyNotification.EVENT_RECEIVED));
        muleContext.fireNotification(new DummyNotification(" quick brown", DummyNotification.EVENT_RECEIVED));

        // Wait for the notifcation event to be fired as they are queued
        latch.await(20000, TimeUnit.MILLISECONDS);
        assertEquals(2, customNotificationCount.get());
    }

    public void onNotification(ServerNotification notification)
    {
        if (notification.getAction() == ModelNotification.MODEL_STOPPED)
        {
            modelStopped.set(true);
        }
        else
        {
            if (notification.getAction() == ManagerNotification.MANAGER_STOPPED)
            {
                managerStopped.set(true);
                managerStoppedEvents.incrementAndGet();
            }
        }
    }

    public static interface DummyNotificationListener extends CustomNotificationListener
    {
        // no methods
    }

    public class DummyNotification extends CustomNotification
    {
        /**
         * Serial version
         */
        private static final long serialVersionUID = -1117307108932589331L;

        public static final int EVENT_RECEIVED = -999999;

        public DummyNotification(String message, int action)
        {
            super(message, action);
            resourceIdentifier = message;
        }
    }
}
