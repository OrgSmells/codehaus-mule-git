/*
 * $Header:
 * /cvsroot/mule/mule/src/java/org/mule/providers/vm/VMMessageReceiver.java,v
 * 1.8 2003/11/10 21:05:47 rossmason Exp $ $Revision$ $Date: 2003/11/10
 * 21:05:47 $
 * ------------------------------------------------------------------------------------------------------
 * 
 * Copyright (c) Cubis Limited. All rights reserved. http://www.cubis.co.uk
 * 
 * The software in this package is published under the terms of the BSD style
 * license a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 *  
 */
package org.mule.providers.stream;

import org.mule.InitialisationException;
import org.mule.impl.MuleMessage;
import org.mule.providers.PollingMessageReceiver;
import org.mule.umo.UMOComponent;
import org.mule.umo.UMOMessage;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.provider.UMOConnector;

import java.io.IOException;
import java.io.InputStream;

/**
 * <code>StreamMessageReceiver</code> is a listener of events from a mule
 * components which then simply passes the events on to the target components.
 *
 * @author <a href="mailto:ross.mason@cubis.co.uk">Ross Mason</a>
 * @version $Revision$
 */
public class StreamMessageReceiver extends PollingMessageReceiver
{
    public static final int DEFAULT_BUFFER_SIZE = 1024 * 8;

    private int bufferSize = DEFAULT_BUFFER_SIZE;
    private InputStream inputStream;

    public StreamMessageReceiver(UMOConnector connector,
                       UMOComponent component,
                       UMOEndpoint endpoint,
                       InputStream stream,
                       Long checkFrequency) throws InitialisationException
    {

        super(connector, component, endpoint, checkFrequency);
        inputStream = stream;

    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see org.mule.util.timer.TimeEventListener#timeExpired(org.mule.util.timer.TimeEvent)
	 */
    public void poll()
    {
        try
        {
            StringBuffer message = new StringBuffer();
            byte[] buf = new byte[getBufferSize()];
            int len = inputStream.read(buf);
            if(len==-1) return;
            message.append(new String(buf, 0, len));

            UMOMessage umoMessage = new MuleMessage(connector.getMessageAdapter(message.toString()));
            routeMessage(umoMessage,  endpoint.isSynchronous());

            ((StreamConnector) endpoint.getConnector()).reinitialise();
        }
        catch (IOException e)
        {
            handleException("Failed to read messages from stream", e);
        }
        catch (Exception e)
        {
            handleException("Failed to create or dispatch event from stream", e);
        }
    }

    public InputStream getInputStream()
    {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream)
    {
        this.inputStream = inputStream;
    }

    public int getBufferSize()
    {
        return bufferSize;
    }

    public void setBufferSize(int bufferSize)
    {
        this.bufferSize = bufferSize;
    }
}
