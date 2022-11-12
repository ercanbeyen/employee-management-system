package com.ercanbeyen.employeemanagementsystem.service;

import com.ercanbeyen.employeemanagementsystem.dto.EmployeeDto;
import com.ercanbeyen.employeemanagementsystem.dto.SalaryDto;
import com.ercanbeyen.employeemanagementsystem.dto.request.UpdateEmployeeDetailsRequest;
import com.ercanbeyen.employeemanagementsystem.dto.request.UpdateOccupationRequest;
import com.ercanbeyen.employeemanagementsystem.entity.enums.Currency;
import com.ercanbeyen.employeemanagementsystem.entity.Employee;
import com.ercanbeyen.employeemanagementsystem.util.CustomPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface EmployeeService {
    EmployeeDto createEmployee(EmployeeDto user);
    List<EmployeeDto> filterEmployees(String department, String role, Currency currency, Integer limit);
    List<EmployeeDto> searchEmployees(String firstName, String lastName);
    EmployeeDto getEmployee(int id);
    EmployeeDto updateEmployeeDetails(int id, UpdateEmployeeDetailsRequest request);
    EmployeeDto updateSalary(int id, SalaryDto salaryDto);
    EmployeeDto updateOccupation(int id, UpdateOccupationRequest request);
    String uploadImage(int id, MultipartFile file) throws IOException;
    byte[] downloadImage(int id, String fileName);
    void deleteEmployee(int id);
    Page<Employee> pagination(int currentPage, int pageSize);
    Page<Employee> pagination(Pageable pageable);
    Page<Employee> slice(Pageable pageable);
    CustomPage<EmployeeDto> customPagination(Pageable pageable);
}
