package com.ercanbeyen.employeemanagementsystem.service.impl;

import com.ercanbeyen.employeemanagementsystem.dto.request.DepartmentRequest;

import com.ercanbeyen.employeemanagementsystem.dto.DepartmentDto;
import com.ercanbeyen.employeemanagementsystem.entity.Department;

import com.ercanbeyen.employeemanagementsystem.exception.DataConflict;
import com.ercanbeyen.employeemanagementsystem.exception.DataNotFound;
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
    public DepartmentDto createDepartment(DepartmentRequest request) {
        Department department = new Department();
        department.setName(request.getName());
        department.setLatestChangeAt(new Date());
        department.setLatestChangeBy("Admin");

        return convertDepartmentToDepartmentDto(departmentRepository.save(department));
    }

    @Override
    public DepartmentDto updateDepartment(int id, DepartmentRequest request) {
        String departmentName = request.getName();

        Department departmentInDb = departmentRepository.findById(id).orElseThrow(
                () -> new DataNotFound("Department called " + departmentName + " is not found")

        );

        departmentInDb.setLatestChangeAt(new Date());
        departmentInDb.setLatestChangeBy("Admin");
        departmentInDb.setName(departmentName);

        return convertDepartmentToDepartmentDto(departmentRepository.save(departmentInDb));
    }

    @Override
    public Department assignDepartment(String departmentName) {
        return departmentRepository.findByName(departmentName).orElseThrow(
                () -> new DataNotFound("Department called " + departmentName + " is not found")
        );
    }

    @Override
    public void deleteDepartment(int id) {
        Department department = departmentRepository.findById(id).orElseThrow(
                () -> new DataNotFound("Department with id " + id + " is not found")
        );

        int numberOfEmployee = department.getEmployees().size();

        if (numberOfEmployee > 0) {
            throw new DataConflict(
                    "Department called " + department.getName() + " could not be deleted, because it contains "
                            + numberOfEmployee + " employee(s)"
            );
        }

        departmentRepository.deleteById(id);
    }

    @Override
    public List<DepartmentDto> getDepartments() {
        return convertDepartmentToDepartmentDtos(departmentRepository.findAll());
    }

    @Override
    public DepartmentDto getDepartment(int id) {
        Department department = departmentRepository.findById(id).orElseThrow(
                () -> new DataNotFound("Department with id " + id + " is not found")
        );

        return convertDepartmentToDepartmentDto(department);
    }

    @Override
    public List<Department> getDepartmentsForStatistics() {
        return departmentRepository.findAll();
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
