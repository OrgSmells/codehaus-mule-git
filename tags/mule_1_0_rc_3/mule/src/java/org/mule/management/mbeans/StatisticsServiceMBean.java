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
package org.mule.management.mbeans;

import org.mule.management.stats.Statistics;


/**
 * <code>StatisticsServiceMBean</code> is a JMX interfaces for querying Mule
 * event processing statistics
 *
 * @author <a href="mailto:S.Vanmeerhaege@gfdi.be">Vanmeerhaeghe St�phane</a>
 * @version $Revision$
 */
public interface StatisticsServiceMBean extends Statistics {

    public void logCSVSummary();

    public String printHtmlSummary();
}