/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.impl.model;

import org.mule.providers.service.TransportFactory;
import org.mule.umo.model.UMOModel;
import org.mule.util.BeanUtils;
import org.mule.util.ClassUtils;
import org.mule.util.SpiUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Will locate the model service in META-INF/service using the model type as the key
 * and construct the model.
 */
public final class ModelFactory
{

    public static final String DEFAULT_MODEL_NAME = "main";

    public static final String MODEL_SERVICE_PATH = "org/mule/models";

    /** Do not instanciate. */
    private ModelFactory ()
    {
        // no-op
    }

    public static UMOModel createModel(String type) throws ModelServiceNotFoundException
    {
        String location = SpiUtils.SERVICE_ROOT + MODEL_SERVICE_PATH;
        InputStream is = SpiUtils.findServiceDescriptor(MODEL_SERVICE_PATH, type + ".properties", TransportFactory.class);
        try
        {
            if (is != null)
            {
                Properties props = new Properties();
                props.load(is);
                String clazz = props.getProperty("model");
                try
                {
                    UMOModel model = (UMOModel) ClassUtils.instanciateClass(clazz, ClassUtils.NO_ARGS,
                        ModelFactory.class);
                    BeanUtils.populateWithoutFail(model, props, false);
                    if (model.getName() == null)
                    {
                        model.setName(DEFAULT_MODEL_NAME);
                    }
                    return model;
                }
                catch (Exception e)
                {
                    throw new ModelServiceNotFoundException(location + "/" + type, e);
                }
            }
            else
            {
                throw new ModelServiceNotFoundException(location + "/" + type);
            }
        }
        catch (IOException e)
        {
            throw new ModelServiceNotFoundException(location + "/" + type, e);
        }
    }
}
