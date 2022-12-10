package com.ercanbeyen.employeemanagementsystem.service.impl;

import com.ercanbeyen.employeemanagementsystem.constants.messages.Messages;
import com.ercanbeyen.employeemanagementsystem.dto.SalaryDto;
import com.ercanbeyen.employeemanagementsystem.dto.request.*;
import com.ercanbeyen.employeemanagementsystem.entity.*;
import com.ercanbeyen.employeemanagementsystem.constants.enums.Currency;
import com.ercanbeyen.employeemanagementsystem.constants.enums.Role;
import com.ercanbeyen.employeemanagementsystem.exception.DataConflict;
import com.ercanbeyen.employeemanagementsystem.exception.DataForbidden;
import com.ercanbeyen.employeemanagementsystem.exception.DataNotFound;

import com.ercanbeyen.employeemanagementsystem.dto.EmployeeDto;
import com.ercanbeyen.employeemanagementsystem.repository.EmployeeRepository;
import com.ercanbeyen.employeemanagementsystem.service.*;
import com.ercanbeyen.employeemanagementsystem.util.CustomPage;
import com.ercanbeyen.employeemanagementsystem.util.RoleUtils;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService, UserDetailsService {
    @Autowired
    private final EmployeeRepository employeeRepository;
    @Autowired
    private final ModelMapper modelMapper;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final DepartmentService departmentService;

    @Autowired
    private final JobTitleService jobTitleService;

    @Autowired
    private final SalaryService salaryService;

    @Autowired
    private final ImageService imageService;
    @Autowired
    private final AuthenticationService authenticationService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Employee employee = employeeRepository
                .findByEmail(email)
                .orElseThrow(
                        () -> new UsernameNotFoundException("Employee not found in the database")
                );

        log.info("Employee found in the database {}", email);

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(employee.getRole().toString()));

        return new org.springframework.security.core.userdetails.User(employee.getEmail(), employee.getPassword(), authorities);
    }

    @Transactional
    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        log.debug("Employee creation is started");
        Employee employee = modelMapper.map(employeeDto, Employee.class);

        Department department = departmentService.findDepartmentByName(employeeDto.getDepartment());
        employee.setDepartment(department);
        log.debug("Department is assigned to the employee");

        JobTitle jobTitle = jobTitleService.findJobTitleByName(employeeDto.getJobTitle());
        employee.setJobTitle(jobTitle);
        log.debug("Job Title is assigned to the employee");

        Salary salary = salaryService.createSalary(employeeDto.getSalary());
        employee.setSalary(salary);
        log.debug("Salary is assigned to the employee");

        String encodedPassword = getEncodedPassword(employeeDto.getPassword());
        employee.setPassword(encodedPassword);

        Employee newEmployee = employeeRepository.save(employee);
        log.debug("Employee creation is completed");

        return modelMapper.map(newEmployee, EmployeeDto.class);
    }

    public List<EmployeeDto> getEmployees(Role role, String department, String jobTitle, Currency currency, Boolean sort, Integer limit) {
        log.debug("Employee filtering is started");
        List<Employee> employees = employeeRepository.findAll();

        if (role != null) {
            employees = employees
                    .stream()
                    .filter(employee -> employee.getRole() == role)
                    .collect(Collectors.toList());
            log.debug("Employees are filtered by role");
        }

        if (department != null) {
            employees = employees
                    .stream()
                    .filter(employee -> employee.getDepartment().getName().equals(department))
                    .collect(Collectors.toList());

            log.debug("Employees are filtered by department called {}", department);

            if (jobTitle != null) {
                employees = employees
                        .stream()
                        .filter(employee -> employee.getJobTitle().getName().equals(jobTitle))
                        .collect(Collectors.toList());

                log.debug("Employees are filtered by job title called {}", jobTitle);
            }
        }

        if (currency != null) {
            employees = employees
                    .stream()
                    .filter(employee -> employee.getSalary().getCurrency().equals(currency))
                    .collect(Collectors.toList());

            log.debug("Employees are filtered by currency called {}", currency);
        }

        if (sort != null && sort) {
            employees = employees
                    .stream()
                    .sorted((employee1, employee2) -> {
                        double amount1 = employee1.getSalary().getAmount();
                        double amount2 = employee2.getSalary().getAmount();
                        return Double.compare(amount2, amount1);
                    })
                    .toList();

            log.debug("Employees are sorted by salary amount");

            if (limit != null) {
                employees = employees
                        .stream()
                        .limit(limit)
                        .toList();
                log.debug("Employees with top {} salary amount are selected", limit);
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
        } else if (isFirstNameFull) { // search by first name
            employees = employeeRepository.findByFirstName(firstName);
        } else { // search by last name
            employees = employeeRepository.findByLastName(lastName);
        }

        log.debug("Search operation is completed");

        return modelMapper.map(employees, new TypeToken<List<EmployeeDto>>(){}.getType());
    }

    @Override
    public EmployeeDto getEmployee(int id) {
        log.debug("Get employee operation is starting");

        Employee employee = employeeRepository
                .findById(id)
                .orElseThrow(
                        () -> new DataNotFound(String.format(Messages.NOT_FOUND, "Employee", id))
                );

        log.debug("Employee is retrieved successfully");

        /*String password = employee.getPassword();
        System.out.println("Password: " + password);*/

        return modelMapper.map(employee, EmployeeDto.class);
    }

    @Override
    public EmployeeDto updateEmployeeDetails(int id, UpdateEmployeeDetailsRequest request) {
        log.debug("Employee details update operation is started");

        Employee employee = employeeRepository
                .findById(id)
                .orElseThrow(
                        () -> new DataNotFound(String.format(Messages.NOT_FOUND, "Employee", id))
                );

        String loggedIn_email = authenticationService.getEmail();
        String loggedIn_role = authenticationService.getRole();

        if (!loggedIn_email.equals(employee.getEmail()) && !loggedIn_role.equals(String.valueOf(Role.ADMIN))) {
            throw new DataForbidden("Either you are not the owner of the account or you are not an admin");
        }

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
                        () -> new DataNotFound(String.format(Messages.NOT_FOUND, "Employee", id))
                );

        checkUpdateSalary(employee);

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
                            () -> new DataNotFound(String.format(Messages.ITEM_NOT_FOUND, "Email", email))
                    );

            checkUpdateSalary(employee);

            employees.add(employee);
            salaries.add(employee.getSalary());
        }

        salaryService.updateSalaries(salaries, request.getPercentage());

        return modelMapper.map(employees, new TypeToken<List<EmployeeDto>>(){}.getType());
    }

    @Transactional
    @Override
    public EmployeeDto updateProfession(int id, UpdateProfessionRequest request) {
        log.debug("Employee's department and job title updates are starting");

        Employee employee = employeeRepository
                .findById(id)
                .orElseThrow(
                        () -> new DataNotFound(String.format(Messages.NOT_FOUND, "Employee", id))
                );

        Department updatedDepartment = departmentService.findDepartmentByName(request.getDepartment());

        employee.setDepartment(updatedDepartment);
        employee.setRole(Role.USER); // for consistency in role logic
        log.debug("Department is updated");

        JobTitle updatedJobTitle = jobTitleService.findJobTitleByName(request.getJobTitle());
        employee.setJobTitle(updatedJobTitle);
        log.debug("Role is updated");

        return modelMapper.map(employeeRepository.save(employee), EmployeeDto.class);
    }

    @Override
    public EmployeeDto updateRole(int id, RoleRequest request) {
        log.debug("Employee's role update is starting");

        Employee employeeInDb = employeeRepository
                .findById(id)
                .orElseThrow(
                        () -> new DataNotFound(String.format(Messages.NOT_FOUND, "Employee", id))
                );

        Role role = request.getRole();
        RoleUtils.checkRoleUpdate(role, employeeInDb.getDepartment().getName());
        employeeInDb.setRole(role);

        return modelMapper.map(employeeRepository.save(employeeInDb), EmployeeDto.class);
    }

    @Override
    public String updatePassword(int id, String newPassword, String confirmationPassword) {
        log.debug("Update password operation is starting");

        Employee employee = employeeRepository
                .findById(id)
                .orElseThrow(
                        () -> new DataNotFound(String.format(Messages.NOT_FOUND, "Employee", id))
                );

        log.debug("Employee is found");

        if (!newPassword.equals(confirmationPassword)) {
            throw new DataConflict("Passwords are not matching");
        }

        log.debug("Passwords are matching");

        String loggedIn_role = authenticationService.getRole();
        String loggedIn_email = authenticationService.getEmail();

        log.debug("Email and role of the logged in user is retrieved");

        if (loggedIn_role.equals(String.valueOf(Role.ADMIN)) || loggedIn_email.equals(employee.getEmail())) {
            String encodedPassword = getEncodedPassword(newPassword);
            employee.setPassword(encodedPassword);
            employeeRepository.save(employee);
            return Messages.PASSWORD_UPDATE_SUCCESS;
        }

        throw new DataForbidden("Only admin or account owner may update the password");
    }

    @Transactional
    @Override
    public String uploadImage(int id, MultipartFile file) throws IOException {
        log.debug("Upload image operation is started");

        Employee employee = employeeRepository
                .findById(id)
                .orElseThrow(
                        () -> new DataNotFound(String.format(Messages.NOT_FOUND, "Employee", id))
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

        employeeRepository
                .findById(id)
                .orElseThrow(
                        () -> new DataNotFound(String.format(Messages.NOT_FOUND, "Employee", id))
                );

        log.debug("Employee is found, now download the image");

        return imageService.downloadImage(fileName);
    }


    @Override
    public void deleteEmployee(int id) {
        log.debug("Delete employee operation is started");

        Employee employee = employeeRepository
                .findById(id)
                .orElseThrow(
                        () -> new DataNotFound(String.format(Messages.NOT_FOUND, "Employee", id))
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

    @Override
    public List<Employee> getEmployeesForStatistics() {
        return employeeRepository.findAll();
    }

    @Override
    public List<Role> findRolesByDepartmentForStatistics(String department) {
        return findRolesByDepartment(department);
    }

    @Override
    public Employee getEmployeeByEmail(String email) {
        return employeeRepository
                .findByEmail(email)
                .orElseThrow(
                        () -> new DataNotFound(String.format(Messages.ITEM_NOT_FOUND, "Email", email))
                );
    }


    private List<Role> findRolesByDepartment(String department) {
        return employeeRepository
                .findAll()
                .stream()
                .filter(employee -> employee.getDepartment().getName().equals(department))
                .map(Employee::getRole)
                .toList();
    }

    private void checkUpdateSalary(Employee employee) {
        String loggedIn_email = authenticationService.getEmail();
        String loggedIn_role = authenticationService.getRole();
        Employee loggedIn_employee = getEmployeeByEmail(loggedIn_email);

        /* If role of the logged in employee is admin, then terminate the method */
        if (loggedIn_role.equals(String.valueOf(Role.ADMIN))) {
            return;
        }

        String employeeDepartment = employee.getDepartment().getName();
        String loggedIn_department = loggedIn_employee.getDepartment().getName();

        if (!employeeDepartment.equals(loggedIn_department)) { // employee is not in human resource department
            return;
        }

        String loggedIn_jobTitle = loggedIn_employee.getJobTitle().getName();

        /* Both user is in HR department */
        if (loggedIn_jobTitle.equals("Director")) { // only director may update the salary of his/her colleagues in the same department
            return;
        }

        throw new DataConflict("You cannot update the salaries of other colleagues in the same department as you");
    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
