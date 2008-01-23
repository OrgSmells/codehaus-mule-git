/*
 * $Id:SerializedUMOMessageTransformersTestCase.java 5937 2007-04-09 22:35:04Z rossmason $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transformer.simple;

import org.mule.DefaultMuleEvent;
import org.mule.DefaultMuleMessage;
import org.mule.RequestContext;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.Transformer;
import org.mule.tck.AbstractTransformerTestCase;
import org.mule.tck.MuleTestUtils;
import org.mule.tck.testmodels.fruit.Apple;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.output.ByteArrayOutputStream;

public class SerializedUMOMessageTransformersTestCase extends AbstractTransformerTestCase
{
    private MuleMessage testObject = null;

    // @Override
    protected void doSetUp() throws Exception
    {
        RequestContext.setEvent(new DefaultMuleEvent(testObject, getTestEndpoint("test", "sender"), MuleTestUtils
            .getTestSession(), true));
    }

    // @Override
    protected void doTearDown() throws Exception
    {
        RequestContext.clear();
    }

    // @Override

    public void testTransform() throws Exception
    {
        // this depends on the ordering of properties in the map.
        // because we now make a copy of maps in RequestContext this order can change
        //super.testTransform();
    }

    public SerializedUMOMessageTransformersTestCase()
    {
        Map props = new HashMap();
        props.put("object", new Apple());
        props.put("number", new Integer(1));
        props.put("string", "hello");
        testObject = new DefaultMuleMessage("test", props);
    }

    public Transformer getTransformer() throws Exception
    {
        return new UMOMessageToByteArray();
    }

    public Transformer getRoundTripTransformer() throws Exception
    {
        return new ByteArrayToUMOMessage();
    }

    public Object getTestData()
    {
        return testObject;
    }

    public Object getResultData()
    {
        try
        {
            ByteArrayOutputStream bs = null;
            ObjectOutputStream os = null;

            bs = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bs);
            os.writeObject(testObject);
            os.flush();
            os.close();
            return bs.toByteArray();
        }
        catch (IOException e)
        {
            throw new IllegalStateException(e.getMessage());
        }
    }

    // @Override
    public boolean compareResults(Object src, Object result)
    {
        if (src == null && result == null)
        {
            return true;
        }
        if (src == null || result == null)
        {
            return false;
        }
        return Arrays.equals((byte[])src, (byte[])result);
    }

    // @Override
    public boolean compareRoundtripResults(Object src, Object result)
    {
        if (src == null && result == null)
        {
            return true;
        }
        if (src == null || result == null)
        {
            return false;
        }
        if (src instanceof MuleMessage && result instanceof MuleMessage)
        {
            return ((MuleMessage)src).getPayload().equals(((MuleMessage)result).getPayload())
                            && ((MuleMessage)src).getProperty("object").equals(
                                ((MuleMessage)result).getProperty("object"))
                            && ((MuleMessage)src).getProperty("string").equals(
                                ((MuleMessage)result).getProperty("string"))
                            && ((MuleMessage)src).getIntProperty("number", -1) == ((MuleMessage)result)
                                .getIntProperty("number", -2);
        }
        else
        {
            return false;
        }
    }
}
