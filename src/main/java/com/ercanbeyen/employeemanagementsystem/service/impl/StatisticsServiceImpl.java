package com.ercanbeyen.employeemanagementsystem.service.impl;

import com.ercanbeyen.employeemanagementsystem.dto.response.Statistics;
import com.ercanbeyen.employeemanagementsystem.entity.Department;
import com.ercanbeyen.employeemanagementsystem.entity.JobTitle;
import com.ercanbeyen.employeemanagementsystem.entity.Salary;
import com.ercanbeyen.employeemanagementsystem.constants.enums.Currency;
import com.ercanbeyen.employeemanagementsystem.service.DepartmentService;
import com.ercanbeyen.employeemanagementsystem.service.JobTitleService;
import com.ercanbeyen.employeemanagementsystem.service.SalaryService;
import com.ercanbeyen.employeemanagementsystem.service.StatisticsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {
    @Autowired
    private final DepartmentService departmentService;

    @Autowired
    private final JobTitleService jobTitleService;

    @Autowired
    private final SalaryService salaryService;

    @Override
    public Statistics getDepartmentStatistics() {
        Statistics statistics = new Statistics();
        List<Department> departments = departmentService.getDepartmentsForStatistics();

        Department maximumDepartment = departments
                .stream()
                .max(Comparator.comparing(Department::getSize))
                .orElseThrow(NoSuchElementException::new);

        statistics.setMaximum(maximumDepartment.getName());

        Department minimumDepartment = departments
                .stream()
                .min(Comparator.comparing(Department::getSize))
                .orElseThrow(NoSuchElementException::new);

        statistics.setMinimum(minimumDepartment.getName());

        Double average = null;
        OptionalDouble optionalAverage = departments
                .stream()
                .mapToDouble(Department::getSize)
                .average();

        if (optionalAverage.isPresent()) {
            average = optionalAverage.getAsDouble();
        }

        statistics.setAverage(average);

        HashMap<String, Integer> map = new HashMap<>();
        for (Department department : departments) {
            map.put(department.getName(), department.getSize());
        }

        statistics.setSizes(map);

        return statistics;
    }

    @Override
    public Statistics getJobTitleStatistics() {
        Statistics statistics = new Statistics();
        List<JobTitle> jobTitles = jobTitleService.getJobTitlesForStatistics();

        JobTitle maximumJobTitle = jobTitles
                .stream()
                .max(Comparator.comparing(JobTitle::getSize))
                .orElseThrow(NoSuchElementException::new);

        statistics.setMaximum(maximumJobTitle.getName());

        JobTitle minimumJobTitle = jobTitles
                .stream()
                .min(Comparator.comparing(JobTitle::getSize))
                .orElseThrow(NoSuchElementException::new);

        statistics.setMinimum(minimumJobTitle.getName());

        Double average = null;

        OptionalDouble optionalAverage = jobTitles
                .stream()
                .mapToDouble(JobTitle::getSize)
                .average();

        if (optionalAverage.isPresent()) {
            average = optionalAverage.getAsDouble();
        }

        statistics.setAverage(average);

        HashMap<String, Integer> map = new HashMap<>();

        for (JobTitle jobTitle: jobTitles) {
            map.put(jobTitle.getName(), jobTitle.getSize());
        }

        statistics.setSizes(map);

        return statistics;
    }

    @Override
    public Statistics getSalaryStatistics() {
        Statistics statistics = new Statistics();
        List<Salary> salaries = salaryService.getSalariesForStatistics();

        Salary maximumSalary = salaries
                .stream()
                .max(Comparator.comparing(Salary::getAmount))
                .orElseThrow(NoSuchElementException::new);

        statistics.setMaximum(maximumSalary.getAmount().toString());

        Salary minimumSalary = salaries
                .stream()
                .min(Comparator.comparing(Salary::getAmount))
                .orElseThrow(NoSuchElementException::new);

        statistics.setMinimum(minimumSalary.getAmount().toString());

        Double average = null;

        OptionalDouble optionalAverage = salaries
                .stream()
                .mapToDouble(Salary::getAmount)
                .average();

        if (optionalAverage.isPresent()) {
            average = optionalAverage.getAsDouble();
        }

        statistics.setAverage(average);

        HashMap<String, Integer> map = new HashMap<>();

        map.put(String.valueOf(Currency.TRY), 0);
        map.put(String.valueOf(Currency.USD), 0);
        map.put(String.valueOf(Currency.EUR), 0);

        for (Salary salary : salaries) {
            String key = salary.getCurrency().toString();
            Integer value = map.get(key);
            map.put(key, ++value);
        }

        statistics.setSizes(map);

        return statistics;
    }
}
