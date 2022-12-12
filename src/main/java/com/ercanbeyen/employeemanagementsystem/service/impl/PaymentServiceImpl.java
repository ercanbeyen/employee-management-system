package com.ercanbeyen.employeemanagementsystem.service.impl;

import com.ercanbeyen.employeemanagementsystem.constants.enums.Currency;
import com.ercanbeyen.employeemanagementsystem.constants.enums.PaymentType;
import com.ercanbeyen.employeemanagementsystem.constants.messages.Messages;
import com.ercanbeyen.employeemanagementsystem.dto.PaymentDto;
import com.ercanbeyen.employeemanagementsystem.entity.Employee;
import com.ercanbeyen.employeemanagementsystem.entity.Payment;
import com.ercanbeyen.employeemanagementsystem.entity.Salary;
import com.ercanbeyen.employeemanagementsystem.exception.DataNotFound;
import com.ercanbeyen.employeemanagementsystem.repository.PaymentRepository;
import com.ercanbeyen.employeemanagementsystem.service.AuthenticationService;
import com.ercanbeyen.employeemanagementsystem.service.EmployeeService;
import com.ercanbeyen.employeemanagementsystem.service.PaymentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private final PaymentRepository paymentRepository;
    @Autowired
    private final ModelMapper modelMapper;
    @Autowired
    private final EmployeeService employeeService;
    @Autowired
    private final AuthenticationService authenticationService;

    @Override
    public PaymentDto createPayment(PaymentDto paymentDto) {
        log.debug("Payment creation is starting");
        Payment payment = new Payment();

        Employee employee = employeeService.getEmployeeByEmail(paymentDto.getEmail());
        payment.setEmployee(employee);
        log.debug("Employee is found");

        PaymentType type = paymentDto.getType();
        payment.setType(type);
        log.debug("Payment type is {}", type);

        double amount;
        Currency currency;

        if (type == PaymentType.SALARY) {
            Salary salary = employee.getSalary();
            amount = salary.getAmount();
            currency = salary.getCurrency();
        } else {
            amount = paymentDto.getAmount();
            currency = payment.getCurrency();
        }

        payment.setAmount(amount);
        payment.setCurrency(currency);
        log.debug("Amount and currency are assigned");

        String loggedIn_email = authenticationService.getEmail();
        payment.setLatestChangeBy(loggedIn_email);
        payment.setLatestChangeAt(new Date());

        Payment createdPayment = paymentRepository.save(payment);
        log.debug("Payment is created");

        return modelMapper.map(createdPayment, PaymentDto.class);
    }

    @Override
    public List<PaymentDto> getPayments(PaymentType type, String email, Currency currency, Boolean sort, Integer limit) {
        log.debug("Payment filtering is starting");
        List<Payment> payments = paymentRepository.findAll();

        if (type != null) {
            payments = payments
                    .stream()
                    .filter(payment -> payment.getType() == type)
                    .toList();
            log.debug("Payments are filtered based on type {}", type);
        }

        if (email != null) {
            payments = payments
                    .stream()
                    .filter(payment -> payment.getEmployee().getEmail().equals(email))
                    .toList();
            log.debug("Payments are filtered based on email {}", email);
        }

        if (currency != null) {
            payments = payments
                    .stream()
                    .filter(payment -> payment.getCurrency() == currency)
                    .toList();
            log.debug("Payments are filtered based on currency {}", currency);
        }

        if (sort != null && sort) {
            payments = payments
                    .stream()
                    .sorted((payment1, payment2) -> {
                        double amount1 = payment1.getAmount();
                        double amount2 = payment2.getAmount();
                        return Double.compare(amount2, amount1);
                    })
                    .toList();
            log.debug("Payments are sorted by amount");

            if (limit != null) {
                payments = payments
                        .stream()
                        .limit(limit)
                        .toList();
                log.debug("Get top {} payment based on amount", limit);
            }
        }

        return modelMapper.map(payments, new TypeToken<List<PaymentDto>>(){}.getType());
    }

    @Override
    public PaymentDto getPayment(int id) {
        Payment payment =  paymentRepository
                .findById(id)
                .orElseThrow(
                        () -> new DataNotFound(String.format(Messages.NOT_FOUND, "Payment", id))
                );

        return modelMapper.map(payment, PaymentDto.class);
    }

    @Override
    public PaymentDto updatePayment(int id, PaymentDto paymentDto) {
        Payment paymentInDb =  paymentRepository
                .findById(id)
                .orElseThrow(
                        () -> new DataNotFound(String.format(Messages.NOT_FOUND, "Payment", id))
                );

        log.debug("Payment is found");

        Employee employee = employeeService.getEmployeeByEmail(paymentDto.getEmail());
        log.debug("Employee is found");

        PaymentType type = paymentDto.getType();
        paymentInDb.setType(type);
        log.debug("Payment type is {}", type);

        double amount;
        Currency currency;

        if (type == PaymentType.SALARY) {
            Salary salary = employee.getSalary();
            amount = salary.getAmount();
            currency = salary.getCurrency();
        } else {
            amount = paymentDto.getAmount();
            currency = paymentDto.getCurrency();
        }

        paymentInDb.setAmount(amount);
        paymentInDb.setCurrency(currency);
        paymentInDb.setEmployee(employee);
        log.debug("Amount, currency and employee are assigned");

        Payment updatedPayment = paymentRepository.save(paymentInDb);
        log.debug("Payment is updated");

        return modelMapper.map(updatedPayment, PaymentDto.class);
    }

    @Override
    public void deletePayment(int id) {
        paymentRepository
                .findById(id)
                .orElseThrow(
                        () -> new DataNotFound(String.format(Messages.NOT_FOUND, "Payment", id))
                );

        paymentRepository.deleteById(id);
    }
}
