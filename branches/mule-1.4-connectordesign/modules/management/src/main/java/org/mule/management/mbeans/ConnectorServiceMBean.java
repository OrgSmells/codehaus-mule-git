/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.management.mbeans;

import java.beans.ExceptionListener;

import org.mule.umo.UMOException;
import org.mule.umo.lifecycle.InitialisationException;
import org.mule.umo.lifecycle.RecoverableException;
import org.mule.umo.provider.UMOMessageDispatcherFactory;

public interface ConnectorServiceMBean
{

    boolean isStarted();

    boolean isDisposed();

    boolean isDisposing();

    String getName();

    String getProtocol();

    ExceptionListener getExceptionListener();

    // TODO HH: we should probably get rid of this, the factory is nobody's business
    UMOMessageDispatcherFactory getDispatcherFactory();

    void startConnector() throws UMOException;

    void stopConnector() throws UMOException;

    void dispose();

    void initialise() throws InitialisationException, RecoverableException;

}
