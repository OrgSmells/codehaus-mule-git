/*
 * $Header$
 * $Revision$
 * $Date$
 * -----------------------------------------------------------------------------------------------------
 *
 * Copyright (c) Cubis Limited. All rights reserved.
 * http://www.cubis.co.uk 
 * 
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file. 
 *
 */
package org.mule.providers.jms;

import java.util.List;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.Topic;

import org.mule.InitialisationException;
import org.mule.impl.MuleMessage;
import org.mule.providers.TransactedPollingMessageReceiver;
import org.mule.providers.jms.filters.JmsSelectorFilter;
import org.mule.transaction.TransactionCoordination;
import org.mule.umo.UMOComponent;
import org.mule.umo.UMOTransaction;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.provider.UMOConnector;
import org.mule.umo.provider.UMOMessageAdapter;

/**
 * @author <a href="mailto:ross.mason@cubis.co.uk">Ross Mason</a>
 * @author Guillaume Nodet
 * @version $Revision$
 */
public class JmsMessageReceiver extends
		TransactedPollingMessageReceiver {

	private JmsConnector connector;
	private boolean reuseConsumer;
	private boolean reuseSession;
	private Session session;
	private MessageConsumer consumer;
	private long frequency;
	
	public JmsMessageReceiver() {
	}
	
    public JmsMessageReceiver(UMOConnector connector,
            				  UMOComponent component,
            				  UMOEndpoint endpoint) throws InitialisationException {
    	super(connector, component, endpoint, new Long(10));
    	this.connector = (JmsConnector) connector;
    	this.frequency = 10000;
    	this.reuseConsumer = true;
    	this.reuseSession = true;
        receiveMessagesInTransaction = (endpoint.getTransactionConfig() != null);
    }
	
    /**
     * The poll method is overrident from the 
     */
    public void poll() throws Exception
    {
    	try {
			// Create consumer if necessary
			if (consumer == null) {
				createConsumer();
			}
			// Do polling
			super.poll();
		} finally {
			// Close consumer if necessary 
			closeConsumer();
		}
    }
    
	/* (non-Javadoc)
	 * @see org.mule.providers.TransactionEnabledPollingMessageReceiver#getMessages()
	 */
	protected List getMessages() throws Exception {
		// As the session is created outside the transaction, it is not
		// bound to it yet
        UMOTransaction tx = TransactionCoordination.getInstance().getTransaction();
        if (tx != null) {
        	tx.bindResource(connector.getConnection(), session);
        }
		
        // Retrieve message
		Message message = consumer.receive(frequency);
        if (message == null) {
        	if (tx != null) {
        		tx.setRollbackOnly();
        	}
        	return null;
        }
        
        // Process message
        if (logger.isDebugEnabled())
        {
            logger.debug("Message received it is of type: " + message.getClass().getName());
            logger.debug("Message received on " + message.getJMSDestination() + " (" + message.getJMSDestination().getClass().getName() + ")");
        }

        String correlationId = message.getJMSCorrelationID();
        logger.debug("Message CorrelationId is: " + correlationId);
        logger.info("Jms Message Id is: " + message.getJMSMessageID());

        if (message.getJMSRedelivered())
        {
            logger.info("Message with correlationId: " + correlationId + " is redelivered. handing off to Exception Handler");
            handleMessageRedelivered(message, session);
            return null;
        }

        if (tx instanceof JmsClientAcknowledgeTransaction) {
        	tx.bindResource(message, null);
        }
        
        UMOMessageAdapter adapter = connector.getMessageAdapter(message);
        routeMessage(new MuleMessage(adapter));
        return null;
	}

	/* (non-Javadoc)
	 * @see org.mule.providers.TransactionEnabledPollingMessageReceiver#processMessage(java.lang.Object)
	 */
	protected void processMessage(Object msg) throws Exception {
		// This method is never called as the 
		// message is processed when received
	}
	
	protected void closeConsumer() {
		// Close consumer
		if (!reuseSession || !reuseConsumer) {
			JmsUtils.closeQuietly(consumer);
			consumer = null;
		}
		// Do not close session if a transaction is in progress
		// the session will be close by the transaction
		if (!reuseSession) {
			JmsUtils.closeQuietly(session);
			session = null;
		}
	}

	/**
	 * Create a consumer for the jms destination
	 * @throws Exception
	 */
	protected void createConsumer() throws Exception {
		JmsSupport jmsSupport = this.connector.getJmsSupport();
		
		// Create session if none exists
		if (session == null) {
			session = (Session) this.connector.getSession(endpoint);
		}
		
		// Create destination
        String resourceInfo = endpoint.getEndpointURI().getResourceInfo();
        boolean topic = (resourceInfo!=null && "topic".equalsIgnoreCase(resourceInfo));
        Destination dest = jmsSupport.createDestination(session, endpoint.getEndpointURI().getAddress(), topic);

        // Extract jms selector
        String selector=null;
        if (endpoint.getFilter()!=null && endpoint.getFilter() instanceof JmsSelectorFilter) {
            selector = ((JmsSelectorFilter) endpoint.getFilter()).getExpression();
        } else if (endpoint.getProperties() != null) {
            //still allow the selector to be set as a property on the endpoint
            //to be backward compatable
            selector = (String) endpoint.getProperties().get(JmsConnector.JMS_SELECTOR_PROPERTY);
        }
        String tempDurable = (String)endpoint.getProperties().get("durable");
        boolean durable = connector.isDurable();
        if(tempDurable!=null) durable = Boolean.valueOf(tempDurable).booleanValue();

        //Get the durable subscriber name if there is one
        String durableName = (String)endpoint.getProperties().get("durableName");
        if(durableName==null && durable && dest instanceof Topic)
        {
            durableName = "mule." + connector.getName() + "." + endpoint.getEndpointURI().getAddress();
            logger.debug("Jms Connector for this receiver is durable but no durable name has been specified. Defaulting to: " + durableName);
        }

        // Create consumer
        consumer = jmsSupport.createConsumer(session, dest, selector, connector.isNoLocal(), durableName);
	}
	
	/**
	 * Process a redelivered message
	 * 
	 * TODO: if messages are redelivered due to a previous transaction rollback,
	 *       why handle them differently ?
	 */
    protected void handleMessageRedelivered(Message message, Session session)
    {
        String corrId = null;
        try
        {
            corrId = message.getJMSCorrelationID();
        }
        catch (JMSException e)
        {
            corrId = "could not read id: " + e.getMessage();
        }

        String msg = "Message was redelivered, most likely due to a transaction rollback. " +
                "Correlation id was: " + corrId;
        logger.warn(msg);
        MessageRedeliveredException exception = new MessageRedeliveredException(msg, session);
        handleException(message, exception);
    }
}
