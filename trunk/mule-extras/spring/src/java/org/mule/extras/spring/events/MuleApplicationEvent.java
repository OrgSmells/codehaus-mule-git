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
package org.mule.extras.spring.events;

import java.util.HashMap;
import java.util.Map;

import org.mule.MuleManager;
import org.mule.impl.endpoint.MuleEndpointURI;
import org.mule.umo.UMOEventContext;
import org.mule.umo.endpoint.MalformedEndpointException;
import org.mule.umo.endpoint.UMOEndpointURI;
import org.mule.util.PropertiesHelper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;

/**
 * <code>MuleApplicationEvent</code> is an Spring ApplicationEvent used to
 * wrap a MuleEvent
 *
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */

public class MuleApplicationEvent extends ApplicationEvent
{
    private UMOEventContext context;
    private UMOEndpointURI endpointUri;
    private ApplicationContext applicationContext;
    private Map properties = new HashMap();

    public MuleApplicationEvent(Object message, String endpoint) throws MalformedEndpointException
    {
        super(message);
        String temp = PropertiesHelper.getStringProperty(MuleManager.getInstance().getEndpointIdentifiers(), endpoint, endpoint);
        setEndpoint(new MuleEndpointURI(temp));
    }


    MuleApplicationEvent(Object message, UMOEventContext context, ApplicationContext appContext) throws MalformedEndpointException
    {
        super(message);
        this.context = context;
        setEndpoint(context.getEndpointURI());
        applicationContext = appContext;
    }


    protected void setEndpoint(UMOEndpointURI endpointUri) throws MalformedEndpointException
    {
        this.endpointUri = endpointUri;
    }

    public UMOEventContext getMuleEventContext()
    {
        return context;
    }

    public UMOEndpointURI getEndpoint() {
        return endpointUri;
    }

    public ApplicationContext getApplicationContext()
    {
        return applicationContext;
    }

    public Map getProperties()
    {
        return properties;
    }

    public void setProperty(Object key,  Object value)
    {
        this.properties.put(key, value);
    }

    public Object getProperty(Object key)
    {
        return properties.get(key);
    }
}
