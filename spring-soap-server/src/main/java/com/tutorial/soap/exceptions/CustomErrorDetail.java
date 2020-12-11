package com.tutorial.soap.exceptions;

public enum CustomErrorDetail {
    COUNTRY_NOT_FOUND("MOVIE_404", "Movie not found");

    private final String code;

    private final String message;

    CustomErrorDetail(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
