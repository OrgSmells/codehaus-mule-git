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
package org.mule.util;

import EDU.oswego.cs.dl.util.concurrent.Channel;
import EDU.oswego.cs.dl.util.concurrent.PooledExecutor;
import org.mule.config.ThreadingProfile;
import org.mule.umo.lifecycle.Disposable;

import java.util.ArrayList;
import java.util.List;

/**
 * <code>DisposableThreadPool</code> explicitly disposes all threads in
 * the pool that implement Disposable
 * Calling shutdownNow() will invoke the dispose method on this pool
 *
 * @author <a href="mailto:ross.mason@cubis.co.uk">Ross Mason</a>
 * @version $Revision$
 */
public class DisposableThreadPool extends PooledExecutor implements Disposable
{
    public DisposableThreadPool()
    {
        super();
    }

    public DisposableThreadPool(int i)
    {
        super(i);
    }

    public DisposableThreadPool(Channel channel)
    {
        super(channel);
    }

    public DisposableThreadPool(Channel channel, int i)
    {
        super(channel, i);
    }

    public DisposableThreadPool (String name) {
        super();
        setThreadFactory(new ThreadingProfile.NamedThreadFactory(name, Thread.NORM_PRIORITY));
    }
    public void dispose()
    {
        List list = new ArrayList(threads_.values());
        Thread thread;
        for (int i = 0; i < list.size(); i++)
        {
              thread = (Thread)list.get(i);
              if(thread instanceof Disposable) {
                ((Disposable)thread).dispose();
            } else {
                thread.interrupt();
            }
        }
        super.shutdownNow();
    }

    public void shutdownNow()
    {
        dispose();
    }
}
