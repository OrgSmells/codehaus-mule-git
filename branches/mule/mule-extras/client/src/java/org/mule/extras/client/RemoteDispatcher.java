/*
 * $Header$
 * $Revision$
 * $Date$
 * ------------------------------------------------------------------------------------------------------
 *
 * Copyright (c) Cubis Limited. All rights reserved.
 * http://www.cubis.co.uk
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.mule.extras.client;

import EDU.oswego.cs.dl.util.concurrent.Callable;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.MuleManager;
import org.mule.config.MuleProperties;
import org.mule.impl.MuleEvent;
import org.mule.impl.MuleMessage;
import org.mule.impl.MuleSession;
import org.mule.impl.endpoint.MuleEndpointURI;
import org.mule.impl.internal.events.AdminEvent;
import org.mule.providers.service.ConnectorFactory;
import org.mule.umo.FutureMessageResult;
import org.mule.umo.UMOEvent;
import org.mule.umo.UMOException;
import org.mule.umo.UMOMessage;
import org.mule.umo.UMOSession;
import org.mule.umo.endpoint.MalformedEndpointException;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.provider.UMOMessageDispatcher;
import org.mule.umo.transformer.UMOTransformer;
import org.mule.util.MuleObjectHelper;

import java.util.Map;

/**
 * <code>RemoteDispatcher</code> TODO
 *
 * @author <a href="mailto:ross.mason@cubis.co.uk">Ross Mason</a>
 * @version $Revision$
 */

public class RemoteDispatcher
{
    /**
     * logger used by this class
     */
    protected static transient Log logger = LogFactory.getLog(RemoteDispatcher.class);

    private MuleEndpointURI serverEndpoint;

    private MuleClient client;

    /** Calls made to a remote server are serialised using xstream */
    private XStream xstream = null;

    RemoteDispatcher(MuleClient client, String endpoint, String user, String password) throws MalformedEndpointException
    {
        this(client, endpoint);
        //todo authenticate
    }

    RemoteDispatcher(MuleClient client, String endpoint) throws MalformedEndpointException
    {
        serverEndpoint = new MuleEndpointURI(endpoint);
        xstream = new XStream(new XppDriver());
        this.client = client;
    }

    /**
         * Dispatcher an event asynchronously to a components on a remote Mule instance.
         * Users can endpoint a url to a remote Mule server in the constructor of a Mule client,  by
         * default the default Mule server url tcp://localhost:60504 is used.
         *
         * @param component         the name of the Mule components to dispatch to
         * @param payload           the object that is the payload of the event
         * @param messageProperties any properties to be associated with the payload.
         *                          as null
         * @throws org.mule.umo.UMOException if the dispatch fails or the components or transfromers cannot be found
         */
        public void dispatchToRemoteComponent(String component, Object payload, Map messageProperties) throws UMOException
        {
            doToRemoteComponent(component, payload, messageProperties, true);
        }

        /**
         * sends an event synchronously to a components on a remote Mule instance.
         * Users can endpoint a url to a remote Mule server in the constructor of a Mule client,  by
         * default the default Mule server url tcp://localhost:60504 is used.
         *
         * @param component         the name of the Mule components to send to
         * @param payload           the object that is the payload of the event
         * @param messageProperties any properties to be associated with the payload.
         *                          as null
         * @return the result message if any of the invocation
         * @throws org.mule.umo.UMOException if the dispatch fails or the components or transfromers cannot be found
         */
        public UMOMessage sendToRemoteComponent(String component, Object payload, Map messageProperties) throws UMOException
        {
            return doToRemoteComponent(component, payload, messageProperties, true);
        }

        /**
         * sends an event to a components on a remote Mule instance, while making the result of
         * the event trigger available as a Future result that can be accessed later by client code.
         * Users can endpoint a url to a remote Mule server in the constructor of a Mule client,  by
         * default the default Mule server url tcp://localhost:60504 is used.
         *
         * @param component         the name of the Mule components to send to
         * @param transformers      a comma separated list of transformers to apply to the result message
         * @param payload           the object that is the payload of the event
         * @param messageProperties any properties to be associated with the payload.
         *                          as null
         * @return the result message if any of the invocation
         * @throws org.mule.umo.UMOException if the dispatch fails or the components or transfromers cannot be found
         */
        public FutureMessageResult sendAsyncToRemoteComponent(final String component, String transformers, final Object payload, final Map messageProperties) throws UMOException
        {
            UMOTransformer trans = null;
            FutureMessageResult result = null;
            if (transformers != null)
            {
                trans = MuleObjectHelper.getTransformer(transformers, ",");
                result = new FutureMessageResult(trans);
            } else {
                result = new FutureMessageResult();
            }

            Callable callable = new Callable() {
                public Object call() throws Exception
                {
                    return doToRemoteComponent(component, payload, messageProperties, true);
                }
            };

            result.execute(callable);
            return result;
        }

    public UMOMessage sendRemote(String endpoint, Object payload, Map messageProperties) throws UMOException
    {
        return doToRemote(endpoint, payload, messageProperties, true);
    }

    public void dispatchRemote(String endpoint, Object payload, Map messageProperties) throws UMOException
    {
        doToRemote(endpoint, payload, messageProperties, false);
    }

    public FutureMessageResult sendAsyncRemote(final String endpoint, final Object payload, final Map messageProperties) throws UMOException
    {
        FutureMessageResult result = null;
        result = new FutureMessageResult();

        Callable callable = new Callable() {
            public Object call() throws Exception
            {
                return doToRemote(endpoint, payload, messageProperties, true);
            }
        };

        result.execute(callable);
        return result;
    }

    public UMOMessage receiveRemote(String endpoint, int timeout) throws UMOException
    {
        AdminEvent action = new AdminEvent(AdminEvent.ACTION_RECEIVE, null, endpoint);
        action.setProperty(MuleProperties.MULE_SYNCHRONOUS_RECEIVE_PROPERTY, "true");
        UMOMessage result = dispatchAction(action, true, timeout);
        return result;
    }

    public FutureMessageResult asyncReceiveRemote(final String endpoint, final int timeout) throws UMOException
    {
        FutureMessageResult result = null;
        result = new FutureMessageResult();

        Callable callable = new Callable() {
            public Object call() throws Exception
            {
                return receiveRemote(endpoint, timeout);
            }
        };

        result.execute(callable);
        return result;
    }

    protected UMOMessage doToRemoteComponent(String component, Object payload, Map messageProperties, boolean synchronous) throws UMOException
    {
        UMOMessage message = new MuleMessage(payload, messageProperties);
        message.setBooleanProperty(MuleProperties.MULE_SYNCHRONOUS_RECEIVE_PROPERTY, synchronous);

        AdminEvent action = new AdminEvent(AdminEvent.ACTION_INVOKE, message, "mule://" + component);
        UMOMessage result = dispatchAction(action, synchronous, MuleManager.getConfiguration().getSynchronousEventTimeout());
        return result;
    }

    protected UMOMessage doToRemote(String endpoint, Object payload, Map messageProperties, boolean synchronous) throws UMOException
    {
        UMOMessage message = new MuleMessage(payload, messageProperties);
        message.setProperty(MuleProperties.MULE_SYNCHRONOUS_RECEIVE_PROPERTY, String.valueOf(synchronous));
        AdminEvent action = new AdminEvent((synchronous ? AdminEvent.ACTION_SEND : AdminEvent.ACTION_DISPATCH), message, endpoint);

        UMOMessage result = dispatchAction(action, synchronous, -1);
        return result;
    }

    protected UMOMessage dispatchAction(AdminEvent action, boolean synchronous, int timeout) throws UMOException
    {
        String xml = xstream.toXML(action);

        UMOMessage message = new MuleMessage(xml, (action.getMessage()==null ? null :action.getMessage().getProperties()));

        message.addProperties(action.getProperties());
        UMOEndpoint endpoint = ConnectorFactory.createEndpoint(serverEndpoint, UMOEndpoint.ENDPOINT_TYPE_SENDER);
        UMOSession session = new MuleSession(null);
        UMOEvent event = new MuleEvent(message, endpoint, session, true);
        event.setTimeout(timeout);
        logger.debug("MuleClient sending remote call to: " + action.getEndpoint() + ". At " + serverEndpoint.toString() + " .Event is: " + event);

        UMOMessageDispatcher dispatcher = endpoint.getConnector().getDispatcher(serverEndpoint.getAddress());

        UMOMessage result = null;

        try
        {
            if(synchronous) {
                result = dispatcher.send(event);
            } else {
                dispatcher.dispatch(event);
                return null;
            }
            if (result != null) {
                String resultXml = result.getPayloadAsString();
                if(resultXml!=null && resultXml.length() > 0) {
                    //System.out.println("Remote dispatcher received result:\n" + resultXml);
                    result = (UMOMessage)xstream.fromXML(resultXml);
                } else {
                    return null;
                }
            }

        } catch (Exception e)
        {
            throw new MuleClientException("Failed to send remote event: " + e.getMessage(), e);
        }

        logger.debug("Result of MuleClient remote call is: " + (result==null ? "null" : result.getPayload()));
        return result;
    }

    public void close() throws MuleClientException {

    }
}
