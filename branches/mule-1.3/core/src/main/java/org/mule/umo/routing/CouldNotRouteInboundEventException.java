/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.umo.routing;

import org.mule.config.i18n.Message;
import org.mule.umo.UMOMessage;
import org.mule.umo.endpoint.UMOEndpoint;

/**
 * <code>CouldNotRouteInboundEventException</code> thrown if the current component
 * cannot accept the inbound event
 * 
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */

public class CouldNotRouteInboundEventException extends RoutingException
{
    /**
     * Serial version
     */
    private static final long serialVersionUID = 2736231899561051615L;

    public CouldNotRouteInboundEventException(UMOMessage message, UMOEndpoint endpoint)
    {
        super(message, endpoint);
    }

    public CouldNotRouteInboundEventException(UMOMessage umoMessage, UMOEndpoint endpoint, Throwable cause)
    {
        super(umoMessage, endpoint, cause);
    }

    public CouldNotRouteInboundEventException(Message message, UMOMessage umoMessage, UMOEndpoint endpoint)
    {
        super(message, umoMessage, endpoint);
    }

    public CouldNotRouteInboundEventException(Message message,
                                              UMOMessage umoMessage,
                                              UMOEndpoint endpoint,
                                              Throwable cause)
    {
        super(message, umoMessage, endpoint, cause);
    }
}
