package com.ercanbeyen.employeemanagementsystem.service;

import com.ercanbeyen.employeemanagementsystem.dto.request.CreateJobTitleRequest;
import com.ercanbeyen.employeemanagementsystem.dto.JobTitleDto;
import com.ercanbeyen.employeemanagementsystem.dto.request.UpdateJobTitleRequest;
import com.ercanbeyen.employeemanagementsystem.entity.JobTitle;

import java.util.List;

public interface JobTitleService {
    JobTitleDto createJobTitle(CreateJobTitleRequest createRoleRequest);
    JobTitleDto updateJobTitle(int id, UpdateJobTitleRequest updateRoleRequest);
    JobTitle assignJobTitle(String role);
    void deleteJobTitle(int id);
    List<JobTitleDto> getJobTitles();
    JobTitleDto getJobTitle(int id);
}
