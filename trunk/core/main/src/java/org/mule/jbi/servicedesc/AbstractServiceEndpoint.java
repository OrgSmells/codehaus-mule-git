/*
 * Copyright 2005 SymphonySoft Limited. All rights reserved.
 * http://www.symphonysoft.com
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * ------------------------------------------------------------------------------------------------------
 * $Header$
 * $Revision: 3 $
 * $Date: 2005-11-19 17:43:12 +0100 (Sat, 19 Nov 2005) $
 */
package org.mule.jbi.servicedesc;

import org.w3c.dom.DocumentFragment;

import javax.jbi.servicedesc.ServiceEndpoint;
import javax.xml.namespace.QName;

/**
 * 
 * @author <a href="mailto:gnt@codehaus.org">Guillaume Nodet</a>
 */
public abstract class AbstractServiceEndpoint implements ServiceEndpoint {

	protected String component;
	protected boolean active;
	
	public DocumentFragment getAsReference(QName operationName) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isActive() {
		return this.active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getComponent() {
		return this.component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

}
