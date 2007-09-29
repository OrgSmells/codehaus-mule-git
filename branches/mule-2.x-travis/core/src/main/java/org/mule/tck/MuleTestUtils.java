/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.tck;

import org.mule.impl.ManagementContext;
import org.mule.impl.MuleEvent;
import org.mule.impl.MuleMessage;
import org.mule.impl.MuleSession;
import org.mule.impl.RequestContext;
import org.mule.impl.endpoint.MuleEndpoint;
import org.mule.impl.endpoint.MuleEndpointURI;
import org.mule.impl.model.seda.SedaComponent;
import org.mule.impl.model.seda.SedaModel;
import org.mule.providers.AbstractConnector;
import org.mule.routing.outbound.OutboundPassThroughRouter;
import org.mule.tck.testmodels.fruit.Apple;
import org.mule.tck.testmodels.mule.TestAgent;
import org.mule.tck.testmodels.mule.TestCompressionTransformer;
import org.mule.tck.testmodels.mule.TestConnector;
import org.mule.umo.UMOComponent;
import org.mule.umo.UMODescriptor;
import org.mule.umo.UMOEvent;
import org.mule.umo.UMOEventContext;
import org.mule.umo.UMOManagementContext;
import org.mule.umo.UMOSession;
import org.mule.umo.UMOTransaction;
import org.mule.umo.UMOTransactionFactory;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.endpoint.UMOEndpointURI;
import org.mule.umo.endpoint.UMOImmutableEndpoint;
import org.mule.umo.provider.UMOConnector;
import org.mule.umo.provider.UMOMessageDispatcher;
import org.mule.umo.provider.UMOMessageDispatcherFactory;
import org.mule.umo.routing.UMOOutboundRouter;
import org.mule.umo.transformer.UMOTransformer;
import org.mule.util.ClassUtils;
import org.mule.util.object.ObjectFactory;
import org.mule.util.object.SimpleObjectFactory;

import com.mockobjects.dynamic.Mock;

import java.util.HashMap;
import java.util.Map;

/**
 * Utilities for creating test and Mock Mule objects
 */
public final class MuleTestUtils
{
    public static UMOEndpoint getTestEndpoint(String name, String type, UMOManagementContext context) throws Exception
    {
        Map props = new HashMap();
        props.put("name", name);
        props.put("type", type);
        props.put("endpointURI", new MuleEndpointURI("test://test"));
        props.put("connector", "testConnector");
        MuleEndpoint endpoint = new MuleEndpoint();
        // need to build endpoint this way to avoid depenency to any endpoint jars
        AbstractConnector connector = null;
        connector = (AbstractConnector)ClassUtils.loadClass("org.mule.tck.testmodels.mule.TestConnector",
            AbstractMuleTestCase.class).newInstance();

        connector.setName("testConnector");
        connector.setManagementContext(context);
        context.applyLifecycle(connector);

        endpoint.setConnector(connector);
        endpoint.setEndpointURI(new MuleEndpointURI("test://test"));
        endpoint.setName(name);
        endpoint.setType(type);
        endpoint.setManagementContext(context);
        context.applyLifecycle(endpoint);
        return endpoint;
    }
    
    public static UMOEndpoint getTestSchemeMetaInfoEndpoint(String name, String type, String protocol, UMOManagementContext context)
        throws Exception
    {
        UMOEndpoint endpoint = new MuleEndpoint();
        // need to build endpoint this way to avoid depenency to any endpoint jars
        AbstractConnector connector = null;
        connector = (AbstractConnector) ClassUtils.loadClass("org.mule.tck.testmodels.mule.TestConnector",
            AbstractMuleTestCase.class).newInstance();

        connector.setName("testConnector");
        connector.setManagementContext(context);
        context.applyLifecycle(connector);
        connector.registerSupportedProtocol(protocol);
        
        endpoint.setConnector(connector);
        endpoint.setEndpointURI(new MuleEndpointURI("test:" + protocol + "://test"));
        endpoint.setName(name);
        endpoint.setType(type);
        return endpoint;
    }

    /** Supply no component, no endpoint */
    public static UMOEvent getTestEvent(Object data, UMOManagementContext context) throws Exception
    {
        return getTestEvent(data, getTestComponent(context), context);
    }

    /** Supply component but no endpoint */
    public static UMOEvent getTestEvent(Object data, UMOComponent component, UMOManagementContext context) throws Exception
    {
        return getTestEvent(data, component, getTestEndpoint("test1", UMOEndpoint.ENDPOINT_TYPE_SENDER, context), context);
    }

    /** Supply endpoint but no component */
    public static UMOEvent getTestEvent(Object data, UMOImmutableEndpoint endpoint, UMOManagementContext context) throws Exception
    {
        return getTestEvent(data, getTestComponent(context), endpoint, context);
    }

    public static UMOEvent getTestEvent(Object data, UMOComponent component, UMOImmutableEndpoint endpoint, UMOManagementContext context) throws Exception
    {
        UMOSession session = getTestSession(component);
        return new MuleEvent(new MuleMessage(data, new HashMap()), endpoint, session, true);
    }

    public static UMOEventContext getTestEventContext(Object data, UMOManagementContext context) throws Exception
    {
        try
        {
            UMOEvent event = getTestEvent(data, context);
            RequestContext.setEvent(event);
            return RequestContext.getEventContext();
        }
        finally
        {
            RequestContext.setEvent(null);
        }
    }

    public static UMOTransformer getTestTransformer() throws Exception
    {
        UMOTransformer t = new TestCompressionTransformer();
        t.initialise();
        return t;
    }

    public static UMOSession getTestSession(UMOComponent component)
    {
        return new MuleSession(component);
    }

    public static UMOSession getTestSession()
    {
        return getTestSession(null);
    }

    public static TestConnector getTestConnector(UMOManagementContext context) throws Exception
    {
        TestConnector testConnector = new TestConnector();
        testConnector.setName("testConnector");
        testConnector.setManagementContext(context);
        context.applyLifecycle(testConnector);
        return testConnector;
    }

    public static UMOComponent getTestComponent(UMOManagementContext context) throws Exception
    {
        return getTestComponent("appleService", Apple.class, context);
    }

    public static UMOComponent getTestComponent(String name, Class clazz, UMOManagementContext context) throws Exception
    {
        return getTestComponent(name, clazz, null, context);
    }
    
    public static UMOComponent getTestComponent(String name, Class clazz, Map props, UMOManagementContext context) throws Exception
    {
        SedaModel model = new SedaModel();
        model.setManagementContext(context);
        context.applyLifecycle(model);
        
        UMOComponent c = new SedaComponent();
        c.setName(name);
        ObjectFactory of = new SimpleObjectFactory(clazz, props);
        of.initialise();
        c.setServiceFactory(of);
        c.setModel(model);

        c.setManagementContext(context);
        context.applyLifecycle(c);

        UMOOutboundRouter router = new OutboundPassThroughRouter();
        router.addEndpoint(getTestEndpoint("test1", UMOEndpoint.ENDPOINT_TYPE_SENDER, context));
        c.getOutboundRouter().addRouter(router);        
        return c;
    }

//    public static MuleDescriptor getTestDescriptor(String name, String implementation, UMOManagementContext context) throws Exception
//    {
//        MuleDescriptor descriptor = new MuleDescriptor();
//        descriptor.setExceptionListener(new DefaultExceptionStrategy());
//        descriptor.setName(name);
//        //descriptor.setServiceFactory(new SimpleObjectFactory(implementation));
//        UMOOutboundRouter router = new OutboundPassThroughRouter();
//        router.addEndpoint(getTestEndpoint("test1", UMOEndpoint.ENDPOINT_TYPE_SENDER, context));
//        descriptor.getOutboundRouter().addRouter(router);
//        descriptor.setManagementContext(context);
//        descriptor.initialise();
//
//        return descriptor;
//    }
//
//    public static MuleDescriptor getTestDescriptor(UMOManagementContext context) throws Exception
//    {
//        return getTestDescriptor("appleService", Apple.class.getName(), context);
//    }

    public static TestAgent getTestAgent() throws Exception
    {
        TestAgent t = new TestAgent();
        t.initialise();
        return t;
    }

    public static Mock getMockSession()
    {
        return new Mock(UMOSession.class, "umoSession");
    }

    public static Mock getMockMessageDispatcher()
    {
        return new Mock(UMOMessageDispatcher.class, "umoMessageDispatcher");
    }

    public static Mock getMockMessageDispatcherFactory()
    {
        return new Mock(UMOMessageDispatcherFactory.class, "umoMessageDispatcherFactory");
    }

    public static Mock getMockConnector()
    {
        return new Mock(UMOConnector.class, "umoConnector");
    }

    public static Mock getMockEvent()
    {
        return new Mock(UMOEvent.class, "umoEvent");
    }

    public static Mock getMockManagementContext()
    {
        return new Mock(ManagementContext.class, "muleManagementContext");
    }

    public static Mock getMockEndpoint()
    {
        return new Mock(UMOEndpoint.class, "umoEndpoint");
    }

    public static Mock getMockEndpointURI()
    {
        return new Mock(UMOEndpointURI.class, "umoEndpointUri");
    }

    public static Mock getMockDescriptor()
    {
        return new Mock(UMODescriptor.class, "umoDescriptor");
    }

    public static Mock getMockTransaction()
    {
        return new Mock(UMOTransaction.class, "umoTransaction");
    }

    public static Mock getMockTransactionFactory()
    {
        return new Mock(UMOTransactionFactory.class, "umoTransactionFactory");
    }
}
