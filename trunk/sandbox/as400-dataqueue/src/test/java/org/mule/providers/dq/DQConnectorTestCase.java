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
package org.mule.providers.dq;

import org.mule.tck.providers.AbstractConnectorTestCase;
import org.mule.umo.provider.UMOConnector;

/**
 * @author <a href="mailto:ross.mason@cubis.co.uk">Ross Mason</a>
 * @version $Revision$
 */
public class DQConnectorTestCase extends AbstractConnectorTestCase
{
    public UMOConnector getConnector() throws Exception
    {
        DQConnector c = new DQConnector();
        c.setName("dqConnector");
        c.setRecordFormat("DQ_recordFormat.xml");
        c.setUsername("xxx");
        c.setPassword("xxx");
        c.setHostname("localhost");
        c.initialise();
        return c;
    }

    public Object getValidMessage() throws Exception
    {
        DQMessage message = new DQMessage();
        message.addEntry("entry1", "value1");
        return message;
    }

    public String getTestEndpointURI()
    {
        return "dq://L701QUEUE.DTAQ?lib=/QSYS.LIB";
    }
}
