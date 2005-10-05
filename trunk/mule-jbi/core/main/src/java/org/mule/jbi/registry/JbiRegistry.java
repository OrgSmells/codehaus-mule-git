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
*
*/
package org.mule.jbi.registry;

import org.mule.registry.Assembly;
import org.mule.registry.ComponentType;
import org.mule.registry.Library;
import org.mule.registry.RegistryComponent;
import org.mule.registry.RegistryStore;
import org.mule.registry.Unit;
import org.mule.registry.impl.AbstractRegistry;

/**
 * todo document
 *
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */
public class JbiRegistry extends AbstractRegistry {

    public JbiRegistry(RegistryStore store) {
        super(store);
    }

    public RegistryComponent createComponent(String name, ComponentType type) {
        RegistryComponent component = new JbiRegistryComponent(name, type, this);
        return component;
    }

    public Assembly createAssembly(String name) {
        Assembly assembly = new JbiAssembly(this);
        assembly.setName(name);
        return assembly;
    }

    public Unit createUnit(String name) {
        Unit unit = new JbiUnit(this);
        unit.setName(name);
        return unit;
    }

    public Library createLibrary(String name) {
        Library library = new JbiLibrary(this);
        library.setName(name);
        return library;
    }
}
