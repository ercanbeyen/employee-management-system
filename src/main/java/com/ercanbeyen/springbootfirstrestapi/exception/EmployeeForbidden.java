package com.ercanbeyen.springbootfirstrestapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class EmployeeForbidden extends RuntimeException {

    public EmployeeForbidden(String message) {
        super(message);
    }
}
