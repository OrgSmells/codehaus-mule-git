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
 */
package org.mule.routing.outbound;

import EDU.oswego.cs.dl.util.concurrent.CopyOnWriteArrayList;
import org.mule.impl.MuleMessage;
import org.mule.umo.UMOMessage;
import org.mule.umo.endpoint.UMOEndpoint;

import java.util.List;
import java.util.Map;

/**
 * <code>FilteringListMessageSplitter</code> Accepts a List as a message payload
 * then routes list elements as messages over an endpoint where the endpoint's filter
 * accepts the payload.
 *
 * @author <a href="mailto:ross.mason@cubis.co.uk">Ross Mason</a>
 * @version $Revision$
 */
public class FilteringListMessageSplitter extends AbstractMessageSplitter
{
    private CopyOnWriteArrayList payload;
    private Map properties;
    /**
     * Template method can be used to split the message up before the getMessagePart
     * method is called .
     *
     * @param message the message being routed
     */
    protected void initialise(UMOMessage message)
    {
        if(message.getPayload() instanceof List) {
            //get a synchronised cloned list
            payload = new CopyOnWriteArrayList((List)message.getPayload());
        } else {
            throw new IllegalArgumentException("The payload for this router must be of type java.util.list");
        }
        //Cache the properties here because for some message types getting the properties
        //can be expensive
        properties = message.getProperties();
    }

    /**
     * Retrieves a specific message part for the given endpoint. the message will then
     * be routed via the parovider.
     *
     * @param message  the current message being processed
     * @param endpoint the endpoint that will be used to route the resulting message
     *                 part
     * @return the message part to dispatch
     */
    protected UMOMessage getMessagePart(UMOMessage message, UMOEndpoint endpoint)
    {
        Object object;
        UMOMessage result = null;
        for (int i=0; i< payload.size(); i++)
        {
            object = payload.get(i);
            //If there is no filter assume that the endpoint can accept the message.
            //Endpoints will be processed in order to only the last (if any) of the
            //the endpoints may not have a filter
            if(endpoint.getFilter()==null) {
                if(logger.isDebugEnabled()) {
                    logger.debug("There is not filter configured on: " + endpoint.getEndpointURI().toString() + ". Using endpoint");
                }
                result = new MuleMessage(object, properties);
                payload.remove(i);
                break;
            } else if(endpoint.getFilter().accept(object)) {
                if(logger.isDebugEnabled()) {
                    logger.debug("Endpoint filter matched. Routing message over: " + endpoint.getEndpointURI().toString());
                }
                result = new MuleMessage(object, properties);
                payload.remove(i);
                break;
            }
        }
        return result;
    }
}
