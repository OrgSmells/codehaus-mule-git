/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the BSD style
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.extras.spring.events;

/**
 * <code>ApplicationEventException</code> TODO
 * 
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */

public class ApplicationEventException extends Exception
{
    /**
     * Serial version
     */
    private static final long serialVersionUID = 718759087364948708L;

    public ApplicationEventException(String message)
    {
        super(message);
    }

    public ApplicationEventException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
