package com.ercanbeyen.employeemanagementsystem.controller;

import com.ercanbeyen.employeemanagementsystem.constants.messages.Messages;
import com.ercanbeyen.employeemanagementsystem.dto.CommentDto;
import com.ercanbeyen.employeemanagementsystem.dto.response.ResponseHandler;
import com.ercanbeyen.employeemanagementsystem.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<Object> createComment(@Valid @RequestBody CommentDto commentDto) {
        CommentDto createdComment = commentService.createComment(commentDto);
        return ResponseHandler.generateResponse(HttpStatus.CREATED, true, Messages.SUCCESS, createdComment);
    }

    @GetMapping
    public ResponseEntity<Object> getComments(
            @RequestParam int ticketId,
            @RequestParam(required = false) List<String> emails,
            @RequestParam(required = false) Boolean sortByDate,
            @RequestParam(required = false) Boolean descending) {
        List<CommentDto> comments = commentService.getComments(ticketId, emails, sortByDate, descending);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, comments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getComment(@PathVariable("id") int id) {
        CommentDto comment = commentService.getComment(id);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, comment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateComment(@PathVariable("id") int id, @Valid @RequestBody CommentDto commentDto) {
        CommentDto updatedComment = commentService.updateComment(id, commentDto);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, updatedComment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteComment(@PathVariable("id") int id) {
        String message = commentService.deleteComment(id);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, message);
    }
}
