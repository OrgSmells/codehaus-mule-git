/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.umo.manager;

import org.mule.umo.lifecycle.Disposable;
import org.mule.umo.lifecycle.Startable;

import javax.resource.spi.work.WorkManager;

import edu.emory.mathcs.backport.java.util.concurrent.Executor;

/**
 * <code>UMOWorkManager</code> extends the standard JCA WorkManager with lifecycle
 * methods and util.concurrent bridging.
 */
public interface UMOWorkManager extends WorkManager, Startable, Disposable, Executor
{
    // no additional methods
}
