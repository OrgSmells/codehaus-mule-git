/*
 * $Header$ $Revision$ $Date$
 * ------------------------------------------------------------------------------------------------------
 * 
 * Copyright (c) Cubis Limited. All rights reserved. http://www.cubis.co.uk
 * 
 * The software in this package is published under the terms of the BSD style
 * license a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 *  
 */

package org.mule.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.transaction.constraints.ConstraintFilter;
import org.mule.umo.UMOTransactionConfig;
import org.mule.umo.UMOTransactionFactory;
import org.mule.MuleManager;

/**
 * <p/>
 * <code>MuleTransactionConfig</code> defines transaction configuration for
 * a transactional endpoint.
 *
 * @author <a href="mailto:ross.mason@cubis.co.uk">Ross Mason</a>
 * @version $Revision$
 */
public class MuleTransactionConfig implements UMOTransactionConfig
{
    /**
     * logger used by this class
     */
    protected static transient Log logger = LogFactory.getLog(MuleTransactionConfig.class);

    public static final String ACTION_NONE_STRING = "NONE";
    public static final String ACTION_ALWAYS_BEGIN_STRING = "ALWAYS_BEGIN";
    public static final String ACTION_BEGIN_OR_JOIN_STRING = "BEGIN_OR_JOIN";
    public static final String ACTION_ALWAYS_JOIN_STRING = "ALWAYS_JOIN";
    public static final String ACTION_JOIN_IF_POSSIBLE_STRING = "JOIN_IF_POSSIBLE";

    public static final String ACTION_ALWAYS_COMMIT_STRING = "ALWAYS_COMMIT";
    public static final String ACTION_COMMIT_IF_POSSIBLE_STRING = "COMMIT_IF_POSSIBLE";

    private UMOTransactionFactory factory;

    private byte beginAction = ACTION_NONE;

    private ConstraintFilter constraint = null;

    private int timeout;

    public MuleTransactionConfig()
    {
        timeout = MuleManager.getConfiguration().getTransactionTimeout();

    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see org.mule.umo.UMOTransactionConfig#getFactory()
	 */
    public UMOTransactionFactory getFactory()
    {
        return factory;
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see org.mule.umo.UMOTransactionConfig#setFactory(org.mule.umo.UMOTransactionFactory)
	 */
    public void setFactory(UMOTransactionFactory factory)
    {
        if (factory == null)
        {
            throw new IllegalArgumentException("Transaction Factory cannot be null");
        }
        this.factory = factory;
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see org.mule.umo.UMOTransactionConfig#getBeginAction()
	 */
    public byte getBeginAction()
    {
        return beginAction;
    }

    /*
	 * (non-Javadoc)
	 *
	 * @see org.mule.umo.UMOTransactionConfig#setBeginAction(byte)
	 */
    public void setBeginAction(byte action)
    {
        beginAction = action;

    }

    public void setBeginActionAsString(String action)
    {
        if (ACTION_ALWAYS_BEGIN_STRING.equals(action))
        {
            beginAction = ACTION_ALWAYS_BEGIN;
        }
        else if (ACTION_BEGIN_OR_JOIN_STRING.equals(action))
        {
            beginAction = ACTION_BEGIN_OR_JOIN;
        }
        else if (ACTION_ALWAYS_JOIN_STRING.equals(action))
        {
            beginAction = ACTION_ALWAYS_JOIN;
        }
        else if (ACTION_JOIN_IF_POSSIBLE_STRING.equals(action))
        {
            beginAction = ACTION_JOIN_IF_POSSIBLE;
        }
        else if (ACTION_NONE_STRING.equals(action))
        {
            beginAction = ACTION_NONE;
        }
        else
        {
            throw new IllegalArgumentException("Action " + action + " is not recognised as a begin action.");
        }
    }

    public boolean isTransacted()
    {
        if (factory != null) {
            if (!factory.isTransacted()) {
            	return false;
            }
            return beginAction != ACTION_NONE;
        }
        return false;
    }

    public ConstraintFilter getConstraint()
    {
        if(constraint==null) return null;
        try
        {
            return (ConstraintFilter)constraint.clone();
        } catch (CloneNotSupportedException e)
        {
            logger.fatal("Failed to clone ContraintFilter: " + e.getMessage(), e);
            return constraint;
        }
    }

    public void setConstraint(ConstraintFilter constraint)
    {
        this.constraint = constraint;
    }

    public int getTimeout()
    {
        return timeout;
    }

    public void setTimeout(int timeout)
    {
        this.timeout = timeout;
    }
}
