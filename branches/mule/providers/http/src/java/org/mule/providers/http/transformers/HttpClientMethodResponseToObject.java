/*
 * $Header$
 * $Revision$
 * $Date$
 * ------------------------------------------------------------------------------------------------------
 *
 * Copyright (c) Cubis Limited. All rights reserved.
 * http://www.cubis.co.uk
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.mule.providers.http.transformers;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpMethod;
import org.mule.impl.MuleMessage;
import org.mule.providers.http.HttpConstants;
import org.mule.transformers.AbstractTransformer;
import org.mule.umo.UMOMessage;
import org.mule.umo.transformer.TransformerException;

import java.util.HashMap;
import java.util.Map;

/**
 * <code>HttpClientMethodResponseToObject</code> transforms a http client response to a
 * MuleMessage.
 *
 * @author <a href="mailto:ross.mason@cubis.co.uk">Ross Mason</a>
 * @version $Revision$
 */

public class HttpClientMethodResponseToObject extends AbstractTransformer
{
    public HttpClientMethodResponseToObject()
    {
        registerSourceType(HttpMethod.class);
        setReturnClass(UMOMessage.class);
    }

    public Object doTransform(Object src) throws TransformerException
    {
        Object msg;
        HttpMethod httpMethod = (HttpMethod)src;
        Header contentType = httpMethod.getResponseHeader(HttpConstants.HEADER_CONTENT_TYPE);
        if(contentType!=null && !contentType.getValue().startsWith("text/")) {
            msg = httpMethod.getResponseBody();
        } else {
            msg = httpMethod.getResponseBodyAsString();
        }
        //Standard headers
        Map headerProps = new HashMap();
        Header[] headers = httpMethod.getRequestHeaders();
        for (int i=0;i < headers.length; i++)
        {
            headerProps.put(headers[i].getName(), headers[i].getValue());
        }
        return new MuleMessage(msg, headerProps);
    }
}
