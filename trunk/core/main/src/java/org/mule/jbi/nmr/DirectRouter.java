/* 
* $Header$
* $Revision: 3 $
* $Date: 2005-11-19 17:43:12 +0100 (Sat, 19 Nov 2005) $
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
package org.mule.jbi.nmr;

import org.mule.registry.Registry;

import javax.jbi.messaging.MessageExchange;
import javax.jbi.messaging.MessagingException;

/**
 * todo document
 *
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision: 3 $
 */
public class DirectRouter extends AbstractRouter {

    public DirectRouter(Registry registry) {
        super(registry);
    }

    public void route(MessageExchange me) throws MessagingException {
        routeExchange(me);
    }
}
