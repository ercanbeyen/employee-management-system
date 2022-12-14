package com.ercanbeyen.employeemanagementsystem.service;

import com.ercanbeyen.employeemanagementsystem.dto.request.JobTitleRequest;
import com.ercanbeyen.employeemanagementsystem.dto.JobTitleDto;

import com.ercanbeyen.employeemanagementsystem.entity.JobTitle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface JobTitleService {
    JobTitleDto createJobTitle(JobTitleRequest request);
    JobTitleDto updateJobTitle(int id, JobTitleRequest request);
    JobTitle findJobTitleByName(String jobTitle);
    void deleteJobTitle(int id);
    List<JobTitleDto> getJobTitles();
    JobTitleDto getJobTitle(int id);
    List<JobTitle> getJobTitlesForStatistics();
    Page<JobTitle> pagination(int pageNumber, int pageSize);
    Page<JobTitle> pagination(Pageable pageable);
    Page<JobTitle> slice(Pageable pageable);
}
