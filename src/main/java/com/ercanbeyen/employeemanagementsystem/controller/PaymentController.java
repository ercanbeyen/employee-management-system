package com.ercanbeyen.employeemanagementsystem.controller;

import com.ercanbeyen.employeemanagementsystem.constants.enums.Currency;
import com.ercanbeyen.employeemanagementsystem.constants.enums.PaymentType;
import com.ercanbeyen.employeemanagementsystem.constants.messages.Messages;
import com.ercanbeyen.employeemanagementsystem.dto.PaymentDto;
import com.ercanbeyen.employeemanagementsystem.dto.response.ResponseHandler;
import com.ercanbeyen.employeemanagementsystem.entity.Payment;
import com.ercanbeyen.employeemanagementsystem.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    @Autowired
    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Object> createPayment(@Valid @RequestBody PaymentDto paymentDto) {
        PaymentDto createdPayment = paymentService.createPayment(paymentDto);
        return ResponseHandler.generateResponse(HttpStatus.CREATED, true, Messages.SUCCESS, createdPayment);
    }

    @GetMapping
    public ResponseEntity<Object> getPayments(
            @RequestParam(required = false) PaymentType type,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Currency currency,
            @RequestParam(required = false) Boolean sort,
            @RequestParam(required = false) Integer limit) {
        List<PaymentDto> paymentDtos = paymentService.getPayments(type, email, currency, sort, limit);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, paymentDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPayment(@PathVariable("id") int id) {
        PaymentDto paymentDto = paymentService.getPayment(id);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, paymentDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePayment(@PathVariable("id") int id, @Valid @RequestBody PaymentDto paymentDto) {
        PaymentDto updatedPayment = paymentService.updatePayment(id, paymentDto);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, updatedPayment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePayment(@PathVariable("id") int id) {
        paymentService.deletePayment(id);
        return ResponseHandler.generateResponse(HttpStatus.NO_CONTENT, true, Messages.SUCCESS, null);
    }

    @GetMapping("/pagination")
    public ResponseEntity<Object> pagination(@RequestParam int pageNumber, @RequestParam int pageSize) {
        Page<Payment> page = paymentService.pagination(pageNumber, pageSize);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, page);
    }

    @GetMapping("/pagination/v1")
    public ResponseEntity<Object> pagination(Pageable pageable) {
        Page<Payment> page = paymentService.pagination(pageable);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, page);
    }

    @GetMapping("/pagination/v2")
    public ResponseEntity<Object> slice(Pageable pageable) {
        Slice<Payment> slice = paymentService.slice(pageable);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, slice);
    }
}
