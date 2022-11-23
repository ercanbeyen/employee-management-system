package com.ercanbeyen.employeemanagementsystem.entity;

import com.ercanbeyen.employeemanagementsystem.entity.enums.Gender;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


@Data
@Entity
public class Employee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 100)
    private String firstName;

    @Column(length = 100)
    private String lastName;

    @Column(unique = true)
    private String email;

    @Column(length = 20)
    private String phoneNumber;

    private String nationality;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String address;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "departmentId", referencedColumnName = "id")
    private Department department;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "jobTitleId", referencedColumnName = "id")
    private JobTitle jobTitle;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "salaryId", referencedColumnName = "id")
    private Salary salary;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "photoId", referencedColumnName = "id")
    private Image photo;
}
