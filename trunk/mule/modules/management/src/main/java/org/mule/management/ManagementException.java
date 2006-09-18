/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the BSD style
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.management;

import org.mule.config.i18n.Message;
import org.mule.umo.UMOException;

/**
 * <code>ManagementException</code> is a general exception thrown by
 * management extensions
 * 
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */
public abstract class ManagementException extends UMOException
{
    /**
     * @param message the exception message
     */
    protected ManagementException(Message message)
    {
        super(message);
    }

    /**
     * @param message the exception message
     * @param cause the exception that cause this exception to be thrown
     */
    protected ManagementException(Message message, Throwable cause)
    {
        super(message, cause);
    }

    protected ManagementException(Throwable cause)
    {
        super(cause);
    }
}
