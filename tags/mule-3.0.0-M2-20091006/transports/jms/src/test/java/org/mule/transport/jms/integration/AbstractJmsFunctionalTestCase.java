/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.jms.integration;

import org.mule.api.config.ConfigurationBuilder;
import org.mule.config.spring.SpringXmlConfigurationBuilder;
import org.mule.tck.FunctionalTestCase;
import org.mule.tck.MuleParameterized;
import org.mule.transport.jms.JmsConstants;
import org.mule.transport.jms.JmsMessageUtils;
import org.mule.util.ClassUtils;
import org.mule.util.CollectionUtils;
import org.mule.util.IOUtils;
import org.mule.util.StringUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameters;

/**
 * This is the base class for all integration tests that are part of the JMS integration test suite.  This is
 * a suite that can be run on multiple JMS providers since all configuration for the provider is abstracted into
 * a single class which implements {@link JmsVendorConfiguration}.  The implementation
 * of this class is loaded by looking for the classname in a properties file called 'jms-vendor-configs.txt'in the root
 * classpath.
 * <p/>
 * This test case provides a number of support methods for testing Jms providers with Mule.  This implementation is based
 * around the concept of scenarios.  Scenarios define an action or set of actions and are represented as implementations
 * of {@link AbstractJmsFunctionalTestCase.Scenario}.  Scenarios can be combined to create
 * a test.  The default scenarios are usually sufficient to create a test.  These are:
 * {@link AbstractJmsFunctionalTestCase.ScenarioReceive}
 * {@link AbstractJmsFunctionalTestCase.ScenarioNotReceive}
 * {@link AbstractJmsFunctionalTestCase.ScenarioCommit}
 * {@link AbstractJmsFunctionalTestCase.ScenarioRollback}
 * {@link AbstractJmsFunctionalTestCase.NonTransactedScenario}
 * <p/>
 * This object will also add properties to the registry that can be accessed within XML config files using placeholders.
 * The following properties are made available -
 * <ul>
 * <li>${inbound.destination} - the URI of the inbound destination (retrieved from an {@link JmsVendorConfiguration} implementation)</li>
 * <li>${outbound.destination} - the URI of the outbound destination (retrieved from an {@link JmsVendorConfiguration} implementation)</li>
 * <li>${middle.destination} - the URI of the middle destination (retrieved from an {@link JmsVendorConfiguration} implementation)</li>
 * <li>${middle2.destination} - the URI of a second middle destination 'middle2'.</li>
 * <li>${middle3.destination} - the URI of a third middle destination 'middle3'.</li>
 * <li>${broadcast.destination} - the URI of the broadcast topic (retrieved from an {@link JmsVendorConfiguration} implementation)</li>
 * <li>${protocol} - the protocol of the current messaging connector (retrieved from an {@link JmsVendorConfiguration} implementation)</li>
 * </ul>
 * <p/>
 * For each integration test there are 2 configuration files. One is provided by the JMS integration suite and defines the
 * event flow for the test. The other is a vendor-specific config file that defines the connectors and possibly endpoints and
 * transformers for the Jms connector being tested. These configurations are known as 'connector' files, they share the same
 * file name as the generic configuration file prepended with 'connector-'.  The location of these files must be
 * <p/>
 * <code>
 * integration/&lt;provider_name>/connector-&lt;event_flow_config_name&gt;</code>
 * <p/>
 * The 'provider_name' is obtained from the {@link JmsVendorConfiguration} implementation.
 * <p/>
 * In order to know what objects to define in the 'connector-' files you must copy the connector files from the ActiveMQ (default)
 * test suite and configure the objects to match the configuration in the ActiveMQ tests.  Note that the object names must
 * be consistently the same for things to work.
 */
@RunWith(MuleParameterized.class)
public abstract class AbstractJmsFunctionalTestCase extends FunctionalTestCase
{
    public static final String CONNECTOR = "jmsConnector";

    public static final String DEFAULT_INPUT_MESSAGE = "INPUT MESSAGE";
    public static final String DEFAULT_OUTPUT_MESSAGE = "OUTPUT MESSAGE";

    public static final String INBOUND_ENDPOINT_KEY = "inbound.destination";
    public static final String OUTBOUND_ENDPOINT_KEY = "outbound.destination";

    public static final String MIDDLE_ENDPOINT_KEY = "middle.destination";
    public static final String MIDDLE2_ENDPOINT_KEY = "middle2.destination";
    public static final String MIDDLE3_ENDPOINT_KEY = "middle3.destination";
    public static final String BROADCAST_TOPIC_ENDPOINT_KEY = "broadcast.topic.destination";

    protected static final Log logger = LogFactory.getLog("MULE_TESTS");

    protected JmsVendorConfiguration jmsConfig = null;

    /**
     * This test case has been refactored to support multiple JMS providers.
     */
    private boolean multipleProviders = true;

    /**
     * Are we using transactions for this test case?
     */
    private boolean transacted = false;

    /**
     * Are we using persistent queues for this test case?
     */
    private boolean persistent = false;

    /**
     * Are we using auto-acknowledge or some other mode?
     */
    private int acknowledgeMode = Session.AUTO_ACKNOWLEDGE;
    
    /**
     * Which JMS spec. are we using?
     */
    private String jmsSpecification = JmsConstants.JMS_SPECIFICATION_102B;
        
    /**
     * ClientID used when subscribing to JMS topics
     */
    private String clientId;

    /**
     * Are we working with JMS topics rather than queues?
     */
    private boolean useTopics = false;

    /**
     * Implement this interface to provide a callback which will be executed immediately 
     * after sending/receiving a message.
     */
    interface MessagePostProcessor
    {
        void postProcess(Session session, Message message) throws JMSException;
    }
    
    /**
     * Finds the {@link JmsVendorConfiguration} instances to test with by looking
     * in a file called <tt>jms-vendor-configs.txt</tt> which contains one or more fuly qualified classnames of
     * {@link JmsVendorConfiguration} instances to load.
     *
     * @return a collection of {@link JmsVendorConfiguration} instance to test against.
     *
     * @throws Exception if the 'jms-vendor-configs.txt' cannot be loaded or the classes defined within that file
     * are not on the classpath
     *
     * TODO this method can return more than one provider, but our test class can only handle one at a time
     * IMPORTANT: Only set one class in 'jms-vendor-configs.txt'
     */
    @Parameters
    public static Collection jmsProviderConfigs()
    {
        URL url = ClassUtils.getResource("jms-vendor-configs.txt", AbstractJmsFunctionalTestCase.class);

        if (url == null)
        {
            fail("Please specify the org.mule.transport.jms.integration.JmsVendorConfiguration " +
                  "implementation to use in jms-vendor-configs.txt on classpaath.");
            return CollectionUtils.EMPTY_COLLECTION;
        }

        try
        {
            List configs = new ArrayList();
            List classes = IOUtils.readLines(url.openStream());
            int i = 0;
            for (Iterator iterator = classes.iterator(); iterator.hasNext(); i++)
            {
                String cls = ((String) iterator.next()).trim();
                // ignore lines commented out with "#"
                if (!cls.startsWith("#"))
                {
                    configs.add(new Object[] { ClassUtils.instanciateClass(cls, ClassUtils.NO_ARGS) });
                }
            }
            return configs;
        }
        catch (Exception e)
        {
            if (logger.isDebugEnabled())
            {
                e.printStackTrace();
            }
            fail("Please specify the org.mule.transport.jms.integration.JmsVendorConfiguration " +
                 "implementation to use in jms-vendor-configs.txt on classpath: " + e.getMessage());
            return CollectionUtils.EMPTY_COLLECTION;
        }
    }

    /**
     * Since we are using JUnit 4, but the Mule Test Framework assumes JUnit 3, we
     * need to explicitly call the setUp and tearDown methods
     *
     * @throws Exception if, well, anything goes wrong
     */
    @Before
    public void before() throws Exception
    {
        super.setUp();
    }

    /**
     * Since we are using JUnit 4, but the Mule Test Framework assumes JUnit 3, we
     * need to explicitly call the setUp and tearDown methods
     *
     * @throws Exception if, well, anything goes wrong
     */
    @After
    public void after() throws Exception
    {
        super.tearDown();
        purge(getJmsConfig().getInboundDestinationName());
        purge(getJmsConfig().getOutboundDestinationName());
        purge(getJmsConfig().getMiddleDestinationName());
        purge(getJmsConfig().getDeadLetterDestinationName());
    }

    public AbstractJmsFunctionalTestCase(JmsVendorConfiguration config)
    {
        super();
        try
        {
            config.initialise(getClass());
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        setJmsConfig(config);
        System.out.print("\n===== Parameterized test using: " + config.getName() + " =====");
    }

    /**
     * Adds the following properties to the registry so that the Xml configuration files can reference them.
     * <p/>
     * <ul>
     * <li>${inbound.destination} - the URI of the inbound destination (retrieved from an {@link JmsVendorConfiguration} implementation)</li>
     * <li>${outbound.destination} - the URI of the outbound destination (retrieved from an {@link JmsVendorConfiguration} implementation)</li>
     * <li>${middle.destination} - the URI of the middle destination (retrieved from an {@link JmsVendorConfiguration} implementation)</li>
     * <li>${middle2.destination} - the URI of a second middle destination 'middle2'.</li>
     * <li>${middle3.destination} - the URI of a third middle destination 'middle3'.</li>
     * <li>${broadcast.destination} - the URI of the broadcast topic (retrieved from an {@link JmsVendorConfiguration} implementation)</li>
     * <li>${protocol} - the protocol of the current messaging connector (retrieved from an {@link JmsVendorConfiguration} implementation)</li>
     * </ul>
     *
     * @return
     */
    @Override
    protected Properties getStartUpProperties()
    {
        Properties props = new Properties();
        // Inject endpoint names into the config
        props.put(INBOUND_ENDPOINT_KEY, getJmsConfig().getInboundEndpoint());
        props.put(OUTBOUND_ENDPOINT_KEY, getJmsConfig().getOutboundEndpoint());
        props.put(MIDDLE_ENDPOINT_KEY, getJmsConfig().getMiddleEndpoint());
        props.put(MIDDLE2_ENDPOINT_KEY, getJmsConfig().getMiddleEndpoint() + "2");
        props.put(MIDDLE3_ENDPOINT_KEY, getJmsConfig().getMiddleEndpoint() + "3");

        props.put(BROADCAST_TOPIC_ENDPOINT_KEY, getJmsConfig().getTopicBroadcastEndpoint());
        props.put("protocol", getJmsConfig().getProtocol());

        Map p = getJmsConfig().getProperties();
        if (p != null)
        {
            props.putAll(p);
        }
        return props;
    }

    /**
     * This creates a {@link SpringXmlConfigurationBuilder} as expected but also figures out
     * which 'connector' configuration file to load with the event flow configuration (obtained from the overriding 
     * class which implements {@link #getConfigResources()}).
     *
     * @return The config builder used to create the Mule instance for this test
     * @throws Exception
     */
    @Override
    protected ConfigurationBuilder getBuilder() throws Exception
    {
        if (multipleProviders)
        {
            final String configResource = getConfigResources();
            // multiple configs arent' supported by this mechanism, validate and fail if needed
            if (StringUtils.splitAndTrim(configResource, ",; ").length > 1)
            {
                throw new IllegalArgumentException("Parameterized tests don't support multiple " +
                                                   "config files as input: " + configResource);
            }
            String resources = configResource.substring(configResource.lastIndexOf("/") + 1);
            resources = String.format("integration/%s/connector-%s,%s", getJmsConfig().getName(),
                    resources, getConfigResources());
            SpringXmlConfigurationBuilder builder = new SpringXmlConfigurationBuilder(resources);
            return builder;
        }
        else
        {
            return super.getBuilder();
        }            
    }

    /**
     * Returns the {@link JmsVendorConfiguration} implemetation to be used with this test
     *
     * @return settings for this test
     */
    public final JmsVendorConfiguration getJmsConfig()
    {
        if (jmsConfig == null)
        {
            jmsConfig = createJmsConfig();
        }
        return jmsConfig;
    }

    /**
     * Sets the {@link JmsVendorConfiguration} implemetation to be used with this test
     *
     * @param jmsConfig the settings for this test
     */
    public final void setJmsConfig(JmsVendorConfiguration jmsConfig)
    {
        this.jmsConfig = jmsConfig;
    }

    /**
     * Overriding classes must override this or inject this object. It is responsible for creating the
     * {@link JmsVendorConfiguration} instance to be used by this test.
     *
     * @return the settings for this test
     */
    protected JmsVendorConfiguration createJmsConfig()
    {
        // Overriding classes must override this or inject this object
        return null;
    }

    /**
     * Create a connection factory for the Jms profider being tested.  This calls
     * through to {@link org.mule.transport.jms.integration.JmsVendorConfiguration#getConnection(boolean, boolean)}
     *
     * @param topic whether to use a topic or queue connection factory, for 1.1
     *              implementations this proerty can be ignored
     * @param xa    whether to create an XA connection factory
     * @return a new JMS connection
     */
    protected final Connection getConnection(boolean topic, boolean xa) throws Exception
    {
        checkConfig();
        return getJmsConfig().getConnection(topic, xa);
    }

    /**
     * Returns the {@link #getInboundQueueName()} in the form of an endpoint URI i.e.
     * jms://in.
     * <p/>
     * This calls through to {@link JmsVendorConfiguration#getInboundEndpoint()}
     *
     * @return the Inbound JMS endpoint
     */
    protected final String getInboundEndpoint()
    {
        checkConfig();
        return getJmsConfig().getInboundEndpoint();
    }

    /**
     * Returns the {@link #getOutboundQueueName()} in the form of an endpoint URI i.e.
     * jms://out.
     * <p/>
     * This calls through to {@link org.mule.transport.jms.integration.JmsVendorConfiguration#getOutboundEndpoint()}
     *
     * @return the Outbound JMS endpoint
     */
    protected final String getOutboundEndpoint()
    {
        checkConfig();
        return getJmsConfig().getOutboundEndpoint();
    }

    /**
     * Timeout in milliseconds used when checking that a message is NOT present. This is usually 1000-2000ms.
     * It is customizable so that slow connections i.e. over a wAN can be accounted for.
     * <p/>
     * This calls through to {@link JmsVendorConfiguration#getSmallTimeout()}
     *
     * @return timeout in milliseconds used when checking that a message is NOT present
     */
    protected final long getSmallTimeout()
    {
        checkConfig();
        return getJmsConfig().getSmallTimeout();

    }

    /**
     * The timeout in milliseconds used when waiting for a message to arrive. This is usually 3000-5000ms.
     * However, it is customizable so that slow connections i.e. over a wAN can be accounted for.
     * <p/>
     * This calls through to {@link JmsVendorConfiguration#getTimeout()}
     *
     * @return The timeout used when waiting for a message to arrive
     */
    protected final long getTimeout()
    {
        checkConfig();
        return getJmsConfig().getTimeout();
    }

    /**
     * Ensures that the {@link org.mule.transport.jms.integration.JmsVendorConfiguration} instance is not null
     * if it is an {@link IllegalStateException} will be thrown
     */
    protected void checkConfig()
    {
        if (getJmsConfig() == null)
        {
            throw new IllegalStateException("There must be a Jms Vendor config set on this test");
        }
    }

    public Message send() throws Exception
    {
        return send(DEFAULT_INPUT_MESSAGE);
    }

    public Message send(Object payload) throws Exception
    {
        if (isTransacted())
        {
            throw new IllegalStateException("This test case is using transactions, you must call either sendAndCommit() or sendAndRollback()");
        }
        else
        {
            return send(payload, null);
        }
    }

    public Message sendAndCommit(Object payload) throws Exception
    {
        return send(payload, new MessagePostProcessor() 
        {
            public void postProcess(Session session, Message message) throws JMSException
            {
                session.commit();
            }
        });
    }
    
    public Message sendAndRollback(Object payload) throws Exception
    {
        return send(payload, new MessagePostProcessor() 
        {
            public void postProcess(Session session, Message message) throws JMSException
            {
                session.rollback();
            }
        });
    }

    protected Message send(Object payload, MessagePostProcessor postProcessor) throws Exception
    {
        if (isUseTopics())
        {
            return send(getJmsConfig().getBroadcastDestinationName(), payload, postProcessor);
        }
        else
        {
            return send(getJmsConfig().getInboundDestinationName(), payload, postProcessor);
        }
    }
    
    protected Message send(String dest, Object payload, MessagePostProcessor postProcessor) throws Exception
    {
        Connection connection = null;
        try
        {
            connection = getConnection(isUseTopics(), false);
            if (getClientId() != null)
            {
                connection.setClientID(getClientId());
            }
            connection.start();
            Session session = null;
            try
            {
                session = connection.createSession(isTransacted(), getAcknowledgeMode());
                Destination destination;
                if (isUseTopics())
                {
                    destination = session.createTopic(dest);
                }
                else
                {
                    destination = session.createQueue(dest);
                }
                MessageProducer producer = null;
                try
                {
                    producer = session.createProducer(destination);
                    if (isPersistent())
                    {
                        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
                    }
                    Message message = createJmsMessage(payload, session);
                    producer.send(message);
                    if (postProcessor != null)
                    {
                        postProcessor.postProcess(session, message);
                    }
                    return message;
                }
                finally
                {
                    if (producer != null)
                    {
                        producer.close();
                    }
                }
            }
            finally
            {
                if (session != null)
                {
                    session.close();
                }
            }
        }
        finally
        {
            if (connection != null)
            {
                connection.close();
            }
        }
    }

    protected Message createJmsMessage(Object payload, Session session) throws JMSException
    {
        return JmsMessageUtils.toMessage(payload, session);
    }
    
    public Message receive() throws Exception
    {
        return receive((MessagePostProcessor) null);
    }

    public Message receive(MessagePostProcessor postProcessor) throws Exception
    {
        if (isTransacted())
        {
            throw new IllegalStateException("This test case is using transactions, you must call either receiveAndCommit() or receiveAndRollback()");
        }
        else
        {
            return receive(getTimeout(), postProcessor);
        }
    }    
    
    /**
     * Short timeout, use when no message is expected.
     */
    public Message receiveNoWait() throws Exception
    {
        return receive(getSmallTimeout(), null);
    }
    
    public Message receiveAndCommit() throws Exception
    {
        return receive(getTimeout(), new MessagePostProcessor() 
            {
                public void postProcess(Session session, Message message) throws JMSException
                {
                    session.commit();
                }
            });
    }
    
    public Message receiveAndRollback() throws Exception
    {
        return receive(getTimeout(), new MessagePostProcessor() 
        {
            public void postProcess(Session session, Message message) throws JMSException
            {
                session.rollback();
            }
        });
    }

    protected Message receive(long timeout, MessagePostProcessor postProcessor) throws Exception
    {
        if (isUseTopics())
        {
            return receive(getJmsConfig().getBroadcastDestinationName(), timeout, postProcessor);
        }
        else
        {
            return receive(getJmsConfig().getOutboundDestinationName(), timeout, postProcessor);
        }
    }
    
    protected Message receive(String queue, long timeout, MessagePostProcessor postProcessor) throws Exception
    {
        Connection connection = null;
        try
        {
            connection = getConnection(isUseTopics(), false);
            if (getClientId() != null)
            {
                connection.setClientID(getClientId());
            }
            connection.start();
            Session session = null;
            try
            {
                session = connection.createSession(isTransacted(), getAcknowledgeMode());
                Destination destination;
                if (isUseTopics())
                {
                    destination = session.createTopic(queue);
                }
                else
                {
                    destination = session.createQueue(queue);
                }
                MessageConsumer consumer = null;
                try
                {
                    if (isUseTopics())
                    {
                        consumer = session.createDurableSubscriber((Topic) destination, getClientId());
                    }
                    else
                    {
                        consumer = session.createConsumer(destination);
                    }
                    Message message = consumer.receive(timeout);
                    if (postProcessor != null)
                    {
                        postProcessor.postProcess(session, message);
                    }
                    return message;
                }
                finally
                {
                    if (consumer != null)
                    {
                        consumer.close();
                    }
                }
            }
            finally
            {
                if (session != null)
                {
                    session.close();
                }
            }
        }
        finally
        {
            if (connection != null)
            {
                connection.close();
            }
        }
    }

    /**
     * Purge destinations for clean test setup. Especially applicable to WMQ tests, as messages from
     * other tests may still exist from other tests' runs.
     * <p/>
     * Well-behaving tests should drain both inbound and outbound destinations, as well as any intermediary ones.
     * Typically this method is called from {@link #suitePreSetUp()} and {@link #suitePostTearDown()}, with proper super calls.
     * @param destination destination name without any protocol specifics
     * @see #suitePreSetUp()
     * @see #suitePostTearDown()
     */
    protected void purge(final String destination) throws Exception
    {
        Connection c = null;
        Session s = null;
        try
        {
            c = getConnection(false, false);
            assertNotNull(c);
            c.start();

            s = c.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination d = s.createQueue(destination);
            MessageConsumer consumer = s.createConsumer(d);

            while (consumer.receiveNoWait() != null)
            {
                logger.warn("Destination " + destination + " isn't empty, draining it");
            }
        }
        finally
        {
            if (c != null)
            {
                c.stop();
                if (s != null)
                {
                    s.close();
                }
                c.close();
            }
        }

    }

    /** Verify that no message is pending on the default output queue. */
    protected void receiveAndAssertNone() throws Exception
    {
        assertNull(receiveNoWait());
    }

    /** Receive a message from the default output queue and compare it to the default output message. */
    protected void receiveAndAssert() throws Exception
    {
        assertPayloadEquals(DEFAULT_OUTPUT_MESSAGE, receive());
    }
    
    protected void assertPayloadEquals(Object expected, Message message) 
    {
        assertNotNull("Message is null", message);
        Object object = null;
        try
        {
            object = JmsMessageUtils.toObject(message, getJmsSpecification(), muleContext.getConfiguration().getDefaultEncoding());
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }
        if (expected instanceof byte[])
        {
            assertEquals((byte[]) expected, (byte[]) object);
        }
        else
        {
            assertEquals(expected, object);             
        }
    }

    protected void assertEquals(byte[] expected, byte[] actual)
    {
        assertEquals("Wrong number of bytes", expected.length, actual.length);
        for (int i=0; i < expected.length; ++i)
        {
            assertEquals("Byte #" + i + " does not match", expected[i], actual[i]);
        }
    }
    
    public boolean isMultipleProviders()
    {
        return multipleProviders;
    }

    public void setMultipleProviders(boolean multipleProviders)
    {
        this.multipleProviders = multipleProviders;
    }

    public boolean isTransacted()
    {
        return transacted;
    }

    public void setTransacted(boolean transacted)
    {
        this.transacted = transacted;
    }

    public boolean isPersistent()
    {
        return persistent;
    }

    public void setPersistent(boolean persistent)
    {
        this.persistent = persistent;
    }

    public int getAcknowledgeMode()
    {
        return acknowledgeMode;
    }

    public void setAcknowledgeMode(int acknowledgeMode)
    {
        this.acknowledgeMode = acknowledgeMode;
    }

    public String getJmsSpecification()
    {
        return jmsSpecification;
    }

    public void setJmsSpecification(String jmsSpecification)
    {
        this.jmsSpecification = jmsSpecification;
    }

    public String getClientId()
    {
        return clientId;
    }

    public void setClientId(String clientId)
    {
        this.clientId = clientId;
    }

    public boolean isUseTopics()
    {
        return useTopics;
    }

    public void setUseTopics(boolean useTopics)
    {
        this.useTopics = useTopics;
    }

}
