package com.ercanbeyen.springbootfirstrestapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class ResourceNotAcceptable extends RuntimeException {
    public ResourceNotAcceptable(String message) {
        super(message);
    }
}
