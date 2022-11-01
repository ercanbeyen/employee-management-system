package com.ercanbeyen.employeemanagementsystem.controller;

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
    public ResponseEntity<Salary> updateSalary(int id, @RequestBody @Valid Salary salary) {
        Salary updatedSalary = salaryService.updateSalary(id, salary);
        return new ResponseEntity<>(updatedSalary, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Salary> getSalary(@PathVariable("id") int id) {
        Salary salary = salaryService.getSalary(id);
        return new ResponseEntity<>(salary, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Salary>> getSalaries() {
        List<Salary> salaries = salaryService.getSalaries();
        return new ResponseEntity<>(salaries, HttpStatus.OK);
    }
}
