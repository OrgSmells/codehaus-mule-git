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
 
package org.mule.samples.errorhandler.handlers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.samples.errorhandler.ErrorMessage;
import org.mule.samples.errorhandler.HandlerException;
import org.mule.samples.errorhandler.exceptions.FatalException;
import org.mule.util.StringMessageHelper;

/**
 *  <code>FatalBehaviour</code> TODO (document class)
 *
 * @author <a href="mailto:ross.mason@cubis.co.uk">Ross Mason</a>
 * @version $Revision$
 */
public class FatalHandler extends DefaultHandler
{
    /** logger used by this class */
    private static transient Log logger = LogFactory.getLog(FatalHandler.class);

    public FatalHandler( ) {
       super();
       registerException(FatalException.class);
    }

	public void processException(ErrorMessage message, Throwable t) throws HandlerException
	{
        System.out.println( StringMessageHelper.getBoilerPlate("Exception received in \n" +
                " FATAL EXCEPTION HANDLER \n." +
                " Logic could be put in here to enrich the message content"));
        logger.fatal("Exception is: " + t, t);
    }

}
