/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.test.integration.routing.nested;

public class Invoker
{
    private HelloInterface hello;

    public String invoke(String s)
    {
        return "Received: " + hello.hello(s, new Integer(0xC0DE));
    }
    
    public void setHello(HelloInterface hello)
    {
        this.hello = hello;
    }

    public HelloInterface getHello()
    {
        return hello;
    }
}
