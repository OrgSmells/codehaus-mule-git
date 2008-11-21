/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.management.agents;

import org.mule.tck.AbstractMuleTestCase;

public class MuleContextRestartTestCase extends AbstractMuleTestCase
{

    @Override
    protected String getConfigurationResources()
    {
        return "mule-context-restart-config.xml";
    }

    public void testContextRestart() throws Exception
    {
        muleContext.stop();
        muleContext.start();
    }

}
