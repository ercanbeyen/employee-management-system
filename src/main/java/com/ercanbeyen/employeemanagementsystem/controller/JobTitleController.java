package com.ercanbeyen.employeemanagementsystem.controller;

import com.ercanbeyen.employeemanagementsystem.dto.request.JobTitleRequest;
import com.ercanbeyen.employeemanagementsystem.dto.JobTitleDto;

import com.ercanbeyen.employeemanagementsystem.dto.response.ResponseHandler;
import com.ercanbeyen.employeemanagementsystem.service.JobTitleService;
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
    public ResponseEntity<Object> createRole(@RequestBody JobTitleRequest request) {
        JobTitleDto createdRole = jobTitleService.createJobTitle(request);
        return ResponseHandler.generateResponse(HttpStatus.CREATED, true, "Success", createdRole);
    }

    @GetMapping
    public ResponseEntity<Object> getRoles() {
        List<JobTitleDto> roles = jobTitleService.getJobTitles();
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Success", roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getRole(@PathVariable("id") int id) {
        JobTitleDto role = jobTitleService.getJobTitle(id);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Success", role);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateRole(@PathVariable("id") int id, @RequestBody JobTitleRequest request) {
        JobTitleDto updatedRole = jobTitleService.updateJobTitle(id, request);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Success", updatedRole);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRole(@PathVariable("id") int id) {
        jobTitleService.deleteJobTitle(id);
        return ResponseHandler.generateResponse(HttpStatus.NO_CONTENT, true, "Success", null);
    }
}
