/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.routing.outbound;

import org.mule.api.MessagingException;
import org.mule.api.MuleMessage;
import org.mule.api.MuleSession;
import org.mule.api.routing.OutboundRouter;
import org.mule.api.routing.OutboundRouterCollection;
import org.mule.api.routing.RoutingException;
import org.mule.api.transaction.TransactionCallback;
import org.mule.management.stats.RouterStatistics;
import org.mule.routing.AbstractRouterCollection;
import org.mule.transaction.TransactionTemplate;

import java.util.Iterator;

/**
 * <code>DefaultOutboundRouterCollection</code> is a container of routers. An
 * DefaultOutboundRouterCollection must have atleast one router. By default the first matching
 * router is used to route an event though it is possible to match on all routers
 * meaning that the message will get sent over all matching routers.
 */

public class DefaultOutboundRouterCollection extends AbstractRouterCollection implements OutboundRouterCollection
{

    public DefaultOutboundRouterCollection()
    {
        super(RouterStatistics.TYPE_OUTBOUND);
    }

    public MuleMessage route(final MuleMessage message, final MuleSession session, final boolean synchronous)
            throws MessagingException
    {
        MuleMessage result;
        boolean matchfound = false;

        for (Iterator iterator = getRouters().iterator(); iterator.hasNext();)
        {
            OutboundRouter umoOutboundRouter = (OutboundRouter) iterator.next();
            if (umoOutboundRouter.isMatch(message))
            {
                matchfound = true;
                // Manage outbound only transactions here
                final OutboundRouter router = umoOutboundRouter;

                
                TransactionTemplate tt = new TransactionTemplate(umoOutboundRouter.getTransactionConfig(),
                    session.getService().getExceptionListener(), muleContext);
                
                TransactionCallback cb = new TransactionCallback()
                {
                    public Object doInTransaction() throws Exception
                    {
                        return router.route(message, session, synchronous);
                    }
                };
                try
                {
                    result = (MuleMessage) tt.execute(cb);
                }
                catch (Exception e)
                {
                    throw new RoutingException(message, null, e);
                }

                if (!isMatchAll())
                {
                    return result;
                }
            }
        }

        if (!matchfound && getCatchAllStrategy() != null)
        {
            if (logger.isDebugEnabled())
            {
                logger.debug("Message did not match any routers on: "
                        + session.getService().getName()
                        + " invoking catch all strategy");
            }
            return catchAll(message, session, synchronous);
        }
        else if (!matchfound)
        {
            logger.warn("Message did not match any routers on: "
                    + session.getService().getName()
                    + " and there is no catch all strategy configured on this router.  Disposing message " + message);
        }
        return message;
    }

    protected MuleMessage catchAll(MuleMessage message, MuleSession session, boolean synchronous)
            throws RoutingException
    {
        if (getStatistics().isEnabled())
        {
            getStatistics().incrementCaughtMessage();
        }

        return getCatchAllStrategy().catchMessage(message, session, synchronous);
    }

    public boolean hasEndpoints()
    {
        for (Iterator iterator = routers.iterator(); iterator.hasNext();)
        {
            OutboundRouter router = (OutboundRouter) iterator.next();
            if (router.getEndpoints().size() > 0 || router.isDynamicEndpoints())
            {
                return true;
            }
        }
        return false;
    }

}
