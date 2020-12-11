package com.tutorial.soap.exceptions;

import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.ws.soap.SoapFault;
import org.springframework.ws.soap.SoapFaultDetail;
import org.springframework.ws.soap.server.endpoint.SoapFaultDefinition;
import org.springframework.ws.soap.server.endpoint.SoapFaultMappingExceptionResolver;

import javax.xml.namespace.QName;
import java.util.Properties;

@Component
public class DetailSoapFaultDefinitionExceptionResolver extends SoapFaultMappingExceptionResolver {

    private static final QName CODE = new QName("code");
    private static final QName MESSAGE = new QName("message");
    private static final QName REQUEST_ID = new QName("requestId");

    public DetailSoapFaultDefinitionExceptionResolver() {
        super();
        SoapFaultDefinition faultDefinition = new SoapFaultDefinition();
        faultDefinition.setFaultCode(SoapFaultDefinition.SERVER);
        this.setDefaultFault(faultDefinition);
        Properties errorMappings = new Properties();
        errorMappings.setProperty(CustomException.class.getName(), CustomException.NAMESPACE_URI);
        errorMappings.setProperty(BusinessException.class.getName(), BusinessException.NAMESPACE_URI);
        errorMappings.setProperty(DataException.class.getName(), DataException.NAMESPACE_URI);
        errorMappings.setProperty(SystemException.class.getName(), SystemException.NAMESPACE_URI);
        this.setExceptionMappings(errorMappings);
        this.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    @Override
    public void customizeFault(Object endpoint, Exception ex, SoapFault fault) {
        logger.warn("Exception processed ", ex);
        if (ex instanceof CustomException) {
            SoapFaultDetail detail = fault.addFaultDetail();
            detail.addFaultDetailElement(CODE).addText(((CustomException) ex).getCode());
            detail.addFaultDetailElement(MESSAGE).addText(((CustomException) ex).getDetailMessage());
            detail.addNamespaceDeclaration(((CustomException) ex).getDetailPrefix(),
                    CustomException.DETAIL_NAMESPACE);
            if (((CustomException) ex).getRequestId() != null) {
                detail.addAttribute(REQUEST_ID, ((CustomException) ex).getRequestId());
            }
        }
    }

}
