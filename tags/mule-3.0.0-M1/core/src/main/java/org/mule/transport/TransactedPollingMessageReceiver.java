/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport;

import org.mule.api.MuleException;
import org.mule.api.config.ThreadingProfile;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.api.lifecycle.CreateException;
import org.mule.api.service.Service;
import org.mule.api.transaction.TransactionCallback;
import org.mule.api.transport.Connector;
import org.mule.transaction.TransactionTemplate;

import java.util.Iterator;
import java.util.List;

import javax.resource.spi.work.Work;

import edu.emory.mathcs.backport.java.util.concurrent.CountDownLatch;

/**
 * The TransactedPollingMessageReceiver is an abstract receiver that handles polling
 * and transaction management. Derived implementations of these class must be thread
 * safe as several threads can be started at once for an improveded throuput.
 */
public abstract class TransactedPollingMessageReceiver extends AbstractPollingMessageReceiver
{
    /** determines whether messages will be received in a transaction template */
    private boolean receiveMessagesInTransaction = true;

    /** determines whether Multiple receivers are created to improve throughput */
    private boolean useMultipleReceivers = true;

    public TransactedPollingMessageReceiver(Connector connector,
                                            Service service,
                                            final InboundEndpoint endpoint) throws CreateException
    {
        super(connector, service, endpoint);
        this.setReceiveMessagesInTransaction(endpoint.getTransactionConfig().isTransacted());
    }

    /**
     * @deprecated please use
     *             {@link #TransactedPollingMessageReceiver(Connector,Service,InboundEndpoint)}
     *             instead
     */
    public TransactedPollingMessageReceiver(Connector connector,
                                            Service service,
                                            final InboundEndpoint endpoint,
                                            long frequency) throws CreateException
    {
        this(connector, service, endpoint);
        this.setFrequency(frequency);
    }

    public boolean isReceiveMessagesInTransaction()
    {
        return receiveMessagesInTransaction;
    }

    public void setReceiveMessagesInTransaction(boolean useTx)
    {
        receiveMessagesInTransaction = useTx;
    }

    public boolean isUseMultipleTransactedReceivers()
    {
        return useMultipleReceivers;
    }

    public void setUseMultipleTransactedReceivers(boolean useMultiple)
    {
        useMultipleReceivers = useMultiple;
    }

    @Override
    public void doStart() throws MuleException
    {
        // Connector property overrides any implied value
        this.setUseMultipleTransactedReceivers(connector.isCreateMultipleTransactedReceivers());

        ThreadingProfile tp = connector.getReceiverThreadingProfile();
        int numReceiversToStart = 1;

        if (this.isReceiveMessagesInTransaction() && this.isUseMultipleTransactedReceivers()
                && tp.isDoThreading())
        {
            numReceiversToStart = connector.getNumberOfConcurrentTransactedReceivers();
        }

        for (int i = 0; i < numReceiversToStart; i++)
        {
            super.doStart();
        }
    }

    public void poll() throws Exception
    {
        TransactionTemplate tt = new TransactionTemplate(endpoint.getTransactionConfig(),
                connector.getExceptionListener(), connector.getMuleContext());

        if (this.isReceiveMessagesInTransaction())
        {
            // Receive messages and process them in a single transaction
            // Do not enable threading here, but several workers
            // may have been started
            TransactionCallback cb = new TransactionCallback()
            {
                public Object doInTransaction() throws Exception
                {
                    List messages = getMessages();
                    if (messages != null && messages.size() > 0)
                    {
                        for (Iterator it = messages.iterator(); it.hasNext();)
                        {
                            TransactedPollingMessageReceiver.this.processMessage(it.next());
                        }
                    }
                    return null;
                }
            };
            tt.execute(cb);
        }
        else
        {
            // Receive messages and launch a worker for each message
            List messages = getMessages();
            if (messages != null && messages.size() > 0)
            {
                final CountDownLatch countdown = new CountDownLatch(messages.size());
                for (Iterator it = messages.iterator(); it.hasNext();)
                {
                    try
                    {
                        this.getWorkManager().scheduleWork(
                                new MessageProcessorWorker(tt, countdown, it.next()));
                    }
                    catch (Exception e)
                    {
                        countdown.countDown();
                        throw e;
                    }
                }
                countdown.await();
            }
        }
    }

    protected class MessageProcessorWorker implements Work, TransactionCallback
    {
        private final TransactionTemplate tt;
        private final Object message;
        private final CountDownLatch latch;

        public MessageProcessorWorker(TransactionTemplate tt, CountDownLatch latch, Object message)
        {
            this.tt = tt;
            this.message = message;
            this.latch = latch;
        }

        public void release()
        {
            // nothing to do
        }

        public void run()
        {
            try
            {
                tt.execute(this);
            }
            catch (Exception e)
            {
                handleException(e);
            }
            finally
            {
                latch.countDown();
            }
        }

        public Object doInTransaction() throws Exception
        {
            TransactedPollingMessageReceiver.this.processMessage(message);
            return null;
        }

    }

    protected abstract List getMessages() throws Exception;

    protected abstract void processMessage(Object message) throws Exception;

}
