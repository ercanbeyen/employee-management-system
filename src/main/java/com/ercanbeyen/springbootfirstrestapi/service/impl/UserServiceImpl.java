package com.ercanbeyen.springbootfirstrestapi.service.impl;

import com.ercanbeyen.springbootfirstrestapi.dto.UserDto;
import com.ercanbeyen.springbootfirstrestapi.entity.User;
import com.ercanbeyen.springbootfirstrestapi.repository.UserRepository;
import com.ercanbeyen.springbootfirstrestapi.service.UserService;
import com.ercanbeyen.springbootfirstrestapi.util.CustomPage;
import lombok.RequiredArgsConstructor;
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
        //user.setId(100L);
        /*User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setJob(userDto.getJob());
        user.setGpa(userDto.getGpa());
        System.out.println(user);*/
        user.setCreatedAt(new Date());
        user.setCreatedBy("Admin");
        //return userRepository.save(user);
        UserDto resultDto = modelMapper.map(userRepository.save(user), UserDto.class);
        return resultDto;
        //return userDto;
    }

    @Override
    public List<UserDto> getUsers(String job, Optional<Integer> limit) {
        List<User> users = userRepository.findAll();
        int userLimit;

        if (job != null) { // filter users based on job
            users = users.stream().filter(user -> user.getJob().equals(job)).toList();
        }

        if (limit.isPresent()) { // get users with highest gpa
            userLimit = limit.get();
            users = users.stream().sorted(Comparator.comparing(User::getGpa).reversed()).limit(userLimit).collect(Collectors.toList());
        }

        List<UserDto> userDtos = users.stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
        return userDtos;
        //return userRepository.findAll();
    }

    @Override
    public UserDto getUser(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            //return user.get();
            return modelMapper.map(user.get(), UserDto.class);
        }

        //return null;
        //throw new IllegalArgumentException("User is not found");
        //throw new UserNotFound("User is not found");
        throw new RuntimeException("User is not found");
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
            //return userRepository.save(userInDb);
        }

        return null;
    }

    @Override
    public Boolean deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            userRepository.deleteById(id);
            return true;
        }

        return false;
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
