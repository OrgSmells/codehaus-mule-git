/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.file;

import org.mule.DefaultMuleMessage;
import org.mule.api.DefaultMuleException;
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.api.lifecycle.CreateException;
import org.mule.api.routing.RoutingException;
import org.mule.api.service.Service;
import org.mule.api.transport.Connector;
import org.mule.transport.AbstractPollingMessageReceiver;
import org.mule.transport.ConnectException;
import org.mule.transport.file.i18n.FileMessages;
import org.mule.util.FileUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Comparator;

import edu.emory.mathcs.backport.java.util.Arrays;

import org.apache.commons.collections.comparators.ReverseComparator;

/**
 * <code>FileMessageReceiver</code> is a polling listener that reads files from a
 * directory.
 */

public class FileMessageReceiver extends AbstractPollingMessageReceiver
{
    public static final String COMPARATOR_CLASS_NAME_PROPERTY = "comparator";
    public static final String COMPARATOR_REVERSE_ORDER_PROPERTY = "reverseOrder";

    private static final File[] NO_FILES = new File[0];

    private String readDir = null;
    private String moveDir = null;
    private String workDir = null;
    private File readDirectory = null;
    private File moveDirectory = null;
    private String moveToPattern = null;
    private String workFileNamePattern = null;
    private FilenameFilter filenameFilter = null;
    private FileFilter fileFilter = null;

    public FileMessageReceiver(Connector connector,
                               Service service,
                               InboundEndpoint endpoint,
                               String readDir,
                               String moveDir,
                               String moveToPattern,
                               long frequency) throws CreateException
    {
        super(connector, service, endpoint);
        this.setFrequency(frequency);

        this.readDir = readDir;
        this.moveDir = moveDir;
        this.moveToPattern = moveToPattern;
        this.workDir = ((FileConnector) connector).getWorkDirectory();
        this.workFileNamePattern = ((FileConnector) connector).getWorkFileNamePattern();

        if (endpoint.getFilter() instanceof FilenameFilter)
        {
            filenameFilter = (FilenameFilter) endpoint.getFilter();
        }
        else if (endpoint.getFilter() instanceof FileFilter)
        {
            fileFilter = (FileFilter) endpoint.getFilter();
        }
        else if (endpoint.getFilter() != null)
        {
            throw new CreateException(FileMessages.invalidFileFilter(endpoint.getEndpointURI()), this);
        }
    }

    @Override
    protected void doConnect() throws Exception
    {
        if (readDir != null)
        {
            readDirectory = FileUtils.openDirectory(readDir);
            if (!(readDirectory.canRead()))
            {
                throw new ConnectException(FileMessages.fileDoesNotExist(readDirectory.getAbsolutePath()), this);
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
                throw new ConnectException(FileMessages.moveToDirectoryNotWritable(), this);
            }
        }
    }

    @Override
    protected void doDisconnect() throws Exception
    {
        // template method
    }

    @Override
    protected void doDispose()
    {
        // nothing to do
    }

    @Override
    public void poll()
    {
        try
        {
            File[] files = this.listFiles();
            if (logger.isDebugEnabled())
            {
                logger.debug("Files: " + Arrays.toString(files));
            }
            Comparator comparator = getComparator();
            if (comparator != null)
            {
                Arrays.sort(files, comparator);
            }
            for (File file : files)
            {
                // don't process directories
                if (file.isFile())
                {
                    processFile(file);
                }
            }
        }
        catch (Exception e)
        {
            this.handleException(e);
        }
    }

    public synchronized void processFile(File sourceFile) throws MuleException
    {
        FileConnector fileConnector = (FileConnector) connector;
        
        //TODO RM*: This can be put in a Filter. Also we can add an AndFileFilter/OrFileFilter to allow users to
        //combine file filters (since we can only pass a single filter to File.listFiles, we would need to wrap
        //the current And/Or filters to extend {@link FilenameFilter}
        boolean checkFileAge = fileConnector.getCheckFileAge();
        if (checkFileAge)
        {
            long fileAge = ((FileConnector) connector).getFileAge();
            long lastMod = sourceFile.lastModified();
            long now = System.currentTimeMillis();
            long thisFileAge = now - lastMod;
            if (thisFileAge < fileAge)
            {
                if (logger.isDebugEnabled())
                {
                    logger.debug("The file has not aged enough yet, will return nothing for: " + sourceFile);
                }
                return;
            }
        }

        // don't process a file that is locked by another process (probably still being written)
        if (!attemptFileLock(sourceFile))
        {
            return;
        }
        else if(logger.isInfoEnabled())
        {
            logger.info("Lock obtained on file: " + sourceFile.getAbsolutePath());
        }

        String sourceFileOriginalName = sourceFile.getName();

        // Perform some quick checks to make sure file can be processed
        if (!(sourceFile.canRead() && sourceFile.exists() && sourceFile.isFile()))
        {
            throw new DefaultMuleException(FileMessages.fileDoesNotExist(sourceFileOriginalName));
        }

        // This isn't nice but is needed as MuleMessage is required to resolve
        // destination file name
        DefaultMuleMessage fileParserMsgAdaptor = new DefaultMuleMessage(null, connector.getMuleContext());
        fileParserMsgAdaptor.setProperty(FileConnector.PROPERTY_ORIGINAL_FILENAME, sourceFileOriginalName);
        
        File workFile = null;
        if (workDir != null && (moveDir == null || (moveDir != null && fileConnector.isStreaming())))
        {
            String workFileName = sourceFileOriginalName;
            
            workFileName = fileConnector.getFilenameParser().getFilename(fileParserMsgAdaptor,
                    workFileNamePattern);
            // don't use new File() directly, see MULE-1112
            workFile = FileUtils.newFile(workDir, workFileName);
            
            move(sourceFile, workFile);
            // Now the Work File is the Source file
            sourceFile = workFile;
        }
        
        // set up destination file
        File destinationFile = null;
        if (moveDir != null)
        {
            String destinationFileName = sourceFileOriginalName;
            if (moveToPattern != null)
            {
                destinationFileName = ((FileConnector) connector).getFilenameParser().getFilename(fileParserMsgAdaptor,
                    moveToPattern);
            }
            // don't use new File() directly, see MULE-1112
            destinationFile = FileUtils.newFile(moveDir, destinationFileName);
        }

        MuleMessage message = null;
        String encoding = endpoint.getEncoding();
        try
        {
            if (fileConnector.isStreaming())
            {
                ReceiverFileInputStream payload = new ReceiverFileInputStream(sourceFile, 
                    fileConnector.isAutoDelete(), destinationFile);
                message = createMuleMessage(payload, encoding);
            }
            else
            {
                message = createMuleMessage(sourceFile, encoding);
            }
        }
        catch (FileNotFoundException e)
        {
            // we can ignore since we did manage to acquire a lock, but just in case
            logger.error("File being read disappeared!", e);
            return;
        }

        message.setProperty(FileConnector.PROPERTY_ORIGINAL_FILENAME, sourceFileOriginalName);

        if (!fileConnector.isStreaming())
        {
            moveAndDelete(sourceFile, destinationFile, sourceFileOriginalName, message);
        }
        else
        {
            // If we are streaming no need to move/delete now, that will be done when
            // stream is closed
            message.setProperty(FileConnector.PROPERTY_FILENAME, sourceFile.getName());
            this.routeMessage(message, endpoint.isSynchronous());
        }
    }

    private void moveAndDelete(final File sourceFile, File destinationFile, 
        String sourceFileOriginalName, MuleMessage message)
    {

        boolean fileWasMoved = false;

        try
        {
            // If we are moving the file to a read directory, move it there now and
            // hand over a reference to the
            // File in its moved location
            if (destinationFile != null)
            {
                // move sourceFile to new destination
                try
                {
                    FileUtils.moveFile(sourceFile, destinationFile);
                }
                catch (IOException e)
                {
                    // move didn't work - bail out (will attempt rollback)
                    throw new DefaultMuleException(FileMessages.failedToMoveFile(
                        sourceFile.getAbsolutePath(), destinationFile.getAbsolutePath()));
                }

                // create new Message for destinationFile
                message = createMuleMessage(destinationFile, endpoint.getEncoding());
                message.setProperty(FileConnector.PROPERTY_FILENAME, destinationFile.getName());
                message.setProperty(FileConnector.PROPERTY_ORIGINAL_FILENAME, sourceFileOriginalName);
            }

            // finally deliver the file message
            this.routeMessage(message, endpoint.isSynchronous());

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
                        throw new DefaultMuleException(FileMessages.failedToDeleteFile(sourceFile));
                    }
                }
                else
                {
                    // nothing to do here since moveFile() should have deleted
                    // the source file for us
                }
            }
        }
        catch (Exception e)
        {
            boolean fileWasRolledBack = false;

            // only attempt rollback if file move was successful
            if (fileWasMoved)
            {
                try
                {
                    rollbackFileMove(destinationFile, sourceFile.getAbsolutePath());
                    fileWasRolledBack = true;
                }
                catch (IOException ioException)
                {
                    // eat it
                }
            }

            // wrap exception & handle it
            Exception ex = new RoutingException(FileMessages.exceptionWhileProcessing(sourceFile.getName(),
                (fileWasRolledBack ? "successful" : "unsuccessful")), new DefaultMuleMessage(message, connector.getMuleContext()), endpoint, e);
            this.handleException(ex);
        }
    }

    /**
     * Try to acquire a lock on a file and release it immediately. Usually used as a
     * quick check to see if another process is still holding onto the file, e.g. a
     * large file (more than 100MB) is still being written to.
     * 
     * @param sourceFile file to check
     * @return <code>true</code> if the file can be locked
     */
    protected boolean attemptFileLock(final File sourceFile)
    {
        // check if the file can be processed, be sure that it's not still being
        // written
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
        catch (FileNotFoundException fnfe)
        {
            logger.warn("Unable to open " + sourceFile.getAbsolutePath(), fnfe);
        }
        catch (IOException e)
        {
            // Unable to create a lock. This exception should only be thrown when
            // the file is already locked. No sense in repeating the message over
            // and over.
        }
        finally
        {
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
     * Get a list of files to be processed.
     * 
     * @return an array of files to be processed.
     * @throws org.mule.api.MuleException which will wrap any other exceptions or
     *             errors.
     */
    File[] listFiles() throws MuleException
    {
        try
        {
            File[] todoFiles;
            if (fileFilter != null)
            {
                todoFiles = readDirectory.listFiles(fileFilter);
            }
            else
            {
                todoFiles = readDirectory.listFiles(filenameFilter);
            }

            return (todoFiles == null ? NO_FILES : todoFiles);
        }
        catch (Exception e)
        {
            throw new DefaultMuleException(FileMessages.errorWhileListingFiles(), e);
        }
    }

    /**
     * Exception tolerant roll back method
     * 
     * @throws Throwable
     */
    protected void rollbackFileMove(File sourceFile, String destinationFilePath) throws IOException
    {
        try
        {
            FileUtils.moveFile(sourceFile, FileUtils.newFile(destinationFilePath));
        }
        catch (IOException t)
        {
            logger.debug("rollback of file move failed: " + t.getMessage());
            throw t;
        }
    }

    protected Comparator getComparator() throws Exception
    {

        Object o = getEndpoint().getProperty(COMPARATOR_CLASS_NAME_PROPERTY);
        Object reverseProperty = this.getEndpoint().getProperty(COMPARATOR_REVERSE_ORDER_PROPERTY);
        boolean reverse = false;
        if (o != null)
        {
            if (reverseProperty != null)
            {
                reverse = Boolean.valueOf((String) reverseProperty);
            }
            Class clazz = Class.forName(o.toString());
            o = clazz.newInstance();
            return reverse ? new ReverseComparator((Comparator) o) : (Comparator) o;
        }
        return null;
    }
    
    private void move(final File sourceFile,File destinationFile) throws DefaultMuleException
    {
        if (destinationFile != null)
        {
            // move sourceFile to new destination. Do not use FileUtils here as it ultimately
            // falls back to copying the file which will cause problems on large files again -
            // which is what we're trying to avoid in the first place
            boolean fileWasMoved = sourceFile.renameTo(destinationFile);

            // move didn't work - bail out
            if (!fileWasMoved)
            {
                throw new DefaultMuleException(FileMessages.failedToMoveFile(sourceFile.getAbsolutePath(),
                    destinationFile.getAbsolutePath()));
            }
        }
    }
    
}
