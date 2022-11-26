package com.ercanbeyen.employeemanagementsystem.advice;

import com.ercanbeyen.employeemanagementsystem.dto.response.ResponseHandler;
import com.ercanbeyen.employeemanagementsystem.exception.*;

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

        String[] fields = new String[] {"Email", "Currency", "Department", "Role", "JobTitle", "Gender"};

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
        return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, errorMessage, null);
    }

    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    protected ResponseEntity<Object> handleConflict(DataIntegrityViolationException ex, WebRequest request) {
        org.hibernate.exception.ConstraintViolationException exDetail =
                (org.hibernate.exception.ConstraintViolationException) ex.getCause();

        String constraintName = exDetail.getConstraintName();
        String errorMessage = convertField(constraintName) + " should be unique!";

        return ResponseHandler.generateResponse(HttpStatus.NOT_ACCEPTABLE, false, errorMessage, null);
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

        return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "Error", errors);
    }

    @ExceptionHandler(DataNotFound.class)
    public final ResponseEntity<?> handleDataNotFound(Exception exception) {
        return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, false, exception.getMessage(), null);
    }

    @ExceptionHandler(DataForbidden.class)
    public final ResponseEntity<?> handleDataForbidden(Exception exception) {
        return ResponseHandler.generateResponse(HttpStatus.FORBIDDEN, false, exception.getMessage(), null);
    }

    @ExceptionHandler(DataConflict.class)
    public final ResponseEntity<?> handleDataConflict(Exception exception) {
        return ResponseHandler.generateResponse(HttpStatus.CONFLICT, false, exception.getMessage(), null);
    }

    @ExceptionHandler(DataNotAcceptable.class)
    public final ResponseEntity<?> handleDataNotAcceptable(Exception exception) {
        return ResponseHandler.generateResponse(HttpStatus.NOT_ACCEPTABLE, false, exception.getMessage(), null);
    }

    @ExceptionHandler(Exception.class) // general class (used if exception is other than the handled ones)
    public final ResponseEntity<?> handleGeneralException(Exception exception) {
        return ResponseHandler.generateResponse(HttpStatus.EXPECTATION_FAILED, false, exception.getMessage(), null);
    }
}
