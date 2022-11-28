package com.ercanbeyen.employeemanagementsystem.service;

import com.ercanbeyen.employeemanagementsystem.entity.Statistics;

public interface StatisticsService {
    Statistics getDepartmentStatistics();
    Statistics getJobTitleStatistics();
    Statistics getSalaryStatistics();
    Statistics getRoleStatistics();
}
