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
 */
package org.mule.impl.space;

import org.mule.util.queue.JournalPersistenceStrategy;

import java.io.IOException;

/**
 * A high performance Persistent space that uses journalling to persist items
 *
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */
public class JournalledSpaceFactory  extends DefaultSpaceFactory {

    public JournalledSpaceFactory(boolean enableMonitorEvents) throws IOException {
        super(enableMonitorEvents);
        setPersistenceStrategy(new JournalPersistenceStrategy());
    }

    public JournalledSpaceFactory(boolean enableMonitorEvents, int capacity) throws IOException {
        super(enableMonitorEvents, capacity);
        setPersistenceStrategy(new JournalPersistenceStrategy());
    }

    public JournalledSpaceFactory(boolean enableMonitorEvents, boolean enableCaching) throws IOException {
        super(enableMonitorEvents);
        setPersistenceStrategy(new JournalPersistenceStrategy());
        setEnableCaching(enableCaching);
    }

    public JournalledSpaceFactory(boolean enableMonitorEvents, int capacity, boolean enableCaching) throws IOException {
        super(enableMonitorEvents, capacity);
        setPersistenceStrategy(new JournalPersistenceStrategy());
        setEnableCaching(enableCaching);
    }
}