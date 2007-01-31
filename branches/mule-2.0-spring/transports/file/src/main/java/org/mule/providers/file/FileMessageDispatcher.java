/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.file;

import org.mule.MuleException;
import org.mule.MuleManager;
import org.mule.config.i18n.Message;
import org.mule.impl.MuleMessage;
import org.mule.providers.AbstractMessageDispatcher;
import org.mule.providers.file.filters.FilenameWildcardFilter;
import org.mule.umo.UMOEvent;
import org.mule.umo.UMOException;
import org.mule.umo.UMOMessage;
import org.mule.umo.endpoint.UMOImmutableEndpoint;
import org.mule.util.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.net.URLDecoder;

/**
 * <code>FileMessageDispatcher</code> is used to read/write files to the filesystem
 */
public class FileMessageDispatcher extends AbstractMessageDispatcher
{
    private final FileConnector connector;

    public FileMessageDispatcher(UMOImmutableEndpoint endpoint)
    {
        super(endpoint);
        this.connector = (FileConnector)endpoint.getConnector();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.umo.provider.UMOConnectorSession#dispatch(org.mule.umo.UMOEvent)
     */
    protected void doDispatch(UMOEvent event) throws Exception
    {
        Object data = event.getTransformedMessage();
        // Wrap the transformed message before passing it to the filename parser
        UMOMessage message = new MuleMessage(data, event.getMessage());

        byte[] buf;
        if (data instanceof byte[])
        {
            buf = (byte[])data;
        }
        else
        {
            buf = data.toString().getBytes(event.getEncoding());
        }

        FileOutputStream fos = (FileOutputStream)connector.getOutputStream(event.getEndpoint(), message);
        if (event.getMessage().getStringProperty(FileConnector.PROPERTY_FILENAME, null) == null)
        {
            event.getMessage().setStringProperty(FileConnector.PROPERTY_FILENAME,
                message.getStringProperty(FileConnector.PROPERTY_FILENAME, ""));
        }
        try
        {
            fos.write(buf);
        }
        finally
        {
            fos.close();
        }
    }
    
    /**
     * There is no associated session for a file connector
     * 
     * @throws UMOException
     */
    public Object getDelegateSession() throws UMOException
    {
        return null;
    }

    /**
     * Will attempt to do a receive from a directory, if the endpointUri resolves to
     * a file name the file will be returned, otherwise the first file in the
     * directory according to the filename filter configured on the connector.
     * @param timeout this is ignored when doing a receive on this dispatcher
     * @return a message containing file contents or null if there was notthing to
     *         receive
     * @throws Exception
     */

    protected UMOMessage doReceive(long timeout) throws Exception
    {
        File file = FileUtils.newFile(endpoint.getEndpointURI().getAddress());
        File result = null;
        FilenameFilter filenameFilter = null;
        String filter = (String)endpoint.getProperty("filter");
        if (filter != null)
        {
            filter = URLDecoder.decode(filter, MuleManager.getConfiguration().getDefaultEncoding());
            filenameFilter = new FilenameWildcardFilter(filter);
        }
        if (file.exists())
        {
            if (file.isFile())
            {
                result = file;
            }
            else if (file.isDirectory())
            {
                result = getNextFile(endpoint.getEndpointURI().getAddress(), filenameFilter);
            }
            if (result != null)
            {
                boolean checkFileAge = connector.getCheckFileAge();
                if (checkFileAge)
                {
                    long fileAge = connector.getFileAge();
                    long lastMod = result.lastModified();
                    long now = (new java.util.Date()).getTime();
                    if ((now - lastMod) < fileAge)
                    {
                        return null;
                    }
                }

                MuleMessage message = new MuleMessage(connector.getMessageAdapter(result));
                if (connector.getMoveToDirectory() != null)
                {
                    {
                        File destinationFile = new File(connector.getMoveToDirectory(), result
                            .getName());
                        if (!result.renameTo(destinationFile))
                        {
                            logger.error("Failed to move file: " + result.getAbsolutePath()
                                         + " to " + destinationFile.getAbsolutePath());
                        }
                    }
                }
                result.delete();
                return message;
            }
        }
        return null;
    }

    private File getNextFile(String dir, FilenameFilter filter) throws UMOException
    {
        File[] files;
        File file = FileUtils.newFile(dir);
        File result = null;
        try
        {
            if (file.exists())
            {
                if (file.isFile())
                {
                    result = file;
                }
                else if (file.isDirectory())
                {
                    if (filter != null)
                    {
                        files = file.listFiles(filter);
                    }
                    else
                    {
                        files = file.listFiles();
                    }
                    if (files.length > 0)
                    {
                        result = files[0];
                    }
                }
            }
            return result;
        }
        catch (Exception e)
        {
            throw new MuleException(new Message("file", 1), e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.umo.provider.UMOConnectorSession#send(org.mule.umo.UMOEvent)
     */
    protected UMOMessage doSend(UMOEvent event) throws Exception
    {
        doDispatch(event);
        return event.getMessage();
    }

    protected void doDispose()
    {
        // no op
    }

    protected void doConnect() throws Exception
    {
        // no op
    }

    protected void doDisconnect() throws Exception
    {
        // no op
    }

}
