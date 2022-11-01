package com.ercanbeyen.employeemanagementsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SalaryNotFound extends RuntimeException {
    public SalaryNotFound(String message) {
        super(message);
    }
}
