package com.ercanbeyen.employeemanagementsystem.entity;

import com.ercanbeyen.employeemanagementsystem.entity.enums.Gender;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


@Data
@Entity
public class Employee implements Serializable {
    @Id
    @SequenceGenerator(name = "employee_seq_gen", sequenceName = "employee_gen", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_seq_gen")
    private int id;

    @Column(length = 100)
    private String firstName;

    @Column(length = 100)
    private String lastName;

    @Column(unique = true)
    private String email;

    private String contactNumber;

    private String nationality;

    private Gender gender;

    private String address;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "departmentId", referencedColumnName = "id")
    private Department department;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "roleId", referencedColumnName = "id")
    private Role role;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "salaryId", referencedColumnName = "id")
    private Salary salary;
}
