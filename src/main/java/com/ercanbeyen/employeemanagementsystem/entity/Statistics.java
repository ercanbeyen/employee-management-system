package com.ercanbeyen.employeemanagementsystem.entity;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Statistics <T> {
    private String minimum;
    private String maximum;
    private Double average;
    private Map<String, T> sizes;
}
