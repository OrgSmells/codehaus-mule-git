/* 
 * $Header$
 * $Revision$
 * $Date$
 * ------------------------------------------------------------------------------------------------------
 * 
 * Copyright (c) Cubis Limited. All rights reserved.
 * http://www.cubis.co.uk 
 * 
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file. 
 *
 */
package org.mule.umo;

import org.mule.MuleException;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.routing.UMOInboundMessageRouter;
import org.mule.umo.routing.UMOOutboundMessageRouter;
import org.mule.umo.routing.UMOResponseMessageRouter;
import org.mule.umo.transformer.UMOTransformer;

import java.util.HashMap;
import java.util.List;
import java.beans.ExceptionListener;

/**
 * <code>UMODescriptor</code> describes all the properties for a Mule UMO.  New Mule
 * Managed components can be initialised as needed from their descriptor.
 *
 * @author <a href="mailto:ross.mason@cubis.co.uk">Ross Mason</a>
 * @version $Revision$
 */
public interface UMODescriptor extends UMOImmutableDescriptor
{
    /**
     * Interceptors are executable objects that can be chained together.
     * Interceptors are executed in the order they are added, for example
     * if INTERCEPTOR_1 is added and then INTERCEPTOR_2 is added to UMO_A the execution order
     * will be: INTERCEPTOR_1 -> INTERCEPTOR_2 -> UMO_A.
     *
     * @param interceptor the interceptor to add.
     */
    public void addInterceptor(UMOInterceptor interceptor);

    /**
     * Interceptors are executable objects that can be chained together.
     * Interceptors are executed in the order they are added, for example
     * if INTERCEPTOR_1 is added and then INTERCEPTOR_2 is added to UMO_A the execution order
     * will be: INTERCEPTOR_1 -> INTERCEPTOR_2 -> UMO_A.
     *
     * @param interceptorList A list of interceptors to associate.
     */
    public void setInterceptors(List interceptorList);

    /**
     * The exception strategy to use to handle exceptions in the Mule UMO.
     *
     * @param listener the exception strategy to use. If none has been set or argument is null a default
     */
    void setExceptionListener(ExceptionListener listener);

    /**
     * The inbound endpointUri to use when receiveing an event.
     *
     * @param endpoint the inbound endpoint to use
     * @throws MuleException if the Provider is not valid i.e. the proivder
     *                       is not a receiver
     * @see org.mule.umo.endpoint.UMOEndpoint
     */
     public void setInboundEndpoint(UMOEndpoint endpoint) throws MuleException;


    /**
     * sets the identifier for the Mule UMO created from the descriptor
     * @param newName the identifier for the Mule UMO created from the descriptor
     */
    void setName(String newName);

    /**
     * The outbound Provider to use when sending an event.
     *
     * @param endpoint the outbound endpoint to use
     * @throws MuleException if the Provider is not valid i.e. the proivder
     *                       is a receiver
     * @see UMOEndpoint
     */
     public void setOutboundEndpoint(UMOEndpoint endpoint) throws MuleException;

    /**
     * @param props the properties for the descriptor.  These will be passed to the
     *              UMO when it's initialise method is called or set as bean properties whe the UMO is created
     */
    void setProperties(HashMap props);

    /**
     * The version on the Mule UMO.  This is currently not used by the mule run-time but may
     * be used in future.
     *
     * @param ver the version of the Mule descriptor
     */
    void setVersion(String ver);

    /**
     * The String used to instanciate create the object, this can be a FQ class name or a reference
     * to an object in a configured container
     *
     * @param reference The String object reference
     */
    public void setImplementation(Object reference);

    /**
     * Inbound Routers control how events are received by a component. If no
     * router is set. A default will be used that uses the inboundProvider
     * set on his descriptor.
     * @param router the inbound router for this component
     * @see UMOInboundMessageRouter
     */
    public void setInboundRouter(UMOInboundMessageRouter router);

    /**
     * Outbound Routers control how events are published by a component once.
     * the event has been processed.
     * If no router is set. A default will be used that uses the outboundProvider
     * set on his descriptor to route the event.
     * @param router the outbound router for this component
     * @see UMOOutboundMessageRouter
     */
    public void setOutboundRouter(UMOOutboundMessageRouter router);

    /**
     * Response Routers control how events are returned in a request/response call.
     * It cn be use to aggregate response events before returning, thus acting as a
     * Join in a forked process.  This can be used to make request/response calls a lot
     * more efficient as independent tasks can be forked, execute concurrently and then
     * join before the request completes
     *
     * @param router the response router for this component
     * @see org.mule.umo.routing.UMOResponseMessageRouter
     */
    public void setResponseRouter(UMOResponseMessageRouter router);

    /**
     * @param transformer the transformer to use.
     * @see UMOTransformer
     * @see org.mule.transformers.AbstractTransformer
     */
    public void setInboundTransformer(UMOTransformer transformer);

    /**
     * The transformer to use when sending events or data.
     *
     * @param transformer the transformer to use.
     * @see UMOTransformer
     * @see org.mule.transformers.AbstractTransformer
     */
    public void setOutboundTransformer(UMOTransformer transformer);

}