package com.ercanbeyen.employeemanagementsystem.controller;

import com.ercanbeyen.employeemanagementsystem.constants.messages.Messages;
import com.ercanbeyen.employeemanagementsystem.dto.request.AddressDto;
import com.ercanbeyen.employeemanagementsystem.dto.response.ResponseHandler;
import com.ercanbeyen.employeemanagementsystem.entity.Address;
import com.ercanbeyen.employeemanagementsystem.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
@RequiredArgsConstructor
public class AddressController {
    @Autowired
    private final AddressService addressService;

    @GetMapping
    public ResponseEntity<Object> getAddresses() {
        List<AddressDto> addressDtos = addressService.getAddresses();
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, addressDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getAddress(@PathVariable("id") int id) {
        AddressDto addressDto = addressService.getAddress(id);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, addressDto);
    }

    @GetMapping("/pagination")
    public ResponseEntity<Object> pagination(@RequestParam int pageNumber, @RequestParam int pageSize) {
        Page<Address> page = addressService.pagination(pageNumber, pageSize);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, page);
    }

    @GetMapping("/pagination/v1")
    public ResponseEntity<Object> pagination(Pageable pageable) {
        Page<Address> page = addressService.pagination(pageable);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, page);
    }

    @GetMapping("/pagination/v2")
    public ResponseEntity<Object> slice(Pageable pageable) {
        Slice<Address> slice = addressService.slice(pageable);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, Messages.SUCCESS, slice);
    }

}
