package org.mule.extras.oracle.jms.transformers;

import java.sql.SQLException;

import javax.jms.JMSException;

import oracle.jms.AdtMessage;
import oracle.xdb.XMLType;

import org.mule.config.i18n.Message;
import org.mule.transformers.AbstractTransformer;
import org.mule.umo.transformer.TransformerException;
import org.mule.extras.oracle.jms.OracleJmsConnector;
import org.w3c.dom.Document;

/**
 * Transformer for use with the Oracle Jms Connector.
 * Expects a JMS message whose payload is Oracle's native XML data type. 
 * Returns the XML as a W3C Document (DOM).
 *
 * @author <a href="mailto:carlson@hotpop.com">Travis Carlson</a>
 * @see XMLMessageToString
 * @see OracleJmsConnector
 * @see <a href="http://www.lc.leidenuniv.nl/awcourse/oracle/appdev.920/a96620/toc.htm">Oracle9i XML Database Developer's Guide - Oracle XML DB</a>
 */
public class XMLMessageToDOM extends AbstractTransformer {

    public XMLMessageToDOM() {
        super();
        registerSourceType(AdtMessage.class);
        registerSourceType(XMLType.class);
        setReturnClass(Document.class);
    }

    /**
     * @param adtMessage - JMS message whose payload is Oracle's native XML data type.
     * @return the XML as a W3C Document (DOM).
     */
    public Object doTransform(Object adtMessage) throws TransformerException {
        Object payload = null;
        Document document = null;

        // The source must be an AdtMessage.
        if (!(adtMessage instanceof AdtMessage)) throw new TransformerException(Message.createStaticMessage("Object to transform must be of type AdtMessage."), this);

        try {
            payload = ((AdtMessage) adtMessage).getAdtPayload();
        } catch (JMSException e) {
            throw new TransformerException(Message.createStaticMessage("Unable to get ADT payload from JMS message."), this, e);
        }

        // The message payload must be an XMLType.
        if (!(payload instanceof XMLType)) throw new TransformerException(Message.createStaticMessage("The message payload must be an XMLType."), this);

        try {
            document = ((XMLType) payload).getDOM();
        } catch (SQLException e) {
            throw new TransformerException(Message.createStaticMessage("Unable to extract DOM from XMLType."), this, e);
        }
        return document;
    }
}