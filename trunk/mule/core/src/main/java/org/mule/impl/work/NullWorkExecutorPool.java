/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the BSD style
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

/**
 *
 * Copyright 2004 The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.mule.impl.work;

import org.mule.config.ThreadingProfile;

/**
 * 
 * 
 * @version $Rev$ $Date$
 * 
 */
public class NullWorkExecutorPool implements WorkExecutorPool
{

    private ThreadingProfile profile;
    private String name;

    public NullWorkExecutorPool(ThreadingProfile profile, String name)
    {
        this.profile = profile;
        this.name = name;
    }

    public int getPoolSize()
    {
        return 0;
    }

    public int getMaximumPoolSize()
    {
        return profile.getMaxThreadsActive();
    }

    public void setMaximumPoolSize(int maxSize)
    {
        profile.setMaxThreadsActive(maxSize);
    }

    public WorkExecutorPool start()
    {
        return new WorkExecutorPoolImpl(profile, name);
    }

    public WorkExecutorPool stop()
    {
        return this;
    }

    public void execute(Runnable command)
    {
        throw new IllegalStateException("Stopped");
    }
}
