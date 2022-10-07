package com.ercanbeyen.springbootfirstrestapi.dto;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class EmployeeDto {

    @NotBlank(message = "First name should not be blank")
    private String firstName;

    @NotBlank(message = "Last name should not be blank")
    private String lastName;

    @Email(message = "Invalid email address")
    private String email;

    private boolean isActive = false;

    private String nationality;

    private String department;

    private String position;

    @Min(0)
    @Max(100)
    private double salary;
}
