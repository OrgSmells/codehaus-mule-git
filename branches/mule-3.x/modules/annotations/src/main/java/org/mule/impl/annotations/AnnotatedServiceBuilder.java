/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.impl.annotations;

import org.mule.api.AnnotationException;
import org.mule.api.EndpointAnnotationParser;
import org.mule.api.MuleContext;
import org.mule.api.MuleException;
import org.mule.api.RouterAnnotationParser;
import org.mule.api.component.JavaComponent;
import org.mule.api.config.MuleProperties;
import org.mule.api.context.MuleContextAware;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.api.endpoint.OutboundEndpoint;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.model.Model;
import org.mule.api.object.ObjectFactory;
import org.mule.api.routing.OutboundRouter;
import org.mule.component.DefaultJavaComponent;
import org.mule.component.PooledJavaComponent;
import org.mule.config.AnnotationsParserFactory;
import org.mule.config.annotations.Service;
import org.mule.config.annotations.endpoints.Bind;
import org.mule.config.annotations.endpoints.Channel;
import org.mule.config.annotations.endpoints.ChannelType;
import org.mule.config.annotations.endpoints.Reply;
import org.mule.config.annotations.i18n.AnnotationsMessages;
import org.mule.config.annotations.routing.Router;
import org.mule.config.annotations.routing.RouterType;
import org.mule.impl.endpoint.AnnotatedEndpointData;
import org.mule.impl.endpoint.AnnotatedEndpointHelper;
import org.mule.impl.endpoint.MEP;
import org.mule.impl.registry.RegistryMap;
import org.mule.model.seda.SedaService;
import org.mule.object.PrototypeObjectFactory;
import org.mule.object.SingletonObjectFactory;
import org.mule.routing.binding.DefaultInterfaceBinding;
import org.mule.routing.outbound.OutboundPassThroughRouter;
import org.mule.util.BeanUtils;
import org.mule.util.StringUtils;
import org.mule.util.TemplateParser;
import org.mule.utils.AnnotationMetaData;
import org.mule.utils.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * This builder will process annotations on an object and create a Mule service using Router, endpoint and service annotation
 * to construct the service.
 *
 * @since 3.0.0
 */
public class AnnotatedServiceBuilder
{
    protected MuleContext context;
    private Model model;
    private final TemplateParser parser = TemplateParser.createAntStyleParser();
    protected RegistryMap regProps;
    protected AnnotatedEndpointHelper helper;
    protected AnnotationsParserFactory parserFactory;

    public Model getModel()
    {
        return model;
    }

    public void setModel(Model model)
    {
        this.model = model;
    }


    public AnnotatedServiceBuilder(MuleContext context) throws MuleException
    {

        this.context = context;
        this.regProps = new RegistryMap(context.getRegistry());
        this.helper = new AnnotatedEndpointHelper(context);
        this.parserFactory = context.getRegistry().lookupObject(AnnotationsParserFactory.class);
        assert parserFactory != null;
    }

    protected ObjectFactory createObjectFactory(Object object)
    {
        Service serv = object.getClass().getAnnotation(Service.class);
        if (serv.scope().equals(ObjectScope.SINGLETON))
        {
            return new SingletonObjectFactory(object);
        }
        else
        {
            Map props = BeanUtils.describe(object);
            return new PrototypeObjectFactory(object.getClass(), props);
        }
    }

    public org.mule.api.service.Service createService(Object object) throws MuleException
    {
        Class componentFactoryClass = object.getClass();


        org.mule.api.service.Service serviceDescriptor = create(createObjectFactory(object));

        processInbound(componentFactoryClass, serviceDescriptor);

        processOutbound(componentFactoryClass, serviceDescriptor);

        //check for Nested bindings
        processEndpointBindings(componentFactoryClass, serviceDescriptor);

        //Check for Async reply Config
        processReply(componentFactoryClass, serviceDescriptor);
        //processAsyncReplyEndpoint(componentFactoryClass, serviceDescriptor);


        return serviceDescriptor;
    }

    protected org.mule.api.service.Service create(ObjectFactory componentFactory) throws InitialisationException
    {
        org.mule.api.service.Service serviceDescriptor = new SedaService();
        Service service = componentFactory.getObjectClass().getAnnotation(Service.class);

        serviceDescriptor.setName(getValue(service.name()));
        componentFactory.initialise();
        if (service.scope().equals(ObjectScope.POOLED))
        {
            serviceDescriptor.setComponent(new PooledJavaComponent(componentFactory));
        }
        else
        {
            serviceDescriptor.setComponent(new DefaultJavaComponent(componentFactory));
        }
        return serviceDescriptor;
    }


    protected String getValue(String key)
    {
        return parser.parse(regProps, key);
    }

    protected void processInbound(Class componentFactoryClass, org.mule.api.service.Service service) throws MuleException
    {

        InboundEndpoint inboundEndpoint;
        List<AnnotationMetaData> annotations = AnnotationUtils.getClassAndMethodAnnotations(componentFactoryClass);
        for (AnnotationMetaData annotation : annotations)
        {
            inboundEndpoint = tryInboundEndpointAnnotation(annotation, ChannelType.Inbound);
            if (inboundEndpoint != null)
            {
                if (annotation.getType() == ElementType.METHOD)
                {
                    inboundEndpoint.getProperties().put(MuleProperties.MULE_METHOD_PROPERTY, annotation.getElementName());
                }
                service.getInboundRouter().addEndpoint(inboundEndpoint);
            }
        }

        //Lets process the inbound routers
        processInboundRouters(componentFactoryClass, service);
    }

    protected void processInboundRouters(Class componentFactoryClass, org.mule.api.service.Service service) throws MuleException
    {
        for (int i = 0; i < componentFactoryClass.getAnnotations().length; i++)
        {
            Annotation annotation = componentFactoryClass.getAnnotations()[i];
            Router routerAnnotation = annotation.annotationType().getAnnotation(Router.class);
            if (routerAnnotation != null && routerAnnotation.type() == RouterType.Inbound)
            {
                RouterAnnotationParser parser = parserFactory.getRouterParser(annotation, componentFactoryClass, null);
                if (parser != null)
                {
                    service.getInboundRouter().addRouter(parser.parseRouter(annotation));
                }
                else
                {
                    //TODO i18n
                    throw new IllegalStateException("Cannot find parser for router annotation: " + annotation.toString());
                }
            }
        }
    }

    protected void processReplyRouters(Class componentFactoryClass, org.mule.api.service.Service service) throws MuleException
    {
        List<AnnotationMetaData> annotations = AnnotationUtils.getClassAndMethodAnnotations(componentFactoryClass);
        for (AnnotationMetaData metaData : annotations)
        {
            Router routerAnnotation = metaData.getAnnotation().annotationType().getAnnotation(Router.class);
            if (routerAnnotation != null && routerAnnotation.type() == RouterType.ReplyTo)
            {


                RouterAnnotationParser parser = parserFactory.getRouterParser(metaData.getAnnotation(), metaData.getClazz(), metaData.getMember());
                if (parser != null)
                {
                    org.mule.api.routing.Router router = parser.parseRouter(metaData.getAnnotation());
                    //Todo, wrap lifecycle
                    if (router instanceof MuleContextAware)
                    {
                        ((MuleContextAware) router).setMuleContext(context);
                    }
                    router.initialise();
                    service.getResponseRouter().addRouter(router);
                    break;
                }
                else
                {
                    //TODO i18n
                    throw new IllegalStateException("Cannot find parser for router annotation: " + metaData.getAnnotation().toString());
                }
            }
        }
    }

    protected OutboundRouter processOutboundRouter(Class componentFactoryClass) throws MuleException
    {
        Collection routerParsers = context.getRegistry().lookupObjects(RouterAnnotationParser.class);
        OutboundRouter router = null;

        List<AnnotationMetaData> annotations = AnnotationUtils.getClassAndMethodAnnotations(componentFactoryClass);
        for (AnnotationMetaData metaData : annotations)
        {
            Router routerAnnotation = metaData.getAnnotation().annotationType().getAnnotation(Router.class);
            if (routerAnnotation != null && routerAnnotation.type() == RouterType.Outbound)
            {
                if (router != null)
                {
                    //TODO i18n
                    throw new IllegalStateException("You can only configure one outbound router on a service");
                }
                RouterAnnotationParser parser = parserFactory.getRouterParser(metaData.getAnnotation(), metaData.getClazz(), metaData.getMember());
                if (parser != null)
                {
                    router = (OutboundRouter) parser.parseRouter(metaData.getAnnotation());
                }
                else
                {
                    //TODO i18n
                    throw new IllegalStateException("Cannot find parser for router annotation: " + metaData.getAnnotation().toString());
                }
            }
        }
        if (router == null)
        {
            router = new OutboundPassThroughRouter();
        }
        //Todo, wrap lifecycle
        if (router instanceof MuleContextAware)
        {
            ((MuleContextAware) router).setMuleContext(context);
        }
        router.initialise();
        return router;
    }

    protected void processOutbound(Class componentFactoryClass, org.mule.api.service.Service service) throws MuleException
    {
        OutboundRouter router = processOutboundRouter(componentFactoryClass);
        Reply replyEp = (Reply) componentFactoryClass.getAnnotation(Reply.class);
        if (replyEp != null)
        {
            router.setReplyTo(replyEp.uri());
        }

        OutboundEndpoint outboundEndpoint;
        List<AnnotationMetaData> annotations = AnnotationUtils.getClassAndMethodAnnotations(componentFactoryClass);
        for (AnnotationMetaData annotation : annotations)
        {
            outboundEndpoint = tryOutboundEndpointAnnotation(annotation, ChannelType.Outbound);
            if (outboundEndpoint != null)
            {
                router.addEndpoint(outboundEndpoint);
            }
        }

        if (router instanceof MuleContextAware)
        {
            ((MuleContextAware) router).setMuleContext(context);
        }
        router.initialise();
        service.getOutboundRouter().addRouter(router);
    }

    protected InboundEndpoint tryInboundEndpointAnnotation(AnnotationMetaData metaData, ChannelType channelType) throws MuleException
    {
        Channel channelAnno = metaData.getAnnotation().annotationType().getAnnotation(Channel.class);
        if (channelAnno != null && channelAnno.type() == channelType)
        {
            EndpointAnnotationParser parser = parserFactory.getEndpointParser(metaData.getAnnotation(), metaData.getClazz(), metaData.getMember());
            if (parser == null)
            {
                //TODO i18n
                throw new AnnotationException(AnnotationsMessages.createStaticMessage("No parser found for annotation: " + metaData));
            }
            else
            {
                return parser.parseInboundEndpoint(metaData.getAnnotation(), Collections.EMPTY_MAP);
            }
        }
        return null;
    }

    protected OutboundEndpoint tryOutboundEndpointAnnotation(AnnotationMetaData metaData, ChannelType channelType) throws MuleException
    {
        Channel channelAnno = metaData.getAnnotation().annotationType().getAnnotation(Channel.class);
        if (channelAnno != null && channelAnno.type() == channelType)
        {
            EndpointAnnotationParser parser = parserFactory.getEndpointParser(metaData.getAnnotation(), metaData.getClazz(), metaData.getMember());
            if (parser == null)
            {
                //TODO i18n
                throw new AnnotationException(AnnotationsMessages.createStaticMessage("No parser found for annotation: " + metaData));
            }
            else
            {
                return parser.parseOutboundEndpoint(metaData.getAnnotation(), Collections.EMPTY_MAP);
            }
        }
        return null;
    }


    protected void processReply(Class componentFactoryClass, org.mule.api.service.Service service) throws MuleException
    {

        InboundEndpoint inboundEndpoint;
        for (int i = 0; i < componentFactoryClass.getAnnotations().length; i++)
        {
            Annotation annotation = componentFactoryClass.getAnnotations()[i];
            inboundEndpoint = tryInboundEndpointAnnotation(
                    new AnnotationMetaData(componentFactoryClass, null, ElementType.TYPE, annotation), ChannelType.Reply);
            if (inboundEndpoint != null)
            {
                service.getResponseRouter().addEndpoint(inboundEndpoint);
            }
        }

        //Lets process the reply routers
        processReplyRouters(componentFactoryClass, service);
    }

    //TODO Move this to an Object processor

    protected void processEndpointBindings(Class componentFactoryClass, org.mule.api.service.Service service) throws MuleException
    {
        Field[] fields = componentFactoryClass.getDeclaredFields();
        for (int i = 0; i < fields.length; i++)
        {
            Field field = fields[i];
            if (field.isAnnotationPresent(Bind.class))
            {
                Bind binding = field.getAnnotation(Bind.class);

                AnnotatedEndpointData epd = new AnnotatedEndpointData(MEP.OutIn, binding);
                epd.setConnectorName(binding.connector());
                epd.setAddress(binding.uri());

                org.mule.api.routing.InterfaceBinding router = new DefaultInterfaceBinding();
                router.setInterface(field.getType());
                if (!StringUtils.isBlank(binding.method()))
                {
                    router.setMethod(getValue(binding.method()));
                    for (int j = 0; j < componentFactoryClass.getMethods().length; j++)
                    {
                        Method m = componentFactoryClass.getMethods()[j];
                        if (m.getName().equals(router.getMethod()))
                        {
                            epd.setMEPUsingMethod(m, false);
                            break;
                        }
                    }
                }
                router.setEndpoint(helper.processEndpoint(epd));
                ((JavaComponent) service.getComponent()).getBindingCollection().addRouter(router);
            }
        }
    }
}