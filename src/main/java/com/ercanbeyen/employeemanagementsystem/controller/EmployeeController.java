package com.ercanbeyen.employeemanagementsystem.controller;

import com.ercanbeyen.employeemanagementsystem.constants.messages.Messages;
import com.ercanbeyen.employeemanagementsystem.dto.EmployeeDto;
import com.ercanbeyen.employeemanagementsystem.dto.SalaryDto;
import com.ercanbeyen.employeemanagementsystem.dto.request.UpdateEmployeeDetailsRequest;
import com.ercanbeyen.employeemanagementsystem.dto.request.UpdateProfessionRequest;
import com.ercanbeyen.employeemanagementsystem.dto.request.UpdateSalaryRequest;
import com.ercanbeyen.employeemanagementsystem.dto.response.ResponseHandler;

import com.ercanbeyen.employeemanagementsystem.constants.enums.Currency;
import com.ercanbeyen.employeemanagementsystem.entity.Employee;
import com.ercanbeyen.employeemanagementsystem.constants.enums.Role;
import com.ercanbeyen.employeemanagementsystem.util.CustomPage;
import com.ercanbeyen.employeemanagementsystem.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<Object> createEmployee(@Valid @RequestBody EmployeeDto employee) {
        EmployeeDto createdEmployee = employeeService.createEmployee(employee);
        return ResponseHandler.generateResponse(HttpStatus.CREATED, true, Messages.SUCCESS, createdEmployee);
    }

    @GetMapping("/filter")
    public ResponseEntity<Object> filterEmployees(
            @RequestParam(required = false) Role role,
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String jobTitle,
            @RequestParam(required = false) Currency currency,
            @RequestParam(required = false) Integer limit) {
        List<EmployeeDto> employees = employeeService.filterEmployees(role, department, jobTitle, currency, limit);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, employees);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchEmployees(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName) {
        List<EmployeeDto> employees = employeeService.searchEmployees(firstName, lastName);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getEmployee(@PathVariable("id") int id) {
        EmployeeDto employee = employeeService.getEmployee(id);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, employee);
    }

    @PutMapping("/{id}/details")
    public ResponseEntity<Object> updateEmployeeDetails(@PathVariable("id") int id, @Valid @RequestBody UpdateEmployeeDetailsRequest request) {
        EmployeeDto updatedEmployee = employeeService.updateEmployeeDetails(id, request);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, updatedEmployee);
    }

    @PutMapping("/{id}/occupation")
    public ResponseEntity<Object> updateOccupation(@PathVariable("id") int id, @Valid @RequestBody UpdateProfessionRequest request) {
        EmployeeDto employeeDto = employeeService.updateProfession(id, request);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, employeeDto);
    }

    @PutMapping("/{id}/salary")
    public ResponseEntity<Object> updateSalary(@PathVariable("id") int id, @Valid @RequestBody SalaryDto salaryDto) {
        EmployeeDto employeeDto = employeeService.updateSalary(id, salaryDto);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, employeeDto);
    }

    @PutMapping("/salaries")
    public ResponseEntity<Object> updateSalaries(@Valid @RequestBody UpdateSalaryRequest request) {
        List<EmployeeDto> employeeDtos = employeeService.updateSalaries(request);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, employeeDtos);
    }

    @GetMapping("/pagination")
    public ResponseEntity<Object> pagination(@RequestParam int currentPage, @RequestParam int pageSize) {
        Page<Employee> page = employeeService.pagination(currentPage, pageSize);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, page);
    }

    @GetMapping("/pagination/v1")
    public ResponseEntity<Object> pagination(Pageable pageable) {
        Page<Employee> page = employeeService.pagination(pageable);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, page);
    }

    @GetMapping("/pagination/v2")
    public ResponseEntity<Object> slice(Pageable pageable) {
        Slice<Employee> slice = employeeService.slice(pageable);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, slice);
    }

    @GetMapping("/pagination/v3")
    public ResponseEntity<Object> customPagination(Pageable pageable) {
        CustomPage<EmployeeDto> customPage = employeeService.customPagination(pageable);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, customPage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable("id") int id) {
        employeeService.deleteEmployee(id);
        return ResponseHandler.generateResponse(HttpStatus.NO_CONTENT, true, Messages.SUCCESS, null);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Object> uploadPhoto(@PathVariable("id") int id, @RequestParam("image") MultipartFile file) throws IOException {
        String uploadMessage = employeeService.uploadImage(id, file);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, uploadMessage);
    }

    @GetMapping("/{id}/{fileName}")
    public ResponseEntity<Object> downloadPhoto(@PathVariable("id") int id, @PathVariable("fileName") String fileName) {
        byte[] downloadedImage = employeeService.downloadImage(id, fileName);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(downloadedImage);
    }

}
