/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.jms;

import org.mule.tck.AbstractMuleTestCase;
import org.mule.transport.jms.JmsConstants;
import org.mule.transport.jms.JmsMessageUtils;

import com.mockobjects.constraint.IsInstanceOf;
import com.mockobjects.dynamic.Mock;

import javax.jms.BytesMessage;
import javax.jms.TextMessage;

public class JmsMessageUtilsTestCase extends AbstractMuleTestCase
{

    public void testHeaders()
    {
        // already valid headers are returned as-is, so we can assertSame
        assertSame("identifier", JmsMessageUtils.encodeHeader("identifier"));
        assertSame("_identifier", JmsMessageUtils.encodeHeader("_identifier"));
        assertSame("identifier_", JmsMessageUtils.encodeHeader("identifier_"));
        assertSame("ident_ifier", JmsMessageUtils.encodeHeader("ident_ifier"));

        assertEquals("_identifier", JmsMessageUtils.encodeHeader("-identifier"));
        assertEquals("identifier_", JmsMessageUtils.encodeHeader("identifier-"));
        assertEquals("ident_ifier", JmsMessageUtils.encodeHeader("ident-ifier"));
        assertEquals("_ident_ifier_", JmsMessageUtils.encodeHeader("-ident_ifier-"));
        assertEquals("_ident_ifier_", JmsMessageUtils.encodeHeader("-ident-ifier-"));
    }

    public void testTextMessageNullContent() throws Exception
    {
        Mock mockMessage = new Mock(TextMessage.class);
        mockMessage.expectAndReturn("getText", null);

        TextMessage mockTextMessage = (TextMessage)mockMessage.proxy();

        byte[] result = JmsMessageUtils.toByteArray(mockTextMessage, JmsConstants.JMS_SPECIFICATION_102B);
        assertNotNull(result);
        assertEquals("Should return an empty byte array.", 0, result.length);

        mockMessage.verify();
    }

    public void testByteMessageNullContent() throws Exception
    {
        // test for JMS 1.0.2-compliant code path
        Mock mockMessage = new Mock(BytesMessage.class);
        mockMessage.expect("reset");
        mockMessage.expectAndReturn("readBytes", new IsInstanceOf(byte[].class), -1);
        BytesMessage mockBytesMessage = (BytesMessage)mockMessage.proxy();

        byte[] result = JmsMessageUtils.toByteArray(mockBytesMessage, JmsConstants.JMS_SPECIFICATION_102B);
        assertNotNull(result);
        assertEquals("Should return an empty byte array.", 0, result.length);
        mockMessage.verify();

        // test for JMS 1.1-compliant code path
        mockMessage = new Mock(BytesMessage.class);
        mockMessage.expect("reset");
        mockMessage.expectAndReturn("getBodyLength", new Long(0));
        mockBytesMessage = (BytesMessage)mockMessage.proxy();

        result = JmsMessageUtils.toByteArray(mockBytesMessage, JmsConstants.JMS_SPECIFICATION_11);
        assertNotNull(result);
        assertEquals("Should return an empty byte array.", 0, result.length);
        mockMessage.verify();
    }

}
