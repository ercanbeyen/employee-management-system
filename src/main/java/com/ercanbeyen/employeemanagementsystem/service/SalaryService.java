package com.ercanbeyen.employeemanagementsystem.service;

import com.ercanbeyen.employeemanagementsystem.dto.SalaryDto;
import com.ercanbeyen.employeemanagementsystem.entity.Salary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SalaryService {
    Salary createSalary(Salary salary);
    SalaryDto getSalary(int id);
    List<SalaryDto> getSalaries();
    void updateSalary(int id, SalaryDto salary);
    void updateSalaries(List<Salary> salaries, double percentage);
    List<Salary> getSalariesForStatistics();
    Page<Salary> pagination(int pageNumber, int pageSize);
    Page<Salary> pagination(Pageable pageable);
    Page<Salary> slice(Pageable pageable);
}
