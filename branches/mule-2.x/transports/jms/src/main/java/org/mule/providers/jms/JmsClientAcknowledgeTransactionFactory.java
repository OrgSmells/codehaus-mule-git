/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.jms;

import org.mule.api.MuleContext;
import org.mule.umo.TransactionException;
import org.mule.umo.UMOTransaction;
import org.mule.umo.UMOTransactionFactory;

/**
 * <code>JmsClientAcknowledgeTransactionFactory</code> creates a JMS Client
 * Acknowledge Transaction using a JMS Message.
 */

public class JmsClientAcknowledgeTransactionFactory implements UMOTransactionFactory
{
    /*
     * (non-Javadoc)
     * 
     * @see org.mule.umo.UMOTransactionFactory#beginTransaction(java.lang.Object)
     */
    public UMOTransaction beginTransaction(MuleContext muleContext) throws TransactionException
    {
        JmsClientAcknowledgeTransaction tx = new JmsClientAcknowledgeTransaction();
        tx.begin();
        return tx;
    }

    public boolean isTransacted()
    {
        return false;
    }

}
