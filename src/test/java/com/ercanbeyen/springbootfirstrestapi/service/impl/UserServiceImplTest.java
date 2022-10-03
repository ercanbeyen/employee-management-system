package com.ercanbeyen.springbootfirstrestapi.service.impl;

import com.ercanbeyen.springbootfirstrestapi.dto.UserDto;
import com.ercanbeyen.springbootfirstrestapi.entity.User;
import com.ercanbeyen.springbootfirstrestapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

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

    /*
    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        modelMapper = Mockito.mock(ModelMapper.class);
        userService = new UserServiceImpl(userRepository, modelMapper);
    }
    */

    @Test
    public void whenCreateUserCalledWithValidUserDto_itShouldReturnUserDto() {
        UserDto userDto = new UserDto();
        userDto.setFirstName("Test-FirstName");
        userDto.setLastName("Test-LastName");
        userDto.setJob("Test-Developer");
        userDto.setGpa(3.2);

        User user = new User();
        user.setId(100L);
        user.setFirstName("Test-FirstName");
        user.setLastName("Test-LastName");
        user.setJob("Test-Developer");
        user.setGpa(3.2);
        //userMock.setId(100L);

        /*
        Mockito.when(userMock.getId()).thenReturn(100L);
        Mockito.when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(userMock);
        UserDto result = userService.createUser(userDto);

         */

        Mockito.when(modelMapper.map(userDto, User.class)).thenReturn(user);
        Mockito.when(userRepository.save(user)).thenReturn(user);
        Mockito.when(modelMapper.map(user, UserDto.class)).thenReturn(userDto);

        UserDto result = userService.createUser(userDto);

        assertEquals(result, userDto);
        Mockito.verify(modelMapper).map(userDto, User.class);
        Mockito.verify(userRepository).save(user);
        Mockito.verify(modelMapper).map(user, UserDto.class);

        /*
        assertEquals(userDto.getFirstName(), result.getFirstName());
        assertEquals(userDto.getClass() ,result.getClass());

         */
    }
}