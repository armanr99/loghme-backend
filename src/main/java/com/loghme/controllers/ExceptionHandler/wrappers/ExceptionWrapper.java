package com.loghme.controllers.ExceptionHandler.wrappers;

import org.springframework.http.HttpStatus;

public class ExceptionWrapper {
    private String message;
    private int status;

    public ExceptionWrapper(Exception exception, HttpStatus status) {
        this.message = exception.toString();
        this.status = status.value();
    }
}