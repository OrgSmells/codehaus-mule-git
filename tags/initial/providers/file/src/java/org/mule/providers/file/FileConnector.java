/*

 * $Header$ $Revision$ $Date$

 * ------------------------------------------------------------------------------------------------------

 * 

 * Copyright (c) Cubis Limited. All rights reserved. http://www.cubis.co.uk

 * 

 * The software in this package is published under the terms of the BSD style

 * license a copy of which has been included with this distribution in the

 * LICENSE.txt file.

 *  

 */


package org.mule.providers.file;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.InitialisationException;
import org.mule.MuleException;
import org.mule.config.MuleProperties;
import org.mule.providers.AbstractServiceEnabledConnector;
import org.mule.transformers.simple.ByteArrayToSerialisable;
import org.mule.transformers.simple.SerialisableToByteArray;
import org.mule.umo.UMOComponent;
import org.mule.umo.UMOException;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.provider.UMOMessageReceiver;
import org.mule.util.Utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;


/**
 * <code>FileConnector</code> is used for setting up listeners on a directory and
 * for writing files to a directory.  The connecotry provides support for defining
 * file output patterns and filters for receiving files.
 *
 * @author <a href="mailto:ross.mason@cubis.co.uk">Ross Mason</a>
 * @version $Revision$
 */

public class FileConnector extends AbstractServiceEnabledConnector
{
    /**
     * logger used by this class
     */
    private static transient Log logger = LogFactory.getLog(FileConnector.class);

    //These are properties that can be overridden on the Receiver by the endpoint
    //declaration
    public static final String PROPERTY_POLLING_FREQUENCY = "pollingFrequency";
    public static final String PROPERTY_FILENAME = "filename";
    public static final String PROPERTY_ORIGINAL_FILENAME = "originalFilename";
    public static final String PROPERTY_OUTPUT_PATTERN = "outputPattern";
    public static final String PROPERTY_MOVE_TO_PATTERN = "moveToPattern";
    public static final String PROPERTY_MOVE_TO_DIRECTORY = "moveToDirectory";
    public static final String PROPERTY_DELETE_ON_READ = "autoDelete";
    public static final String PROPERTY_DIRECTORY = "directory";

    /**
     * Time in milliseconds to poll. On each poll the poll() method is called
     */
    private long pollingFrequency = 0;

    private File moveToDirectory = null;

    private String moveToPattern = null;

    private File writeToDirectory = null;

    private String writeToDirectoryName = null;

    private String moveToDirectoryName = null;

    private String outputPattern = null;

    private String outputFilename = null;

    private boolean outputAppend = false;

    private boolean autoDelete = true;

    private FileOutputStream outputStream = null;

    private boolean serialiseObjects = false;

    public FilenameParser filenameParser;

    /*
	 * (non-Javadoc)
	 *
	 * @see org.mule.providers.AbstractConnector#doInitialise()
	 */
    public  FileConnector()
    {
        filenameParser = new SimpleFilenameParser();
    }

    /**
     * Registers a listener for a particular directory
     * The following properties can be overriden in the endpoint declaration
     * <ul>
     * <li>moveToDirectory</li>
     * <li>filterPatterns</li>
     * <li>filterClass</li>
     * <li>pollingFrequency</li>
     * </ul>
     */
    public UMOMessageReceiver createReceiver(UMOComponent component, UMOEndpoint endpoint) throws Exception
    {
        String readDir = endpoint.getEndpointURI().getAddress();
        File dir = null;
        long polling = 0;

        if (readDir != null)
        {
            dir = Utility.openDirectory(readDir);
            if (!(dir.canRead()))
            {
                throw new MuleException("endpointUri [File System directory] does not " + "exist or cannot be read: " + dir.getAbsolutePath());
            }
            else
            {
                logger.debug("Listening on endpointUri: " + dir.getAbsolutePath());
            }
        }
        File moveTo = moveToDirectory;
        Map props = endpoint.getProperties();
        if (props != null)
        {
            //Override properties on the endpoint for the specific endpoint
            String move = (String) props.get(PROPERTY_MOVE_TO_DIRECTORY);
            if (move != null)
            {
                moveTo = Utility.openDirectory(move);
            }
            String tempPolling = (String) props.get(PROPERTY_POLLING_FREQUENCY);
            if (tempPolling != null)
            {
                polling = Long.parseLong(tempPolling);
            }
        }
        if (polling <= 0)
        {
            polling = 1000;
        }
        logger.debug("set polling frequency to: " + polling);
        try
        {

        return serviceDescriptor.createMessageReceiver(this, component, endpoint, new Object[]{dir, moveTo, moveToPattern, new Long(polling)});

        } catch (Exception e)
        {
            throw new InitialisationException("Failed to create receiver using spi interface: " +
                    serviceDescriptor.getMessageReceiver() + ". Error is: " + e.getMessage(), e);
        } 
    }

    /*
	 * (non-Javadoc)
	 *
	 * @see org.mule.providers.UMOConnector#stop()
	 */
    protected synchronized void stopConnector() throws UMOException
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

    /*
	 * (non-Javadoc)
	 *
	 * @see org.mule.providers.UMOConnector#getProtocol()
	 */
    public String getProtocol()
    {
        return "FILE";
    }

    public FilenameParser getFilenameParser()
    {
        return filenameParser;
    }

    public void setFilenameParser(FilenameParser filenameParser)
    {
        this.filenameParser = filenameParser;
    }

    /*
	 * (non-Javadoc)
	 *
	 * @see org.mule.providers.AbstractConnector#disposeConnector()
	 */
    protected void disposeConnector() throws UMOException
    {
        stopConnector();
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
    public void setMoveToDirectory(String dir) throws IOException
    {
        this.moveToDirectoryName = dir;
        if (moveToDirectoryName != null)
        {
            moveToDirectory = Utility.openDirectory((moveToDirectoryName));
            if (!(moveToDirectory.canRead()) || !moveToDirectory.canWrite())
            {
                throw new IOException("Error on initialization, Move To directory does not exist or is not read/write");
            }
        }
    }


    /**
     * @return Returns the outputAppend.
     */
    public boolean isOutputAppend()
    {
        return outputAppend;
    }


    /**
     * @param outputAppend The outputAppend to set.
     */
    public void setOutputAppend(boolean outputAppend)
    {
        this.outputAppend = outputAppend;
    }


    /**
     * @return Returns the outputFilename.
     */
    public String getOutputFilename()
    {
        return outputFilename;
    }


    /**
     * @param outputFilename The outputFilename to set.
     */
    public void setOutputFilename(String outputFilename)
    {
        this.outputFilename = outputFilename;
    }


    /**
     * @return Returns the outputPattern.
     */
    public String getOutputPattern()
    {
        return outputPattern;
    }


    /**
     * @param outputPattern The outputPattern to set.
     */
    public void setOutputPattern(String outputPattern)
    {
        this.outputPattern = outputPattern;
    }


    /**
     * @return Returns the outputStream.
     */
    public FileOutputStream getOutputStream()
    {
        return outputStream;
    }


    /**
     * @param outputStream The outputStream to set.
     */
    public void setOutputStream(FileOutputStream outputStream)
    {
        this.outputStream = outputStream;
    }


    /**
     * @return Returns the pollingFrequency.
     */
    public long getPollingFrequency()
    {
        return pollingFrequency;
    }


    /**
     * @param pollingFrequency The pollingFrequency to set.
     */
    public void setPollingFrequency(long pollingFrequency)
    {
        this.pollingFrequency = pollingFrequency;
    }

    /**
     * @return Returns the writeToDirectory.
     */
    public String getWriteToDirectory()
    {
        return writeToDirectoryName;
    }


    /**
     * @param dir The writeToDirectory to set.
     */
    public void setWriteToDirectory(String dir) throws IOException
    {
        this.writeToDirectoryName = dir;
        if (writeToDirectoryName != null)
        {
            writeToDirectory = Utility.openDirectory((writeToDirectoryName));
            if (!(writeToDirectory.canRead()) || !writeToDirectory.canWrite())
            {
                throw new IOException("Error on initialization, Write To directory does not exist or is not read/write");
            }
        }
    }

    public boolean isSerialiseObjects()
    {
        return serialiseObjects;
    }

    public void setSerialiseObjects(boolean serialiseObjects)
    {
        //set serialisable transformers on the connector if this is set
        if(serialiseObjects) {
            if(serviceOverrides==null) serviceOverrides = new Properties();
            serviceOverrides.setProperty(MuleProperties.CONNECTOR_INBOUND_TRANSFORMER, ByteArrayToSerialisable.class.getName());
            serviceOverrides.setProperty(MuleProperties.CONNECTOR_OUTBOUND_TRANSFORMER, SerialisableToByteArray.class.getName());
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
        if(!autoDelete) {
            if(serviceOverrides==null) serviceOverrides = new Properties();
            serviceOverrides.setProperty(MuleProperties.CONNECTOR_MESSAGE_ADAPTER, FileMessageAdapter.class.getName());
        }
    }

    public String getMoveToPattern()
    {
        return moveToPattern;
    }

    public void setMoveToPattern(String moveToPattern)
    {
        this.moveToPattern = moveToPattern;
    }
}