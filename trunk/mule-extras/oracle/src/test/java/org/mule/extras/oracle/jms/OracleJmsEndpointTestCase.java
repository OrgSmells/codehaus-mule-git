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
package org.mule.extras.oracle.jms;

import org.mule.impl.endpoint.MuleEndpointURI;
import org.mule.tck.NamedTestCase;
import org.mule.umo.endpoint.UMOEndpointURI;

/**
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */
public class OracleJmsEndpointTestCase extends NamedTestCase
{
    public void testWithoutPayloadFactory() throws Exception {
    	UMOEndpointURI url = 
    		new MuleEndpointURI("jms://XML_QUEUE?transformers=XMLMessageToString");
        assertNull(url.getParams().getProperty(OracleJmsConnector.PAYLOADFACTORY_PROPERTY));
    }

    public void testWithPayloadFactory() throws Exception {
    	UMOEndpointURI url = 
    		new MuleEndpointURI("jms://XML_QUEUE" +
				"?" + OracleJmsConnector.PAYLOADFACTORY_PROPERTY + "=oracle.xdb.XMLTypeFactory" +
	            "&transformers=XMLMessageToString");
        assertEquals("oracle.xdb.XMLTypeFactory", url.getParams().getProperty(OracleJmsConnector.PAYLOADFACTORY_PROPERTY));
    }
}
