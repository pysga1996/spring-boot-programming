package com.tutorial.soap.endpoints;

import com.tutorial.soap.constants.WsEndpointNamespace;
import com.tutorial.soap.constants.WsEndpointUri;
import com.tutorial.soap.models.*;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Endpoint
public class ClassEndpoint {

    @PayloadRoot(namespace = WsEndpointNamespace.CLASS, localPart = "getClassRequest")
    @ResponsePayload
    public GetClassResponse getClazz(@RequestPayload GetClassRequest request) throws DatatypeConfigurationException {
        GetClassResponse response = new GetClassResponse();
        Clazz mathClass = new Clazz();
        Student thanh = new Student();
        thanh.setAge((byte) 25);
        thanh.setGender(true);
        thanh.setId((short) 150);
        thanh.setName("Vũ Tất Thành");
        Student trang = new Student();
        trang.setAge((byte) 29);
        trang.setGender(true);
        trang.setId((short) 395);
        trang.setName("Nguyễn Thu Trang");
        mathClass.setStudentList(new StudentList());
        mathClass.getStudentList().getStudent().addAll(Arrays.asList(thanh, trang));
        mathClass.setName("Math Class");
        Date beginDate = new Date(2016-1900, Calendar.MARCH, 13);
        GregorianCalendar gregory = new GregorianCalendar();
        gregory.setTime(beginDate);
        mathClass.setBeginDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregory));
        response.setClazz(mathClass);
        return response;
    }
}
