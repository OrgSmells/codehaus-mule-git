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
 */
package org.mule.impl;

import org.mule.umo.UMODescriptor;

/**
 * <code>UMODescriptorAware</code> is an injector interface that will supply a
 * UMODescriptor to the object.  This interface should be implemented by
 * components managed by mule that want to receive their UMODescriptor instance.
 *
 * The UMODescriptor will be set before any initialisation method is called. i.e. if
 * the component implements org.mule.umo.lifecycle.Initialisable, the descriptor will
 * be set before initialise() method is called.
 *
 * @author <a href="mailto:ross.mason@cubis.co.uk">Ross Mason</a>
 * @version $Revision$
 * @see org.mule.umo.lifecycle.Initialisable
 * @see UMODescriptor
 */

public interface UMODescriptorAware
{
    public void setDescriptor(UMODescriptor descriptor);
}
