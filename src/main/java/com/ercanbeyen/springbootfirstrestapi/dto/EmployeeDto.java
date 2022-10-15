package com.ercanbeyen.springbootfirstrestapi.dto;

import com.ercanbeyen.springbootfirstrestapi.entity.Occupation;
import com.ercanbeyen.springbootfirstrestapi.entity.enums.Gender;
import com.ercanbeyen.springbootfirstrestapi.entity.enums.Salary;
import lombok.Data;

import javax.validation.constraints.*;

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

    private boolean status = true;

    private String nationality;

    private Gender gender;

    private Occupation occupation;

    private Salary salary;
}
