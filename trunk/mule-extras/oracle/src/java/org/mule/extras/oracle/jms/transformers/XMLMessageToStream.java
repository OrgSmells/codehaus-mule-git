package org.mule.extras.oracle.jms.transformers;

import java.io.InputStream;

import javax.jms.JMSException;

import oracle.jms.AdtMessage;
import oracle.xdb.XMLType;

import org.mule.config.i18n.Message;
import org.mule.transformers.AbstractTransformer;
import org.mule.umo.transformer.TransformerException;
import org.mule.extras.oracle.jms.OracleJmsConnector;

/**
 * Transformer for use with the Oracle Jms Connector.
 * Expects a JMS message whose payload is Oracle's native XML data type. 
 * Returns the XML as an InputStream.
 *
 * @author <a href="mailto:carlson@hotpop.com">Travis Carlson</a>
 * @see XMLMessageToString
 * @see OracleJmsConnector
 * @see <a href="http://www.lc.leidenuniv.nl/awcourse/oracle/appdev.920/a96620/toc.htm">Oracle9i XML Database Developer's Guide - Oracle XML DB</a>
 */
public class XMLMessageToStream extends AbstractTransformer {

    public XMLMessageToStream() {
        super();
        registerSourceType(AdtMessage.class);
        registerSourceType(XMLType.class);
        setReturnClass(InputStream.class);
    }

    /**
     * @param adtMessage - JMS message whose payload is Oracle's native XML data type.
     * @return the XML as an InputStream.
     */
    public Object doTransform(Object adtMessage) throws TransformerException {
        Object payload = null;

        // The source must be an AdtMessage.
        if (!(adtMessage instanceof AdtMessage)) throw new TransformerException(Message.createStaticMessage("Object to transform must be of type AdtMessage."), this);

        try {
            payload = ((AdtMessage) adtMessage).getAdtPayload();
        } catch (JMSException e) {
            throw new TransformerException(Message.createStaticMessage("Unable to get ADT payload from JMS message."), this, e);
        }

        // The message payload must be an XMLType.
        if (!(payload instanceof XMLType)) throw new TransformerException(Message.createStaticMessage("The message payload must be an XMLType."), this);

        return ((XMLType) payload).getStream();
    }
}