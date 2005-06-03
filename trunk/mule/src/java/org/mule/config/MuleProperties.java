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
package org.mule.config;

/**
 * <code>MuleProperties</code> is a set of constants pertaining to Mule system
 * properties.
 * 
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */

public interface MuleProperties
{
    /**
     * The prefix for any Mule-specific properties set in the system properties
     */
    String PROPERTY_PREFIX = "MULE_";

    /** used to store the component name to invoke on a remote server */
    String COMPONENT_NAME_PROPERTY = "org.mule.destination.component";
    String REGISTER_COMPONENT_PROPERTY = "org.mule.register.component";
    String DISABLE_SERVER_CONNECTIONS = "org.mule.disable.server.connections";

    // Generic Event properties
    String MULE_EVENT_PROPERTY = PROPERTY_PREFIX + "EVENT";
    String MULE_EVENT_TIMEOUT_PROPERTY = PROPERTY_PREFIX + "EVENT_TIMEOUT";
    String MULE_METHOD_PROPERTY = PROPERTY_PREFIX + "METHOD";
    String MULE_ENDPOINT_PROPERTY = PROPERTY_PREFIX + "ENDPOINT";
    String MULE_ERROR_CODE_PROPERTY = PROPERTY_PREFIX + "ERROR_CODE";
    String MULE_REPLY_TO_PROPERTY = PROPERTY_PREFIX + "REPLYTO";
    String MULE_USER_PROPERTY = PROPERTY_PREFIX + "USER";
    String MULE_REPLY_TO_REQUESTOR_PROPERTY = PROPERTY_PREFIX + "REPLYTO_REQUESTOR";
    String MULE_MESSAGE_ID_PROPERTY = PROPERTY_PREFIX + "MESSAGE_ID";
    String MULE_CORRELATION_ID_PROPERTY = PROPERTY_PREFIX + "CORRELATION_ID";
    String MULE_CORRELATION_GROUP_SIZE_PROPERTY = PROPERTY_PREFIX + "CORRELATION_GROUP";
    String MULE_CORRELATION_SEQUENCE_PROPERTY = PROPERTY_PREFIX + "CORRELATION_SEQUENCE";
    String MULE_SYNCHRONOUS_RECEIVE_PROPERTY = PROPERTY_PREFIX + "SYNC_RECEIVE";
    // End Generic connector Event properties

    // Connector Service descriptor properties
    String CONNECTOR_CLASS = "connector";
    String CONNECTOR_MESSAGE_RECEIVER_CLASS = "message.receiver";
    String CONNECTOR_FACTORY = "connector.factory";
    String CONNECTOR_DISPATCHER_FACTORY = "dispatcher.factory";
    String CONNECTOR_TRANSACTION_FACTORY = "transaction.factory";
    String CONNECTOR_MESSAGE_ADAPTER = "message.adapter";
    String CONNECTOR_INBOUND_TRANSFORMER = "inbound.transformer";
    String CONNECTOR_OUTBOUND_TRANSFORMER = "outbound.transformer";
    String CONNECTOR_RESPONSE_TRANSFORMER = "response.transformer";
    String CONNECTOR_ENDPOINT_BUILDER = "endpoint.builder";
    String CONNECTOR_SERVICE_FINDER = "service.finder";
    String CONNECTOR_SERVICE_ERROR = "service.error";
    // End Connector Service descriptor properties

}
