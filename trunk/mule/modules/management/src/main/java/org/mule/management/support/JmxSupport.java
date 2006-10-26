/*
* $Id$
* --------------------------------------------------------------------------------------
* Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
*
* The software in this package is published under the terms of the BSD style
* license, a copy of which has been included with this distribution in the
* LICENSE.txt file.
*/

package org.mule.management.support;

import javax.management.ObjectName;
import javax.management.MalformedObjectNameException;

/**
 * Mule JMX supporting interface.
 */
public interface JmxSupport
{
    /**
     * Uses JMX 1.2 and higher standard escape method and semantics.
     * @param name value to escape for JMX compliance
     * @return value valid for JMX
     */
    String escape(String name);

    /**
     * Calculates the domain name for the current Mule instance. The rules are:
     * <ul>
     * <li>Default Mule domain
     * <li>If this server's instance ID is available, append "." (dot) and the ID
     * <li>If no instance ID is available, don't append anything
     * </ul>
     * @return JMX domain name
     */
    String getDomainName();

    /**
     * Create an object name. May cache the result.
     * @param name jmx object name
     * @return object name for MBeanServer consumption
     * @throws MalformedObjectNameException for invalid names
     */
    ObjectName getInstance(String name) throws MalformedObjectNameException;
}
