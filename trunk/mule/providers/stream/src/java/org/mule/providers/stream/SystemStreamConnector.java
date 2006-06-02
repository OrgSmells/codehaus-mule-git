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
package org.mule.providers.stream;

import org.mule.umo.UMOComponent;
import org.mule.umo.UMOException;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.provider.UMOConnector;
import org.mule.umo.provider.UMOMessageReceiver;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * <code>SystemStreamConnector</code> Connects to the System streams in and out by default and add some basic fuctionality for
 * Writing out prompt messages
 *
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */

public class SystemStreamConnector extends StreamConnector
{

    private String promptMessage;

    private long messageDelayTime = 3000;

    private boolean firstTime=true;

    public SystemStreamConnector()
    {
        super();
        inputStream = System.in;
        outputStream = System.out;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.mule.providers.stream.StreamConnector#getInputStream()
     */
    public InputStream getInputStream()
    {
        return inputStream;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.mule.providers.AbstractConnector#doStart()
     */
    public void doStart()
    {
        firstTime = false;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.mule.providers.stream.StreamConnector#getOutputStream()
     */
    public OutputStream getOutputStream()
    {
        return outputStream;
    }

    /**
     * @return Returns the promptMessage.
     */
    public String getPromptMessage()
    {
        return promptMessage;
    }

    /**
     * @param promptMessage The promptMessage to set.
     */
    public void setPromptMessage(String promptMessage)
    {
        this.promptMessage = promptMessage;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.mule.umo.provider.UMOMessageDispatcher#getConnector()
     */
    public UMOConnector getConnector()
    {
        return this;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.mule.umo.provider.UMOMessageDispatcher#getDelegateSession()
     */
    public Object getDelegateSession() throws UMOException
    {
        return null;
    }

    public UMOMessageReceiver registerListener(UMOComponent component, UMOEndpoint endpoint) throws Exception
    {
        if (receivers.size() > 0) {
            throw new UnsupportedOperationException("You can only register one listener per system stream connector");
        }
        UMOMessageReceiver receiver = super.registerListener(component, endpoint);
        return receiver;
    }


    public long getMessageDelayTime() {
        if(firstTime) {
            return messageDelayTime + 4000;
        } else {
            return messageDelayTime;
        }
    }

    public void setMessageDelayTime(long messageDelayTime) {
        this.messageDelayTime = messageDelayTime;
    }


    protected void doDispose () {
        // Override as a no-op.
        // The reason is System.in/out shouldn't be closed.
        // It is valid for them to remain open (consider, e.g. tail -F).
        // Trying to close System.in will result in I/O block, and
        // available() will always return 0 bytes for System.in.

        // There is a scheme to get a ref to System.in via NIO,
        // e.g. :
        // FileInputStream fis = new FileInputStream(FileDescriptor.in);
        // InputStream is = Channels.newInputStream(fis.getChannel);
        //
        // It is then possible to register a watchdog thread for the caller
        // which will interrupt this (now wrapped with NIO) read() call.

        // Well, it isn't absolutely required for the reasons stated above,
        // just following the KISS principle.
    }
}
