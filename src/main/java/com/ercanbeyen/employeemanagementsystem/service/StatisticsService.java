package com.ercanbeyen.employeemanagementsystem.service;

import com.ercanbeyen.employeemanagementsystem.dto.response.Statistics;

public interface StatisticsService {
    Statistics getDepartmentStatistics();
    Statistics getJobTitleStatistics();
    Statistics getSalaryStatistics();
}
