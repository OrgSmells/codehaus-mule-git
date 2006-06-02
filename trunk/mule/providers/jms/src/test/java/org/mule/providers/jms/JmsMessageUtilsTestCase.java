/*
 * $Header$
 * $Revision: 1390 $
 * $Date: 2006-02-20 06:03:55 -0500 (Mon, 20 Feb 2006) $
 * ------------------------------------------------------------------------------------------------------
 *
 * Copyright (c) SymphonySoft Limited. All rights reserved.
 * http://www.symphonysoft.com
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */

package org.mule.providers.jms;

import com.mockobjects.dynamic.Mock;

import org.mule.tck.AbstractMuleTestCase;

import javax.jms.TextMessage;

/**
 * @author <a href="mailto:aperepel@itci.com">Andrew Perepelytsya</a>
 */
public class JmsMessageUtilsTestCase extends AbstractMuleTestCase {

    public void testTextMessageNullContent() throws Exception {
        Mock mockMessage = new Mock(TextMessage.class);
        mockMessage.expectAndReturn("getText", null);

        TextMessage mockTextMessage = (TextMessage) mockMessage.proxy();

        byte[] result = JmsMessageUtils.getBytesFromMessage(mockTextMessage);
        assertNotNull(result);
        assertEquals("Should return an empty byte array.", 0, result.length);

        mockMessage.verify();
    }
}
