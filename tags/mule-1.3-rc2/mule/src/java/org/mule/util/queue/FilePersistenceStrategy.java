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
package org.mule.util.queue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.doomdark.uuid.UUIDGenerator;
import org.mule.MuleManager;
import org.mule.config.MuleConfiguration;
import org.mule.util.file.DeleteException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:gnt@codehaus.org">Guillaume Nodet</a>
 * @version $Revision$
 */
public class FilePersistenceStrategy implements QueuePersistenceStrategy
{

    private static final Log logger = LogFactory.getLog(FilePersistenceStrategy.class);

    public static final String EXTENSION = ".msg";

    private File store;

    private UUIDGenerator gen = UUIDGenerator.getInstance();

    public FilePersistenceStrategy()
    {
    }

    protected String getId(Object obj)
    {
        String id = gen.generateRandomBasedUUID().toString();
        return id;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.transaction.xa.queue.QueuePersistenceStrategy#store(java.lang.Object)
     */
    public Object store(String queue, Object obj) throws IOException
    {
        String id = getId(obj);
        File file = new File(store, queue + File.separator + id + EXTENSION);
        file.getParentFile().mkdirs();
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
        oos.writeObject(obj);
        oos.close();
        return id;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.transaction.xa.queue.QueuePersistenceStrategy#remove(java.lang.Object)
     */
    public void remove(String queue, Object id) throws IOException
    {
        File file = new File(store, queue + File.separator + id + EXTENSION);
        if (file.exists()) {
            if (!file.delete()) {
                throw new DeleteException(file);
            }
        } else {
            throw new FileNotFoundException(file.toString());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.transaction.xa.queue.QueuePersistenceStrategy#load(java.lang.Object)
     */
    public Object load(String queue, Object id) throws IOException
    {
        File file = new File(store, queue + File.separator + id + EXTENSION);
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(file));
            Object obj = ois.readObject();
            return obj;
        } catch (ClassNotFoundException e) {
            throw (IOException) new IOException("Error loading persistent object").initCause(e);
        } finally {
            if (ois != null) {
                ois.close();
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.transaction.xa.queue.QueuePersistenceStrategy#restore()
     */
    public List restore() throws IOException
    {
        List msgs = new ArrayList();
        if(store==null) {
            logger.warn("No store has be set on the File Persistence Strategy. Not restoring at this time");
            return msgs;
        }
        try {
            restoreFiles(store, msgs);
            logger.debug("Restore retrieved " + msgs.size() + " objects");
            return msgs;
        } catch (ClassNotFoundException e) {
            throw (IOException) new IOException("Could not restore").initCause(e);
        }
    }

    protected void restoreFiles(File dir, List msgs) throws IOException, ClassNotFoundException
    {
        File[] files = dir.listFiles();
        if(files==null) {
            return;
        }

        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                restoreFiles(files[i], msgs);
            } else if (files[i].getName().endsWith(EXTENSION)) {
                String id = files[i].getCanonicalPath();
                id = id.substring(store.getCanonicalPath().length() + 1, id.length() - EXTENSION.length());
                String queue = id.substring(0, id.indexOf(File.separator));
                id = id.substring(queue.length() + 1);
                msgs.add(new HolderImpl(queue, id));
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.util.queue.QueuePersistenceStrategy#open()
     */
    public void open() throws IOException
    {
        String path = MuleManager.getConfiguration().getWorkingDirectory() + File.separator
                + MuleConfiguration.DEFAULT_QUEUE_STORE;
        store = new File(path).getCanonicalFile();
        store.mkdirs();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.transaction.xa.queue.QueuePersistenceStrategy#close()
     */
    public void close() throws IOException
    {
        // Nothing to do
    }

    protected static class HolderImpl implements Holder
    {
        private String queue;
        private Object id;

        public HolderImpl(String queue, Object id)
        {
            this.queue = queue;
            this.id = id;
        }

        public Object getId()
        {
            return id;
        }

        public String getQueue()
        {
            return queue;
        }
    }

    public boolean isTransient() {
        return false;
    }
}
