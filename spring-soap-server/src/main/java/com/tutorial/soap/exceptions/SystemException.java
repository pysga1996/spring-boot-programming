package com.tutorial.soap.exceptions;

public class SystemException extends CustomException {

    public static final String LOCAL_PART = "system";

    public static final String NAMESPACE_URI = "{" + NAMESPACE + "}" + LOCAL_PART;

    private static final String TITLE = "SYSTEM ERROR";

    public SystemException(String detailLocalPart, CustomErrorDetail customErrorDetail, String requestId) {
        super(TITLE, detailLocalPart, customErrorDetail, requestId);
    }
}
