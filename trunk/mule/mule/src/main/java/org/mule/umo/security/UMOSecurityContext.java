/*
 * $Id
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the BSD style
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.umo.security;

/**
 * <code>UMOSecurityContext</code> holds security information and is
 * associated with the UMOSession.
 * 
 * @see org.mule.umo.UMOSession
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */

public interface UMOSecurityContext
{
    void setAuthentication(UMOAuthentication authentication);

    UMOAuthentication getAuthentication();
}
