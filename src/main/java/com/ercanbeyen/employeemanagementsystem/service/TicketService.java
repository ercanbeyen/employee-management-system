package com.ercanbeyen.employeemanagementsystem.service;

import com.ercanbeyen.employeemanagementsystem.constants.enums.ticket.Priority;
import com.ercanbeyen.employeemanagementsystem.constants.enums.ticket.Topic;
import com.ercanbeyen.employeemanagementsystem.constants.enums.ticket.TicketType;
import com.ercanbeyen.employeemanagementsystem.dto.TicketDto;
import com.ercanbeyen.employeemanagementsystem.entity.Ticket;

import java.util.List;

public interface TicketService {
    TicketDto createTicket(TicketDto ticketDto);
    TicketDto getTicket(int id);
    List<TicketDto> getTickets(Boolean closed, Priority priority, Topic topic, TicketType ticketType, String assigneeEmail, String requesterEmail, Boolean sort, Integer limit);
    TicketDto updateTicket(int id, TicketDto ticketDto);
    void deleteTicket(int id);
    String assignTicket(int id, String email);
    String closeTicket(int id);
    String reopenTicket(int id);
    List<Ticket> getTicketsForStatistics();
    Ticket getTicketById(int id);
}
