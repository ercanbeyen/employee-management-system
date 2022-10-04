package com.ercanbeyen.springbootfirstrestapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFound.class) // custom class
    public final ResponseEntity<?> handleUserNotFound(Exception exception) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), exception.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class) // general class (used if exception is other than these two)
    public final ResponseEntity<?> handleGeneralException(Exception exception) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), exception.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.EXPECTATION_FAILED);
    }
}
