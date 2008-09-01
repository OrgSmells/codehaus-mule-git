/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.retry.test;

import org.mule.api.retry.RetryPolicy;
import org.mule.retry.PolicyStatus;
import org.mule.retry.policies.AbstractPolicyTemplate;

public class TestRetryPolicyTemplate extends AbstractPolicyTemplate
{
    protected boolean fooBar = false;
    protected int revolutions = 200;

    public TestRetryPolicyTemplate()
    {
        super();
    }

    public TestRetryPolicyTemplate(boolean fooBar, int revolutions)
    {
        super();
        this.fooBar = fooBar;
        this.revolutions = revolutions;
    }

    public RetryPolicy createRetryInstance()
    {
        return new TestRetryPolicy(fooBar, revolutions);
    }

    protected static class TestRetryPolicy implements RetryPolicy
    {
        protected boolean fooBar;
        protected int revolutions;

        public TestRetryPolicy(boolean fooBar, int revolutions)
        {
            this.fooBar = fooBar;
            this.revolutions = revolutions;
        }
        
        public PolicyStatus applyPolicy(Throwable cause)
        {
            return PolicyStatus.policyExhausted(cause);
        }
    }

    public boolean isFooBar()
    {
        return fooBar;
    }

    public void setFooBar(boolean fooBar)
    {
        this.fooBar = fooBar;
    }

    public int getRevolutions()
    {
        return revolutions;
    }

    public void setRevolutions(int revolutions)
    {
        this.revolutions = revolutions;
    }
}
