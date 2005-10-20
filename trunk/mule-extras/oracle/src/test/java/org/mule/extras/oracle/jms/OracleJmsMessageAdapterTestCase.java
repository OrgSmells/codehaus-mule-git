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

import javax.jms.Message;

import org.mule.tck.providers.AbstractMessageAdapterTestCase;
import org.mule.umo.provider.UMOMessageAdapter;

/**
 * @author <a href="mailto:carlson@hotpop.com">Travis Carlson</a>
 */
public class OracleJmsMessageAdapterTestCase extends AbstractMessageAdapterTestCase
{
    public UMOMessageAdapter createAdapter(Object payload) throws Exception
    {
        return new OracleJmsMessageAdapter(payload);
    }

    public Object getValidMessage() throws Exception
    {
        return OracleJmsConnectorTestCase.getMessage();
    }
}
