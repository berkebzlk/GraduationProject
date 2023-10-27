package com.berkebzlk.GraduationProject.exception;

import org.springframework.http.HttpStatus;

public class HotelAPIException extends RuntimeException {
    private HttpStatus httpStatus;
    private String message;

    public HotelAPIException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HotelAPIException(String message, HttpStatus httpStatus, String message1) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message1;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
