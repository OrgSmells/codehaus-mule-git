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
package org.mule.test.usecases.sync;

import org.mule.tck.NamedTestCase;
import org.mule.MuleManager;
import org.mule.umo.UMOException;
import org.mule.umo.UMOMessage;
import org.mule.extras.client.MuleClient;
import org.mule.config.builders.MuleXmlConfigurationBuilder;

import java.util.Date;

/**
 * @author <a href="mailto:ross.mason@cubis.co.uk">Ross Mason</a>
 * @version $Revision$
 */
public class SyncSocketToJmsTestCase extends NamedTestCase
{
    protected void setUp() throws Exception
    {
        if(MuleManager.isInstanciated()) MuleManager.getInstance().dispose();
        MuleXmlConfigurationBuilder builder = new MuleXmlConfigurationBuilder();
        builder.configure("org/mule/test/usecases/sync/socket-auth-jms.xml");
    }

    public void testSyncResponse() throws Exception
    {
        MuleClient client = new MuleClient();
        UMOMessage message = client.send("tcp://localhost:4444", "ross", null);
        assertNotNull(message);
        assertEquals("Received: User ross Authorised", message.getPayloadAsString());

        message = client.send("tcp://localhost:4444", "dave", null);

        assertNotNull(message);
        assertEquals("User not Authorised", message.getPayloadAsString());

    }
}
