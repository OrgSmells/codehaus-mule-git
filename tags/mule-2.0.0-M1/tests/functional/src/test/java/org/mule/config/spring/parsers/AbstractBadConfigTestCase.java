/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.spring.parsers;

import org.mule.config.ConfigurationBuilder;
import org.mule.config.builders.MuleXmlConfigurationBuilder;
import org.mule.tck.AbstractMuleTestCase;
import org.mule.tck.FunctionalTestCase;
import org.mule.util.ClassUtils;

/**
 * A stripped-down versino of FunctionalTestCase that allows us to test the parsing of a bad configuration. 
 */
public abstract class AbstractBadConfigTestCase extends AbstractMuleTestCase
{

    public void assertErrorContains(String phrase) throws Exception
    {
        try {
            parseConfig();
            fail("expected error");
        } catch (Exception e) {
            logger.debug("Caught " + e);
            assertTrue("Missing phrase: " + phrase, e.toString().indexOf(phrase) > -1);
        }
    }

    protected void parseConfig() throws Exception
    {
        ConfigurationBuilder builder = getBuilder();
        builder.configure(getConfigResources());
    }

    protected ConfigurationBuilder getBuilder() throws Exception
    {
        return new MuleXmlConfigurationBuilder();
    }

    protected abstract String getConfigResources();

}
