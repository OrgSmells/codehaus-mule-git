/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the BSD style
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.bpm;

import org.mule.extras.client.MuleClient;
import org.mule.providers.AbstractServiceEnabledConnector;
import org.mule.util.StringUtils;

/**
 * The BPM provider allows Mule events to initiate and/or advance processes in an
 * external or embedded Business Process Management System (BPMS). It also allows
 * executing processes to generate Mule events.
 */
public class ProcessConnector extends AbstractServiceEnabledConnector {

    /** The underlying BPMS */
    protected BPMS bpms;

    /** This field will be used to correlated messages with processes. */
    protected String processIdField;

    /** If true, a process can only send messages to the outgoing endpoints defined for
     * the local component.  If false, a process can send messages to any endpoint
     * on any component. */
    protected boolean localEndpointsOnly = true;

    /** The global receiver allows an endpoint of type "bpm://*" to receive any incoming
     * message to the BPMS, regardless of the process.  If this false, the process name
     * must be specified for each endpoint, e.g. "bpm://myProcess" and will only receive
     * messages for the specified process. */
    protected boolean allowGlobalReceiver = false;

    public static final String PROPERTY_ENDPOINT = "endpoint";
    public static final String PROPERTY_PROCESS_TYPE = "processType";
    public static final String PROPERTY_PROCESS_ID = "processId";
    public static final String PROPERTY_ACTION = "action";
    public static final String PROPERTY_TRANSITION = "transition";
    public static final String PROPERTY_PROCESS_STARTED = "started";
    public static final String ACTION_START = "start";
    public static final String ACTION_ADVANCE = "advance";
    public static final String ACTION_UPDATE = "update";
    public static final String ACTION_ABORT = "abort";
    public static final String PROCESS_VARIABLE_INCOMING = "incoming";
    public static final String PROCESS_VARIABLE_INCOMING_SOURCE = "incomingSource";
    public static final String PROCESS_VARIABLE_DATA = "data";

    public static final String PROTOCOL = "bpm";
    public static final String GLOBAL_RECEIVER = PROTOCOL + "://*";

    private MuleClient muleClient = null;

    public String getProtocol() {
        return PROTOCOL;
    }

    /**
     * This method looks for a receiver based on the process name and ID.  It searches
     * iteratively from the narrowest scope (match process name and ID) to the widest
     * scope (match neither - global receiver) possible.
     *
     * @return ProcessMessageReceiver or null if no match is found
     */
    public ProcessMessageReceiver lookupReceiver(String processName, Object processId) {
        ProcessMessageReceiver receiver =
            (ProcessMessageReceiver) lookupReceiver(toUrl(processName, processId));
        if (receiver == null) {
            receiver = (ProcessMessageReceiver) lookupReceiver(toUrl(processName, null));
        }
        if (receiver == null) {
            receiver = (ProcessMessageReceiver) lookupReceiver(toUrl(null, null));
        }
        return receiver;
    }

    /**
     * Generate a URL based on the process name and ID such as "bpm://myProcess/2342"
     * If the parameters are missing, and <code>allowGlobalReceiver</code> is true,
     * the GLOBAL_RECEIVER is returned.
     */
    public String toUrl(String processName, Object processId) {
        String url = getProtocol() + "://";
        if (StringUtils.isNotEmpty(processName)) {
            url += processName;
            if (processId != null) {
                url += "/" + processId;
            }
        }
        else if (isAllowGlobalReceiver()) {
            return GLOBAL_RECEIVER;
        }
        else {
            throw new IllegalArgumentException("No valid URL could be created for the given process name and ID.");
        }
        return url;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Getters and Setters
    ////////////////////////////////////////////////////////////////////////////

    public BPMS getBpms() {
        return bpms;
    }

    public void setBpms(BPMS bpms) {
        this.bpms = bpms;
    }

    public MuleClient getMuleClient() {
        return muleClient;
    }

    public boolean isLocalEndpointsOnly() {
        return localEndpointsOnly;
    }

    public void setLocalEndpointsOnly(boolean localEndpointsOnly) {
        this.localEndpointsOnly = localEndpointsOnly;
    }

    public boolean isAllowGlobalReceiver() {
        return allowGlobalReceiver;
    }

    public void setAllowGlobalReceiver(boolean allowGlobalReceiver) {
        this.allowGlobalReceiver = allowGlobalReceiver;
    }

    public String getProcessIdField() {
        return processIdField;
    }

    public void setProcessIdField(String processIdField) {
        this.processIdField = processIdField;
    }
}
