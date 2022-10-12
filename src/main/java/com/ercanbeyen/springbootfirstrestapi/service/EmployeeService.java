package com.ercanbeyen.springbootfirstrestapi.service;

import com.ercanbeyen.springbootfirstrestapi.dto.EmployeeDto;
import com.ercanbeyen.springbootfirstrestapi.entity.Currency;
import com.ercanbeyen.springbootfirstrestapi.entity.Employee;
import com.ercanbeyen.springbootfirstrestapi.util.CustomPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    EmployeeDto createEmployee(EmployeeDto user);
    List<EmployeeDto> getEmployees(String department, String role, Currency currency, Optional<Integer> limit);
    EmployeeDto getEmployee(int id);
    EmployeeDto updateEmployee(int id, EmployeeDto user);
    void deleteUser(int id);
    Page<Employee> pagination(int currentPage, int pageSize);
    Page<Employee> pagination(Pageable pageable);
    Page<Employee> slice(Pageable pageable);
    CustomPage<EmployeeDto> customPagination(Pageable pageable);
    void changeStatus(int id);
}
