package com.ercanbeyen.employeemanagementsystem.service;

import com.ercanbeyen.employeemanagementsystem.dto.SalaryDto;
import com.ercanbeyen.employeemanagementsystem.dto.request.UpdateSalaryRequest;
import com.ercanbeyen.employeemanagementsystem.entity.Salary;

import java.util.List;

public interface SalaryService {
    Salary createSalary(Salary salary);
    SalaryDto getSalary(int id);
    List<SalaryDto> getSalaries();
    Salary updateSalary(int id, SalaryDto salary);
    List<SalaryDto> updateSalaries(UpdateSalaryRequest request);
}
