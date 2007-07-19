/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.ra;

import org.mule.umo.UMOException;
import org.mule.umo.manager.UMOWorkManager;

import javax.resource.spi.work.ExecutionContext;
import javax.resource.spi.work.Work;
import javax.resource.spi.work.WorkException;
import javax.resource.spi.work.WorkListener;
import javax.resource.spi.work.WorkManager;

import edu.emory.mathcs.backport.java.util.concurrent.RejectedExecutionException;

/**
 * <code>DelegateWorkManager</code> is a wrapper around a WorkManager provided by a
 * JCA container.
 */
public class DelegateWorkManager implements UMOWorkManager
{
    private final WorkManager workManager;

    public DelegateWorkManager(WorkManager workManager)
    {
        this.workManager = workManager;
    }

    public void doWork(Work work) throws WorkException
    {
        workManager.doWork(work);
    }

    public void doWork(Work work, long l, ExecutionContext executionContext, WorkListener workListener)
        throws WorkException
    {
        workManager.doWork(work, l, executionContext, workListener);
    }

    public long startWork(Work work) throws WorkException
    {
        return workManager.startWork(work);
    }

    public long startWork(Work work, long l, ExecutionContext executionContext, WorkListener workListener)
        throws WorkException
    {
        return workManager.startWork(work, l, executionContext, workListener);
    }

    public void scheduleWork(Work work) throws WorkException
    {
        workManager.scheduleWork(work);
    }

    public void scheduleWork(Work work, long l, ExecutionContext executionContext, WorkListener workListener)
        throws WorkException
    {
        workManager.scheduleWork(work, l, executionContext, workListener);
    }

    public void execute(Runnable command)
    {
        try
        {
            this.scheduleWork(new RunnableWorkAdapter(command));
        }
        catch (WorkException wex)
        {
            // unfortunately RejectedExecutionException is the closest thing we have
            // as proper RuntimeException
            throw new RejectedExecutionException(wex);
        }
    }

    public void start() throws UMOException
    {
        // nothing to do
    }

    public void stop() throws UMOException
    {
        // nothing to do
    }

    public void dispose()
    {
        // nothing to do
    }

    protected static class RunnableWorkAdapter implements Work
    {
        private final Runnable command;

        public RunnableWorkAdapter(Runnable command)
        {
            super();
            this.command = command;
        }

        public void release()
        {
            // nothing to do
        }

        public void run()
        {
            command.run();
        }
    }

}
