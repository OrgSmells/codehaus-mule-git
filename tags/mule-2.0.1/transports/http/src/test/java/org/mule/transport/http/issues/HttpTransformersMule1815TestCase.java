/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.http.issues;

import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.FunctionalTestCase;
import org.mule.tck.functional.FunctionalTestComponent;
import org.mule.tck.functional.StringAppendTestTransformer;

public class HttpTransformersMule1815TestCase extends FunctionalTestCase
{

    public static final String OUTBOUND_MESSAGE = "Test message";

    protected String getConfigResources()
    {
        return "http-transformers-mule-1815-test.xml";
    }

    private MuleMessage sendTo(String uri) throws MuleException
    {
        MuleClient client = new MuleClient();
        MuleMessage message = client.send(uri, OUTBOUND_MESSAGE, null);
        assertNotNull(message);
        return message;
    }

    /**
     * With no transformer we expect just the modification from the FTC
     *
     * @throws Exception
     */
    public void testBase() throws Exception
    {
        assertEquals(
                FunctionalTestComponent.received(OUTBOUND_MESSAGE),
                sendTo("base").getPayloadAsString());
    }

    /**
     * Adapted model, which should not apply transformers
     *
     * @throws Exception
     */
    public void testAdapted() throws Exception
    {
        assertEquals(
                FunctionalTestComponent.received(OUTBOUND_MESSAGE),
                sendTo("adapted").getPayloadAsString());
    }

    /**
     * Transformers on the adapted model should be ignored
     *
     * @throws Exception
     */
    public void testIgnored() throws Exception
    {
        assertEquals(
                FunctionalTestComponent.received(OUTBOUND_MESSAGE),
                sendTo("ignored").getPayloadAsString());
    }

    /**
     * But transformers on the base model should be applied
     *
     * @throws Exception
     */
    public void testInbound() throws Exception
    {
        assertEquals(
                // this reads backwards - innermost is first in chain
                FunctionalTestComponent.received(
                        StringAppendTestTransformer.append(" transformed 2",
                                StringAppendTestTransformer.appendDefault(
                                        OUTBOUND_MESSAGE))),
                sendTo("inbound").getPayloadAsString());
    }

}
