package com.ercanbeyen.springbootfirstrestapi.service;

import com.ercanbeyen.springbootfirstrestapi.dto.DepartmentDto;
import com.ercanbeyen.springbootfirstrestapi.entity.Department;
import com.ercanbeyen.springbootfirstrestapi.entity.Employee;

import java.util.List;

public interface DepartmentService {
    DepartmentDto createDepartment(DepartmentDto departmentDto);
    DepartmentDto updateDepartment(int id, DepartmentDto departmentDto);
    Department assignDepartment(String departmentName);
    void deleteDepartment(int id);
    List<DepartmentDto> getDepartments();
    DepartmentDto getDepartment(int id);
}
