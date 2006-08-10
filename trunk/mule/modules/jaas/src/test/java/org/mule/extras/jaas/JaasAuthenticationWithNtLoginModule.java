/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the BSD style
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.extras.jaas;

import java.util.HashMap;
import java.util.Map;
import org.mule.MuleManager;
import org.mule.config.MuleProperties;
import org.mule.extras.client.MuleClient;
import org.mule.impl.security.MuleCredentials;
import org.mule.tck.FunctionalTestCase;
import org.mule.umo.UMOEncryptionStrategy;
import org.mule.umo.UMOMessage;

public class JaasAuthenticationWithNtLoginModule extends FunctionalTestCase {
	
	public JaasAuthenticationWithNtLoginModule() {
		super();
		this.setDisposeManagerPerSuite(true);
	}

	public void testCaseAuthentication() throws Exception{
	    MuleClient client = new MuleClient();
	    
		Map props = new HashMap();
        UMOEncryptionStrategy strategy = MuleManager.getInstance().getSecurityManager().getEncryptionStrategy("PBE");
        String header=  MuleCredentials.createHeader("Marie.Rizzo", "dragon", "PBE", strategy);
        props.put(MuleProperties.MULE_USER_PROPERTY, header);     
        UMOMessage m = client.send("vm://localhost/test","Test", props);
        
	    assertNotNull(m);
	    assertTrue(m.getPayload() instanceof String);
	    assertTrue(m.getPayloadAsString().equals("Test Received"));
	}
	
				    
	protected String getConfigResources() {
		return "mule-conf-with-NTLoginModule.xml";
	} 
}