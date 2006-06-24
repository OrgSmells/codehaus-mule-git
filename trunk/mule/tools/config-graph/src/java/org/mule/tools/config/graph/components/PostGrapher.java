/*
 * $Id$
 * ------------------------------------------------------------------------------------------------------
 *
 * Copyright (c) SymphonySoft Limited. All rights reserved.
 * http://www.symphonysoft.com
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */

package org.mule.tools.config.graph.components;

import org.mule.tools.config.graph.config.GraphEnvironment;

public interface PostGrapher
{

    public String getStatusTitle();

    public abstract void postGrapher(GraphEnvironment env);

}