/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.extras.client;

import org.mule.DefaultMuleMessage;
import org.mule.DefaultMuleEvent;
import org.mule.DefaultMuleSession;
import org.mule.MuleSessionHandler;
import org.mule.RegistryContext;
import org.mule.RequestContext;
import org.mule.api.FutureMessageResult;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.api.config.MuleProperties;
import org.mule.api.endpoint.EndpointBuilder;
import org.mule.api.endpoint.EndpointFactory;
import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.api.lifecycle.Disposable;
import org.mule.api.security.Credentials;
import org.mule.api.transformer.wire.WireFormat;
import org.mule.api.transport.DispatchException;
import org.mule.context.notification.AdminNotification;
import org.mule.security.MuleCredentials;
import org.mule.transformer.wire.SerializationWireFormat;
import org.mule.transformer.TransformerUtils;
import org.mule.transport.AbstractConnector;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Map;

import edu.emory.mathcs.backport.java.util.concurrent.Callable;
import edu.emory.mathcs.backport.java.util.concurrent.Executor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <code>RemoteDispatcher</code> is used to make and receive requests to a remote
 * Mule instance. It is used to proxy requests to Mule using the Server URL as the
 * transport channel.
 */

public class RemoteDispatcher implements Disposable
{

    /**
     * logger used by this class
     */
    protected static final Log logger = LogFactory.getLog(RemoteDispatcher.class);

    /**
     * dispatch destination
     */
    private ImmutableEndpoint asyncServerEndpoint;
    private ImmutableEndpoint syncServerEndpoint;
    private Credentials credentials = null;

    /**
     * an ExecutorService for async messages (optional)
     */
    private Executor asyncExecutor;

    /**
     * calls made to a remote server are serialised using a wireformat
     */
    private WireFormat wireFormat;

    protected RemoteDispatcher(String endpoint, Credentials credentials) throws MuleException
    {
        this(endpoint);
        this.credentials = credentials;
    }

    protected RemoteDispatcher(String endpoint) throws MuleException
    {
        EndpointFactory endpointFactory = RegistryContext.getRegistry().lookupEndpointFactory();
        asyncServerEndpoint = endpointFactory.getOutboundEndpoint(endpoint);
        
        EndpointBuilder endpointBuilder = endpointFactory.getEndpointBuilder(endpoint);
        endpointBuilder.setRemoteSync(true);
        syncServerEndpoint = RegistryContext.getRegistry().lookupEndpointFactory().getOutboundEndpoint(
            endpointBuilder);

        wireFormat = new SerializationWireFormat();
    }

    protected void setExecutor(Executor e)
    {
        this.asyncExecutor = e;
    }

    /**
     * Dispatcher an event asynchronously to a components on a remote Mule instance.
     * Users can endpoint a url to a remote Mule server in the constructor of a Mule
     * client, by default the default Mule server url tcp://localhost:60504 is used.
     * 
     * @param service the name of the Mule components to dispatch to
     * @param payload the object that is the payload of the event
     * @param messageProperties any properties to be associated with the payload. as
     *            null
     * @throws org.mule.api.MuleException if the dispatch fails or the components or
     *             transfromers cannot be found
     */
    public void dispatchToRemoteComponent(String component, Object payload, Map messageProperties)
        throws MuleException
    {
        doToRemoteComponent(component, payload, messageProperties, false);
    }

    /**
     * sends an event synchronously to a components on a remote Mule instance. Users
     * can endpoint a url to a remote Mule server in the constructor of a Mule
     * client, by default the default Mule server url tcp://localhost:60504 is used.
     * 
     * @param service the name of the Mule components to send to
     * @param payload the object that is the payload of the event
     * @param messageProperties any properties to be associated with the payload. as
     *            null
     * @return the result message if any of the invocation
     * @throws org.mule.api.MuleException if the dispatch fails or the components or
     *             transfromers cannot be found
     */
    public MuleMessage sendToRemoteComponent(String component, Object payload, Map messageProperties)
        throws MuleException
    {
        return doToRemoteComponent(component, payload, messageProperties, true);
    }

    /**
     * sends an event to a components on a remote Mule instance, while making the
     * result of the event trigger available as a Future result that can be accessed
     * later by client code. Users can endpoint a url to a remote Mule server in the
     * constructor of a Mule client, by default the default Mule server url
     * tcp://localhost:60504 is used.
     * 
     * @param service the name of the Mule components to send to
     * @param transformers a comma separated list of transformers to apply to the
     *            result message
     * @param payload the object that is the payload of the event
     * @param messageProperties any properties to be associated with the payload. as
     *            null
     * @return the result message if any of the invocation
     * @throws org.mule.api.MuleException if the dispatch fails or the components or
     *             transfromers cannot be found
     */
    public FutureMessageResult sendAsyncToRemoteComponent(final String component,
                                                          String transformers,
                                                          final Object payload,
                                                          final Map messageProperties) throws MuleException
    {
        Callable callable = new Callable()
        {
            public Object call() throws Exception
            {
                return doToRemoteComponent(component, payload, messageProperties, true);
            }
        };

        FutureMessageResult result = new FutureMessageResult(callable);

        if (asyncExecutor != null)
        {
            result.setExecutor(asyncExecutor);
        }

        if (transformers != null)
        {
            result.setTransformers(TransformerUtils.getTransformers(transformers));
        }

        result.execute();
        return result;
    }

    public MuleMessage sendRemote(String endpoint, Object payload, Map messageProperties, int timeout)
        throws MuleException
    {
        return doToRemote(endpoint, payload, messageProperties, true, timeout);
    }

    public MuleMessage sendRemote(String endpoint, Object payload, Map messageProperties) throws MuleException
    {
        return doToRemote(endpoint, payload, messageProperties, true, RegistryContext.getConfiguration()
            .getDefaultSynchronousEventTimeout());
    }

    public void dispatchRemote(String endpoint, Object payload, Map messageProperties) throws MuleException
    {
        doToRemote(endpoint, payload, messageProperties, false, -1);
    }

    public FutureMessageResult sendAsyncRemote(final String endpoint,
                                               final Object payload,
                                               final Map messageProperties) throws MuleException
    {
        Callable callable = new Callable()
        {
            public Object call() throws Exception
            {
                return doToRemote(endpoint, payload, messageProperties, true, -1);
            }
        };

        FutureMessageResult result = new FutureMessageResult(callable);

        if (asyncExecutor != null)
        {
            result.setExecutor(asyncExecutor);
        }

        result.execute();
        return result;
    }

    public MuleMessage receiveRemote(String endpoint, int timeout) throws MuleException
    {
        AdminNotification action = new AdminNotification(null, AdminNotification.ACTION_RECEIVE, endpoint);
        action.setProperty(MuleProperties.MULE_REMOTE_SYNC_PROPERTY, "true");
        action.setProperty(MuleProperties.MULE_EVENT_TIMEOUT_PROPERTY, new Long(timeout));
        MuleMessage result = dispatchAction(action, true, timeout);
        return result;
    }

    public FutureMessageResult asyncReceiveRemote(final String endpoint, final int timeout)
        throws MuleException
    {
        Callable callable = new Callable()
        {
            public Object call() throws Exception
            {
                return receiveRemote(endpoint, timeout);
            }
        };

        FutureMessageResult result = new FutureMessageResult(callable);

        if (asyncExecutor != null)
        {
            result.setExecutor(asyncExecutor);
        }

        result.execute();
        return result;
    }

    protected MuleMessage doToRemoteComponent(String component,
                                             Object payload,
                                             Map messageProperties,
                                             boolean synchronous) throws MuleException
    {
        MuleMessage message = new DefaultMuleMessage(payload, messageProperties);
        message.setBooleanProperty(MuleProperties.MULE_REMOTE_SYNC_PROPERTY, synchronous);
        setCredentials(message);
        AdminNotification action = new AdminNotification(message, AdminNotification.ACTION_INVOKE,
            "mule://" + component);
        MuleMessage result = dispatchAction(action, synchronous, RegistryContext.getConfiguration()
            .getDefaultSynchronousEventTimeout());
        return result;
    }

    protected MuleMessage doToRemote(String endpoint,
                                    Object payload,
                                    Map messageProperties,
                                    boolean synchronous,
                                    int timeout) throws MuleException
    {
        MuleMessage message = new DefaultMuleMessage(payload, messageProperties);
        message.setProperty(MuleProperties.MULE_REMOTE_SYNC_PROPERTY, String.valueOf(synchronous));
        setCredentials(message);
        AdminNotification action = new AdminNotification(message, (synchronous
                        ? AdminNotification.ACTION_SEND : AdminNotification.ACTION_DISPATCH), endpoint);

        MuleMessage result = dispatchAction(action, synchronous, timeout);
        return result;
    }

    protected MuleMessage dispatchAction(AdminNotification action, boolean synchronous, int timeout)
        throws MuleException
    {
        ImmutableEndpoint serverEndpoint = null;
        if (synchronous)
        {
            serverEndpoint = syncServerEndpoint;
        }
        else
        {
            serverEndpoint = asyncServerEndpoint;
        }
        
        updateContext(new DefaultMuleMessage(action), serverEndpoint, synchronous);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        wireFormat.write(out, action, serverEndpoint.getEncoding());
        byte[] payload = out.toByteArray();

        MuleMessage message = action.getMessage();

        if (message == null)
        {
            message = new DefaultMuleMessage(payload);
        }
        else
        {
            message = new DefaultMuleMessage(payload, message);
        }

        message.addProperties(action.getProperties());
        DefaultMuleSession session = new DefaultMuleSession(message,
            ((AbstractConnector)serverEndpoint.getConnector()).getSessionHandler());

        MuleEvent event = new DefaultMuleEvent(message, serverEndpoint, session, true);
        event.setTimeout(timeout);
        if (logger.isDebugEnabled())
        {
            logger.debug("MuleClient sending remote call to: " + action.getResourceIdentifier() + ". At "
                         + serverEndpoint.toString() + " . MuleEvent is: " + event);
        }

        MuleMessage result;

        try
        {
            if (synchronous)
            {
                result = serverEndpoint.send(event);
            }
            else
            {
                serverEndpoint.dispatch(event);
                return null;
            }

            if (result != null)
            {
                if (result.getPayload() != null)
                {
                    Object response;
                    if (result.getPayload() instanceof InputStream)
                    {
                        response = wireFormat.read((InputStream)result.getPayload());
                    }
                    else
                    {
                        ByteArrayInputStream in = new ByteArrayInputStream(result.getPayloadAsBytes());
                        response = wireFormat.read(in);
                    }

                    if (response instanceof AdminNotification)
                    {
                        response = ((AdminNotification)response).getMessage();
                    }
                    return (MuleMessage)response;
                }
            }
        }
        catch (Exception e)
        {
            throw new DispatchException(event.getMessage(), event.getEndpoint(), e);
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("Result of MuleClient remote call is: "
                         + (result == null ? "null" : result.getPayload()));
        }

        return result;
    }

    public void dispose()
    {
        // nothing to do here
    }

    protected void setCredentials(MuleMessage message)
    {
        if (credentials != null)
        {
            message.setProperty(MuleProperties.MULE_USER_PROPERTY, MuleCredentials.createHeader(
                credentials.getUsername(), credentials.getPassword()));
        }
    }

    public WireFormat getWireFormat()
    {
        return wireFormat;
    }

    public void setWireFormat(WireFormat wireFormat)
    {
        this.wireFormat = wireFormat;
    }

    protected void updateContext(MuleMessage message, ImmutableEndpoint endpoint, boolean synchronous)
        throws MuleException
    {

        RequestContext.setEvent(new DefaultMuleEvent(message, endpoint, new DefaultMuleSession(message,
            new MuleSessionHandler()), synchronous));
    }
}
