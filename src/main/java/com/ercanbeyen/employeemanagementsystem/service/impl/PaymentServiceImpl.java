package com.ercanbeyen.employeemanagementsystem.service.impl;

import com.ercanbeyen.employeemanagementsystem.constants.messages.Messages;
import com.ercanbeyen.employeemanagementsystem.dto.PaymentDto;
import com.ercanbeyen.employeemanagementsystem.entity.Employee;
import com.ercanbeyen.employeemanagementsystem.entity.Payment;
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

        payment.setType(paymentDto.getType());
        payment.setAmount(paymentDto.getAmount());
        payment.setCurrency(paymentDto.getCurrency());

        String loggedIn_email = authenticationService.getEmail();
        payment.setLatestChangeBy(loggedIn_email);
        payment.setLatestChangeAt(new Date());

        Payment createdPayment = paymentRepository.save(payment);
        log.debug("Payment is created");

        return modelMapper.map(createdPayment, PaymentDto.class);
    }

    @Override
    public List<PaymentDto> getPayments() {
        return modelMapper.map(paymentRepository.findAll(), new TypeToken<List<PaymentDto>>(){}.getType());
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

        paymentInDb.setAmount(paymentDto.getAmount());
        paymentInDb.setCurrency(paymentDto.getCurrency());
        paymentInDb.setType(paymentDto.getType());
        paymentInDb.setEmployee(employee);

        Payment updatedPayment = paymentRepository.save(paymentInDb);
        log.debug("Payment is updated");

        return modelMapper.map(updatedPayment, PaymentDto.class);
    }

    @Override
    public void deletePayment(int id) {
        paymentRepository.deleteById(id);
    }
}
