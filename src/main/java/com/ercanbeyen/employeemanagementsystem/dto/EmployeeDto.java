
package com.ercanbeyen.employeemanagementsystem.dto;

import com.ercanbeyen.employeemanagementsystem.entity.enums.Gender;
import com.ercanbeyen.employeemanagementsystem.entity.Salary;
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

    private String nationality;

    private Gender gender;

    private String department;

    private String jobTitle;

    private Salary salary;

    private String photoFile = null;
}
