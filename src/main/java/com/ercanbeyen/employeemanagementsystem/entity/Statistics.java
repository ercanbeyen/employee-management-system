package com.ercanbeyen.employeemanagementsystem.entity;

import lombok.Data;

import java.util.Map;

@Data
public class Statistics <T, V> {
    private String minimum;
    private String maximum;
    private Double average;
    private Map<T, V> sizes;
}
