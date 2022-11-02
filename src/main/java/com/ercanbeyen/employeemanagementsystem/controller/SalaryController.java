package com.ercanbeyen.employeemanagementsystem.controller;

import com.ercanbeyen.employeemanagementsystem.dto.response.ResponseHandler;
import com.ercanbeyen.employeemanagementsystem.entity.Salary;
import com.ercanbeyen.employeemanagementsystem.service.SalaryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/salaries")
public class SalaryController {

    private final SalaryService salaryService;

    public SalaryController(SalaryService salaryService) {
        this.salaryService = salaryService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateSalary(int id, @RequestBody @Valid Salary salary) {
        Salary updatedSalary = salaryService.updateSalary(id, salary);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Success", updatedSalary);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getSalary(@PathVariable("id") int id) {
        Salary salary = salaryService.getSalary(id);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Success", salary);
    }

    @GetMapping
    public ResponseEntity<Object> getSalaries() {
        List<Salary> salaries = salaryService.getSalaries();
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Success", salaries);
    }
}
