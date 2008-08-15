/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.cxf.testmodels;

public class TestXFireComponent
{
    public String testXFireException(String data) throws CxfEnabledFaultMessage
    {
        CustomFault fault = new CustomFault();
        fault.setDescription("Custom Exception Message");
        throw new CxfEnabledFaultMessage("XFire Exception Message", fault);
    }

    public String testNonXFireException(String data)
    {
        throw new UnsupportedOperationException("Non-XFire Enabled Exception");
    }
}
