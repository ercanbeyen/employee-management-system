package com.ercanbeyen.springbootfirstrestapi.service.impl;

import com.ercanbeyen.springbootfirstrestapi.dto.EmployeeDto;
import com.ercanbeyen.springbootfirstrestapi.entity.Employee;
import com.ercanbeyen.springbootfirstrestapi.exception.EmployeeNotFound;
import com.ercanbeyen.springbootfirstrestapi.repository.EmployeeRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Spy
    private ModelMapper modelMapper;

    @Test
    @DisplayName("When Create Employee Called With UserDto It Should Return EmployeeDto")
    public void whenCreateEmployeeCalledWithValidUserDto_itShouldReturnEmployeeDto() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("Test-FirstName");
        employee.setLastName("Test-LastName");
        employee.setEmail("test1@email.com");
        employee.setNationality("TestLand");
        employee.setPosition("Test-Developer");
        employee.setSalary(3.2);

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("Test-FirstName");
        employeeDto.setLastName("Test-LastName");
        employeeDto.setEmail("test1@email.com");
        employeeDto.setNationality("TestLand");
        employeeDto.setPosition("Test-Developer");
        employeeDto.setSalary(3.2);

        Mockito.when(modelMapper.map(employeeDto, Employee.class)).thenReturn(employee);
        Mockito.when(employeeRepository.save(employee)).thenReturn(employee);
        Mockito.when(modelMapper.map(employee, EmployeeDto.class)).thenReturn(employeeDto);

        EmployeeDto result = employeeService.createEmployee(employeeDto);

        assertEquals(result, employeeDto);
        Mockito.verify(modelMapper).map(employeeDto, Employee.class);
        Mockito.verify(employeeRepository).save(employee);
        Mockito.verify(modelMapper).map(employee, EmployeeDto.class);
    }

    @Test
    @DisplayName("When Create Employee Called With Empty EmployeeDto It Should Return Empty EmployeeDto")
    public void whenCreateEmployeeCalledWithEmptyUserDto_itShouldReturnEmptyEmployeeDto() {
        EmployeeDto employeeDto = new EmployeeDto();
        Employee employee = new Employee();

        Mockito.when(modelMapper.map(employeeDto, Employee.class)).thenReturn(employee);
        Mockito.when(employeeRepository.save(employee)).thenReturn(employee);
        Mockito.when(modelMapper.map(employee, EmployeeDto.class)).thenReturn(employeeDto);

        EmployeeDto result = employeeService.createEmployee(employeeDto);

        assertEquals(result, employeeDto);
        Mockito.verify(modelMapper).map(employeeDto, Employee.class);
        Mockito.verify(employeeRepository).save(employee);
        Mockito.verify(modelMapper).map(employee, EmployeeDto.class);
    }

    @Test
    @DisplayName("When Get Employee Called With Valid It Should Return EmployeeDto")
    public void whenGetEmployeeCalledWithValidId_itShouldReturnemployeeDto() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("Test-FirstName");
        employee.setLastName("Test-LastName");
        employee.setEmail("test1@email.com");
        employee.setNationality("TestLand");
        employee.setDepartment("IT");
        employee.setPosition("Test-Developer");
        employee.setSalary(3.2);

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("Test-FirstName");
        employeeDto.setLastName("Test-LastName");
        employeeDto.setEmail("test1@email.com");
        employeeDto.setNationality("TestLand");
        employeeDto.setDepartment("IT");
        employeeDto.setPosition("Test-Developer");
        employeeDto.setSalary(3.2);

        Optional<Employee> optionalEmployee = Optional.of(employee);

        Mockito.when(employeeRepository.findById(1L)).thenReturn(optionalEmployee);
        Mockito.when(modelMapper.map(employee, EmployeeDto.class)).thenReturn(employeeDto);

        EmployeeDto result = employeeService.getEmployee(1L);
        assertEquals(employeeDto, result);

        Mockito.verify(employeeRepository).findById(1L);
        Mockito.verify(modelMapper).map(employee, EmployeeDto.class);
    }

    @Test
    @DisplayName("When GetEmployees Called With Non Null Parameters It Should Return EmployeesDtos")
    public void whenGetUsersCalledWithNonNullParameters_itShouldReturnUserDtos() {
        EmployeeDto employeeDto1 = new EmployeeDto();
        employeeDto1.setFirstName("Test-FirstName1");
        employeeDto1.setLastName("Test-LastName1");
        employeeDto1.setEmail("test1@email.com");
        employeeDto1.setNationality("TestLand");
        employeeDto1.setActive(true);
        employeeDto1.setDepartment("IT");;
        employeeDto1.setPosition("Test-Developer");
        employeeDto1.setSalary(3.4);

        EmployeeDto employeeDto2 = new EmployeeDto();
        employeeDto2.setFirstName("Test-FirstName2");
        employeeDto2.setLastName("Test-LastName2");
        employeeDto2.setEmail("test2@email.com");
        employeeDto2.setNationality("TestLand");
        employeeDto2.setActive(true);
        employeeDto2.setDepartment("IT");
        employeeDto2.setPosition("Test-Developer");
        employeeDto2.setSalary(3.2);

        List<EmployeeDto> employeeDtos = Arrays.asList(employeeDto1, employeeDto2);

        Employee employee1 = new Employee();
        employee1.setId(1L);
        employee1.setFirstName("Test-FirstName1");
        employee1.setLastName("Test-LastName1");
        employee1.setEmail("test1@email.com");
        employee1.setActive(true);
        employee1.setNationality("TestLand");
        employee1.setDepartment("IT");
        employee1.setPosition("Test-Developer");
        employee1.setSalary(3.4);

        Employee employee2 = new Employee();
        employee2.setId(2L);
        employee2.setFirstName("Test-FirstName2");
        employee2.setLastName("Test-LastName2");
        employee2.setEmail("test2@email.com");
        employee2.setNationality("TestLand");
        employee2.setActive(true);
        employee2.setDepartment("IT");
        employee2.setPosition("Test-Developer");
        employee2.setSalary(3.2);

        List<Employee> employees = Arrays.asList(employee1, employee2);

        Optional<Integer> limit = Optional.of(2);

        Mockito.when(employeeRepository.findAll()).thenReturn(employees);

        //Mockito.when(modelMapper.map(users, UserDto.class)).thenReturn(userDtos); // Wrong
        Mockito.when(modelMapper.map(employees, new TypeToken<List<EmployeeDto>>(){}.getType())).thenReturn(employeeDtos);

        List<EmployeeDto> result = employeeService.getEmployees("IT", "Test-Developer", limit);

        assertEquals(employeeDtos, result);
        Mockito.verify(employeeRepository).findAll();

        Mockito.verify(modelMapper).map(employees, new TypeToken<List<EmployeeDto>>(){}.getType());
    }

    @Test
    @DisplayName("When UpdateEmployee Called With Valid Parameters It Should Return the EmployeeDto")
    public void whenUpdateEmployeeCalledWithValidParameters_itShouldReturnEmployeeDto() {

        Long id = 1L;

        Employee employee = new Employee();
        employee.setId(id);
        employee.setFirstName("Test-FirstName");
        employee.setLastName("Test-LastName");
        employee.setEmail("test@email.com");
        employee.setNationality("TestLand");
        employee.setActive(true);
        employee.setDepartment("IT");
        employee.setPosition("Test-Developer");
        employee.setSalary(3.2);

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("Test-FirstName");
        employeeDto.setLastName("Test-LastName");
        employeeDto.setEmail("test@email.com");
        employeeDto.setActive(true);
        employeeDto.setDepartment("IT");
        employeeDto.setNationality("TestLand");
        employeeDto.setPosition("Test-Developer");
        employeeDto.setSalary(3.2);

        Optional<Employee> optionalEmployee = Optional.of(employee);

        Mockito.when(employeeRepository.findById(id)).thenReturn(optionalEmployee);
        Mockito.when(employeeRepository.save(employee)).thenReturn(employee);
        Mockito.when(modelMapper.map(employee, EmployeeDto.class)).thenReturn(employeeDto);

        EmployeeDto result = employeeService.updateEmployee(id, employeeDto);

        assertEquals(result, employeeDto);

        Mockito.verify(employeeRepository).findById(id);
        Mockito.verify(employeeRepository).save(employee);
        Mockito.verify(modelMapper).map(employee, EmployeeDto.class);
    }

    @Test
    @DisplayName("When UpdateEmployee Called With Invalid Id It Should Throw Exception")
    public void whenUpdateEmployeeCalledWithInvalidId_itShouldThrowError() {

        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("Test-FirstName");
        employee.setLastName("Test-LastName");
        employee.setEmail("test@email.com");
        employee.setNationality("TestLand");
        employee.setActive(true);
        employee.setDepartment("IT");
        employee.setPosition("Test-Developer");
        employee.setSalary(3.2);

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("Test-FirstName");
        employeeDto.setLastName("Test-LastName");
        employeeDto.setEmail("test@email.com");
        employeeDto.setActive(true);
        employeeDto.setDepartment("IT");
        employeeDto.setNationality("TestLand");
        employeeDto.setPosition("Test-Developer");
        employeeDto.setSalary(3.2);

        Optional<Employee> optionalEmployee = Optional.empty();
        Long id = 2L;

        Mockito.when(employeeRepository.findById(id)).thenReturn(optionalEmployee);


        String message = "User with id " + id + " is not found";

        RuntimeException exception = assertThrows(EmployeeNotFound.class, () -> {
            employeeService.updateEmployee(id, employeeDto);
        });

        assertEquals(message, exception.getMessage());

        Mockito.verify(employeeRepository).findById(id);
        Mockito.verifyNoInteractions(modelMapper);
    }

    @Test
    @DisplayName("When ChangeStatus Is Called With Valid Id It Should Update The Status")
    public void whenChangeStatusIsCalledWithValidId_ItShouldUpdateTheStatus() {
        Long id = 1L;
        boolean previousStatus = false;

        Employee employee = new Employee();
        employee.setId(id);
        employee.setActive(previousStatus);

        Employee updatedEmployee = new Employee();
        updatedEmployee.setId(id);
        updatedEmployee.setActive(!previousStatus);

        Optional<Employee> optionalEmployee = Optional.of(employee);

        Mockito.when(employeeRepository.findById(id)).thenReturn(optionalEmployee);
        Mockito.when(employeeRepository.save(updatedEmployee)).thenReturn(updatedEmployee);

        employeeService.changeStatus(id);

        Mockito.verify(employeeRepository).findById(id);
        Mockito.verify(employeeRepository).save(employee);
    }
}