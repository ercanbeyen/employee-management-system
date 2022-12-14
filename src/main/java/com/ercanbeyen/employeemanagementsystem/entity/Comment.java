package com.ercanbeyen.employeemanagementsystem.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String text;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ticketId", referencedColumnName = "id")
    private Ticket ticket;
}
