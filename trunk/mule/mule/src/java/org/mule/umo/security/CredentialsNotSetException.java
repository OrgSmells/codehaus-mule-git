/*
 * $Id$
 * ------------------------------------------------------------------------------------------------------
 *
 * Copyright (c) SymphonySoft Limited. All rights reserved.
 * http://www.symphonysoft.com
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.mule.umo.security;

import org.mule.config.i18n.Message;
import org.mule.umo.UMOMessage;
import org.mule.umo.endpoint.UMOImmutableEndpoint;

/**
 * <code>CredentialsNotSetException</code> is thrown when user credentials
 * cannot be obtained from the current message
 * 
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */
public class CredentialsNotSetException extends UnauthorisedException
{
    public CredentialsNotSetException(Message message, UMOMessage umoMessage)
    {
        super(message, umoMessage);
    }

    public CredentialsNotSetException(Message message, UMOMessage umoMessage, Throwable cause)
    {
        super(message, umoMessage, cause);
    }

    public CredentialsNotSetException(UMOMessage umoMessage,
                                      UMOSecurityContext context,
                                      UMOImmutableEndpoint endpoint,
                                      UMOEndpointSecurityFilter filter)
    {
        super(umoMessage, context, endpoint, filter);
    }
}
