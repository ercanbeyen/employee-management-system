package com.ercanbeyen.employeemanagementsystem.service.impl;

import com.ercanbeyen.employeemanagementsystem.dto.request.CreateDepartmentRequest;
import com.ercanbeyen.employeemanagementsystem.dto.DepartmentDto;
import com.ercanbeyen.employeemanagementsystem.dto.request.UpdateDepartmentRequest;
import com.ercanbeyen.employeemanagementsystem.entity.Department;

import com.ercanbeyen.employeemanagementsystem.exception.DepartmentNotFound;
import com.ercanbeyen.employeemanagementsystem.repository.DepartmentRepository;
import com.ercanbeyen.employeemanagementsystem.service.DepartmentService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private final DepartmentRepository departmentRepository;

    @Override
    public DepartmentDto createDepartment(CreateDepartmentRequest createDepartmentRequest) {
        Department department = new Department();
        department.setName(createDepartmentRequest.getName());
        department.setLatestChangeAt(new Date());
        department.setLatestChangeBy("Admin");

        return convertDepartmentToDepartmentDto(departmentRepository.save(department));
    }

    @Override
    public DepartmentDto updateDepartment(int id, UpdateDepartmentRequest updateDepartmentRequest) {
        String departmentName = updateDepartmentRequest.getName();

        Department departmentInDb = departmentRepository.findById(id).orElseThrow(
                () -> new DepartmentNotFound("Department called " + departmentName + " is not found")

        );

        departmentInDb.setLatestChangeAt(new Date());
        departmentInDb.setLatestChangeBy("Admin");
        departmentInDb.setName(departmentName);

        return convertDepartmentToDepartmentDto(departmentRepository.save(departmentInDb));
    }

    @Override
    public Department assignDepartment(String departmentName) {
        return departmentRepository.findByName(departmentName).orElseThrow(
                () -> new DepartmentNotFound("Department called " + departmentName + " is not found")
        );
    }

    @Override
    public void deleteDepartment(int id) {
        departmentRepository.deleteById(id);
    }

    @Override
    public List<DepartmentDto> getDepartments() {
        return convertDepartmentToDepartmentDtos(departmentRepository.findAll());
    }

    @Override
    public DepartmentDto getDepartment(int id) {
        Department department = departmentRepository.findById(id).orElseThrow(
                () -> new DepartmentNotFound("Department with id " + id + " is not found")
        );

        return convertDepartmentToDepartmentDto(department);
    }

    List<DepartmentDto> convertDepartmentToDepartmentDtos(List<Department> departments) {
        List<DepartmentDto> departmentDtos = new ArrayList<>();

        for (Department department : departments) {
            DepartmentDto departmentDto = convertDepartmentToDepartmentDto(department);
            departmentDtos.add(departmentDto);
        }

        return departmentDtos;

    }

    DepartmentDto convertDepartmentToDepartmentDto(Department department) {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setName(department.getName());
        department.getEmployees().forEach(employee -> departmentDto.getEmails().add(employee.getEmail()));
        return departmentDto;
    }

}
