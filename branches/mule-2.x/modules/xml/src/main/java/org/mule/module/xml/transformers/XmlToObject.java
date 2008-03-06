/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.xml.transformers;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

/**
 * <code>XmlToObject</code> converts xml created by the ObjectToXml transformer in to a
 * java object graph. This transformer uses XStream. Xstream uses some clever tricks so
 * objects that get marshalled to XML do not need to implement any interfaces including
 * Serializable and you don't even need to specify a default constructor.
 *
 * @see org.mule.transformer.xml.ObjectToXml
 */

public class XmlToObject extends AbstractXStreamTransformer
{

    private final DomDocumentToXml domTransformer = new DomDocumentToXml();

    public XmlToObject()
    {
        registerSourceType(String.class);
        registerSourceType(byte[].class);
        registerSourceType(InputStream.class);
        registerSourceType(org.w3c.dom.Document.class);
        registerSourceType(org.dom4j.Document.class);
        setReturnClass(Object.class);
    }

    public Object transform(MuleMessage message, String outputEncoding) throws TransformerException
    {
        Object src = message.getPayload();
        if (src instanceof byte[])
        {
            try
            {
                Reader xml = new InputStreamReader(new ByteArrayInputStream((byte[]) src), outputEncoding);
                return getXStream().fromXML(xml);
            }
            catch (UnsupportedEncodingException uee)
            {
                throw new TransformerException(this, uee);
            }
        }
        else if(src instanceof InputStream){
            try
            {
                Reader xml = new InputStreamReader((InputStream) src, outputEncoding);
                return getXStream().fromXML(xml);
            }
            catch (UnsupportedEncodingException uee)
            {
                throw new TransformerException(this, uee);
            }
            finally
            {
                try
                {
                    ((InputStream) src).close();
                }
                catch (IOException e)
                {
                    logger.warn("Exception closing stream: ", e);
                }
            }
        }
        else if (src instanceof String)
        {
            return getXStream().fromXML(src.toString());
        }
        else
        {
            return getXStream().fromXML((String) domTransformer.transform(src));
        }
    }

}
