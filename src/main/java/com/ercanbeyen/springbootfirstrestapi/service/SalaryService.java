package com.ercanbeyen.springbootfirstrestapi.service;

import com.ercanbeyen.springbootfirstrestapi.entity.Salary;

import java.util.List;

public interface SalaryService {
    Salary createSalary(Salary salary);
    Salary getSalary(int id);
    List<Salary> getSalaries();
    Salary updateSalary(int id, Salary salary);
}
