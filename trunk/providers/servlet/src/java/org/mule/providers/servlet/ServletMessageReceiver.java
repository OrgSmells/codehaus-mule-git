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
package org.mule.providers.servlet;

import org.mule.umo.lifecycle.InitialisationException;
import org.mule.providers.AbstractMessageReceiver;
import org.mule.umo.UMOComponent;
import org.mule.umo.lifecycle.InitialisationException;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.provider.UMOConnector;

/**
 * <code>ServletMessageReceiver</code> is a receiver that is invoked from a
 * Servlet when an event is received.
 *
 * There is a one-to-one mapping between a ServletMessageReceiver and a
 * servlet in the serving webapp.
 *
 * @author <a href="mailto:ross.mason@cubis.co.uk">Ross Mason</a>
 * @version $Revision$
 */

public class ServletMessageReceiver extends AbstractMessageReceiver
{
     public ServletMessageReceiver(UMOConnector connector,
                        UMOComponent component,
                        UMOEndpoint endpoint) throws InitialisationException
     {
        create(connector, component, endpoint);
    }
}
