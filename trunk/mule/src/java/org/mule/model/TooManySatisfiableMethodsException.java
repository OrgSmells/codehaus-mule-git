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

package org.mule.model;

import org.mule.umo.model.ModelException;
import org.mule.util.StringMessageHelper;
import org.mule.config.i18n.Message;
import org.mule.config.i18n.Messages;

/**
 * <code>TooManySatisfiableMethodsException</code> is thrown by EntryPointResolvers when
 * the component passed has more than one methods that meet the criteria of the configured
 * EntryPointResolver.
 *
 * @author <a href="mailto:ross.mason@cubis.co.uk">Ross Mason</a>
 * @version $Revision$
 * @see org.mule.umo.model.UMOEntryPointResolver
 */
public class TooManySatisfiableMethodsException extends ModelException
{
    /**
     * @param component 
     */
    public TooManySatisfiableMethodsException(Object component)
    {
        super(new Message(Messages.TOO_MANY_ENTRY_POINTS_ON_X, StringMessageHelper.getObjectValue(component)));
    }

    public TooManySatisfiableMethodsException(Object component, Exception cause)
    {
        super(new Message(Messages.TOO_MANY_ENTRY_POINTS_ON_X, StringMessageHelper.getObjectValue(component)), cause);
    }
}
