/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.util.object;

/**
 * An object implementing this interface may be indexed/stored/retrieved by ID.
 */
public interface Identifiable
{
    public String getId();
    
    public void setId(String id);
}


