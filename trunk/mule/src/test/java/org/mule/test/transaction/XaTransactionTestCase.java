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
package org.mule.test.transaction;

import javax.transaction.Transaction;
import javax.transaction.TransactionManager;

import org.mule.MuleManager;
import org.mule.tck.AbstractMuleTestCase;
import org.mule.transaction.XaTransaction;
import org.mule.umo.UMOTransaction;

import com.mockobjects.dynamic.Mock;

/**
 * <code>XATransactionTestCase</code> TODO
 *
 * @author <a href="mailto:ross.mason@cubis.co.uk">Ross Mason</a>
 * @author Guillaume Nodet
 * @version $Revision$
 */

public class XaTransactionTestCase extends AbstractMuleTestCase
{
    protected Mock mockTm = new Mock(TransactionManager.class);

	protected void setUp() throws Exception
    {
        super.setUp();
        TransactionManager tm = (TransactionManager) mockTm.proxy(); 
    	MuleManager.getInstance().setTransactionManager(tm);
    }
    
    protected void tearDown() throws Exception {
        if (MuleManager.isInstanciated()) {
        	MuleManager.getInstance().dispose();
        }
    }

    public void testBeginCommit() throws Exception
    {
        Mock mockTx = new Mock(Transaction.class);
    	mockTm.expect("begin");
    	mockTm.expectAndReturn("getTransaction", (Transaction) mockTx.proxy());
    	
        XaTransaction tx = new XaTransaction();
		assertFalse(tx.isBegun());
		assertEquals(UMOTransaction.STATUS_NO_TRANSACTION, tx.getStatus());
		tx.begin();
		
		mockTx.expectAndReturn("getStatus", UMOTransaction.STATUS_ACTIVE);
		assertTrue(tx.isBegun());

		mockTx.expectAndReturn("getStatus", UMOTransaction.STATUS_ACTIVE);
		mockTx.expect("commit");
		tx.commit();

		mockTx.expectAndReturn("getStatus", UMOTransaction.STATUS_COMMITTED);
		assertTrue(tx.isCommitted());
    }

}
