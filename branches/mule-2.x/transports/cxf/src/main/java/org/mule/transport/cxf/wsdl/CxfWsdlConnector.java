/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.cxf.wsdl;

import org.mule.transport.cxf.CxfConnector;

/**
 * TODO document
 */
public class CxfWsdlConnector extends CxfConnector
{

    protected void registerProtocols()
    {
        registerSupportedProtocol("http");
        registerSupportedProtocol("https");

        // This allows the generic WSDL provider to created endpoints using this
        // connector
        registerSupportedProtocolWithoutPrefix("wsdl:http");
        registerSupportedProtocolWithoutPrefix("wsdl:https");
    }

    public String getProtocol()
    {
        return "wsdl-cxf";
    }
}
