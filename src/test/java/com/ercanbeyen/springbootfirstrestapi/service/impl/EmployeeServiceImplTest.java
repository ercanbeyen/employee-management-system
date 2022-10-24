package com.ercanbeyen.springbootfirstrestapi.service.impl;

import com.ercanbeyen.springbootfirstrestapi.dto.EmployeeDto;
import com.ercanbeyen.springbootfirstrestapi.entity.*;
import com.ercanbeyen.springbootfirstrestapi.entity.enums.Currency;
import com.ercanbeyen.springbootfirstrestapi.entity.Salary;
import com.ercanbeyen.springbootfirstrestapi.exception.ResourceNotFound;
import com.ercanbeyen.springbootfirstrestapi.repository.EmployeeRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Spy
    private ModelMapper modelMapper;

    private List<EmployeeDto> getMockEmployeesDtos() {
        String nationality = "Turkey";
        String department = "IT";
        String role = "Developer";

        boolean status = true;
        Currency currency = Currency.TRY;
        int id = 1;

        Salary salary1 = new Salary();
        salary1.setId(id);
        salary1.setCurrency(currency);
        salary1.setAmount(3.4);

        EmployeeDto employee1 = new EmployeeDto();
        employee1.setFirstName("Test-FirstName1");
        employee1.setLastName("Test-LastName1");
        employee1.setEmail("test1@email.com");
        employee1.setNationality(nationality);

        id++;

        Salary salary2 = new Salary();
        salary2.setId(id);
        salary2.setCurrency(currency);
        salary2.setAmount(3.2);


        EmployeeDto employee2 = new EmployeeDto();
        employee2.setFirstName("Test-FirstName2");
        employee2.setLastName("Test-LastName2");
        employee2.setEmail("test2@email.com");
        employee2.setNationality(nationality);

        return Arrays.asList(employee1, employee2);
    }

    private List<Employee> getMockEmployees() {
        int id = 1;
        String nationality = "Turkey";

        Employee employee1 = new Employee();
        employee1.setId(id);
        employee1.setFirstName("Test-FirstName1");
        employee1.setLastName("Test-LastName1");
        employee1.setEmail("test1@email.com");
        employee1.setNationality(nationality);

        id++;

        Employee employee2 = new Employee();
        employee2.setId(id);
        employee2.setFirstName("Test-FirstName2");
        employee2.setLastName("Test-LastName2");
        employee2.setEmail("test2@email.com");
        employee2.setNationality(nationality);

        return Arrays.asList(employee1, employee2);
    }

    @Test
    @DisplayName("When Create Employee Called With UserDto It Should Return EmployeeDto")
    public void whenCreateEmployeeCalledWithValidUserDto_itShouldReturnEmployeeDto() {
        Employee employee = getMockEmployees().get(0);
        EmployeeDto employeeDto = getMockEmployeesDtos().get(0);

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
    public void whenGetEmployeeCalledWithValidId_itShouldReturnEmployeeDto() {
        Employee employee = getMockEmployees().get(0);
        EmployeeDto employeeDto = getMockEmployeesDtos().get(0);

        int id = 1;
        Optional<Employee> optionalEmployee = Optional.of(employee);

        Mockito.when(employeeRepository.findById(1)).thenReturn(optionalEmployee);
        Mockito.when(modelMapper.map(employee, EmployeeDto.class)).thenReturn(employeeDto);

        EmployeeDto result = employeeService.getEmployee(id);

        assertEquals(employeeDto, result);

        Mockito.verify(employeeRepository).findById(1);
        Mockito.verify(modelMapper).map(employee, EmployeeDto.class);
    }

    @Test
    @DisplayName("When FilterEmployees Called With Same Parameters It Should Return All EmployeesDtos")
    public void whenFilterEmployeesCalledWithNonNullParameters_itShouldReturnUserDtos() {
        List<EmployeeDto> employeeDtos = getMockEmployeesDtos();
        List<Employee> employees = getMockEmployees();

        String department = "IT";
        Currency currency = Currency.TRY;
        String role = "Developer";
        Integer limit = null;

        //Mockito.when(contractService.filterEmployees(department, role, currency, limit)).thenReturn(employees);
        Mockito.when(employeeRepository.findAll()).thenReturn(employees);

        Mockito.when(modelMapper.map(employees, new TypeToken<List<EmployeeDto>>(){}.getType())).thenReturn(employeeDtos);


        List<EmployeeDto> result = employeeService.filterEmployees(department, role, currency, limit);

        assertEquals(employeeDtos, result);

        Mockito.verify(employeeRepository).findAll();
        Mockito.verify(modelMapper).map(employees, new TypeToken<List<EmployeeDto>>(){}.getType());
    }

    @Test
    @DisplayName("When GetEmployees Called With Different Parameters It Should Return The Requested EmployeeDto")
    public void whenFilterEmployeesCalledWithDifferentParameters_itShouldReturnTheRequestedEmployeeDto() {
        String role = "Business Analyst";
        String department = "IT";
        Currency currency = Currency.TRY;
        int employeeIndex = 0;
        boolean status = true;
        Integer limit = null;

        List<EmployeeDto> employeeDtos = getMockEmployeesDtos();

        EmployeeDto employeeDto = employeeDtos.get(employeeIndex);

        List<Employee> employees = getMockEmployees();

        Employee employee = employees.get(employeeIndex);

        List<Employee> requestedEmployees = List.of(employee);
        List<EmployeeDto> requestedEmployeeDtos = List.of(employeeDto);

        Mockito.when(employeeRepository.findAll()).thenReturn(employees);;
        Mockito.when(modelMapper.map(requestedEmployees, new TypeToken<List<EmployeeDto>>(){}.getType())).thenReturn(requestedEmployeeDtos);


        List<EmployeeDto> result = employeeService.filterEmployees(department, role, currency, limit);

        assertEquals(result, requestedEmployeeDtos);

        Mockito.verify(employeeRepository).findAll();
        Mockito.verify(modelMapper).map(requestedEmployees, new TypeToken<List<EmployeeDto>>(){}.getType());
    }

    @Test
    @DisplayName("When UpdateEmployee Called With Valid Parameters It Should Return the EmployeeDto")
    public void whenUpdateEmployeeCalledWithValidParameters_itShouldReturnEmployeeDto() {
        int id = 0;

        Employee employee = getMockEmployees().get(0);
        EmployeeDto employeeDto = getMockEmployeesDtos().get(0);

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
        EmployeeDto employeeDto = getMockEmployeesDtos().get(0);
        Optional<Employee> optionalEmployee = Optional.empty();

        int id = 2;
        String message = "User with id " + id + " is not found";

        Mockito.when(employeeRepository.findById(id)).thenReturn(optionalEmployee);

        RuntimeException exception = assertThrows(ResourceNotFound.class, () -> {
            employeeService.updateEmployee(id, employeeDto);
        });

        assertEquals(message, exception.getMessage());

        Mockito.verify(employeeRepository).findById(id);
        Mockito.verifyNoInteractions(modelMapper);
    }


    @Test
    @DisplayName("When SearchEmployees Called With First name And Last name It Should Return Employee Dto")
    public void whenSearchEmployeeCalledWithFirstNameAndLastName_ItShouldReturnEmployeeDto() {
        String firstName = "Test-FirstName1";
        String lastName = "Test-LastName1";
        int employeeIndex = 0;

        List<EmployeeDto> employeeDtos = getMockEmployeesDtos();
        List<Employee> employees = getMockEmployees();

        List<Employee> targetEmployees = Collections.singletonList(employees.get(employeeIndex));
        List<EmployeeDto> targetEmployeeDtos = Collections.singletonList(employeeDtos.get(employeeIndex));

        Mockito.when(employeeRepository.findByFirstNameAndLastName(firstName, lastName)).thenReturn(targetEmployees);
        Mockito.when(modelMapper.map(targetEmployees, new TypeToken<List<EmployeeDto>>(){}.getType())).thenReturn(targetEmployeeDtos);

        List<EmployeeDto> result = employeeService.searchEmployees(firstName, lastName);

        assertEquals(result, targetEmployeeDtos);

        Mockito.verify(employeeRepository).findByFirstNameAndLastName(firstName, lastName);
        Mockito.verify(modelMapper).map(targetEmployees, new TypeToken<List<EmployeeDto>>(){}.getType());
    }
}