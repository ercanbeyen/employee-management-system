package com.ercanbeyen.employeemanagementsystem.controller;

import com.ercanbeyen.employeemanagementsystem.constants.messages.Messages;
import com.ercanbeyen.employeemanagementsystem.dto.response.ResponseHandler;
import com.ercanbeyen.employeemanagementsystem.entity.Statistics;
import com.ercanbeyen.employeemanagementsystem.service.StatisticsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {
    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/departments")
    public ResponseEntity<Object> getDepartmentStatistics() {
        Statistics statistics = statisticsService.getDepartmentStatistics();
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, statistics);
    }


    @GetMapping("/jobTitles")
    public ResponseEntity<Object> getJobTitlesStatistics() {
        Statistics statistics = statisticsService.getJobTitleStatistics();
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, statistics);
    }

    @GetMapping("/salaries")
    public ResponseEntity<Object> getSalaryStatistics() {
        Statistics statistics = statisticsService.getSalaryStatistics();
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, statistics);
    }

    @GetMapping("/roles")
    public ResponseEntity<Object> getRoleStatistics() {
        Statistics statistics = statisticsService.getRoleStatistics();
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, statistics);
    }
}
