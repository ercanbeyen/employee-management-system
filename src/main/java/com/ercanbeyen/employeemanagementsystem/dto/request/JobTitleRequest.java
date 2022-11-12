package com.ercanbeyen.employeemanagementsystem.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class JobTitleRequest {
    @NotBlank(message = "Job Title name should not be blank")
    String name;
}
