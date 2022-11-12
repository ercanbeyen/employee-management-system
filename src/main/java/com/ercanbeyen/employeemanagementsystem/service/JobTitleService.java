package com.ercanbeyen.employeemanagementsystem.service;

import com.ercanbeyen.employeemanagementsystem.dto.request.JobTitleRequest;
import com.ercanbeyen.employeemanagementsystem.dto.JobTitleDto;

import com.ercanbeyen.employeemanagementsystem.entity.JobTitle;

import java.util.List;

public interface JobTitleService {
    JobTitleDto createJobTitle(JobTitleRequest request);
    JobTitleDto updateJobTitle(int id, JobTitleRequest request);
    JobTitle assignJobTitle(String role);
    void deleteJobTitle(int id);
    List<JobTitleDto> getJobTitles();
    JobTitleDto getJobTitle(int id);
}
