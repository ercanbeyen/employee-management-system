package com.ercanbeyen.springbootfirstrestapi.service;

import com.ercanbeyen.springbootfirstrestapi.dto.UserDto;
import com.ercanbeyen.springbootfirstrestapi.entity.User;
import com.ercanbeyen.springbootfirstrestapi.util.CustomPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDto createUser(UserDto user); // default types are public and abstract
    List<UserDto> getUsers(Optional<Integer> limit);
    UserDto getUser(Long id);
    UserDto updateUser(Long id, UserDto user);
    Boolean deleteUser(Long id);
    Page<User> pagination(int currentPage, int pageSize);
    Page<User> pagination(Pageable pageable);
    Page<User> slice(Pageable pageable);
    CustomPage<UserDto> customPagination(Pageable pageable);
}
