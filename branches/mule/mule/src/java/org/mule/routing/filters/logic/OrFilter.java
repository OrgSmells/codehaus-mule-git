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
package org.mule.routing.filters.logic;

import org.mule.umo.UMOFilter;

/**
 * <code>OrFilter</code> accepts if the leftFilter or rightFilter filter accept
 *
 * @author <a href="mailto:ross.mason@cubis.co.uk">Ross Mason</a>
 * @version $Revision$
 */

public class OrFilter implements UMOFilter
{
    private UMOFilter leftFilter;
    private UMOFilter rightFilter;

    public OrFilter(UMOFilter leftFilter, UMOFilter rightFilder)
    {
        this.leftFilter = leftFilter;
        this.rightFilter = rightFilder;
    }

    public OrFilter()
    {
    }


    public void setLeftFilter(UMOFilter leftFilter)
    {
        this.leftFilter = leftFilter;
    }

    public void setRightFilter(UMOFilter rightFilter)
    {
        this.rightFilter = rightFilter;
    }


    public UMOFilter getLeftFilter()
    {
        return leftFilter;
    }

    public UMOFilter getRightFilter()
    {
        return rightFilter;
    }

    public boolean accept(Object object)
    {
        return leftFilter.accept(object) || rightFilter.accept(object);
    }
}
