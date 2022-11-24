package com.ercanbeyen.employeemanagementsystem.service.impl;

import com.ercanbeyen.employeemanagementsystem.dto.SalaryDto;
import com.ercanbeyen.employeemanagementsystem.dto.request.UpdateEmployeeDetailsRequest;
import com.ercanbeyen.employeemanagementsystem.dto.request.UpdateOccupationRequest;
import com.ercanbeyen.employeemanagementsystem.dto.request.UpdateSalaryRequest;
import com.ercanbeyen.employeemanagementsystem.entity.*;
import com.ercanbeyen.employeemanagementsystem.entity.enums.Currency;
import com.ercanbeyen.employeemanagementsystem.exception.DataNotFound;

import com.ercanbeyen.employeemanagementsystem.dto.EmployeeDto;
import com.ercanbeyen.employeemanagementsystem.repository.EmployeeRepository;
import com.ercanbeyen.employeemanagementsystem.service.*;
import com.ercanbeyen.employeemanagementsystem.util.CustomPage;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    private final JobTitleService jobTitleService;

    @Autowired
    private final SalaryService salaryService;

    @Autowired
    private final ImageService imageService;

    @Transactional
    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        log.debug("Employee creation is started");
        Employee employee = modelMapper.map(employeeDto, Employee.class);

        Department department = departmentService.assignDepartment(employeeDto.getDepartment());
        employee.setDepartment(department);
        log.debug("Department is assigned to the employee");

        JobTitle jobTitle = jobTitleService.assignJobTitle(employeeDto.getJobTitle());
        employee.setJobTitle(jobTitle);
        log.debug("Job Title is assigned to the employee");

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
                        .filter(employee -> employee.getJobTitle().getName().equals(role))
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

        throw new DataNotFound("Employee with id " + id + " is not found");
    }

    @Override
    public EmployeeDto updateEmployeeDetails(int id, UpdateEmployeeDetailsRequest request) {
        log.debug("Employee details update operation is started");

        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new DataNotFound("Employee with id " + id + " is not found")
        );

        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setPhoneNumber(employee.getPhoneNumber());
        employee.setNationality(request.getNationality());
        employee.setGender(request.getGender());
        log.debug("Employee details are updated");

        Employee updatedEmployee = employeeRepository.save(employee);
        log.debug("Employee update operation is completed");

        return modelMapper.map(updatedEmployee, EmployeeDto.class);
    }

    @Override
    public EmployeeDto updateSalary(int id, SalaryDto salaryDto) {
        log.debug("Employee salary update operation is started");

        Employee employee = employeeRepository
                .findById(id)
                .orElseThrow(
                        () -> new DataNotFound("Employee with id " + id + " is not found")
        );

        salaryService.updateSalary(employee.getSalary().getId(), salaryDto);
        log.debug("Salary of the employee with id {} is updated; currency: {} and amount: {}", employee.getId(), salaryDto.getCurrency(), salaryDto.getAmount());

        return modelMapper.map(employee, EmployeeDto.class);
    }

    @Override
    public List<EmployeeDto> updateSalaries(UpdateSalaryRequest request) {
        List<Employee> employees = new ArrayList<>();
        List<String> emails = request.getEmails();
        List<Salary> salaries = new ArrayList<>();

        /* Get Employees according to specified emails */
        for (String email : emails) {
            Employee employee = employeeRepository
                    .findByEmail(email)
                    .orElseThrow(
                            () -> new DataNotFound("Email " + email + " is not found")
                    );

            employees.add(employee);
            salaries.add(employee.getSalary());
        }

        salaryService.updateSalaries(salaries, request.getPercentage());

        return modelMapper.map(employees, new TypeToken<List<EmployeeDto>>(){}.getType());
    }

    @Transactional
    @Override
    public EmployeeDto updateOccupation(int id, UpdateOccupationRequest request) {
        log.debug("Employee's department and role updates are starting");

        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new DataNotFound("Employee with id " + id + " is not found")
        );

        Department updatedDepartment = departmentService.assignDepartment(request.getDepartment());
        employee.setDepartment(updatedDepartment);
        log.debug("Department is updated");

        JobTitle updatedJobTitle = jobTitleService.assignJobTitle(request.getRole());
        employee.setJobTitle(updatedJobTitle);
        log.debug("Role is updated");

        return modelMapper.map(employeeRepository.save(employee), EmployeeDto.class);
    }

    @Transactional
    @Override
    public String uploadImage(int id, MultipartFile file) throws IOException {
        log.debug("Upload image operation is started");

        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new DataNotFound("Employee with id " + id + " is not found")
        );

        log.debug("Employee is found, now upload the image");

        Image uploadedFile = imageService.uploadImage(file);
        employee.setPhoto(uploadedFile);
        employeeRepository.save(employee);

        return "image file called " + employee.getPhoto().getName() + " is successfully uploaded";
    }

    @Override
    public byte[] downloadImage(int id, String fileName) {
        log.debug("Download image operation is started");

        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new DataNotFound("Employee with id " + id + " is not found")
        );

        log.debug("Employee is found, now download the image");

        return imageService.downloadImage(fileName);
    }


    @Override
    public void deleteEmployee(int id) {
        log.debug("Delete employee operation is started");

        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new DataNotFound("Employee with id " + id + " is not found")
        );

        // Remove bidirectional connections between employee&department and employee&role
        employee.getDepartment().removeEmployee(employee);
        employee.setDepartment(null);
        employee.getJobTitle().removeEmployee(employee);
        employee.setJobTitle(null);
        log.debug("Bidirectional connections are removed");

        employeeRepository.deleteById(id);
        log.debug("Employee with id " + id + " is deleted");
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
