package com.ercanbeyen.springbootfirstrestapi.service.impl;

import com.ercanbeyen.springbootfirstrestapi.entity.Employee;
import com.ercanbeyen.springbootfirstrestapi.exception.EmployeeForbidden;
import com.ercanbeyen.springbootfirstrestapi.exception.EmployeeNotFound;
import com.ercanbeyen.springbootfirstrestapi.dto.EmployeeDto;
import com.ercanbeyen.springbootfirstrestapi.repository.EmployeeRepository;
import com.ercanbeyen.springbootfirstrestapi.service.EmployeeService;
import com.ercanbeyen.springbootfirstrestapi.util.CustomPage;
import lombok.RequiredArgsConstructor;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private final EmployeeRepository employeeRepository;
    @Autowired
    private final ModelMapper modelMapper;

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        Employee employee = modelMapper.map(employeeDto, Employee.class);
        employee.setCreatedAt(new Date());
        employee.setCreatedBy("Admin");
        return modelMapper.map(employeeRepository.save(employee), EmployeeDto.class);
    }

    @Override
    public List<EmployeeDto> getEmployees(String department, String position, Optional<Integer> limit) {
        List<Employee> employees = employeeRepository
                .findAll()
                .stream()
                .filter(Employee::isActive)
                .collect(Collectors.toList());
        int userLimit;

        if (StringUtils.isNotBlank(department)) { // filter employees based on department
            employees = employees.stream().filter(employee -> employee.getDepartment().equals(department)).collect(Collectors.toList());
        }

        if (StringUtils.isNotBlank(position)) { // filter users based on job
            employees = employees.stream().filter(user -> user.getPosition().equals(position)).toList();
        }

        if (limit.isPresent()) { // get users with highest gpa
            userLimit = limit.get();
            employees = employees.stream().sorted(Comparator.comparing(Employee::getSalary).reversed()).limit(userLimit).collect(Collectors.toList());
        }

        return modelMapper.map(employees, new TypeToken<List<EmployeeDto>>(){}.getType());
    }

    @Override
    public EmployeeDto getEmployee(Long id) {
        Optional<Employee> user = employeeRepository.findById(id);

        if (user.isPresent()) {
            return modelMapper.map(user.get(), EmployeeDto.class);
        }

        throw new EmployeeNotFound("User with id " + id + " is not found");
    }

    @Override
    public EmployeeDto updateEmployee(Long id, EmployeeDto user) {
        Optional<Employee> requestedUser = employeeRepository.findById(id);

        if (requestedUser.isEmpty()) {
            throw new EmployeeNotFound("User with id " + id + " is not found");
        }

        Employee employeeInDb = requestedUser.get();

        if (!employeeInDb.isActive()) {
            throw new EmployeeForbidden("User with id " + id + " is deactivated");
        }

        employeeInDb.setFirstName(user.getFirstName());
        employeeInDb.setLastName(user.getLastName());
        employeeInDb.setNationality(user.getNationality());
        employeeInDb.setDepartment(user.getDepartment());
        employeeInDb.setPosition(user.getPosition());
        employeeInDb.setSalary(user.getSalary());
        employeeInDb.setUpdatedAt(new Date());
        employeeInDb.setUpdatedBy("Admin");
        return modelMapper.map(employeeRepository.save(employeeInDb), EmployeeDto.class);
    }

    @Override
    public void deleteUser(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public Page<Employee> pagination(int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        return employeeRepository.findAll(pageable);
    }

    @Override
    public Page<Employee> pagination(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    @Override
    public Page<Employee> slice(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    @Override
    public CustomPage<EmployeeDto> customPagination(Pageable pageable) {
        Page<Employee> page = employeeRepository.findAll(pageable);
        EmployeeDto[] employees = modelMapper.map(page.getContent(), EmployeeDto[].class);
        return new CustomPage<EmployeeDto>(page, Arrays.asList(employees));
    }

    @Override
    public void changeStatus(Long id) {
        employeeRepository.findById(id).ifPresentOrElse(employee -> {
            boolean previousStatus = employee.isActive();
            employee.setActive(!previousStatus);
            employeeRepository.save(employee);
        },
        () -> {
            throw new EmployeeNotFound("User with id " + id + " is not found");
        });
    }

}
