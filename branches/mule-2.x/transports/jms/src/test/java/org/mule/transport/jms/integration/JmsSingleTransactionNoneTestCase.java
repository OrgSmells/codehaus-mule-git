/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.jms.integration;

import org.junit.Test;

/**
 * Send and recieve JmsMessage without any tx
 */
public class JmsSingleTransactionNoneTestCase extends AbstractJmsFunctionalTestCase
{
    private static final String CONNECTOR_NAME = "jmsConnector";

    public JmsSingleTransactionNoneTestCase(JmsVendorConfiguration config)
    {
        super(config);
    }

    protected String getConfigResources()
    {
        return "integration/jms-single-tx-NONE.xml";
    }

    @Test
    public void testNoneTx() throws Exception
    {
        send();
        receiveAndAssert();
        receiveAndAssertNone();
    }
}
