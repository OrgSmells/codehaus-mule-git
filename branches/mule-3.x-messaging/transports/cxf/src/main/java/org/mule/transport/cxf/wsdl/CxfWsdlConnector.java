/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.cxf.wsdl;

import org.mule.api.MuleContext;
import org.mule.transport.cxf.CxfConnector;

/**
 * TODO document
 */
public class CxfWsdlConnector extends CxfConnector
{
    
    public CxfWsdlConnector(MuleContext context)
    {
        super(context);
    }

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
