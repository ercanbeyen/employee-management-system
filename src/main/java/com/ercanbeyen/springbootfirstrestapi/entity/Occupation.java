package com.ercanbeyen.springbootfirstrestapi.entity;

import com.ercanbeyen.springbootfirstrestapi.entity.enums.Department;
import com.ercanbeyen.springbootfirstrestapi.entity.enums.Level;
import com.ercanbeyen.springbootfirstrestapi.entity.enums.Role;
import lombok.Data;

import javax.persistence.*;


@Data
@Entity
public class Occupation extends BaseEntity {

    @SequenceGenerator(name = "occupation_seq_gen", sequenceName = "occupation_geb", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "occupation_seq_gen")
    @Id
    private int id;

    @Column(name = "DEPARTMENT")
    private Department department;

    @Column(name = "ROLE")
    private Role role;

    @Column(name = "LEVEL")
    private Level level = Level.BEGINNER;
}
