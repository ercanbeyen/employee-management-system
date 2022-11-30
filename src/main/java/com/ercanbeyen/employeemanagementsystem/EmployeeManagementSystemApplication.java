package com.ercanbeyen.employeemanagementsystem;


import com.ercanbeyen.employeemanagementsystem.constants.enums.Currency;
import com.ercanbeyen.employeemanagementsystem.constants.enums.Gender;
import com.ercanbeyen.employeemanagementsystem.constants.enums.Role;
import com.ercanbeyen.employeemanagementsystem.dto.EmployeeDto;
import com.ercanbeyen.employeemanagementsystem.entity.Salary;
import com.ercanbeyen.employeemanagementsystem.service.EmployeeService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Employee API", version = "1.0", description = "Employee Management System"))
public class EmployeeManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeManagementSystemApplication.class, args);
	}

	/*
	@Bean
	CommandLineRunner run(EmployeeService employeeService) {
		return args -> {
			employeeService.createEmployee(new EmployeeDto("Ercan", "Beyen", "ercanbeyen@email.com", "1234", Role.ADMIN, "122334", "Turkey", Gender.MALE, "IT", "Developer", new Salary(0, 2D, Currency.TRY), null));

		};
	}
	*/
}
