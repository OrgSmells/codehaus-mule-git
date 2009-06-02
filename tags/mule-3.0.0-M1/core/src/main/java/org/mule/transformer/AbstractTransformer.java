/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transformer;

import org.mule.api.MuleMessage;
import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.transformer.Transformer;
import org.mule.api.transformer.TransformerException;
import org.mule.api.transport.MessageAdapter;
import org.mule.config.i18n.CoreMessages;
import org.mule.transport.NullPayload;
import org.mule.util.ClassUtils;
import org.mule.util.FileUtils;
import org.mule.util.StringMessageUtils;
import org.mule.util.StringUtils;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import javax.xml.transform.stream.StreamSource;

import edu.emory.mathcs.backport.java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <code>AbstractTransformer</code> is a base class for all transformers.
 * Transformations transform one object into another.
 */

public abstract class AbstractTransformer implements Transformer
{
    protected static final int DEFAULT_TRUNCATE_LENGTH = 200;

    protected final Log logger = LogFactory.getLog(getClass());

    /**
     * The return type that will be returned by the {@link #transform} method is
     * called
     */
    protected Class returnClass = Object.class;

    /**
     * The name that identifies this transformer. If none is set the class name of
     * the transformer is used
     */
    protected String name = null;

    /** The endpoint that this transformer instance is configured on */
    protected ImmutableEndpoint endpoint = null;

    /**
     * A list of supported Class types that the source payload passed into this
     * transformer
     */
    protected final List sourceTypes = new CopyOnWriteArrayList();

    /**
     * Determines whether the transformer will throw an exception if the message
     * passed is is not supported or the return tye is incorrect
     */
    private boolean ignoreBadInput = false;

    /** default constructor required for discovery */
    public AbstractTransformer()
    {
        super();
    }

    protected Object checkReturnClass(Object object) throws TransformerException
    {
        if (returnClass != null)
        {
            if (!returnClass.isInstance(object))
            {
                throw new TransformerException(
                        CoreMessages.transformUnexpectedType(object.getClass(), returnClass),
                        this);
            }
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("The transformed object is of expected type. Type is: " +
                    ClassUtils.getSimpleName(object.getClass()));
        }

        return object;
    }

    protected void registerSourceType(Class aClass)
    {
        if (!sourceTypes.contains(aClass))
        {
            sourceTypes.add(aClass);

            if (aClass.equals(Object.class))
            {
                logger.debug("java.lang.Object has been added as source type for this transformer, there will be no source type checking performed");
            }
        }
    }

    protected void unregisterSourceType(Class aClass)
    {
        sourceTypes.remove(aClass);
    }

    /** @return transformer name */
    public String getName()
    {
        if (name == null)
        {
            name = this.generateTransformerName();
        }
        return name;
    }

    /** @param string  */
    public void setName(String string)
    {
        if (string == null)
        {
            string = ClassUtils.getSimpleName(this.getClass());
        }

        logger.debug("Setting transformer name to: " + string);
        name = string;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.mule.transformer.Transformer#getReturnClass()
     */
    public Class getReturnClass()
    {
        return returnClass;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.mule.transformer.Transformer#setReturnClass(java.lang.String)
     */
    public void setReturnClass(Class newClass)
    {
        returnClass = newClass;
    }

    public boolean isSourceTypeSupported(Class aClass)
    {
        return isSourceTypeSupported(aClass, false);
    }

    public boolean isSourceTypeSupported(Class aClass, boolean exactMatch)
    {
        int numTypes = sourceTypes.size();

        if (numTypes == 0)
        {
            return !exactMatch;
        }

        for (int i = 0; i < numTypes; i++)
        {
            Class anotherClass = (Class) sourceTypes.get(i);
            if (exactMatch)
            {
                if (anotherClass.equals(aClass))
                {
                    return true;
                }
            }
            else if (anotherClass.isAssignableFrom(aClass))
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Transforms the object.
     *
     * @param src The source object to transform.
     * @return The transformed object
     */
    public final Object transform(Object src) throws TransformerException
    {
        String encoding = null;

        Object payload = src;
        MessageAdapter adapter;
        if (src instanceof MessageAdapter)
        {
            encoding = ((MessageAdapter) src).getEncoding();
            adapter = (MessageAdapter) src;
            if ((!isSourceTypeSupported(MessageAdapter.class, true)
                    && !isSourceTypeSupported(MuleMessage.class, true)
                    && !(this instanceof AbstractMessageAwareTransformer))
                    )
            {
                src = ((MessageAdapter) src).getPayload();
                payload = adapter.getPayload();
            }
        }

        if (encoding == null && endpoint != null)
        {
            encoding = endpoint.getEncoding();
        }
        else if (encoding == null)
        {
            encoding = FileUtils.DEFAULT_ENCODING;
        }

        Class srcCls = src.getClass();
        if (!isSourceTypeSupported(srcCls))
        {
            if (ignoreBadInput)
            {
                logger.debug("Source type is incompatible with this transformer and property 'ignoreBadInput' is set to true, so the transformer chain will continue.");
                return payload;
            }
            else
            {
                throw new TransformerException(
                        CoreMessages.transformOnObjectUnsupportedTypeOfEndpoint(this.getName(),
                                payload.getClass(), endpoint), this);
            }
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("Applying transformer " + getName() + " (" + getClass().getName() + ")");
            logger.debug("Object before transform: "
                    + StringMessageUtils.truncate(StringMessageUtils.toString(payload), DEFAULT_TRUNCATE_LENGTH, false));
        }

        Object result;
        result = doTransform(payload, encoding);
        // }
        if (result == null)
        {
            result = NullPayload.getInstance();
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("Object after transform: "
                    + StringMessageUtils.truncate(StringMessageUtils.toString(result), DEFAULT_TRUNCATE_LENGTH, false));
        }

        result = checkReturnClass(result);
        return result;
    }

    protected boolean isConsumed(Class srcCls)
    {
        return InputStream.class.isAssignableFrom(srcCls) || StreamSource.class.isAssignableFrom(srcCls);
    }

    public ImmutableEndpoint getEndpoint()
    {
        return endpoint;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.mule.api.transformer.Transformer#setConnector(org.mule.api.transport.Connector)
     */
    public void setEndpoint(ImmutableEndpoint endpoint)
    {
        this.endpoint = endpoint;
    }

    protected abstract Object doTransform(Object src, String encoding) throws TransformerException;

    /**
     * Template method where deriving classes can do any initialisation after the
     * properties have been set on this transformer
     *
     * @throws InitialisationException
     */
    public void initialise() throws InitialisationException
    {
        // do nothing, subclasses may override
    }

    protected String generateTransformerName()
    {
        String name = ClassUtils.getSimpleName(this.getClass());
        int i = name.indexOf("To");
        if (i > 0 && returnClass != null)
        {
            String target = ClassUtils.getSimpleName(returnClass);
            if (target.equals("byte[]"))
            {
                target = "byteArray";
            }
            name = name.substring(0, i + 2) + StringUtils.capitalize(target);
        }
        return name;
    }

    public List getSourceTypes()
    {
        return Collections.unmodifiableList(sourceTypes);
    }

    public boolean isIgnoreBadInput()
    {
        return ignoreBadInput;
    }

    public void setIgnoreBadInput(boolean ignoreBadInput)
    {
        this.ignoreBadInput = ignoreBadInput;
    }

    @Override
    public String toString()
    {
        StringBuffer sb = new StringBuffer(80);
        sb.append(ClassUtils.getSimpleName(this.getClass()));
        sb.append("{this=").append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(", name='").append(name).append('\'');
        sb.append(", ignoreBadInput=").append(ignoreBadInput);
        sb.append(", returnClass=").append(returnClass);
        sb.append(", sourceTypes=").append(sourceTypes);
        sb.append('}');
        return sb.toString();
    }

    public boolean isAcceptNull()
    {
        return false;
    }

}
