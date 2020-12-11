package com.tutorial.soapclient.clients;

import com.tutorial.soapclient.constants.WsEndpointUri;
import com.tutorial.soapclient.interceptors.SoapClientInterceptor;
import com.tutorial.soapclient.models.GetClassRequest;
import com.tutorial.soapclient.models.GetClassResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;

@Component
public class ClassClient extends WebServiceGatewaySupport {

    private static final Logger logger = LoggerFactory.getLogger(ClassClient.class);

    @Value("${ws.url}")
    private String wsUrl;

    @Autowired
    public ClassClient(Wss4jSecurityInterceptor wss4jSecurityInterceptor,
                       SoapClientInterceptor clientInterceptor, Jaxb2Marshaller marshaller) {
        this.getWebServiceTemplate().setInterceptors(new ClientInterceptor[]{
                wss4jSecurityInterceptor, clientInterceptor});
        this.setDefaultUri(wsUrl);
        this.setMarshaller(marshaller);
        this.setUnmarshaller(marshaller);
    }

    public GetClassResponse getClazz() {
        GetClassRequest request = new GetClassRequest();
        return (GetClassResponse) getWebServiceTemplate()
                .marshalSendAndReceive(wsUrl, request);
    }
}
