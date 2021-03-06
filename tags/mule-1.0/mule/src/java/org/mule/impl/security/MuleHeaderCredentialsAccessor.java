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
 */
package org.mule.impl.security;

import org.mule.config.MuleProperties;
import org.mule.umo.UMOEvent;
import org.mule.umo.security.UMOCredentialsAccessor;

/**
 * <code>MuleHeaderCredentialsAccessor</code> obtains and sets the user credentials
 * as Mule property headers
 *
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */
public class MuleHeaderCredentialsAccessor implements UMOCredentialsAccessor
{
    public Object getCredentials(UMOEvent event)
    {
        return event.getProperty(MuleProperties.MULE_USER_PROPERTY);
    }

    public void setCredentials(UMOEvent event, Object credentials)
    {
        event.setProperty(MuleProperties.MULE_USER_PROPERTY, credentials);
    }
}
