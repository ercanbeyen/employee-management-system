package com.ercanbeyen.springbootfirstrestapi.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RoleDto {
    String name;
    List<String> emails = new ArrayList<>();
}
