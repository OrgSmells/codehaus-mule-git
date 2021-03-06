/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transformers.xml;

import java.io.ByteArrayInputStream;

import org.mule.umo.UMOEventContext;
import org.mule.umo.transformer.TransformerException;

/**
 * <code>XmlToObject</code> converts xml created by the ObjectToXml transformer in
 * to a java object graph. This transformer uses XStream. Xstream uses some cleaver
 * tricks so objects that get marshalled to xml do not need to implement any
 * interfaces including Serializable and you don't even need to specify a default
 * constructor.
 * 
 * @see org.mule.transformers.xml.ObjectToXml
 */

public class XmlToObject extends AbstractXStreamTransformer
{
    /**
     * Serial version
     */
    private static final long serialVersionUID = -2804761502812600068L;

    private final DomDocumentToXml domTransformer = new DomDocumentToXml();

    public XmlToObject()
    {
        registerSourceType(String.class);
        registerSourceType(byte[].class);
        registerSourceType(org.w3c.dom.Document.class);
        registerSourceType(org.dom4j.Document.class);
    }

    public Object transform(Object src, String encoding, UMOEventContext context) throws TransformerException
    {

        if (src instanceof byte[])
        {
            return getXStream().fromXML(new ByteArrayInputStream((byte[])src));
        }
        else if (src instanceof String)
        {
            return getXStream().fromXML(src.toString());
        }
        else
        {
            return getXStream().fromXML((String)domTransformer.transform(src));
        }
    }
}
