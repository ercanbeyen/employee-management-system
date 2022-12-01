package com.ercanbeyen.employeemanagementsystem.service.impl;

import com.ercanbeyen.employeemanagementsystem.constants.messages.Messages;
import com.ercanbeyen.employeemanagementsystem.dto.request.JobTitleRequest;
import com.ercanbeyen.employeemanagementsystem.dto.JobTitleDto;

import com.ercanbeyen.employeemanagementsystem.entity.JobTitle;
import com.ercanbeyen.employeemanagementsystem.exception.DataConflict;
import com.ercanbeyen.employeemanagementsystem.exception.DataNotFound;

import com.ercanbeyen.employeemanagementsystem.repository.JobTitleRepository;
import com.ercanbeyen.employeemanagementsystem.service.AuthenticationService;
import com.ercanbeyen.employeemanagementsystem.service.JobTitleService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobTitleServiceImpl implements JobTitleService {
    @Autowired
    private final JobTitleRepository jobTitleRepository;
    @Autowired
    private final AuthenticationService authenticationService;

    @Override
    public JobTitleDto createJobTitle(JobTitleRequest request) {
        JobTitle jobTitle = new JobTitle();
        jobTitle.setName(request.getName());

        jobTitle.setLatestChangeAt(new Date());
        String email = authenticationService.getEmail();
        jobTitle.setLatestChangeBy(email);

        return convertRoleToRoleDto(jobTitleRepository.save(jobTitle));
    }

    @Override
    public JobTitleDto updateJobTitle(int id, JobTitleRequest request) {
        String jobTitle = request.getName();

        JobTitle jobTitleInDb = jobTitleRepository
                .findById(id)
                .orElseThrow(
                        () -> new DataNotFound(String.format(Messages.ITEM_NOT_FOUND, "Job title", jobTitle))
                );

        jobTitleInDb.setLatestChangeAt(new Date());

        String email = authenticationService.getEmail();
        jobTitleInDb.setLatestChangeBy(email);

        jobTitleInDb.setName(jobTitle);

        return convertRoleToRoleDto(jobTitleRepository.save(jobTitleInDb));
    }

    @Override
    public JobTitle findJobTitleByName(String jobTitle) {
        return jobTitleRepository
                .findByName(jobTitle)
                .orElseThrow(
                        () -> new DataNotFound(String.format(Messages.ITEM_NOT_FOUND, "Job title", jobTitle))
                );
    }

    @Override
    public void deleteJobTitle(int id) {
        JobTitle jobTitle = jobTitleRepository
                .findById(id)
                .orElseThrow(
                        () -> new DataNotFound(String.format(Messages.NOT_FOUND, "Job title", id))
                );

        int numberOfEmployees = jobTitle.getEmployees().size();

        if (numberOfEmployees > 0) {
            throw new DataConflict(String.format(Messages.DELETE_PROFESSION_RESTRICTION, "Job title", jobTitle.getName(), numberOfEmployees));
        }

        jobTitleRepository.deleteById(id);
    }

    @Override
    public List<JobTitleDto> getJobTitles() {
        return convertRolesToRoleDtos(jobTitleRepository.findAll());
    }

    @Override
    public JobTitleDto getJobTitle(int id) {
        JobTitle jobTitle = jobTitleRepository
                .findById(id)
                .orElseThrow(
                        () -> new DataNotFound(String.format(Messages.NOT_FOUND, "Job title", id))
                );

        return convertRoleToRoleDto(jobTitle);
    }

    @Override
    public List<JobTitle> getJobTitlesForStatistics() {
        return jobTitleRepository.findAll();
    }

    List<JobTitleDto> convertRolesToRoleDtos(List<JobTitle> jobTitles) {
        List<JobTitleDto> jobTitleDtos = new ArrayList<>();

        for (JobTitle jobTitle : jobTitles) {
            JobTitleDto jobTitleDto = convertRoleToRoleDto(jobTitle);
            jobTitleDtos.add(jobTitleDto);
        }

        return jobTitleDtos;

    }

    JobTitleDto convertRoleToRoleDto(JobTitle jobTitle) {
        JobTitleDto jobTitleDto = new JobTitleDto();
        jobTitleDto.setName(jobTitle.getName());
        jobTitle.getEmployees().forEach(employee -> jobTitleDto.getEmails().add(employee.getEmail()));
        return jobTitleDto;
    }
}
