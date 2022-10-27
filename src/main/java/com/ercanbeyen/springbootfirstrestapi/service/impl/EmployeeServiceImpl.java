package com.ercanbeyen.springbootfirstrestapi.service.impl;

import com.ercanbeyen.springbootfirstrestapi.entity.*;
import com.ercanbeyen.springbootfirstrestapi.entity.enums.Currency;
import com.ercanbeyen.springbootfirstrestapi.exception.ResourceNotFound;
import com.ercanbeyen.springbootfirstrestapi.dto.EmployeeDto;
import com.ercanbeyen.springbootfirstrestapi.repository.EmployeeRepository;
import com.ercanbeyen.springbootfirstrestapi.service.DepartmentService;
import com.ercanbeyen.springbootfirstrestapi.service.EmployeeService;
import com.ercanbeyen.springbootfirstrestapi.service.RoleService;
import com.ercanbeyen.springbootfirstrestapi.service.SalaryService;
import com.ercanbeyen.springbootfirstrestapi.util.CustomPage;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private final EmployeeRepository employeeRepository;
    @Autowired
    private final ModelMapper modelMapper;

    @Autowired
    private final DepartmentService departmentService;

    @Autowired
    private final RoleService roleService;

    @Autowired
    private final SalaryService salaryService;


    @Transactional
    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        log.debug("Employee creation is started");
        Employee employee = modelMapper.map(employeeDto, Employee.class);

        Department department = departmentService.assignDepartment(employeeDto.getDepartment());
        employee.setDepartment(department);
        log.debug("Department is assigned to the employee");

        Role role = roleService.assignRole(employeeDto.getRole());
        employee.setRole(role);
        log.debug("Role is assigned to the employee");

        Salary salary = salaryService.createSalary(employeeDto.getSalary());
        employee.setSalary(salary);
        log.debug("Salary is assigned to the user");

        Employee newEmployee = employeeRepository.save(employee);
        log.debug("Employee creation is completed");

        return modelMapper.map(newEmployee, EmployeeDto.class);
    }

    public List<EmployeeDto> filterEmployees(String department, String role, Currency currency, Integer limit) {
        log.debug("Employee filtering is started");
        List<Employee> employees = employeeRepository.findAll();

        if (department != null) {
            employees = employees
                    .stream()
                    .filter(employee -> employee.getDepartment().getName().equals(department))
                    .collect(Collectors.toList());

            log.debug("Employees are filtered by department called {}", department);

            if (role != null) {
                employees = employees
                        .stream()
                        .filter(employee -> employee.getRole().getName().equals(role))
                        .collect(Collectors.toList());

                log.debug("Employees are filtered by role called {}", role);
            }
        }

        if (currency != null) {
            employees = employees
                    .stream()
                    .filter(employee -> employee.getSalary().getCurrency().equals(currency))
                    .collect(Collectors.toList());

            log.debug("Employees are filtered by currency called {}", currency);

            if (limit != null) {
                employees = employees
                        .stream()
                        .sorted((employee1, employee2) -> {
                            double amount1 = employee1.getSalary().getAmount();
                            double amount2 = employee2.getSalary().getAmount();
                            return Double.compare(amount2, amount1);
                        })
                        .limit(limit)
                        .collect(Collectors.toList());

                log.debug("Employee with top {} salary is selected", limit);
            }

        }
        return modelMapper.map(employees, new TypeToken<List<EmployeeDto>>(){}.getType());
    }

    @Override
    public List<EmployeeDto> searchEmployees(String firstName, String lastName) {
        boolean isFirstNameFull = StringUtils.isNotBlank(firstName);
        boolean isLastNameFull = StringUtils.isNotBlank(lastName);
        List<Employee> employees;

        log.debug("Employee search operation is started");

        if (isFirstNameFull && isLastNameFull) { // search by first name and last name
            employees = employeeRepository.findByFirstNameAndLastName(firstName, lastName);
        }
        else if (isFirstNameFull) { // search by first name
            employees = employeeRepository.findByFirstName(firstName);
        }
        else { // search by last name
            employees = employeeRepository.findByLastName(lastName);
        }

        log.debug("Search operation is completed");

        return modelMapper.map(employees, new TypeToken<List<EmployeeDto>>(){}.getType());
    }

    @Override
    public EmployeeDto getEmployee(int id) {
        Optional<Employee> user = employeeRepository.findById(id);

        if (user.isPresent()) {
            return modelMapper.map(user.get(), EmployeeDto.class);
        }

        throw new ResourceNotFound("User with id " + id + " is not found");
    }

    @Transactional
    @Override
    public EmployeeDto updateEmployee(int id, EmployeeDto employeeDto) {

        log.debug("Employee update operation is started");

        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("Employee with id " + id + " is not found")
        );

        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setEmail(employeeDto.getEmail());
        employee.setContactNumber(employee.getContactNumber());
        employee.setNationality(employeeDto.getNationality());
        employee.setGender(employeeDto.getGender());
        log.debug("Employee details are updated");

        Department department = departmentService.assignDepartment(employeeDto.getDepartment());
        employee.setDepartment(department);
        log.debug("Department of the employee is updated to {}", department.getName());

        Role role = roleService.assignRole(employeeDto.getRole());
        employee.setRole(role);
        log.debug("Role of the employee is updated to {}", role.getName());

        Salary salary = salaryService.updateSalary(employee.getSalary().getId(), employeeDto.getSalary());
        employee.setSalary(salary);
        log.debug("Salary of the employee is updated; currency: {} and amount: {}", salary.getCurrency(), salary.getAmount());

        Employee updatedEmployee = employeeRepository.save(employee);
        log.debug("Employee update operation is completed");

        return modelMapper.map(updatedEmployee, EmployeeDto.class);
    }

    @Override
    public void deleteEmployee(int id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("Employee with id " + id + " is not found")
        );

        // Remove bidirectional connections between employee&department and employee&role
        employee.getDepartment().removeEmployee(employee);
        employee.setDepartment(null);
        employee.getRole().removeEmployee(employee);
        employee.setRole(null);
        log.debug("Bidirectional connections are removed");

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
        return new CustomPage<>(page, Arrays.asList(employees));
    }

}
