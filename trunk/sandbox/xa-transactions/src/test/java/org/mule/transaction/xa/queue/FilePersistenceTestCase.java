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
package org.mule.transaction.xa.queue;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author <a href="mailto:gnt@codehaus.org">Guillaume Nodet</a>
 * @version $Revision$
 */
public class FilePersistenceTestCase extends AbstractTransactionQueueManagerTestCase {

	private static final Log logger = LogFactory.getLog(FilePersistenceTestCase.class);
	
	protected static final String STORE_DIR = "./target/file";
	
	protected void deleteWholeDir(File f) {
		if (f.exists()) {
			File[] files = f.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteWholeDir(files[i]);
				} else {
					logger.info("Deleting " + files[i]);
					files[i].delete();
				}
			}
			f.delete();
		}
	}
	
	protected void setUp() throws Exception {
		deleteWholeDir(new File(STORE_DIR));
	}
	
	protected Log getLogger() {
		return logger;
	}
	
	protected TransactionalQueueManager createQueueManager() throws Exception {
		TransactionalQueueManager mgr = new TransactionalQueueManager();
		mgr.setPersistenceStrategy(new FilePersistenceStrategy(new File(STORE_DIR)));
		return mgr;
	}

	/* (non-Javadoc)
	 * @see org.mule.transaction.xa.queue.AbstractTransactionQueueManagerTestCase#isPersistent()
	 */
	protected boolean isPersistent() {
		return true;
	}
	
}
