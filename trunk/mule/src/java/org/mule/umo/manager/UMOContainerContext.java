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
 *
 */
package org.mule.umo.manager;

import org.mule.umo.lifecycle.Initialisable;
import org.mule.umo.lifecycle.Disposable;

import java.io.Reader;
import java.util.Map;

/**
 * <code>UMOContainerContext</code> defines the contract between Mule and
 * an underlying container such as String or Pico.
 *
 * @author <a href="mailto:ross.mason@cubis.co.uk">Ross Mason</a>
 * @version $Revision$
 */
public interface UMOContainerContext extends Initialisable, Disposable
{
    /**
     * The identifying name of the container.  Note that implementations
     * should provide a default name that users can choose to override
     * The name can be used to reference a container when more than one
     * is registered
     * @param name the identifying name of the container
     */
    public void setName(String name);

    /**
     * Gets the identifying name of the container
     * @return the identifying name of the container
     */
    public String getName();

    /**
     * Queries a component from the underlying container
     * @param key the key fo find the component with.  Its up to the
     * individual implementation to check the type of this key and
     * look up objects accordingly
     * @return The component found in the container
     * @throws ObjectNotFoundException if the component is not found
     */
    public Object getComponent(Object key) throws ObjectNotFoundException;

    /**
     * This method will be called if there is a configuration fragement for the
     * container to use to configure itself. In Mule Xml the fragment is Xml
     * that is embedded in the &lt;configuration&gt; element of the &lt;container-context$gt;
     * element.
     * @param configuration
     * @param doctype the doctype declaration to use for the configuration fragment.
     * can be null if no validation is to be performed or the fragment is not Xml
     * @param encoding the encoding to use in the Xml declaration. Default is UTF-8
     * @throws ContainerException
     */
    public void configure(Reader configuration, String doctype, String encoding) throws ContainerException;
}
