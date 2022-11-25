package com.ercanbeyen.employeemanagementsystem.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.util.ArrayList;
import java.util.List;

@MappedSuperclass
@Getter
@Setter
public class ProfessionDto {
    private String name;
    private List<String> emails = new ArrayList<>();
}
