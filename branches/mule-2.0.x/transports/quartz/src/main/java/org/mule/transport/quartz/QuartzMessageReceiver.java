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

import org.mule.api.MuleException;
import org.mule.api.endpoint.EndpointException;
import org.mule.api.endpoint.Endpoint;
import org.mule.api.lifecycle.CreateException;
import org.mule.api.service.Service;
import org.mule.api.transport.Connector;
import org.mule.config.i18n.CoreMessages;
import org.mule.transport.AbstractMessageReceiver;
import org.mule.transport.quartz.i18n.QuartzMessages;
import org.mule.transport.quartz.jobs.MuleReceiverJob;

import java.util.Date;

import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.ObjectAlreadyExistsException;
import org.quartz.Scheduler;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;

/**
 * Listens for Quartz sheduled events using the Receiver Job and fires events to the
 * service associated with this receiver.
 */
public class QuartzMessageReceiver extends AbstractMessageReceiver
{

    public static final String QUARTZ_RECEIVER_PROPERTY = "mule.quartz.receiver";
    public static final String QUARTZ_CONNECTOR_PROPERTY = "mule.quartz.connector";

    private final QuartzConnector connector;

    public QuartzMessageReceiver(Connector connector, Service service, Endpoint endpoint)
            throws CreateException
    {
        super(connector, service, endpoint);
        this.connector = (QuartzConnector) connector;
    }

    protected void doDispose()
    {
        // template method
    }

    protected void doStart() throws MuleException
    {
        try
        {
            Scheduler scheduler = connector.getQuartzScheduler();

            JobDetail jobDetail = new JobDetail();
            jobDetail.setName(endpoint.getEndpointURI().toString());
            jobDetail.setJobClass(MuleReceiverJob.class);
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put(QUARTZ_RECEIVER_PROPERTY, this.getReceiverKey());
            jobDataMap.put(QUARTZ_CONNECTOR_PROPERTY, this.connector.getName());
            jobDataMap.putAll(endpoint.getProperties());
            jobDetail.setJobDataMap(jobDataMap);

            Trigger trigger = null;
            String cronExpression = jobDataMap.getString(QuartzConnector.PROPERTY_CRON_EXPRESSION);
            String repeatInterval = jobDataMap.getString(QuartzConnector.PROPERTY_REPEAT_INTERVAL);
            String repeatCount = jobDataMap.getString(QuartzConnector.PROPERTY_REPEAT_COUNT);
            String startDelay = jobDataMap.getString(QuartzConnector.PROPERTY_START_DELAY);
            String groupName = jobDataMap.getString(QuartzConnector.PROPERTY_GROUP_NAME);
            String jobGroupName = jobDataMap.getString(QuartzConnector.PROPERTY_JOB_GROUP_NAME);

            if (groupName == null)
            {
                groupName = QuartzConnector.DEFAULT_GROUP_NAME;
            }
            if (jobGroupName == null)
            {
                jobGroupName = groupName;
            }

            jobDetail.setGroup(groupName);

            if (cronExpression != null)
            {
                CronTrigger ctrigger = new CronTrigger();
                ctrigger.setCronExpression(cronExpression);
                trigger = ctrigger;
            }
            else if (repeatInterval != null)
            {
                SimpleTrigger strigger = new SimpleTrigger();
                strigger.setRepeatInterval(Long.parseLong(repeatInterval));
                if (repeatCount != null)
                {
                    strigger.setRepeatCount(Integer.parseInt(repeatCount));
                }
                else
                {
                    strigger.setRepeatCount(-1);
                }
                trigger = strigger;
            }
            else
            {
                throw new IllegalArgumentException(
                        QuartzMessages.cronExpressionOrIntervalMustBeSet().getMessage());
            }
            long start = System.currentTimeMillis();
            if (startDelay != null)
            {
                start += Long.parseLong(startDelay);
            }
            trigger.setStartTime(new Date(start));
            trigger.setName(endpoint.getEndpointURI().toString());
            trigger.setGroup(groupName);
            trigger.setJobName(endpoint.getEndpointURI().toString());
            trigger.setJobGroup(jobGroupName);

            // We need to handle cases when the job has already been
            // persisted
            try
            {
                scheduler.scheduleJob(jobDetail, trigger);
            }
            catch (ObjectAlreadyExistsException oaee)
            {
                // Do anything here?
            }

            scheduler.start();
        }
        catch (Exception e)
        {
            throw new EndpointException(CoreMessages.failedToStart("Quartz receiver"), e);
        }
    }

    protected void doStop() throws MuleException
    {
        // nothing to do
    }

    protected void doConnect() throws Exception
    {
        // nothing to do
    }

    protected void doDisconnect() throws Exception
    {
        // nothing to do
    }

}
