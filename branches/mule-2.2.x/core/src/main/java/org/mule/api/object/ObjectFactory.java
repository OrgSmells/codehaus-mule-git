/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.api.object;

import org.mule.api.lifecycle.Disposable;
import org.mule.api.lifecycle.Initialisable;
import org.mule.api.lifecycle.InitialisationCallback;

/**
 * <code>ObjectFactory</code> is a generic Factory interface.
 */
public interface ObjectFactory extends Initialisable, Disposable
{
    /**
     * Retrieve an instance of the object. This may create a new instance or look up
     * an existing instance depending on the implementation. If a new instance is
     * created it will also be initialized by this method
     * (Initilisable.initialise()).
     */
    Object getInstance() throws Exception;

    /**
     * Returns the class of the object to be instantiated without actually creating
     * an instance. This may not be logical or even possible depending on the
     * implementation.
     */
    Class getObjectClass();
    
    /**
     * Returns true if the ObjectFactory implementation always returns the same object
     * instance.
     * 
     * @return
     */
    boolean isSingleton();

    /**
     * Returns true if Mule should not manage the life-cycle the object instance returned from the ObjectFactory.
     * This is normally false except when an ObjectFactory implementation obtains instance from containers
     * (e.g. Spring) that already manages the objects lifecycle.
     * instance.
     * 
     * @return
     */
    boolean isExternallyManagedLifecycle();
    
    /**
     * Register a custom initialiser
     */
    void addObjectInitialisationCallback(InitialisationCallback callback);

}
