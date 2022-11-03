package com.ercanbeyen.employeemanagementsystem.dto;

import com.ercanbeyen.employeemanagementsystem.entity.enums.Currency;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SalaryDto {
    Currency currency;

    @NotNull(message = "Please enter an amount")
    double amount;
}
