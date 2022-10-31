package com.ercanbeyen.springbootfirstrestapi.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class BaseDepartmentRequest {
    @NotBlank(message = "Department name should not be empty")
    String name;
}
