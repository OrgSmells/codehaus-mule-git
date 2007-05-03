/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package net.sf.pzfilereader.writer;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import junit.framework.Assert;

import net.sf.pzfilereader.InitialisationException;

public class FixedLengthWriterTestCase extends PZWriterTestCase
{
    public void testWriteFixedLength() throws Exception
    {
        OutputStream out = new ByteArrayOutputStream();

        InputStream mapping = this.getClass().getClassLoader().getResourceAsStream("FixedLength.pzmap.xml");        
        PZWriter writer = new FixedWriterFactory(mapping).createWriter(out);

        writer.addRecordEntry("LASTNAME", "DOE");
        writer.addRecordEntry("ADDRESS", "1234 CIRCLE CT");
        writer.addRecordEntry("STATE", "OH");
        writer.addRecordEntry("ZIP", "44035");
        writer.addRecordEntry("FIRSTNAME", "JOHN");
        writer.addRecordEntry("CITY", "ELYRIA");
        writer.nextRecord();
        writer.flush();
        
        String expected = this.normalizeLineEnding("JOHN                               DOE                                1234 CIRCLE CT                                                                                      ELYRIA                                                                                              OH44035");
        Assert.assertEquals(expected, out.toString());
    }
    
    public void testWriterWithDifferentFillChar() throws Exception
    {
        OutputStream out = new ByteArrayOutputStream();

        InputStream mapping = this.getClass().getClassLoader().getResourceAsStream("FixedLength.pzmap.xml");        
        PZWriter writer = new FixedWriterFactory(mapping, '.').createWriter(out);

        writer.addRecordEntry("LASTNAME", "DOE");
        writer.addRecordEntry("ADDRESS", "1234 CIRCLE CT");
        writer.addRecordEntry("STATE", "OH");
        writer.addRecordEntry("ZIP", "44035");
        writer.addRecordEntry("FIRSTNAME", "JOHN");
        writer.addRecordEntry("CITY", "ELYRIA");
        writer.nextRecord();
        writer.flush();
        
        String expected = this.normalizeLineEnding("JOHN...............................DOE................................1234 CIRCLE CT......................................................................................ELYRIA..............................................................................................OH44035");
        Assert.assertEquals(expected, out.toString());
    }

    public void testCreateParserWithMalformedMappingFile() throws Exception
    {
        try
        {
            InputStream mapping = this.getClass().getClassLoader().getResourceAsStream("BrokenMapping.pzmap.xml");
            new FixedWriterFactory(mapping);
            Assert.fail();
        }
        catch (InitialisationException ie)
        {
            // this excecption must occur, mapping xml is invalid
        }
    }
    
    public void testWriteStringWiderThanColumnDefinition() throws Exception
    {
        InputStream mapping = this.getClass().getClassLoader().getResourceAsStream("FixedLength.pzmap.xml");
        OutputStream out = new ByteArrayOutputStream();
        
        PZWriter writer = new FixedWriterFactory(mapping).createWriter(out);
        try
        {
            writer.addRecordEntry("STATE", "THISISTOOLONG");
            Assert.fail("writing entries that are too long should fail");
        }
        catch (IllegalArgumentException iae)
        {
            // expected exception
        }
    }
    
    public void testWriteNullColumn() throws Exception
    {
        OutputStream out = new ByteArrayOutputStream();
        
        InputStream mapping = this.getClass().getClassLoader().getResourceAsStream("FixedLength.pzmap.xml");        
        PZWriter writer = new FixedWriterFactory(mapping).createWriter(out);

        writer.addRecordEntry("LASTNAME", "DOE");
        writer.addRecordEntry("ADDRESS", "1234 CIRCLE CT");
        writer.addRecordEntry("STATE", "OH");
        writer.addRecordEntry("ZIP", "44035");
        // note that we don't write a firstname
        writer.addRecordEntry("FIRSTNAME", null);
        writer.addRecordEntry("CITY", "ELYRIA");
        writer.nextRecord();
        writer.flush();
        
        String expected = this.normalizeLineEnding("                                   DOE                                1234 CIRCLE CT                                                                                      ELYRIA                                                                                              OH44035");
        Assert.assertEquals(expected, out.toString());
    }
}


