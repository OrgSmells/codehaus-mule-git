/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.internal.admin;

import org.mule.agent.MuleAdminAgent;
import org.mule.tck.AbstractMuleTestCase;

public class MuleAdminAgentTestCase extends AbstractMuleTestCase
{

    public void testEmptyServerUrl() throws Exception
    {
        final String serverEndpoint = "";
        MuleAdminAgent agent = new MuleAdminAgent();
        agent.setServerUri(serverEndpoint);
        agent.setMuleContext(muleContext);
        agent.initialise();

        // if it doesn't here fail, it has been registered
    }

    public void testNonEmptyServerUrl() throws Exception
    {
        final String serverEndpoint = "test://12345";
        MuleAdminAgent agent = new MuleAdminAgent();
        agent.setMuleContext(muleContext);        
        agent.setServerUri(serverEndpoint);
        agent.initialise();

        // if it doesn't here fail, it has been registered
    }

}
