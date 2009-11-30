/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.api.lifecycle;

import org.mule.api.MuleContext;
import org.mule.api.MuleException;
import org.mule.api.registry.Registry;

import java.util.Set;

/**
 * The LifecycleManager is responsible for managing the different lifecycle phases of the server and managing the
 * transitions between lifecycle phases.
 */

public interface LifecycleManager
{
    Set<LifecyclePhase> getLifecycles();

    void setLifecycles(Set<LifecyclePhase> lifecycles);

    void registerLifecycle(LifecyclePhase lci);

    /**
     * Applies lifecycle phase to all objects in the Registry.
     */
    void firePhase(MuleContext muleContext, String phase) throws MuleException;

    /**
     * Applies lifecycle phase to a collection of objects.
     */
    LifecyclePhase applyPhase(Registry registry, String phase) throws MuleException;

    void applyPhase
            (Object object, String phase) throws MuleException;

    /**
     * Applies lifecycle phase to an object independent of the current lifecycle phase. All phases between the current
     * phase and the 'endPhase' will be executed.
     * @param endPhase the final phase to execute on the object.  All phases inbetween current and end will be executed
     */
    void applyPhases(Object object, String endPhase) throws MuleException;
    
    String getCurrentPhase();

    String getExecutingPhase();

    void reset();

    boolean isPhaseComplete(String phaseName);

    /**
     * Successively applies all completed lifecycle phase to an object.
     */
    void applyCompletedPhases(Object object) throws MuleException;

    void checkPhase(String name) throws IllegalStateException;
}
