package com.ercanbeyen.employeemanagementsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class DataNotAcceptable extends RuntimeException {
    public DataNotAcceptable(String message) {
        super(message);
    }
}
