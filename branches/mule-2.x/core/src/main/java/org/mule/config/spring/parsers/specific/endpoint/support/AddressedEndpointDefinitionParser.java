/*
 * $Id:AddressedEndpointDefinitionParser.java 8321 2007-09-10 19:22:52Z acooke $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.spring.parsers.specific.endpoint.support;

import org.mule.config.spring.parsers.AbstractMuleBeanDefinitionParser;
import org.mule.config.spring.parsers.MuleChildDefinitionParser;
import org.mule.config.spring.parsers.MuleDefinitionParser;
import org.mule.config.spring.parsers.assembly.PropertyConfiguration;
import org.mule.config.spring.parsers.delegate.AbstractSingleParentFamilyDefinitionParser;
import org.mule.config.spring.parsers.generic.AttributePropertiesDefinitionParser;
import org.mule.config.spring.parsers.processors.BlockAttribute;
import org.mule.config.spring.parsers.processors.CheckExclusiveAttributes;
import org.mule.config.spring.parsers.processors.CheckRequiredAttributes;
import org.mule.config.spring.parsers.specific.URIBuilder;
import org.mule.util.ArrayUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.BeanDefinition;

/**
 * Combine a
 * {@link org.mule.config.spring.parsers.specific.endpoint.support.ChildAddressDefinitionParser} and
 * either a
 * {@link org.mule.config.spring.parsers.specific.endpoint.support.OrphanEndpointDefinitionParser}
 * or a
 * {@link org.mule.config.spring.parsers.specific.endpoint.support.ChildEndpointDefinitionParser}
 * in one parser.  This lets us put the address attributes in the endpoint element.
 */
public class AddressedEndpointDefinitionParser extends AbstractSingleParentFamilyDefinitionParser
{

    protected Log logger = LogFactory.getLog(getClass());
    public static final boolean META = ChildAddressDefinitionParser.META;
    public static final boolean PROTOCOL = ChildAddressDefinitionParser.PROTOCOL;
    public static final String QUERY_MAP = "queryMap";

    // this is an example of parsing a single element with several parsers.  in this case
    // (because we extend SingleParentFamilyDefinitionParser) the first parser is expected to
    // create the "parent".  then subsequent parsers will be called as children.

    // because all are generated from one element we need to be careful to block attributes
    // that are irrelevant to a particular parser.

    public AddressedEndpointDefinitionParser(String protocol, MuleDefinitionParser endpointParser)
    {
        this(protocol, PROTOCOL, endpointParser);
    }

    public AddressedEndpointDefinitionParser(String metaOrProtocol, boolean isMeta, MuleDefinitionParser endpointParser)
    {
        this(metaOrProtocol, isMeta, endpointParser, new String[]{}, new String[]{});
    }

    /**
     * @param metaOrProtocol The transport metaOrProtocol ("tcp" etc)
     * @param isMeta Whether transport is "meta" or not (eg cxf)
     * @param endpointParser The parser for the endpoint
     * @param propertyAttributes A list of attribute names which will be set as properties on the
     * endpointParser
     * @param requiredAddressAttributes A list of attribute names that are required if "address"
     * isn't present
     */
    public AddressedEndpointDefinitionParser(String metaOrProtocol, boolean isMeta,
                                             MuleDefinitionParser endpointParser,
                                             String[] propertyAttributes,
                                             String[] requiredAddressAttributes)
    {
        // the first delegate, the parent, is an endpoint; we block address and property
        // related attributes
        disableAttributes(endpointParser, URIBuilder.ALL_ATTRIBUTES);
        disableAttributes(endpointParser, requiredAddressAttributes);
        disableAttributes(endpointParser, propertyAttributes);
        addDelegate(endpointParser);

        // the next delegate parses the address.  it will see the endpoint as parent automatically.
        MuleChildDefinitionParser addressParser =
                new CompositeAddressDefinitionParser(metaOrProtocol, isMeta,
                        requiredAddressAttributes, propertyAttributes);

        // this handles the exception thrown if a ref is found in the address parser
        addHandledException(BlockAttribute.BlockAttributeException.class);
        addChildDelegate(addressParser);
    }

    /**
     * This class extends the "chain" of child parsers, adding an address parser with a child
     * properties parser.  The final result is three parsers - the endpoint factory parser,
     * whose child is the address parser, whose child is the properties parser.
     */
    private static class CompositeAddressDefinitionParser
            extends AbstractSingleParentFamilyDefinitionParser implements MuleChildDefinitionParser
    {

        private MuleChildDefinitionParser addressParser;

        public CompositeAddressDefinitionParser(String metaOrProtocol, boolean isMeta, String[] requiredAddressAttributes, String[] propertyAttributes)
        {
            // this parses the address.  it will see the endpoint as parent automatically.
            addressParser = new ChildAddressDefinitionParser(metaOrProtocol, isMeta);
            addDelegate(addressParser);

            // the next delegate parses property attributes
            MuleChildDefinitionParser propertiesParser = new AttributePropertiesDefinitionParser(QUERY_MAP);
            propertiesParser.addCollection(QUERY_MAP);

            // this handles the "ref problem" - we don't want this parsers to be used if a "ref"
            // defines the address so add a preprocessor to check for that and indicate that the
            // exception should be handled internally, rather than shown to the user.
            // we do this before the extra processors below so that this is called last,
            // allowing other processors to check for conflicts between ref and other attributes
            addressParser.registerPreProcessor(new BlockAttribute(AbstractMuleBeanDefinitionParser.ATTRIBUTE_REF));
            propertiesParser.registerPreProcessor(new BlockAttribute(AbstractMuleBeanDefinitionParser.ATTRIBUTE_REF));

            // the address parser see only the endpoint attributes
            enableAttributes(addressParser, URIBuilder.ALL_ATTRIBUTES);
            enableAttributes(addressParser, ArrayUtils.setDifference(requiredAddressAttributes, propertyAttributes));
            // we require either a reference, an address, or the attributes specified
            String[][] addressAttributeSets = new String[][]{
                    new String[]{URIBuilder.ADDRESS},
                    new String[]{AbstractMuleBeanDefinitionParser.ATTRIBUTE_REF},
                    requiredAddressAttributes};
            addressParser.registerPreProcessor(new CheckRequiredAttributes(addressAttributeSets));
            // and they must be exclusive
            addressParser.registerPreProcessor(new CheckExclusiveAttributes(addressAttributeSets));

            // the properties parser should only see properties
            enableAttributes(propertiesParser, propertyAttributes);
            // we also enable "ref" so that we can throw an error if present
            enableAttribute(propertiesParser, AbstractMuleBeanDefinitionParser.ATTRIBUTE_REF);
            propertiesParser.registerPreProcessor(new CheckExclusiveAttributes(
                    new String[][]{propertyAttributes, new String[]{AbstractMuleBeanDefinitionParser.ATTRIBUTE_REF}}));
            addChildDelegate(propertiesParser);
        }

        public void forceParent(BeanDefinition parent)
        {
            addressParser.forceParent(parent);
        }

        public PropertyConfiguration getTargetPropertyConfiguration()
        {
            return addressParser.getTargetPropertyConfiguration();
        }

    }

}