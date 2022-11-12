package com.ercanbeyen.employeemanagementsystem.service.impl;

import com.ercanbeyen.employeemanagementsystem.dto.request.CreateJobTitleRequest;
import com.ercanbeyen.employeemanagementsystem.dto.JobTitleDto;
import com.ercanbeyen.employeemanagementsystem.dto.request.UpdateJobTitleRequest;
import com.ercanbeyen.employeemanagementsystem.entity.JobTitle;
import com.ercanbeyen.employeemanagementsystem.exception.DataConflict;
import com.ercanbeyen.employeemanagementsystem.exception.DataNotFound;

import com.ercanbeyen.employeemanagementsystem.repository.JobTitleRepository;
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

    @Override
    public JobTitleDto createJobTitle(CreateJobTitleRequest createRoleRequest) {
        JobTitle jobTitle = new JobTitle();
        jobTitle.setName(createRoleRequest.getName());
        jobTitle.setLatestChangeAt(new Date());
        jobTitle.setLatestChangeBy("Admin");
        return convertRoleToRoleDto(jobTitleRepository.save(jobTitle));
    }

    @Override
    public JobTitleDto updateJobTitle(int id, UpdateJobTitleRequest updateRoleRequest) {
        String roleName = updateRoleRequest.getName();

        JobTitle jobTitleInDb = jobTitleRepository.findById(id).orElseThrow(
                () -> new DataNotFound("Role " + roleName + " is not found")
        );

        jobTitleInDb.setLatestChangeAt(new Date());
        jobTitleInDb.setLatestChangeBy("Admin");
        jobTitleInDb.setName(roleName);

        return convertRoleToRoleDto(jobTitleRepository.save(jobTitleInDb));
    }

    @Override
    public JobTitle assignJobTitle(String roleName) {
        return jobTitleRepository.findByName(roleName).orElseThrow(
                () -> new DataNotFound("Role " + roleName + " is not found")
        );
    }

    @Override
    public void deleteJobTitle(int id) {
        JobTitle jobTitle = jobTitleRepository.findById(id).orElseThrow(
                () -> new DataNotFound("Role wih id " + id + " is not found")
        );

        int numberOfEmployees = jobTitle.getEmployees().size();

        if (numberOfEmployees > 0) {
            throw new DataConflict(
                    "Role called " + jobTitle.getName() + " could not be deleted, because it contains "
                            + numberOfEmployees + " employee(s)"
            );
        }

        jobTitleRepository.deleteById(id);
    }

    @Override
    public List<JobTitleDto> getJobTitles() {
        return convertRolesToRoleDtos(jobTitleRepository.findAll());
    }

    @Override
    public JobTitleDto getJobTitle(int id) {
        JobTitle jobTitle = jobTitleRepository.findById(id).orElseThrow(
                () -> new DataNotFound("Role with id " + id + " is not found")
        );

        return convertRoleToRoleDto(jobTitle);
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
