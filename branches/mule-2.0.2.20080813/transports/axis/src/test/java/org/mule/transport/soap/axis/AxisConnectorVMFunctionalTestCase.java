/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.soap.axis;

import org.mule.tck.providers.soap.AbstractSoapResourceEndpointFunctionalTestCase;
import org.mule.transport.vm.VMConnector;

public class AxisConnectorVMFunctionalTestCase extends AbstractSoapResourceEndpointFunctionalTestCase
{
    protected String getTransportProtocol()
    {
        return VMConnector.VM;
    }

    protected String getSoapProvider()
    {
        return "axis";
    }

    public String getConfigResources()
    {
        return "axis-" + getTransportProtocol() + "-mule-config.xml";
    }
}
