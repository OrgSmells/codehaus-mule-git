/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.ftp;

import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.transport.file.FileConnector;
import org.mule.transport.ftp.server.NamedPayload;

import java.util.HashMap;
import java.util.Map;

public class FtpFunctionalTestCase extends AbstractFtpServerTestCase
{
    private static int PORT = 60198;

    public FtpFunctionalTestCase()
    {
        super(PORT);
    }

    protected String getConfigResources()
    {
        return "ftp-functional-test.xml";
    }

    protected int getPort()
    {
        return PORT;
    }
    
    public void testSendAndRequest() throws Exception
    {
        Map properties = new HashMap();
        MuleClient client = new MuleClient();
        client.dispatch("ftp://anonymous:email@localhost:" + getPort(), TEST_MESSAGE, properties);
        NamedPayload payload = awaitUpload();
        assertNotNull(payload);
        assertEquals(TEST_MESSAGE, new String(payload.getPayload()));
        logger.info("received message OK!");
        MuleMessage retrieved = client.request("ftp://anonymous:email@localhost:" + getPort(), getTimeout());
        assertNotNull(retrieved);
        assertNotNull(retrieved.getProperty(FileConnector.PROPERTY_ORIGINAL_FILENAME));
        assertNotNull(retrieved.getProperty(FileConnector.PROPERTY_FILE_SIZE));
        assertEquals(retrieved.getPayloadAsString(), TEST_MESSAGE);
    }

}
