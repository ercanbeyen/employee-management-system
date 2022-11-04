package com.ercanbeyen.employeemanagementsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class DataForbidden extends RuntimeException {

    public DataForbidden(String message) {
        super(message);
    }
}
