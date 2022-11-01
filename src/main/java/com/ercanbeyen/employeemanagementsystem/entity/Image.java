package com.ercanbeyen.employeemanagementsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Image extends BaseEntity {

    @Id
    @SequenceGenerator(name = "image_seq_gen", sequenceName = "image_gen", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "image_gen")
    private int id;

    private String name;
    private String type;

    @Lob
    @Column(length = 1000)
    private byte[] data;
}
