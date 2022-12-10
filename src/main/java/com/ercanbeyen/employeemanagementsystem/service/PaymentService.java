package com.ercanbeyen.employeemanagementsystem.service;

import com.ercanbeyen.employeemanagementsystem.dto.PaymentDto;

import java.util.List;

public interface PaymentService {
    PaymentDto createPayment(PaymentDto paymentDto);
    List<PaymentDto> getPayments();
    PaymentDto getPayment(int id);
    PaymentDto updatePayment(int id, PaymentDto paymentDto);
    void deletePayment(int id);
}
