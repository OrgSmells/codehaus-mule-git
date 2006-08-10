/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the BSD style
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.umo.lifecycle;

/**
 * <code>Lifecycle</code> adds lifecycle methods <code>start</code>,
 * <code>stop</code> and <code>dispose</code>.
 * 
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */
public interface Lifecycle extends Startable, Stoppable, Disposable
{
    // no additional methods - see super interfaces
}
