package com.ercanbeyen.springbootfirstrestapi.service;

import com.ercanbeyen.springbootfirstrestapi.dto.EmployeeDto;
import com.ercanbeyen.springbootfirstrestapi.entity.enums.Currency;
import com.ercanbeyen.springbootfirstrestapi.entity.Employee;
import com.ercanbeyen.springbootfirstrestapi.util.CustomPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeService {
    EmployeeDto createEmployee(EmployeeDto user);
    List<EmployeeDto> filterEmployees(String department, String role, Currency currency, Integer limit);
    List<EmployeeDto> searchEmployees(String firstName, String lastName);
    EmployeeDto getEmployee(int id);
    EmployeeDto updateEmployee(int id, EmployeeDto user);
    void deleteEmployee(int id);
    Page<Employee> pagination(int currentPage, int pageSize);
    Page<Employee> pagination(Pageable pageable);
    Page<Employee> slice(Pageable pageable);
    CustomPage<EmployeeDto> customPagination(Pageable pageable);
}
