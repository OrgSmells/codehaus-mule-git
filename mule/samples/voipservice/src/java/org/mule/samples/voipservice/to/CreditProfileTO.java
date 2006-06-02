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
package org.mule.samples.voipservice.to;

import java.io.Serializable;

/**
 * @author Binildas Christudas
 */
public class CreditProfileTO implements Serializable {

    public static final int CREDIT_NOT_AUTHORISED = -1;
    public static final int CREDIT_AUTHORISED = 1;
    public static final int CREDIT_LIMIT = 1000;

    private CustomerTO customerTO;
    private int creditScore;
    private int creditAuthorisedStatus;
    private boolean valid;

    public CreditProfileTO() {
        super();
    }

    public CreditProfileTO(CustomerTO customerTO) {
        this.customerTO = customerTO;

    }

    public CustomerTO getCustomer() {
        return customerTO;
    }

    public void setCustomer(CustomerTO customerTO) {
        this.customerTO = customerTO;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(int creditScore) {
        this.creditScore = creditScore;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public int getCreditAuthorisedStatus() {
        return creditAuthorisedStatus;
    }

    public void setCreditAuthorisedStatus(int creditAuthorisedStatus) {
        this.creditAuthorisedStatus = creditAuthorisedStatus;
    }

}
