/*
 * $Header$
 * $Revision$
 * $Date$
 * ------------------------------------------------------------------------------------------------------
 *
 * Copyright (c) SymphonySoft Limited. All rights reserved.
 * http://www.symphonysoft.com
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.mule.routing.response;

import EDU.oswego.cs.dl.util.concurrent.ConcurrentHashMap;
import EDU.oswego.cs.dl.util.concurrent.Latch;
import EDU.oswego.cs.dl.util.concurrent.Sync;
import EDU.oswego.cs.dl.util.concurrent.SynchronizedBoolean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.config.MuleProperties;
import org.mule.config.i18n.Message;
import org.mule.config.i18n.Messages;
import org.mule.routing.inbound.EventGroup;
import org.mule.umo.UMOEvent;
import org.mule.umo.UMOMessage;
import org.mule.umo.routing.ResponseTimeoutException;
import org.mule.umo.routing.RoutingException;

import java.util.Map;

/**
 * <code>AbstractResponseAggregator</code> provides a base class for implementing
 * response aggregator routers. This provides a thread-safe implemenetation and allows
 * developers to customise how and when events are grouped and collated.
 *
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */
public abstract class AbstractResponseAggregator extends AbstractResponseRouter
{
    /**
     * logger used by this class
     */
    protected transient Log logger = LogFactory.getLog(getClass());

    private Map responseEvents = new ConcurrentHashMap();
    private Map locks = new ConcurrentHashMap();

    protected long timeout = 5000;

    protected Map eventGroups = new ConcurrentHashMap();
    private Object lock = new Object();

    public void process(UMOEvent event) throws RoutingException
    {
        SynchronizedBoolean doAggregate = new SynchronizedBoolean(false);
        EventGroup eg = addEvent(event);
        doAggregate.commit(false, shouldAggregate(eg));

        if (doAggregate.get())
        {
            synchronized(lock) {
                UMOMessage returnMessage = aggregateEvents(eg);
                String id = eg.getGroupId();
                removeGroup(id);
                responseEvents.put(id, returnMessage);
                Sync s = (Sync)locks.get(id);
                if(s==null) {
                	if (logger.isDebugEnabled()) {
                		logger.debug("Creating latch for " + id + " in " + this);
                	}
                    s = new Latch();
                    locks.put(id, s);
                }
                s.release();
            }
        }
    }

    /**
     * Adds the event to an event group. Groups are defined by the correlationId
     * on the message.  If no correlationId is set a default group is created for
     * all events without a correlationId.
     * If there is no group for the current correlationId one will be created and added
     * to the router.
     *
     * @param event
     * @return
     */
    protected EventGroup addEvent(UMOEvent event) throws RoutingException
    {
        String cId = (String) correlationExtractor.getProperty(MuleProperties.MULE_CORRELATION_ID_PROPERTY, event.getMessage());
        if (cId == null) {
            throw new RoutingException(new Message(Messages.NO_CORRELATION_ID), event.getMessage(), event.getEndpoint());
        }
        EventGroup eg = (EventGroup) eventGroups.get(cId);
        if(logger.isDebugEnabled()) logger.debug("Adding event to response aggregator group: " + cId);
        if (eg == null)
        {
            eg = new EventGroup(cId);
            eg.addEvent(event);
            eventGroups.put(eg.getGroupId(), eg);
        } else
        {
            eg.addEvent(event);
        }
        return eg;
    }

    protected void removeGroup(String id)
    {
         eventGroups.remove(id);
    }

    public UMOMessage getResponse(UMOMessage message) throws RoutingException
    {
        String messageId = null;
        messageId = message.getUniqueId();
        if (logger.isDebugEnabled()) {
            logger.debug("Waiting for response for message id: " + messageId + " in " + this);
        }

        Sync s = (Sync)locks.get(messageId);
        if(s==null) {
    		logger.debug("Got response but no one is waiting for it yet. Creating latch for " + messageId + " in " + this);
            s = new Latch();
            locks.put(messageId, s);
        } else {
            logger.debug("Got latch for message: " + messageId);
        }

        boolean b = false;
        try
        {
            logger.debug("Waiting for response to message: " + messageId);
            if(timeout<=0) {
                s.acquire();
                b = true;
            } else {
                b = s.attempt(timeout);
            }
        } catch (InterruptedException e)
        {
            logger.error(e.getMessage(), e);
        }
        if(!b) {
           throw new ResponseTimeoutException(new Message(Messages.RESPONSE_TIMED_OUT_X_WAITING_FOR_ID_X, String.valueOf(timeout), messageId), message, null);
        }

        UMOMessage result = (UMOMessage)responseEvents.get(messageId);
        locks.remove(messageId);
        if(result==null) {
            //this should never happen ,just using it as a safe gaurd for now
            throw new IllegalStateException("Response Message is null");
        }
        return result;
    }

    public long getTimeout()
    {
        return timeout;
    }

    public void setTimeout(long timeout)
    {
        this.timeout = timeout;
    }

    /**
     * Determines if the event group is ready to be aggregated.
     * if the group is ready to be aggregated (this is entirely up
     * to the application. it could be determined by volume, last modified time
     * or some oher criteria based on the last event received)
     *
     * @param events
     * @return
     */
    protected abstract boolean shouldAggregate(EventGroup events);

    /**
     * This method is invoked if the shouldAggregate method is called and returns
     * true.  Once this method returns an aggregated message the event group is removed
     * from the router
     *
     * @param events the event group for this request
     * @return an aggregated message
     * @throws RoutingException if the aggregation fails.  in this scenario the whole
     *                          event group is removed and passed to the exception handler for this
     *                          componenet
     */
    protected abstract UMOMessage aggregateEvents(EventGroup events) throws RoutingException;
}
