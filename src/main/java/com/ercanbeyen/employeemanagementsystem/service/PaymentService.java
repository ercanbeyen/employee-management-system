package com.ercanbeyen.employeemanagementsystem.service;

import com.ercanbeyen.employeemanagementsystem.constants.enums.Currency;
import com.ercanbeyen.employeemanagementsystem.constants.enums.PaymentType;
import com.ercanbeyen.employeemanagementsystem.dto.PaymentDto;
import com.ercanbeyen.employeemanagementsystem.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PaymentService {
    PaymentDto createPayment(PaymentDto paymentDto);
    List<PaymentDto> getPayments(PaymentType type, String email, Currency currency, Boolean sort, Integer limit);
    PaymentDto getPayment(int id);
    PaymentDto updatePayment(int id, PaymentDto paymentDto);
    void deletePayment(int id);
    List<Payment> getPaymentsForStatistics();
    Page<Payment> pagination(int pageNumber, int pageSize);
    Page<Payment> pagination(Pageable pageable);
    Page<Payment> slice(Pageable pageable);

}