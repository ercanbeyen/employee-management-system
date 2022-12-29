package com.ercanbeyen.employeemanagementsystem.service.impl;

import com.ercanbeyen.employeemanagementsystem.constants.enums.Role;
import com.ercanbeyen.employeemanagementsystem.constants.enums.ticket.Priority;
import com.ercanbeyen.employeemanagementsystem.constants.enums.ticket.Topic;
import com.ercanbeyen.employeemanagementsystem.constants.enums.ticket.TicketType;
import com.ercanbeyen.employeemanagementsystem.constants.messages.Messages;
import com.ercanbeyen.employeemanagementsystem.dto.TicketDto;
import com.ercanbeyen.employeemanagementsystem.entity.Employee;
import com.ercanbeyen.employeemanagementsystem.entity.Ticket;
import com.ercanbeyen.employeemanagementsystem.exception.DataConflict;
import com.ercanbeyen.employeemanagementsystem.exception.DataForbidden;
import com.ercanbeyen.employeemanagementsystem.exception.DataNotFound;
import com.ercanbeyen.employeemanagementsystem.repository.TicketRepository;
import com.ercanbeyen.employeemanagementsystem.service.AuthenticationService;
import com.ercanbeyen.employeemanagementsystem.service.EmployeeService;
import com.ercanbeyen.employeemanagementsystem.service.TicketService;

import com.ercanbeyen.employeemanagementsystem.util.TicketUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public List<TicketDto> getTickets(Boolean closed, Priority priority, Topic topic, TicketType type, String requesterEmail, String assigneeEmail, Boolean sort, Integer limit) {
        log.debug("Ticket filtering operation is starting");
        List<Ticket> tickets = ticketRepository.findAll();

        if (closed != null) {
            tickets = tickets
                    .stream()
                    .filter(ticket -> ticket.isClosed() == closed)
                    .toList();
            log.debug("Tickets are filtered by closed status");
        }

        if (priority != null) {
            tickets = tickets
                    .stream()
                    .filter(ticket -> ticket.getPriority() == priority)
                    .toList();

            log.debug("Tickets are filtered by priority {}", priority);
        } else if (sort != null && sort) {
            tickets = TicketUtils.sortTickets(tickets);
            log.debug("Tickets are sorted by priority");
        }

        if (type != null) {
            tickets = tickets
                    .stream()
                    .filter(ticket -> ticket.getType() == type)
                    .toList();
            log.debug("Tickets are filtered by type {}", type);
        }

        if (requesterEmail != null) {
            tickets = tickets
                    .stream()
                    .filter(ticket -> ticket.getRequester().getEmail().equals(requesterEmail))
                    .toList();
            log.debug("Tickets are filtered by requester {}", requesterEmail);
        }

        if (assigneeEmail != null) {
            tickets = tickets
                    .stream()
                    .filter(ticket -> ticket.getAssignee().getEmail().equals(assigneeEmail))
                    .toList();
            log.debug("Tickets are filtered by assignee {}", assigneeEmail);
        }

        if (limit != null) {
            tickets = tickets
                    .stream()
                    .limit(limit)
                    .toList();
            log.debug("Top {} tickets are fetched", limit);
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

        String loggedIn_email = authenticationService.getEmail();
        String loggedIn_role = authenticationService.getRole();

        if (!loggedIn_role.equals(String.valueOf(Role.ADMIN)) && !loggedIn_email.equals(ticketInDb.getRequester().getEmail())) {
            throw new DataForbidden("You cannot update the ticket");
        }

        log.debug("Logged in employee is either admin or the requester");

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

    @Override
    public String assignTicket(int id, String email) {
        log.debug("Ticket assigning is starting");

        Ticket ticket = ticketRepository
                .findById(id)
                .orElseThrow(
                        () -> new DataNotFound(String.format(Messages.NOT_FOUND, "Ticket", id))
                );

        log.debug("Ticket is found");

        Employee assignee = employeeService.getEmployeeByEmail(email);
        log.debug("Employee is found");

        ticket.setAssignee(assignee);
        ticketRepository.save(ticket);

        return "Ticket " + id + " is assigned to employee " + email;
    }

    @Override
    public String closeTicket(int id) {
        log.debug("Ticket closing is starting");

        Ticket ticket = ticketRepository
                .findById(id)
                .orElseThrow(
                        () -> new DataNotFound(String.format(Messages.NOT_FOUND, "Ticket", id))
                );

        log.debug("Ticket is found");

        if (ticket.isClosed()) {
            throw new DataConflict("Ticket " + id + " has already been closed");
        }

        log.debug("Ticket has not closed yet");

        Employee assignee = ticket.getAssignee();
        String loggedIn_role = authenticationService.getRole();
        String loggedIn_email = authenticationService.getEmail();

        /* Either assignee or admin may close the ticket */
        if (!loggedIn_role.equals(String.valueOf(Role.ADMIN)) && !assignee.getEmail().equals(loggedIn_email)) {
            throw new DataForbidden("Only assignee or admin may close this ticket");
        }

        log.debug("Assignee is same employee with the logged in employee");

        ticket.setClosed(true);
        ticket.setLatestChangeAt(new Date());
        ticket.setLatestChangeBy(loggedIn_email);

        ticketRepository.save(ticket);
        log.debug("Ticket is closed");

        return "Ticket " + id + " is closed";
    }

    @Override
    public String reopenTicket(int id) {
        log.debug("Ticket closing is starting");

        Ticket ticket = ticketRepository
                .findById(id)
                .orElseThrow(
                        () -> new DataNotFound(String.format(Messages.NOT_FOUND, "Ticket", id))
                );

        log.debug("Ticket is found");

        if (!ticket.isClosed()) {
            throw new DataConflict("Ticket " + id + " has already been opened");
        }

        log.debug("Ticket has been closed");


        String loggedIn_role = authenticationService.getRole();
        String loggedIn_email = authenticationService.getEmail();

        /* Only admin may reopen the ticket */
        if (!loggedIn_role.equals(String.valueOf(Role.ADMIN))) {
            throw new DataForbidden("Only admin may reopen the ticket");
        }

        log.debug("Logged in employee is admin");

        ticket.setClosed(false);
        ticket.setLatestChangeAt(new Date());
        ticket.setLatestChangeBy(loggedIn_email);

        ticketRepository.save(ticket);
        log.debug("Ticket is reopened");

        return "Ticket " + id + " is reopened";
    }

    @Override
    public List<Ticket> getTicketsForStatistics() {
        return ticketRepository.findAll();
    }

    @Override
    public Ticket getTicketById(int id) {
        return ticketRepository
                .findById(id)
                .orElseThrow(
                        () -> new DataNotFound(String.format(Messages.NOT_FOUND, "Ticket", id))
                );
    }

    @Override
    public Page<Ticket> pagination(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return ticketRepository.findAll(pageable);
    }

    @Override
    public Page<Ticket> pagination(Pageable pageable) {
        return ticketRepository.findAll(pageable);
    }

    @Override
    public Page<Ticket> slice(Pageable pageable) {
        return ticketRepository.findAll(pageable);
    }

}
