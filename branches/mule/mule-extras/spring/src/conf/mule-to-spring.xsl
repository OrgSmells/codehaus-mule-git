<?xml version="1.0" encoding="ISO-8859-1"?>

<xsl:stylesheet
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:java="http://xml.apache.org/xslt/java"
    extension-element-prefixes="java"
    version='1.0'>

    <xsl:output method="xml" indent="yes" encoding="ISO-8859-1" standalone="yes"
        doctype-public="-//SPRING//DTD BEAN//EN"
        doctype-system="http://www.springframework.org/dtd/spring-beans.dtd"/>

    <xsl:template match="mule-configuration">
        <xsl:variable name="firstContext" select="java:org.mule.extras.spring.config.MuleBeanDefinitionReader.isFirstContext()"/>
        <beans>
            <xsl:if test="$firstContext">
                <bean id="muleManager" class="org.mule.extras.spring.config.AutowireUMOManagerFactoryBean"/>
                <bean id="muleNameProcessor" class="org.mule.extras.spring.config.MuleObjectNameProcessor"/>
            </xsl:if>
            <xsl:apply-templates/>
        </beans>
    </xsl:template>


    <xsl:template match="*">
        <xsl:copy>
            <xsl:copy-of select="attribute::*"/>
            <xsl:apply-templates/>
        </xsl:copy>
    </xsl:template>

    <xsl:template match="mule-environment-properties">
        <bean name="muleConfiguratrion" class="org.mule.config.MuleConfiguration">
            <property name="model">
                <value>
                    <xsl:value-of select="@model"/>
                </value>
            </property>
            <property name="recoverableMode">
                <value>
                    <xsl:value-of select="@recoverableMode"/>
                </value>
            </property>
            <property name="synchronous">
                <value>
                    <xsl:value-of select="@synchronous"/>
                </value>
            </property>
            <property name="synchronousReceive">
                <value>
                    <xsl:value-of select="@synchronousReceive"/>
                </value>
            </property>
            <property name="workingDirectory">
                <value>
                    <xsl:value-of select="@workingDirectory"/>
                </value>
            </property>
            <property name="serverUrl">
                <value>
                    <xsl:value-of select="@serverUrl"/>
                </value>
            </property>

            <xsl:apply-templates select="threading-profile" mode="global"/>
            <xsl:apply-templates select="queue-profile"/>
            <xsl:apply-templates select="pooling-profile"/>
        </bean>
    </xsl:template>

    <xsl:template match="environment-properties">
        <bean name="muleEnvironmentProperties" class="java.util.HashMap">
            <constructor-arg>
                <map>
                    <xsl:apply-templates select="property" mode="mapProperty"/>
                    <xsl:apply-templates select="system-property" mode="mapSystemProperty"/>
                    <xsl:apply-templates select="factory-property" mode="mapFactoryProperty"/>
                </map>
            </constructor-arg>
        </bean>
    </xsl:template>

    <!-- Connector Template -->
    <xsl:template match="connector">
        <xsl:variable name="type">
            <xsl:choose>
                <xsl:when test="@className">
                    <xsl:value-of select="@className"/>
                </xsl:when>
            </xsl:choose>
        </xsl:variable>
        <xsl:variable name="name">
            <xsl:choose>
                <xsl:when test="@name">
                    <xsl:value-of select="@name"/>
                </xsl:when>
            </xsl:choose>
        </xsl:variable>

        <bean name="{$name}" class="{$type}">
            <xsl:apply-templates select="properties"/>
            <xsl:apply-templates select="exception-strategy"/>
            <xsl:apply-templates select="threading-profile" mode="global"/>
        </bean>
    </xsl:template>

    <!-- Transaction manager Template -->
    <xsl:template match="transaction-manager">
        <bean class="{@factory}">
            <xsl:apply-templates select="properties"/>
        </bean>
    </xsl:template>

    <!-- Agent Template -->
    <xsl:template match="agents">
        <xsl:apply-templates select="agent"/>
    </xsl:template>
    <xsl:template match="agent">
        <bean name="{@name}" class="{@className}">
            <xsl:apply-templates select="properties"/>
        </bean>
    </xsl:template>

    <xsl:template match="endpoint-identifiers">
        <bean name="muleEndpointIdentifiers" class="java.util.HashMap">
            <constructor-arg>
                <map>
                    <xsl:apply-templates select="endpoint-identifier"/>
                </map>
            </constructor-arg>
        </bean>
    </xsl:template>

    <!-- endpoint Identifier Template -->
    <xsl:template match="endpoint-identifier">
        <entry key="{@name}">
            <value>
                <xsl:value-of select="@value"/>
            </value>
        </entry>
    </xsl:template>

    <!-- Global Endpoints -->
    <xsl:template match="global-endpoints">
        <xsl:apply-templates select="endpoint"/>
    </xsl:template>

    <!--transformer Template -->
    <xsl:template match="transformers">
        <xsl:apply-templates select="transformer"/>
    </xsl:template>
    <xsl:template match="transformer">
        <xsl:variable name="name">
            <xsl:value-of select="@name"/>
        </xsl:variable>
        <xsl:variable name="type">
            <xsl:value-of select="@className"/>
        </xsl:variable>
        <bean name="{$name}" class="{$type}">
            <xsl:apply-templates select="@returnClass" mode="addProperties"/>
            <xsl:apply-templates select="properties"/>
        </bean>
    </xsl:template>


    <!-- Endpoint Template -->
    <xsl:template match="endpoint|global-endpoint">
        <xsl:variable name="name">
            <xsl:choose>
                <xsl:when test="@name">
                    <xsl:value-of select="@name"/>
                </xsl:when>
                <xsl:otherwise>endpoint</xsl:otherwise>
            </xsl:choose>
        </xsl:variable>

        <bean name="{$name}" class="org.mule.impl.endpoint.MuleEndpoint">
            <xsl:apply-templates select="@transformers" mode="addTransformers"/>
            <xsl:apply-templates select="@address" mode="addEndpointURI"/>
            <xsl:apply-templates select="@*[local-name() != 'address' and local-name() != 'transformers']" mode="addProperties"/>
            <xsl:apply-templates select="properties" mode="asMap"/>
            <xsl:apply-templates select="transaction"/>
            <xsl:apply-templates select="filter"/>
        </bean>
    </xsl:template>

    <xsl:template match="endpoint" mode="propertyEndpoint">
        <property name="endpoint">
            <xsl:apply-templates select="."/>
        </property>
    </xsl:template>

    <xsl:template match="global-endpoint" mode="propertyEndpoint">
        <property name="endpoint">
            <xsl:apply-templates select="."/>
        </property>
    </xsl:template>

    <xsl:template match="interceptor-stack">
        <bean name="muleInterceptorStacks" class="java.util.HashMap">
            <constructor-arg>
                <map>
                    <entry key="{@name}">
                        <bean class="java.util.ArrayList">
                            <constructor-arg>
                                <list>
                                    <xsl:apply-templates select="interceptor"/>
                                </list>
                            </constructor-arg>
                        </bean>
                    </entry>
                </map>
            </constructor-arg>
        </bean>
    </xsl:template>

    <xsl:template match="interceptor">
        <bean class="{@className}"/>
    </xsl:template>

    <xsl:template match="model">
        <xsl:variable name="type">
            <xsl:choose>
                <xsl:when test="@className">
                    <xsl:value-of select="@className"/>
                </xsl:when>
                <xsl:otherwise>org.mule.impl.MuleModel</xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <bean name="{@name}" class="{$type}">
            <xsl:apply-templates select="bean" mode="asProperty"/>
            <!--xsl:apply-templates select="component-lifecycle-adapter-factory"/>
            <xsl:apply-templates select="exception-strategy"/>
            <xsl:apply-templates select="entry-point-resolver"/>
            <xsl:apply-templates select="mule-descriptor"/-->
        </bean>
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="entry-point-resolver">
        <bean name="entryPointResolver" class="{@className}"/>
    </xsl:template>

    <xsl:template match="component-lifecycle-adapter-factory">
        <bean name="componentLifecycleAdapterFactory" class="{@className}"/>
    </xsl:template>

    <xsl:template match="model/exception-strategy">
        <bean name="muleModelExceptionStrategy" class="{@className}">
            <xsl:apply-templates select="properties"/>
            <xsl:apply-templates select="endpoint" mode="propertyEndpoint"/>
            <xsl:apply-templates select="global-endpoint" mode="propertyEndpoint"/>
        </bean>
    </xsl:template>

    <xsl:template match="exception-strategy">
        <property name="exceptionStrategy">
            <bean class="{@className}">
                <xsl:apply-templates select="properties"/>
                <xsl:apply-templates select="endpoint" mode="propertyEndpoint"/>
                <xsl:apply-templates select="global-endpoint" mode="propertyEndpoint"/>
            </bean>
        </property>
    </xsl:template>

    <xsl:template match="mule-descriptor">
        <xsl:variable name="type">
            <xsl:choose>
                <xsl:when test="@className">
                    <xsl:value-of select="@className"/>
                </xsl:when>
                <xsl:otherwise>org.mule.impl.MuleDescriptor</xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <bean name="{@name}" class="{$type}">
            <property name="implementation">
                <value>
                    <xsl:value-of select="@implementation"/>
                </value>
            </property>
            <xsl:if test="@containerManaged">
                <property name="containerManaged">
                    <value>
                        <xsl:value-of select="@containerManaged"/>
                    </value>
                </property>
            </xsl:if>
            <xsl:if test="@version">
                <property name="version">
                    <value>
                        <xsl:value-of select="@version"/>
                    </value>
                </property>
            </xsl:if>

            <xsl:apply-templates select="@inboundEndpoint" mode="addEndpointURI"/>
            <xsl:apply-templates select="@outboundEndpoint" mode="addEndpointURI"/>
            <xsl:apply-templates select="@inboundTransformer" mode="addTransformers"/>
            <xsl:apply-templates select="@outboundTransformer" mode="addTransformers"/>
            <xsl:apply-templates select="properties" mode="asMap"/>
            <xsl:apply-templates select="inbound-router"/>
            <xsl:apply-templates select="response-router"/>
            <xsl:apply-templates select="outbound-router"/>
            <xsl:apply-templates select="exception-strategy"/>

            <xsl:apply-templates select="threading-profile"/>
            <xsl:apply-templates select="queue-profile"/>
            <xsl:apply-templates select="pooling-profile"/>
            <xsl:apply-templates select="bean" mode="asProperty"/>
        </bean>
    </xsl:template>

    <!-- filter Template -->
    <xsl:template match="constraint|filter|left-filter|right-filter">
        <xsl:variable name="name">
            <xsl:choose>
                <xsl:when test="local-name()='left-filter'">leftFilter</xsl:when>
                <xsl:otherwise>
                    <xsl:choose>
                        <xsl:when test="local-name()='right-filter'">rightFilter</xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="local-name()"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:otherwise>
            </xsl:choose>

        </xsl:variable>

        <property name="{$name}">
            <bean class="{@className}">
                <xsl:apply-templates select="@*" mode="addProperties"/>
                <xsl:apply-templates select="properties"/>
                <xsl:apply-templates select="left-filter"/>
                <xsl:apply-templates select="right-filter"/>
                <xsl:apply-templates select="filter"/>
            </bean>
        </property>
    </xsl:template>

    <!-- inbound router Template -->
    <xsl:template match="inbound-router">
        <xsl:variable name="type">
            <xsl:choose>
                <xsl:when test="@className">
                    <xsl:value-of select="@className"/>
                </xsl:when>
                <xsl:otherwise>org.mule.routing.inbound.InboundMessageRouter</xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <property name="inboundRouter">
            <bean class="{$type}">
                <property name="endpoints">
                    <list>
                        <xsl:apply-templates select="endpoint"/>
                        <xsl:apply-templates select="global-endpoint"/>
                    </list>
                </property>
                <property name="routers">
                    <list>
                        <xsl:apply-templates select="router" mode="inbound"/>
                    </list>
                </property>
                <xsl:apply-templates select="catch-all-strategy"/>
            </bean>
        </property>
    </xsl:template>

    <!-- Response router Template -->
    <xsl:template match="response-router">
        <xsl:variable name="type">
            <xsl:choose>
                <xsl:when test="@className">
                    <xsl:value-of select="@className"/>
                </xsl:when>
                <xsl:otherwise>org.mule.routing.response.ResponseMessageRouter</xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <property name="responseRouter">
            <bean class="{$type}">
                <xsl:apply-templates select="@transformers" mode="addTransformers"/>
                <property name="endpoints">
                    <list>
                        <xsl:apply-templates select="endpoint"/>
                        <xsl:apply-templates select="global-endpoint"/>
                    </list>
                </property>
                <property name="routers">
                    <list>
                        <xsl:apply-templates select="router" mode="inbound"/>
                    </list>
                </property>
                <xsl:apply-templates select="catch-all-strategy"/>
            </bean>
        </property>
    </xsl:template>


    <!-- Outbound router Template -->
    <xsl:template match="outbound-router">
        <xsl:variable name="type">
            <xsl:choose>
                <xsl:when test="@className">
                    <xsl:value-of select="@className"/>
                </xsl:when>
                <xsl:otherwise>org.mule.routing.outbound.OutboundMessageRouter</xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <property name="outboundRouter">
            <bean class="{$type}">
                <xsl:apply-templates select="@*" mode="addProperties"/>
                <xsl:apply-templates select="catch-all-strategy"/>
                <property name="routers">
                    <list>
                        <xsl:apply-templates select="router"/>
                    </list>
                </property>
            </bean>
        </property>
    </xsl:template>

    <!-- Catch all strategy Template -->
    <xsl:template match="catch-all-strategy">
        <property name="catchAllStrategy">
            <bean class="{@className}">
                <xsl:apply-templates select="endpoint" mode="propertyEndpoint"/>
                <xsl:apply-templates select="global-endpoint" mode="propertyEndpoint"/>
                <xsl:apply-templates select="properties"/>
            </bean>
        </property>
    </xsl:template>

    <!-- Global Endpoint Template -->
    <xsl:template match="global-endpoint">
        <bean class="org.mule.impl.endpoint.MuleEndpoint"
            factory-method="getEndpointFromUri">
            <constructor-arg index="0">
                <value>
                    <xsl:value-of select="@name"/>
                </value>
            </constructor-arg>
            <xsl:apply-templates select="@transformers" mode="addTransformers"/>
            <xsl:apply-templates select="@address" mode="addEndpointURI"/>
        </bean>
    </xsl:template>

    <!-- Router Template -->
    <xsl:template match="router">
        <bean class="{@className}">
            <property name="endpoints">
                <list>
                    <xsl:apply-templates select="endpoint"/>
                    <xsl:apply-templates select="global-endpoint"/>
                </list>
            </property>
            <xsl:apply-templates select="properties"/>
            <xsl:apply-templates select="filter"/>
        </bean>
    </xsl:template>

    <xsl:template match="router" mode="inbound">
        <bean class="{@className}">
            <xsl:apply-templates select="properties"/>
            <xsl:apply-templates select="filter"/>
        </bean>
    </xsl:template>

    <!-- Transaction Template -->
    <xsl:template match="transaction">
        <property name="transactionConfig">
            <bean class="org.mule.impl.MuleTransactionConfig">
                <xsl:if test="@beginAction">
                    <property name="beginActionAsString">
                        <value>
                            <xsl:value-of select="@beginAction"/>
                        </value>
                    </property>
                </xsl:if>
                <xsl:if test="@commitAction">
                    <property name="commitActionAsString">
                        <value>
                            <xsl:value-of select="@commitAction"/>
                        </value>
                    </property>
                </xsl:if>
                <xsl:if test="@factory">
                    <property name="factory">
                        <bean class="{@factory}"/>
                    </property>
                </xsl:if>
                <xsl:apply-templates select="constraint"/>

            </bean>
        </property>
    </xsl:template>

    <!-- Threading Profile -->
    <xsl:template match="threading-profile" mode="global">
        <xsl:variable name="propertyName">
            <xsl:choose>
                <xsl:when test="@id='dispatcher'">dispatcherThreadingProfile</xsl:when>
                <xsl:otherwise>
                    <xsl:choose>
                        <xsl:when test="@id='reciever'">recieverThreadingProfile</xsl:when>
                        <xsl:otherwise>
                            <xsl:choose>
                                <xsl:when test="@id='component'">componentThreadingProfile</xsl:when>
                                <xsl:otherwise>defaultThreadingProfile</xsl:otherwise>
                            </xsl:choose>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>

        <property name="{$propertyName}">
            <bean class="org.mule.config.ThreadingProfile">
                <xsl:apply-templates select="@*[local-name() !='id' and local-name() !='poolExhaustedAction']|*" mode="addProperties"/>
                <xsl:if test="@poolExhaustedAction">
                    <property name="poolExhaustedActionString">
                        <value>
                            <xsl:value-of select="@poolExhaustedAction"/>
                        </value>
                    </property>
                </xsl:if>
            </bean>
        </property>

    </xsl:template>

    <xsl:template match="threading-profile">
        <property name="threadingProfile">
            <bean class="org.mule.config.ThreadingProfile">
                <xsl:apply-templates select="@*[local-name() !='id' and local-name() !='poolExhaustedAction']|*" mode="addProperties"/>
                <xsl:if test="@poolExhaustedAction">
                    <property name="poolExhaustedActionString">
                        <value>
                            <xsl:value-of select="@poolExhaustedAction"/>
                        </value>
                    </property>
                </xsl:if>
            </bean>
        </property>
    </xsl:template>

    <xsl:template match="pooling-profile">
        <property name="poolingProfile">
            <bean name="pooling-profile" class="org.mule.config.PoolingProfile">
                <xsl:apply-templates select="@*[local-name() !='initialisationPolicy' and local-name() !='exhaustedAction' and local-name() !='factory']|*" mode="addProperties"/>
                <xsl:if test="@exhaustedActionString">
                    <property name="exhaustedAction">
                        <value>
                            <xsl:value-of select="@exhaustedAction"/>
                        </value>
                    </property>
                </xsl:if>
                <xsl:if test="@initialisationPolicy">
                    <property name="initialisationPolicyString">
                        <value>
                            <xsl:value-of select="@initialisationPolicy"/>
                        </value>
                    </property>
                </xsl:if>
                <xsl:if test="@factory">
                    <property name="poolFactory">
                        <bean class="{@factory}"/>
                    </property>
                </xsl:if>
            </bean>
        </property>
    </xsl:template>

    <xsl:template match="queue-profile">
        <property name="queueProfile">
            <bean name="queue-profile" class="org.mule.config.QueueProfile">
                <xsl:apply-templates select="@*" mode="addProperties"/>
                <xsl:apply-templates select="persistence-strategy"/>
            </bean>
        </property>
    </xsl:template>

    <!-- general utilities -->
    <xsl:template name="makeBean">
        <xsl:param name="defaultType"/>
        <xsl:variable name="type">
            <xsl:choose>
                <xsl:when test="@className">
                    <xsl:value-of select="@className"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="$defaultType"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>

        <bean name="{local-name()}" class="{$type}" autowire="constructor">
            <xsl:apply-templates select="@*|*" mode="addProperties"/>
        </bean>
    </xsl:template>


    <xsl:template match="*|@*[local-name() != 'className']" mode="addProperties">
        <property name="{local-name()}">
            <value>
                <xsl:value-of select="."/>
            </value>
        </property>
    </xsl:template>

    <xsl:template match="persistence-strategy">
        <property name="persistenceStrategy">
            <bean class="{@className}">
                <xsl:apply-templates select="properties"/>
            </bean>
        </property>
    </xsl:template>

    <xsl:template match="@address|@inboundEndpoint|@outboundEndpoint" mode="addEndpointURI">
        <xsl:variable name="name">
            <xsl:choose>
                <xsl:when test="local-name()='address'">endpointURI</xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="local-name()"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <property name="{$name}">
            <bean class="org.mule.impl.endpoint.MuleEndpointURI">
                <constructor-arg index="0">
                    <value>
                        <xsl:value-of select="."/>
                    </value>
                </constructor-arg>
            </bean>
        </property>
    </xsl:template>

    <xsl:template match="@transformers|@inboundTransformer|@outboundTransformer" mode="addTransformers">
        <xsl:variable name="propertyName">
            <xsl:choose>
                <xsl:when test="local-name() = 'transformers'">transformer</xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="local-name()"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <property name="{$propertyName}">
            <bean class="org.mule.util.MuleObjectHelper"
                factory-method="getTransformer">
                <constructor-arg index="0">
                    <value>
                        <xsl:value-of select="."/>
                    </value>
                </constructor-arg>
                <constructor-arg index="1">
                    <value> </value>
                </constructor-arg>
            </bean>
        </property>
    </xsl:template>

    <!-- Properties  templates -->
    <!-- currently factory-property is not supported
    Nor is the default value on system-property
    or the required value on container-property
    -->
    <xsl:template match="properties">
        <xsl:apply-templates select="property"/>
        <xsl:apply-templates select="container-property"/>
        <xsl:apply-templates select="system-property"/>
        <xsl:apply-templates select="factory-property"/>
        <xsl:apply-templates select="map"/>
        <xsl:apply-templates select="list"/>
        <xsl:apply-templates select="bean"/>
        <xsl:apply-templates select="spring-property"/>
    </xsl:template>

    <xsl:template match="properties" mode="asMap">
        <property name="properties">
            <map>
                <xsl:apply-templates select="property" mode="mapProperty"/>
                <xsl:apply-templates select="container-property" mode="mapContainerProperty"/>
                <xsl:apply-templates select="system-property" mode="mapSystemProperty"/>
                <xsl:apply-templates select="factory-property" mode="mapFactoryProperty"/>
                <xsl:apply-templates select="map" mode="mapMapProperty"/>
                <xsl:apply-templates select="list" mode="mapListProperty"/>
                <xsl:apply-templates select="bean" mode="asMap"/>
                <xsl:apply-templates select="spring-property" mode="asMap"/>

            </map>
        </property>
    </xsl:template>

    <xsl:template match="property">
        <xsl:if test="@name!='org.mule.useManagerProperties'">
            <property name="{@name}">
                <value>
                    <xsl:value-of select="@value"/>
                </value>
            </property>
        </xsl:if>
    </xsl:template>

    <!-- container properties -->
    <xsl:template match="container-property">
        <property name="{@name}">
            <ref local="{@reference}"/>
        </property>
    </xsl:template>

    <!-- System Properties -->
    <xsl:template match="system-property">
        <property name="{@name}">
            <value>${
                <xsl:value-of select="@value"/>}
            </value>
        </property>
    </xsl:template>

    <!-- Factory Properties -->
    <xsl:template match="factory-property">
        <entry key="{@name}">
            <bean class="{@factory}"/>
        </entry>
    </xsl:template>

    <!-- Map properties -->
    <xsl:template match="map">
        <property name="{@name}">
            <map>
                <xsl:apply-templates select="property" mode="mapProperty"/>
                <xsl:apply-templates select="container-property" mode="mapContainerProperty"/>
                <xsl:apply-templates select="system-property" mode="mapSystemProperty"/>
                <xsl:apply-templates select="factory-property" mode="mapFactoryProperty"/>
            </map>
        </property>
    </xsl:template>

    <xsl:template match="property" mode="mapProperty">
        <entry key="{@name}">
            <value>
                <xsl:value-of select="@value"/>
            </value>
        </entry>
    </xsl:template>

    <!-- container properties -->
    <xsl:template match="container-property" mode="mapContainerProperty">
        <entry key="{@name}">
            <ref local="{@reference}"/>
        </entry>
    </xsl:template>

    <!-- System Properties -->
    <xsl:template match="system-property" mode="mapSystemProperty">
        <entry key="{@name}">
            <value>${
                <xsl:value-of select="@key"/>}
            </value>
        </entry>
    </xsl:template>

    <!-- Factory Properties in a Map -->
    <xsl:template match="factory-property" mode="mapFactoryProperty">
        <entry key="{@name}">
            <bean class="{@factory}"/>
        </entry>
    </xsl:template>

    <!-- List Properties in a Map -->
    <xsl:template match="list" mode="mapListProperty">
        <entry key="{@name}">
            <list>
                <xsl:apply-templates select="entry"/>
                <xsl:apply-templates select="container-entry"/>
                <xsl:apply-templates select="system-entry"/>
                <xsl:apply-templates select="factory-entry"/>
            </list>
        </entry>
    </xsl:template>

    <!-- Map Properties in a Map -->
    <xsl:template match="map" mode="mapMapProperty">
        <entry key="{@name}">
            <map>
                <xsl:apply-templates select="property" mode="mapProperty"/>
                <xsl:apply-templates select="container-property" mode="mapContainerProperty"/>
                <xsl:apply-templates select="system-property" mode="mapSystemProperty"/>
                <xsl:apply-templates select="factory-property" mode="mapFactoryProperty"/>
            </map>
        </entry>
    </xsl:template>

    <!-- List properties -->
    <xsl:template match="list">
        <property name="{@name}">
            <list>
                <xsl:apply-templates select="entry"/>
                <xsl:apply-templates select="container-entry"/>
                <xsl:apply-templates select="system-entry"/>
                <xsl:apply-templates select="factory-entry"/>
            </list>
        </property>
    </xsl:template>

    <xsl:template match="entry">
        <value>
            <xsl:value-of select="@value"/>
        </value>
    </xsl:template>

    <xsl:template match="container-entry">
        <ref local="{@value}"/>
    </xsl:template>

    <!-- Factory Entry -->
    <xsl:template match="factory-entry">
        <bean class="{@factory}"/>
    </xsl:template>

    <xsl:template match="system-entry">
        <value>${
            <xsl:value-of select="@value"/>}
        </value>
    </xsl:template>

    <!--
Templates for processing interleaved bean configuration
    -->

    <xsl:template match="bean" mode="asProperty">
        <xsl:variable name="name">
            <xsl:choose>
                <xsl:when test="@name">
                    <xsl:value-of select="@name"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="@id"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <property name="{$name}">
            <bean name="{$name}" class="{@class}">
                <xsl:apply-templates/>
            </bean>
        </property>
    </xsl:template>

    <xsl:template match="bean" mode="asMap">
        <xsl:variable name="name">
            <xsl:choose>
                <xsl:when test="@name">
                    <xsl:value-of select="@name"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="@id"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <entry key="{$name}">
            <bean name="{$name}" class="{@class}">
                <xsl:apply-templates/>
            </bean>
        </entry>
    </xsl:template>


    <xsl:template match="spring-property">
        <property name="{@name}">
            <xsl:apply-templates/>
        </property>
    </xsl:template>

    <xsl:template match="spring-property" mode="asMap">
        <entry key="{@name}">
            <xsl:apply-templates/>
        </entry>
    </xsl:template>

    <xsl:template match="spring-map">
        <map>
            <xsl:apply-templates/>
        </map>
    </xsl:template>

    <xsl:template match="spring-list">
        <list>
            <xsl:apply-templates/>
        </list>
    </xsl:template>

    <xsl:template match="spring-entry">
        <entry key="{@key}">
            <xsl:apply-templates/>
        </entry>
    </xsl:template>
</xsl:stylesheet>
