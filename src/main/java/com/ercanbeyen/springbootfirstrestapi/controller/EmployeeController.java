package com.ercanbeyen.springbootfirstrestapi.controller;

import com.ercanbeyen.springbootfirstrestapi.dto.EmployeeDto;
import com.ercanbeyen.springbootfirstrestapi.entity.enums.Currency;
import com.ercanbeyen.springbootfirstrestapi.entity.Employee;
import com.ercanbeyen.springbootfirstrestapi.util.CustomPage;
import com.ercanbeyen.springbootfirstrestapi.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody @Valid EmployeeDto employee) {
        EmployeeDto createdEmployee = employeeService.createEmployee(employee);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<EmployeeDto>> filterEmployees(
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) Currency currency,
            @RequestParam(required = false) Integer limit) {
        List<EmployeeDto> employees = employeeService.filterEmployees(department, role, currency, limit);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/search")
    public ResponseEntity<List<EmployeeDto>> searchEmployees(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName) {
        List<EmployeeDto> employees = employeeService.searchEmployees(firstName, lastName);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployee(@PathVariable("id") int id) {
        EmployeeDto employee = employeeService.getEmployee(id);
        return ResponseEntity.ok(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable("id") int id, @RequestBody @Valid EmployeeDto user) {
        EmployeeDto updatedEmployee = employeeService.updateEmployee(id, user);
        return ResponseEntity.ok(updatedEmployee);
    }

    @GetMapping("/pagination")
    public ResponseEntity<Page<Employee>> pagination(@RequestParam int currentPage, @RequestParam int pageSize) {
        Page<Employee> page = employeeService.pagination(currentPage, pageSize);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/pagination/v1")
    public ResponseEntity<Page<Employee>> pagination(Pageable pageable) {
        Page<Employee> page = employeeService.pagination(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/pagination/v2")
    public ResponseEntity<Slice<Employee>> slice(Pageable pageable) {
        Slice<Employee> slice = employeeService.slice(pageable);
        return ResponseEntity.ok(slice);
    }

    @GetMapping("/pagination/v3")
    public ResponseEntity<CustomPage<EmployeeDto>> customPagination(Pageable pageable) {
        CustomPage<EmployeeDto> customPage = employeeService.customPagination(pageable);
        return ResponseEntity.ok(customPage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable("id") int id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
