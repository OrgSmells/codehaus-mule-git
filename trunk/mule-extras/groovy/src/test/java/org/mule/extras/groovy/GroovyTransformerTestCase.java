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
package org.mule.extras.groovy;

/**
 * @author <a href="mailto:ross.mason@cubis.co.uk">Ross Mason</a>
 * @version $Revision$
 */
import org.mule.extras.groovy.transformers.GroovyTransformer;
import org.mule.tck.AbstractTransformerTestCase;
import org.mule.umo.transformer.UMOTransformer;

import java.util.ArrayList;
import java.util.List;

public class GroovyTransformerTestCase extends AbstractTransformerTestCase {
    public UMOTransformer getTransformer() throws Exception
    {
        GroovyTransformer transformer = new GroovyTransformer();
        transformer.setName("StringToList");
        transformer.initialise();
        return transformer;
    }

    public UMOTransformer getRoundTripTransformer() throws Exception
    {
        GroovyTransformer transformer = new GroovyTransformer();
        transformer.setName("ListToStringTransformer");
        transformer.setMethodName("getString");
        transformer.setScript("ListToString.groovy");
        transformer.initialise();
        return transformer;
    }

    public Object getTestData()
    {
        return "this is groovy!";
    }

    public Object getResultData()
    {
        List list = new ArrayList();
        list.add("this");
        list.add("is");
        list.add("groovy!");
        return list;
    }

}