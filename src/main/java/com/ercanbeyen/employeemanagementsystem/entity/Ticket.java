package com.ercanbeyen.employeemanagementsystem.entity;

import com.ercanbeyen.employeemanagementsystem.constants.enums.ticket.Topic;
import com.ercanbeyen.employeemanagementsystem.constants.enums.ticket.Priority;
import com.ercanbeyen.employeemanagementsystem.constants.enums.ticket.TicketType;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Ticket extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private boolean closed = false;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    private TicketType type;

    @Enumerated(EnumType.STRING)
    private Topic topic;

    private String subject;

    private String description;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "requesterId", referencedColumnName = "id")
    private Employee requester;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "assigneeId", referencedColumnName = "id")
    private Employee assignee;

    @OneToMany(mappedBy = "ticket", cascade =  CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @Override
    public String toString() {
        List<Integer> commentIds = comments
                .stream()
                .map(Comment::getId)
                .toList();

        return "Ticket{" +
                "id=" + id +
                ", closed=" + closed +
                ", priority=" + priority +
                ", type=" + type +
                ", topic=" + topic +
                ", subject='" + subject + '\'' +
                ", description='" + description + '\'' +
                ", requester=" + requester +
                ", assignee=" + assignee +
                ", commentIds=" + commentIds +
                '}';
    }
}
