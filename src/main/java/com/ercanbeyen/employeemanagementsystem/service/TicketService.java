package com.ercanbeyen.employeemanagementsystem.service;

import com.ercanbeyen.employeemanagementsystem.constants.enums.ticket.Priority;
import com.ercanbeyen.employeemanagementsystem.constants.enums.ticket.Topic;
import com.ercanbeyen.employeemanagementsystem.constants.enums.ticket.Type;
import com.ercanbeyen.employeemanagementsystem.dto.TicketDto;
import com.ercanbeyen.employeemanagementsystem.entity.Ticket;

import java.util.List;

public interface TicketService {
    TicketDto createTicket(TicketDto ticketDto);
    TicketDto getTicket(int id);
    List<TicketDto> filterTickets(Boolean closed, Priority priority, Topic topic, Type type, String assigneeEmail, String requesterEmail);
    TicketDto updateTicket(int id, TicketDto ticketDto);
    void deleteTicket(int id);
    String assignTicket(int id, String email);
    String closeTicket(int id);
    String reopenTicket(int id);
    List<Ticket> getTicketsForStatistics();
}
