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
package org.mule.management.stats;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.management.stats.printers.AbstractTablePrinter;
import org.mule.management.stats.printers.SimplePrinter;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * <code>AllStatistics</code> todo
 * 
 * @author <a href="mailto:S.Vanmeerhaege@gfdi.be">Vanmeerhaeghe St�phane </a>
 * @version $Revision$
 */
public class AllStatistics {

    private static final Log log = LogFactory.getLog(AllStatistics.class);
    private boolean isStatisticsEnabled;
    private long startTime;

    private HashMap componentStat = new HashMap();

    /**
     *  
     */
    public AllStatistics() {
        clear();
    }

    public void logSummary() {
        logSummary(new SimplePrinter(System.out));
    }

    public void logSummary(PrintWriter printer) {

        if(printer instanceof AbstractTablePrinter) {
            printer.print(componentStat.values());
        } else {
            Iterator it = componentStat.values().iterator();

            while (it.hasNext()) {
                printer.print(it.next());
            }
        }
//        printer.println("-----------------------------");
//        printer.println("duration (ms): " + (System.currentTimeMillis() - startTime));
    }

    public synchronized void clear() {

        Iterator it = getComponentStatistics().iterator();
        
         while(it.hasNext())
         {
             ((Statistics) it.next()).clear();
         }
        startTime = System.currentTimeMillis();
    }

    /**
     * Are statistics logged
     */
    public boolean isEnabled() {
        return isStatisticsEnabled;
    }

    /**
     * Enable statistics logs (this is a dynamic parameter)
     */
    public void setEnabled(boolean b) {
        isStatisticsEnabled = b;

        Iterator it = componentStat.values().iterator();

        while (it.hasNext()) {
            ((ComponentStatistics) it.next()).setEnabled(b);
        }
    }

    public long getStartTime() {
        return startTime;
    }

    public synchronized void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public synchronized void add(ComponentStatistics stat) {
        if (stat != null)
            componentStat.put(stat.getName(), stat);

    }
    public synchronized Collection getComponentStatistics() {
        return componentStat.values();
    }
    

}