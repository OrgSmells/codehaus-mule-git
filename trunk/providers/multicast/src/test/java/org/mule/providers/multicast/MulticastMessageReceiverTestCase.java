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
package org.mule.providers.multicast;

import com.mockobjects.dynamic.Mock;
import org.mule.impl.endpoint.MuleEndpoint;
import org.mule.providers.AbstractConnector;
import org.mule.tck.providers.AbstractMessageReceiverTestCase;
import org.mule.umo.UMOComponent;
import org.mule.umo.provider.UMOMessageReceiver;

/**
 * @author <a href="mailto:ross.mason@cubis.co.uk">Ross Mason</a>
 * @version $Revision$
 */

public class MulticastMessageReceiverTestCase extends AbstractMessageReceiverTestCase
{
    public UMOMessageReceiver getMessageReceiver() throws Exception
    {
        endpoint = new MuleEndpoint("multicast://228.2.3.4:10100", true);
        endpoint.setConnector(new MulticastConnector());
        Mock mockComponent = new Mock(UMOComponent.class);

        return new MulticastMessageReceiver((AbstractConnector) endpoint.getConnector(), (UMOComponent) mockComponent.proxy(), endpoint);
    }
}
