/*
* $Id:ExceptionNotificationListener.java 7275 2007-06-28 02:51:31Z aperepel $
* --------------------------------------------------------------------------------------
* Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
*
* The software in this package is published under the terms of the CPAL v1.0
* license, a copy of which has been included with this distribution in the
* LICENSE.txt file.
*/

package org.mule.api.context.notification;


/**
 * <code>ExceptionNotificationListener</code> is an observer interface that
 * objects can implement and then register themselves with the Mule manager to be
 * notified when a Exception event occurs.
 */
public interface ExceptionNotificationListener extends ServerNotificationListener
{
    // no methods
}
