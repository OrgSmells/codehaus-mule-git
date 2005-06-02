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
package org.mule.providers.soap.axis;

import org.apache.axis.AxisProperties;
import org.apache.axis.constants.Style;
import org.apache.axis.constants.Use;
import org.apache.axis.description.ServiceDesc;
import org.apache.axis.encoding.TypeMappingRegistryImpl;
import org.apache.axis.encoding.ser.BeanDeserializerFactory;
import org.apache.axis.encoding.ser.BeanSerializerFactory;
import org.apache.axis.handlers.soap.SOAPService;
import org.apache.axis.providers.java.RPCProvider;
import org.apache.axis.wsdl.fromJava.Namespaces;
import org.apache.axis.wsdl.fromJava.Types;
import org.mule.config.i18n.Message;
import org.mule.config.i18n.Messages;
import org.mule.impl.MuleDescriptor;
import org.mule.providers.AbstractMessageReceiver;
import org.mule.providers.soap.ServiceProxy;
import org.mule.providers.soap.axis.extensions.MuleProvider;
import org.mule.umo.UMOComponent;
import org.mule.umo.UMOException;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.endpoint.UMOEndpointURI;
import org.mule.umo.lifecycle.InitialisationException;
import org.mule.umo.provider.UMOConnector;
import org.mule.util.ClassHelper;

import javax.xml.namespace.QName;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <code>AxisMessageReceiver</code> is used to register a component as a service
 * with a Axis server.
 *
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */

public class AxisMessageReceiver extends AbstractMessageReceiver
{
    protected AxisConnector connector;
    protected SOAPService service;

    public AxisMessageReceiver(UMOConnector connector, UMOComponent component,
                               UMOEndpoint endpoint) throws InitialisationException
    {
        super(connector, component, endpoint);
        this.connector = (AxisConnector)connector;
        try {
            init();
        } catch (Exception e) {
            throw new InitialisationException(e, this);
        }
    }

    protected void init() throws Exception
    {
        service = new SOAPService( new MuleProvider(connector));
        MuleDescriptor descriptor =(MuleDescriptor)component.getDescriptor();

        service.setEngine( connector.getAxisServer() );

        UMOEndpointURI uri = endpoint.getEndpointURI();
        String serviceName = component.getDescriptor().getName();

        String servicePath = uri.getPath();
        service.setOption(serviceName, this);
        service.setOption(AxisConnector.SERVICE_PROPERTY_SERVCE_PATH, servicePath);
        service.setOption(AxisConnector.SERVICE_PROPERTY_COMPONENT_NAME, serviceName);
        service.setName(serviceName);

        //Add any custom options from the Descriptor config
        Map options = (Map)descriptor.getProperties().get("axisOptions");
        if(options!=null) {
            Map.Entry entry;
            for (Iterator iterator = options.entrySet().iterator(); iterator.hasNext();)
            {
                entry =  (Map.Entry)iterator.next();
                service.setOption(entry.getKey().toString(), entry.getValue());
                logger.debug("Adding Axis option: " + entry);
            }
        }
        //set method names
        Class[] interfaces = ServiceProxy.getInterfacesForComponent(component);
        if (interfaces.length == 0) {
            throw new InitialisationException(new Message(Messages.X_MUST_IMPLEMENT_AN_INTERFACE, serviceName), component);
        }
        //You must supply a class name if you want to restrict methods
        //or specify the 'allowedMethods' property in the axisOptions property
        String methodNames = "*";

        String[] methods = ServiceProxy.getMethodNames(interfaces);
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < methods.length; i++)
        {
            buf.append(methods[i]).append(",");
        }
        String className = interfaces[0].getName();
        methodNames = buf.toString();
        methodNames = methodNames.substring(0, methodNames.length() -1);

        // The namespace of the service.
        String namespace =  Namespaces.makeNamespace( className );

        /* Now we set up the various options for the SOAPService. We set:
         *
         * RPCProvider.OPTION_WSDL_SERVICEPORT
         * In essense, this is our service name
         *
         * RPCProvider.OPTION_CLASSNAME
         * This tells the serverProvider (whether it be an AvalonProvider or just
         * JavaProvider) what class to load via "makeNewServiceObject".
         *
         * RPCProvider.OPTION_SCOPE
         * How long the object loaded via "makeNewServiceObject" will persist -
         * either request, session, or application.  We use the default for now.
         *
         * RPCProvider.OPTION_WSDL_TARGETNAMESPACE
         * A namespace created from the package name of the service.
         *
         * RPCProvider.OPTION_ALLOWEDMETHODS
         * What methods the service can execute on our class.
         *
         * We don't set:
         * RPCProvider.OPTION_WSDL_PORTTYPE
         * RPCProvider.OPTION_WSDL_SERVICEELEMENT
         */
        setOptionIfNotset(service,  RPCProvider.OPTION_WSDL_SERVICEPORT, serviceName);
        setOptionIfNotset(service, RPCProvider.OPTION_CLASSNAME, className );
        setOptionIfNotset(service, RPCProvider.OPTION_SCOPE, "Request");
        setOptionIfNotset(service, RPCProvider.OPTION_WSDL_TARGETNAMESPACE, namespace  );

        // Set the allowed methods, allow all if there are none specified.
        if ( methodNames == null)
        {
            setOptionIfNotset(service, RPCProvider.OPTION_ALLOWEDMETHODS, "*" );
        }
        else
        {
            setOptionIfNotset(service, RPCProvider.OPTION_ALLOWEDMETHODS, methodNames );
        }

        /* Create a service description.  This tells Axis that this
         * service exists and also what it can execute on this service.  It is
         * created with all the options we set above.
         */
        ServiceDesc sd = service.getInitializedServiceDesc(null);
        sd.setName(serviceName);
        sd.setEndpointURL(uri.getAddress() + "/" + serviceName);

        String style = (String)descriptor.getProperties().get("style");
        String use = (String)descriptor.getProperties().get("use");
        String doc = (String)descriptor.getProperties().get("documentation");

        //Note that Axis has specific rules to how these two variables are
        //combined.  This is handled for us
        //Set style: RPC/wrapped/Doc/Message
        if(style!=null) sd.setStyle(Style.getStyle(style));
        //Set use: Endcoded/Literal
        if(use!=null) sd.setUse(Use.getUse(use));
        sd.setDocumentation(doc);

        // Tell Axis to try and be intelligent about serialization.
        TypeMappingRegistryImpl registry = (TypeMappingRegistryImpl)service.getTypeMappingRegistry();
        //TypeMappingImpl tm = (TypeMappingImpl) registry.();
        //Handle complex bean type automatically
        //registry.setDoAutoTypes( true );
        //Axis 1.2 fix to handle autotypes properly
        AxisProperties.setProperty("axis.doAutoTypes", "true");

        //Load any explicitly defined bean types
        List types = (List)descriptor.getProperties().get("beanTypes");
        registerTypes(registry, types);
        //register any beantypes set on the connector for this service
        registerTypes(registry, connector.getBeanTypes());

        service.setName(serviceName);
        service.stop();
        //Add initialisation callback for the Axis service
        descriptor.addInitialisationCallback(new AxisInitialisationCallback(service));
    }

    public void doConnect() throws Exception
    {
        // Tell the axis configuration about our new service.
        connector.getServerProvider().deployService( service.getName(), service );
        connector.registerReceiverWithMuleService(this, endpoint.getEndpointURI());
    }

    public void doDisconnect() throws Exception {
        try {
            doStop();
        } catch (UMOException e) {
            logger.error(e.getMessage(), e);
        }
        //todo: how do you undeploy an Axis service?

        //Unregister the mule part of the service
        connector.unregisterReceiverWithMuleService(this, endpoint.getEndpointURI());
    }


    public void doStart() throws UMOException {
        if(service!=null) service.start();
    }

    public void doStop() throws UMOException {
        if(service!=null) service.stop();
    }

    protected void setOptionIfNotset(SOAPService service, String option, Object value) {
        Object val = service.getOption(option);
        if(val==null) service.setOption(option, value);
    }

    protected void registerTypes(TypeMappingRegistryImpl registry, List types) throws ClassNotFoundException
    {
        if(types!=null) {
            Class clazz;
            for (Iterator iterator = types.iterator(); iterator.hasNext();)
            {
                clazz = ClassHelper.loadClass(iterator.next().toString(), getClass());
                QName xmlType = new QName(
                Namespaces.makeNamespace( clazz.getName() ),
                Types.getLocalNameFromFullName( clazz.getName() ) );

                registry.getDefaultTypeMapping().register(clazz,
                      xmlType,
                      new BeanSerializerFactory(clazz, xmlType),
                      new BeanDeserializerFactory(clazz, xmlType) );
            }
        }
    }

}
