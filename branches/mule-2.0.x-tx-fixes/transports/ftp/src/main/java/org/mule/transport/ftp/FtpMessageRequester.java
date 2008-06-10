/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.ftp;

import org.mule.DefaultMuleMessage;
import org.mule.api.MuleMessage;
import org.mule.api.endpoint.EndpointURI;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.transport.AbstractMessageRequester;
import org.mule.transport.file.FileConnector;

import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class FtpMessageRequester extends AbstractMessageRequester
{
    protected final FtpConnector connector;

    public FtpMessageRequester(InboundEndpoint endpoint)
    {
        super(endpoint);
        this.connector = (FtpConnector) endpoint.getConnector();
    }

    protected void doDispose()
    {
        // no op
    }

    protected void doConnect() throws Exception
    {
        // what was this for?!
        //connector.releaseFtp(endpoint.getEndpointURI());
    }

    protected void doDisconnect() throws Exception
    {
        try
        {
            EndpointURI uri = endpoint.getEndpointURI();
            FTPClient client = connector.getFtp(uri);
            connector.destroyFtp(uri, client);
        }
        catch (Exception e)
        {
            // pool may be closed
        }
    }

    /**
     * Make a specific request to the underlying transport
     *
     * @param timeout the maximum time the operation should block before returning.
     *            The call should return immediately if there is data available. If
     *            no data becomes available before the timeout elapses, null will be
     *            returned
     * @return the result of the request wrapped in a MuleMessage object. Null will be
     *         returned if no data was avaialable
     * @throws Exception if the call to the underlying protocal cuases an exception
     */
    protected MuleMessage doRequest(long timeout) throws Exception
    {
        FTPClient client = null;
        try
        {
            client = connector.createFtpClient(endpoint);

            FilenameFilter filenameFilter = null;
            if (endpoint.getFilter() instanceof FilenameFilter)
            {
                filenameFilter = (FilenameFilter) endpoint.getFilter();
            }

            FTPFile[] files = client.listFiles();
            if (!FTPReply.isPositiveCompletion(client.getReplyCode()))
            {
                throw new IOException("Ftp error: " + client.getReplyCode());
            }
            if (files == null || files.length == 0)
            {
                return null;
            }
            List fileList = new ArrayList();
            for (int i = 0; i < files.length; i++)
            {
                if (files[i].isFile())
                {
                    if (filenameFilter == null || filenameFilter.accept(null, files[i].getName()))
                    {
                        fileList.add(files[i]);
                        // only read the first one
                        break;
                    }
                }
            }
            if (fileList.size() == 0)
            {
                return null;
            }

            FTPFile file = (FTPFile) fileList.get(0);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            if (!client.retrieveFile(file.getName(), baos))
            {
                throw new IOException("Ftp error: " + client.getReplyCode());
            }

            MuleMessage reply = new DefaultMuleMessage(connector.getMessageAdapter(baos.toByteArray()));
            reply.setProperty(FileConnector.PROPERTY_ORIGINAL_FILENAME, file.getName());
            reply.setProperty(FileConnector.PROPERTY_FILE_SIZE, new Long(file.getSize()));
            return reply;
        }
        finally
        {
            logger.debug("leaving doRequest()");
            connector.releaseFtp(endpoint.getEndpointURI(), client);
        }
    }

}