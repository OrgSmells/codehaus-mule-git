/* 

 * $Header$

 * $Revision$

 * $Date$

 * ------------------------------------------------------------------------------------------------------

 * 

 * Copyright (c) Cubis Limited. All rights reserved.

 * http://www.cubis.co.uk 

 * 

 * The software in this package is published under the terms of the BSD

 * style license a copy of which has been included with this distribution in

 * the LICENSE.txt file. 

 *

 */

package org.mule.providers.stream;


import org.mule.providers.AbstractServiceEnabledConnector;
import org.mule.umo.UMOComponent;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.provider.UMOMessageReceiver;

import java.io.InputStream;
import java.io.OutputStream;


/**
 * <code>StreamConnector</code> can send and receive mule events over IO streams.
 *
 * @author <a href="mailto:ross.mason@cubis.co.uk">Ross Mason</a>
 * @version $Revision$
 */

public abstract class StreamConnector extends AbstractServiceEnabledConnector
{

    protected StreamConnector()
    {
    }

    /* (non-Javadoc)
     * @see org.mule.umo.provider.UMOConnector#registerListener(org.mule.umo.UMOSession, org.mule.umo.endpoint.UMOEndpoint)
     */
    public UMOMessageReceiver createReceiver(UMOComponent component, UMOEndpoint endpoint) throws Exception
    {
         return serviceDescriptor.createMessageReceiver(this, component, endpoint, new Object[]{getInputStream(), new Long(1000)});
    }

    /* (non-Javadoc)
     * @see org.mule.providers.AbstractConnector#stopConnector()
     */
    public synchronized void stopConnector()
    {
    }

    protected void disposeConnector()
    {
    }

    /* (non-Javadoc)
     * @see org.mule.providers.AbstractConnector#startConnector()
     */
    public synchronized void startConnector()
    {
    }

    /* (non-Javadoc)
     * @see org.mule.umo.provider.UMOConnector#getProtocol()
     */

    public String getProtocol()
    {
        return "stream";
    }

    public abstract InputStream getInputStream();


    public abstract OutputStream getOutputStream();


    /**
     * Sub classes might want to reinitialise between stream reads here
     */
    public abstract void reinitialise();

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    public void run()
    {
        // noop
    }
}