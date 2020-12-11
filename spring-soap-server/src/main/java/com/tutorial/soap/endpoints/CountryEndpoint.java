package com.tutorial.soap.endpoints;

import com.tutorial.soap.constants.WsEndpointNamespace;
import com.tutorial.soap.exceptions.CustomErrorDetail;
import com.tutorial.soap.exceptions.DataException;
import com.tutorial.soap.models.Country;
import com.tutorial.soap.models.GetCountryRequest;
import com.tutorial.soap.models.GetCountryResponse;
import com.tutorial.soap.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/**
 * @author thanhvt
 * @project spring-soap-web-service-demo
 */
@Endpoint
public class CountryEndpoint {

    private final CountryRepository countryRepository;

    @Autowired
    public CountryEndpoint(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @PayloadRoot(namespace = WsEndpointNamespace.COUNTRIES, localPart = "getCountryRequest")
    @ResponsePayload
    public GetCountryResponse getCountry(@RequestPayload GetCountryRequest request) {
        GetCountryResponse response = new GetCountryResponse();
        Country country = countryRepository.findCountry(request.getName());
        if (country == null)
            throw new DataException("country", CustomErrorDetail.COUNTRY_NOT_FOUND, String.valueOf(Math.random()));
        response.setCountry(country);
        return response;
    }
}
