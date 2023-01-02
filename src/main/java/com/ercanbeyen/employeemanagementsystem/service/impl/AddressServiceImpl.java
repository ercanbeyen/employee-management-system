package com.ercanbeyen.employeemanagementsystem.service.impl;

import com.ercanbeyen.employeemanagementsystem.constants.messages.Messages;
import com.ercanbeyen.employeemanagementsystem.dto.request.AddressDto;
import com.ercanbeyen.employeemanagementsystem.entity.Address;
import com.ercanbeyen.employeemanagementsystem.exception.DataNotFound;
import com.ercanbeyen.employeemanagementsystem.repository.AddressRepository;
import com.ercanbeyen.employeemanagementsystem.service.AddressService;
import com.ercanbeyen.employeemanagementsystem.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    @Autowired
    private final AddressRepository addressRepository;
    @Autowired
    private final ModelMapper modelMapper;
    @Autowired
    private final AuthenticationService authenticationService;

    @Override
    public Address createAddress(Address address) {
        String loggedIn_email = authenticationService.getEmail();
        address.setLatestChangeBy(loggedIn_email);
        address.setLatestChangeAt(new Date());
        return addressRepository.save(address);
    }

    @Override
    public List<AddressDto> getAddresses() {
        List<Address> addresses = addressRepository.findAll();

        return modelMapper.map(addresses, new TypeToken<List<AddressDto>>(){}.getType());
    }

    @Override
    public AddressDto getAddress(int id) {
        Address addressInDb = addressRepository.findById(id)
                .orElseThrow(
                        () -> new DataNotFound(String.format(Messages.NOT_FOUND, "Address", id))
                );

        return modelMapper.map(addressInDb, AddressDto.class);
    }

    @Override
    public void updateAddress(int id, Address address) {
        Address addressInDb = addressRepository.findById(id)
                .orElseThrow(
                        () -> new DataNotFound(String.format(Messages.NOT_FOUND, "Address", id))
                );

        addressInDb.setCountry(address.getCountry());
        addressInDb.setCity(address.getCity());
        addressInDb.setPostCode(address.getPostCode());

        String loggedIn_email = authenticationService.getEmail();
        addressInDb.setLatestChangeBy(loggedIn_email);
        addressInDb.setLatestChangeAt(new Date());

        addressRepository.save(addressInDb);
    }

    @Override
    public Page<Address> pagination(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return addressRepository.findAll(pageable);
    }

    @Override
    public Page<Address> pagination(Pageable pageable) {
        return addressRepository.findAll(pageable);
    }

    @Override
    public Page<Address> slice(Pageable pageable) {
        return addressRepository.findAll(pageable);
    }

}
