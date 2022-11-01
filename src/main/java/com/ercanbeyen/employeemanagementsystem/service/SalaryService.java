package com.ercanbeyen.employeemanagementsystem.service;

import com.ercanbeyen.employeemanagementsystem.entity.Salary;

import java.util.List;

public interface SalaryService {
    Salary createSalary(Salary salary);
    Salary getSalary(int id);
    List<Salary> getSalaries();
    Salary updateSalary(int id, Salary salary);
}
