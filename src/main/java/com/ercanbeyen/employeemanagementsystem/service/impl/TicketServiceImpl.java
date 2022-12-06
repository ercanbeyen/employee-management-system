package com.ercanbeyen.employeemanagementsystem.service.impl;

import com.ercanbeyen.employeemanagementsystem.constants.enums.Role;
import com.ercanbeyen.employeemanagementsystem.constants.enums.ticket.Priority;
import com.ercanbeyen.employeemanagementsystem.constants.enums.ticket.Topic;
import com.ercanbeyen.employeemanagementsystem.constants.enums.ticket.Type;
import com.ercanbeyen.employeemanagementsystem.constants.messages.Messages;
import com.ercanbeyen.employeemanagementsystem.dto.TicketDto;
import com.ercanbeyen.employeemanagementsystem.entity.Employee;
import com.ercanbeyen.employeemanagementsystem.entity.Ticket;
import com.ercanbeyen.employeemanagementsystem.exception.DataConflict;
import com.ercanbeyen.employeemanagementsystem.exception.DataNotFound;
import com.ercanbeyen.employeemanagementsystem.repository.TicketRepository;
import com.ercanbeyen.employeemanagementsystem.service.AuthenticationService;
import com.ercanbeyen.employeemanagementsystem.service.EmployeeService;
import com.ercanbeyen.employeemanagementsystem.service.TicketService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    @Autowired
    private final TicketRepository ticketRepository;
    @Autowired
    private final ModelMapper modelMapper;
    @Autowired
    private final EmployeeService employeeService;
    @Autowired
    private final AuthenticationService authenticationService;

    @Override
    public TicketDto createTicket(TicketDto ticketDto) {
        log.debug("Ticket creation is starting");
        Ticket ticket = modelMapper.map(ticketDto, Ticket.class);

        String loggedIn_email = authenticationService.getEmail();
        Employee requester = employeeService.getEmployeeByEmail(loggedIn_email);
        ticket.setRequester(requester);
        log.debug("Requester is set");

        ticket.setLatestChangeBy(loggedIn_email);
        ticket.setLatestChangeAt(new Date());

        return modelMapper.map(ticketRepository.save(ticket), TicketDto.class);
    }

    @Override
    public TicketDto getTicket(int id) {
        Ticket ticket = ticketRepository
                .findById(id)
                .orElseThrow(
                        () -> new DataNotFound(String.format(Messages.NOT_FOUND, "Ticket", id))
                );

        return modelMapper.map(ticket, TicketDto.class);
    }

    @Override
    public List<TicketDto> filterTickets(Boolean closed, Priority priority, Topic topic, Type type, String requesterEmail, String assigneeEmail) {
        log.debug("Ticket filtering operation is starting");
        List<Ticket> tickets = ticketRepository.findAll();

        if (closed != null) {
            tickets = tickets
                    .stream()
                    .filter(ticket -> ticket.isClosed() == closed)
                    .toList();
        }

        if (priority != null) {
            tickets = tickets
                    .stream()
                    .filter(ticket -> ticket.getPriority() == priority)
                    .toList();
        }

        if (type != null) {
            tickets = tickets
                    .stream()
                    .filter(ticket -> ticket.getType() == type)
                    .toList();
        }

        if (assigneeEmail != null) {
            tickets = tickets
                    .stream()
                    .filter(ticket -> ticket.getAssignee().getEmail().equals(assigneeEmail))
                    .toList();
        }

        /* Only admin may observe all the tickets, other roles may observe their own tickets */
        String loggedIn_role = authenticationService.getRole();

        if (!loggedIn_role.equals(String.valueOf(Role.ADMIN))) {
            requesterEmail = authenticationService.getEmail();
        }

        if (requesterEmail != null) {
            String finalRequesterEmail = requesterEmail;
            tickets = tickets
                    .stream()
                    .filter(ticket -> ticket.getRequester().getEmail().equals(finalRequesterEmail))
                    .toList();
        }

        return modelMapper.map(tickets, new TypeToken<List<TicketDto>>(){}.getType());
    }

    @Override
    public TicketDto updateTicket(int id, TicketDto ticketDto) {
        log.debug("Ticket update operation is starting");

        Ticket ticketInDb = ticketRepository
                .findById(id)
                .orElseThrow(
                        () -> new DataNotFound(String.format(Messages.NOT_FOUND, "Ticket", id))
                );

        log.debug("Ticket is found");

        if (ticketInDb.isClosed()) {
            throw new DataConflict("Closed tickets cannot be updated");
        }

        log.debug("Ticket is still open");

        ticketInDb.setTopic(ticketDto.getTopic());
        ticketInDb.setPriority(ticketDto.getPriority());
        ticketInDb.setType(ticketDto.getType());
        ticketInDb.setSubject(ticketDto.getSubject());
        ticketInDb.setDescription(ticketDto.getDescription());
        log.debug("Related fields are updated");

        String loggedIn_email = authenticationService.getEmail();
        ticketInDb.setLatestChangeBy(loggedIn_email);
        ticketInDb.setLatestChangeAt(new Date());

        Ticket updatedTicket = ticketRepository.save(ticketInDb);
        log.debug("Ticket update operation is completed");

        return modelMapper.map(updatedTicket, TicketDto.class);
    }

    @Override
    public void deleteTicket(int id) {
        log.debug("Ticket delete operation is starting");

        ticketRepository
                .findById(id)
                .orElseThrow(
                        () -> new DataNotFound(String.format(Messages.NOT_FOUND, "Ticket", id))
                );

        log.debug("Ticket is found");

        ticketRepository.deleteById(id);

        log.debug("Ticket is deleted");
    }
}
