/*
 * $Id:JotmTxThreadAssociationTestCase.java 8215 2007-09-05 16:56:51Z aperepel $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.extras.jotm;

import org.mule.api.transaction.TransactionManagerFactory;
import org.mule.tck.AbstractTxThreadAssociationTestCase;

public class JotmTxThreadAssociationTestCase extends AbstractTxThreadAssociationTestCase
{

    protected TransactionManagerFactory getTransactionManagerFactory()
    {
        return new JotmTransactionManagerFactory();
    }
}