package com.ercanbeyen.employeemanagementsystem.dto;

import javax.validation.constraints.NotNull;

public class CommentDto {
    @NotNull(message = "You must comment on a ticket")
    private int ticketId;
    @NotNull(message = "Comment must have an employee")
    private int employee_id;
    private String text;
}
