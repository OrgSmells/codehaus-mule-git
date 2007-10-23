/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.providers.file;

import org.mule.MuleManager;
import org.mule.tck.FunctionalTestCase;
import org.mule.tck.functional.EventCallback;
import org.mule.tck.functional.FunctionalTestComponent;
import org.mule.umo.UMOEventContext;
import org.mule.util.FileUtils;

import java.io.File;

import edu.emory.mathcs.backport.java.util.concurrent.CountDownLatch;
import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;

public class FileComparatorTestCase extends FunctionalTestCase
{

    public static final String PATH = "./.mule/in/";
    public static final String FILE_CONNECTOR_NAME = "fileConnector";
    public static final int TIMEOUT = 50000;
    public static final String FILE_NAMES[] = {"first", "second"};
    public static final String MODEL_NAME = "ESTest";
    public static final String COMPONENT_NAME = "FolderTO";


    public void testComparator() throws Exception
    {

        final CountDownLatch countDown = new CountDownLatch(2);
        EventCallback callback = new EventCallback()
        {
            public void eventReceived(UMOEventContext context, Object component) throws Exception
            {
                int index = (int) countDown.getCount() - 1;
                assertEquals(context.getMessage().getProperty(FileConnector.PROPERTY_ORIGINAL_FILENAME), FILE_NAMES[index]);
                countDown.countDown();
            }
        };

        ((FunctionalTestComponent) MuleManager.getInstance().lookupModel(MODEL_NAME).getComponent(COMPONENT_NAME).getDescriptor().getImplementation()).
                setEventCallback(callback);

        MuleManager.getInstance().lookupConnector(FILE_CONNECTOR_NAME).stopConnector();
        File f1 = FileUtils.newFile(PATH + FILE_NAMES[0]);
        f1.createNewFile();
        Thread.sleep(1);
        File f2 = FileUtils.newFile(PATH + FILE_NAMES[1]);
        f2.createNewFile();
        Thread.sleep(10);
        MuleManager.getInstance().lookupConnector(FILE_CONNECTOR_NAME).startConnector();
        assertTrue(countDown.await(TIMEOUT, TimeUnit.MILLISECONDS));
    }


    protected String getConfigResources()
    {
        return "file-functional-config.xml";
    }
}
