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
package org.mule.providers.jdbc;

import org.mule.umo.UMOTransaction;
import org.mule.umo.UMOTransactionException;
import org.mule.umo.UMOTransactionFactory;

/**
 * @author Guillaume Nodet
 * @version $Revision$
 */
public class JdbcTransactionFactory implements UMOTransactionFactory {

	/* (non-Javadoc)
	 * @see org.mule.umo.UMOTransactionFactory#beginTransaction()
	 */
	public UMOTransaction beginTransaction() throws UMOTransactionException {
        JdbcTransaction tx = new JdbcTransaction();
        tx.begin();
        return tx;
	}

	/* (non-Javadoc)
	 * @see org.mule.umo.UMOTransactionFactory#isTransacted()
	 */
	public boolean isTransacted() {
		return true;
	}

}
