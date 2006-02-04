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
package org.mule.config;

import javax.naming.NamingException;
import java.util.Map;
import java.util.HashMap;

/**
 * todo document
 *
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */
public class NamingExceptionReader implements ExceptionReader {
    public String getMessage(Throwable t) {
        NamingException e = (NamingException)t;
        return e.toString(true);
    }

    public Throwable getCause(Throwable t) {
        NamingException e = (NamingException)t;
        return e.getRootCause();
    }

    public Class getExceptionType() {
        return NamingException.class;
    }

    /**
     * Returns a map of the non-stanard information stored on the exception
     *
     * @param t the exception to extract the information from
     * @return a map of the non-stanard information stored on the exception
     */
    public Map getInfo(Throwable t) {
        NamingException e = (NamingException)t;
        Map info = new HashMap();
        info.put("Remaining Name", e.getRemainingName().toString());
        info.put("Resolved Name", e.getResolvedName().toString());
        return info;
    }
}
