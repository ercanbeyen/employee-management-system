package com.ercanbeyen.employeemanagementsystem.dto.response;

import lombok.Data;

import java.util.HashMap;

@Data
public class Statistics {
    private String minimum;
    private String maximum;
    private Double average;
    private HashMap<String, Integer> sizes;
}
