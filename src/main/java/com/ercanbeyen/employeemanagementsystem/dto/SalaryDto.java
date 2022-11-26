package com.ercanbeyen.employeemanagementsystem.dto;

import com.ercanbeyen.employeemanagementsystem.entity.enums.Currency;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class SalaryDto {
    Currency currency;

    @Min(0)
    @NotNull(message = "Please enter an amount")
    Double amount;
}
