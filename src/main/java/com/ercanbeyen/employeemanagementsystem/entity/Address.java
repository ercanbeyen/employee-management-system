package com.ercanbeyen.employeemanagementsystem.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Data
@Entity
public class Address extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    @NotBlank(message = "Please enter a country")
    private String country;
    @NotBlank(message = "Please enter a city")
    private String city;
    @NotBlank(message = "Please enter a post code")
    private String postCode;
}
