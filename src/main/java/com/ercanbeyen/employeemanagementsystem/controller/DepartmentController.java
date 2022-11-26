package com.ercanbeyen.employeemanagementsystem.controller;


import com.ercanbeyen.employeemanagementsystem.constants.messages.Messages;
import com.ercanbeyen.employeemanagementsystem.dto.DepartmentDto;
import com.ercanbeyen.employeemanagementsystem.dto.request.DepartmentRequest;

import com.ercanbeyen.employeemanagementsystem.dto.response.ResponseHandler;
import com.ercanbeyen.employeemanagementsystem.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public ResponseEntity<Object> createDepartment(@RequestBody DepartmentRequest request) {
        DepartmentDto createdDepartment = departmentService.createDepartment(request);
        return ResponseHandler.generateResponse(HttpStatus.CREATED, true, Messages.SUCCESS, createdDepartment);
    }

    @GetMapping
    public ResponseEntity<Object> getDepartments() {
        List<DepartmentDto> departments = departmentService.getDepartments();
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, departments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getDepartment(@PathVariable("id") int id) {
        DepartmentDto department = departmentService.getDepartment(id);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, department);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> updateDepartment(@PathVariable("id") int id, @RequestBody DepartmentRequest request) {
        DepartmentDto updatedDepartment = departmentService.updateDepartment(id, request);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, updatedDepartment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteDepartment(@PathVariable("id") int id) {
        departmentService.deleteDepartment(id);
        return ResponseHandler.generateResponse(HttpStatus.NO_CONTENT, true, Messages.SUCCESS, null);
    }

}
