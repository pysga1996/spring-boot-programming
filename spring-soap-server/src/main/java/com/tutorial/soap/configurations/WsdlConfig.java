package com.tutorial.soap.configurations;

import com.tutorial.soap.constants.WsEndpointNamespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.ws.wsdl.wsdl11.Wsdl11Definition;
import org.springframework.xml.xsd.XsdSchema;
import org.springframework.xml.xsd.XsdSchemaCollection;

@Configuration
public class WsdlConfig {

    private final XsdSchemaCollection xsdSchemaCollection;

    @Autowired
    public WsdlConfig(XsdSchemaCollection xsdSchemaCollection) {
        this.xsdSchemaCollection = xsdSchemaCollection;
    }

    @Bean(name = "tutorials")
    public Wsdl11Definition tutorialsWsdl() {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("VengeancePort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace(WsEndpointNamespace.TUTORIALS);
        wsdl11Definition.setServiceName("VengeanceService");
        wsdl11Definition.setSchemaCollection(xsdSchemaCollection);
        return wsdl11Definition;
    }

//    @Bean(name = "countries")
//    public Wsdl11Definition countriesWsdl(XsdSchema countriesSchema) {
//        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
//        wsdl11Definition.setPortTypeName("CountriesPort");
//        wsdl11Definition.setLocationUri("/ws");
//        wsdl11Definition.setTargetNamespace(WsEndpointNamespace.COUNTRIES);
//        wsdl11Definition.setSchema(countriesSchema);
//        return wsdl11Definition;
//    }
//
//    @Bean(name = "class")
//    public Wsdl11Definition classWsdl(XsdSchema classSchema) {
//        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
//        wsdl11Definition.setPortTypeName("ClassPort");
//        wsdl11Definition.setLocationUri("/ws");
//        wsdl11Definition.setTargetNamespace(WsEndpointNamespace.CLASS);
//        wsdl11Definition.setSchema(classSchema);
//        return wsdl11Definition;
//    }
}
