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
 *
 */
package org.mule.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.MuleManager;
import org.mule.config.MuleProperties;
import org.mule.config.i18n.Message;
import org.mule.config.i18n.Messages;
import org.mule.impl.endpoint.MuleEndpoint;
import org.mule.providers.AbstractConnector;
import org.mule.umo.UMOComponent;
import org.mule.umo.UMOEvent;
import org.mule.umo.UMOException;
import org.mule.umo.UMOMessage;
import org.mule.umo.UMOSession;
import org.mule.umo.endpoint.EndpointNotFoundException;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.provider.DispatchException;
import org.mule.umo.provider.ReceiveException;
import org.mule.umo.provider.UMOConnector;
import org.mule.umo.provider.UMOMessageDispatcher;
import org.mule.umo.provider.UMOSessionHandler;
import org.mule.umo.routing.UMOOutboundMessageRouter;
import org.mule.umo.security.UMOSecurityContext;
import org.mule.util.UUID;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <code>MuleSession</code> manages the interaction and distribution of events
 * for Mule UMOs.
 * 
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */

public final class MuleSession implements UMOSession
{
    /**
     * logger used by this class
     */
    private static transient Log logger = LogFactory.getLog(MuleSession.class);

    /**
     * The Mule Component associated with the component
     */
    private UMOComponent component = null;

    /**
     * Determines if the component is valid
     */
    private boolean valid = true;

    private String id;

    private UMOSecurityContext securityContext;

    private Map properties = null;

    public MuleSession(UMOComponent component) {
        properties = new HashMap();
        id = UUID.getUUID();
        this.component = component;
    }

    public MuleSession(UMOMessage message, UMOSessionHandler requestSessionHandler, UMOComponent component) throws UMOException {
        this(message, requestSessionHandler);
        if (component == null) {
            throw new IllegalArgumentException(new Message(Messages.PROPERTIES_X_NOT_SET, "component").toString());
        }
        this.component = component;
    }

    public MuleSession(UMOMessage message, UMOSessionHandler requestSessionHandler) throws UMOException {

        if (requestSessionHandler == null) {
            throw new IllegalArgumentException(new Message(Messages.PROPERTIES_X_NOT_SET, "requestSessionHandler").toString());
        }

        if (message == null) {
            throw new IllegalArgumentException(new Message(Messages.PROPERTIES_X_NOT_SET, "message").toString());
        }

        properties = new HashMap();
        requestSessionHandler.populateSession(message, this);
        id = (String)getProperty(requestSessionHandler.getSessionIDKey());
        if(id==null) {
            id = UUID.getUUID();
            if(logger.isDebugEnabled()) {
                logger.debug("There is no session id on the request using key: " +requestSessionHandler.getSessionIDKey() +
                        ". Generating new session id: " + id);
            }
        } else if(logger.isDebugEnabled()) {
            logger.debug("Got session with id: " + id);
        }
    }

    public void dispatchEvent(UMOMessage message) throws UMOException
    {
        UMOOutboundMessageRouter router = component.getDescriptor().getOutboundRouter();
        if (router == null) {
            throw new EndpointNotFoundException(new Message(Messages.NO_OUTBOUND_ROUTER_SET_ON_X,
                                                            component.getDescriptor().getName()));
        }
        router.route(message, this, false);
    }

    public void dispatchEvent(UMOMessage message, String endpointName) throws UMOException
    {
        dispatchEvent(message, MuleManager.getInstance().lookupEndpoint(endpointName));
    }

    public void dispatchEvent(UMOMessage message, UMOEndpoint endpoint) throws UMOException
    {
        if (component == null) {
            throw new IllegalStateException(new Message(Messages.X_IS_NULL, "Component").getMessage());
        } else if (endpoint == null) {
            endpoint = component.getDescriptor().getOutboundEndpoint();
        }

        logger.debug("Session has received asynchronous event on: " + endpoint);
        UMOEvent event = createOutboundEvent(message, endpoint, RequestContext.getEvent());
        dispatchEvent(event);
    }

    public UMOMessage sendEvent(UMOMessage message, String endpointName) throws UMOException
    {
        return sendEvent(message, MuleManager.getInstance().lookupEndpoint(endpointName));
    }

    public UMOMessage sendEvent(UMOMessage message) throws UMOException
    {
        UMOOutboundMessageRouter router = component.getDescriptor().getOutboundRouter();
        if (router == null) {
            throw new EndpointNotFoundException(new Message(Messages.NO_OUTBOUND_ROUTER_SET_ON_X,
                                                            component.getDescriptor().getName()));
        }
        return router.route(message, this, true);
    }

    public UMOMessage sendEvent(UMOMessage message, UMOEndpoint endpoint) throws UMOException
    {
        if (component == null) {
            throw new IllegalStateException(new Message(Messages.X_IS_NULL, "Component").getMessage());
        } else if (endpoint == null) {
            endpoint = component.getDescriptor().getOutboundEndpoint();
        }

        logger.debug("Session has received synchronous event on endpoint: " + endpoint);

        UMOEvent event = createOutboundEvent(message, endpoint, RequestContext.getEvent());
        UMOMessage result = sendEvent(event);

        //Handles the situation where a response has been received via a remote ReplyTo channel.
        //TODO RM: not sure we need this bit of code??
//        if(endpoint.isRemoteSync() && endpoint.getResponseTransformer()!=null && result!=null) {
//            Object response = endpoint.getResponseTransformer().transform(result.getPayload());
//            result = new MuleMessage(response, result.getProperties(), result);
//        }
        if(result!=null) {
            RequestContext.rewriteEvent(result);
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.umo.UMOSession#dispatchEvent(org.mule.umo.UMOEvent)
     */
    public void dispatchEvent(UMOEvent event) throws UMOException
    {
        if (event.getEndpoint().canSend()) {
            try {
                logger.debug("dispatching event: " + event);
                UMOConnector connector = event.getEndpoint().getConnector();
                UMOMessageDispatcher dispatcher = connector.getDispatcher(event.getEndpoint().getEndpointURI().getAddress());

                if(connector instanceof AbstractConnector) {
                    ((AbstractConnector)connector).getSessionHandler().writeSession(event.getMessage(), this);
                } else {
                    //Todo in Mule20 we'll flatten the Connector hierachy
                    logger.warn("A session handler could not be obtained, using  default");
                    new MuleSessionHandler().writeSession(event.getMessage(), this);
                }
                dispatcher.dispatch(event);
            } catch (Exception e) {
                throw new DispatchException(event.getMessage(), event.getEndpoint(), e);
            }
        } else if (component != null) {
            logger.debug("dispatching event to component: " + component.getDescriptor().getName() + " event is: "
                    + event);
            component.dispatchEvent(event);
        } else {
            throw new DispatchException(new Message(Messages.NO_COMPONENT_FOR_ENDPOINT),
                                        event.getMessage(),
                                        event.getEndpoint());
        }
    }

    public String getId()
    {
        return id;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.umo.UMOSession#sendEvent(org.mule.umo.UMOEvent)
     */
    public UMOMessage sendEvent(UMOEvent event) throws UMOException
    {
        int timeout = event.getMessage().getIntProperty(MuleProperties.MULE_EVENT_TIMEOUT_PROPERTY, -1);
        if (timeout >= 0) {
            event.setTimeout(timeout);
        }

        if (event.getEndpoint().canSend()) {
            try {
                logger.debug("sending event: " + event);

                UMOConnector connector = event.getEndpoint().getConnector();
                UMOMessageDispatcher dispatcher = connector.getDispatcher(event.getEndpoint().getEndpointURI().getAddress());

                if(connector instanceof AbstractConnector) {
                    ((AbstractConnector)connector).getSessionHandler().writeSession(event.getMessage(), this);
                } else {
                    //Todo in Mule20 we'll flatten the Connector hierachy
                    logger.warn("A session handler could not be obtained, using  default");
                    new MuleSessionHandler().writeSession(event.getMessage(), this);
                }
                return dispatcher.send(event);
            } catch (UMOException e) {
                throw e;
            } catch (Exception e) {
                throw new DispatchException(event.getMessage(), event.getEndpoint(), e);
            }

        } else if (component != null) {
            logger.debug("dispatching event to component: " + component.getDescriptor().getName() + " event is: "
                    + event);
            return component.sendEvent(event);

        } else {
            throw new DispatchException(new Message(Messages.NO_COMPONENT_FOR_ENDPOINT),
                                        event.getMessage(),
                                        event.getEndpoint());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.umo.UMOSession#isValid()
     */
    public boolean isValid()
    {
        return valid;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.umo.UMOSession#setValid(boolean)
     */
    public void setValid(boolean value)
    {
        valid = value;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.umo.UMOSession#receiveEvent(org.mule.umo.endpoint.UMOEndpoint,
     *      long, org.mule.umo.UMOEvent)
     */
    public UMOMessage receiveEvent(String endpointName, long timeout) throws UMOException
    {
        UMOEndpoint endpoint = MuleEndpoint.getOrCreateEndpointForUri(endpointName, UMOEndpoint.ENDPOINT_TYPE_RECEIVER);
        return receiveEvent(endpoint, timeout);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.umo.UMOSession#receiveEvent(org.mule.umo.endpoint.UMOEndpoint,
     *      long, org.mule.umo.UMOEvent)
     */
    public UMOMessage receiveEvent(UMOEndpoint endpoint, long timeout) throws UMOException
    {
        try {
            UMOMessageDispatcher dispatcher = endpoint.getConnector().getDispatcher(endpoint.getEndpointURI()
                                                                                            .getAddress());
            return dispatcher.receive(endpoint.getEndpointURI(), timeout);
        } catch (Exception e) {
            throw new ReceiveException(endpoint.getEndpointURI(), timeout, e);
        }
    }

    public UMOEvent createOutboundEvent(UMOMessage message, UMOEndpoint endpoint, UMOEvent previousEvent)
            throws UMOException
    {
        if (endpoint == null) {
            throw new DispatchException(new Message(Messages.X_IS_NULL, "Outbound Endpoint"), message, endpoint);
        }

        logger.debug("Creating event with data: " + message.getPayload().getClass().getName()
                + ", for endpoint: " + endpoint);

        // Use default transformer if none is set
        if (endpoint.getTransformer() == null) {
            if (endpoint.getConnector() instanceof AbstractConnector) {
                endpoint.setTransformer(((AbstractConnector) endpoint.getConnector()).getDefaultOutboundTransformer());
                logger.debug("Using default connector outbound transformer: " + endpoint.getTransformer());
            }
        }
        try {
            UMOEvent event;
            if (previousEvent != null) {
                event = new MuleEvent(message, endpoint, component, previousEvent);
            } else {
                event = new MuleEvent(message, endpoint, this, false, null);
            }
            return event;
        } catch (Exception e) {
            throw new DispatchException(new Message(Messages.FAILED_TO_CREATE_X, "Event"), message, endpoint, e);
        }
    }

    /**
     * @return Returns the component.
     */
    public UMOComponent getComponent()
    {
        return component;
    }

    void setComponent(UMOComponent component)
    {
        this.component = component;
    }

    /**
     * The security context for this session. If not null outbound, inbound
     * and/or method invocations will be authenticated using this context
     * 
     * @param context the context for this session or null if the request is not
     *            secure.
     */
    public void setSecurityContext(UMOSecurityContext context)
    {
        securityContext = context;
    }

    /**
     * The security context for this session. If not null outbound, inbound
     * and/or method invocations will be authenticated using this context
     * 
     * @return the context for this session or null if the request is not
     *         secure.
     */
    public UMOSecurityContext getSecurityContext()
    {
        return securityContext;
    }

    /**
     * Will set a session level property.  These will either be stored and retrieved using the underlying
     * transport mechanism of stored using a default mechanism
     *
     * @param key   the key for the object data being stored on the session
     * @param value the value of the session data
     */
    public void setProperty(Object key, Object value) {
        properties.put(key, value);
    }

    /**
     * Will retrieve a session level property.
     *
     * @param key the key for the object data being stored on the session
     * @return the value of the session data or null if the property does not exist
     */
    public Object getProperty(Object key) {
        return properties.get(key);
    }

    /**
     * Will retrieve a session level property and remove it from the session
     *
     * @param key the key for the object data being stored on the session
     * @return the value of the session data or null if the property does not exist
     */
    public Object removeProperty(Object key) {
        return properties.remove(key);
    }

    /**
     * Returns an iterater of property keys for the session properties on this session
     *
     * @return an iterater of property keys for the session properties on this session
     */
    public Iterator getPropertyNames() {
        return properties.keySet().iterator();
    }
}
