package com.ercanbeyen.employeemanagementsystem.service;

import com.ercanbeyen.employeemanagementsystem.constants.enums.Role;
import com.ercanbeyen.employeemanagementsystem.entity.Statistics;

import java.util.Map;

public interface StatisticsService {
    Statistics<Integer> getDepartmentStatistics();
    Statistics<Integer> getJobTitleStatistics();
    Statistics<Integer> getSalaryStatistics();
    Statistics<Map<Role, Integer>> getRoleStatistics();
    Statistics<Map<String, Integer>> getTicketStatistics();
}
