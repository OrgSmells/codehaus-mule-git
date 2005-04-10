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
 *
 */
package org.mule.transaction;

import EDU.oswego.cs.dl.util.concurrent.SynchronizedBoolean;
import org.mule.config.i18n.Message;
import org.mule.config.i18n.Messages;
import org.mule.umo.TransactionException;

/**
 * This abstract class can be used as a base class for transactions
 * that can enlist only one resource (such as jms session or jdbc connection).
 * 
 * @author Guillaume Nodet
 * @version $Revision$
 */
public abstract class AbstractSingleResourceTransaction extends AbstractTransaction {

    protected Object key;
    protected Object resource;
    
    protected SynchronizedBoolean started = new SynchronizedBoolean(false);
    protected SynchronizedBoolean committed = new SynchronizedBoolean(false);
    protected SynchronizedBoolean rolledBack = new SynchronizedBoolean(false);
    protected SynchronizedBoolean rollbackOnly = new SynchronizedBoolean(false);
    
	/* (non-Javadoc)
	 * @see org.mule.umo.UMOTransaction#begin()
	 */
	public void begin() throws TransactionException {
		super.begin();
        started.commit(false, true);
	}

	/* (non-Javadoc)
	 * @see org.mule.umo.UMOTransaction#commit()
	 */
	public void commit() throws TransactionException {
		super.commit();
	    committed.commit(false, true);
	}

	/* (non-Javadoc)
	 * @see org.mule.umo.UMOTransaction#rollback()
	 */
	public void rollback() throws TransactionException {
		super.rollback();
        rolledBack.commit(false, true);
	}

	/* (non-Javadoc)
	 * @see org.mule.umo.UMOTransaction#getStatus()
	 */
	public int getStatus() throws TransactionStatusException {
        if (rolledBack.get()) return STATUS_ROLLEDBACK;
        if (committed.get()) return STATUS_COMMITTED;
		if (rollbackOnly.get()) return STATUS_MARKED_ROLLBACK;
        if (started.get()) return STATUS_ACTIVE;
        return STATUS_NO_TRANSACTION;
	}

	/* (non-Javadoc)
	 * @see org.mule.umo.UMOTransaction#getResource(java.lang.Object)
	 */
	public Object getResource(Object key) {
		return key != null && this.key == key ? this.resource : null;
	}

	/* (non-Javadoc)
	 * @see org.mule.umo.UMOTransaction#hasResource(java.lang.Object)
	 */
	public boolean hasResource(Object key) {
		return key != null && this.key == key;
	}

	/* (non-Javadoc)
	 * @see org.mule.umo.UMOTransaction#bindResource(java.lang.Object, java.lang.Object)
	 */
	public void bindResource(Object key, Object resource) throws TransactionException {
		if (key == null) {
			throw new IllegalTransactionStateException(new Message(Messages.TX_CANT_BIND_TO_NULL_KEY));
		}
		if (resource == null) {
			throw new IllegalTransactionStateException(new Message(Messages.TX_CANT_BIND_NULL_RESOURCE));
		}
		if (this.key != null) {
			throw new IllegalTransactionStateException(new Message(Messages.TX_SINGLE_RESOURCE_ONLY));
		}
		this.key = key;
		this.resource = resource;
	}

	/* (non-Javadoc)
	 * @see org.mule.umo.UMOTransaction#setRollbackOnly()
	 */
	public void setRollbackOnly() {
        rollbackOnly.set(true);
	}

    public Object getId() {
        return key;
    }

	/**
	 * Really begin the transaction.
	 * Note that resources are enlisted yet. 
	 * @throws TransactionException
	 */
	protected abstract void doBegin() throws TransactionException;

	/**
	 * Commit the transaction on the underlying resource 
	 * @throws TransactionException
	 */
	protected abstract void doCommit() throws TransactionException;

	/**
	 * Rollback the transaction on the underlying resource 
	 * @throws TransactionException
	 */
	protected abstract void doRollback() throws TransactionException;
	
}
