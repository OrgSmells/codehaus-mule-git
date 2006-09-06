/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the BSD style
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.providers.gs.filters;

import net.jini.core.entry.Entry;
import org.mule.umo.UMOFilter;

import java.lang.reflect.InvocationTargetException;

/**
 * todo document
 *
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */
public interface JavaSpaceFilter extends UMOFilter {
    
    public Entry getEntry() throws IllegalAccessException, NoSuchMethodException,
            InvocationTargetException, InstantiationException, ClassNotFoundException;
}
