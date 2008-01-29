/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.soap.xfire.testmodels;

import org.codehaus.xfire.service.Service;

/**
 * This class is only used in the namespace handler tests to test the proper
 * instantiation of client service objects.
 */
public class MockService extends Service
{
    public MockService()
    {
        super();
    }
}


