package com.ercanbeyen.springbootfirstrestapi.repository;

import com.ercanbeyen.springbootfirstrestapi.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Integer> {

}
