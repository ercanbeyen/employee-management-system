package com.ercanbeyen.employeemanagementsystem.controller;

import com.ercanbeyen.employeemanagementsystem.constants.messages.Messages;
import com.ercanbeyen.employeemanagementsystem.dto.CommentDto;
import com.ercanbeyen.employeemanagementsystem.dto.response.ResponseHandler;
import com.ercanbeyen.employeemanagementsystem.entity.Comment;
import com.ercanbeyen.employeemanagementsystem.service.CommentService;
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
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    @Autowired
    private final CommentService commentService;

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

    @GetMapping("/pagination")
    public ResponseEntity<Object> pagination(@RequestParam int pageNumber, @RequestParam int pageSize) {
        Page<Comment> page = commentService.pagination(pageNumber, pageSize);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, page);
    }

    @GetMapping("/pagination/v1")
    public ResponseEntity<Object> pagination(Pageable pageable) {
        Page<Comment> page = commentService.pagination(pageable);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, page);
    }

    @GetMapping("/pagination/v2")
    public ResponseEntity<Object> slice(Pageable pageable) {
        Slice<Comment> slice = commentService.slice(pageable);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, slice);
    }
}
