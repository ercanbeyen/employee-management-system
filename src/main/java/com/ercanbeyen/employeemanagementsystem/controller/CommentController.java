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
    public ResponseEntity<Object> getComments() {
        List<CommentDto> comments = commentService.getComments();
        return ResponseHandler.generateResponse(HttpStatus.CREATED, true, Messages.SUCCESS, comments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getComment(@PathVariable("id") int id) {
        CommentDto comment = commentService.getComment(id);
        return ResponseHandler.generateResponse(HttpStatus.CREATED, true, Messages.SUCCESS, comment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteComment(@PathVariable("id") int id) {
        String message = commentService.deleteComment(id);
        return ResponseHandler.generateResponse(HttpStatus.CREATED, true, Messages.SUCCESS, message);
    }
}
