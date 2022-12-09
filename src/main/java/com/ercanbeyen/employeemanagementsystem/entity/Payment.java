package com.ercanbeyen.employeemanagementsystem.entity;

import com.ercanbeyen.employeemanagementsystem.constants.enums.Currency;
import com.ercanbeyen.employeemanagementsystem.constants.enums.PaymentType;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Payment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;
    private Double amount;
    @Enumerated(EnumType.STRING)
    private Currency currency;
    @Enumerated(EnumType.STRING)
    private PaymentType type;
}
