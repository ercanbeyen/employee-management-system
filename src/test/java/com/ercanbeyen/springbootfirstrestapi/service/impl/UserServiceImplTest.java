package com.ercanbeyen.springbootfirstrestapi.service.impl;

import com.ercanbeyen.springbootfirstrestapi.dto.UserDto;
import com.ercanbeyen.springbootfirstrestapi.entity.User;
import com.ercanbeyen.springbootfirstrestapi.repository.UserRepository;

import org.hibernate.mapping.Array;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        user.setId(1L);
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
    @DisplayName("When Get User Called With Valid It Should Return UserDto")
    public void whenGetUserCalledWithValidId_itShouldReturnUserDto() {
        User user = new User();
        user.setId(1L);
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

        Mockito.when(userRepository.findById(1L)).thenReturn(optionalUser);
        Mockito.when(modelMapper.map(user, UserDto.class)).thenReturn(userDto);

        UserDto result = userService.getUser(1L);
        assertEquals(userDto, result);

        Mockito.verify(userRepository).findById(1L);
        Mockito.verify(modelMapper).map(user, UserDto.class);
    }

    @Test
    @DisplayName("When GetUsers Called With Non Null Parameters It Should Return UserDtos")
    public void whenGetUsersCalledWithNonNullParameters_itShouldReturnUserDtos() {

        UserDto userDto1 = new UserDto();
        userDto1.setFirstName("Test-FirstName1");
        userDto1.setLastName("Test-LastName1");
        userDto1.setEmail("test1@email.com");
        userDto1.setNationality("Test-Nation");
        userDto1.setJob("Test-Developer");
        userDto1.setGpa(3.4);

        UserDto userDto2 = new UserDto();
        userDto2.setFirstName("Test-FirstName2");
        userDto2.setLastName("Test-LastName2");
        userDto2.setEmail("test2@email.com");
        userDto2.setNationality("Test-Nation");
        userDto2.setJob("Test-Developer");
        userDto2.setGpa(3.2);

        List<UserDto> userDtos = Arrays.asList(userDto1, userDto2);

        User user1 = new User();
        user1.setId(1L);
        user1.setFirstName("Test-FirstName1");
        user1.setLastName("Test-LastName1");
        user1.setEmail("test1@email.com");
        user1.setNationality("Test-Nation");
        user1.setJob("Test-Developer");
        user1.setGpa(3.4);

        User user2 = new User();
        user2.setId(2L);
        user2.setFirstName("Test-FirstName2");
        user2.setLastName("Test-LastName2");
        user2.setEmail("test2@email.com");
        user2.setNationality("Test-Nation");
        user2.setJob("Test-Developer");
        user2.setGpa(3.2);

        List<User> users = Arrays.asList(user1, user2);

        Optional<Integer> limit = Optional.of(2);

        Mockito.when(userRepository.findAll()).thenReturn(users);

        //Mockito.when(modelMapper.map(users, UserDto.class)).thenReturn(userDtos); // Wrong
        Mockito.when(modelMapper.map(users, new TypeToken<List<UserDto>>(){}.getType())).thenReturn(userDtos);

        /*
        // Works
        Mockito.when(modelMapper.map(user1, UserDto.class)).thenReturn(userDto1);
        Mockito.when(modelMapper.map(user2, UserDto.class)).thenReturn(userDto2);

         */

        List<UserDto> result = userService.getUsers("Test-Nation", "Test-Developer", limit);

        assertEquals(userDtos, result);
        Mockito.verify(userRepository).findAll();

        Mockito.verify(modelMapper).map(users, new TypeToken<List<UserDto>>(){}.getType());

        /*
        // Works
                Mockito.verify(modelMapper).map(user1, UserDto.class);
        Mockito.verify(modelMapper).map(user2, UserDto.class);
         */
    }
}