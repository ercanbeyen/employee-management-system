package com.ercanbeyen.employeemanagementsystem.service.impl;

import com.ercanbeyen.employeemanagementsystem.constants.enums.Role;
import com.ercanbeyen.employeemanagementsystem.constants.messages.Messages;
import com.ercanbeyen.employeemanagementsystem.dto.CommentDto;
import com.ercanbeyen.employeemanagementsystem.entity.BaseEntity;
import com.ercanbeyen.employeemanagementsystem.entity.Comment;
import com.ercanbeyen.employeemanagementsystem.entity.Employee;
import com.ercanbeyen.employeemanagementsystem.entity.Ticket;
import com.ercanbeyen.employeemanagementsystem.exception.DataConflict;
import com.ercanbeyen.employeemanagementsystem.exception.DataForbidden;
import com.ercanbeyen.employeemanagementsystem.exception.DataNotFound;
import com.ercanbeyen.employeemanagementsystem.repository.CommentRepository;
import com.ercanbeyen.employeemanagementsystem.repository.EmployeeRepository;
import com.ercanbeyen.employeemanagementsystem.service.AuthenticationService;
import com.ercanbeyen.employeemanagementsystem.service.CommentService;
import com.ercanbeyen.employeemanagementsystem.service.EmployeeService;
import com.ercanbeyen.employeemanagementsystem.service.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private final CommentRepository commentRepository;
    @Autowired
    private final ModelMapper modelMapper;
    @Autowired
    private final AuthenticationService authenticationService;
    @Autowired
    private final EmployeeService employeeService;
    @Autowired
    private final TicketService ticketService;
    @Override
    public CommentDto createComment(CommentDto commentDto) {
        String loggedIn_email = authenticationService.getEmail();
        Employee employee = employeeService.getEmployeeByEmail(loggedIn_email);

        if (employee.getId() != commentDto.getEmployeeId()) {
            throw new DataConflict("You cannot make comment for someone else");
        }

        log.debug("Commenter is a valid employee");

        Ticket ticket = ticketService.getTicketById(commentDto.getTicketId());
        log.debug("Ticket is found");

        Comment comment = new Comment();

        comment.setEmployee(employee);
        comment.setText(commentDto.getText());
        comment.setTicket(ticket);
        comment.setLatestChangeAt(new Date());
        comment.setLatestChangeBy(loggedIn_email);

        Comment createdComment = commentRepository.save(comment);
        log.debug("Comment is successfully created");

        return modelMapper.map(createdComment, CommentDto.class);
    }

    @Override
    public List<CommentDto> getComments(int ticketId, List<String> emails, Boolean sortByDate, Boolean descending) {
        List<Employee> employees = new ArrayList<>();

        if (emails != null) {
            for (String email : emails) {
                Employee employee = employeeService.getEmployeeByEmail(email);
                employees.add(employee);
            }
        }

        Ticket ticket = ticketService.getTicketById(ticketId);
        List<Comment> allComments = ticket.getComments();
        List<Comment> comments = new ArrayList<>();

        if (employees.size() > 0) {
            for (Employee employee : employees) {
                Iterator<Comment> iterator = allComments.iterator();
                if (iterator.hasNext()) {
                    Comment comment = iterator.next();
                    if (comment.getEmployee().getId() == employee.getId()) {
                        comments.add(comment);
                        allComments.remove(comment);
                    }
                }
            }
        } else {
            comments = allComments;
        }

        if (sortByDate != null && sortByDate) {
            if (descending) {
                comments = comments
                        .stream()
                        .sorted((comment1, comment2) -> comment2.getLatestChangeAt().compareTo(comment1.getLatestChangeAt()))
                        .toList();
            } else {
                comments = comments
                        .stream()
                        .sorted(Comparator.comparing(BaseEntity::getLatestChangeAt))
                        .toList();
            }
        }

        return modelMapper.map(comments, new TypeToken<List<CommentDto>>(){}.getType());
    }

    @Override
    public CommentDto getComment(int id) {
        Comment comment = commentRepository
                .findById(id)
                .orElseThrow(
                        () -> new DataNotFound(String.format(Messages.NOT_FOUND, "Comment", id))
                );

        return modelMapper.map(comment, CommentDto.class);
    }

    @Override
    public CommentDto updateComment(int id, CommentDto commentDto) {
        Comment comment = commentRepository
                .findById(id)
                .orElseThrow(
                        () -> new DataNotFound(String.format(Messages.NOT_FOUND, "Comment", id))
                );

        String loggedIn_email = authenticationService.getEmail();
        Employee loggedIn_employee = employeeService.getEmployeeByEmail(loggedIn_email);

        if (loggedIn_employee.getId() != commentDto.getEmployeeId() || commentDto.getEmployeeId() != comment.getEmployee().getId()) {
            throw new DataConflict("You cannot update comment of someone else");
        }

        log.debug("Commenter is a valid employee");

        ticketService.getTicketById(commentDto.getTicketId());
        log.debug("Ticket is found");

        comment.setText(commentDto.getText());
        comment.setLatestChangeAt(new Date());

        Comment updatedComment = commentRepository.save(comment);
        log.debug("Comment is successfully updated");

        return modelMapper.map(updatedComment, CommentDto.class);
    }

    @Override
    public String deleteComment(int id) {
        log.debug("Delete comment operation is starting");

        Comment comment = commentRepository
                .findById(id)
                .orElseThrow(
                        () -> new DataNotFound(String.format(Messages.NOT_FOUND, "Comment", id))
                );

        String loggedIn_email = authenticationService.getEmail();
        Employee loggedIn_employee = employeeService.getEmployeeByEmail(loggedIn_email);

        if (loggedIn_employee.getId() != comment.getEmployee().getId() && loggedIn_employee.getRole() != Role.ADMIN) {
            throw new DataForbidden("You are not authorized to delete this comment");
        }

        log.debug("Validations are satisfied, now comment can be deleted");
        Ticket ticket = comment.getTicket();

        // Remove bidirectional connections between ticket&comment
        ticket.getComments().remove(comment);
        comment.setTicket(null);
        log.debug("Bidirectional connections are removed");

        commentRepository.deleteById(id);

        String message = "Ticket " + id + " is deleted";
        log.debug(message);

        return message;

    }

    @Override
    public Page<Comment> pagination(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return commentRepository.findAll(pageable);
    }

    @Override
    public Page<Comment> pagination(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }

    @Override
    public Page<Comment> slice(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }
}
