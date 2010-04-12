/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.quartz;

import org.mule.api.MuleContext;
import org.mule.api.MuleException;
import org.mule.api.config.MuleProperties;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.transport.ConnectorException;
import org.mule.config.i18n.CoreMessages;
import org.mule.transport.AbstractConnector;

import java.util.Properties;

import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Creates a connection to a Quartz scheduler. This allows events to be scheduled at
 * specific times, with repeated occurrences.
 */
public class QuartzConnector extends AbstractConnector
{

    public static final String QUARTZ = "quartz";

    public static final String PROPERTY_CRON_EXPRESSION = "cronExpression";
    public static final String PROPERTY_REPEAT_INTERVAL = "repeatInterval";
    public static final String PROPERTY_REPEAT_COUNT = "repeatCount";
    public static final String PROPERTY_START_DELAY = "startDelay";
    public static final String PROPERTY_PAYLOAD = "payload";

    public static final String PROPERTY_JOB_CONFIG = "jobConfig";
    public static final String PROPERTY_JOB_DATA = "jobData";

    public static final String PROPERTY_JOB_REF = "jobRef";
    public static final String PROPERTY_JOB_OBJECT = "jobObject";

    public static final String DEFAULT_GROUP_NAME = "mule";

    /**
     * Properties to be used for creating the scheduler.  If no properties are given, the
     * scheduler will be created by StdSchedulerFactory.getDefaultScheduler()
     */
    private Properties factoryProperties = null;

    /**
     * The scheduler instance.  This can be configured by the user and injected as a bean
     * or if not, it will be created by Mule upon initialization.
     */
    private Scheduler quartzScheduler = null;

    public QuartzConnector(MuleContext context)
    {
        super(context);
    }
    
    protected void doInitialise() throws InitialisationException
    {
        if(factoryProperties==null)
        {
            factoryProperties = new Properties();
        }

        //Set the thread count, we can't seem to plug in our work manager unfortunately
        factoryProperties.setProperty("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
        factoryProperties.setProperty("org.quartz.threadPool.threadCount", String.valueOf(getReceiverThreadingProfile().getMaxThreadsActive()));

        try
        {
            if (quartzScheduler == null)
            {
                SchedulerFactory factory = new StdSchedulerFactory(factoryProperties);
                quartzScheduler = factory.getScheduler();
            }
            quartzScheduler.getContext().put(MuleProperties.MULE_CONTEXT_PROPERTY, muleContext);            
        
        }
        catch (Exception e)
        {
            throw new InitialisationException(CoreMessages.initialisationFailure("Quartz conntector"), e, this);
        }
    }

    protected void doDispose()
    {
        // template method
    }

    protected void doConnect() throws Exception
    {
        // template method
    }

    protected void doDisconnect() throws Exception
    {
        // template method
    }

    protected void doStart() throws MuleException
    {
        try
        {
            quartzScheduler.start();
        }
        catch (Exception e)
        {
            throw new ConnectorException(CoreMessages.failedToStart("Quartz provider"), this, e);
        }
    }

    protected void doStop() throws MuleException
    {
        try
        {
            if (quartzScheduler != null)
            {
                quartzScheduler.shutdown();
            }
        }
        catch (Exception e)
        {
            throw new ConnectorException(CoreMessages.failedToStop("Quartz provider"), this, e);
        }
    }

    public String getProtocol()
    {
        return QUARTZ;
    }

    public Scheduler getQuartzScheduler()
    {
        return quartzScheduler;
    }

    public void setQuartzScheduler(Scheduler scheduler)
    {
        this.quartzScheduler = scheduler;
    }

    public Properties getFactoryProperties()
    {
        return factoryProperties;
    }

    public void setFactoryProperties(Properties factoryProperties)
    {
        this.factoryProperties = factoryProperties;
    }
}
