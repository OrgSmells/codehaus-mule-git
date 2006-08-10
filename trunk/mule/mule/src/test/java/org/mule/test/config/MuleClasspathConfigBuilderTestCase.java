/*
 * $Id
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the BSD style
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
import org.mule.config.ConfigurationBuilder;
import org.mule.config.ConfigurationException;
import org.mule.config.builders.MuleClasspathConfigurationBuilder;

/**
 * Test Case for MuleClasspathCofigurationBuilder. It borrows everything
 * from the corresponding MuleXml config builder and just substitute the
 * builder itself since the config files are in the tests classpath.
 * 
 * @author Massimo Lusetti <mlusetti@gmail.com>
 */
public class MuleClasspathConfigBuilderTestCase extends
        MuleXmlConfigBuilderTestCase
{
    /**
     * Get the builder
     */
    public ConfigurationBuilder getBuilder()
    {
        try {
            return new MuleClasspathConfigurationBuilder();
        } catch (ConfigurationException e) {
            fail(e.getMessage());
            return null;
        }
    }
}
