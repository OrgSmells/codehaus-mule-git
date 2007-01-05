/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.ftp;

import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.resource.spi.work.Work;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.mule.impl.MuleMessage;
import org.mule.providers.AbstractPollingMessageReceiver;
import org.mule.providers.file.FileConnector;
import org.mule.umo.UMOComponent;
import org.mule.umo.UMOMessage;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.endpoint.UMOEndpointURI;
import org.mule.umo.lifecycle.InitialisationException;
import org.mule.umo.provider.UMOConnector;

public class FtpMessageReceiver extends AbstractPollingMessageReceiver
{
    protected final FtpConnector connector;
    protected final FilenameFilter filenameFilter;

    // there's nothing like homegrown pseudo-2PC.. :/
    // shared state management like this should go into the connector and use
    // something like commons-tx
    protected final Set scheduledFiles = Collections.synchronizedSet(new HashSet());
    protected final Set currentFiles = Collections.synchronizedSet(new HashSet());

    public FtpMessageReceiver(UMOConnector connector,
                              UMOComponent component,
                              UMOEndpoint endpoint,
                              Long frequency) throws InitialisationException
    {
        super(connector, component, endpoint, frequency);
        this.connector = (FtpConnector)connector;

        if (endpoint.getFilter() instanceof FilenameFilter)
        {
            this.filenameFilter = (FilenameFilter)endpoint.getFilter();
        }
        else
        {
            this.filenameFilter = null;
        }
    }

    public void poll() throws Exception
    {
        FTPFile[] files = listFiles();

        synchronized (scheduledFiles)
        {
            for (int i = 0; i < files.length; i++)
            {
                final FTPFile file = files[i];
                final String fileName = file.getName();

                if (!scheduledFiles.contains(fileName) && !currentFiles.contains(fileName))
                {
                    scheduledFiles.add(fileName);
                    getWorkManager().scheduleWork(new FtpWork(fileName, file));
                }
            }
        }
    }

    protected FTPFile[] listFiles() throws Exception
    {
        final UMOEndpointURI uri = endpoint.getEndpointURI();
        FTPClient client = null;

        try
        {
            client = connector.getFtp(uri);
            connector.enterActiveOrPassiveMode(client, endpoint);
            connector.setupFileType(client, endpoint);

            if (!client.changeWorkingDirectory(uri.getPath()))
            {
                throw new IOException("Ftp error: " + client.getReplyCode());
            }

            FTPFile[] files = client.listFiles();

            if (!FTPReply.isPositiveCompletion(client.getReplyCode()))
            {
                throw new IOException("Ftp error: " + client.getReplyCode());
            }

            if (files == null || files.length == 0)
            {
                return files;
            }

            List v = new ArrayList();

            for (int i = 0; i < files.length; i++)
            {
                if (files[i].isFile())
                {
                    if (filenameFilter == null || filenameFilter.accept(null, files[i].getName()))
                    {
                        v.add(files[i]);
                    }
                }
            }

            return (FTPFile[])v.toArray(new FTPFile[v.size()]);
        }
        finally
        {
            connector.releaseFtp(uri, client);
        }
    }

    protected void processFile(FTPFile file) throws Exception
    {
        FTPClient client = null;
        UMOEndpointURI uri = endpoint.getEndpointURI();

        try
        {
            client = connector.getFtp(uri);
            connector.enterActiveOrPassiveMode(client, endpoint);
            connector.setupFileType(client, endpoint);

            if (!client.changeWorkingDirectory(endpoint.getEndpointURI().getPath()))
            {
                throw new IOException("Ftp error: " + client.getReplyCode());
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            if (!client.retrieveFile(file.getName(), baos))
            {
                throw new IOException("Ftp error: " + client.getReplyCode());
            }

            UMOMessage message = new MuleMessage(connector.getMessageAdapter(baos.toByteArray()));
            message.setProperty(FileConnector.PROPERTY_ORIGINAL_FILENAME, file.getName());
            routeMessage(message);

            if (!client.deleteFile(file.getName()))
            {
                throw new IOException("Ftp error: " + client.getReplyCode());
            }
        }
        finally
        {
            connector.releaseFtp(uri, client);
        }
    }

    protected void doConnect() throws Exception
    {
        FTPClient client = connector.getFtp(getEndpointURI());
        connector.releaseFtp(getEndpointURI(), client);
    }

    protected void doDisconnect() throws Exception
    {
        // no op
    }

    protected void doDispose()
    {
        // template method
    }

    private final class FtpWork implements Work
    {
        private final String _name;
        private final FTPFile _file;

        private FtpWork(String name, FTPFile file)
        {
            _name = name;
            _file = file;
        }

        public void run()
        {
            try
            {
                currentFiles.add(_name);
                processFile(_file);
            }
            catch (Exception e)
            {
                connector.handleException(e);
            }
            finally
            {
                currentFiles.remove(_name);
                scheduledFiles.remove(_name);
            }
        }

        public void release()
        {
            // no op
        }
    }

}
