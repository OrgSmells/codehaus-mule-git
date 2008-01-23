/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.api.store;

import org.mule.api.MuleException;
import org.mule.config.i18n.Message;

/**
 * The base exception thrown where there is a problem reading or writing to a store
 */
public class StoreException extends MuleException
{

    public StoreException(Message message)
    {
        super(message);
    }

    public StoreException(Message message, Throwable cause)
    {
        super(message, cause);
    }
}
