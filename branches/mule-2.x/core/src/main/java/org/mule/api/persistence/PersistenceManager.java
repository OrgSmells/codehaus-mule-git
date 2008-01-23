/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.api.persistence;

import org.mule.api.lifecycle.Disposable;
import org.mule.api.lifecycle.Initialisable;
import org.mule.api.lifecycle.Startable;
import org.mule.api.lifecycle.Stoppable;

/**
 * <code>PersistenceManager</code> is the interface for any object that
 * can persist stuff. Right now, it is used for the registry. For example,
 * a RegistryPersistenceManager will get data from the RegistryStore, via
 * XStream, and persist it to a file or xml database.
 *
 * In the future, we might have different kinds of persistence managers.
 * 
 * @author 
 * @version $Revision$
 */
public interface PersistenceManager extends Initialisable, Startable, Stoppable, Disposable
{
    /*
     * Temporary method!
     * Call the manager to schedule the persistence
     */
    void requestPersistence(Persistable source);

    /*
     * Returns whether or not the manager is ready to persist
     */
    boolean isReady();

    /**
     * Returns number of requests since the last persistences
     */
    int getRequestCount();

    /**
     * Returns the last time a request was made
     */
    long getLastRequestTime();

    /**
     * Do the persistence. This will probably be replaced by an internal
     * call used by a timer listener
     */
    void persist();

    String getStoreType();
}
