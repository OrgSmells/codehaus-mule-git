/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.email;

import org.mule.umo.lifecycle.InitialisationException;
import org.mule.umo.security.TlsIndirectKeyStore;
import org.mule.umo.security.TlsIndirectTrustStore;
import org.mule.umo.security.tls.TlsConfiguration;

import java.io.IOException;

/**
 * Creates a secure connection to a POP3 mailbox
 */
public abstract class TlsRetrievalMailConnector 
extends RetrieveMailConnector implements TlsIndirectTrustStore, TlsIndirectKeyStore
{

    private String namespace;
    private String socketFactory;
    private String socketFactoryFallback = "false";
    private TlsConfiguration tls = new TlsConfiguration(TlsConfiguration.DEFAULT_KEYSTORE);

    protected TlsRetrievalMailConnector(int defaultPort, String namespace, Class defaultSocketFactory)
    {
        super(defaultPort);
        this.namespace = namespace;
        socketFactory = defaultSocketFactory.getName();
    }
    
    protected void doInitialise() throws InitialisationException
    {
        tls.initialise(true, namespace);
        super.doInitialise();
        System.setProperty("mail." + getProtocol() + ".ssl", "true");
        System.setProperty("mail." + getProtocol() + ".socketFactory.class", getSocketFactory());
        System.setProperty("mail." + getProtocol() + ".socketFactory.fallback", getSocketFactoryFallback());
    }

    public String getSocketFactory()
    {
        return socketFactory;
    }

    public void setSocketFactory(String sslSocketFactory)
    {
        this.socketFactory = sslSocketFactory;
    }

    public String getSocketFactoryFallback()
    {
        return socketFactoryFallback;
    }

    public void setSocketFactoryFallback(String socketFactoryFallback)
    {
        this.socketFactoryFallback = socketFactoryFallback;
    }

    public String getTrustStore()
    {
        return tls.getTrustStore();
    }

    public String getTrustStorePassword()
    {
        return tls.getTrustStorePassword();
    }

    public void setTrustStore(String trustStore) throws IOException
    {
        tls.setTrustStore(trustStore);
    }

    public void setTrustStorePassword(String trustStorePassword)
    {
        tls.setTrustStorePassword(trustStorePassword);
    }

    // these were not present before, but could be set implicitly via global proeprties
    // that is no longer true, so i have added them in here

    public String getClientKeyStore()
    {
        return this.tls.getClientKeyStore();
    }

    public String getClientKeyStorePassword()
    {
        return this.tls.getClientKeyStorePassword();
    }

    public String getClientKeyStoreType()
    {
        return this.tls.getClientKeyStoreType();
    }

    public void setClientKeyStore(String name) throws IOException
    {
        this.tls.setClientKeyStore(name);
    }

    public void setClientKeyStorePassword(String clientKeyStorePassword)
    {
        this.tls.setClientKeyStorePassword(clientKeyStorePassword);
    }

    public void setClientKeyStoreType(String clientKeyStoreType)
    {
        this.tls.setClientKeyStoreType(clientKeyStoreType);
    }

}
