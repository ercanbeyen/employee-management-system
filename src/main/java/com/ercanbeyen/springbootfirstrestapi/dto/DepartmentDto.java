package com.ercanbeyen.springbootfirstrestapi.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DepartmentDto {
    private String name;
    private List<String> emails = new ArrayList<>();
}
