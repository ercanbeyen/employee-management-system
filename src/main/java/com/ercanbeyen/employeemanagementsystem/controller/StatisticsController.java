package com.ercanbeyen.employeemanagementsystem.controller;

import com.ercanbeyen.employeemanagementsystem.constants.enums.Currency;
import com.ercanbeyen.employeemanagementsystem.constants.enums.PaymentType;
import com.ercanbeyen.employeemanagementsystem.constants.enums.Role;

import com.ercanbeyen.employeemanagementsystem.constants.enums.ticket.TicketType;
import com.ercanbeyen.employeemanagementsystem.constants.messages.Messages;
import com.ercanbeyen.employeemanagementsystem.dto.response.ResponseHandler;
import com.ercanbeyen.employeemanagementsystem.entity.Statistics;
import com.ercanbeyen.employeemanagementsystem.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticsController {
    @Autowired
    private final StatisticsService statisticsService;

    @GetMapping("/departments")
    public ResponseEntity<Object> getDepartmentStatistics() {
        Statistics<String, Integer> statistics = statisticsService.getDepartmentStatistics();
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, statistics);
    }


    @GetMapping("/jobTitles")
    public ResponseEntity<Object> getJobTitlesStatistics() {
        Statistics<String, Integer> statistics = statisticsService.getJobTitleStatistics();
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, statistics);
    }

    @GetMapping("/salaries")
    public ResponseEntity<Object> getSalaryStatistics() {
        Statistics<Currency, Integer> statistics = statisticsService.getSalaryStatistics();
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, statistics);
    }

    @GetMapping("/roles")
    public ResponseEntity<Object> getRoleStatistics() {
        Statistics<String, Map<Role, Integer>> statistics = statisticsService.getRoleStatistics();
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, statistics);
    }

    @GetMapping("/tickets")
    public ResponseEntity<Object> getTicketStatistics() {
        Statistics<String, Map<String, Integer>> statistics = statisticsService.getTicketStatistics();
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, statistics);
    }

    @GetMapping("/payments")
    public ResponseEntity<Object> getPaymentStatistics() {
        Statistics<PaymentType, Integer> statistics = statisticsService.getPaymentStatistics();
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, statistics);
    }

    @GetMapping("/comments")
    public ResponseEntity<Object> getCommentStatistics() {
        Statistics<TicketType, Integer> statistics = statisticsService.getCommentStatistics();
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, statistics);
    }

    @GetMapping("/addresses")
    public ResponseEntity<Object> getAddressStatistics() {
        Statistics<String, Integer> statistics = statisticsService.getAddressStatistics();
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, statistics);
    }
}
