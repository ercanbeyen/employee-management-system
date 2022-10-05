package com.ercanbeyen.springbootfirstrestapi.advice;

import com.ercanbeyen.springbootfirstrestapi.exception.ExceptionResponse;
import com.ercanbeyen.springbootfirstrestapi.exception.UserNotFound;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private String convertField(String field) {
        if (field.contains("EMAIL")) {
            return "Email";
        }
        return field;
    }

    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    protected ResponseEntity<Object> handleConflict(DataIntegrityViolationException ex, WebRequest request) {
        org.hibernate.exception.ConstraintViolationException exDetail =
                (org.hibernate.exception.ConstraintViolationException) ex.getCause();

        String constraintName = exDetail.getConstraintName();
        String errorMessage = convertField(constraintName) + " should be unique!";
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), errorMessage);

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_ACCEPTABLE);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String field = error.getField();
            String defaultMessage = error.getDefaultMessage();
            errors.put(field, defaultMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFound.class) // custom class
    public final ResponseEntity<?> handleUserNotFound(Exception exception) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), exception.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class) // general class (used if exception is other than the handled ones)
    public final ResponseEntity<?> handleGeneralException(Exception exception) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), exception.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.EXPECTATION_FAILED);
    }
}
