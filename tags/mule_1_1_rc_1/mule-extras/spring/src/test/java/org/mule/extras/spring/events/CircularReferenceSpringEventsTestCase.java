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
package org.mule.extras.spring.events;

import org.mule.MuleManager;
import org.mule.config.builders.MuleXmlConfigurationBuilder;
import org.mule.tck.AbstractMuleTestCase;
import org.mule.umo.manager.UMOManager;
import org.springframework.context.support.AbstractApplicationContext;

/**
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */
public class CircularReferenceSpringEventsTestCase extends AbstractMuleTestCase
{
    protected void setUp() throws Exception
    {
        if (MuleManager.isInstanciated())
            MuleManager.getInstance().dispose();

        MuleXmlConfigurationBuilder builder = new MuleXmlConfigurationBuilder();
        builder.configure("mule-events-with-manager.xml");
    }

    public void testManagerIsInstanciated() throws Exception
    {
        UMOManager m = MuleManager.getInstance();
        assertTrue(m.isInitialised());
        assertTrue(m.isStarted());
        assertNotNull(m.getContainerContext());
        assertNotNull(m.getContainerContext()
                       .getComponent(AbstractApplicationContext.APPLICATION_EVENT_MULTICASTER_BEAN_NAME));
    }
}
