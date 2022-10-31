package com.ercanbeyen.springbootfirstrestapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
* TODO:
*  Write codes for EmployeeNotFound, DepartmentNotFound, RoleNotFound, and SalaryNotFound entities
*  Replace all ResourceNotFound messages with the specialized entities
*  Refactor ApiExceptionHandler with adding the specialized entities
* */

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFound extends RuntimeException {
    public ResourceNotFound(String message) {
        super(message);
    }
}
