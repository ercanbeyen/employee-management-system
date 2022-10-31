package com.ercanbeyen.springbootfirstrestapi.controller;

import com.ercanbeyen.springbootfirstrestapi.dto.CreateDepartmentRequest;
import com.ercanbeyen.springbootfirstrestapi.dto.DepartmentDto;
import com.ercanbeyen.springbootfirstrestapi.dto.UpdateDepartmentRequest;
import com.ercanbeyen.springbootfirstrestapi.service.DepartmentService;
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
    public ResponseEntity<DepartmentDto> createDepartment(@RequestBody CreateDepartmentRequest createDepartmentRequest) {
        DepartmentDto createdDepartment = departmentService.createDepartment(createDepartmentRequest);
        return new ResponseEntity<>(createdDepartment, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDto>> getDepartments() {
        List<DepartmentDto> departments = departmentService.getDepartments();
        return ResponseEntity.ok(departments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDto> getDepartment(@PathVariable("id") int id) {
        DepartmentDto department = departmentService.getDepartment(id);
        return ResponseEntity.ok(department);
    }


    @PutMapping("/{id}")
    public ResponseEntity<DepartmentDto> updateDepartment(@PathVariable("id") int id, @RequestBody UpdateDepartmentRequest updateDepartmentRequest) {
        DepartmentDto updatedDepartment = departmentService.updateDepartment(id, updateDepartmentRequest);
        return ResponseEntity.ok(updatedDepartment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable("id") int id) {
        departmentService.deleteDepartment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
