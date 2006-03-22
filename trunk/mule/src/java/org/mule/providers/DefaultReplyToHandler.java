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

package org.mule.providers;

import edu.emory.mathcs.backport.java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.config.MuleProperties;
import org.mule.config.i18n.Message;
import org.mule.config.i18n.Messages;
import org.mule.impl.MuleEvent;
import org.mule.impl.endpoint.MuleEndpoint;
import org.mule.impl.endpoint.MuleEndpointURI;
import org.mule.impl.model.AbstractComponent;
import org.mule.umo.UMOEvent;
import org.mule.umo.UMOException;
import org.mule.umo.UMOMessage;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.endpoint.UMOEndpointURI;
import org.mule.umo.provider.DispatchException;
import org.mule.umo.transformer.UMOTransformer;

import java.util.Map;

/**
 * <code>DefaultReplyToHandler</code> is responsible for processing a message
 * replyTo header.
 * 
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */

public class DefaultReplyToHandler implements ReplyToHandler
{
    private UMOTransformer transformer;

    private Map endpointCache = new ConcurrentHashMap();
    /**
     * logger used by this class
     */
    protected static transient Log logger = LogFactory.getLog(DefaultReplyToHandler.class);

    public DefaultReplyToHandler(UMOTransformer transformer)
    {
        this.transformer = transformer;
    }

    public void processReplyTo(UMOEvent event, UMOMessage returnMessage, Object replyTo)
            throws UMOException
    {
        if (logger.isDebugEnabled()) {
            logger.debug("sending reply to: " + returnMessage.getReplyTo());
        }
        String replyToEndpoint = replyTo.toString();

        // get the endpoint for this url
        UMOEndpoint endpoint = getEndpoint(event, replyToEndpoint);
        if (transformer == null) {
            transformer = event.getEndpoint().getResponseTransformer();
        }
        if (transformer != null) {
            endpoint.setTransformer(transformer);
        }

        // make sure remove the replyTo property as not cause a a forever
        // replyto loop
        returnMessage.removeProperty(MuleProperties.MULE_REPLY_TO_PROPERTY);

        // Create the replyTo event asynchronous
        UMOEvent replyToEvent = new MuleEvent(returnMessage, endpoint, event.getSession(), false);

        // dispatch the event
        try {
            endpoint.getConnector().getDispatcher(replyTo.toString()).dispatch(replyToEvent);
            if (logger.isInfoEnabled()) {
                logger.info("reply to sent: " + endpoint);
            }
            ((AbstractComponent)event.getComponent()).getStatistics().incSentReplyToEvent();
        }
        catch (Exception e) {
            throw new DispatchException(new Message(Messages.FAILED_TO_DISPATCH_TO_REPLYTO_X, endpoint),
                    replyToEvent.getMessage(), replyToEvent.getEndpoint(), e);
        }

    }

    protected UMOEndpoint getEndpoint(UMOEvent event, String endpointUri) throws UMOException
    {
        UMOEndpoint endpoint = (UMOEndpoint)endpointCache.get(endpointUri);
        if (endpoint == null) {
            UMOEndpointURI ep = new MuleEndpointURI(endpointUri);
            endpoint = MuleEndpoint.getOrCreateEndpointForUri(ep, UMOEndpoint.ENDPOINT_TYPE_SENDER);
            endpointCache.put(endpointUri, endpoint);
        }
        return endpoint;
    }

    public UMOTransformer getTransformer()
    {
        return transformer;
    }

    public void setTransformer(UMOTransformer transformer)
    {
        this.transformer = transformer;
    }
}
