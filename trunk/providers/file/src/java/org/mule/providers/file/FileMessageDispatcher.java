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
package org.mule.providers.file;

import org.mule.MuleException;
import org.mule.config.i18n.Message;
import org.mule.impl.MuleMessage;
import org.mule.providers.AbstractMessageDispatcher;
import org.mule.umo.UMOEvent;
import org.mule.umo.UMOException;
import org.mule.umo.UMOMessage;
import org.mule.umo.endpoint.UMOEndpointURI;
import org.mule.umo.provider.UMOConnector;
import org.mule.util.Utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * <code>FileMessageDispatcher</code> is used to read/write files to the filesystem and
 *
 *
 * @author <a href="mailto:ross.mason@cubis.co.uk">Ross Mason</a>
 * @version $Revision$
 */
public class FileMessageDispatcher extends AbstractMessageDispatcher
{
    private FileConnector connector;

    public FileMessageDispatcher(FileConnector connector)
    {
        super(connector);
        this.connector = connector;
    }


    /* (non-Javadoc)
     * @see org.mule.umo.provider.UMOConnectorSession#dispatch(org.mule.umo.UMOEvent)
     */
    public void doDispatch(UMOEvent event) throws Exception
    {
        try
        {
            String endpoint = event.getEndpoint().getEndpointURI().getAddress();
            Object data = event.getTransformedMessage();
            String filename = (String) event.getProperty(FileConnector.PROPERTY_FILENAME);

            if(filename==null) {
                String outPattern = (String) event.getProperty(FileConnector.PROPERTY_OUTPUT_PATTERN);
                if(outPattern==null) {
                    outPattern = connector.getOutputPattern();
                }
                filename = generateFilename(event, outPattern);
            }
            
            if (filename == null)
            {
                throw new IOException("Filename is null");
            }

            File file = Utility.createFile(endpoint +  "/" + filename);
            byte[] buf;
            if (data instanceof byte[])
            {
                buf = (byte[])data;
            } else
            {
                buf = data.toString().getBytes();
            }

            logger.info("Writing file to: " + file.getAbsolutePath());
            FileOutputStream fos = new FileOutputStream(file, connector.isOutputAppend());
            try
            {
                fos.write(buf);
            } finally
            {
                fos.close();
            }
        } catch (Exception e)
        {
            getConnector().handleException(e);
        }

    }

    /**
     * There is no associated session for a file connector
     * @return
     * @throws UMOException
     */
    public Object getDelegateSession() throws UMOException
    {
        return null;
    }

    /**
     * Will attempt to do a receive from a directory, if the endpointUri resolves to a file name
     * the file will be returned, otherwise the first file in the directory according to the
     * filename filter configured on the connector.
     * @param endpointUri a path to a file or directory
     * @param timeout this is ignored when doing a receive on this dispatcher
     * @return a message containing file contents or null if there was notthing to receive
     * @throws Exception
     */
    public UMOMessage receive(UMOEndpointURI endpointUri, long timeout) throws Exception
    {
        File file = new File(endpointUri.getAddress());
        File result = null;
        if (file.exists())
        {
            if (file.isFile())
            {
                result = file;
            } else if (file.isDirectory())
            {
                result = getNextFile(endpointUri.getAddress());
            }
            if (result != null)
            {
                MuleMessage message = new MuleMessage(connector.getMessageAdapter(result));
                if (connector.getMoveToDirectory() != null)
                {
                    {
                        File destinationFile = new File(connector.getMoveToDirectory(), result.getName());
                        if (!result.renameTo(destinationFile))
                        {
                            logger.error("Failed to move file: " + result.getAbsolutePath() + " to " + destinationFile.getAbsolutePath());
                        }
                    }
                }
                result.delete();
                return message;
            }
        }
        return null;
    }

    private File getNextFile(String dir) throws UMOException
    {
        File[] files = new File[]{};
        File file = new File(dir);
        File result = null;
        try
        {
            if (file.exists())
            {
                if (file.isFile())
                {
                    result = file;
                } else if (file.isDirectory())
                {
                    //todo what if we want to set a filter here?
                    files = file.listFiles();
                    if (files.length > 0)
                    {
                        result = files[0];
                    }
                }
            }
            return result;
        } catch (Exception e)
        {
            throw new MuleException(new Message("file", 1), e);
        }
    }

    /* (non-Javadoc)
     * @see org.mule.umo.provider.UMOConnectorSession#send(org.mule.umo.UMOEvent)
     */
    public UMOMessage doSend(UMOEvent event) throws Exception
    {
        doDispatch(event);
        return event.getMessage();
    }


    /* (non-Javadoc)
     * @see org.mule.umo.provider.UMOConnectorSession#getConnector()
     */
    public UMOConnector getConnector()
    {
        return connector;
    }

    private String generateFilename(UMOEvent event, String pattern)
    {
        if (pattern == null) {
            pattern = connector.getOutputPattern();
        }
        return connector.getFilenameParser().getFilename(event, pattern);
    }

    public void doDispose()
    {
    }

}
