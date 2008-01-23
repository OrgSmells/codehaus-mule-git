/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.http.transformers;

import org.mule.tck.FunctionalTestCase;
import org.mule.transport.http.servlet.ServletConnector;

public abstract class ServletNamespaceHandlerTestCase extends FunctionalTestCase
{

    protected String getConfigResources()
    {
        return "servlet-namespace-config.xml";
    }

    public void testConnectorProperties()
    {
        ServletConnector connector =
                (ServletConnector) muleContext.getRegistry().lookupConnector("servletConnector");
        assertNotNull(connector);

        assertEquals("", connector.getServletUrl());
    }

}