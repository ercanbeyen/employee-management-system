package com.ercanbeyen.springbootfirstrestapi.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "UserTable")
@Data // adds all the setters, getters ... etc. If you want to add them separately, you may user @Getter, @Setter annotations
public class User extends BaseEntity {
    @Id
    @SequenceGenerator(name = "user_seq_gen", sequenceName = "user_gen", initialValue = 100, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq_gen")
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME", length = 100)
    private String firstName;
    @Column(name = "SURNAME", length = 100)
    private String lastName;
}
