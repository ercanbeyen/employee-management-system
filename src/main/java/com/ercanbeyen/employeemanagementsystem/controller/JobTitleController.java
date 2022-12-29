package com.ercanbeyen.employeemanagementsystem.controller;

import com.ercanbeyen.employeemanagementsystem.constants.messages.Messages;
import com.ercanbeyen.employeemanagementsystem.dto.request.JobTitleRequest;
import com.ercanbeyen.employeemanagementsystem.dto.JobTitleDto;

import com.ercanbeyen.employeemanagementsystem.dto.response.ResponseHandler;
import com.ercanbeyen.employeemanagementsystem.entity.Department;
import com.ercanbeyen.employeemanagementsystem.entity.JobTitle;
import com.ercanbeyen.employeemanagementsystem.service.JobTitleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobTitles")
public class JobTitleController {
    private final JobTitleService jobTitleService;

    public JobTitleController(JobTitleService jobTitleService) {
        this.jobTitleService = jobTitleService;
    }

    @PostMapping
    public ResponseEntity<Object> createJobTitle(@RequestBody JobTitleRequest request) {
        JobTitleDto createdRole = jobTitleService.createJobTitle(request);
        return ResponseHandler.generateResponse(HttpStatus.CREATED, true, Messages.SUCCESS, createdRole);
    }

    @GetMapping
    public ResponseEntity<Object> getJobTitles() {
        List<JobTitleDto> roles = jobTitleService.getJobTitles();
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getJobTitle(@PathVariable("id") int id) {
        JobTitleDto role = jobTitleService.getJobTitle(id);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, role);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateJobTitle(@PathVariable("id") int id, @RequestBody JobTitleRequest request) {
        JobTitleDto updatedRole = jobTitleService.updateJobTitle(id, request);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, updatedRole);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteJobTitle(@PathVariable("id") int id) {
        jobTitleService.deleteJobTitle(id);
        return ResponseHandler.generateResponse(HttpStatus.NO_CONTENT, true, Messages.SUCCESS, null);
    }

    @GetMapping("/pagination")
    public ResponseEntity<Object> pagination(@RequestParam int pageNumber, @RequestParam int pageSize) {
        Page<JobTitle> page = jobTitleService.pagination(pageNumber, pageSize);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, page);
    }

    @GetMapping("/pagination/v1")
    public ResponseEntity<Object> pagination(Pageable pageable) {
        Page<JobTitle> page = jobTitleService.pagination(pageable);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, page);
    }

    @GetMapping("/pagination/v2")
    public ResponseEntity<Object> slice(Pageable pageable) {
        Slice<JobTitle> slice = jobTitleService.slice(pageable);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, slice);
    }
}
