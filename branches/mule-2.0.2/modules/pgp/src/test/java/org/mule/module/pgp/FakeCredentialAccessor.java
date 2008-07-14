/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.pgp;

import org.mule.api.MuleEvent;
import org.mule.api.security.CredentialsAccessor;

public class FakeCredentialAccessor implements CredentialsAccessor
{

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.api.security.CredentialsAccessor#getCredentials(org.mule.api.MuleEvent)
     */
    public Object getCredentials(MuleEvent event)
    {
        return "Mule client <mule_client@mule.com>";
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.api.security.CredentialsAccessor#setCredentials(org.mule.api.MuleEvent,
     *      java.lang.Object)
     */
    public void setCredentials(MuleEvent event, Object credentials)
    {
        // dummy
    }

}
