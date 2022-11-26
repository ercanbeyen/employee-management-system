package com.ercanbeyen.employeemanagementsystem.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public class BaseRequest {
    @NotBlank(message = "Name should not be empty")
    String name;
}
