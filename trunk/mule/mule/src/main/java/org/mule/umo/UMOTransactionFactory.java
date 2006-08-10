/*
 * $Id
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the BSD style
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
 */

package org.mule.umo;

/**
 * <p>
 * <code>UMOTransactionFactory</code> creates a transaction.
 * 
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */
public interface UMOTransactionFactory
{
    /**
     * Create and begins a new transaction
     * 
     * @return a new Transaction
     * @throws TransactionException if the transaction cannot be created or
     *             begun
     */
    UMOTransaction beginTransaction() throws TransactionException;

    /**
     * Determines whether this transaction factory creates transactions that are
     * really transacted or if they are being used to simulate batch actions,
     * such as using Jms Client Acknowledge.
     * 
     * @return
     */
    boolean isTransacted();
}
