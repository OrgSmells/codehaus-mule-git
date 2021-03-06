/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.test.integration.providers.jms.oracle.util;

import javax.jms.JMSException;

import oracle.AQ.AQException;
import oracle.AQ.AQQueue;
import oracle.AQ.AQQueueProperty;
import oracle.AQ.AQQueueTable;
import oracle.AQ.AQQueueTableProperty;
import oracle.jms.AQjmsSession;

/**
 * Convenience methods for creating and deleting Oracle queues.  This class uses
 * the native {@code oracle.AQ} package because administrative functionality is not
 * available through the standard JMS package ({@code oracle.jms}).
 *
 * @author <a href="mailto:carlson@hotpop.com">Travis Carlson</a>
 * @see <a href="http://www.lc.leidenuniv.nl/awcourse/oracle/appdev.920/a96587/toc.htm">Oracle9i Application Developer's Guide - Advanced Queueing</a>
 */
public class AQUtil {

    public static void createOrReplaceTextQueue(AQjmsSession session, String schema, String name, boolean multiconsumer) throws AQException, JMSException {
        createOrReplaceQueue(session, schema, name, /*payloadType*/"SYS.AQ$_JMS_TEXT_MESSAGE", multiconsumer);
    }

    public static void createOrReplaceXmlQueue(AQjmsSession session, String schema, String name, boolean multiconsumer) throws AQException, JMSException {
        createOrReplaceQueue(session, schema, name, /*payloadType*/"SYS.XMLTYPE", multiconsumer);
    }

    public static void createOrReplaceQueue(AQjmsSession session, String schema, String name, String payloadType) throws AQException, JMSException {
        createOrReplaceQueue(session, schema, name, payloadType, /*multiconsumer*/false);
    }

    public static void createOrReplaceQueue(AQjmsSession session, String schema, String name, String payloadType, boolean multiconsumer) throws AQException, JMSException {
        dropQueue(session, schema, name, /*force*/true);
        createQueue(session, schema, name, payloadType, multiconsumer);
    }

    public static void createQueue(AQjmsSession session, String schema, String name, String payloadType, boolean multiconsumer) throws AQException, JMSException {
          AQQueueTable table = session.createQueueTable (schema, "queue_" + name, new AQQueueTableProperty(payloadType));
          AQQueueProperty prop = new AQQueueProperty();
          // TODO The setMultiConsumer() is not yet available in this version of the
          // aqapi.jar library.
          //if (multiconsumer) prop.setMultiConsumer(true);

          AQQueue queue = table.createQueue (name, prop);
          queue.start();
       }

    public static void dropQueue(AQjmsSession session, String schema, String name) throws AQException, JMSException {
        dropQueue(session, schema, "queue_" + name, /*force*/false);
    }

    public static void dropQueue(AQjmsSession session, String schema, String name, boolean force) throws AQException, JMSException {
        AQQueueTable queueTable = null;
        try {
             queueTable = session.getQueueTable(schema, "queue_" + name);
         } catch (JMSException e) {
             // The queue does not exist.
             if (force == false) throw e;
         }

         if (queueTable != null) {
             queueTable.drop(/*stop and drop associated queues*/true);
         }
       }
}
