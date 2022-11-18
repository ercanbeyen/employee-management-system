package com.ercanbeyen.employeemanagementsystem.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class JobTitleDto {
    String name;
    List<String> emails = new ArrayList<>();
}