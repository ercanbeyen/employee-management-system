package com.ercanbeyen.springbootfirstrestapi.service.impl;

import com.ercanbeyen.springbootfirstrestapi.dto.UserDto;
import com.ercanbeyen.springbootfirstrestapi.entity.User;
import com.ercanbeyen.springbootfirstrestapi.repository.UserRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Spy
    //@Mock
    private ModelMapper modelMapper;

    @Test
    @DisplayName("When Create User Called With UserDto It Should Return UserDto")
    public void whenCreateUserCalledWithValidUserDto_itShouldReturnUserDto() {
        UserDto userDto = new UserDto();
        userDto.setFirstName("Test-FirstName");
        userDto.setLastName("Test-LastName");
        userDto.setEmail("test@email.com");
        userDto.setNationality("Test-Nation");
        userDto.setJob("Test-Developer");
        userDto.setGpa(3.2);

        User user = new User();
        user.setId(100L);
        user.setFirstName("Test-FirstName");
        user.setLastName("Test-LastName");
        userDto.setEmail("test@email.com");
        userDto.setNationality("Test-Nation");
        user.setJob("Test-Developer");
        user.setGpa(3.2);

        Mockito.when(modelMapper.map(userDto, User.class)).thenReturn(user);
        Mockito.when(userRepository.save(user)).thenReturn(user);
        Mockito.when(modelMapper.map(user, UserDto.class)).thenReturn(userDto);

        UserDto result = userService.createUser(userDto);

        assertEquals(result, userDto);
        Mockito.verify(modelMapper).map(userDto, User.class);
        Mockito.verify(userRepository).save(user);
        Mockito.verify(modelMapper).map(user, UserDto.class);
    }

    @Test
    @DisplayName("When Create User Called With Empty UserDto It Should Return Empty UserDto")
    public void whenCreateUserCalledWithEmptyUserDto_itShouldReturnEmptyUserDto() {
        UserDto userDto = new UserDto();
        User user = new User();

        Mockito.when(modelMapper.map(userDto, User.class)).thenReturn(user);
        Mockito.when(userRepository.save(user)).thenReturn(user);
        Mockito.when(modelMapper.map(user, UserDto.class)).thenReturn(userDto);

        UserDto result = userService.createUser(userDto);

        assertEquals(result, userDto);
        Mockito.verify(modelMapper).map(userDto, User.class);
        Mockito.verify(userRepository).save(user);
        Mockito.verify(modelMapper).map(user, UserDto.class);
    }

    @Test
    @DisplayName("When Create User Called With Valid It Should Return UserDto")
    public void whenCreateUserCalledWithValidId_itShouldReturnUserDto() {
        User user = new User();
        user.setId(100L);
        user.setFirstName("Test-FirstName");
        user.setLastName("Test-LastName");
        user.setEmail("test@email.com");
        user.setNationality("Test-Nation");
        user.setJob("Test-Developer");
        user.setGpa(3.2);

        UserDto userDto = new UserDto();
        userDto.setFirstName("Test-FirstName");
        userDto.setLastName("Test-LastName");
        userDto.setJob("Test-Developer");
        userDto.setEmail("test@email.com");
        userDto.setNationality("Test-Nation");
        userDto.setGpa(3.2);

        Optional<User> optionalUser = Optional.of(user);

        Mockito.when(userRepository.findById(100L)).thenReturn(optionalUser);
        Mockito.when(modelMapper.map(user, UserDto.class)).thenReturn(userDto);

        UserDto result = userService.getUser(100L);
        assertEquals(userDto, result);

        Mockito.verify(userRepository).findById(100L);
        Mockito.verify(modelMapper).map(user, UserDto.class);
    }
}