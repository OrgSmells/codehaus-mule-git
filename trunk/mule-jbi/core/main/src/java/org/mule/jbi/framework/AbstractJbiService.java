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
 * $Revision$
 * $Date$
 */
package org.mule.jbi.framework;

import org.mule.jbi.JbiContainer;

public abstract class AbstractJbiService {

	protected JbiContainer container;
	
	public AbstractJbiService(JbiContainer container) {
		this.container = container;
	}
	
}
