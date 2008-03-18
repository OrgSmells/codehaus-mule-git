/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.file;

import org.mule.RegistryContext;
import org.mule.api.MessagingException;
import org.mule.api.transport.MessageAdapter;
import org.mule.transport.AbstractMessageAdapterTestCase;
import org.mule.transport.file.FileContentsMessageAdapter;
import org.mule.util.FileUtils;

import java.io.File;
import java.util.Arrays;


public class FileContentsMessageAdapterTestCase extends AbstractMessageAdapterTestCase
{
    private String validMessageContent = "Yabbadabbadooo!";
    private byte[] validMessage = validMessageContent.getBytes();
    private File messageFile;

    /*
     * (non-Javadoc)
     *
     * @see junit.framework.TestCase#setUp()
     */
    protected void doSetUp() throws Exception
    {
        super.doSetUp();

        // The working directory is deleted on tearDown
        File dir = FileUtils.newFile(muleContext.getConfiguration().getWorkingDirectory(), "tmp");
        if (!dir.exists())
        {
            dir.mkdirs();
        }

        messageFile = File.createTempFile("simple", ".mule", dir);
        FileUtils.writeStringToFile(messageFile, validMessageContent, null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.tck.providers.AbstractMessageAdapterTestCase#getValidMessage()
     */
    public Object getValidMessage()
    {
        return validMessage;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.tck.providers.AbstractMessageAdapterTestCase#createAdapter()
     */
    public MessageAdapter createAdapter(Object payload) throws MessagingException
    {
        if (payload.equals(validMessage))
        {
            return new FileContentsMessageAdapter(messageFile);
        }
        else
        {
            // properly throw
            return new FileContentsMessageAdapter(payload);
        }
    }

    // overridden to properly check the byte[] by content and not just by reference
    public void doTestMessageEqualsPayload(Object message, Object payload) throws Exception
    {
        if (message instanceof byte[] && payload instanceof byte[])
        {
            assertTrue(Arrays.equals((byte[])message, (byte[])payload));
        }
        else
        {
            fail("message and payload must both be byte[]");
        }
    }

    public void testMessageContentsProperlyLoaded() throws Exception
    {
        // get new message adapter to test
        MessageAdapter adapter = new FileContentsMessageAdapter(messageFile);

        // delete the file before accessing the payload
         assertTrue(messageFile.delete());

        // slight detour for testing :)
        doTestMessageEqualsPayload(validMessage, adapter.getPayload());
    }

    /**
     * This is not a valid use case since Transport adapters are immutable, hence a new one should be created
     * for each messages
     */
//    public void testMultipleSetMessageCalls() throws Exception
//    {
//        // get new message adapter to test
//        FileContentsMessageAdapter adapter = new FileContentsMessageAdapter(messageFile);
//
//        // access first payload
//        doTestMessageEqualsPayload(validMessage, adapter.getPayload());
//
//        // create another source file
//        String secondMessageContent = "Hooray";
//        byte[] secondMessage = secondMessageContent.getBytes();
//        File secondFile = File.createTempFile("simple2", ".mule", messageFile.getParentFile());
//        FileUtils.writeStringToFile(secondFile, secondMessageContent, null);
//
//        // replace the first message content
        //This shouln't even be visible
//        adapter.setFileMessage(secondFile);
//
//        // make sure the file was properly read
//        doTestMessageEqualsPayload(secondMessage, adapter.getPayload());
//    }

}
