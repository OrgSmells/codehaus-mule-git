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
package org.mule.test.filters;

import org.mule.routing.filters.WildcardFilter;
import org.mule.routing.filters.logic.AndFilter;
import org.mule.routing.filters.logic.NotFilter;
import org.mule.routing.filters.logic.OrFilter;
import org.mule.tck.NamedTestCase;

/**
 * @author <a href="mailto:ross.mason@cubis.co.uk">Ross Mason</a>
 * @version $Revision$
 */

public class LogicFiltersTestCase extends NamedTestCase
{
    public void testAndFilter()
    {
        WildcardFilter left = new WildcardFilter("blah.blah.*");
        WildcardFilter right = new WildcardFilter("blah.*");
        AndFilter filter = new AndFilter(left, right);
        assertNotNull(filter.getLeftFilter());
        assertNotNull(filter.getRightFilter());

        assertTrue(filter.accept("blah.blah.blah"));
        assertTrue(right.accept("blah.blah"));
        assertTrue(!left.accept("blah.blah"));
        assertTrue(!filter.accept("blah.blah"));

        filter = new AndFilter();
        filter.setLeftFilter(left);
        filter.setRightFilter(right);

        assertTrue(filter.accept("blah.blah.blah"));
        assertTrue(!filter.accept("blah.blah"));
    }

    public void testOrFilter()
    {
        WildcardFilter left = new WildcardFilter("blah.blah.*");
        WildcardFilter right = new WildcardFilter("blah.b*");
        OrFilter filter = new OrFilter(left, right);
        assertNotNull(filter.getLeftFilter());
        assertNotNull(filter.getRightFilter());

        assertTrue(filter.accept("blah.blah.blah"));
        assertTrue(right.accept("blah.blah"));
        assertTrue(!left.accept("blah.blah"));
        assertTrue(filter.accept("blah.blah"));
        assertTrue(!filter.accept("blah.x.blah"));

        filter = new OrFilter();
        filter.setLeftFilter(left);
        filter.setRightFilter(right);

        assertTrue(filter.accept("blah.blah.blah"));
        assertTrue(filter.accept("blah.blah"));
        assertTrue(!filter.accept("blah.x.blah"));
    }

    public void testNotFilter()
    {
        WildcardFilter filter = new WildcardFilter("blah.blah.*");
        NotFilter notFilter = new NotFilter(filter);
        assertNotNull(notFilter.getFilter());

        assertTrue(filter.accept("blah.blah.blah"));
        assertTrue(!notFilter.accept("blah.blah.blah"));

        notFilter = new NotFilter();
        notFilter.setFilter(filter);
        assertTrue(filter.accept("blah.blah.blah"));
        assertTrue(!notFilter.accept("blah.blah.blah"));
    }
}
