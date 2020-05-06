package com.loghme.controllers.DTOs.responses.Exception;

import org.springframework.http.HttpStatus;

public class ExceptionResponse {
    private String message;
    private int status;

    public ExceptionResponse(Exception exception, HttpStatus status) {
        this.message = exception.toString();
        this.status = status.value();
    }
}
