package com.ercanbeyen.employeemanagementsystem.controller;

import com.ercanbeyen.employeemanagementsystem.dto.request.CreateDepartmentRequest;
import com.ercanbeyen.employeemanagementsystem.dto.DepartmentDto;
import com.ercanbeyen.employeemanagementsystem.dto.request.UpdateDepartmentRequest;
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
    public ResponseEntity<Object> createDepartment(@RequestBody CreateDepartmentRequest createDepartmentRequest) {
        DepartmentDto createdDepartment = departmentService.createDepartment(createDepartmentRequest);
        return ResponseHandler.generateResponse(HttpStatus.CREATED, true, "Success", createdDepartment);
    }

    @GetMapping
    public ResponseEntity<Object> getDepartments() {
        List<DepartmentDto> departments = departmentService.getDepartments();
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Success", departments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getDepartment(@PathVariable("id") int id) {
        DepartmentDto department = departmentService.getDepartment(id);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Success", department);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> updateDepartment(@PathVariable("id") int id, @RequestBody UpdateDepartmentRequest updateDepartmentRequest) {
        DepartmentDto updatedDepartment = departmentService.updateDepartment(id, updateDepartmentRequest);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Success", updatedDepartment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteDepartment(@PathVariable("id") int id) {
        departmentService.deleteDepartment(id);
        return ResponseHandler.generateResponse(HttpStatus.NO_CONTENT, true, "Success", null);
    }

}
