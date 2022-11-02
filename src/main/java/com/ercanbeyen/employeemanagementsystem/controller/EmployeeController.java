package com.ercanbeyen.employeemanagementsystem.controller;

import com.ercanbeyen.employeemanagementsystem.dto.EmployeeDto;
import com.ercanbeyen.employeemanagementsystem.dto.response.ResponseHandler;
import com.ercanbeyen.employeemanagementsystem.entity.enums.Currency;
import com.ercanbeyen.employeemanagementsystem.entity.Employee;
import com.ercanbeyen.employeemanagementsystem.util.CustomPage;
import com.ercanbeyen.employeemanagementsystem.service.EmployeeService;
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
    public ResponseEntity<Object> createEmployee(@RequestBody @Valid EmployeeDto employee) {
        EmployeeDto createdEmployee = employeeService.createEmployee(employee);
        return ResponseHandler.generateResponse(HttpStatus.CREATED, true, "Success", createdEmployee);
    }

    @GetMapping("/filter")
    public ResponseEntity<Object> filterEmployees(
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) Currency currency,
            @RequestParam(required = false) Integer limit) {
        List<EmployeeDto> employees = employeeService.filterEmployees(department, role, currency, limit);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Success", employees);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchEmployees(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName) {
        List<EmployeeDto> employees = employeeService.searchEmployees(firstName, lastName);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Success", employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getEmployee(@PathVariable("id") int id) {
        EmployeeDto employee = employeeService.getEmployee(id);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Success", employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateEmployee(@PathVariable("id") int id, @RequestBody @Valid EmployeeDto user) {
        EmployeeDto updatedEmployee = employeeService.updateEmployee(id, user);
        //return ResponseEntity.ok(updatedEmployee);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Success", updatedEmployee);
    }

    @GetMapping("/pagination")
    public ResponseEntity<Object> pagination(@RequestParam int currentPage, @RequestParam int pageSize) {
        Page<Employee> page = employeeService.pagination(currentPage, pageSize);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Success", page);
    }

    @GetMapping("/pagination/v1")
    public ResponseEntity<Object> pagination(Pageable pageable) {
        Page<Employee> page = employeeService.pagination(pageable);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Success", page);
    }

    @GetMapping("/pagination/v2")
    public ResponseEntity<Object> slice(Pageable pageable) {
        Slice<Employee> slice = employeeService.slice(pageable);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Success", slice);
    }

    @GetMapping("/pagination/v3")
    public ResponseEntity<Object> customPagination(Pageable pageable) {
        CustomPage<EmployeeDto> customPage = employeeService.customPagination(pageable);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Success", customPage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable("id") int id) {
        employeeService.deleteEmployee(id);
        return ResponseHandler.generateResponse(HttpStatus.NO_CONTENT, true, "Success", null);
    }

}
