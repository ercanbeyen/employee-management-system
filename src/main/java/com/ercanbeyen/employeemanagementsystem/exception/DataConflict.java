package com.ercanbeyen.employeemanagementsystem.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DataConflict extends RuntimeException {
    public DataConflict(String message) {
        super(message);
    }
}
