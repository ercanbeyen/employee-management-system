package com.ercanbeyen.employeemanagementsystem.service.impl;

import com.ercanbeyen.employeemanagementsystem.constants.messages.Messages;
import com.ercanbeyen.employeemanagementsystem.dto.request.DepartmentRequest;

import com.ercanbeyen.employeemanagementsystem.dto.DepartmentDto;
import com.ercanbeyen.employeemanagementsystem.entity.Department;

import com.ercanbeyen.employeemanagementsystem.exception.DataConflict;
import com.ercanbeyen.employeemanagementsystem.exception.DataNotFound;
import com.ercanbeyen.employeemanagementsystem.repository.DepartmentRepository;
import com.ercanbeyen.employeemanagementsystem.service.AuthenticationService;
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
    @Autowired
    private final AuthenticationService authenticationService;

    @Override
    public DepartmentDto createDepartment(DepartmentRequest request) {
        Department department = new Department();
        department.setName(request.getName());

        department.setLatestChangeAt(new Date());
        String loggedIn_email = authenticationService.getEmail();
        department.setLatestChangeBy(loggedIn_email);

        return convertDepartmentToDepartmentDto(departmentRepository.save(department));
    }

    @Override
    public DepartmentDto updateDepartment(int id, DepartmentRequest request) {
        String departmentName = request.getName();

        Department departmentInDb = departmentRepository
                .findById(id)
                .orElseThrow(
                        () -> new DataNotFound(String.format(Messages.ITEM_NOT_FOUND, "Department", departmentName))
                );

        departmentInDb.setName(departmentName);

        departmentInDb.setLatestChangeAt(new Date());

        String email = authenticationService.getEmail();
        departmentInDb.setLatestChangeBy(email);

        return convertDepartmentToDepartmentDto(departmentRepository.save(departmentInDb));
    }

    @Override
    public Department findDepartmentByName(String department) {
        return departmentRepository
                .findByName(department)
                .orElseThrow(
                        () -> new DataNotFound(String.format(Messages.ITEM_NOT_FOUND, "Department", department))
                );
    }

    @Override
    public void deleteDepartment(int id) {
        Department department = departmentRepository
                .findById(id)
                .orElseThrow(
                        () -> new DataNotFound(String.format(Messages.NOT_FOUND, "Department", id))
                );

        int numberOfEmployees = department.getEmployees().size();

        if (numberOfEmployees > 0) {
            throw new DataConflict(String.format(Messages.DELETE_PROFESSION_RESTRICTION, "Department", department.getName(), numberOfEmployees));
        }

        departmentRepository.deleteById(id);
    }

    @Override
    public List<DepartmentDto> getDepartments() {
        return convertDepartmentToDepartmentDtos(departmentRepository.findAll());
    }

    @Override
    public DepartmentDto getDepartment(int id) {
        Department department = departmentRepository
                .findById(id)
                .orElseThrow(
                        () -> new DataNotFound(String.format(Messages.NOT_FOUND, "Department", id))
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
