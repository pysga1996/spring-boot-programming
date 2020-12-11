package com.tutorial.soapclient.interceptors;

import com.tutorial.soapclient.constants.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.soap.saaj.SaajSoapMessage;

import javax.xml.soap.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

@Component
public class SoapClientInterceptor implements ClientInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(SoapClientInterceptor.class);

    @Override
    public boolean handleRequest(MessageContext messageContext) throws WebServiceClientException {
        SOAPPart soapPart = this.getSOAPPart(messageContext, MessageType.REQUEST);
        try {
            this.printXML(soapPart);
        } catch (TransformerException e) {
            logger.error("error", e);
        }
        return true;
    }

    @Override
    public boolean handleResponse(MessageContext messageContext) throws WebServiceClientException {
        SOAPPart soapPart = this.getSOAPPart(messageContext, MessageType.RESPONSE);
        try {
            this.printXML(soapPart);
        } catch (TransformerException e) {
            logger.error("error", e);
        }
        return true;
    }

    @Override
    public boolean handleFault(MessageContext messageContext) throws WebServiceClientException {
        logger.info("intercepted a fault.");
        SOAPPart soapPart = this.getSOAPPart(messageContext, MessageType.RESPONSE);
        try {
            SOAPFault soapFault = this.getSOAPFaultDetail(soapPart);
            Detail detail = soapFault.getDetail();
            logger.error(String.format("Error occurred while invoking web service - %s ",
                soapFault.getFaultString()));
            logger.error(String.format("Request id - %s ", detail.getAttribute("id")));
            logger.error(String.format("Error code - %s ",
                detail.getElementsByTagName("code").item(0).getTextContent()));
            logger.error(String.format("Error message - %s ",
                detail.getElementsByTagName("message").item(0).getTextContent()));
            this.printXML(detail);
            throw new RuntimeException(String
                .format("Error occurred while invoking web service - %s ",
                    soapFault.getFaultString()));
        } catch (SOAPException | TransformerException e) {
            logger.error("error", e);
        }
        return true;
    }

    @Override
    public void afterCompletion(MessageContext messageContext, Exception e)
        throws WebServiceClientException {

    }

    private SOAPPart getSOAPPart(MessageContext messageContext, MessageType messageType) {
        WebServiceMessage message;
        switch (messageType) {
            case REQUEST:
                message = messageContext.getRequest();
                break;
            case RESPONSE:
                message = messageContext.getResponse();
                break;
            default:
                throw new RuntimeException("Message Type was not specified!!!");
        }
        SaajSoapMessage saajSoapMessage = (SaajSoapMessage) message;
        SOAPMessage soapMessage = saajSoapMessage.getSaajMessage();
        return soapMessage.getSOAPPart();
    }

    private SOAPFault getSOAPFaultDetail(SOAPPart soapPart) throws SOAPException {
        SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
        SOAPBody soapBody = soapEnvelope.getBody();
        return soapBody.getFault();
    }

    private void printXML(Node node) throws TransformerException {
        StringWriter sw = new StringWriter();
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        transformerFactory.setAttribute("indent-number", 4);
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
        transformer.transform(
            new DOMSource(node), new StreamResult(sw));
        logger.info("\n" + sw.toString());
    }
}
