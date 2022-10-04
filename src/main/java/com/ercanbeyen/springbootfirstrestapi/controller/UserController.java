package com.ercanbeyen.springbootfirstrestapi.controller;

import com.ercanbeyen.springbootfirstrestapi.dto.UserDto;
import com.ercanbeyen.springbootfirstrestapi.entity.User;
import com.ercanbeyen.springbootfirstrestapi.util.CustomPage;
import com.ercanbeyen.springbootfirstrestapi.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController // this api might be opened to outside
@RequestMapping("/users") // for specifying the broadcast address
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("")
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto user) {
        UserDto createdUser = userService.createUser(user);
        //return ResponseEntity.ok(createdUser);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<UserDto>> getUsers(@RequestParam(required = false) String job, @RequestParam(required = false) Optional<Integer> limit) {
        List<UserDto> users = userService.getUsers(job, limit);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") Long id) {
        UserDto user = userService.getUser(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long id, @RequestBody @Valid UserDto user) {
        UserDto updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/pagination")
    public ResponseEntity<Page<User>> pagination(@RequestParam int currentPage, @RequestParam int pageSize) {
        Page<User> page = userService.pagination(currentPage, pageSize);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/pagination/v1")
    public ResponseEntity<Page<User>> pagination(Pageable pageable) {
        Page<User> page = userService.pagination(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/pagination/v2")
    public ResponseEntity<Slice<User>> slice(Pageable pageable) {
        Slice<User> slice = userService.slice(pageable);
        return ResponseEntity.ok(slice);
    }

    @GetMapping("/pagination/v3")
    public ResponseEntity<CustomPage<UserDto>> customPagination(Pageable pageable) {
        CustomPage<UserDto> customPage = userService.customPagination(pageable);
        return ResponseEntity.ok(customPage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable("id") Long id) {
        Boolean status = userService.deleteUser(id);
        return ResponseEntity.ok(status);
        //return ResponseEntity.badRequest().build();
    }

}
