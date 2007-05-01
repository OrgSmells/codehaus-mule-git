/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.umo.transformer;

import org.mule.umo.endpoint.UMOImmutableEndpoint;
import org.mule.umo.lifecycle.Initialisable;

/**
 * <code>UMOTransformer</code> can be chained together to covert message payloads from one
 * object type to another.

 */
public interface UMOBaseTransformer extends Initialisable, Cloneable
{

    /**
     * Sets the endpoint associated with with this connector
     * @param endpoint sets the endpoint associated with the transfromer
     */
    // TODO HH: kill
    void setEndpoint(UMOImmutableEndpoint endpoint);

    /**
     * @param newName the logical name for the transformer
     */
    void setName(String newName);

    /**
     * @return the logical name of the transformer
     */
    String getName();

    // TODO RM*: This can be removed for Mule 2.0 (we'll use Spring bean prototypes)
    Object clone() throws CloneNotSupportedException;

}
