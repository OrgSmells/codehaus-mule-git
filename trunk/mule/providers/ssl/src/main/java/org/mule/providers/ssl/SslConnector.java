/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the BSD style
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.providers.ssl;

import org.mule.config.i18n.Message;
import org.mule.config.i18n.Messages;
import org.mule.providers.tcp.TcpConnector;
import org.mule.umo.lifecycle.InitialisationException;
import org.mule.umo.security.provider.SecurityProviderFactory;
import org.mule.umo.security.provider.AutoDiscoverySecurityProviderFactory;
import org.mule.umo.security.provider.SecurityProviderInfo;
import org.mule.util.FileUtils;

import javax.net.ssl.KeyManagerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.Provider;
import java.security.Security;

/**
 * <code>TcpConnector</code> can bind or sent to a given tcp port on a given
 * host.
 *
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */
public class SslConnector extends TcpConnector
{
    public static final String DEFAULT_KEYSTORE_TYPE = KeyStore.getDefaultType();

    private SecurityProviderFactory spFactory = new AutoDiscoverySecurityProviderFactory();
    private SecurityProviderInfo spInfo = spFactory.getSecurityProviderInfo();

    private String keyStore = null;
    private String keyPassword = null;
    private String storePassword = null;
    private String keyStoreType = DEFAULT_KEYSTORE_TYPE;
    private String keyManagerAlgorithm = spInfo.getKeyManagerAlgorithm();
    private Provider provider = spFactory.getProvider();
    private String protocolHandler = spInfo.getProtocolHandler();
    private String clientKeyStore = null;
    private String clientKeyStorePassword = null;
    private String trustStore = null;
    private String trustStorePassword = null;
    private boolean explicitTrustStoreOnly = false;

    private KeyManagerFactory keyManagerFactory = null;
    private boolean requireClientAuthentication = true;

    public void doInitialise() throws InitialisationException
    {
        if (getProvider() == null) {
            throw new NullPointerException("The security provider cannot be null");
        }
        if (getKeyStore() != null) {
            if (getKeyPassword() == null) {
                throw new NullPointerException("The Key password cannot be null");
            }
            if (getStorePassword() == null) {
                throw new NullPointerException("The KeyStore password cannot be null");
            }
            if (getKeyManagerAlgorithm() == null) {
                throw new NullPointerException("The Key Manager Algorithm cannot be null");
            }
            if (getKeyStoreType() == null) {
                throw new NullPointerException("The KeyStore type cannot be null");
            }
        }

        if (getKeyStore() != null) {
            KeyStore keystore;
            try {
                Security.addProvider(getProvider());
                // Create keyStore
                keystore = KeyStore.getInstance(keyStoreType);
                InputStream is = FileUtils.loadResource(getKeyStore(), getClass());
                if (is == null) {
                    throw new FileNotFoundException("Failed to load keystore from classpath or local file: "
                            + getKeyStore());
                }
                keystore.load(is, getKeyPassword().toCharArray());
            } catch (Exception e) {
                throw new InitialisationException(new Message(Messages.FAILED_LOAD_X, "KeyStore: " + getKeyStore()),
                                                  e,
                                                  this);
            }
            try {
                // Get key manager
                keyManagerFactory = KeyManagerFactory.getInstance(getKeyManagerAlgorithm());
                // Initialize the KeyManagerFactory to work with our keyStore
                keyManagerFactory.init(keystore, getStorePassword().toCharArray());
            } catch (Exception e) {
                throw new InitialisationException(new Message(Messages.FAILED_LOAD_X, "Key Manager ("
                        + getKeyManagerAlgorithm() + ")"), e, this);
            }
        }

        super.doInitialise();

        if (protocolHandler != null) {
            System.setProperty("java.protocol.handler.pkgs", protocolHandler);
        }
        if (clientKeyStore != null) {
            try {
                String clientPath = FileUtils.getResourcePath(clientKeyStore, getClass());
                System.setProperty("javax.net.ssl.keyStore", clientPath);
                System.setProperty("javax.net.ssl.keyStorePassword", clientKeyStorePassword);

                logger.info("Set Client Key store: javax.net.ssl.keyStore=" + clientPath);
            } catch (IOException e) {
                throw new InitialisationException(new Message(Messages.FAILED_LOAD_X, "Client KeyStore: "
                        + clientKeyStore), e, this);
            }
        }

        if (trustStore != null) {
            System.setProperty("javax.net.ssl.trustStore", getTrustStore());
            System.setProperty("javax.net.ssl.trustStorePassword", getTrustStorePassword());
            logger.debug("Set Trust store: javax.net.ssl.trustStore=" + getTrustStore());
        } else if (!isExplicitTrustStoreOnly()) {
            logger.info("Defaulting trust store to client Key Store");
            trustStore = getClientKeyStore();
            trustStorePassword = getClientKeyStorePassword();
            System.setProperty("javax.net.ssl.trustStore", getTrustStore());
            System.setProperty("javax.net.ssl.trustStorePassword", getTrustStorePassword());
            logger.debug("Set Trust store: javax.net.ssl.trustStore=" + getTrustStore());
        }

    }

    public String getProtocol()
    {
        return "SSL";
    }

    public String getKeyStore()
    {
        return keyStore;
    }

    public void setKeyStore(String keyStore)
    {
        this.keyStore = keyStore;
    }

    public String getKeyPassword()
    {
        return keyPassword;
    }

    public void setKeyPassword(String keyPassword)
    {
        this.keyPassword = keyPassword;
    }

    public String getStorePassword()
    {
        return storePassword;
    }

    public void setStorePassword(String storePassword)
    {
        this.storePassword = storePassword;
    }

    public String getKeyStoreType()
    {
        return keyStoreType;
    }

    public void setKeyStoreType(String keyStoreType)
    {
        this.keyStoreType = keyStoreType;
    }

    public String getKeyManagerAlgorithm()
    {
        return keyManagerAlgorithm;
    }

    public void setKeyManagerAlgorithm(String keyManagerAlgorithm)
    {
        this.keyManagerAlgorithm = keyManagerAlgorithm;
    }

    public boolean isRequireClientAuthentication()
    {
        return requireClientAuthentication;
    }

    public void setRequireClientAuthentication(boolean requireClientAuthentication)
    {
        this.requireClientAuthentication = requireClientAuthentication;
    }

    public KeyManagerFactory getKeyManagerFactory()
    {
        return keyManagerFactory;
    }

    public Provider getProvider()
    {
        return provider;
    }

    public void setProvider(Provider provider)
    {
        this.provider = provider;
    }

    public String getProtocolHandler()
    {
        return protocolHandler;
    }

    public void setProtocolHandler(String protocolHandler)
    {
        this.protocolHandler = protocolHandler;
    }

    public String getClientKeyStore()
    {
        return clientKeyStore;
    }

    public void setClientKeyStore(String clientKeyStore) throws IOException
    {
        this.clientKeyStore = clientKeyStore;
        if (this.clientKeyStore != null) {
            this.clientKeyStore = FileUtils.getResourcePath(clientKeyStore, getClass());
            logger.debug("Normalised clientKeyStore path to: " + getClientKeyStore());
        }
    }

    public String getClientKeyStorePassword()
    {
        return clientKeyStorePassword;
    }

    public void setClientKeyStorePassword(String clientKeyStorePassword)
    {
        this.clientKeyStorePassword = clientKeyStorePassword;
    }

    public String getTrustStore()
    {
        return trustStore;
    }

    public void setTrustStore(String trustStore) throws IOException
    {
        this.trustStore = trustStore;
        if (this.trustStore != null) {
            this.trustStore = FileUtils.getResourcePath(trustStore, getClass());
            logger.debug("Normalised trustStore path to: " + getTrustStore());
        }
    }

    public String getTrustStorePassword()
    {
        return trustStorePassword;
    }

    public void setTrustStorePassword(String trustStorePassword)
    {
        this.trustStorePassword = trustStorePassword;
    }

    public boolean isExplicitTrustStoreOnly()
    {
        return explicitTrustStoreOnly;
    }

    public void setExplicitTrustStoreOnly(boolean explicitTrustStoreOnly)
    {
        this.explicitTrustStoreOnly = explicitTrustStoreOnly;
    }
}
