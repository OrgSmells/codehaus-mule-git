/* 
 * $Header$
 * $Revision$
 * $Date$
 * ------------------------------------------------------------------------------------------------------
 * 
 * Copyright (c) Cubis Limited. All rights reserved.
 * http://www.cubis.co.uk 
 * 
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file. 
 *
 */

package org.mule.umo.lifecycle;

import org.mule.umo.UMOException;


/**
 * <code>Disposable</code> is a lifecycle interface that gets called
 * at the dispose lifecycle stage of the implementing component as the component
 * is being destroyed.
 *
 * @author <a href="mailto:ross.mason@cubis.co.uk">Ross Mason</a>
 * @version $Revision$
 */
public interface Disposable
{
    public void dispose() throws UMOException;
}
