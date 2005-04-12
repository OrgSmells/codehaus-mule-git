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
package org.mule.config;

import java.io.Reader;

/**
 * <code>ReaderResource</code> is a reader with a description associated with it.
 * This is useful for error resolution as the reader description can be included when
 * reporting errors during reading.
 *
 * @author <a href="mailto:ross.mason@cubis.co.uk">Ross Mason</a>
 * @version $Revision$
 */
public class ReaderResource {

    private String description;
    private Reader reader;

    public ReaderResource(String description, Reader reader) {
        this.description = description;
        this.reader = reader;
    }

    public String getDescription() {
        return description;
    }

    public Reader getReader() {
        return reader;
    }
}
