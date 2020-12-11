package com.tutorial.soap.exceptions;

public class BusinessException extends CustomException {

    public static final String LOCAL_PART = "business";

    public static final String NAMESPACE_URI = "{" + NAMESPACE + "}" + LOCAL_PART;

    private static final String TITLE = "BUSINESS ERROR";

    public BusinessException(String detailLocalPart, CustomErrorDetail customErrorDetail, String requestId) {
        super(TITLE, detailLocalPart, customErrorDetail, requestId);
    }
}
