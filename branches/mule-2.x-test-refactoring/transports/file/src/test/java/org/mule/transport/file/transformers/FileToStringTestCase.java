/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.file.transformers;

import org.mule.api.transformer.TransformerException;
import org.mule.api.transformer.Transformer;
import org.mule.tck.AbstractTransformerTestCase;
import org.mule.transport.file.transformers.FileToString;
import org.mule.util.FileUtils;
import org.mule.util.SystemUtils;

import java.io.File;
import java.io.FileWriter;

/**
 * Test case for FileToString transformer
 */
public class FileToStringTestCase extends AbstractTransformerTestCase
{
    FileToString _fts;
    File _testData = null;
    final String _resultData = "The dog is on the table, where's the dog?";

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.tck.AbstractTransformerTestCase#doSetUp()
     */
    protected void doSetUp() throws Exception
    {
        super.doSetUp();
        _testData = FileUtils.newFile(SystemUtils.JAVA_IO_TMPDIR, "FileToStringTestData");
        FileWriter fw = new FileWriter(_testData);
        fw.write(_resultData);
        fw.close();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.tck.AbstractTransformerTestCase#doTearDown()
     */
    protected void doTearDown() throws Exception
    {
        assertTrue(_testData.delete());
        super.doTearDown();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.tck.AbstractTransformerTestCase#getResultData()
     */
    public Object getResultData()
    {
        return _resultData;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.tck.AbstractTransformerTestCase#getRoundTripTransformer()
     */
    public Transformer getRoundTripTransformer() throws Exception
    {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.tck.AbstractTransformerTestCase#getTestData()
     */
    public Object getTestData()
    {
        return _testData;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.tck.AbstractTransformerTestCase#getTransformer()
     */
    public Transformer getTransformer() throws Exception
    {
        return new FileToString();
    }

    /**
     * Transform with a wrong encoding should result in an Exception to be thrown
     */
    public void testTransformExcEnc() throws Exception
    {
        try
        {
            FileToString fts = (FileToString)getTransformer();
            fts.doTransform(getTestData(), "NO-SUCH_ENCODING");
            fail("Should fail when the specified encoding is not supported");
        }
        catch (TransformerException tfe)
        {
            // Expected
        }
    }

}
