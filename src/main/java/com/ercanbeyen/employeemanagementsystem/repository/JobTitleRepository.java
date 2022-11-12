package com.ercanbeyen.employeemanagementsystem.repository;

import com.ercanbeyen.employeemanagementsystem.entity.JobTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobTitleRepository extends JpaRepository<JobTitle, Integer> {
    Optional<JobTitle> findByName(String name);
}
