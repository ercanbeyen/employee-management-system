package com.ercanbeyen.springbootfirstrestapi.service;

import com.ercanbeyen.springbootfirstrestapi.dto.EmployeeDto;
import com.ercanbeyen.springbootfirstrestapi.entity.Employee;
import com.ercanbeyen.springbootfirstrestapi.util.CustomPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    EmployeeDto createEmployee(EmployeeDto user); // default types are public and abstract
    List<EmployeeDto> getEmployees(String department, String job, Optional<Integer> limit);
    EmployeeDto getEmployee(Long id);
    EmployeeDto updateEmployee(Long id, EmployeeDto user);
    void deleteUser(Long id);
    Page<Employee> pagination(int currentPage, int pageSize);
    Page<Employee> pagination(Pageable pageable);
    Page<Employee> slice(Pageable pageable);
    CustomPage<EmployeeDto> customPagination(Pageable pageable);
    void changeStatus(Long id);
}
