package com.ercanbeyen.springbootfirstrestapi.dto;

import com.ercanbeyen.springbootfirstrestapi.entity.Job;
import com.ercanbeyen.springbootfirstrestapi.entity.Salary;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.Currency;

@Data
public class EmployeeDto {

    @NotBlank(message = "First name should not be blank")
    private String firstName;

    @NotBlank(message = "Last name should not be blank")
    private String lastName;

    @NotBlank(message = "Email address should not be blank")
    @Email(message = "Invalid email address")
    private String email;

    private String contactNumber;

    private boolean isActive = false;

    private String nationality;

    private String gender;

    private Job job;

    private Salary salary;
}
