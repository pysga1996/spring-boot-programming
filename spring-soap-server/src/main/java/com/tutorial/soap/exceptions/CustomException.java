package com.tutorial.soap.exceptions;

//@SoapFault(faultCode = FaultCode.SERVER,
//        customFaultCode = "{" + WsEndpointNamespace.EXCEPTION + "}serviceGeneralFault",
//        faultStringOrReason = "Service Error")
public class CustomException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String code;

    private final String message;

    private final String requestId;

    private final String detailPrefix;

    public static final String NAMESPACE = "http://www.vengeance.net/tutorial/exception";

    public static final String DETAIL_NAMESPACE = NAMESPACE + "/detail";

    public static final String LOCAL_PART = "";

    public static final String NAMESPACE_URI = "{" + NAMESPACE + "}" + LOCAL_PART;

    public CustomException(String title, String detailPrefix, CustomErrorDetail customErrorDetail, String requestId) {
        super(title);
        this.detailPrefix = detailPrefix;
        this.code = customErrorDetail.getCode();
        this.message = customErrorDetail.getMessage();
        this.requestId = requestId;
    }

    public String getCode() {
        return code;
    }

    public String getDetailMessage() {
        return message;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getDetailPrefix() {
        return detailPrefix;
    }
}
