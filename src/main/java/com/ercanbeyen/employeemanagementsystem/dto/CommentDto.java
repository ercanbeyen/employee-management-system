package com.ercanbeyen.employeemanagementsystem.dto;

import javax.validation.constraints.NotNull;

public class CommentDto {
    @NotNull(message = "You must comment on a ticket")
    private int ticketId;
    private String text;
}
