package com.ercanbeyen.employeemanagementsystem.service;


import com.ercanbeyen.employeemanagementsystem.dto.CommentDto;
import com.ercanbeyen.employeemanagementsystem.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto);
    List<CommentDto> getComments(int ticketId, List<String> emails, Boolean sortByDate, Boolean descending);
    CommentDto getComment(int id);
    CommentDto updateComment(int id, CommentDto commentDto);
    String deleteComment(int id);
    Page<Comment> pagination(int pageNumber, int pageSize);
    Page<Comment> pagination(Pageable pageable);
    Page<Comment> slice(Pageable pageable);
}
