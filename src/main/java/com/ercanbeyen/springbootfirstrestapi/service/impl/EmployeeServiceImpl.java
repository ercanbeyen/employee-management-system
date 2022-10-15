package com.ercanbeyen.springbootfirstrestapi.service.impl;

import com.ercanbeyen.springbootfirstrestapi.entity.*;
import com.ercanbeyen.springbootfirstrestapi.entity.enums.Currency;
import com.ercanbeyen.springbootfirstrestapi.entity.enums.Department;
import com.ercanbeyen.springbootfirstrestapi.entity.enums.Role;
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

        Date createdDate = new Date();
        String createdBy = "Admin";

        employee.setOccupation(employeeDto.getOccupation());
        employee.setSalary(employeeDto.getSalary());

        employee.getOccupation().setCreatedAt(createdDate);
        employee.getOccupation().setCreatedBy(createdBy);

        employee.getSalary().setCreatedAt(createdDate);
        employee.getSalary().setCreatedBy(createdBy);

        employee.setCreatedAt(new Date());
        employee.setCreatedBy("Admin");

        return modelMapper.map(employeeRepository.save(employee), EmployeeDto.class);
    }

    @Override
    public List<EmployeeDto> filterEmployees(Department department, Role role, Currency currency, Optional<Integer> limit) {
        List<Employee> employees = employeeRepository.findAllByStatus(true);

        int userLimit;

        if (department != null) { // filter employees based on department
            employees = employees
                    .stream()
                    .filter(employee -> employee.getOccupation().getDepartment() == department)
                    .collect(Collectors.toList());
        }

        if (role != null) { // filter employees based on position
            employees = employees
                    .stream()
                    .filter(user -> user.getOccupation().getRole() == role)
                    .toList();
        }

        if (currency != null) {
            employees = employees
                    .stream()
                    .filter(employee -> employee.getSalary().getCurrency().equals(currency))
                    .toList();
        }

        if (limit.isPresent()) { // get employees with the highest salary
            userLimit = limit.get();
            employees = employees
                    .stream()
                    .sorted((employee1, employee2) -> {
                        double salaryAmount1 = employee1.getSalary().getAmount();
                        double salaryAmount2 = employee2.getSalary().getAmount();
                        return Double.compare(salaryAmount2, salaryAmount1); // reverse sorting with changing place of the parameters
                    })
                    .limit(userLimit)
                    .collect(Collectors.toList());
        }

        return modelMapper.map(employees, new TypeToken<List<EmployeeDto>>(){}.getType());
    }

    @Override
    public List<EmployeeDto> searchEmployees(String firstName, String lastName) {
        boolean isFirstNameFull = StringUtils.isNotBlank(firstName);
        boolean isLastNameFull = StringUtils.isNotBlank(lastName);
        List<Employee> employees;

        if (!isFirstNameFull && !isLastNameFull) {
            employees = new ArrayList<>();
        }
        else {
            if (isFirstNameFull && isLastNameFull) { // search by first name and last name
                employees = employeeRepository.findAllByFirstNameAndLastName(firstName, lastName);
            }
            else if (isFirstNameFull) { // search by first name
                employees = employeeRepository.findAllByFirstName(firstName);
            }
            else { // search by last name
                employees = employeeRepository.findAllByLastName(lastName);
            }
            employees = employees // at the end, filter by status
                    .stream()
                    .filter(Employee::isStatus)
                    .collect(Collectors.toList());
        }

        return modelMapper.map(employees, new TypeToken<List<EmployeeDto>>(){}.getType());
    }

    @Override
    public EmployeeDto getEmployee(int id) {
        Optional<Employee> user = employeeRepository.findById(id);

        if (user.isPresent()) {
            return modelMapper.map(user.get(), EmployeeDto.class);
        }

        throw new EmployeeNotFound("User with id " + id + " is not found");
    }

    @Override
    public EmployeeDto updateEmployee(int id, EmployeeDto employee) {
        Optional<Employee> requestedEmployee = employeeRepository.findById(id);

        if (requestedEmployee.isEmpty()) {
            throw new EmployeeNotFound("User with id " + id + " is not found");
        }

        Employee employeeInDb = requestedEmployee.get();

        if (!employeeInDb.isStatus()) {
            throw new EmployeeForbidden("User with id " + id + " is deactivated");
        }

        employeeInDb.setFirstName(employee.getFirstName());
        employeeInDb.setLastName(employee.getLastName());
        employeeInDb.setEmail(employee.getEmail());
        employeeInDb.setNationality(employee.getNationality());
        employeeInDb.setOccupation(employee.getOccupation());
        employeeInDb.setSalary(employee.getSalary());

        Date updatedDate = new Date();
        String updatedBy = "Admin";

        employeeInDb.getOccupation().setUpdatedAt(updatedDate);
        employeeInDb.getSalary().setUpdatedAt(updatedDate);
        employeeInDb.setUpdatedAt(updatedDate);
        employeeInDb.setUpdatedBy(updatedBy);

        return modelMapper.map(employeeRepository.save(employeeInDb), EmployeeDto.class);
    }

    @Override
    public void deleteEmployee(int id) {
        employeeRepository.findById(id).ifPresentOrElse(
                employee -> {
                    if (employee.isStatus()) {
                        throw new EmployeeForbidden("Active employees cannot be deleted");
                    }
                    employeeRepository.deleteById(id);
                    },
                () -> {
            throw new EmployeeNotFound("Employee with id " + id + " is not found");
                });
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
    public boolean changeStatus(int id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);

        if (optionalEmployee.isEmpty()) {
            throw new EmployeeNotFound("Employee with id " + id + " is not found");
        }

        Employee employee = optionalEmployee.get();
        boolean nextStatus = !employee.isStatus();
        employee.setStatus(nextStatus);
        employeeRepository.save(employee);

        return nextStatus;
    }

}
