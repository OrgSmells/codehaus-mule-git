/*
 * $Id
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the BSD style
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
*/
package org.mule.config.builders;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.digester.ObjectCreateRule;
import org.mule.config.ConfigurationException;
import org.mule.config.i18n.Message;
import org.mule.config.i18n.Messages;
import org.mule.impl.container.ContainerKeyPair;
import org.mule.umo.manager.UMOContainerContext;
import org.xml.sax.Attributes;

import java.lang.reflect.InvocationTargetException;

/**
 * A digester rule that will either create an object of look it up from
 * a container.
 *
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */
public class ObjectGetOrCreateRule extends ObjectCreateRule
{
    public static final String DEFAULT_REF_ATTRIBUTE = "ref";
    public static final String DEFAULT_CLASSNAME_ATTRIBUTE = "className";
    protected String refAttrib = DEFAULT_REF_ATTRIBUTE;
    protected String classAttrib = DEFAULT_CLASSNAME_ATTRIBUTE;
    protected boolean classRefRequired = false;
    protected String containerMethodName;
    protected UMOContainerContext context;
    protected String containerAttrib;

    public ObjectGetOrCreateRule(String defaultImpl, String className, String containerMethodName) {
        this(defaultImpl, className, DEFAULT_REF_ATTRIBUTE, false, containerMethodName);
    }

    public ObjectGetOrCreateRule(String defaultImpl, String className, boolean classRefRequired, String containerMethodName) {
        this(defaultImpl, className, DEFAULT_REF_ATTRIBUTE, classRefRequired, containerMethodName);
    }

    public ObjectGetOrCreateRule(String defaultImpl, String className, String refAttrib,
                                 boolean classRefRequired, String containerMethodName) {
        super(defaultImpl, className);
        this.refAttrib = refAttrib;
        this.classRefRequired = classRefRequired;
        this.containerMethodName = containerMethodName;
    }

     public ObjectGetOrCreateRule(String defaultImpl, String className, String refAttrib,
                                 String classAttrib, boolean classRefRequired, String containerMethodName) {
        super(defaultImpl, className);
        this.refAttrib = refAttrib;
        this.classRefRequired = classRefRequired;
        this.containerMethodName = containerMethodName;
        this.classAttrib = classAttrib;
    }

    public ObjectGetOrCreateRule(String defaultImpl, String className, String refAttrib, String containerAttrib,
                                     String classAttrib, boolean classRefRequired, String containerMethodName) {
            super(defaultImpl, className);
            this.refAttrib = refAttrib;
            this.containerAttrib = containerAttrib;
            this.classRefRequired = classRefRequired;
            this.containerMethodName = containerMethodName;
            this.classAttrib = classAttrib;
        }


    /**
     * This method is deprecated in the Digester API however the API still uses it and we must overload it in order to
     * customse the ObjectCreateRuleBehaviour
     * @param attributes
     * @throws Exception
     */
    public void begin(Attributes attributes) throws Exception
    {

        String ref = attributes.getValue(refAttrib);
        String container = null;
        if(containerAttrib!=null) {
            container = attributes.getValue(containerAttrib);
        }
        if(ref!=null) {
            Object cRef = ref;
            if(container!=null) {
                cRef = new ContainerKeyPair(container, ref);
            }
            Object obj = getContainer().getComponent(cRef);
            digester.push(obj);
        } else {
            String classRef = attributes.getValue(classAttrib);
            if(classRef==null && classRefRequired) {
                throw new ConfigurationException(new Message(
                        Messages.MUST_SPECIFY_REF_ATTRIB_X_OR_CLASS_ATTRIB_X_FOR_X, refAttrib, classAttrib, this.digester.getCurrentElementName()));
            } else {
                super.begin(attributes);
            }
        }
    }

    protected UMOContainerContext getContainer() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if(context==null) {
            Object root = digester.getRoot();
            context = (UMOContainerContext)MethodUtils.invokeMethod(root, containerMethodName, null);
            if(context==null) {
                throw new NullPointerException(new Message(Messages.X_IS_NULL, "Container context").toString());
            }
        }
        return context;
    }
}
