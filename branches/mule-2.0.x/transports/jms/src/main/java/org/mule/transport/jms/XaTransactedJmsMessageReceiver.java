/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.jms;

import org.mule.DefaultMuleMessage;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.api.lifecycle.CreateException;
import org.mule.api.service.Service;
import org.mule.api.transaction.Transaction;
import org.mule.api.transport.Connector;
import org.mule.api.transport.MessageAdapter;
import org.mule.transaction.TransactionCoordination;
import org.mule.transaction.XaTransaction;
import org.mule.transport.ConnectException;
import org.mule.transport.SingleAttemptConnectionStrategy;
import org.mule.transport.TransactedPollingMessageReceiver;
import org.mule.transport.jms.filters.JmsSelectorFilter;
import org.mule.util.ClassUtils;
import org.mule.util.MapUtils;

import java.util.List;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;

public class XaTransactedJmsMessageReceiver extends TransactedPollingMessageReceiver
{
    public static final long DEFAULT_JMS_POLL_FREQUENCY = 100;
    public static final TimeUnit DEFAULT_JMS_POLL_TIMEUNIT = TimeUnit.MILLISECONDS;
    
    protected final JmsConnector connector;
    protected boolean reuseConsumer;
    protected boolean reuseSession;
    protected final ThreadContextLocal context = new ThreadContextLocal();
    protected final long timeout;
    protected final RedeliveryHandler redeliveryHandler;

    /**
     * Holder receiving the session and consumer for this thread.
     */
    protected static class JmsThreadContext
    {
        public Session session;
        public MessageConsumer consumer;
    }

    /**
     * Strongly typed ThreadLocal for ThreadContext.
     */
    protected static class ThreadContextLocal extends ThreadLocal
    {
        public JmsThreadContext getContext()
        {
            return (JmsThreadContext)get();
        }

        protected Object initialValue()
        {
            return new JmsThreadContext();
        }
    }

    public XaTransactedJmsMessageReceiver(Connector umoConnector, Service service, InboundEndpoint endpoint)
        throws CreateException
    {
        super(umoConnector, service, endpoint);
        // TODO AP: find appropriate value for polling frequency with the scheduler;
        // see setFrequency/setTimeUnit & VMMessageReceiver for more
        this.setFrequency(DEFAULT_JMS_POLL_FREQUENCY);
        this.setTimeUnit(DEFAULT_JMS_POLL_TIMEUNIT);
        this.connector = (JmsConnector) umoConnector;
        this.timeout = endpoint.getTransactionConfig().getTimeout();

        // If reconnection is set, default reuse strategy to false
        // as some jms brokers will not detect lost connections if the
        // same consumer / session is used
        if (this.connectionStrategy instanceof SingleAttemptConnectionStrategy)
        {
            this.reuseConsumer = true;
            this.reuseSession = true;
        }

        // User may override reuse strategy if necessary
        this.reuseConsumer = MapUtils.getBooleanValue(endpoint.getProperties(), "reuseConsumer",
            this.reuseConsumer);
        this.reuseSession = MapUtils.getBooleanValue(endpoint.getProperties(), "reuseSession",
            this.reuseSession);

        // Do extra validation, XA Topic & reuse are incompatible. See MULE-2622
        boolean topic = connector.getTopicResolver().isTopic(getEndpoint());
        if (topic && (reuseConsumer || reuseSession))
        {
            logger.warn("Destination " + getEndpoint().getEndpointURI() + " is a topic and XA transaction was " +
                        "configured. Forcing 'reuseSession' and 'reuseConsumer' to false. Set these " +
                        "on endpoint to avoid the message.");
            reuseConsumer = false;
            reuseSession = false;
        }

        // Check if the destination is a queue and
        // if we are in transactional mode.
        // If true, set receiveMessagesInTransaction to true.
        // It will start multiple threads, depending on the threading profile.

        // If we're using topics we don't want to use multiple receivers as we'll get
        // the same message multiple times
        this.setUseMultipleTransactedReceivers(!topic);

        try
        {
            redeliveryHandler = this.connector.getRedeliveryHandlerFactory().create();
            redeliveryHandler.setConnector(this.connector);
        }
        catch (Exception e)
        {
            throw new CreateException(e, this);
        }

    }

    protected void doDispose()
    {
        // template method
    }

    protected void doConnect() throws Exception
    {
        if (connector.isConnected() && connector.isEagerConsumer())
        {
            createConsumer();
            // creating this consumer now would prevent from the actual worker
            // consumer
            // to receive the message!
            //Antoine Borg 08 Dec 2006 - Uncommented for MULE-1150
            // if we comment this line, if one tries to restart the service through
            // JMX,
            // this will fail...
            //This Line seems to be the root to a number of problems and differences between
            //Jms providers. A which point the consumer is created changes how the conneciton can be managed.
            //For example, WebsphereMQ needs the consumer created here, otherwise ReconnectionStrategies don't work properly
            //(See MULE-1150) However, is the consumer is created here for Active MQ, The worker thread cannot actually
            //receive the message.  We need to test with a few more Jms providers and transactions to see which behaviour
            // is correct.  My gut feeling is that the consumer should be created here and there is a bug in ActiveMQ
        }
    }

    protected void doDisconnect() throws Exception
    {
        if (connector.isConnected())
        {
            closeConsumer(true);
        }
    }

    /**
     * The poll method is overriden from the {@link TransactedPollingMessageReceiver}
     */
    public void poll() throws Exception
    {
        try
        {
            JmsThreadContext ctx = context.getContext();
            // Create consumer if necessary
            if (ctx.consumer == null)
            {
                createConsumer();
            }
            // Do polling
            super.poll();
        }
        catch (Exception e)
        {
            // Force consumer to close
            closeConsumer(true);
            throw e;
        }
        finally
        {
            // Close consumer if necessary
            closeConsumer(false);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.transport.TransactionEnabledPollingMessageReceiver#getMessages()
     */
    protected List getMessages() throws Exception
    {
        // As the session is created outside the transaction, it is not
        // bound to it yet
        JmsThreadContext ctx = context.getContext();

        Transaction tx = TransactionCoordination.getInstance().getTransaction();
        if (tx != null)
        {
            tx.bindResource(connector.getConnection(), ctx.session);
        }

        // Retrieve message
        Message message = null;
        try
        {
            message = ctx.consumer.receive(timeout);
        }
        catch (JMSException e)
        {
            // If we're being disconnected, ignore the exception
            if (!this.isConnected())
            {
                // ignore
            }
            else
            {
                throw e;
            }
        }
        if (message == null)
        {
            if (tx != null)
            {
                tx.setRollbackOnly();
            }
            return null;
        }
        message = connector.preProcessMessage(message, ctx.session);

        // Process message
        if (logger.isDebugEnabled())
        {
            logger.debug("Message received it is of type: " +
                    ClassUtils.getSimpleName(message.getClass()));
            if (message.getJMSDestination() != null)
            {
                logger.debug("Message received on " + message.getJMSDestination() + " ("
                             + message.getJMSDestination().getClass().getName() + ")");
            }
            else
            {
                logger.debug("Message received on unknown destination");
            }
            logger.debug("Message CorrelationId is: " + message.getJMSCorrelationID());
            logger.debug("Jms Message Id is: " + message.getJMSMessageID());
        }

        if (message.getJMSRedelivered())
        {
            if (logger.isDebugEnabled())
            {
                logger.debug("Message with correlationId: " + message.getJMSCorrelationID()
                             + " is redelivered. handing off to Exception Handler");
            }
            redeliveryHandler.handleRedelivery(message);
        }

        if (tx instanceof JmsClientAcknowledgeTransaction)
        {
            tx.bindResource(message, null);
        }

        MessageAdapter adapter = connector.getMessageAdapter(message);
        routeMessage(new DefaultMuleMessage(adapter));
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.transport.TransactionEnabledPollingMessageReceiver#processMessage(java.lang.Object)
     */
    protected void processMessage(Object msg) throws Exception
    {
        // This method is never called as the
        // message is processed when received
    }

    protected void closeConsumer(boolean force)
    {
        JmsThreadContext ctx = context.getContext();
        if (ctx == null)
        {
            return;
        }
        // Close consumer
        if (force || !reuseSession || !reuseConsumer)
        {
            connector.closeQuietly(ctx.consumer);
            ctx.consumer = null;
        }
        // Do not close session if a transaction is in progress
        // the session will be closed by the transaction
        if (force || !reuseSession)
        {
            connector.closeQuietly(ctx.session);
            ctx.session = null;
        }
    }

    /**
     * Create a consumer for the jms destination
     * 
     * @throws Exception
     */
    protected void createConsumer() throws Exception
    {
        try
        {
            JmsSupport jmsSupport = this.connector.getJmsSupport();
            JmsThreadContext ctx = context.getContext();
            // Create session if none exists
            if (ctx.session == null)
            {
                ctx.session = this.connector.getSession(endpoint);
                //set reuse flag
                ((XaTransaction.MuleXaObject) ctx.session).setReuseObject(reuseSession);

            }

            // Create destination
            final boolean topic = connector.getTopicResolver().isTopic(endpoint);
            Destination dest = jmsSupport.createDestination(ctx.session, endpoint.getEndpointURI()
                .getAddress(), topic);

            // Extract jms selector
            String selector = null;
            if (endpoint.getFilter() != null && endpoint.getFilter() instanceof JmsSelectorFilter)
            {
                selector = ((JmsSelectorFilter)endpoint.getFilter()).getExpression();
            }
            else if (endpoint.getProperties() != null)
            {
                // still allow the selector to be set as a property on the endpoint
                // to be backward compatible
                selector = (String)endpoint.getProperties().get(JmsConstants.JMS_SELECTOR_PROPERTY);
            }
            String tempDurable = (String)endpoint.getProperties().get("durable");
            boolean durable = connector.isDurable();
            if (tempDurable != null)
            {
                durable = Boolean.valueOf(tempDurable).booleanValue();
            }

            // Get the durable subscriber name if there is one
            String durableName = (String)endpoint.getProperties().get("durableName");
            if (durableName == null && durable && topic)
            {
                durableName = "mule." + connector.getName() + "." + endpoint.getEndpointURI().getAddress();
                logger.debug("Jms Connector for this receiver is durable but no durable name has been specified. Defaulting to: "
                             + durableName);
            }

            // Create consumer
            ctx.consumer = jmsSupport.createConsumer(ctx.session, dest, selector, connector.isNoLocal(),
                durableName, topic);
        }
        catch (JMSException e)
        {
            throw new ConnectException(e, this);
        }
    }
}
