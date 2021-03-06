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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.transaction.xa.XAResource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.util.queue.QueuePersistenceStrategy.Holder;
import org.mule.util.xa.AbstractTransactionContext;
import org.mule.util.xa.AbstractXAResourceManager;
import org.mule.util.xa.ResourceManagerException;
import org.mule.util.xa.ResourceManagerSystemException;

/**
 * @author <a href="mailto:gnt@codehaus.org">Guillaume Nodet</a>
 * @version $Revision$
 */
public class TransactionalQueueManager extends AbstractXAResourceManager implements QueueManager
{

    private static Log logger = LogFactory.getLog(TransactionalQueueManager.class);

    private Map queues = new HashMap();

    private QueuePersistenceStrategy memoryPersistenceStrategy = new MemoryPersistenceStrategy();
    private QueuePersistenceStrategy persistenceStrategy;

    private QueueConfiguration defaultQueueConfiguration = new QueueConfiguration(false);

    public synchronized QueueSession getQueueSession()
    {
        return new QueueSessionImpl();
    }

    public synchronized void setDefaultQueueConfiguration(QueueConfiguration config)
    {
        this.defaultQueueConfiguration = config;
    }

    public synchronized void setQueueConfiguration(String queueName, QueueConfiguration config)
    {
        getQueue(queueName).config = config;
    }

    protected synchronized QueueInfo getQueue(String name)
    {
        QueueInfo q = (QueueInfo) queues.get(name);
        if (q == null) {
            q = new QueueInfo();
            q.name = name;
            q.list = new LinkedList();
            q.config = defaultQueueConfiguration;
            queues.put(name, q);
        }
        return q;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.transaction.xa.AbstractResourceManager#getLogger()
     */
    protected Log getLogger()
    {
        return logger;
    }

    public void close()
    {
        try {
            stop(SHUTDOWN_MODE_NORMAL);
        } catch (ResourceManagerException e) {
            logger.error("Error disposing manager", e);
        }
    }

    protected void doStart() throws ResourceManagerSystemException
    {
        if (persistenceStrategy != null) {
            try {
                persistenceStrategy.open();
            } catch (IOException e) {
                throw new ResourceManagerSystemException(e);
            }
        }
    }

    protected boolean shutdown(int mode, long timeoutMSecs)
    {
        try {
            if (persistenceStrategy != null) {
                persistenceStrategy.close();
            }
        } catch (IOException e) {
            logger.error("Error closing persistent store", e);
        }
        return super.shutdown(mode, timeoutMSecs);
    }

    protected void recover() throws ResourceManagerSystemException
    {
        if (persistenceStrategy != null) {
            try {
                List msgs = persistenceStrategy.restore();
                for (Iterator it = msgs.iterator(); it.hasNext();) {
                    Holder h = (Holder) it.next();
                    getQueue(h.getQueue()).putNow(h.getId());
                }
            } catch (Exception e) {
                throw new ResourceManagerSystemException(e);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.transaction.xa.AbstractResourceManager#createTransactionContext()
     */
    protected AbstractTransactionContext createTransactionContext(Object session)
    {
        return new QueueTransactionContext();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.transaction.xa.AbstractResourceManager#doBegin(org.mule.transaction.xa.AbstractTransactionContext)
     */
    protected void doBegin(AbstractTransactionContext context)
    {
        // Nothing special to do
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.transaction.xa.AbstractResourceManager#doPrepare(org.mule.transaction.xa.AbstractTransactionContext)
     */
    protected int doPrepare(AbstractTransactionContext context)
    {
        return XAResource.XA_OK;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.transaction.xa.AbstractResourceManager#doCommit(org.mule.transaction.xa.AbstractTransactionContext)
     */
    protected void doCommit(AbstractTransactionContext context) throws ResourceManagerException
    {
        QueueTransactionContext ctx = (QueueTransactionContext) context;
        try {
            if (ctx.added != null) {
                for (Iterator it = ctx.added.entrySet().iterator(); it.hasNext();) {
                    Map.Entry entry = (Map.Entry) it.next();
                    QueueInfo queue = (QueueInfo) entry.getKey();
                    List queueAdded = (List) entry.getValue();
                    if (queueAdded != null && queueAdded.size() > 0) {
                        for (Iterator itAdded = queueAdded.iterator(); itAdded.hasNext();) {
                            Object object = itAdded.next();
                            Object id = doStore(queue, object);
                            queue.putNow(id);
                        }
                    }
                }
            }
            if (ctx.removed != null) {
                for (Iterator it = ctx.removed.entrySet().iterator(); it.hasNext();) {
                    Map.Entry entry = (Map.Entry) it.next();
                    QueueInfo queue = (QueueInfo) entry.getKey();
                    List queueRemoved = (List) entry.getValue();
                    if (queueRemoved != null && queueRemoved.size() > 0) {
                        for (Iterator itRemoved = queueRemoved.iterator(); itRemoved.hasNext();) {
                            Object id = itRemoved.next();
                            doRemove(queue, id);
                        }
                    }
                }
            }
        } catch (Exception e) {
            // throw new ResourceManagerException("Could not commit
            // transaction", e);
            // TODO: add an i18n Message
            throw new ResourceManagerException(e);
        } finally {
            ctx.added = null;
            ctx.removed = null;
        }
    }

    protected Object doStore(QueueInfo queue, Object object) throws IOException
    {
        QueuePersistenceStrategy ps = (queue.config.persistent) ? persistenceStrategy : memoryPersistenceStrategy;
        Object id = ps.store(queue.name, object);
        return id;
    }

    protected void doRemove(QueueInfo queue, Object id) throws IOException
    {
        QueuePersistenceStrategy ps = (queue.config.persistent) ? persistenceStrategy : memoryPersistenceStrategy;
        ps.remove(queue.name, id);
    }

    protected Object doLoad(QueueInfo queue, Object id) throws IOException
    {
        QueuePersistenceStrategy ps = (queue.config.persistent) ? persistenceStrategy : memoryPersistenceStrategy;
        Object obj = ps.load(queue.name, id);
        return obj;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.transaction.xa.AbstractResourceManager#doRollback(org.mule.transaction.xa.AbstractTransactionContext)
     */
    protected void doRollback(AbstractTransactionContext context) throws ResourceManagerException
    {
        QueueTransactionContext ctx = (QueueTransactionContext) context;
        if (ctx.removed != null) {
            for (Iterator it = ctx.removed.entrySet().iterator(); it.hasNext();) {
                Map.Entry entry = (Map.Entry) it.next();
                QueueInfo queue = (QueueInfo) entry.getKey();
                List queueRemoved = (List) entry.getValue();
                if (queueRemoved != null && queueRemoved.size() > 0) {
                    for (Iterator itRemoved = queueRemoved.iterator(); itRemoved.hasNext();) {
                        Object id = itRemoved.next();
                        queue.putNow(id);
                    }
                }
            }
        }
        ctx.added = null;
        ctx.removed = null;
    }

    public static class QueueInfo
    {
        protected LinkedList list;
        protected String name;
        protected QueueConfiguration config;

        public boolean equals(Object obj)
        {
            return (obj instanceof QueueInfo && name.equals(((QueueInfo) obj).name));
        }

        public int hashCode()
        {
            return name.hashCode();
        }

        public void putNow(Object o)
        {
            synchronized (list) {
                list.addLast(o);
                list.notifyAll();
            }
        }

        public boolean offer(Object o, int room, long timeout) throws InterruptedException
        {
            if (Thread.interrupted()) {
                throw new InterruptedException();
            }
            synchronized (list) {
                if (config.capacity > 0) {
                    if (config.capacity <= room) {
                        throw new IllegalStateException("Can not add more objects than the capacity in one time");
                    }
                    long l1 = timeout > 0L ? System.currentTimeMillis() : 0L;
                    long l2 = timeout;
                    while (list.size() >= config.capacity - room) {
                        if (l2 <= 0L) {
                            return false;
                        }
                        list.wait(l2);
                        l2 = timeout - (System.currentTimeMillis() - l1);
                    }
                }
                if (o != null) {
                    list.addLast(o);
                }
                list.notifyAll();
                return true;
            }
        }

        public Object poll(long timeout) throws InterruptedException
        {
            if (Thread.interrupted()) {
                throw new InterruptedException();
            }
            synchronized (list) {
                long l1 = timeout > 0L ? System.currentTimeMillis() : 0L;
                long l2 = timeout;
                while (list.isEmpty()) {
                    if (l2 <= 0L) {
                        return null;
                    }
                    list.wait(l2);
                    l2 = timeout - (System.currentTimeMillis() - l1);
                }
                Object o = list.removeFirst();
                list.notifyAll();
                return o;
            }
        }

        public Object peek() throws InterruptedException
        {
            if (Thread.interrupted()) {
                throw new InterruptedException();
            }
            synchronized (list) {
                if (list.isEmpty()) {
                    return null;
                } else {
                    return list.getFirst();
                }
            }
        }

    }

    protected class QueueTransactionContext extends AbstractTransactionContext
    {
        protected Map added;
        protected Map removed;

        public boolean offer(QueueInfo queue, Object item, long timeout) throws InterruptedException
        {
            readOnly = false;
            if (added == null) {
                added = new HashMap();
            }
            List queueAdded = (List) added.get(queue);
            if (queueAdded == null) {
                queueAdded = new ArrayList();
                added.put(queue, queueAdded);
            }
            // wait for enough room
            if (queue.offer(null, queueAdded.size(), Long.MAX_VALUE)) {
                queueAdded.add(item);
                return true;
            } else {
                return false;
            }
        }

        public Object poll(QueueInfo queue, long timeout) throws IOException, InterruptedException
        {
            readOnly = false;
            if (added != null) {
                List queueAdded = (List) added.get(queue);
                if (queueAdded != null) {
                    return queueAdded.remove(queueAdded.size() - 1);
                }
            }
            Object o = queue.poll(Long.MAX_VALUE);
            if (o != null) {
                if (removed == null) {
                    removed = new HashMap();
                }
                List queueRemoved = (List) removed.get(queue);
                if (queueRemoved == null) {
                    queueRemoved = new ArrayList();
                    removed.put(queue, queueRemoved);
                }
                queueRemoved.add(o);
                o = doLoad(queue, o);
            }
            return o;
        }

        public Object peek(QueueInfo queue) throws IOException, InterruptedException
        {
            readOnly = false;
            if (added != null) {
                List queueAdded = (List) added.get(queue);
                if (queueAdded != null) {
                    return queueAdded.get(queueAdded.size() - 1);
                }
            }
            Object o = queue.peek();
            if (o != null) {
                o = doLoad(queue, o);
            }
            return o;
        }

        public int size(QueueInfo queue)
        {
            int sz = queue.list.size();
            if (added != null) {
                List queueAdded = (List) added.get(queue);
                if (queueAdded != null) {
                    sz += queueAdded.size();
                }
            }
            return sz;
        }

    }

    protected class QueueSessionImpl extends AbstractSession implements QueueSession
    {

        /*
         * (non-Javadoc)
         * 
         * @see org.mule.transaction.xa.queue.QueueSession#getQueue(java.lang.String)
         */
        public Queue getQueue(String name)
        {
            QueueInfo queue = TransactionalQueueManager.this.getQueue(name);
            return new QueueImpl(queue);
        }

        protected class QueueImpl implements Queue
        {

            protected QueueInfo queue;

            public QueueImpl(QueueInfo queue)
            {
                this.queue = queue;
            }

            public void put(Object item) throws InterruptedException
            {
                offer(item, Long.MAX_VALUE);
            }

            public boolean offer(Object item, long timeout) throws InterruptedException
            {
                if (localContext != null) {
                    return ((QueueTransactionContext) localContext).offer(queue, item, timeout);
                } else {
                    try {
                        Object id = doStore(queue, item);
                        try {
                            if (!queue.offer(id, 0, timeout)) {
                                doRemove(queue, item);
                                return false;
                            } else {
                                return true;
                            }
                        } catch (InterruptedException e) {
                            doRemove(queue, item);
                            throw e;
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            public Object take() throws InterruptedException
            {
                return poll(Long.MAX_VALUE);
            }

            public Object poll(long timeout) throws InterruptedException
            {
                try {
                    if (localContext != null) {
                        return ((QueueTransactionContext) localContext).poll(queue, timeout);
                    } else {
                        Object id = queue.poll(timeout);
                        if (id != null) {
                            Object item = doLoad(queue, id);
                            doRemove(queue, id);
                            return item;
                        }
                        return null;
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public Object peek() throws InterruptedException
            {
                try {
                    if (localContext != null) {
                        return ((QueueTransactionContext) localContext).peek(queue);
                    } else {
                        Object id = queue.peek();
                        if (id != null) {
                            Object item = doLoad(queue, id);
                            doRemove(queue, id);
                            return item;
                        }
                        return null;
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public int size()
            {
                if (localContext != null) {
                    return ((QueueTransactionContext) localContext).size(queue);
                } else {
                    return queue.list.size();
                }
            }

        }
    }

    /**
     * @return Returns the persistenceStrategy.
     */
    public QueuePersistenceStrategy getPersistenceStrategy()
    {
        return persistenceStrategy;
    }

    /**
     * @param persistenceStrategy The persistenceStrategy to set.
     */
    public void setPersistenceStrategy(QueuePersistenceStrategy persistenceStrategy)
    {
        if (operationMode != OPERATION_MODE_STOPPED) {
            throw new IllegalStateException();
        }
        this.persistenceStrategy = persistenceStrategy;
    }

    public QueuePersistenceStrategy getMemoryPersistenceStrategy()
    {
        return memoryPersistenceStrategy;
    }

    public void setMemoryPersistenceStrategy(QueuePersistenceStrategy memoryPersistenceStrategy)
    {
        if (operationMode != OPERATION_MODE_STOPPED) {
            throw new IllegalStateException();
        }
        this.memoryPersistenceStrategy = memoryPersistenceStrategy;
    }
}
