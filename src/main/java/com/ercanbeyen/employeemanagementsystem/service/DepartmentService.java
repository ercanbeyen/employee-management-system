package com.ercanbeyen.employeemanagementsystem.service;

import com.ercanbeyen.employeemanagementsystem.dto.request.DepartmentRequest;
import com.ercanbeyen.employeemanagementsystem.dto.DepartmentDto;

import com.ercanbeyen.employeemanagementsystem.entity.Department;

import java.util.List;

public interface DepartmentService {
    DepartmentDto createDepartment(DepartmentRequest createDepartmentRequest);
    DepartmentDto updateDepartment(int id, DepartmentRequest request);
    Department assignDepartment(String departmentName);
    void deleteDepartment(int id);
    List<DepartmentDto> getDepartments();
    DepartmentDto getDepartment(int id);
    List<Department> getDepartmentsForStatistics();
}
