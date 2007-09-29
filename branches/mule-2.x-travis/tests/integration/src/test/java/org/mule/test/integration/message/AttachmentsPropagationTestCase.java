/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.test.integration.message;

import org.mule.extras.client.MuleClient;
import org.mule.providers.email.transformers.PlainTextDataSource;
import org.mule.tck.AbstractMuleTestCase;
import org.mule.tck.MuleTestUtils;
import org.mule.tck.functional.EventCallback;
import org.mule.tck.functional.FunctionalTestComponent;
import org.mule.umo.UMOEventContext;
import org.mule.umo.UMOMessage;
import org.mule.umo.endpoint.UMOEndpoint;

import javax.activation.DataHandler;

public class AttachmentsPropagationTestCase extends AbstractMuleTestCase implements EventCallback
{
    // @Override
    protected void doSetUp() throws Exception
    {
        super.doSetUp();

        UMOEndpoint vmSingle = managementContext.getRegistry().createEndpointFromUri("vm://Single", UMOEndpoint.ENDPOINT_TYPE_RECEIVER, managementContext);
        UMOEndpoint vmChained = managementContext.getRegistry().createEndpointFromUri("vm://Chained", UMOEndpoint.ENDPOINT_TYPE_RECEIVER, managementContext);

        FunctionalTestComponent single = new FunctionalTestComponent();
        single.setEventCallback(this);
        FunctionalTestComponent chained = new FunctionalTestComponent();
        chained.setEventCallback(this);
        managementContext.getRegistry().registerService(single, "SINGLE", vmSingle.getEndpointURI());
        managementContext.getRegistry().registerService(chained, "CHAINED", vmChained.getEndpointURI(), vmSingle.getEndpointURI());
    }

    public void eventReceived(UMOEventContext context, Object component) throws Exception
    {
        UMOMessage message = context.getMessage();
        // add an attachment, named after the componentname...
        message.addAttachment(context.getComponent().getName(), new DataHandler(
            new PlainTextDataSource("text/plain", "<content>")));

        // return the list of attachment names
        FunctionalTestComponent fc = (FunctionalTestComponent) component;
        fc.setReturnMessage(message.getAttachmentNames().toString());
    }

    public void testSingleComponentKnowsAttachments() throws Exception
    {

        MuleClient client = new MuleClient();
        UMOMessage result = client.send("vm://Single", "", null);
        assertNotNull(result);

        // expect SINGLE attachment from SINGLE component
        assertEquals("[SINGLE]", result.getPayloadAsString());
    }

    public void testChainedComponentKnowsAttachments() throws Exception
    {
        MuleClient client = new MuleClient();
        UMOMessage result = client.send("vm://Chained", "", null);
        assertNotNull(result);

        // expect CHAINED attachment from CHAINED component
        // and SINGLE attachment from SINGLE component
        assertEquals("[SINGLE, CHAINED]", result.getPayloadAsString());
    }

    public void testClientReceivesAttachments() throws Exception
    {
        // a MuleClient should be able to receive attachments
        MuleClient client = new MuleClient();

        UMOMessage result = client.send("vm://Single", "", null);
        assertNotNull(result);

        // expect SINGLE attachment from SINGLE component
        assertEquals("[SINGLE]", result.getPayloadAsString());
        assertNotNull(result.getAttachment("SINGLE"));
        assertEquals("<content>", result.getAttachment("SINGLE").getContent().toString());

        result = client.send("vm://Chained", "", null);
        assertNotNull(result);

        // expect SINGLE and CHAINED attachments
        assertEquals("[SINGLE, CHAINED]", result.getPayloadAsString());
        assertNotNull(result.getAttachment("SINGLE"));
        assertEquals("<content>", result.getAttachment("SINGLE").getContent().toString());
        assertNotNull(result.getAttachment("CHAINED"));
        assertEquals("<content>", result.getAttachment("CHAINED").getContent().toString());
    }
}
