package com.ercanbeyen.springbootfirstrestapi.dto;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class UserDto {
    //@NotNull(message = "First name should not be null")
    @NotBlank(message = "First name should not be blank")
    private String firstName;

    //@NotNull(message = "Last name should not be null")
    @NotBlank(message = "Last name should not be blank")
    private String lastName;

    @Email(message = "Invalid email address")
    private String email;

    private String nationality;

    private String job;

    @Min(0)
    @Max(4)
    private double gpa;
}
