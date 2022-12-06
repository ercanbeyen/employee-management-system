package com.ercanbeyen.employeemanagementsystem.dto;

import com.ercanbeyen.employeemanagementsystem.constants.enums.ticket.Priority;
import com.ercanbeyen.employeemanagementsystem.constants.enums.ticket.Topic;

import com.ercanbeyen.employeemanagementsystem.constants.enums.ticket.Type;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
public class TicketDto {
    private boolean closed = false;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    private Topic topic;

    @Enumerated(EnumType.STRING)
    private Type type;

    @NotBlank(message = "Subject should not be blank")
    private String subject;

    @NotBlank(message = "Description should not be blank")
    private String description;

    private String requesterEmail;

    private String assigneeEmail;
}
