/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.retry.policies;

import org.mule.api.retry.RetryPolicy;
import org.mule.retry.PolicyStatus;

import edu.emory.mathcs.backport.java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This policy allows the user to configure how many times a retry should be attempted and
 * how much time to wait between retries.
 */
public class SimpleRetryPolicyFactory extends AbstractPolicyFactory
{
    /**
     * logger used by this class
     */
    protected transient final Log logger = LogFactory.getLog(SimpleRetryPolicyFactory.class);

    public static final int DEFAULT_FREQUENCY = 2000;
    public static final int DEFAULT_RETRY_COUNT = 2;
    public static final int RETRY_COUNT_FOREVER = -1;

    protected volatile int count = DEFAULT_RETRY_COUNT;
    protected volatile long frequency = DEFAULT_FREQUENCY;

    public SimpleRetryPolicyFactory()
    {
        super();
    }

    public SimpleRetryPolicyFactory(long frequency, int retryCount)
    {
        this.frequency = frequency;
        this.count = retryCount;
    }

    public long getFrequency()
    {
        return frequency;
    }

    public int getCount()
    {
        return count;
    }

    public void setFrequency(long frequency)
    {
        this.frequency = frequency;
    }

    public void setCount(int count)
    {
        this.count = count;
    }

    public RetryPolicy create()
    {
        return new SimpleRetryPolicy(frequency, count);
    }

    protected static class SimpleRetryPolicy implements RetryPolicy
    {
        protected transient final Log logger = LogFactory.getLog(SimpleRetryPolicy.class);

        protected static final RetryCounter retryCounter = new RetryCounter();

        protected static final ThreadLocal called = new ThreadLocal();

        private volatile int count = DEFAULT_RETRY_COUNT;
        private volatile long frequency = DEFAULT_FREQUENCY;

        public SimpleRetryPolicy(long frequency, int retryCount)
        {
            this.frequency = frequency;
            this.count = retryCount;
        }

        public int getCount()
        {
            return count;
        }

        /**
         * How many times to retry. Set to -1 to retry forever.
         * 
         * @param retryCount number of retries
         */
        public void setCount(int count)
        {
            this.count = count;
        }

        public long getFrequency()
        {
            return frequency;
        }

        public void setFrequency(long frequency)
        {
            this.frequency = frequency;
        }

        public PolicyStatus applyPolicy(Throwable cause)
        {

            if (count != RETRY_COUNT_FOREVER && retryCounter.current().get() >= count)
            {
                return PolicyStatus.policyExhausted(cause);
            }
            else
            {

                if (logger.isInfoEnabled())
                {
                    logger.info("Waiting for "
                                + frequency
                                + "ms before reconnecting. Failed attempt "
                                + retryCounter.current().get()
                                + " of "
                                + (count != RETRY_COUNT_FOREVER
                                                ? String.valueOf(count)
                                                : "unlimited"));
                }

                try
                {
                    retryCounter.current().getAndIncrement();
                    Thread.sleep(frequency);
                    return PolicyStatus.policyOk();
                }
                catch (InterruptedException e)
                {
                    // If we get an interrupt exception, some one is telling us to stop
                    return PolicyStatus.policyExhausted(e);
                }
            }
        }

        protected static class RetryCounter extends ThreadLocal
        {
            public int countRetry()
            {
                return ((AtomicInteger) get()).incrementAndGet();
            }

            public void reset()
            {
                ((AtomicInteger) get()).set(0);
            }

            public AtomicInteger current()
            {
                return (AtomicInteger) get();
            }

            // @Override
            protected Object initialValue()
            {
                return new AtomicInteger(0);
            }
        }
    }


    public String toString()
    {
        final StringBuffer sb = new StringBuffer();
        sb.append("SimpleRetryPolicy");
        sb.append("{frequency=").append(frequency);
        sb.append(", retryCount=").append(count);
        sb.append(", connectAsync=").append(isConnectAsynchronously());
        sb.append('}');
        return sb.toString();
    }
}
