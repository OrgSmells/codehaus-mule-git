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
package org.mule.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.util.ClassHelper;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

/**
 * <code>MuleDtdResolver</code> attempts to locate the mule-configuration.dtd
 * on the classpath, regardless of the DOCTYPE declaration.  If the dtd is not
 * found, it defaults to trying to download it using the systemId.
 * <p/>
 * This resolve is responsible for associating an Xsl document if any with the Dtd.
 * It also allows for a delegate Entity resolver and delegate Xsl.  This allows
 * Configuration builders to mix Mule Xml configuration with other document based
 * configuration and apply transformers to each of the configuration types (if necessary)
 * before constucting a Mule instance.
 * <p/>
 * Note that its up to the Configuration builder implementation to do the actual transformations
 * this Resolver simple associates Xsl reosurces with dtds
 *
 * @author <a href="mailto:ross.mason@cubis.co.uk">Ross Mason</a>
 * @version $Revision$
 */
public class MuleDtdResolver implements EntityResolver
{
    /**
     * logger used by this class
     */
    protected static transient Log logger = LogFactory.getLog(MuleDtdResolver.class);

    public static final String DEFAULT_MULE_DTD = "mule-configuration.dtd";
    private String dtdName = null;

    //Maybe the dtd should go in the META-INF??
    private static final String SEARCH_PATH = "";

    private EntityResolver delegate;
    private String xsl;
    private static String currentXsl;


    public MuleDtdResolver()
    {
        this(DEFAULT_MULE_DTD);
    }

    public MuleDtdResolver(String dtdName)
    {
        this(dtdName, null, null);
    }

    public MuleDtdResolver(String dtdName, String xsl)
    {
        this(dtdName, xsl, null);
    }

    public MuleDtdResolver(String dtdName, EntityResolver delegate )
    {
        this(dtdName, null, delegate);
    }

    public MuleDtdResolver(String dtdName, String xsl, EntityResolver delegate)
    {
        this.dtdName = dtdName;
        this.delegate = delegate;
        this.xsl = xsl;
        if (logger.isDebugEnabled())
        {
            StringBuffer buffer = new StringBuffer();
            buffer.append("Created Mule Dtd Resolver: ");
            buffer.append("dtd=").append(dtdName).append(", ");
            buffer.append("xsl=").append(xsl).append(", ");
            buffer.append("delegate resolver=").append(delegate).append(", ");
            logger.debug(buffer.toString());
        }
    }

    public InputSource resolveEntity(String publicId, String systemId) throws IOException, SAXException
    {
        logger.debug("Trying to resolve XML entity with public ID: " + publicId +
                " and system ID: " + systemId);

        InputSource source = null;
        currentXsl=null;
        if (delegate != null)
        {
            source = delegate.resolveEntity(publicId, systemId);
        }
        if (source == null)
        {
            if (systemId != null && systemId.indexOf(dtdName) > systemId.lastIndexOf("/"))
            {
                String dtdFile = systemId.substring(systemId.indexOf(dtdName));
                logger.debug("Looking on classpath for " + SEARCH_PATH + dtdFile);

                InputStream is = ClassHelper.getResourceAsStream(SEARCH_PATH + dtdFile, getClass());
                if (is != null)
                {
                    source = new InputSource(is);
                    source.setPublicId(publicId);
                    source.setSystemId(systemId);
                    logger.debug("Found on classpath mule DTD: " + systemId);
                    currentXsl=xsl;
                    return source;
                }
                logger.debug("Could not find dtd resource on classpath: " + SEARCH_PATH + dtdFile);
            }
        }
        return source;
    }

    public String getXslForDtd()
    {
        return currentXsl;
    }
}
