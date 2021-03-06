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

package org.mule.providers.file;

import org.mule.MuleRuntimeException;
import org.mule.config.i18n.Message;
import org.mule.config.i18n.Messages;
import org.mule.umo.MessagingException;

/**
 * <code>FileContentsMessageAdapter</code> provides a wrapper for file data.
 * Users can obtain the contents of the message through the payload property and
 * can get the filename and directory in the properties using PROPERTY_FILENAME
 * and PROPERTY_DIRECTORY.
 * 
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */
public class FileContentsMessageAdapter extends FileMessageAdapter
{

    public FileContentsMessageAdapter(Object message) throws MessagingException
    {
        super(message);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.providers.UMOMessageAdapter#getPayload()
     */
    public Object getPayload()
    {
        synchronized (this) {
            try {
                return this.getPayloadAsBytes();
            }
            catch (Exception noPayloadException) {
                throw new MuleRuntimeException(new Message(Messages.FAILED_TO_READ_PAYLOAD),
                        noPayloadException);
            }
        }
    }

}
