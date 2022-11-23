package com.ercanbeyen.employeemanagementsystem.entity;

import com.ercanbeyen.employeemanagementsystem.entity.enums.Currency;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@Entity
public class Salary extends BaseEntity {

    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Min(0)
    private Double amount;

    @Enumerated(EnumType.STRING)
    private Currency currency;
}
