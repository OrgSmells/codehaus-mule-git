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

import org.mule.tck.providers.AbstractMessageReceiverTestCase;
import org.mule.umo.provider.UMOMessageReceiver;
import org.mule.umo.UMOComponent;
import org.mule.providers.AbstractConnector;
import org.mule.impl.endpoint.MuleEndpoint;
import com.mockobjects.dynamic.Mock;
import com.ibm.as400.access.DataQueue;

/**
 * @author <a href="mailto:ross.mason@cubis.co.uk">Ross Mason</a>
 * @version $Revision$
 */
public class DQMessageReceiverTestCase extends AbstractMessageReceiverTestCase
{
    public UMOMessageReceiver getMessageReceiver() throws Exception
    {
        DQConnector c = new DQConnector();
        c.setName("dqConnector");
        c.setRecordFormat("DQ_recordFormat.xml");
        c.setUsername("xxx");
        c.setPassword("xxx");
        c.setHostname("localhost");
        c.initialise();

        endpoint = new MuleEndpoint("dq://QSYS.LIB/L701QUEUE.DTAQ", true);
        endpoint.setConnector(c);
        Mock mockComponent = new Mock(UMOComponent.class);
        return new DQMessageReceiver((AbstractConnector) endpoint.getConnector(), (UMOComponent) mockComponent.proxy(), endpoint, new Long(1000), new DataQueue(c.getSystem(), "/QSYS.LIB/L701QUEUE.DTAQ"), c.getSystem());
    }
}

