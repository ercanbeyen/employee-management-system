package com.ercanbeyen.employeemanagementsystem.controller;

import com.ercanbeyen.employeemanagementsystem.dto.SalaryDto;
import com.ercanbeyen.employeemanagementsystem.dto.request.UpdateSalaryRequest;
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
    public ResponseEntity<Object> updateSalary(int id, @Valid @RequestBody SalaryDto salaryDto) {
        Salary updatedSalary = salaryService.updateSalary(id, salaryDto);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Success", updatedSalary);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getSalary(@PathVariable("id") int id) {
        SalaryDto salary = salaryService.getSalary(id);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Success", salary);
    }

    @GetMapping
    public ResponseEntity<Object> getSalaries() {
        List<SalaryDto> salaryDtos = salaryService.getSalaries();
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Success", salaryDtos);
    }

    @PutMapping
    public ResponseEntity<Object> updateSalaries(@Valid @RequestBody UpdateSalaryRequest request) {
        List<SalaryDto> salaryDtos = salaryService.updateSalaries(request);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Success", salaryDtos);
    }
}
