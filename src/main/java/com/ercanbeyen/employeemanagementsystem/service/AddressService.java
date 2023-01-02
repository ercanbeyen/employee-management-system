package com.ercanbeyen.employeemanagementsystem.service;

import com.ercanbeyen.employeemanagementsystem.dto.request.AddressDto;
import com.ercanbeyen.employeemanagementsystem.entity.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AddressService {
    Address createAddress(Address address);
    List<AddressDto> getAddresses();
    AddressDto getAddress(int id);
    void updateAddress(int id, Address address);
    Page<Address> pagination(int pageNumber, int pageSize);
    Page<Address> pagination(Pageable pageable);
    Page<Address> slice(Pageable pageable);
}
