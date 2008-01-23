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
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.endpoint.EndpointURI;
import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.api.transport.Connector;
import org.mule.api.transport.DispatchException;
import org.mule.api.transport.MessageAdapter;
import org.mule.transaction.IllegalTransactionStateException;
import org.mule.transport.AbstractMessageDispatcher;
import org.mule.transport.jms.i18n.JmsMessages;
import org.mule.util.ClassUtils;
import org.mule.util.NumberUtils;
import org.mule.util.StringUtils;
import org.mule.util.concurrent.Latch;
import org.mule.util.concurrent.WaitableBoolean;

import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TemporaryQueue;
import javax.jms.TemporaryTopic;

import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;

import org.apache.commons.lang.BooleanUtils;

/**
 * <code>JmsMessageDispatcher</code> is responsible for dispatching messages to JMS
 * destinations. All JMS semantics apply and settings such as replyTo and QoS
 * properties are read from the event properties or defaults are used (according to
 * the JMS specification)
 */
public class JmsMessageDispatcher extends AbstractMessageDispatcher
{

    private JmsConnector connector;
    private Session cachedSession;

    public JmsMessageDispatcher(ImmutableEndpoint endpoint)
    {
        super(endpoint);
        this.connector = (JmsConnector)endpoint.getConnector();
    }

    protected void doDispatch(MuleEvent event) throws Exception
    {
        dispatchMessage(event);
    }

    protected void doConnect() throws Exception
    {
        // template method
    }

    protected void doDisconnect() throws Exception
    {
        // template method
    }

    private MuleMessage dispatchMessage(MuleEvent event) throws Exception
    {
        Session session = null;
        MessageProducer producer = null;
        MessageConsumer consumer = null;
        Destination replyTo = null;
        boolean transacted = false;
        boolean cached = false;
        boolean remoteSync = useRemoteSync(event);

        if (logger.isDebugEnabled())
        {
            logger.debug("dispatching on endpoint: " + event.getEndpoint().getEndpointURI()
                         + ". MuleEvent id is: " + event.getId()
                         + ". Outbound transformers are: " + event.getEndpoint().getTransformers());
        }

        try
        {
            session = connector.getSessionFromTransaction();
            if (session != null)
            {
                transacted = true;

                // If a transaction is running, we can not receive any messages
                // in the same transaction.
                if (remoteSync)
                {
                    throw new IllegalTransactionStateException(
                        JmsMessages.connectorDoesNotSupportSyncReceiveWhenTransacted());
                }
            }
            // Should we be caching sessions? Note this is not part of the JMS spec.
            // and is turned off by default.
            else if (event.getMessage().getBooleanProperty(JmsConstants.CACHE_JMS_SESSIONS_PROPERTY,
                connector.isCacheJmsSessions()))
            {
                cached = true;
                if (cachedSession != null)
                {
                    session = cachedSession;
                }
                else
                {
                    session = connector.getSession(event.getEndpoint());
                    cachedSession = session;
                }
            }
            else
            {
                session = connector.getSession(event.getEndpoint());
                if (event.getEndpoint().getTransactionConfig().isTransacted())
                {
                    transacted = true;
                }
            }

            EndpointURI endpointUri = event.getEndpoint().getEndpointURI();

            boolean topic = connector.getTopicResolver().isTopic(event.getEndpoint(), true);

            Destination dest = connector.getJmsSupport().createDestination(session, endpointUri.getAddress(),
                topic);
            producer = connector.getJmsSupport().createProducer(session, dest, topic);

            Object message = event.transformMessage();
            if (!(message instanceof Message))
            {
                throw new DispatchException(
                    JmsMessages.checkTransformer("JMS message", message.getClass(), connector.getName()),
                    event.getMessage(), event.getEndpoint());
            }

            Message msg = (Message)message;
            if (event.getMessage().getCorrelationId() != null)
            {
                msg.setJMSCorrelationID(event.getMessage().getCorrelationId());
            }

            MuleMessage eventMsg = event.getMessage();

            // Some JMS implementations might not support the ReplyTo property.
            if (connector.supportsProperty(JmsConstants.JMS_REPLY_TO))
            {
                Object tempReplyTo = eventMsg.removeProperty(JmsConstants.JMS_REPLY_TO);
                if (tempReplyTo != null)
                {
                    if (tempReplyTo instanceof Destination)
                    {
                        replyTo = (Destination)tempReplyTo;
                    }
                    else
                    {
                        // TODO AP should this drill-down be moved into the resolver as well?
                        boolean replyToTopic = false;
                        String reply = tempReplyTo.toString();
                        int i = reply.indexOf(":");
                        if (i > -1)
                        {
                            // TODO MULE-1409 this check will not work for ActiveMQ 4.x,
                            // as they have temp-queue://<destination> and temp-topic://<destination> URIs
                            // Extract to a custom resolver for ActiveMQ4.x
                            // The code path can be exercised, e.g. by a LoanBrokerESBTestCase
                            String qtype = reply.substring(0, i);
                            replyToTopic = JmsConstants.TOPIC_PROPERTY.equalsIgnoreCase(qtype);
                            reply = reply.substring(i + 1);
                        }
                        replyTo = connector.getJmsSupport().createDestination(session, reply, replyToTopic);
                    }
                }
                // Are we going to wait for a return event ?
                if (remoteSync && replyTo == null)
                {
                    replyTo = connector.getJmsSupport().createTemporaryDestination(session, topic);
                }
                // Set the replyTo property
                if (replyTo != null)
                {
                    msg.setJMSReplyTo(replyTo);
                }

                // Are we going to wait for a return event ?
                if (remoteSync)
                {
                    consumer = connector.getJmsSupport().createConsumer(session, replyTo, topic);
                }
            }

            // QoS support
            String ttlString = (String)eventMsg.removeProperty(JmsConstants.TIME_TO_LIVE_PROPERTY);
            String priorityString = (String)eventMsg.removeProperty(JmsConstants.PRIORITY_PROPERTY);
            String persistentDeliveryString = (String)eventMsg.removeProperty(JmsConstants.PERSISTENT_DELIVERY_PROPERTY);

            long ttl = StringUtils.isNotBlank(ttlString)
                                ? NumberUtils.toLong(ttlString)
                                : Message.DEFAULT_TIME_TO_LIVE;
            int priority = StringUtils.isNotBlank(priorityString)
                                ? NumberUtils.toInt(priorityString)
                                : Message.DEFAULT_PRIORITY;
            boolean persistent = StringUtils.isNotBlank(persistentDeliveryString)
                                ? BooleanUtils.toBoolean(persistentDeliveryString)
                                : connector.isPersistentDelivery();

            if (connector.isHonorQosHeaders())
            {
                int priorityProp = eventMsg.getIntProperty(JmsConstants.JMS_PRIORITY, Connector.INT_VALUE_NOT_SET);
                int deliveryModeProp = eventMsg.getIntProperty(JmsConstants.JMS_DELIVERY_MODE, Connector.INT_VALUE_NOT_SET);

                if (priorityProp != Connector.INT_VALUE_NOT_SET)
                {
                    priority = priorityProp;
                }
                if (deliveryModeProp != Connector.INT_VALUE_NOT_SET)
                {
                    persistent = deliveryModeProp == DeliveryMode.PERSISTENT;
                }
            }

            if (logger.isDebugEnabled())
            {
                logger.debug("Sending message of type " + ClassUtils.getSimpleName(msg.getClass()));
            }

            if (consumer != null && topic)
            {
                // need to register a listener for a topic
                Latch l = new Latch();
                ReplyToListener listener = new ReplyToListener(l);
                consumer.setMessageListener(listener);

                connector.getJmsSupport().send(producer, msg, persistent, priority, ttl, topic);

                int timeout = event.getTimeout();

                if (logger.isDebugEnabled())
                {
                    logger.debug("Waiting for return event for: " + timeout + " ms on " + replyTo);
                }

                l.await(timeout, TimeUnit.MILLISECONDS);
                consumer.setMessageListener(null);
                listener.release();
                Message result = listener.getMessage();
                if (result == null)
                {
                    logger.debug("No message was returned via replyTo destination");
                    return null;
                }
                else
                {
                    MessageAdapter adapter = connector.getMessageAdapter(result);
                    return new DefaultMuleMessage(JmsMessageUtils.toObject(result, connector.getSpecification()),
                        adapter);
                }
            }
            else
            {
                connector.getJmsSupport().send(producer, msg, persistent, priority, ttl, topic);
                if (consumer != null)
                {
                    int timeout = event.getTimeout();

                    if (logger.isDebugEnabled())
                    {
                        logger.debug("Waiting for return event for: " + timeout + " ms on " + replyTo);
                    }

                    Message result = consumer.receive(timeout);
                    if (result == null)
                    {
                        logger.debug("No message was returned via replyTo destination");
                        return null;
                    }
                    else
                    {
                        MessageAdapter adapter = connector.getMessageAdapter(result);
                        return new DefaultMuleMessage(
                            JmsMessageUtils.toObject(result, connector.getSpecification()), adapter);
                    }
                }
            }
            return null;
        }
        finally
        {
            connector.closeQuietly(producer);
            connector.closeQuietly(consumer);

            // TODO AP check if TopicResolver is to be utilized for temp destinations as well
            if (replyTo != null && (replyTo instanceof TemporaryQueue || replyTo instanceof TemporaryTopic))
            {
                if (replyTo instanceof TemporaryQueue)
                {
                    connector.closeQuietly((TemporaryQueue)replyTo);
                }
                else
                {
                    // hope there are no more non-standard tricks from JMS vendors
                    // here ;)
                    connector.closeQuietly((TemporaryTopic)replyTo);
                }
            }

            // If the session is from the current transaction, it is up to the
            // transaction to close it.
            if (session != null && !cached && !transacted)
            {
                connector.closeQuietly(session);
            }
        }
    }

    protected MuleMessage doSend(MuleEvent event) throws Exception
    {
        MuleMessage message = dispatchMessage(event);
        return message;
    }

    protected void doDispose()
    {
        // template method
    }

    private class ReplyToListener implements MessageListener
    {
        private final Latch latch;
        private volatile Message message;
        private final WaitableBoolean released = new WaitableBoolean(false);

        public ReplyToListener(Latch latch)
        {
            this.latch = latch;
        }

        public Message getMessage()
        {
            return message;
        }

        public void release()
        {
            released.set(true);
        }

        public void onMessage(Message message)
        {
            this.message = message;
            latch.countDown();
            try
            {
                released.whenTrue(null);
            }
            catch (InterruptedException e)
            {
                // ignored
            }
        }
    }

}
