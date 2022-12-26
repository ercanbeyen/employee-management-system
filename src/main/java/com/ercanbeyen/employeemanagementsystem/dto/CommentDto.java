package com.ercanbeyen.employeemanagementsystem.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CommentDto {
    @NotNull(message = "You must comment on a ticket")
    private int ticketId;
    @NotNull(message = "Comment must have an employee")
    private int employeeId;
    private String text;
}
