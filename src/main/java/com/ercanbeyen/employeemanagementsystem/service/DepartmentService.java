package com.ercanbeyen.employeemanagementsystem.service;

import com.ercanbeyen.employeemanagementsystem.dto.request.CreateDepartmentRequest;
import com.ercanbeyen.employeemanagementsystem.dto.DepartmentDto;
import com.ercanbeyen.employeemanagementsystem.dto.request.UpdateDepartmentRequest;
import com.ercanbeyen.employeemanagementsystem.entity.Department;

import java.util.List;

public interface DepartmentService {
    DepartmentDto createDepartment(CreateDepartmentRequest createDepartmentRequest);
    DepartmentDto updateDepartment(int id, UpdateDepartmentRequest updateDepartmentRequest);
    Department assignDepartment(String departmentName);
    void deleteDepartment(int id);
    List<DepartmentDto> getDepartments();
    DepartmentDto getDepartment(int id);
}
