/*
 * Copyright 2005 SymphonySoft Limited. All rights reserved.
 * http://www.symphonysoft.com
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * ------------------------------------------------------------------------------------------------------
 * $Header$
 * $Revision$
 * $Date$
 */
package org.mule.jbi;

import javax.jbi.messaging.MessageExchange;
import javax.jbi.messaging.MessagingException;
import javax.jbi.servicedesc.ServiceEndpoint;

public interface Router {

	ServiceEndpoint getTargetEndpoint(MessageExchange me) throws MessagingException;
}
