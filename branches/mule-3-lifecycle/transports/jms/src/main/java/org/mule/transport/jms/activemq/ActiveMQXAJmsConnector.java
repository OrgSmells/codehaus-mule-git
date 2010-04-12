/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.transport.jms.activemq;

import org.mule.api.MuleContext;
import org.mule.util.ClassUtils;

import java.lang.reflect.InvocationTargetException;

import javax.jms.ConnectionFactory;

public class ActiveMQXAJmsConnector extends ActiveMQJmsConnector
{
    public static final String ACTIVEMQ_XA_CONNECTION_FACTORY_CLASS = "org.apache.activemq.ActiveMQXAConnectionFactory";

    public ActiveMQXAJmsConnector(MuleContext context)
    {
        super(context);
    }
    
    protected ConnectionFactory getDefaultConnectionFactory()
    {
        try
        {
            ConnectionFactory connectionFactory = (ConnectionFactory)
                    ClassUtils.instanciateClass(ACTIVEMQ_XA_CONNECTION_FACTORY_CLASS, getBrokerURL());
            applyVendorSpecificConnectionFactoryProperties(connectionFactory);
            return connectionFactory;
        }
        catch (InvocationTargetException itex)
        {
            Throwable target = itex.getCause();
            handleException(target instanceof Exception ? (Exception) target : new Exception(target));
        }
        catch (Exception e)
        {
            handleException(e);
        }
        return null;
    }
}
