/*
 * $Header$
 * $Revision$
 * $Date$
 * ------------------------------------------------------------------------------------------------------
 *
 * Copyright (c) Cubis Limited. All rights reserved.
 * http://www.cubis.co.uk
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */

package org.mule.providers.jms;

import org.mule.impl.endpoint.MuleEndpointURI;
import org.mule.tck.NamedTestCase;
import org.mule.umo.endpoint.UMOEndpointURI;

/**
 * @author <a href="mailto:ross.mason@cubis.co.uk">Ross Mason</a>
 * @version $Revision$
 */

public class JmsEndpointTestCase extends NamedTestCase
{
    public void testWithoutFullUrl() throws Exception
    {
        UMOEndpointURI url = new MuleEndpointURI("jms:/my.queue");
        assertEquals("jms", url.getScheme());
        assertEquals("my.queue", url.getAddress());
        assertNull(url.getEndpointName());
        assertEquals("jms:/my.queue", url.toString());
    }

    public void testWithoutFullUrlAndProvider() throws Exception
    {
        UMOEndpointURI url = new MuleEndpointURI("jms:/jmsProvider/my.queue");
        assertEquals("jms", url.getScheme());
        assertEquals("my.queue", url.getAddress());
        assertNotNull(url.getEndpointName());
        assertEquals("jmsProvider", url.getEndpointName());
        assertEquals("jms:/jmsProvider/my.queue", url.toString());
    }


    public void testJmsUrl() throws Exception
    {
        UMOEndpointURI url = new MuleEndpointURI("jms://localhost/jmsProvider/queue1");
        assertEquals("jms", url.getScheme());
        assertEquals("queue1", url.getAddress());
        assertEquals("jmsProvider", url.getEndpointName());
        assertEquals("jms://localhost/jmsProvider/queue1", url.toString());
    }

    public void testJmsUrlWithHostOverride() throws Exception
    {
        UMOEndpointURI url = new MuleEndpointURI("jms://jmsProvider/queue1");
        assertEquals("jms", url.getScheme());
        assertEquals("queue1", url.getAddress());
        assertEquals("jmsProvider", url.getEndpointName());
        assertEquals("jms://jmsProvider/queue1", url.toString());
    }

    public void testJmsTopic() throws Exception
    {
        UMOEndpointURI url = new MuleEndpointURI("jms://topic:topic1");
        assertEquals("jms", url.getScheme());
        assertEquals("topic1", url.getAddress());
        assertEquals("topic", url.getResourceInfo());
        assertEquals(null, url.getEndpointName());
        assertEquals("jms://topic:topic1", url.toString());
    }

    public void testJmsTopicWithProvider() throws Exception
    {
        UMOEndpointURI url = new MuleEndpointURI("jms://jmsProvider/topic:topic1");
        assertEquals("jms", url.getScheme());
        assertEquals("topic1", url.getAddress());
        assertEquals("jmsProvider", url.getEndpointName());
        assertEquals("topic", url.getResourceInfo());
        assertEquals("jms://jmsProvider/topic:topic1", url.toString());
    }
}
