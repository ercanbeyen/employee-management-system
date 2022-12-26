package com.ercanbeyen.employeemanagementsystem.service;


import com.ercanbeyen.employeemanagementsystem.dto.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto);
    List<CommentDto> getComments();
    CommentDto getComment(int id);
    CommentDto updateComment(int id, CommentDto commentDto);
    String deleteComment(int id);
}
