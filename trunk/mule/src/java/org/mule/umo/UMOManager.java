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
 *
 */

package org.mule.umo;

import org.mule.InitialisationException;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.lifecycle.Lifecycle;
import org.mule.umo.model.UMOContainerContext;
import org.mule.umo.model.UMOModel;
import org.mule.umo.provider.UMOConnector;
import org.mule.umo.security.UMOSecurityManager;
import org.mule.umo.transformer.UMOTransformer;

import javax.transaction.TransactionManager;
import java.util.List;
import java.util.Map;

/**
 * <code>UMOManager</code> maintains and provides services for a UMO server
 * instance.
 *
 * @author <a href="mailto:ross.mason@cubis.co.uk">Ross Mason</a>
 * @version $Revision$
 */
public interface UMOManager extends Lifecycle
{
    /**
     * Getter for the envionment parameters declared in the nule-config.xml
     *
     * @param key the propery name
     * @return the property value
     */
    public abstract Object getProperty(Object key);

    /**
     * @param logicalName the name of the endpoint to retrieve
     * @return the endpoint instnace if it exists
     */
    public abstract UMOConnector lookupConnector(String logicalName);

    /**
     * @param logicalName the logical mapping name for an endpointUri i.e.
     *                    rather than specifing an endpointUri to be someone@my.com you can supply
     *                    a more descriptive name such as <i>The System Administrator</i>
     * @param defaultName
     * @return the actual endpointUri value or null if it is not found
     */
    public abstract String lookupEndpointIdentifier(String logicalName, String defaultName);

    /**
     * Getter for a global endpoint.  Any endpoints returned from this method
     * will be read-only as they may be shared by other components.  To change
     * any details on the endpoint you must clone it first calling it's clone() method
     *
     * @param logicalName the name of the endpoint
     * @return the <code>UMOEndpoint</code> or null if it doesn't exist
     */
    public abstract UMOEndpoint lookupEndpoint(String logicalName);

    /**
     * Getter method for a Transformer.
     *
     * @param name the name of the transformer
     * @return the Transformer instance if found, otherwise null
     */
    public abstract UMOTransformer lookupTransformer(String name);

    /**
     * Registers a <code>UMOConnector</code> with the <code>MuleManager</code>.
     *
     * @param connector the <code>UMOConnector</code> to register
     */
    public abstract void registerConnector(UMOConnector connector) throws UMOException;

    /**
     * UnRegisters a <code>UMOConnector</code> with the <code>MuleManager</code>.
     *
     * @param connectorName the name of the <code>UMOConnector</code> to unregister
     */
    public abstract void unregisterConnector(String connectorName) throws UMOException;

    /**
     * Registers an endpointUri with a logical name
     *
     * @param logicalName the name of the endpointUri
     * @param endpoint    the physical endpointUri value
     */
    public abstract void registerEndpointIdentifier(String logicalName, String endpoint)  throws InitialisationException;

    /**
     * unregisters an endpointUri with a logical name
     *
     * @param logicalName the name of the endpointUri
     */
    public abstract void unregisterEndpointIdentifier(String logicalName);

    /**
     * Registers a shared/global endpoint with the <code>MuleManager</code>.
     *
     * @param endpoint the <code>UMOEndpoint</code> to register.
     */
    public abstract void registerEndpoint(UMOEndpoint endpoint) throws InitialisationException;

    /**
     * unregisters a shared/global endpoint with the <code>MuleManager</code>.
     *
     * @param endpointName the <code>UMOEndpoint</code> name to unregister.
     */
    public abstract void unregisterEndpoint(String endpointName);

    /**
     * Registers a transformer with the <code>MuleManager</code>.
     *
     * @param transformer the <code>UMOTransformer</code> to register.
     */
    public abstract void registerTransformer(UMOTransformer transformer) throws InitialisationException;

    /**
     * UnRegisters a transformer with the <code>MuleManager</code>.
     *
     * @param transformerName the <code>UMOTransformer</code> name to register.
     */
    public abstract void unregisterTransformer(String transformerName);

    /**
     * Sets an Mule environment parameter in the <code>MuleManager</code>.
     *
     * @param key   the parameter name
     * @param value the parameter value
     */
    public abstract void setProperty(Object key, Object value);
    /**
     * Sets the Jta Transaction Manager to use with this Mule server instance
     *
     * @param manager the manager to use
     * @throws Exception
     */
    public void setTransactionManager(TransactionManager manager) throws Exception;

    /**
     * Returns the Jta transaction manager used by this Mule server instance.
     * or null if a transaction manager has not been set
     * @return the Jta transaction manager used by this Mule server instance.
     * or null if a transaction manager has not been set
     */
    public TransactionManager getTransactionManager();

    /**
     * The model used for managing components for this server
     * @return The model used for managing components for this server
     */
    public UMOModel getModel();

    /**
     * The model used for managing components for this server
     * @param model The model used for managing components for this server
     */
    public void setModel(UMOModel model);

    /**
     * Gets all properties associated with the UMOManager
     * @return a map of properties on the Manager
     */
    public Map getProperties();

    /**
     * Gets an unmodifiable collection of Connectors registered with the
     * UMOManager
     * @return All connectors registered on the Manager
     * @see UMOConnector
     */
    public Map getConnectors();

    /**
     * Gets an unmodifiable collection of endpoints registered with the
     * UMOManager
     * @return All endpoints registered on the Manager
     */
    public Map getEndpointIdentifiers();

    /**
     * Gets an unmodifiable collection of endpoints registered with the
     * UMOManager
     * @return All endpoints registered on the Manager
     * @see org.mule.umo.endpoint.UMOEndpoint
     */
    public Map getEndpoints();

    /**
     * Gets an unmodifiable collection of transformers registered with the
     * UMOManager
     * @return All transformers registered on the Manager
     * @see UMOTransformer
     */
    public Map getTransformers();

    /**
     * registers a interceptor stack list that can be referenced by other components
     * @param name the referenceable name for this stack
     * @param stack a List of interceptors
     * @see UMOInterceptor
     */
    public void registerInterceptorStack(String name, List stack);

    /**
     * Retrieves a configured interceptor stack.
     * @param name the name of the stack
     * @return the interceptor stack requested or null if there wasn't one configured
     * for the given name
     */
    public List lookupInterceptorStack(String name);

    /**
     * Determines if the server has been started
     * @return true if the server has been started
     */
    public boolean isStarted();

    /**
     * Determines if the server has been initialised
     * @return true if the server has been initialised
     */
    public boolean isInitialised();

    /**
     * Returns the long date when the server was started
     * @return the long date when the server was started
     */
    public long getStartDate();

    /**
     * Will register an agent object on this model.  Agents can be server
     * plugins such as Jms support
     * @param agent
     */
    public void registerAgent(UMOAgent agent) throws UMOException;

    /**
     * Removes and destroys a registered agent
     * @param name the agent name
     * @return the destroyed agent or null if the agent doesn't exist
     */
    public UMOAgent removeAgent(String name) throws UMOException;

    /**
     * Registers an intenal server event listener.  The listener will
     * be notified when a particular event happens within the server.  Typically
     * this is not an event in the same sense as an UMOEvent (although there is nothing
     * stopping the implementation of this class triggering listeners when a UMOEvent
     * is received).
     *
     * The types of events fired is entirely defined by the implementation of this class
     * @param l the listener to register
     */
    public void registerListener(UMOServerEventListener l);

    /**
     * Unregisters a previously registered listener.  If the listener has not already been
     * registered, this method should return without exception
     * @param l the listener to unregister
     */
    public void unregisterListener(UMOServerEventListener l);

    /**
     * Fires a server event to all regiistered listeners
     * @param event the event to fire
     */
    public void fireEvent(UMOServerEvent event);

    /**
     * associates a Dependency Injector container with Mule.  This can be used
     * to integrate container managed resources with Mule resources
     * @param context a Container context to use.
     */
    public void setContainerContext(UMOContainerContext context);

    /**
     * associates a Dependency Injector container with Mule.  This can be used
     * to integrate container managed resources with Mule resources
     * @return the container associated with the Manager
     */
    public UMOContainerContext getContainerContext();

    /**
     * Sets the unique Id for this Manager instance.  this id can be used to assign
     * an identy to the manager so it can be identified in a network of Mule nodes
     * @param id the unique Id for this manager in the network
     */
    public void setId(String id);

    /**
     * Gets the unique Id for this Manager instance.  this id can be used to assign
     * an identy to the manager so it can be identified in a network of Mule nodes
     * @return the unique Id for this manager in the network
     */
    public String getId();

    /**
     * Sets the security manager used by this Mule instance to authenticate and authorise
     * incoming and outgoing event traffic and service invocations
     * @param securityManager the security manager used by this Mule instance to authenticate and authorise
     * incoming and outgoing event traffic and service invocations
     */
    public void setSecurityManager(UMOSecurityManager securityManager) throws InitialisationException;

    /**
     * Gets the security manager used by this Mule instance to authenticate and authorise
     * incoming and outgoing event traffic and service invocations
     * @return he security manager used by this Mule instance to authenticate and authorise
     * incoming and outgoing event traffic and service invocations
     */
    public UMOSecurityManager getSecurityManager();
}