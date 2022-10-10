package com.ercanbeyen.springbootfirstrestapi.controller;

import com.ercanbeyen.springbootfirstrestapi.dto.EmployeeDto;
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
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("")
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody @Valid EmployeeDto employee) {
        EmployeeDto createdEmployee = employeeService.createEmployee(employee);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<EmployeeDto>> getEmployees(
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String position,
            @RequestParam(required = false) String currency,
            @RequestParam(required = false) Optional<Integer> limit) {
        List<EmployeeDto> employees = employeeService.getEmployees(department, position, currency, limit);
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
        employeeService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/activations/{id}")
    public ResponseEntity<Void> changeStatus(@PathVariable("id") int id) {
        employeeService.changeStatus(id);
        //return ResponseEntity.ok().build();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
