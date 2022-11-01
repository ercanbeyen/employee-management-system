package com.ercanbeyen.employeemanagementsystem.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Department extends BaseEntity {

    @Id
    @SequenceGenerator(name = "department_seq_gen", sequenceName = "department_gen", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "department_gen")
    private int id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Employee> employees = new ArrayList<>();


    public void removeEmployee(Employee employee) {
        employees.remove(employee);
    }

}
