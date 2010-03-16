/*
 * $Id:XsltTransformerTestCase.java 5937 2007-04-09 22:35:04Z rossmason $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transformers.xml;

import org.mule.RequestContext;
import org.mule.api.MuleEvent;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.transformer.Transformer;
import org.mule.api.transformer.TransformerException;
import org.mule.module.xml.transformer.XsltTransformer;
import org.mule.module.xml.util.XMLTestUtils;
import org.mule.module.xml.util.XMLUtils;
import org.mule.tck.MuleTestUtils;
import org.mule.util.IOUtils;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLStreamReader;

public class XsltTransformerTestCase extends AbstractXmlTransformerTestCase
{

    private String srcData;
    private String resultData;

    // @Override
    @Override
    protected void doSetUp() throws Exception
    {
        srcData = IOUtils.getResourceAsString("cdcatalog.xml", getClass());
        resultData = IOUtils.getResourceAsString("cdcatalog.html", getClass());
    }

    @Override
    public Transformer getTransformer() throws Exception
    {
        XsltTransformer transformer = new XsltTransformer();
        transformer.setReturnClass(String.class);
        transformer.setXslFile("cdcatalog.xsl");
        transformer.setMaxActiveTransformers(42);
        transformer.setMuleContext(muleContext);
        transformer.initialise();
        return transformer;
    }

    @Override
    public Transformer getRoundTripTransformer() throws Exception
    {
        return null;
    }

    // @Override
    @Override
    public void testRoundtripTransform() throws Exception
    {
        // disable this test
    }

    @Override
    public Object getTestData()
    {
        return srcData;
    }

    @Override
    public Object getResultData()
    {
        return resultData;
    }

    public void testAllXmlMessageTypes() throws Exception
    {
        List list = XMLTestUtils.getXmlMessageVariants("cdcatalog.xml");
        Iterator it = list.iterator();
        
        Object expectedResult = getResultData();
        assertNotNull(expectedResult);
        
        Object msg, result;
        while (it.hasNext())
        {
            msg = it.next();
            result = getTransformer().transform(msg);
            assertNotNull(result);
            assertTrue("Test failed for message type: " + msg.getClass(), compareResults(expectedResult, result));
        }        
    }

    public void testTransformXMLStreamReader() throws Exception
    {
        Object expectedResult = getResultData();
        assertNotNull(expectedResult);
        
        XsltTransformer transformer = (XsltTransformer) getTransformer();
        
        InputStream is = IOUtils.getResourceAsStream("cdcatalog.xml", XMLTestUtils.class);
        XMLStreamReader sr = XMLUtils.toXMLStreamReader(transformer.getXMLInputFactory(), is);

        Object result = transformer.transform(sr);
        assertNotNull(result);
        assertTrue("expected: " + expectedResult + "\nresult: " + result, compareResults(expectedResult, result));
    }
    
    public void testCustomTransformerFactoryClass() throws InitialisationException
    {
        XsltTransformer t = new XsltTransformer();
        t.setXslTransformerFactory("com.nosuchclass.TransformerFactory");
        t.setXslFile("cdcatalog.xsl");

        try
        {
            t.initialise();
            fail("should have failed with ClassNotFoundException");
        }
        catch (InitialisationException iex)
        {
            assertEquals(ClassNotFoundException.class, iex.getCause().getClass());
        }

        // try again with JDK default
        t.setXslTransformerFactory(null);
        t.initialise();
    }

    public void testTransformWithStaticParam() throws TransformerException, InitialisationException
    {

        String xml =
                "<node1>" +
                     "<subnode1>sub node 1 original value</subnode1>" +
                     "<subnode2>sub node 2 original value</subnode2>" +
                 "</node1>";

        String param = "sub node 2 cool new value";

        String expectedTransformedxml =
                "<node1>" +
                    "<subnode1>sub node 1 original value</subnode1>" +
                    "<subnode2>" + param + "</subnode2>" +
                "</node1>";

        String xsl = someXslText();

        XsltTransformer transformer = new XsltTransformer();

        transformer.setReturnClass(String.class);
        // set stylesheet
        transformer.setXslt(xsl);

        // set parameter
        Map params = new HashMap();
        params.put("param1", param);
        transformer.setContextProperties(params);

        // init transformer
        transformer.initialise();

        // do transformation
        String transformerResult = (String) transformer.transform(xml);

        // remove doc type and CRLFs
        transformerResult = transformerResult.substring(transformerResult.indexOf("?>") + 2);

        assertTrue(transformerResult.indexOf(expectedTransformedxml) > -1);

    }

    private String someXslText()
    {
        return "<xsl:stylesheet xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" version=\"2.0\"" +
             " xmlns:wsdlsoap=\"http://schemas.xmlsoap.org/wsdl/soap/\"" +
             " xmlns:wsdl=\"http://schemas.xmlsoap.org/wsdl/\">" +
             "<xsl:param name=\"param1\"/>" +
             "<xsl:template match=\"@*|node()\">" +
                 "<xsl:copy><xsl:apply-templates select=\"@*|node()\"/></xsl:copy>" +
             "</xsl:template>" +
                 "<xsl:template match=\"/node1/subnode2/text()\">" +
                 "<xsl:value-of select=\"$param1\"/>" +
             "</xsl:template>" +
         "</xsl:stylesheet>";
    }

    public void testTransformWithDynamicParam() throws Exception
    {

        String xml =
                "<node1>" +
                     "<subnode1>sub node 1 original value</subnode1>" +
                     "<subnode2>sub node 2 original value</subnode2>" +
                 "</node1>";

        String param = "sub node 2 cool new value";

        String expectedTransformedxml =
                "<node1>" +
                    "<subnode1>sub node 1 original value</subnode1>" +
                    "<subnode2>" + param + "</subnode2>" +
                "</node1>";

        String xsl = someXslText();

        XsltTransformer transformer = new XsltTransformer();

        transformer.setReturnClass(String.class);
        transformer.setMuleContext(muleContext);
        // set stylesheet
        transformer.setXslt(xsl);

        // set parameter
        Map params = new HashMap();
        params.put("param1", "#[header:myproperty]");
        transformer.setContextProperties(params);

        // init transformer
        transformer.initialise();

        // set up MuleEventContext
        MuleEvent event = MuleTestUtils.getTestEvent("test message data", muleContext);
        event.getMessage().setProperty("myproperty", param);
        RequestContext.setEvent(event);

        // do transformation
        String transformerResult = (String) transformer.transform(xml);

        // remove doc type and CRLFs
        transformerResult = transformerResult.substring(transformerResult.indexOf("?>") + 2);

        assertTrue(transformerResult.indexOf(expectedTransformedxml) > -1);
    }

    public void testInitialiseMustLoadXsltFile_dontLoadIfThereIsXslText() throws Exception
    {
        XsltTransformer xsltTransformer = new XsltTransformer();
        xsltTransformer.setXslt(someXslText());
        try
        {
            xsltTransformer.initialise();
            assertEquals(someXslText(), xsltTransformer.getXslt());
        }
        catch (InitialisationException e)
        {
            fail("Should not have thrown an exception: " + e);
        }
    }

    public void testInitialiseMustLoadXsltFile_ThrowExceptionIfNoXslTextNorFile() throws Exception
    {
        XsltTransformer xsltTransformer = new XsltTransformer();
        try
        {
            xsltTransformer.initialise();
            fail("Should have thrown an exception because nor xslt-text nor xslt-file was set.");
        }
        catch (InitialisationException e)
        {
            assertTrue(e.getMessage().contains("xsl-file or xsl-text"));
        }
    }

    public void testInitialiseMustLoadXsltFile_ThrowExceptionIfXslFileDoesNotExist() throws Exception
    {
        XsltTransformer xsltTransformer = new XsltTransformer();
        String someNonExistentFileName = "some nonexistent file";
        xsltTransformer.setXslFile(someNonExistentFileName);
        try
        {
            xsltTransformer.initialise();
            fail("Should have thrown an exception because file '" + someNonExistentFileName
                 + "' does not exist.");
        }
        catch (InitialisationException e)
        {
            assertTrue(e.getMessage().contains(someNonExistentFileName));
        }
    }

    public void testInitialiseMustLoadXsltFile_AllGoodIfXslFileDoesNotExistButXslTextIsSet() throws Exception
    {
        XsltTransformer xsltTransformer = new XsltTransformer();
        String someNonExistentFileName = "some nonexistent file";
        xsltTransformer.setXslFile(someNonExistentFileName);
        xsltTransformer.setXslt(someXslText());
        try
        {
            xsltTransformer.initialise();
            assertEquals(someXslText(), xsltTransformer.getXslt());
        }
        catch (InitialisationException e)
        {
            fail("Should NOT have thrown an exception because, despite the fact that file '"
                 + someNonExistentFileName + "' does not exist, the xsl-text property is set.");
        }
    }

    public void testInitialiseMustLoadXsltFile_LoadsFromXslFile() throws Exception
    {
        XsltTransformer xsltTransformer = new XsltTransformer();
        String someExistentFileName = "cdcatalog.xsl";
        xsltTransformer.setXslFile(someExistentFileName);
        try
        {
            xsltTransformer.initialise();
            assertNotNull(xsltTransformer.getXslt());
            String someTextThatIsInTheXslFile = "My CD Collection";
            assertTrue("Should contain the text '" + someTextThatIsInTheXslFile + "', because it is in the '"
                       + someExistentFileName + "' file that we are setting.", xsltTransformer.getXslt()
                .contains(
                someTextThatIsInTheXslFile));
        }
        catch (InitialisationException e)
        {
            fail("Should NOT have thrown an exception because file '" + someExistentFileName
                 + "' DOES exist.");
        }
    }

}
