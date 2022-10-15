package com.ercanbeyen.springbootfirstrestapi.entity;

import com.ercanbeyen.springbootfirstrestapi.entity.enums.Gender;
import com.ercanbeyen.springbootfirstrestapi.entity.enums.Salary;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;


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
    @JoinColumn(name = "occupation_id", referencedColumnName = "id")
    private Occupation occupation;

    public void setOccupation(Occupation occupation) { // Override Occupation's set method
        if (this.occupation == null) {
            this.occupation = occupation;
        }
        else {
            this.occupation.setDepartment(occupation.getDepartment());
            this.occupation.setRole(occupation.getRole());
        }
    }

    @Column(name = "STATUS")
    private boolean status = false;

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
    private Gender gender;

    @Column(name = "ADDRESS")
    private String address;
}
