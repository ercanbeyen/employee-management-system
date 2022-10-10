package com.ercanbeyen.springbootfirstrestapi.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Comparator;
import java.util.Currency;

@Data
@Entity
public class Salary extends BaseEntity {

    @SequenceGenerator(name = "salary_seq_gen", sequenceName = "salary_gen", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "salary_seq_gen")
    @Id
    private int id;

    @Min(0)
    @Max(100)
    private Double amount;

    private Currency currency;
}
