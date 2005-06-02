/* 
 * $Header$
 * $Revision$
 * $Date$
 * ------------------------------------------------------------------------------------------------------
 * 
 * Copyright (c) SymphonySoft Limited. All rights reserved.
 * http://www.symphonysoft.com
 * 
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file. 
 *
 */
package org.mule.providers;

import org.mule.umo.lifecycle.FatalException;
import org.mule.umo.lifecycle.InitialisationException;
import org.mule.config.i18n.Message;

/**
 * todo document
 *
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */
public class ConnectException extends InitialisationException 
{
    public ConnectException(Message message, Object component) {
        super(message, component);
    }

    public ConnectException(Message message, Throwable cause, Object component) {
        super(message, cause, component);
    }

    public ConnectException(Throwable cause, Object component) {
        super(cause, component);
    }
}
