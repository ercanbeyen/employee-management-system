package com.ercanbeyen.employeemanagementsystem.service;

import com.ercanbeyen.employeemanagementsystem.constants.enums.Currency;
import com.ercanbeyen.employeemanagementsystem.constants.enums.PaymentType;
import com.ercanbeyen.employeemanagementsystem.constants.enums.Role;
import com.ercanbeyen.employeemanagementsystem.constants.enums.ticket.TicketType;
import com.ercanbeyen.employeemanagementsystem.entity.Statistics;

import java.util.Map;

public interface StatisticsService {
    Statistics<String, Integer> getDepartmentStatistics();
    Statistics<String, Integer> getJobTitleStatistics();
    Statistics<Currency, Integer> getSalaryStatistics();
    Statistics<String, Map<Role, Integer>> getRoleStatistics();
    Statistics<String, Map<String, Integer>> getTicketStatistics();
    Statistics<PaymentType, Integer> getPaymentStatistics();
    Statistics<TicketType, Integer> getCommentStatistics();
}
