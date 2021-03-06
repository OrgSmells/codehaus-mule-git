/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.jms;

import org.mule.MuleRuntimeException;
import org.mule.config.i18n.MessageFactory;
import org.mule.providers.AbstractMessageReceiver;
import org.mule.providers.AbstractReceiverWorker;
import org.mule.providers.ConnectException;
import org.mule.providers.jms.filters.JmsSelectorFilter;
import org.mule.umo.TransactionException;
import org.mule.umo.UMOComponent;
import org.mule.umo.UMOException;
import org.mule.umo.UMOTransaction;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.lifecycle.InitialisationException;
import org.mule.umo.lifecycle.LifecycleException;
import org.mule.umo.provider.UMOConnector;
import org.mule.util.ClassUtils;

import java.util.ArrayList;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.Topic;
import javax.resource.spi.work.WorkException;

import edu.emory.mathcs.backport.java.util.concurrent.BlockingDeque;
import edu.emory.mathcs.backport.java.util.concurrent.LinkedBlockingDeque;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This is an experimental rework of the <code>transacted.message.receiver</code>
 * (it may work with non-transacted sessions as well,
 * just hasn't been tested and load-tested yet.
 * <p/>
 * In Mule an endpoint corresponds to a single receiver. It's up to the receiver to do multithreaded consumption and
 * resource allocation, if needed. This class honors the <code>numberOfConcurrentTransactedReceivers</code> strictly
 * and will create exactly this number of consumers.
 */
public class MultiConsumerJmsMessageReceiver extends AbstractMessageReceiver
{
    protected final BlockingDeque consumers;

    protected volatile int receiversCount;

    private final JmsConnector jmsConnector;

    public MultiConsumerJmsMessageReceiver(UMOConnector connector, UMOComponent component, UMOEndpoint endpoint)
            throws InitialisationException
    {
        super(connector, component, endpoint);

        jmsConnector = (JmsConnector) connector;

        final boolean isTopic = jmsConnector.getTopicResolver().isTopic(endpoint, true);
        receiversCount = jmsConnector.getNumberOfConcurrentTransactedReceivers();
        if (isTopic && receiversCount != 1)
        {
            if (logger.isInfoEnabled())
            {
                logger.info("Destination " + getEndpoint().getEndpointURI() + " is a topic, but " + receiversCount +
                                " receivers have been configured. Will configure only 1.");
            }
            receiversCount = 1;
        }
        if (logger.isDebugEnabled())
        {
            logger.debug("Creating " + receiversCount + " sub-receivers for " + endpoint.getEndpointURI());
        }
        consumers = new LinkedBlockingDeque(receiversCount);
        for (int i = 0; i < receiversCount; i++)
        {
            consumers.addLast(new SubReceiver());
        }
    }

    protected void doStart() throws UMOException
    {
        logger.debug("doStart()");
        for (int i = 0; i < receiversCount; i++)
        {
            SubReceiver sub = (SubReceiver) consumers.removeFirst();
            sub.doStart();
            consumers.addLast(sub);
        }
    }

    protected void doStop() throws UMOException
    {
        logger.debug("doStop()");
        for (int i = 0; i < receiversCount; i++)
        {
            SubReceiver sub = (SubReceiver) consumers.removeFirst();
            sub.doStop();
            consumers.addLast(sub);
        }
    }

    protected void doConnect() throws Exception
    {
        logger.debug("doConnect()");
        for (int i = 0; i < receiversCount; i++)
        {
            SubReceiver sub = (SubReceiver) consumers.removeFirst();
            sub.doConnect();
            consumers.addLast(sub);
        }
    }

    protected void doDisconnect() throws Exception
    {
        logger.debug("doDisconnect()");
        for (int i = 0; i < receiversCount; i++)
        {
            SubReceiver sub = (SubReceiver) consumers.removeFirst();
            sub.doDisconnect();
            consumers.addLast(sub);
        }

    }

    protected void doDispose()
    {
        logger.debug("doDispose()");
    }

    private class SubReceiver implements MessageListener
    {
        private final Log subLogger = LogFactory.getLog(getClass());

        private volatile Session session;
        private volatile MessageConsumer consumer;

        protected void doConnect() throws Exception
        {
            subLogger.debug("SUB doConnect()");
            createConsumer();
        }

        protected void doDisconnect() throws Exception
        {
            closeConsumer();
        }

        protected void closeConsumer()
        {
            jmsConnector.closeQuietly(consumer);
            consumer = null;
            jmsConnector.closeQuietly(session);
            session = null;
        }

        protected void doStart() throws UMOException
        {
            try
            {
                consumer.setMessageListener(this);
            }
            catch (JMSException e)
            {
                throw new LifecycleException(e, this);
            }
        }

        protected void doStop() throws UMOException
        {
            try
            {
                if (consumer != null)
                {
                    consumer.setMessageListener(null);
                }
            }
            catch (JMSException e)
            {
                throw new LifecycleException(e, this);
            }
        }


        /**
         * Create a consumer for the jms destination.
         */
        protected void createConsumer() throws Exception
        {
            try
            {
                JmsSupport jmsSupport = jmsConnector.getJmsSupport();
                // Create session if none exists
                if (session == null)
                {
                    session = jmsConnector.getSession(endpoint);
                }

                boolean topic = jmsConnector.getTopicResolver().isTopic(endpoint, true);

                // Create destination
                Destination dest = jmsSupport.createDestination(session, endpoint.getEndpointURI().getAddress(),
                                                                topic);

                // Extract jms selector
                String selector = null;
                if (endpoint.getFilter() != null && endpoint.getFilter() instanceof JmsSelectorFilter)
                {
                    selector = ((JmsSelectorFilter) endpoint.getFilter()).getExpression();
                }
                else
                {
                    if (endpoint.getProperties() != null)
                    {
                        // still allow the selector to be set as a property on the endpoint
                        // to be backward compatable
                        selector = (String) endpoint.getProperties().get(JmsConstants.JMS_SELECTOR_PROPERTY);
                    }
                }
                String tempDurable = (String) endpoint.getProperties().get(JmsConstants.DURABLE_PROPERTY);
                boolean durable = jmsConnector.isDurable();
                if (tempDurable != null)
                {
                    durable = Boolean.valueOf(tempDurable).booleanValue();
                }

                // Get the durable subscriber name if there is one
                String durableName = (String) endpoint.getProperties().get(JmsConstants.DURABLE_NAME_PROPERTY);
                if (durableName == null && durable && dest instanceof Topic)
                {
                    durableName = "mule." + jmsConnector.getName() + "." + endpoint.getEndpointURI().getAddress();
                    logger.debug("Jms Connector for this receiver is durable but no durable name has been specified. Defaulting to: "
                                 + durableName);
                }

                // Create consumer
                consumer = jmsSupport.createConsumer(session, dest, selector, jmsConnector.isNoLocal(), durableName,
                                                     topic);
            }
            catch (JMSException e)
            {
                throw new ConnectException(e, this);
            }
        }

        public void onMessage(final Message message)
        {
            try
            {
                // This must be the doWork() to preserve the transactional context.
                // We are already running in the consumer thread by this time.
                // The JmsWorker classe is a one-off executor which is abandoned after it's done and is
                // easily garbage-collected (confirmed with a profiler)
                getWorkManager().doWork(new JmsWorker(message, MultiConsumerJmsMessageReceiver.this, this));
            }
            catch (WorkException e)
            {
                throw new MuleRuntimeException(MessageFactory.createStaticMessage(
                        "Couldn't submit a work item to the WorkManager"), e);
            }
        }
    }

    protected class JmsWorker extends AbstractReceiverWorker
    {
        private final SubReceiver subReceiver;

        public JmsWorker(Message message, AbstractMessageReceiver receiver, SubReceiver subReceiver)
        {
            super(new ArrayList(1), receiver);
            this.subReceiver = subReceiver;
            messages.add(message);
        }

        //@Override
        protected Object preProcessMessage(Object message) throws Exception
        {
            Message m = (Message) message;

            if (logger.isDebugEnabled())
            {
                logger.debug("Message received it is of type: " +
                             ClassUtils.getSimpleName(message.getClass()));
                if (m.getJMSDestination() != null)
                {
                    logger.debug("Message received on " + m.getJMSDestination() + " ("
                                 + m.getJMSDestination().getClass().getName() + ")");
                }
                else
                {
                    logger.debug("Message received on unknown destination");
                }
                logger.debug("Message CorrelationId is: " + m.getJMSCorrelationID());
                logger.debug("Jms Message Id is: " + m.getJMSMessageID());
            }

            if (m.getJMSRedelivered())
            {
                // lazily create the redelivery handler
                RedeliveryHandler redeliveryHandler = jmsConnector.createRedeliveryHandler();
                redeliveryHandler.setConnector(jmsConnector);
                if (logger.isDebugEnabled())
                {
                    logger.debug("Message with correlationId: " + m.getJMSCorrelationID()
                                 + " has redelivered flag set, handing off to Exception Handler");
                }
                redeliveryHandler.handleRedelivery(m);
            }
            return m;

        }

        protected void bindTransaction(UMOTransaction tx) throws TransactionException
        {
            if (tx instanceof JmsTransaction)
            {
                if (logger.isDebugEnabled())
                {
                    logger.debug(">>> !!  Binding " + subReceiver.session + " to " + jmsConnector.getConnection());
                }
                tx.bindResource(jmsConnector.getConnection(), subReceiver.session);
            }
            else
            {
                if (tx instanceof JmsClientAcknowledgeTransaction)
                {
                    //We should still bind the session to the transaction, but we also need the message itself
                    //since that is the object that gets Acknowledged
                    //tx.bindResource(jmsConnector.getConnection(), session);
                    ((JmsClientAcknowledgeTransaction) tx).setMessage((Message) messages.get(0));
                }
            }
        }

    }

}
