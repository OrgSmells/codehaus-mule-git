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

package org.mule.umo.model;

import org.mule.umo.UMODescriptor;

/**
 * <code>UMOEntryPointResolver</code> resolves a method to call on the given
 * UMODescriptor when an event is recieved for the component
 *
 * @author <a href="mailto:ross.mason@cubis.co.uk">Ross Mason</a>
 * @version $Revision$
 */
public interface UMOEntryPointResolver
{
    public UMOEntryPoint resolveEntryPoint(UMODescriptor componentDescriptor) throws ModelException;
}
