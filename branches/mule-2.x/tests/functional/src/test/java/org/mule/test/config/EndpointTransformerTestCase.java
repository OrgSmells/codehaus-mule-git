/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.test.config;

import org.mule.tck.AbstractMuleTestCase;
import org.mule.transformers.NoActionTransformer;
import org.mule.transformers.TransformerUtils;
import org.mule.umo.UMOException;
import org.mule.umo.endpoint.UMOImmutableEndpoint;

public class EndpointTransformerTestCase extends AbstractMuleTestCase
{

    public void testTransformerProperty() throws UMOException
    {
        muleContext.getRegistry().registerTransformer(new NoActionTransformer());
        UMOImmutableEndpoint endpoint = muleContext.getRegistry().lookupEndpointFactory().getOutboundEndpoint(
            "test:///tmp?transformers=NoActionTransformer");
        assertEquals("NoActionTransformer", TransformerUtils.firstOrNull(endpoint.getTransformers()).getName());
    }

    public void testResponseTransformerProperty() throws UMOException
    {
        muleContext.getRegistry().registerTransformer(new NoActionTransformer());
        UMOImmutableEndpoint endpoint = muleContext.getRegistry().lookupEndpointFactory().getInboundEndpoint(
            "test:///tmp?responseTransformers=NoActionTransformer");
        assertEquals("NoActionTransformer", TransformerUtils.firstOrNull(endpoint.getResponseTransformers()).getName());
    }
}
