package com.ercanbeyen.springbootfirstrestapi.service.impl;

import com.ercanbeyen.springbootfirstrestapi.dto.EmployeeDto;
import com.ercanbeyen.springbootfirstrestapi.entity.*;
import com.ercanbeyen.springbootfirstrestapi.entity.enums.Currency;
import com.ercanbeyen.springbootfirstrestapi.entity.enums.Department;
import com.ercanbeyen.springbootfirstrestapi.entity.enums.Level;
import com.ercanbeyen.springbootfirstrestapi.entity.enums.Role;
import com.ercanbeyen.springbootfirstrestapi.entity.enums.Salary;
import com.ercanbeyen.springbootfirstrestapi.exception.EmployeeNotFound;
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
        Department department = Department.IT;
        Role role = Role.DEVELOPER;
        Level level = Level.BEGINNER;
        boolean status = true;
        Currency currency = Currency.TRY;
        int id = 1;

        Occupation occupation1 = new Occupation();
        occupation1.setId(id);
        occupation1.setDepartment(department);
        occupation1.setRole(role);
        occupation1.setLevel(level);

        Salary salary1 = new Salary();
        salary1.setId(id);
        salary1.setCurrency(currency);
        salary1.setAmount(3.4);

        EmployeeDto employee1 = new EmployeeDto();
        employee1.setFirstName("Test-FirstName1");
        employee1.setLastName("Test-LastName1");
        employee1.setEmail("test1@email.com");
        employee1.setNationality(nationality);
        employee1.setActive(status);
        employee1.setOccupation(occupation1);
        employee1.setSalary(salary1);

        id++;

        Occupation occupation2 = new Occupation();
        occupation2.setId(id);
        occupation2.setDepartment(department);
        occupation2.setRole(role);
        occupation2.setLevel(level);

        Salary salary2 = new Salary();
        salary2.setId(id);
        salary2.setCurrency(currency);
        salary2.setAmount(3.2);


        EmployeeDto employee2 = new EmployeeDto();
        employee2.setFirstName("Test-FirstName2");
        employee2.setLastName("Test-LastName2");
        employee2.setEmail("test2@email.com");
        employee2.setNationality(nationality);
        employee2.setActive(status);
        employee2.setOccupation(occupation2);
        employee2.setSalary(salary2);

        return Arrays.asList(employee1, employee2);
    }

    private List<Employee> getMockEmployees() {
        int id = 1;
        String nationality = "Turkey";
        Department department = Department.IT;
        Role role = Role.DEVELOPER;
        Level level = Level.BEGINNER;
        boolean status = true;
        Date createdAt = new Date();
        String createdBy = "Admin";

        Currency currency = Currency.TRY;

        Occupation occupation1 = new Occupation();
        occupation1.setId(id);
        occupation1.setDepartment(department);
        occupation1.setRole(role);
        occupation1.setLevel(level);

        Salary salary1 = new Salary();
        salary1.setId(id);
        salary1.setAmount(3.4);
        salary1.setCurrency(currency);

        Employee employee1 = new Employee();
        employee1.setId(id);
        employee1.setFirstName("Test-FirstName1");
        employee1.setLastName("Test-LastName1");
        employee1.setEmail("test1@email.com");
        employee1.setNationality(nationality);
        employee1.setStatus(status);
        employee1.setOccupation(occupation1);
        employee1.setSalary(salary1);
        employee1.setCreatedAt(createdAt);
        employee1.setCreatedBy(createdBy);

        id++;

        Occupation occupation2 = new Occupation();
        occupation2.setDepartment(department);
        occupation2.setId(id);
        occupation2.setRole(role);
        occupation2.setLevel(level);

        Salary salary2 = new Salary();
        salary2.setId(id);
        salary2.setAmount(3.2);
        salary2.setCurrency(currency);

        Employee employee2 = new Employee();
        employee2.setId(id);
        employee2.setFirstName("Test-FirstName2");
        employee2.setLastName("Test-LastName2");
        employee2.setEmail("test2@email.com");
        employee2.setNationality(nationality);
        employee2.setStatus(status);
        employee2.setOccupation(occupation2);
        employee2.setSalary(salary2);
        employee2.setCreatedAt(createdAt);
        employee2.setCreatedBy(createdBy);

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
    @DisplayName("When FilterEmployees Called With Same Non Null Parameters It Should Return Both EmployeesDtos")
    public void whenFilterEmployeesCalledWithNonNullParameters_itShouldReturnUserDtos() {
        List<EmployeeDto> employeeDtos = getMockEmployeesDtos();
        List<Employee> employees = getMockEmployees();

        boolean status = true;
        int threshold = 2;

        Optional<Integer> limit = Optional.of(threshold);

        Mockito.when(employeeRepository.findAllByStatus(status)).thenReturn(employees);

        Mockito.when(modelMapper.map(employees, new TypeToken<List<EmployeeDto>>(){}.getType())).thenReturn(employeeDtos);

        Department department = Department.IT;
        Currency currency = Currency.TRY;
        Role role = Role.DEVELOPER;


        List<EmployeeDto> result = employeeService.filterEmployees(department, role, currency, limit);

        assertEquals(employeeDtos, result);

        Mockito.verify(employeeRepository).findAllByStatus(status);
        Mockito.verify(modelMapper).map(employees, new TypeToken<List<EmployeeDto>>(){}.getType());
    }

    @Test
    @DisplayName("When GetEmployees Called With Different Parameters It Should Return The Requested EmployeeDto")
    public void whenGetEmployeesCalledWithDifferentParameters_itShouldReturnTheRequestedEmployeeDto() {
        Role role = Role.BUSINESS_ANALYST;
        Department department = Department.IT;
        int employeeIndex = 0;
        boolean status = true;

        List<EmployeeDto> employeeDtos = getMockEmployeesDtos();

        EmployeeDto employeeDto = employeeDtos.get(employeeIndex);
        employeeDto.getOccupation().setRole(role);

        List<Employee> employees = getMockEmployees();
        employees.get(employeeIndex).getOccupation().setRole(role);

        Employee employee = employees.get(employeeIndex);
        employee.getOccupation().setRole(role);

        List<Employee> requestedEmployees = List.of(employee);
        List<EmployeeDto> requestedEmployeeDtos = List.of(employeeDto);

        Mockito.when(employeeRepository.findAllByStatus(status)).thenReturn(requestedEmployees);
        Mockito.when(modelMapper.map(requestedEmployees, new TypeToken<List<EmployeeDto>>(){}.getType())).thenReturn(requestedEmployeeDtos);


        List<EmployeeDto> result = employeeService.filterEmployees(department, role, null, Optional.empty());

        assertEquals(result, requestedEmployeeDtos);

        Mockito.verify(employeeRepository).findAllByStatus(status);
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
        int id = 1;
        int employeeIndex = 0;
        boolean previousStatus = false;

        Employee employee = getMockEmployees().get(employeeIndex);
        employee.setStatus(previousStatus);

        Employee updatedEmployee = getMockEmployees().get(employeeIndex);
        updatedEmployee.setStatus(!previousStatus);

        Optional<Employee> optionalEmployee = Optional.of(employee);

        Mockito.when(employeeRepository.findById(id)).thenReturn(optionalEmployee);
        Mockito.when(employeeRepository.save(updatedEmployee)).thenReturn(updatedEmployee);

        boolean result = employeeService.changeStatus(id);

        assertEquals(!previousStatus, result);

        Mockito.verify(employeeRepository).findById(id);
        Mockito.verify(employeeRepository).save(employee);
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

        Mockito.when(employeeRepository.findAllByFirstNameAndLastName(firstName, lastName)).thenReturn(targetEmployees);
        Mockito.when(modelMapper.map(targetEmployees, new TypeToken<List<EmployeeDto>>(){}.getType())).thenReturn(targetEmployeeDtos);

        List<EmployeeDto> result = employeeService.searchEmployees(firstName, lastName);

        assertEquals(result, targetEmployeeDtos);

        Mockito.verify(employeeRepository).findAllByFirstNameAndLastName(firstName, lastName);
        Mockito.verify(modelMapper).map(targetEmployees, new TypeToken<List<EmployeeDto>>(){}.getType());
    }
}