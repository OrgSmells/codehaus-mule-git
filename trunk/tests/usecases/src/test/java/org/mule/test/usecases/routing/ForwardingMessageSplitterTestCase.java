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
package org.mule.test.usecases.routing;

import org.mule.tck.NamedTestCase;
import org.mule.MuleManager;
import org.mule.umo.UMOMessage;
import org.mule.extras.client.MuleClient;
import org.mule.config.builders.MuleXmlConfigurationBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:ross.mason@cubis.co.uk">Ross Mason</a>
 * @version $Revision$
 */
public class ForwardingMessageSplitterTestCase extends NamedTestCase
{
    protected void setUp() throws Exception
    {
        if(MuleManager.isInstanciated()) MuleManager.getInstance().dispose();
        MuleXmlConfigurationBuilder builder = new MuleXmlConfigurationBuilder();
        builder.configure("org/mule/test/usecases/routing/forwarding-message-splitter.xml");
    }

    public void testSyncResponse() throws Exception
    {
        MuleClient client = new MuleClient();

        List payload = new ArrayList();
        payload.add("hello");
        payload.add(new Integer(3));
        payload.add(new Exception());
        client.send("vm://in.queue", payload, null);
        UMOMessage m = client.receive("vm://component.1", 2000);
        assertNotNull(m);
        assertTrue(m.getPayload() instanceof String);
        m = client.receive("vm://component.2", 2000);
        assertNotNull(m);
        assertTrue(m.getPayload() instanceof Integer);

        m = client.receive("vm://error.queue", 2000);
        assertNotNull(m);
        assertTrue(m.getPayload() instanceof Exception);
    }
}
