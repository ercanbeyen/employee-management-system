package com.ercanbeyen.employeemanagementsystem.service;

import com.ercanbeyen.employeemanagementsystem.dto.request.DepartmentRequest;
import com.ercanbeyen.employeemanagementsystem.dto.DepartmentDto;

import com.ercanbeyen.employeemanagementsystem.entity.Department;
import com.ercanbeyen.employeemanagementsystem.util.CustomPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DepartmentService {
    DepartmentDto createDepartment(DepartmentRequest createDepartmentRequest);
    DepartmentDto updateDepartment(int id, DepartmentRequest request);
    Department findDepartmentByName(String department);
    void deleteDepartment(int id);
    List<DepartmentDto> getDepartments();
    DepartmentDto getDepartment(int id);
    List<Department> getDepartmentsForStatistics();
    Page<Department> pagination(int pageNumber, int pageSize);
    Page<Department> pagination(Pageable pageable);
    Page<Department> slice(Pageable pageable);
}
