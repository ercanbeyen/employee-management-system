package com.ercanbeyen.springbootfirstrestapi.service;

import com.ercanbeyen.springbootfirstrestapi.dto.CreateDepartmentRequest;
import com.ercanbeyen.springbootfirstrestapi.dto.DepartmentDto;
import com.ercanbeyen.springbootfirstrestapi.dto.UpdateDepartmentRequest;
import com.ercanbeyen.springbootfirstrestapi.entity.Department;

import java.util.List;

public interface DepartmentService {
    DepartmentDto createDepartment(CreateDepartmentRequest createDepartmentRequest);
    DepartmentDto updateDepartment(int id, UpdateDepartmentRequest updateDepartmentRequest);
    Department assignDepartment(String departmentName);
    void deleteDepartment(int id);
    List<DepartmentDto> getDepartments();
    DepartmentDto getDepartment(int id);
}
