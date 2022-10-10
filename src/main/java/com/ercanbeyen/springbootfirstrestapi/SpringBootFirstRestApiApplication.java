package com.ercanbeyen.springbootfirstrestapi;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Employee API", version = "1.0", description = "Employee Information"))
public class SpringBootFirstRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootFirstRestApiApplication.class, args);
	}

}
