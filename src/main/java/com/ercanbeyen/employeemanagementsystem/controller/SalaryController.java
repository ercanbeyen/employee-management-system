package com.ercanbeyen.employeemanagementsystem.controller;

import com.ercanbeyen.employeemanagementsystem.constants.messages.Messages;
import com.ercanbeyen.employeemanagementsystem.dto.SalaryDto;
import com.ercanbeyen.employeemanagementsystem.dto.response.ResponseHandler;
import com.ercanbeyen.employeemanagementsystem.entity.Salary;
import com.ercanbeyen.employeemanagementsystem.service.SalaryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salaries")
public class SalaryController {
    private final SalaryService salaryService;

    public SalaryController(SalaryService salaryService) {
        this.salaryService = salaryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getSalary(@PathVariable("id") int id) {
        SalaryDto salary = salaryService.getSalary(id);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, salary);
    }

    @GetMapping
    public ResponseEntity<Object> getSalaries() {
        List<SalaryDto> salaryDtos = salaryService.getSalaries();
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, salaryDtos);
    }

    @GetMapping("/pagination")
    public ResponseEntity<Object> pagination(@RequestParam int pageNumber, @RequestParam int pageSize) {
        Page<Salary> page = salaryService.pagination(pageNumber, pageSize);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, page);
    }

    @GetMapping("/pagination/v1")
    public ResponseEntity<Object> pagination(Pageable pageable) {
        Page<Salary> page = salaryService.pagination(pageable);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, page);
    }

    @GetMapping("/pagination/v2")
    public ResponseEntity<Object> slice(Pageable pageable) {
        Slice<Salary> slice = salaryService.slice(pageable);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, slice);
    }
}
