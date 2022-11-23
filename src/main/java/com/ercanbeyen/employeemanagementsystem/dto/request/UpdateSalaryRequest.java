package com.ercanbeyen.employeemanagementsystem.dto.request;

import com.ercanbeyen.employeemanagementsystem.entity.enums.Currency;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class UpdateSalaryRequest {
    Currency currency;

    @Min(0)
    @Max(100)
    @NotNull(message = "Please enter a percentage")
    Double percentage;
}
