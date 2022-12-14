package com.ercanbeyen.employeemanagementsystem.dto;

import com.ercanbeyen.employeemanagementsystem.constants.enums.ticket.Priority;
import com.ercanbeyen.employeemanagementsystem.constants.enums.ticket.Topic;

import com.ercanbeyen.employeemanagementsystem.constants.enums.ticket.TicketType;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
public class TicketDto {
    private boolean closed = false;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    private Topic topic;

    @Enumerated(EnumType.STRING)
    private TicketType type;

    @NotBlank(message = "Subject should not be blank")
    private String subject;

    @NotBlank(message = "Description should not be blank")
    private String description;

    private String requesterEmail;

    private String assigneeEmail;

    private List<Integer> commentIds = new ArrayList<>();
}
