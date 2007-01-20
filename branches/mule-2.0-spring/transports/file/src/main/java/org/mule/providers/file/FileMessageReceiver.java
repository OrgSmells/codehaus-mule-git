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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

import org.apache.commons.io.IOUtils;
import org.mule.MuleException;
import org.mule.config.i18n.Message;
import org.mule.config.i18n.Messages;
import org.mule.impl.MuleMessage;
import org.mule.providers.ConnectException;
import org.mule.providers.AbstractPollingMessageReceiver;
import org.mule.umo.UMOComponent;
import org.mule.umo.UMOException;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.lifecycle.InitialisationException;
import org.mule.umo.provider.UMOConnector;
import org.mule.umo.provider.UMOMessageAdapter;
import org.mule.umo.routing.RoutingException;
import org.mule.util.FileUtils;

/**
 * <code>FileMessageReceiver</code> is a polling listener that reads files from a
 * directory.
 */

public class FileMessageReceiver extends AbstractPollingMessageReceiver
{
    private String readDir = null;
    private String moveDir = null;
    private File readDirectory = null;
    private File moveDirectory = null;
    private String moveToPattern = null;
    private FilenameFilter filenameFilter = null;

    public FileMessageReceiver(UMOConnector connector,
                               UMOComponent component,
                               UMOEndpoint endpoint,
                               String readDir,
                               String moveDir,
                               String moveToPattern,
                               long frequency) throws InitialisationException
    {
        super(connector, component, endpoint, frequency);

        this.readDir = readDir;
        this.moveDir = moveDir;
        this.moveToPattern = moveToPattern;

        if (endpoint.getFilter() instanceof FilenameFilter)
        {
            filenameFilter = (FilenameFilter)endpoint.getFilter();
        }
    }

    protected void doConnect() throws Exception
    {
        if (readDir != null)
        {
            readDirectory = FileUtils.openDirectory(readDir);
            if (!(readDirectory.canRead()))
            {
                throw new ConnectException(new Message(Messages.FILE_X_DOES_NOT_EXIST,
                    readDirectory.getAbsolutePath()), this);
            }
            else
            {
                logger.debug("Listening on endpointUri: " + readDirectory.getAbsolutePath());
            }
        }

        if (moveDir != null)
        {
            moveDirectory = FileUtils.openDirectory((moveDir));
            if (!(moveDirectory.canRead()) || !moveDirectory.canWrite())
            {
                throw new ConnectException(new Message("file", 5), this);
            }
        }
    }

    protected void doDisconnect() throws Exception
    {
        // template method
    }

    protected void doDispose()
    {
        // nothing to do               
    }

    public void poll()
    {
        try
        {
            File[] files = this.listFiles();
            for (int i = 0; i < files.length; i++)
            {
                this.processFile(files[i]);
            }
        }
        catch (Exception e)
        {
            this.handleException(e);
        }
    }

    public synchronized void processFile(final File sourceFile) throws UMOException
    {
        boolean checkFileAge = ((FileConnector)connector).getCheckFileAge();
        if (checkFileAge)
        {
            long fileAge = ((FileConnector)connector).getFileAge();
            long lastMod = sourceFile.lastModified();
            long now = (new java.util.Date()).getTime();
            if ((now - lastMod) < fileAge)
            {
                return;
            }
        }

        // don't process a file that is locked by another process (probably still being written)
        if (!attemptFileLock(sourceFile))
        {
            return;
        }

        File destinationFile = null;
        String sourceFileOriginalName = sourceFile.getName();
        UMOMessageAdapter msgAdapter = connector.getMessageAdapter(sourceFile);
        msgAdapter.setProperty(FileConnector.PROPERTY_ORIGINAL_FILENAME, sourceFileOriginalName);

        // set up destination file
        if (moveDir != null)
        {
            String destinationFileName = sourceFileOriginalName;

            if (moveToPattern != null)
            {
                destinationFileName = ((FileConnector) connector).getFilenameParser().getFilename(msgAdapter,
                    moveToPattern);
            }

            // don't use new File() directly, see MULE-1112
            destinationFile = FileUtils.newFile(moveDir, destinationFileName);
        }

        boolean fileWasMoved = false;

        try
        {
            // Perform some quick checks to make sure file can be processed
            if (!(sourceFile.canRead() && sourceFile.exists() && sourceFile.isFile()))
            {
                throw new MuleException(new Message(Messages.FILE_X_DOES_NOT_EXIST, sourceFileOriginalName));
            }

            if (destinationFile != null)
            {
                // move sourceFile to new destination
                fileWasMoved = this.moveFile(sourceFile, destinationFile);

                // move didn't work - bail out (will attempt rollback)
                if (!fileWasMoved)
                {
                    throw new MuleException(new Message("file", 4, sourceFile.getAbsolutePath(),
                        destinationFile.getAbsolutePath()));
                }

                // create new MessageAdapter for destinationFile
                msgAdapter = connector.getMessageAdapter(destinationFile);
                msgAdapter.setProperty(FileConnector.PROPERTY_FILENAME, destinationFile.getName());
                msgAdapter.setProperty(FileConnector.PROPERTY_ORIGINAL_FILENAME, sourceFileOriginalName);
            }

            // at this point msgAdapter either points to the old sourceFile
            // or the new destinationFile.
            if (((FileConnector) connector).isAutoDelete())
            {
                // no moveTo directory
                if (destinationFile == null)
                {
                    // delete source
                    if (!sourceFile.delete())
                    {
                        // TODO better message
                        throw new MuleException(new Message("file", 3, sourceFile.getAbsolutePath()));
                    }
                }
                else
                {
                    // nothing to do here since moveFile() should have deleted
                    // the source file for us
                }
            }

            // finally deliver the file message
            this.routeMessage(new MuleMessage(msgAdapter), endpoint.isSynchronous());
        }
        catch (Exception e)
        {
            boolean fileWasRolledBack = false;

            // only attempt rollback if file move was successful
            if (fileWasMoved)
            {
                fileWasRolledBack = this.rollbackFileMove(destinationFile, sourceFile.getAbsolutePath());
            }

            // wrap exception & handle it
            Exception ex = new RoutingException(new Message("file", 2, sourceFile.getName(),
                (fileWasRolledBack ? "successful" : "unsuccessful")), new MuleMessage(msgAdapter), endpoint,
                e);
            this.handleException(ex);
        }
    }

    /**
     * Try to acquire a lock on a file and release it immediately. Usually used as a quick check to
     * see if another process is still holding onto the file, e.g. a large file (more than 100MB) is
     * still being written to.
     * @param sourceFile file to check
     * @return <code>true</code> if the file can be locked
     */
    protected boolean attemptFileLock(final File sourceFile)
    {
        // check if the file can be processed, be sure that it's not still being written
        // if the file can't be locked don't process it yet, since creating
        // a new FileInputStream() will throw an exception
        FileLock lock = null;
        FileChannel channel = null;
        boolean fileCanBeLocked = false;
        try
        {
            channel = new RandomAccessFile(sourceFile, "rw").getChannel();

            // Try acquiring the lock without blocking. This method returns
            // null or throws an exception if the file is already locked.
            lock = channel.tryLock();
        }
        catch (IOException e)
        {
            // unable to create a lock
        }
        finally {
            if (lock != null)
            {
                // if lock is null the file is locked by another process
                fileCanBeLocked = true;
                try
                {
                    // Release the lock
                    lock.release();
                }
                catch (IOException e)
                {
                    // ignore
                }
            }

            if (channel != null)
            {
                try
                {
                    // Close the file
                    channel.close();
                }
                catch (IOException e)
                {
                    // ignore
                }
            }
        }

        return fileCanBeLocked;
    }

    /**
     * Try to move a file by renaming with backup attempt by copying/deleting via NIO
     */
    protected boolean moveFile(File sourceFile, File destinationFile)
    {
        // try fast file-system-level move/rename first
        boolean success = sourceFile.renameTo(destinationFile);

        if (!success)
        {
            // try again using NIO copy
            FileInputStream fis = null;
            FileOutputStream fos = null;
            try
            {
                fis = new FileInputStream(sourceFile);
                fos = new FileOutputStream(destinationFile);
                FileChannel srcChannel = fis.getChannel();
                FileChannel dstChannel = fos.getChannel();
                dstChannel.transferFrom(srcChannel, 0, srcChannel.size());
                srcChannel.close();
                dstChannel.close();
                success = sourceFile.delete();
            }
            catch (IOException ioex)
            {
                // grr!
                success = false;
            }
            finally
            {
                IOUtils.closeQuietly(fis);
                IOUtils.closeQuietly(fos);
            }
        }

        return success;
    }

    /**
     * Exception tolerant roll back method
     */
    protected boolean rollbackFileMove(File sourceFile, String destinationFilePath)
    {
        boolean result = false;
        try
        {
            result = this.moveFile(sourceFile, FileUtils.newFile(destinationFilePath));
        }
        catch (Throwable t)
        {
            logger.debug("rollback of file move failed: " + t.getMessage());
        }
        return result;
    }

    /**
     * Get a list of files to be processed.
     * 
     * @return an array of files to be processed.
     * @throws org.mule.MuleException which will wrap any other exceptions or errors.
     */
    File[] listFiles() throws MuleException
    {
        try
        {
            File[] TODOFiles = readDirectory.listFiles(filenameFilter);
            // logger.trace("Reading directory " + readDirectory.getAbsolutePath() +
            // " -> " + TODOFiles.length + " file(s)");
            return (TODOFiles == null ? new File[0] : TODOFiles);
        }
        catch (Exception e)
        {
            throw new MuleException(new Message("file", 1), e);
        }
    }

}
