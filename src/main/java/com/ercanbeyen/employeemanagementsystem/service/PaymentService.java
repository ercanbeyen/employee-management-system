package com.ercanbeyen.employeemanagementsystem.service;

import com.ercanbeyen.employeemanagementsystem.constants.enums.Currency;
import com.ercanbeyen.employeemanagementsystem.constants.enums.PaymentType;
import com.ercanbeyen.employeemanagementsystem.dto.PaymentDto;

import java.util.List;

public interface PaymentService {
    PaymentDto createPayment(PaymentDto paymentDto);
    List<PaymentDto> getPayments(PaymentType type, String email, Currency currency, Boolean sort, Integer limit);
    PaymentDto getPayment(int id);
    PaymentDto updatePayment(int id, PaymentDto paymentDto);
    void deletePayment(int id);
}