package com.ercanbeyen.employeemanagementsystem.dto.request;

import com.ercanbeyen.employeemanagementsystem.constants.enums.Gender;
import com.ercanbeyen.employeemanagementsystem.entity.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class UpdateEmployeeDetailsRequest {
    @NotBlank(message = "First name should not be blank")
    private String firstName;

    @NotBlank(message = "Last name should not be blank")
    private String lastName;

    @NotBlank(message = "Email address should not be blank")
    @Email(message = "Invalid email address")
    private String email;

    private String contactNumber;

    private String nationality;

    private Address address;

    private Gender gender;
}
