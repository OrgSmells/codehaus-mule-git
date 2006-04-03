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
import org.mule.umo.lifecycle.InitialisationException;
import org.mule.umo.provider.UMOConnector;
import org.mule.umo.provider.UMOMessageReceiver;
import org.mule.util.Utility;
import org.mule.MuleManager;
import org.apache.commons.lang.StringUtils;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.IOException;

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
}
