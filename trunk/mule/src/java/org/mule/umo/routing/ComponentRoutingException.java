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
package org.mule.umo.routing;

import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.UMOComponent;
import org.mule.umo.UMOMessage;
import org.mule.config.i18n.Message;
import org.mule.config.i18n.Messages;

/**
 * <code>ComponentRoutingException</code> is thrown due to a routing exception between
 * the endpoint the event was received on and the component receiving the event
 *
 * @author <a href="mailto:ross.mason@cubis.co.uk">Ross Mason</a>
 * @version $Revision$
 */
public class ComponentRoutingException extends RoutingException
{
    private transient UMOComponent component;
    public ComponentRoutingException(Message message, UMOMessage umoMessage, UMOEndpoint endpoint, UMOComponent component)
    {
        super(generateMessage(message, endpoint, component), umoMessage, endpoint);
        this.component = component;
    }

    public ComponentRoutingException(Message message, UMOMessage umoMessage, UMOEndpoint endpoint, UMOComponent component, Throwable cause)
    {
        super(generateMessage(message, endpoint, component), umoMessage, endpoint, cause);
        this.component = component;
    }

    public ComponentRoutingException(UMOMessage umoMessage, UMOEndpoint endpoint, UMOComponent component)
    {
        super(generateMessage(null, endpoint, component), umoMessage, endpoint);
        this.component = component;
    }

    public ComponentRoutingException(UMOMessage umoMessage, UMOEndpoint endpoint, UMOComponent component, Throwable cause)
    {
        super(generateMessage(null, endpoint, component), umoMessage, endpoint, cause);
        this.component = component;

    }

    private static Message generateMessage(Message message, UMOEndpoint endpoint, UMOComponent component) {

        Message m = new Message(Messages.COMPONENT_X_ROUTING_FAILED_ON_ENDPOINT_X, component.getDescriptor().getName(), endpoint.getEndpointURI());
        if(message!=null) {
            message.setNextMessage(m);
            return message;
        } else {
            return m;
        }
    }

    public UMOComponent getComponent()
    {
        return component;
    }
}
