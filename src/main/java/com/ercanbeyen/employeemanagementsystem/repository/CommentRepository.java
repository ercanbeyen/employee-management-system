package com.ercanbeyen.employeemanagementsystem.repository;

import com.ercanbeyen.employeemanagementsystem.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

}
