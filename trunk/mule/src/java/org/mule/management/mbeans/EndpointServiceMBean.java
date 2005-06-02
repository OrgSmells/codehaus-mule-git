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
 *
 */
package org.mule.management.mbeans;

/**
 * The EndpointServiceMBean allows you to check the confiugration of an endpoint and conect/disconnect
 * endpoints manually.
 *
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */
public interface EndpointServiceMBean {

    public String getAddress();

    public String getName();

    public boolean isConnected();

    public void connect() throws Exception;

    public void disconnect() throws Exception;

    public String getType();

    public boolean isSynchronous();
}
