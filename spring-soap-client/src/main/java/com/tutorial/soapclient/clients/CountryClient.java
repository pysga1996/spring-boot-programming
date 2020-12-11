package com.tutorial.soapclient.clients;

import com.tutorial.soapclient.interceptors.SoapClientInterceptor;
import com.tutorial.soapclient.models.GetCountryRequest;
import com.tutorial.soapclient.models.GetCountryResponse;
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
public class CountryClient extends WebServiceGatewaySupport {

    private static final Logger logger = LoggerFactory.getLogger(CountryClient.class);

    @Value("${ws.url}")
    private String wsUrl;

    @Autowired
    public CountryClient(Wss4jSecurityInterceptor wss4jSecurityInterceptor,
                         SoapClientInterceptor clientInterceptor, Jaxb2Marshaller marshaller) {
        this.getWebServiceTemplate().setInterceptors(new ClientInterceptor[]{
                wss4jSecurityInterceptor, clientInterceptor});
//        this.getWebServiceTemplate().setInterceptors(new ClientInterceptor[]{
//                wss4jSecurityInterceptor});
        this.setDefaultUri(wsUrl);
        this.setMarshaller(marshaller);
        this.setUnmarshaller(marshaller);
    }

    public GetCountryResponse getCountry(String country) {
        GetCountryRequest request = new GetCountryRequest();
        request.setName(country);
        logger.info("Requesting location for " + country);
        return (GetCountryResponse) getWebServiceTemplate()
                .marshalSendAndReceive(wsUrl, request);
    }

}
