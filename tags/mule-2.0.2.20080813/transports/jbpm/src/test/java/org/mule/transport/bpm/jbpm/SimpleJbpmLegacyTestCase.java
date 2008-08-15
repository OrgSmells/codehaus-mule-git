/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.bpm.jbpm;

/**
 * Tests the connector against jBPM with a simple process.
 * jBPM is instantiated by Spring using the Spring jBPM module.
 */
public class SimpleJbpmLegacyTestCase extends SimpleJbpmTestCase {

    protected String getConfigResources() {
        return "jbpm-functional-legacy-test.xml";
    }
}
