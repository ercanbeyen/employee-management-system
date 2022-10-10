package com.ercanbeyen.springbootfirstrestapi.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.util.Comparator;


@Data
@Entity
public class Employee extends BaseEntity {
    @Id
    @SequenceGenerator(name = "employee_seq_gen", sequenceName = "employee_gen", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_seq_gen")
    @Column(name = "ID")
    private int id;

    @Column(name = "NAME", length = 100)
    private String firstName;

    @Column(name = "SURNAME", length = 100)
    private String lastName;

    @Column(name = "EMAIL", unique = true)
    private String email;

    @Setter(AccessLevel.NONE)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "job_id", referencedColumnName = "id")
    private Job job;

    public void setJob(Job job) { // Override Job's set method
        if (this.job == null) {
            this.job = job;
        }
        else {
            this.job.setDepartment(job.getDepartment());
            this.job.setRole(job.getRole());
        }
    }

    @Column(name = "STATUS")
    private boolean active = false;

    @Setter(AccessLevel.NONE)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "salary_id", referencedColumnName = "id")
    private Salary salary;

    public void setSalary(Salary salary) { // Override Salary's set method
        if (this.salary == null) {
            this.salary = salary;
        }
        else {
            this.salary.setAmount(salary.getAmount());
            this.salary.setCurrency(salary.getCurrency());
        }
    }

    @Column(name = "CONTACT_NUMBER")
    private String contactNumber;

    @Column(name = "NATIONALITY")
    private String nationality;

    @Column(name = "GENDER")
    private String gender;

    @Column(name = "ADDRESS")
    private String address;
}
