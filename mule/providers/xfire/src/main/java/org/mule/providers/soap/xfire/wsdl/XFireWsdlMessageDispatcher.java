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

package org.mule.providers.soap.xfire.wsdl;

import org.apache.commons.pool.impl.StackObjectPool;
import org.codehaus.xfire.XFire;
import org.codehaus.xfire.client.Client;
import org.codehaus.xfire.service.Service;
import org.mule.providers.soap.xfire.XFireMessageDispatcher;
import org.mule.umo.endpoint.UMOImmutableEndpoint;

import javax.xml.namespace.QName;

import java.net.URL;

/**
 * TODO document
 * 
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */
public class XFireWsdlMessageDispatcher extends XFireMessageDispatcher
{

    public XFireWsdlMessageDispatcher(UMOImmutableEndpoint endpoint)
    {
        super(endpoint);
    }

    protected void doConnect(final UMOImmutableEndpoint endpoint) throws Exception
    {
        try {
            XFire xfire = connector.getXfire();
            String wsdlUrl = endpoint.getEndpointURI().getAddress();
            String serviceName = wsdlUrl.substring(0, wsdlUrl.lastIndexOf('?'));
            Service service = xfire.getServiceRegistry().getService(new QName(serviceName));

            if (service == null) {
                service = new Client(new URL(wsdlUrl)).getService();
                service.setName(new QName(serviceName));
                xfire.getServiceRegistry().register(service);
            }

            clientPool = new StackObjectPool(new XFireWsdlClientPoolFactory(endpoint, service, xfire));
            clientPool.addObject();
        }
        catch (Exception ex) {
            disconnect();
            throw ex;
        }
    }
}
