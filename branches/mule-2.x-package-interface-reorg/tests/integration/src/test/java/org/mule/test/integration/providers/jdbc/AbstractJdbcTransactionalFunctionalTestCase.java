/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.test.integration.providers.jdbc;


import org.mule.api.EventContext;
import org.mule.api.component.Component;
import org.mule.api.context.ServerNotification;
import org.mule.api.endpoint.EndpointBuilder;
import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.api.notification.TransactionNotificationListener;
import org.mule.api.transaction.Transaction;
import org.mule.api.transaction.TransactionConfig;
import org.mule.api.transaction.TransactionFactory;
import org.mule.impl.DefaultExceptionStrategy;
import org.mule.impl.MuleTransactionConfig;
import org.mule.impl.endpoint.EndpointURIEndpointBuilder;
import org.mule.impl.internal.notifications.TransactionNotification;
import org.mule.impl.model.seda.SedaComponent;
import org.mule.impl.routing.inbound.DefaultInboundRouterCollection;
import org.mule.impl.routing.outbound.OutboundPassThroughRouter;
import org.mule.impl.routing.outbound.DefaultOutboundRouterCollection;
import org.mule.tck.functional.EventCallback;
import org.mule.util.object.PrototypeObjectFactory;

import java.util.HashMap;

import edu.emory.mathcs.backport.java.util.concurrent.atomic.AtomicBoolean;

public abstract class AbstractJdbcTransactionalFunctionalTestCase extends AbstractJdbcFunctionalTestCase  implements TransactionNotificationListener
{

    private Transaction currentTx;
    protected boolean rollbacked = false;

    protected void doSetUp() throws Exception
    {
        super.doSetUp();
        muleContext.registerListener(this);
        currentTx = null;
    }

    public void testReceiveAndSendWithException() throws Exception
    {
        final AtomicBoolean called = new AtomicBoolean(false);

        EventCallback callback = new EventCallback()
        {
            public void eventReceived(EventContext context, Object component) throws Exception
            {
                try
                {
                    called.set(true);
                    currentTx = context.getCurrentTransaction();
                    assertNotNull(currentTx);
                    assertTrue(currentTx.isBegun());
                    currentTx.setRollbackOnly();
                }
                finally
                {
                    synchronized (called)
                    {
                        called.notifyAll();
                    }
                }
            }
        };

        // Start the server
        initialiseComponent(TransactionConfig.ACTION_ALWAYS_BEGIN, callback);
        muleContext.start();

        execSqlUpdate("INSERT INTO TEST(TYPE, DATA, ACK, RESULT) VALUES (1, '" + DEFAULT_MESSAGE
                      + "', NULL, NULL)");

        synchronized (called)
        {
            called.wait(20000);
        }
        assertTrue(called.get());

        Thread.sleep(1000);

        assertTrue(rollbacked);

        Object[] obj = execSqlQuery("SELECT COUNT(*) FROM TEST WHERE TYPE = 2");
        assertNotNull(obj);
        assertEquals(1, obj.length);
        assertEquals(new Integer(0), obj[0]);
        obj = execSqlQuery("SELECT ACK FROM TEST WHERE TYPE = 1");
        assertNotNull(obj);
        assertEquals(1, obj.length);
        assertNull(obj[0]);
    }

    public Component initialiseComponent(byte txBeginAction, EventCallback callback) throws Exception
    {

        Component component = new SedaComponent();
        component.setExceptionListener(new DefaultExceptionStrategy());
        component.setName("testComponent");
        component.setServiceFactory(new PrototypeObjectFactory(JdbcFunctionalTestComponent.class));

        TransactionFactory tf = getTransactionFactory();
        TransactionConfig txConfig = new MuleTransactionConfig();
        txConfig.setFactory(tf);
        txConfig.setAction(txBeginAction);
        
        EndpointBuilder endpointBuilder = new EndpointURIEndpointBuilder(getInDest(), muleContext);
        endpointBuilder.setName("testIn");
        endpointBuilder.setConnector(connector);
        endpointBuilder.setTransactionConfig(txConfig);
        ImmutableEndpoint endpoint = muleContext.getRegistry().lookupEndpointFactory().getInboundEndpoint(
            endpointBuilder);

        EndpointBuilder endpointBuilder2 = new EndpointURIEndpointBuilder(getOutDest(), muleContext);
        endpointBuilder2.setName("testOut");
        endpointBuilder2.setConnector(connector);
        ImmutableEndpoint outProvider = muleContext.getRegistry().lookupEndpointFactory().getOutboundEndpoint(
            endpointBuilder2);
        
        component.setOutboundRouter(new DefaultOutboundRouterCollection());
        OutboundPassThroughRouter router = new OutboundPassThroughRouter();
        router.addEndpoint(outProvider);
        component.getOutboundRouter().addRouter(router);
        component.setInboundRouter(new DefaultInboundRouterCollection());
        component.getInboundRouter().addEndpoint(endpoint);

        HashMap props = new HashMap();
        props.put("eventCallback", callback);
        component.setProperties(props);
        component.setModel(model);
        muleContext.getRegistry().registerComponent(component);
        return component;
    }

    public void onNotification(ServerNotification notification)
    {
        if (notification.getAction() == TransactionNotification.TRANSACTION_ROLLEDBACK)
        {
            this.rollbacked = true;
        }
    }

    abstract protected TransactionFactory getTransactionFactory();

}
