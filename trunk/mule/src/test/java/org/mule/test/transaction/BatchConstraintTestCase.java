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
package org.mule.test.transaction;

import com.mockobjects.dynamic.Mock;
import org.mule.tck.NamedTestCase;
import org.mule.transaction.constraints.BatchConstraint;
import org.mule.transaction.constraints.ConstraintFilter;
import org.mule.umo.UMOEvent;

/**
 * @author <a href="mailto:ross.mason@cubis.co.uk">Ross Mason</a>
 * @version $Revision$
 */

public class BatchConstraintTestCase extends NamedTestCase
{
    public void testConstraintFilter() throws Exception
    {
        UMOEvent testEvent = (UMOEvent)new Mock(UMOEvent.class).proxy();
        BatchConstraint filter = new BatchConstraint();
        filter.setBatchSize(3);
        assertEquals(3, filter.getBatchSize());
        assertTrue(!filter.accept(testEvent));

        ConstraintFilter clone = (ConstraintFilter)filter.clone();
        assertNotNull(clone);
        assertNotSame(filter, clone);

        assertTrue(!filter.accept(testEvent));
        assertTrue(filter.accept(testEvent));

    }
}
