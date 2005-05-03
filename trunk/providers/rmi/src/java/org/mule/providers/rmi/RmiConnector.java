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
package org.mule.providers.rmi;

import java.net.URL;
import java.util.ArrayList;

import org.mule.providers.AbstractServiceEnabledConnector;
import org.mule.util.ClassHelper;
import org.mule.util.Utility;


/**
 * <code>RmiConnector</code> can bind or sent to a given rmi port on a given host.
 *
 * @author <a href="mailto:fsweng@bass.com.my">fs Weng</a>
 * @version $Revision$
 */
public class RmiConnector extends AbstractServiceEnabledConnector
{
    public static final int DEFAULT_RMI_REGISTRY_PORT = 1099;

    public static final int MSG_PARAM_SERVICE_METHOD_NOT_SET = 1;

    public static final int MSG_PROPERTY_SERVICE_METHOD_PARAM_TYPES_NOT_SET = 2;

    public static final String PROPERTY_RMI_SECURITY_POLICY = "securityPolicy";

    public static final String PROPERTY_RMI_SERVER_CODEBASE = "serverCodebase";

    public static final String PROPERTY_SERVER_CLASS_NAME = "serverClassName";

    public static final String PROPERTY_SERVICE_METHOD_PARAM_TYPES = "methodArgumentTypes";

    public static final String PARAM_SERVICE_METHOD = "method";

    private String securityPolicy = null;

    private String serverCodebase = null;

    private String serverClassName = null;

    private ArrayList methodArgumentTypes = null;

    private Class[] argumentClasses = null;


    public String getProtocol()
    {
        return "RMI";
    }

    /**
     * @return Returns the securityPolicy.
     */
    public String getSecurityPolicy()
    {
        return securityPolicy;
    }


    /**
     * @param path The securityPolicy to set.
     */
    public void setSecurityPolicy(String path)
    {
        //verify securityPolicy existence
        if (path != null) {
			URL url = Utility.getResource(path, RmiConnector.class);
            if (url == null) {
                throw new IllegalArgumentException("Error on initialization, RMI security policy does not exist");
            }
	        this.securityPolicy = url.toString();
        }
    }


    /**
     * Method getServerCodebase
     *
     *
     * @return
     *
     */
    public String getServerCodebase() {
        return (this.serverCodebase);
    }

    /**
     * Method setServerCodebase
     *
     *
     * @param serverCodebase
     *
     */
    public void setServerCodebase(String serverCodebase) {
        this.serverCodebase = serverCodebase;
    }

    /**
     * Method getServerClassName
     *
     *
     * @return
     *
     */
    public String getServerClassName() {
        return (this.serverClassName);
    }

    /**
     * Method setServerClassName
     *
     *
     * @param serverClassName
     *
     */
    public void setServerClassName(String serverClassName) {
        this.serverClassName = serverClassName;
    }

    /**
     * Method getMethodArgumentTypes
     *
     *
     * @return
     *
     */
    public ArrayList getMethodArgumentTypes() {
        return (this.methodArgumentTypes);
    }

    /**
     * Method setMethodArgumentTypes
     *
     *
     * @param methodArgumentTypes
     *
     */
    public void setMethodArgumentTypes(ArrayList methodArgumentTypes) throws ClassNotFoundException{
        this.methodArgumentTypes = methodArgumentTypes;

        //String classNames[] = methodArgumentTypes.split(",");

        Class argumentClasses[] = new Class[methodArgumentTypes.size()];

        for (int i = 0; i < methodArgumentTypes.size() ;i++)
        {
            String className = (String)methodArgumentTypes.get(i);
            argumentClasses[i] = ClassHelper.loadClass(className.trim(), this.getClass());
        }

        setArgumentClasses(argumentClasses);
    }

    /**
     * Method getArgumentClasses
     *
     *
     * @return
     *
     */
    public Class[] getArgumentClasses() {
        return (this.argumentClasses);
    }

    /**
     * Method setArgumentClasses
     *
     *
     * @param argumentClasses
     *
     */
    public void setArgumentClasses(Class[] argumentClasses) {
        this.argumentClasses = argumentClasses;
    }
}
