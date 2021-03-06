/* 
 * $Header$
 * $Revision$
 * $Date$
 * ------------------------------------------------------------------------------------------------------
 * 
 * Copyright (c) SymphonySoft Limited. All rights reserved.
 * http://www.symphonysoft.com
 * 
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file. 
 *
 */

package org.mule.umo.lifecycle;

/**
 * <code>Initialisable</code> is a lifecycle interface that gets called at the
 * initialise lifecycle stage of the implementing component.
 * 
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */
public interface Initialisable
{
    /**
     * Method used to perform any initialisation work. If a fatal error occurs
     * during initialisation an <code>InitialisationException</code> should be
     * thrown, causing the Mule instance to shutdown. If the error is
     * recoverable, say by retrying to connect, a
     * <code>RecoverableException</code> should be thrown. There is no
     * guarantee that by throwing a Recoverable exception that the Mule instance
     * will not shut down.
     * 
     * @throws InitialisationException if a fatal error occurs causing the Mule
     *             instance to shutdown
     * @throws RecoverableException if an error occurs that can be recovered
     *             from
     */
    void initialise() throws InitialisationException, RecoverableException;
}
