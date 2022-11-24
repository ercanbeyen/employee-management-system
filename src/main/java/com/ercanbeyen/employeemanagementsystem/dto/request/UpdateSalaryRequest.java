package com.ercanbeyen.employeemanagementsystem.dto.request;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class UpdateSalaryRequest {
    List<String> emails = null;

    @Min(-100)
    @Max(100)
    @NotNull(message = "Please enter a percentage")
    Double percentage;
}
