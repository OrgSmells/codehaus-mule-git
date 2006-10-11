/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the BSD style
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.providers.jms;

import java.util.List;
import java.util.Vector;

import org.apache.commons.collections.MapUtils;
import org.mule.config.ThreadingProfile;
import org.mule.impl.MuleMessage;
import org.mule.providers.AbstractMessageReceiver;
import org.mule.providers.ConnectException;
import org.mule.providers.jms.filters.JmsSelectorFilter;
import org.mule.transaction.TransactionCallback;
import org.mule.transaction.TransactionCoordination;
import org.mule.transaction.TransactionTemplate;
import org.mule.umo.UMOComponent;
import org.mule.umo.UMOException;
import org.mule.umo.UMOTransaction;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.lifecycle.InitialisationException;
import org.mule.umo.lifecycle.LifecycleException;
import org.mule.umo.manager.UMOWorkManager;
import org.mule.umo.provider.UMOConnector;
import org.mule.umo.provider.UMOMessageAdapter;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.Topic;
import javax.resource.spi.work.Work;
import javax.resource.spi.work.WorkException;

/**
 *
 * Registers a single JmsMessage listener but uses a thread pool to process incoming messages
 * 
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision: 2180 $
 */
public class TransactedSingleResourceJmsMessageReceiver extends AbstractMessageReceiver
{
	//SingleJmsReceiver properties
	 protected JmsConnector connector;
	 protected RedeliveryHandler redeliveryHandler;
	 /*protected MessageConsumer consumer;
	 protected Session session;*/
	 protected boolean startOnConnect = false;
	 //end of SingleJmsReceiver properties
	 
	 //TransactedPollingMessageReceiver properties
	 /** determines whether messages will be received in a transaction template */
	 protected boolean receiveMessagesInTransaction = true;

	 /** determines whether Multiple receivers are created to improve throughput */
	 protected boolean useMultipleReceivers = true;
	//end of TransactedPollingMessageReceiver properties
	 
	 //my Properties
	 List workers=null;
	 //end of my Properties
	 
	/**
	 * @param connector
	 * @param component
	 * @param endpoint
	 * @throws InitialisationException
	 */
	public TransactedSingleResourceJmsMessageReceiver(UMOConnector connector, UMOComponent component, UMOEndpoint endpoint) throws InitialisationException {
		super(connector, component, endpoint);
        this.connector = (JmsConnector) connector;
        
        //TODO check which properties being set in the TransecteJmsMessage receiver are needed...

        try {
            redeliveryHandler = this.connector.createRedeliveryHandler();
            redeliveryHandler.setConnector(this.connector);
        } catch (Exception e) {
            throw new InitialisationException(e, this);
        }
	}

	/* (non-Javadoc)
	 * @see org.mule.providers.AbstractMessageReceiver#doConnect()
	 */
	public void doConnect() throws Exception {
		int numberOfWorkers;
		MessageReceiverWorker currentWorker=null;
		
		//		Connector property overrides any implied value
        useMultipleReceivers = connector.isCreateMultipleTransactedReceivers();
        ThreadingProfile tp = connector.getReceiverThreadingProfile();
        
        //create Workers
        if (useMultipleReceivers && receiveMessagesInTransaction && tp.isDoThreading()) {
        	numberOfWorkers=tp.getMaxThreadsActive();
            workers=new Vector(numberOfWorkers);
            for (int i = 0; i < numberOfWorkers; i++) {
                workers.add(new MessageReceiverWorker(this.connector));
            }
        } else {
            workers=new Vector(0);
            workers.add(new MessageReceiverWorker(this.connector));
        }
        
        //call the doConnect of the Workers
        for(int i=0;i<workers.size();i++)
        {
        	currentWorker=(MessageReceiverWorker)workers.get(i);
        	currentWorker.doConnect();
        }   
	}
	
	public void doStart() throws UMOException
	{
		UMOWorkManager workManager=getWorkManager();
		for(int i=0;i<workers.size();i++)
        {
			try
			{
				workManager.scheduleWork((Work) this.workers.get(i));
			}catch(WorkException e)
			{
				throw new LifecycleException(e, this);
			}
        }
	}

	public void doStop() throws UMOException
	{
		for(int i=0;i<workers.size();i++)
        {
        	((MessageReceiverWorker)workers.get(i)).doStop();        	
        }
	}
	
	/* (non-Javadoc)
	 * @see org.mule.providers.AbstractMessageReceiver#doDisconnect()
	 */
	public void doDisconnect() throws Exception {
		for(int i=0;i<workers.size();i++)
        {
        	((MessageReceiverWorker)workers.get(i)).doDisconnect();
        }		
	}
	
	
	
	protected class MessageReceiverWorker implements Work,MessageListener
	{
		//SingleJmsMessageReceiver
		protected JmsConnector connector;
	    protected MessageConsumer consumer;
	    protected Session session;
	    
	    public MessageReceiverWorker(JmsConnector connector)
	    {
	    	this.connector=connector;
	    }

		/* (non-Javadoc)
		 * @see javax.resource.spi.work.Work#release()
		 */
		public void release() {
			// TODO Auto-generated method stub
			
		}

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		public void run(){
			try
			{
				this.doStart();
			}catch(Exception e)
			{
				//TODO do something with the exception!
			}
		}
		
		public void doConnect() throws Exception
		{
			try {
	            JmsSupport jmsSupport = this.connector.getJmsSupport();
	            // Create session if none exists
	            if (session == null) {
	                session = this.connector.getSession(endpoint);
	            }

	            // Create destination
	            String resourceInfo = endpoint.getEndpointURI().getResourceInfo();
	            boolean topic = (resourceInfo != null && JmsConstants.TOPIC_PROPERTY.equalsIgnoreCase(resourceInfo));

	            //todo MULE20 remove resource Info support
	            if(!topic) {
	                topic = MapUtils.getBooleanValue(endpoint.getProperties(),
	                        JmsConstants.TOPIC_PROPERTY, false);
	            }

	            Destination dest = jmsSupport.createDestination(session, endpoint.getEndpointURI().getAddress(), topic);

	            // Extract jms selector
	            String selector = null;
	            if (endpoint.getFilter() != null && endpoint.getFilter() instanceof JmsSelectorFilter) {
	                selector = ((JmsSelectorFilter) endpoint.getFilter()).getExpression();
	            } else if (endpoint.getProperties() != null) {
	                // still allow the selector to be set as a property on the endpoint
	                // to be backward compatable
	                selector = (String) endpoint.getProperties().get(JmsConstants.JMS_SELECTOR_PROPERTY);
	            }
	            String tempDurable = (String) endpoint.getProperties().get(JmsConstants.DURABLE_PROPERTY);
	            boolean durable = connector.isDurable();
	            if (tempDurable != null) {
	                durable = Boolean.valueOf(tempDurable).booleanValue();
	            }

	            // Get the durable subscriber name if there is one
	            String durableName = (String) endpoint.getProperties().get(JmsConstants.DURABLE_NAME_PROPERTY);
	            if (durableName == null && durable && dest instanceof Topic) {
	                durableName = "mule." + connector.getName() + "." + endpoint.getEndpointURI().getAddress();
	                logger.debug("Jms Connector for this receiver is durable but no durable name has been specified. Defaulting to: "
	                        + durableName);
	            }

	            // Create consumer
	            consumer = jmsSupport.createConsumer(session, dest, selector, connector.isNoLocal(), durableName, topic);
	        } catch (JMSException e) {
	            throw new ConnectException(e, this);
	        }
		}
		
		public void doStart() throws UMOException {
	        try {
	            //We ned to register the listener when start is called in order to only start receiving messages after
	            //start/
	            //If the consumer is null it means that the connection strategy is being run in a separate thread
	            //And hasn't managed to connect yet.
	            if(consumer==null)  {
	                startOnConnect=true;
	            } else {
	                startOnConnect=false;
	                
	                this.consumer.setMessageListener(this);
	            }
	        } catch (JMSException e) {
	            throw new LifecycleException(e, this);
	        }
	    }

		public void doStop() throws UMOException {
	        try {
	            if(consumer!=null) {
	                consumer.setMessageListener(null);
	            }
	        } catch (JMSException e) {
	            throw new LifecycleException(e, this);
	        }
	    }
		
		public void doDisconnect() throws Exception
	    {
	        closeConsumer();
	    }
		
		protected void closeConsumer()
	    {
	        connector.closeQuietly(consumer);
	        consumer = null;
	        connector.closeQuietly(session);
	        session = null;
	    }
		
		/* (non-Javadoc)
		 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
		 */
		public void onMessage(Message message) {
			try {
            	TransactionTemplate tt = new TransactionTemplate(endpoint.getTransactionConfig(),
                        connector.getExceptionListener());

            	if (receiveMessagesInTransaction) {
            		TransactionCallback cb = new MessageTransactionCallback(message) {

						public Object doInTransaction() throws Exception {
							//Get Transaction & Bind Session
							UMOTransaction tx = TransactionCoordination.getInstance().getTransaction();
							if (tx != null) {
					            tx.bindResource(connector.getConnection(), session);
					        }
							if(tx instanceof JmsClientAcknowledgeTransaction)
							{
								tx.bindResource(message, message);
							}
							
							if (logger.isDebugEnabled()) {
				                logger.debug("Message received it is of type: " + message.getClass().getName());
				                if (message.getJMSDestination() != null) {
				                    logger.debug("Message received on " + message.getJMSDestination() + " ("
				                            + message.getJMSDestination().getClass().getName() + ")");
				                } else {
				                    logger.debug("Message received on unknown destination");
				                }
				                logger.debug("Message CorrelationId is: " + message.getJMSCorrelationID());
				                logger.debug("Jms Message Id is: " + message.getJMSMessageID());
				            }

				            if (message.getJMSRedelivered()) {
				                if (logger.isDebugEnabled()) {
				                    logger.debug("Message with correlationId: " + message.getJMSCorrelationID()
				                            + " is redelivered. handing off to Exception Handler");
				                }
				                redeliveryHandler.handleRedelivery(message);
				            }
							
							UMOMessageAdapter adapter = connector.getMessageAdapter(message);
			                routeMessage(new MuleMessage(adapter));
							return null;
						}
            		};
            		tt.execute(cb);
            	}else
            	{
            		UMOMessageAdapter adapter = connector.getMessageAdapter(message);
	                routeMessage(new MuleMessage(adapter));
            	}
            			
                
            } catch (Exception e) {
                getConnector().handleException(e);
            }
			
		}
		
	}
	
}
