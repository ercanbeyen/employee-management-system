package com.ercanbeyen.springbootfirstrestapi.entity;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
public class Job extends BaseEntity {

    @SequenceGenerator(name = "job_seq_gen", sequenceName = "job_geb", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "job_seq_gen")
    @Id
    private int id;

    @Column(name = "DEPARTMENT")
    private String department;

    @Column(name = "ROLE")
    private String role;

    @Column(name = "LEVEL")
    private String level = "Beginner";
}
