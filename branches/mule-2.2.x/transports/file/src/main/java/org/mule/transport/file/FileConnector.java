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

import org.mule.api.MessagingException;
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.api.config.MuleProperties;
import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.service.Service;
import org.mule.api.transport.DispatchException;
import org.mule.api.transport.MessageAdapter;
import org.mule.api.transport.MessageReceiver;
import org.mule.config.i18n.CoreMessages;
import org.mule.transformer.simple.ByteArrayToSerializable;
import org.mule.transformer.simple.SerializableToByteArray;
import org.mule.transport.AbstractConnector;
import org.mule.transport.file.filters.FilenameWildcardFilter;
import org.mule.util.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <code>FileConnector</code> is used for setting up listeners on a directory and
 * for writing files to a directory. The connecotry provides support for defining
 * file output patterns and filters for receiving files.
 */

public class FileConnector extends AbstractConnector
{

    private static Log logger = LogFactory.getLog(FileConnector.class);

    public static final String FILE = "file";
    private static final String DEFAULT_WORK_FILENAME_PATTERN = "#[UUID].#[SYSTIME].#[ORIGINALNAME]";

    // These are properties that can be overridden on the Receiver by the endpoint declaration
    // inbound only
    public static final String PROPERTY_POLLING_FREQUENCY = "pollingFrequency";
    public static final String PROPERTY_FILE_AGE = "fileAge";
    public static final String PROPERTY_MOVE_TO_PATTERN = "moveToPattern";
    public static final String PROPERTY_MOVE_TO_DIRECTORY = "moveToDirectory";
    public static final String PROPERTY_READ_FROM_DIRECTORY = "readFromDirectoryName";
    // outbound only
    public static final String PROPERTY_OUTPUT_PATTERN = "outputPattern";
    // apparently unused (once strange override code deleted)
//    public static final String PROPERTY_DELETE_ON_READ = "autoDelete";
//    public static final String PROPERTY_SERVICE_OVERRIDE = "serviceOverrides";

    // message properties
    public static final String PROPERTY_FILENAME = "filename";
    public static final String PROPERTY_ORIGINAL_FILENAME = "originalFilename";
    public static final String PROPERTY_DIRECTORY = "directory";
    public static final String PROPERTY_WRITE_TO_DIRECTORY = "writeToDirectoryName";
    public static final String PROPERTY_FILE_SIZE = "fileSize";

    public static final long DEFAULT_POLLING_FREQUENCY = 1000;


    /**
     * Time in milliseconds to poll. On each poll the poll() method is called
     */
    private long pollingFrequency = 0;

    private String moveToPattern = null;

    private String writeToDirectoryName = null;

    private String moveToDirectoryName = null;
    
    private String workDirectoryName = null;

    private String workFileNamePattern = DEFAULT_WORK_FILENAME_PATTERN;

    private String readFromDirectoryName = null;

    private String outputPattern = null;

    private boolean outputAppend = false;

    private boolean autoDelete = true;

    private boolean checkFileAge = false;

    private long fileAge = 0;

    private FileOutputStream outputStream = null;

    private boolean serialiseObjects = false;

    private boolean streaming = true;

    public FilenameParser filenameParser;

    public FileConnector()
    {
        super();
        filenameParser = new SimpleFilenameParser();
    }

    @Override
    protected void configureDispatcherPool()
    {
        if (isOutputAppend())
        {
            setMaxDispatchersActive(getDispatcherThreadingProfile().getMaxThreadsActive());
        }
        else
        {
            super.configureDispatcherPool();
        }
    }

    @Override
    public void setMaxDispatchersActive(int value)
    {
        if (isOutputAppend() && value != 1)
        {
            logger.warn("MULE-1773: cannot configure maxDispatchersActive when using outputAppend.  New value not set");
        }
        else
        {
            super.setMaxDispatchersActive(value);
        }
    }

    @Override
    protected Object getReceiverKey(Service service, InboundEndpoint endpoint)
    {
        if (endpoint.getFilter() != null && endpoint.getFilter() instanceof FilenameWildcardFilter)
        {
            return endpoint.getEndpointURI().getAddress() + "/"
                    + ((FilenameWildcardFilter) endpoint.getFilter()).getPattern();
        }
        return endpoint.getEndpointURI().getAddress();
    }

    /**
     * Registers a listener for a particular directory The following properties can
     * be overriden in the endpoint declaration
     * <ul>
     * <li>moveToDirectory</li>
     * <li>filterPatterns</li>
     * <li>filterClass</li>
     * <li>pollingFrequency</li>
     * </ul>
     */
    @Override
    public MessageReceiver createReceiver(Service service, InboundEndpoint endpoint) throws Exception
    {
        String readDir = endpoint.getEndpointURI().getAddress();
        if (null != getReadFromDirectory())
        {
            readDir = getReadFromDirectory();
        }

        long polling = this.pollingFrequency;

        String moveTo = moveToDirectoryName;
        String moveToPattern = getMoveToPattern();

        Map props = endpoint.getProperties();
        if (props != null)
        {
            // Override properties on the endpoint for the specific endpoint
            String read = (String) props.get(PROPERTY_READ_FROM_DIRECTORY);
            if (read != null)
            {
                readDir = read;
            }
            String move = (String) props.get(PROPERTY_MOVE_TO_DIRECTORY);
            if (move != null)
            {
                moveTo = move;
            }
            String tempMoveToPattern = (String) props.get(PROPERTY_MOVE_TO_PATTERN);
            if (tempMoveToPattern != null)
            {
                if (logger.isDebugEnabled())
                {
                    logger.debug("set moveTo Pattern to: " + tempMoveToPattern);
                }
                moveToPattern = tempMoveToPattern;
            }

            String tempPolling = (String) props.get(PROPERTY_POLLING_FREQUENCY);
            if (tempPolling != null)
            {
                polling = Long.parseLong(tempPolling);
            }

            if (polling <= 0)
            {
                polling = DEFAULT_POLLING_FREQUENCY;
            }

            if (logger.isDebugEnabled())
            {
                logger.debug("set polling frequency to: " + polling);
            }
            String tempFileAge = (String) props.get(PROPERTY_FILE_AGE);
            if (tempFileAge != null)
            {
                try
                {
                    setFileAge(Long.parseLong(tempFileAge));
                }
                catch (Exception ex1)
                {
                    logger.error("Failed to set fileAge", ex1);
                }
            }

            // this is surreal! what on earth was it useful for?
            // Map srvOverride = (Map) props.get(PROPERTY_SERVICE_OVERRIDE);
            // if (srvOverride != null)
            // {
            // if (serviceOverrides == null)
            // {
            // serviceOverrides = new Properties();
            // }
            // serviceOverrides.setProperty(MuleProperties.CONNECTOR_INBOUND_TRANSFORMER,
            // NoActionTransformer.class.getName());
            // serviceOverrides.setProperty(MuleProperties.CONNECTOR_OUTBOUND_TRANSFORMER,
            // NoActionTransformer.class.getName());
            // }

        }

        try
        {
            return serviceDescriptor.createMessageReceiver(this, service, endpoint, new Object[]{readDir,
                    moveTo, moveToPattern, new Long(polling)});

        }
        catch (Exception e)
        {
            throw new InitialisationException(
                    CoreMessages.failedToCreateObjectWith("Message Receiver",
                            serviceDescriptor), e, this);
        }
    }

    public String getProtocol()
    {
        return FILE;
    }

    public FilenameParser getFilenameParser()
    {
        return filenameParser;
    }

    public void setFilenameParser(FilenameParser filenameParser)
    {
        this.filenameParser = filenameParser;
    }

    protected void doDispose()
    {
        try
        {
            doStop();
        }
        catch (MuleException e)
        {
            logger.error(e.getMessage(), e);
        }
    }

    protected void doInitialise() throws InitialisationException
    {
        // MULE-1773: limit the number of dispatchers per endpoint to 1 until
        // there is a proper (Distributed)LockManager in place (MULE-2402).
        // We also override the setter to prevent "wrong" configuration for now.
        if (isOutputAppend())
        {
            super.setMaxDispatchersActive(1);
        }
    }

    protected void doConnect() throws Exception
    {
        // template method, nothing to do
    }

    protected void doDisconnect() throws Exception
    {
        // template method, nothing to do
    }

    protected void doStart() throws MuleException
    {
        // template method, nothing to do
    }

    protected void doStop() throws MuleException
    {
        if (outputStream != null)
        {
            try
            {
                outputStream.close();
            }
            catch (IOException e)
            {
                logger.warn("Failed to close file output stream on stop: " + e);
            }
        }
    }

    /**
     * @return Returns the moveToDirectoryName.
     */
    public String getMoveToDirectory()
    {
        return moveToDirectoryName;
    }

    /**
     * @param dir The moveToDirectoryName to set.
     */
    public void setMoveToDirectory(String dir)
    {
        this.moveToDirectoryName = dir;
    }

    public void setWorkDirectory(String workDirectoryName) throws IOException 
    {
        this.workDirectoryName = workDirectoryName;
        if (workDirectoryName != null)
        {
            File workDirectory = FileUtils.openDirectory(workDirectoryName);
            if (!workDirectory.canWrite())
            {
                throw new IOException(
                        "Error on initialization, Work Directory '" + workDirectory +"' is not writeable");
            }
        }
    }
    
    public String getWorkDirectory()
    {
        return workDirectoryName;
    }

    public void setWorkFileNamePattern(String workFileNamePattern)
    {
        this.workFileNamePattern = workFileNamePattern;
    }

    public String getWorkFileNamePattern()
    {
        return workFileNamePattern;
    }

    public boolean isOutputAppend()
    {
        return outputAppend;
    }

    public void setOutputAppend(boolean outputAppend)
    {
        this.outputAppend = outputAppend;
    }

    public String getOutputPattern()
    {
        return outputPattern;
    }

    public void setOutputPattern(String outputPattern)
    {
        this.outputPattern = outputPattern;
    }

    public FileOutputStream getOutputStream()
    {
        return outputStream;
    }

    public void setOutputStream(FileOutputStream outputStream)
    {
        this.outputStream = outputStream;
    }

    public long getPollingFrequency()
    {
        return pollingFrequency;
    }

    public void setPollingFrequency(long pollingFrequency)
    {
        this.pollingFrequency = pollingFrequency;
    }

    public long getFileAge()
    {
        return fileAge;
    }

    public boolean getCheckFileAge()
    {
        return checkFileAge;
    }

    public void setFileAge(long fileAge)
    {
        this.fileAge = fileAge;
        this.checkFileAge = true;
    }

    public String getWriteToDirectory()
    {
        return writeToDirectoryName;
    }

    public void setWriteToDirectory(String dir) throws IOException
    {
        this.writeToDirectoryName = dir;
        if (writeToDirectoryName != null)
        {
            File writeToDirectory = FileUtils.openDirectory(writeToDirectoryName);
            if (!writeToDirectory.canWrite())
            {
                throw new IOException(
                        "Error on initialization, " + writeToDirectory 
                        + " does not exist or is not writeable");
            }
        }
    }

    public String getReadFromDirectory()
    {
        return readFromDirectoryName;
    }

    public void setReadFromDirectory(String dir) throws IOException
    {
        this.readFromDirectoryName = dir;
        if (readFromDirectoryName != null)
        {
            // check if the directory exists/can be read
            FileUtils.openDirectory((readFromDirectoryName));
        }
    }

    public boolean isSerialiseObjects()
    {
        return serialiseObjects;
    }

    public void setSerialiseObjects(boolean serialiseObjects)
    {
        // set serialisable transformers on the connector if this is set
        if (serialiseObjects)
        {
            if (serviceOverrides == null)
            {
                serviceOverrides = new Properties();
            }
            serviceOverrides.setProperty(MuleProperties.CONNECTOR_INBOUND_TRANSFORMER,
                    ByteArrayToSerializable.class.getName());
            serviceOverrides.setProperty(MuleProperties.CONNECTOR_OUTBOUND_TRANSFORMER,
                    SerializableToByteArray.class.getName());
        }

        this.serialiseObjects = serialiseObjects;
    }

    public boolean isAutoDelete()
    {
        return autoDelete;
    }

    public void setAutoDelete(boolean autoDelete)
    {
        this.autoDelete = autoDelete;
    }

    public String getMoveToPattern()
    {
        return moveToPattern;
    }

    public void setMoveToPattern(String moveToPattern)
    {
        this.moveToPattern = moveToPattern;
    }

    /**
     * Well get the output stream (if any) for this type of transport. Typically this
     * will be called only when Streaming is being used on an outbound endpoint
     *
     * @param endpoint the endpoint that releates to this Dispatcher
     * @param message  the current message being processed
     * @return the output stream to use for this request or null if the transport
     *         does not support streaming
     * @throws org.mule.api.MuleException
     */
    public OutputStream getOutputStream(ImmutableEndpoint endpoint, MuleMessage message)
            throws MuleException
    {
        String address = endpoint.getEndpointURI().getAddress();
        String writeToDirectory = message.getStringProperty(FileConnector.PROPERTY_WRITE_TO_DIRECTORY, null);
        if (writeToDirectory == null)
        {
            writeToDirectory = getWriteToDirectory();
        }
        if (writeToDirectory != null)
        {
            address = getFilenameParser().getFilename(message, writeToDirectory);
        }

        String filename;
        String outPattern = (String) endpoint.getProperty(FileConnector.PROPERTY_OUTPUT_PATTERN);
        if (outPattern == null)
        {
            outPattern = message.getStringProperty(FileConnector.PROPERTY_OUTPUT_PATTERN, null);
        }
        if (outPattern == null)
        {
            outPattern = getOutputPattern();
        }
        try
        {
            if (outPattern != null)
            {
                filename = generateFilename(message, outPattern);
            }
            else
            {
                filename = message.getStringProperty(FileConnector.PROPERTY_FILENAME, null);
                if (filename == null)
                {
                    filename = generateFilename(message, null);
                }
            }

            if (filename == null)
            {
                throw new IOException("Filename is null");
            }
            File file = FileUtils.createFile(address + "/" + filename);
            if (logger.isInfoEnabled())
            {
                logger.info("Writing file to: " + file.getAbsolutePath());
            }

            return new FileOutputStream(file, isOutputAppend());
        }
        catch (IOException e)
        {
            throw new DispatchException(CoreMessages.streamingFailedNoStream(), message,
                    endpoint, e);
        }
    }

    private String generateFilename(MuleMessage message, String pattern)
    {
        if (pattern == null)
        {
            pattern = getOutputPattern();
        }
        return getFilenameParser().getFilename(message, pattern);
    }

    public boolean isStreaming()
    {
        return streaming;
    }

    public void setStreaming(boolean streaming)
    {
        this.streaming = streaming;
    }

    public MessageAdapter getMessageAdapter(Object message) throws MessagingException
    {
        if (isStreaming())
        {
            // TODO Shouldn't we have a way to specify MessageAdaptor for streaming
            // in service descriptor
            // See MULE-3209, MULE-3199
            return new FileMessageAdapter(message);
        }
        else
        {
            return super.getMessageAdapter(message);
        }
    }

}
