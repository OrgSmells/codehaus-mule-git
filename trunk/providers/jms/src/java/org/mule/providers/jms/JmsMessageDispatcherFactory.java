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
 *
 */
package org.mule.providers.jms;

import org.mule.umo.UMOException;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.endpoint.UMOImmutableEndpoint;
import org.mule.umo.provider.UMOConnector;
import org.mule.umo.provider.UMOMessageDispatcher;
import org.mule.umo.provider.UMOMessageDispatcherFactory;

/**
 * <code>JmsMessageDispatcherFactory</code> creates a message adapter that will dsend Jms messages
 *
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */
public class JmsMessageDispatcherFactory implements UMOMessageDispatcherFactory
{
    public UMOMessageDispatcher create(UMOImmutableEndpoint endpoint) throws UMOException
    {
        return new JmsMessageDispatcher( endpoint);
    }
}
