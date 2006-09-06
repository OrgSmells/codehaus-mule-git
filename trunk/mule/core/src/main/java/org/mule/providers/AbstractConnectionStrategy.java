/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the BSD style
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.MuleManager;
import org.mule.umo.provider.UMOConnectable;
import org.mule.umo.provider.UMOMessageReceiver;

import javax.resource.spi.work.Work;
import javax.resource.spi.work.WorkException;

/**
 * todo document
 * 
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */
public abstract class AbstractConnectionStrategy implements ConnectionStrategy
{

   /**
    * logger used by this class
    */
   protected transient Log logger = LogFactory.getLog(getClass());

   private boolean doThreading = false;

    public final void connect(final UMOConnectable connectable) throws FatalConnectException
    {
        if (doThreading) {
            try {
                MuleManager.getInstance().getWorkManager().scheduleWork(new Work() {
                    public void release()
                    {
                        // nothing to do
                    }

                    public void run()
                    {
                        try {
                            doConnect(connectable);
                        } catch (FatalConnectException e) {
                            resetState();
                            // TODO: this cast is evil
                            if (connectable instanceof AbstractMessageReceiver) {
                                ((AbstractMessageReceiver) connectable).handleException(e);
                            }
                        }
                    }
                });
            } catch (WorkException e) {
                resetState();
                throw new FatalConnectException(e, connectable);
            }
        } else {
            try {
                doConnect(connectable);
            } finally {
                resetState();
            }
        }
    }

    public boolean isDoThreading()
    {
        return doThreading;
    }

    public void setDoThreading(boolean doThreading)
    {
        this.doThreading = doThreading;
    }

    public abstract void doConnect(UMOConnectable connectable) throws FatalConnectException;

    /**
     * Resets any state stored in the retry strategy
     */
    public abstract void resetState();

    protected String getDescription(UMOConnectable connectable) {
        if (connectable instanceof UMOMessageReceiver) {
            return ((UMOMessageReceiver) connectable).getEndpointURI().toString();
        } else {
            return connectable.toString();
        }
    }

}
