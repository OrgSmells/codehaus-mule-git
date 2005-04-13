/*
 * $Header$
 * $Revision$
 * $Date$
 * ------------------------------------------------------------------------------------------------------
 *
 * Copyright (c) Cubis Limited. All rights reserved.
 * http://www.cubis.co.uk
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.mule.extras.pgp;

import cryptix.pki.KeyBundle;
import org.mule.tck.NamedTestCase;

import java.net.URL;

/**
 * @author ariva
 *
 */
public class PGPKeyRingTestCase extends NamedTestCase {
    private PGPKeyRing keyManager;
    
    /* (non-Javadoc)
     * @see org.mule.tck.NamedTestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
                
        PGPKeyRingImpl keyM=new PGPKeyRingImpl();
        URL url;
        
        url = Thread.currentThread().getContextClassLoader().getResource("./serverPublic.gpg");       
        keyM.setPublicKeyRingFileName(url.getFile());

        url = Thread.currentThread().getContextClassLoader().getResource("./serverPrivate.gpg");       
        keyM.setSecretKeyRingFileName(url.getFile());
        
        keyM.setSecretAliasId("0x6168F39C");
        keyM.setSecretPassphrase("TestingPassphrase");
        keyM.initialise();
        
        keyManager=keyM;
    }
    
    /* (non-Javadoc)
     * @see org.mule.tck.NamedTestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        // TODO Auto-generated method stub
        super.tearDown();
        
        keyManager=null;
    }
    
    public void testClientKey() {
        KeyBundle clientKey=keyManager.getKeyBundle("Mule client <mule_client@mule.com>");
        assertNotNull(clientKey);
    }
    
    public void testServerKey() {
        KeyBundle serverKey=keyManager.getSecretKeyBundle();
        assertNotNull(serverKey);       
    }
}
