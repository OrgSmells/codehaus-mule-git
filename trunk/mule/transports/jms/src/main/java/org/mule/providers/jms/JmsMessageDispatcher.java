/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.jms;

import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;

import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TemporaryQueue;
import javax.jms.TemporaryTopic;

import org.apache.commons.collections.MapUtils;
import org.mule.MuleException;
import org.mule.config.i18n.Messages;
import org.mule.impl.MuleMessage;
import org.mule.providers.AbstractMessageDispatcher;
import org.mule.transaction.IllegalTransactionStateException;
import org.mule.umo.UMOEvent;
import org.mule.umo.UMOException;
import org.mule.umo.UMOMessage;
import org.mule.umo.endpoint.UMOEndpointURI;
import org.mule.umo.endpoint.UMOImmutableEndpoint;
import org.mule.umo.provider.DispatchException;
import org.mule.umo.provider.UMOConnector;
import org.mule.umo.provider.UMOMessageAdapter;
import org.mule.util.concurrent.Latch;
import org.mule.util.concurrent.WaitableBoolean;

/**
 * <code>JmsMessageDispatcher</code> is responsible for dispatching messages to JMS
 * destinations. All JMS semantics apply and settings such as replyTo and QoS
 * properties are read from the event properties or defaults are used (according to
 * the JMS specification)
 */
public class JmsMessageDispatcher extends AbstractMessageDispatcher
{

    private JmsConnector connector;
    private Session delegateSession;
    private Session cachedSession;

    public JmsMessageDispatcher(UMOImmutableEndpoint endpoint)
    {
        super(endpoint);
        this.connector = (JmsConnector)endpoint.getConnector();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.providers.UMOConnector#dispatchEvent(org.mule.MuleEvent,
     *      org.mule.providers.MuleEndpoint)
     */
    protected void doDispatch(UMOEvent event) throws Exception
    {
        dispatchMessage(event);
    }

    protected void doConnect(UMOImmutableEndpoint endpoint) throws Exception
    {
        // template method
    }

    protected void doDisconnect() throws Exception
    {
        // template method
    }

    private UMOMessage dispatchMessage(UMOEvent event) throws Exception
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
                         + ". Event id is: " + event.getId());
        }

        try
        {
            // Retrieve the session from the current transaction.
            session = connector.getSessionFromTransaction();
            if (session != null)
            {
                transacted = true;

                // If a transaction is running, we can not receive any messages
                // in the same transaction.
                if (remoteSync)
                {
                    throw new IllegalTransactionStateException(new org.mule.config.i18n.Message("jms", 2));
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
                    // Retrieve a session from the connector
                    session = connector.getSession(event.getEndpoint());
                    cachedSession = session;
                }
            }
            else
            {
                // Retrieve a session from the connector
                session = connector.getSession(event.getEndpoint());
                if (event.getEndpoint().getTransactionConfig().isTransacted())
                {
                    transacted = true;
                }
            }

            // Add a reference to the JMS session used so that an
            // EventAwareTransformer
            // can later retrieve it.
            // TODO Figure out a better way to accomplish this: MULE-1079
            // event.getMessage().setProperty(MuleProperties.MULE_JMS_SESSION,
            // session);

            UMOEndpointURI endpointUri = event.getEndpoint().getEndpointURI();

            // determine if endpointUri is a queue or topic
            // the format is topic:destination
            boolean topic = false;
            String resourceInfo = endpointUri.getResourceInfo();
            topic = (resourceInfo != null && JmsConstants.TOPIC_PROPERTY.equalsIgnoreCase(resourceInfo));
            // TODO MULE20 remove resource info support
            if (!topic)
            {
                topic = MapUtils.getBooleanValue(event.getEndpoint().getProperties(),
                    JmsConstants.TOPIC_PROPERTY, false);
            }

            Destination dest = connector.getJmsSupport().createDestination(session, endpointUri.getAddress(),
                topic);
            producer = connector.getJmsSupport().createProducer(session, dest, topic);

            Object message = event.getTransformedMessage();
            if (!(message instanceof Message))
            {
                throw new DispatchException(new org.mule.config.i18n.Message(
                    Messages.MESSAGE_NOT_X_IT_IS_TYPE_X_CHECK_TRANSFORMER_ON_X, "JMS message",
                    message.getClass().getName(), connector.getName()), event.getMessage(),
                    event.getEndpoint());
            }

            Message msg = (Message)message;
            if (event.getMessage().getCorrelationId() != null)
            {
                msg.setJMSCorrelationID(event.getMessage().getCorrelationId());
            }

            UMOMessage eventMsg = event.getMessage();

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
                        boolean replyToTopic = false;
                        String reply = tempReplyTo.toString();
                        int i = reply.indexOf(":");
                        if (i > -1)
                        {
                            String qtype = reply.substring(0, i);
                            replyToTopic = "topic".equalsIgnoreCase(qtype);
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

            long ttl = Message.DEFAULT_TIME_TO_LIVE;
            int priority = Message.DEFAULT_PRIORITY;
            boolean persistent = Message.DEFAULT_DELIVERY_MODE == DeliveryMode.PERSISTENT;

            if (ttlString != null)
            {
                ttl = Long.parseLong(ttlString);
            }
            if (priorityString != null)
            {
                priority = Integer.parseInt(priorityString);
            }
            if (persistentDeliveryString != null)
            {
                persistent = Boolean.valueOf(persistentDeliveryString).booleanValue();
            }

            logger.debug("Sending message of type " + msg.getClass().getName());
            if (consumer != null && topic)
            {
                // need to register a listener for a topic
                Latch l = new Latch();
                ReplyToListener listener = new ReplyToListener(l);
                consumer.setMessageListener(listener);

                connector.getJmsSupport().send(producer, msg, persistent, priority, ttl, topic);

                int timeout = event.getTimeout();
                logger.debug("Waiting for return event for: " + timeout + " ms on " + replyTo);
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
                    UMOMessageAdapter adapter = connector.getMessageAdapter(result);
                    return new MuleMessage(JmsMessageUtils.getObjectForMessage(result), adapter);
                }
            }
            else
            {
                connector.getJmsSupport().send(producer, msg, persistent, priority, ttl, topic);
                if (consumer != null)
                {
                    int timeout = event.getTimeout();
                    logger.debug("Waiting for return event for: " + timeout + " ms on " + replyTo);
                    Message result = consumer.receive(timeout);
                    if (result == null)
                    {
                        logger.debug("No message was returned via replyTo destination");
                        return null;
                    }
                    else
                    {
                        UMOMessageAdapter adapter = connector.getMessageAdapter(result);
                        return new MuleMessage(JmsMessageUtils.getObjectForMessage(result), adapter);
                    }
                }
            }
            return null;
        }
        finally
        {
            connector.closeQuietly(consumer);
            connector.closeQuietly(producer);

            // TODO I wonder if those temporary destinations also implement BOTH
            // interfaces...
            // keep it 'simple' for now
            if (replyTo != null && (replyTo instanceof TemporaryQueue || replyTo instanceof TemporaryTopic))
            {
                if (replyTo instanceof TemporaryQueue)
                {
                    connector.closeQuietly((TemporaryQueue)replyTo);
                }
                else
                {
                    // hope there are no more non-standard tricks from jms vendors
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

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.providers.UMOConnector#sendEvent(org.mule.MuleEvent,
     *      org.mule.providers.MuleEndpoint)
     */
    protected UMOMessage doSend(UMOEvent event) throws Exception
    {
        UMOMessage message = dispatchMessage(event);
        return message;
    }

    /**
     * Make a specific request to the underlying transport
     * 
     * @param endpoint the endpoint to use when connecting to the resource
     * @param timeout the maximum time the operation should block before returning.
     *            The call should return immediately if there is data available. If
     *            no data becomes available before the timeout elapses, null will be
     *            returned
     * @return the result of the request wrapped in a UMOMessage object. Null will be
     *         returned if no data was avaialable
     * @throws Exception if the call to the underlying protocal cuases an exception
     */
    protected UMOMessage doReceive(UMOImmutableEndpoint endpoint, long timeout) throws Exception
    {

        Session session = null;
        Destination dest = null;
        MessageConsumer consumer = null;
        try
        {
            boolean topic = false;
            String resourceInfo = endpoint.getEndpointURI().getResourceInfo();
            topic = (resourceInfo != null && JmsConstants.TOPIC_PROPERTY.equalsIgnoreCase(resourceInfo));

            session = connector.getSession(false, topic);
            dest = connector.getJmsSupport().createDestination(session,
                endpoint.getEndpointURI().getAddress(), topic);
            consumer = connector.getJmsSupport().createConsumer(session, dest, topic);

            try
            {
                Message message = null;
                if (timeout == RECEIVE_NO_WAIT)
                {
                    message = consumer.receiveNoWait();
                }
                else if (timeout == RECEIVE_WAIT_INDEFINITELY)
                {
                    message = consumer.receive();
                }
                else
                {
                    message = consumer.receive(timeout);
                }
                if (message == null)
                {
                    return null;
                }

                message = connector.preProcessMessage(message, session);

                return new MuleMessage(connector.getMessageAdapter(message));
            }
            catch (Exception e)
            {
                connector.handleException(e);
                return null;
            }
        }
        finally
        {
            connector.closeQuietly(consumer);
            connector.closeQuietly(session);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.umo.provider.UMOMessageDispatcher#getDelegateSession()
     */
    public synchronized Object getDelegateSession() throws UMOException
    {
        try
        {
            // Return the session bound to the current transaction
            // if possible
            Session session = connector.getSessionFromTransaction();
            if (session != null)
            {
                return session;
            }
            // Else create a session for this dispatcher and
            // use it each time
            if (delegateSession == null)
            {
                delegateSession = connector.getSession(false, false);
            }
            return delegateSession;
        }
        catch (Exception e)
        {
            throw new MuleException(new org.mule.config.i18n.Message("jms", 3), e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.umo.provider.UMOMessageDispatcher#getConnector()
     */
    public UMOConnector getConnector()
    {
        return connector;
    }

    protected void doDispose()
    {
        // template method
    }

    private class ReplyToListener implements MessageListener
    {
        private Latch latch;
        private Message message;
        private WaitableBoolean released = new WaitableBoolean(false);

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
