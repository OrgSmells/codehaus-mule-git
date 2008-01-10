/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.util.properties;

import org.mule.providers.NullPayload;
import org.mule.umo.UMOMessage;

import java.sql.Timestamp;

/**
 * Looks up the property on the message using the name given.
 */
public class MessageHeaderPropertyExtractor implements PropertyExtractor
{
    public static final String NAME = "header";

    public Object getProperty(String name, Object message)
    {
        if (message instanceof UMOMessage)
        {
            if (name.equalsIgnoreCase("payload"))
            {
                Object payload = ((UMOMessage) message).getPayload();
                return (payload instanceof NullPayload ? null : payload);
            }
            else
            {
                return ((UMOMessage) message).getProperty(name);
            }
        }
        return null;
    }

    /** {@inheritDoc} */
    public String getName()
    {
        return NAME;
    }

    /** {@inheritDoc} */
    public void setName(String name)
    {
        throw new UnsupportedOperationException("setName");
    }
}
