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


/**
 * <code>Queue</code> TODO
 * 
 * @author <a href="mailto:gnt@codehaus.org">Guillaume Nodet</a>
 * @version $Revision$
 */
public interface Queue {

	/**
	 * Returns the number of elements in this queue.
	 * @return
	 */
	int size();
	
	/**
	 * Puts a new object in this queue.
	 * @param o the object to put
	 */
    void put(Object o);

    /**
     * Retrieves an object from this queue.
     * @return an object or <code>null</code> if none available.
     */
    Object take();
}
