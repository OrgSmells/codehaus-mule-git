/*
 * $Header$
 * $Revision$
 * $Date$
 * ------------------------------------------------------------------------------------------------------
 *
 * Copyright (c) SymphonySoft Limited. All rights reserved.
 * http://www.symphonysoft.com
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.mule.transformers.xml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.transform.ErrorListener;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.StackObjectPool;
import org.mule.config.i18n.Message;
import org.mule.config.i18n.Messages;
import org.mule.umo.lifecycle.InitialisationException;
import org.mule.umo.transformer.TransformerException;
import org.mule.umo.transformer.UMOTransformer;
import org.mule.util.Utility;

/**
 * <code>XsltTransformer</code> performs an XSLT transform on a DOM (or other XML-ish) object
 *
 * @author <a href="mailto:S.Vanmeerhaege@gfdi.be">Vanmeerhaeghe St?phane</a>
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @author <a href="mailto:jesper@selskabet.org">Jesper Steen M�ller</a>
 * @version $Revision$
 */

public class XsltTransformer extends AbstractXmlTransformer {

    private ObjectPool transformerPool;
    private int maxIdleTransformers = 2;
    private String xslFile;
    private static final int MIN_IDLE = 1;

    public XsltTransformer() {
        super();
    }

    /**
     * @see org.mule.umo.lifecycle.Initialisable#initialise()
     */

    public void initialise() throws InitialisationException {
        try {
            transformerPool = new StackObjectPool(new BasePoolableObjectFactory() {
                public Object makeObject() throws Exception {
                    StreamSource source = getStreamSource();
                    TransformerFactory factory = TransformerFactory.newInstance();
                    return factory.newTransformer(source);
                }
            }, Math.max(MIN_IDLE, maxIdleTransformers));
            transformerPool.addObject();
        } catch (Throwable te) {
            throw new InitialisationException(te, this);
        }
    }

    /**
     * Transform, using XSLT, a XML String to another String.
     *
     * @param src The source XML (String, byte[], DOM, etc.)
     * @return The result String (or DOM)
     */
    public Object doTransform(Object src, String encoding) throws TransformerException {
        try {
            Source sourceDoc = getXmlSource(src);
            if (sourceDoc == null) return null;
            
            ResultHolder holder = getResultHolder(returnClass);
            if (holder == null) holder = getResultHolder(src.getClass()); 
           
            DefaultErrorListener errorListener = new DefaultErrorListener(this);
            Transformer transformer = null;
            try {
                transformer = (Transformer)transformerPool.borrowObject();
                
                transformer.setErrorListener(errorListener);
                transformer.setOutputProperty(OutputKeys.ENCODING,encoding);
    
                if (logger.isDebugEnabled()) {
                    logger.debug("Before transform: " + convertToText(src));
                }
    
                transformer.transform(sourceDoc, holder.getResult());
                Object result = holder.getResultObject(); 
    
                if (logger.isDebugEnabled()) {
                    logger.debug("After transform: " + convertToText(result));
                }
                
                if(errorListener.isError()) {
                    throw errorListener.getException();
                }
                return result;
            } finally {
                if (transformer != null) transformerPool.returnObject(transformer);
            }
        } catch (Exception e) {
            throw new TransformerException(this, e);
        }
    }

    /**
     * @return Returns the xslFile.
     */
    public String getXslFile() {
        return xslFile;
    }

    /**
     * @param xslFile The xslFile to set.
     */
    public void setXslFile(String xslFile) {
        this.xslFile = xslFile;
    }

    /**
     * Returns the StreamSource corresponding to xslFile
     *
     * @return The StreamSource
     * @throws InitialisationException
     */
    private StreamSource getStreamSource() throws InitialisationException {
        if (xslFile == null) {
            throw new InitialisationException(new Message(Messages.X_IS_NULL, "xslFile"), this);
        }

        File file = new File(xslFile);
        StreamSource source;

        if (file.exists()) {
            source = new StreamSource(file);
        } else {
            try {
                InputStream stream = Utility.loadResource(xslFile, getClass());
                if(stream==null) {
                    throw new InitialisationException(new Message(Messages.CANT_LOAD_X_FROM_CLASSPATH_FILE, xslFile), this);
                }
                source = new StreamSource(stream);
            } catch (IOException e) {
                throw new InitialisationException(new Message(Messages.FAILED_LOAD_X, xslFile), e, this);
            }
        }

        return source;
    }

    public Object clone() throws CloneNotSupportedException {
        XsltTransformer x = (XsltTransformer) super.clone();

        try {
            if (x.transformer == null) {
                x.initialise();
            }
        } catch (Exception e) {
            throw new CloneNotSupportedException(e.getMessage());
        }
        return x;
    }

    private class DefaultErrorListener implements ErrorListener
    {
        private TransformerException e = null;
        private UMOTransformer trans;

        public DefaultErrorListener(UMOTransformer trans) {
            this.trans = trans;
        }

        public TransformerException getException() {
            return e;
        }

        public boolean isError() {
            return e!=null;
        }

        public void error(javax.xml.transform.TransformerException exception) throws javax.xml.transform.TransformerException {
            logger.error(exception.getMessage(), exception);
            e = new TransformerException(trans, exception);
        }

        public void fatalError(javax.xml.transform.TransformerException exception) throws javax.xml.transform.TransformerException {
            logger.fatal(exception.getMessage());
            e = new TransformerException(trans, exception);
        }

        public void warning(javax.xml.transform.TransformerException exception) throws javax.xml.transform.TransformerException {
            logger.warn(exception.getMessage());
        }
    }

    /**
     * @return The current maximum number of allowable idle transformer objects in the pool
     */
    public int getMaxIdleTransformers() {
        return maxIdleTransformers;
    }

    /**
     * Sets the the current maximum number of idle transformer objects allowed in the pool
     * 
     * @param maxIdleTransformers New maximum size to set
     */
    public void setMaxIdleTransformers(int maxIdleTransformers) {
        this.maxIdleTransformers = maxIdleTransformers;
    }
}
