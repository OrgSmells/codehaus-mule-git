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

import org.mule.transport.ConnectException;
import org.mule.transport.jms.JmsConnector;
import org.mule.transport.jms.xa.ConnectionInvocationHandler;
import org.mule.util.ClassUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;

/**
 * ActiveMQ 4.x-specific JMS connector.
 */
public class ActiveMQJmsConnector extends JmsConnector
{
    public static final String ACTIVEMQ_CONNECTION_FACTORY_CLASS = "org.apache.activemq.ActiveMQConnectionFactory";
    public static final String DEFAULT_BROKER_URL = "vm://localhost?broker.persistent=false&broker.useJmx=false";
    
    private String brokerURL = DEFAULT_BROKER_URL;

    /**
     * Constructs a new ActiveMQJmsConnector.
     */
    public ActiveMQJmsConnector()
    {
        setEagerConsumer(false);
        // TODO MULE-1409 better support for ActiveMQ 4.x temp destinations
    }

    protected ConnectionFactory getDefaultConnectionFactory()
    {
        try
        {
            return (ConnectionFactory) 
                ClassUtils.instanciateClass(ACTIVEMQ_CONNECTION_FACTORY_CLASS, new Object[]{getBrokerURL()});
        }
        catch (Exception e)
        {
            logger.warn(e);
            return null;
        }
    }

    /**
     * Will additionally try to cleanup the ActiveMq connection, otherwise there's a deadlock on shutdown.
     */
    protected void doDisconnect() throws ConnectException
    {
        try
        {
            Connection connection = getConnection();
            if (connection == null)
            {
                return;
            }

            final Class clazz = connection.getClass();
            Method cleanupMethod;
            if (Proxy.isProxyClass(clazz))
            {
                ConnectionInvocationHandler handler =
                        (ConnectionInvocationHandler) Proxy.getInvocationHandler(connection);
                // this is really an XA connection, bypass the java.lang.reflect.Proxy as it
                // can't delegate to non-interfaced methods (like proprietary 'cleanup' one)
                // TODO check if CGlib will manage to enhance the AMQ connection class,
                // there are no final methods, but a number of private ones, though
                connection = (Connection) handler.getTargetObject();
                Class realConnectionClass = connection.getClass();
                cleanupMethod = realConnectionClass.getMethod("cleanup", null);
            }
            else
            {
                cleanupMethod = clazz.getMethod("cleanup", null);
            }

            try
            {
                if (cleanupMethod != null)
                {
                    cleanupMethod.invoke(connection, null);
                }
            }
            finally
            {
                connection.close();
            }
        }
        catch (Exception e)
        {
            throw new ConnectException(e, this);
        }
        finally
        {
            setConnection(null);
        }
    }

    public String getBrokerURL()
    {
        return brokerURL;
    }

    public void setBrokerURL(String brokerURL)
    {
        this.brokerURL = brokerURL;
    }
}
