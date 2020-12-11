package com.tutorial.soap.exceptions;

public class DataException extends CustomException {

    public static final String LOCAL_PART = "data";

    public static final String NAMESPACE_URI = "{" + NAMESPACE + "}" + LOCAL_PART;

    private final static String TITLE = "DATA ERROR";

    public DataException(String detailLocalPart, CustomErrorDetail customErrorDetail, String requestId) {
        super(TITLE, detailLocalPart, customErrorDetail, requestId);
    }
}
