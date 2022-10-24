package com.ercanbeyen.springbootfirstrestapi.advice;

import com.ercanbeyen.springbootfirstrestapi.exception.EmployeeForbidden;
import com.ercanbeyen.springbootfirstrestapi.exception.ExceptionResponse;
import com.ercanbeyen.springbootfirstrestapi.exception.ResourceNotAcceptable;
import com.ercanbeyen.springbootfirstrestapi.exception.ResourceNotFound;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private String convertField(String message) {
        String convertedField = message;

        if (StringUtils.equals(message, null)) {
            return "Field is null";
        }

        String[] fields = new String[] {"Email", "Currency", "Department", "Role", "Level", "Gender"};

        for (String field : fields) {
            Pattern pattern = Pattern.compile(field, Pattern.CASE_INSENSITIVE); // configure matching criteria
            Matcher matcher = pattern.matcher(message); // Trying to find match from the message via matcher with obeying criteria

            if (matcher.find()) { // there is a match
                convertedField = field;
                break;
            }
        }

        return convertedField;
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String errorMessage = convertField(ex.getMessage()) + " is invalid";
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), errorMessage);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
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

        ex.getBindingResult().getGlobalErrors().forEach(error -> {
            String objectName = error.getObjectName();
            String defaultMessage = error.getDefaultMessage();
            errors.put(objectName, defaultMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFound.class) // custom class
    public final ResponseEntity<?> handleItemNotFound(Exception exception) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), exception.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmployeeForbidden.class)
    public final ResponseEntity<?> handleEmployeeForbidden(Exception exception) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), exception.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ResourceNotAcceptable.class)
    public final ResponseEntity<?> handleItemNotAcceptable(Exception exception) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), exception.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(Exception.class) // general class (used if exception is other than the handled ones)
    public final ResponseEntity<?> handleGeneralException(Exception exception) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), exception.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.EXPECTATION_FAILED);
    }
}
