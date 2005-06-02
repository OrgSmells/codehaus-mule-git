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
 */
package org.mule.providers.email;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.providers.AbstractServiceEnabledConnector;


/**
 * <code>Pop3Connector</code> is used to connect and receive mail from a
 * pop3  mailbox
 *
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */

public class Pop3Connector extends AbstractServiceEnabledConnector
{
    public static final String MAILBOX = "INBOX";
    public static final int DEFAULT_POP3_PORT = 110;
    public static final int DEFAULT_CHECK_FREQUENCY = 60000;

    /**
     * Holds the time in milliseconds that the endpoint should wait before checking a mailbox
     */
    private long checkFrequency = DEFAULT_CHECK_FREQUENCY;

    /**
     * holds a path where messages should be backed up to
     */
    private String backupFolder = null;

    /**
     * @return
     */
    public long getCheckFrequency()
    {
        return checkFrequency;
    }

    /* (non-Javadoc)
     * @see org.mule.providers.UMOConnector#getProtocol()
     */
    public String getProtocol()
    {
        return "pop3";
    }

    /**
     * @param l
     */
    public void setCheckFrequency(long l)
    {
        if(l < 1) l = DEFAULT_CHECK_FREQUENCY;
        checkFrequency = l;
    }

    /**
     * @return
     */
    public String getBackupFolder()
    {
        return backupFolder;
    }

    /**
     * @param string
     */
    public void setBackupFolder(String string)
    {
        backupFolder = string;
    }
}