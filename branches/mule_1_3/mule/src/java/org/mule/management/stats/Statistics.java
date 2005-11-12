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
package org.mule.management.stats;

import java.io.Serializable;

/**
 * <code>Statistics</code> todo
 * 
 * @author <a href="mailto:S.Vanmeerhaege@gfdi.be">Vanmeerhaeghe St�phane</a>
 * @version $Revision$
 */
public interface Statistics extends Serializable
{
    /**
     * Are statistics logged
     */
    boolean isEnabled();

    /**
     * Enable statistics logs (this is a dynamic parameter)
     */
    void setEnabled(boolean b);

    void clear();

    void logSummary();
}
