package com.ercanbeyen.employeemanagementsystem.controller;

import com.ercanbeyen.employeemanagementsystem.constants.enums.ticket.Priority;
import com.ercanbeyen.employeemanagementsystem.constants.enums.ticket.Topic;
import com.ercanbeyen.employeemanagementsystem.constants.enums.ticket.TicketType;
import com.ercanbeyen.employeemanagementsystem.constants.messages.Messages;
import com.ercanbeyen.employeemanagementsystem.dto.TicketDto;
import com.ercanbeyen.employeemanagementsystem.dto.response.ResponseHandler;
import com.ercanbeyen.employeemanagementsystem.entity.Ticket;
import com.ercanbeyen.employeemanagementsystem.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {
    @Autowired
    private final TicketService ticketService;

    @PostMapping
    public ResponseEntity<Object> createTicket(@Valid @RequestBody TicketDto ticketDto) {
        TicketDto createdTicket = ticketService.createTicket(ticketDto);
        return ResponseHandler.generateResponse(HttpStatus.CREATED, true, Messages.SUCCESS, createdTicket);
    }

    @GetMapping
    public ResponseEntity<Object> getTickets(
            @RequestParam(required = false) Boolean closed,
            @RequestParam(required = false) Priority priority,
            @RequestParam(required = false) Topic topic,
            @RequestParam(required = false) TicketType ticketType,
            @RequestParam(required = false) String requesterEmail,
            @RequestParam(required = false) String assigneeEmail,
            @RequestParam(required = false) Boolean sort,
            @RequestParam(required = false) Integer limit) {
        List<TicketDto> ticketDtos = ticketService.getTickets(closed, priority, topic, ticketType, requesterEmail, assigneeEmail, sort, limit);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, ticketDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getTicket(@PathVariable("id") int id) {
        TicketDto ticketDto = ticketService.getTicket(id);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, ticketDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTicket(@PathVariable("id") int id, @Valid @RequestBody TicketDto ticketDto) {
        TicketDto updatedTicket = ticketService.updateTicket(id, ticketDto);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, updatedTicket);
    }

    @PutMapping("/{id}/assignment")
    public ResponseEntity<Object> assignTicketToEmployee(@PathVariable("id") int id, @RequestParam String email) {
        String assignMessage = ticketService.assignTicket(id, email);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, assignMessage);
    }

    @PutMapping("/{id}/close")
    public ResponseEntity<Object> closeTicket(@PathVariable("id") int id) {
        String closeMessage = ticketService.closeTicket(id);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, closeMessage);
    }

    @PutMapping("/{id}/reopen")
    public ResponseEntity<Object> reopenTicket(@PathVariable("id") int id) {
        String reopenMessage = ticketService.reopenTicket(id);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, reopenMessage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTicket(@PathVariable("id") int id) {
        ticketService.deleteTicket(id);
        return ResponseHandler.generateResponse(HttpStatus.NO_CONTENT, true, Messages.SUCCESS, null);
    }

    @GetMapping("/pagination")
    public ResponseEntity<Object> pagination(@RequestParam int pageNumber, @RequestParam int pageSize) {
        Page<Ticket> page = ticketService.pagination(pageNumber, pageSize);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, page);
    }

    @GetMapping("/pagination/v1")
    public ResponseEntity<Object> pagination(Pageable pageable) {
        Page<Ticket> page = ticketService.pagination(pageable);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, page);
    }

    @GetMapping("/pagination/v2")
    public ResponseEntity<Object> slice(Pageable pageable) {
        Slice<Ticket> slice = ticketService.slice(pageable);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, slice);
    }

}
