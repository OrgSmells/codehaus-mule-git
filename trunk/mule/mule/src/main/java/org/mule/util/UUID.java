/*
 * $Id
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the BSD style
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
 */

package org.mule.util;

import org.safehaus.uuid.UUIDGenerator;

/**
 * <code>UUID</code> Generates a UUID using the doom dark JUG library
 * 
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */

public class UUID
{
    private static UUIDGenerator generator = UUIDGenerator.getInstance();

    private UUID()
    {
        // no go
    }

    public static String getUUID()
    {
        return generator.generateTimeBasedUUID().toString();
    }

}
