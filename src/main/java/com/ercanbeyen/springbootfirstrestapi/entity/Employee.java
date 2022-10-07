package com.ercanbeyen.springbootfirstrestapi.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "`EMPLOYEE`")
@Data
public class Employee extends BaseEntity {
    @Id
    @SequenceGenerator(name = "employee_seq_gen", sequenceName = "employee_gen", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_seq_gen")
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", length = 100)
    private String firstName;

    @Column(name = "SURNAME", length = 100)
    private String lastName;

    @Column(name = "STATUS")
    private boolean active;

    @Column(name = "EMAIL", unique = true)
    private String email;

    @Column(name = "NATIONALITY")
    private String nationality;

    @Column(name = "DEPARTMENT")
    private String department;

    @Column(name = "POSITION")
    private String position;

    @Column(name = "SALARY")
    private double salary;
}
