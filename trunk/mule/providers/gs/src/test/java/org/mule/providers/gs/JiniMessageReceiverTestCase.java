/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the BSD style
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.providers.gs;

import com.mockobjects.dynamic.Mock;

import org.mule.impl.endpoint.MuleEndpoint;
import org.mule.providers.AbstractConnector;
import org.mule.providers.space.SpaceMessageReceiver;
import org.mule.tck.providers.AbstractMessageReceiverTestCase;
import org.mule.umo.UMOComponent;
import org.mule.umo.UMODescriptor;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.provider.UMOMessageReceiver;

/**
 * @version $Revision$
 */

public class JiniMessageReceiverTestCase extends AbstractMessageReceiverTestCase
{

    public UMOMessageReceiver getMessageReceiver() throws Exception
    {
        Mock mockComponent = new Mock(UMOComponent.class);
        Mock mockDescriptor = new Mock(UMODescriptor.class);
        mockComponent.expectAndReturn("getDescriptor", mockDescriptor.proxy());
        mockDescriptor.expectAndReturn("getResponseTransformer", null);
        
        return new SpaceMessageReceiver((AbstractConnector) endpoint.getConnector(), (UMOComponent) mockComponent.proxy(), endpoint);
    }

    public UMOEndpoint getEndpoint() throws Exception
    {
        return new MuleEndpoint("gs:rmi://localhoast:1000/MyContainer/JavaSpaces", true);
    }
}
