package com.ercanbeyen.employeemanagementsystem.service;

import com.ercanbeyen.employeemanagementsystem.dto.EmployeeDto;
import com.ercanbeyen.employeemanagementsystem.dto.SalaryDto;
import com.ercanbeyen.employeemanagementsystem.dto.request.*;
import com.ercanbeyen.employeemanagementsystem.constants.enums.Currency;
import com.ercanbeyen.employeemanagementsystem.entity.Employee;
import com.ercanbeyen.employeemanagementsystem.constants.enums.Role;
import com.ercanbeyen.employeemanagementsystem.util.CustomPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface EmployeeService {
    EmployeeDto createEmployee(EmployeeDto user);
    List<EmployeeDto> getEmployees(Role role, String department, String jobTitle, Currency currency, Boolean sort, Integer limit);
    List<EmployeeDto> searchEmployees(String firstName, String lastName);
    EmployeeDto getEmployee(int id);
    EmployeeDto updateEmployeeDetails(int id, UpdateEmployeeDetailsRequest request);
    EmployeeDto updateSalary(int id, SalaryDto salaryDto);
    List<EmployeeDto> updateSalaries(UpdateSalaryRequest request);
    EmployeeDto updateProfession(int id, UpdateProfessionRequest request);
    EmployeeDto updateRole(int id, RoleRequest request);
    String updatePassword(int id, String newPassword, String confirmationPassword);
    String uploadImage(int id, MultipartFile file) throws IOException;
    byte[] downloadImage(int id, String fileName);
    void deleteEmployee(int id);
    Page<Employee> pagination(int pageNumber, int pageSize);
    Page<Employee> pagination(Pageable pageable);
    Page<Employee> slice(Pageable pageable);
    CustomPage<EmployeeDto> customPagination(Pageable pageable);
    List<Employee> getEmployeesForStatistics();
    List<Role> findRolesByDepartmentForStatistics(String department);
    Employee getEmployeeByEmail(String email);
}
