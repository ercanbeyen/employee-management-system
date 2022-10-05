package com.ercanbeyen.springbootfirstrestapi.service.impl;

import com.ercanbeyen.springbootfirstrestapi.exception.UserNotFound;
import com.ercanbeyen.springbootfirstrestapi.dto.UserDto;
import com.ercanbeyen.springbootfirstrestapi.entity.User;
import com.ercanbeyen.springbootfirstrestapi.repository.UserRepository;
import com.ercanbeyen.springbootfirstrestapi.service.UserService;
import com.ercanbeyen.springbootfirstrestapi.util.CustomPage;
import lombok.RequiredArgsConstructor;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor // same as constructor injection
public class UserServiceImpl implements UserService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        user.setCreatedAt(new Date());
        user.setCreatedBy("Admin");
        return modelMapper.map(userRepository.save(user), UserDto.class);
    }

    @Override
    public List<UserDto> getUsers(String nationality, String job, Optional<Integer> limit) {
        List<User> users = userRepository.findAll();
        int userLimit;

        if (StringUtils.isNotBlank(nationality)) {
            users = users.stream().filter(user -> user.getNationality().equals(nationality)).toList();
        }

        if (StringUtils.isNotBlank(job)) { // filter users based on job
            users = users.stream().filter(user -> user.getJob().equals(job)).toList();
        }

        if (limit.isPresent()) { // get users with highest gpa
            userLimit = limit.get();
            users = users.stream().sorted(Comparator.comparing(User::getGpa).reversed()).limit(userLimit).collect(Collectors.toList());
        }

        List<UserDto> userDtos = users.stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public UserDto getUser(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            return modelMapper.map(user.get(), UserDto.class);
        }

        throw new UserNotFound("User with id " + id + " is not found");
    }

    @Override
    public UserDto updateUser(Long id, UserDto user) {
        Optional<User> requestedUser = userRepository.findById(id);

        if (requestedUser.isPresent()) {
            User userInDb = requestedUser.get();
            userInDb.setFirstName(user.getFirstName());
            userInDb.setLastName(user.getLastName());
            userInDb.setUpdatedAt(new Date());
            userInDb.setUpdatedBy("Admin");
            return modelMapper.map(userRepository.save(userInDb), UserDto.class);
        }

        throw new UserNotFound("User with id " + id + " is not found");
    }

    @Override
    public Boolean deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            userRepository.deleteById(id);
            return true;
        }

        throw new UserNotFound("User with id " + id + " is not found");
    }

    @Override
    public Page<User> pagination(int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        return userRepository.findAll(pageable);
    }

    @Override
    public Page<User> pagination(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Page<User> slice(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public CustomPage<UserDto> customPagination(Pageable pageable) {
        Page<User> page = userRepository.findAll(pageable);
        UserDto[] userDtos = modelMapper.map(page.getContent(), UserDto[].class);
        return new CustomPage<UserDto>(page, Arrays.asList(userDtos));
    }

}
