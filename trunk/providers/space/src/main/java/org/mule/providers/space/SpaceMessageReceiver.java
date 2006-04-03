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
package org.mule.providers.space;

import org.mule.config.i18n.Message;
import org.mule.config.i18n.Messages;
import org.mule.impl.MuleMessage;
import org.mule.providers.AbstractMessageReceiver;
import org.mule.providers.ConnectException;
import org.mule.umo.UMOComponent;
import org.mule.umo.UMOMessage;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.lifecycle.InitialisationException;
import org.mule.umo.provider.UMOConnector;
import org.mule.umo.provider.UMOMessageAdapter;
import org.mule.umo.space.UMOSpace;
import org.mule.umo.space.UMOSpaceException;

import javax.resource.spi.work.Work;
import javax.resource.spi.work.WorkException;
import javax.resource.spi.work.WorkManager;

import java.util.Properties;

/**
 * <code>SpaceMessageReceiver</code> registers a listener on a Space, which can be a JavaSpace, Rio space,
 * JCache implementation of an internal Mule space.
 *
 * @version $Revision$
 */
public class SpaceMessageReceiver extends AbstractMessageReceiver implements Work {

    private UMOSpace space;
    private SpaceConnector connector;

    public SpaceMessageReceiver(UMOConnector connector, UMOComponent component, UMOEndpoint endpoint)
            throws InitialisationException {
        super(connector, component, endpoint);
        this.connector = (SpaceConnector)connector;
    }

    public void doConnect() throws ConnectException {
        String destination = endpoint.getEndpointURI().getAddress();

        Properties props = new Properties();
        props.putAll(endpoint.getProperties());
        try {
			logger.info("Connecting to space: " + destination);
			space = connector.getSpace(endpoint);
		} catch (UMOSpaceException e) {
			throw new ConnectException(new Message("space", 1, destination), e, this);
		}
        try {
            getWorkManager().scheduleWork(this, WorkManager.INDEFINITE, null, null);
        } catch (WorkException e) {
            throw new ConnectException(new Message(Messages.FAILED_TO_SCHEDULE_WORK), e, this);
        }
    }

    public void doDisconnect() throws ConnectException {
        //Todo How should we disconnect from a Space, maby remove the ref to it for the connector??
    }


    public void run() {
        while (!disposing.get()) {
            if (connector.isStarted() && !disposing.get()) {
                if(logger.isTraceEnabled()) {
                    logger.trace("Receiver starting on space: " + space);
                }

                    try {
                        Object message = space.take(Long.MAX_VALUE);
                        Work work = createWork(space, message);
                        try {
                            getWorkManager().scheduleWork(work, WorkManager.IMMEDIATE, null, null);
                        } catch (WorkException e) {
                            logger.error("GS Server receiver Work was not processed: " + e.getMessage(), e);
                        }
                    } catch (Exception e) {
                        handleException(e);
                    }

                }
            }
        }


    public void release() {
    }

    protected void doDispose() {
    }

    protected Work createWork(UMOSpace space, Object message) throws Exception {
        return new SpaceWorker(space, message);
    }

    protected class SpaceWorker implements Work {
        private UMOSpace space;
        private Object message;

        public SpaceWorker(UMOSpace space, Object message) {
            this.space = space;
            this.message = message;
        }

        public void release() {
        }


        /**
         * Accept requests on a given template
         */
        public void run() {
            try {
                if(logger.isTraceEnabled()) {
                    logger.trace("worker listening on space " + space );
                }

                    //todo transactions

                    UMOMessageAdapter adapter = connector.getMessageAdapter(message);
                    UMOMessage returnMessage = routeMessage(new MuleMessage(adapter), endpoint.isSynchronous());
//                    if (returnMessage != null) {
//                        space.put(returnMessage.getPayload(), Long.MAX_VALUE);
//                    }

            } catch (Exception e) {
                handleException(e);
            } finally {
                release();
            }
        }
    }
}
