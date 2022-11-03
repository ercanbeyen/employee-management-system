package com.ercanbeyen.employeemanagementsystem.config;

import com.ercanbeyen.employeemanagementsystem.dto.EmployeeDto;

import com.ercanbeyen.employeemanagementsystem.entity.Employee;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean(name = "myEntityMapper")
    public ModelMapper modelMapper() {
        return configureModelMapper();
    }

    public ModelMapper configureModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        TypeMap<Employee, EmployeeDto> employeePropertyMapper = modelMapper.createTypeMap(Employee.class, EmployeeDto.class);
        employeePropertyMapper.addMappings(mapper -> mapper.map(src -> src.getDepartment().getName(), EmployeeDto::setDepartment));
        employeePropertyMapper.addMappings(mapper -> mapper.map(src -> src.getRole().getName(), EmployeeDto::setRole));
        employeePropertyMapper.addMappings(mapper -> mapper.map(src -> src.getPhoto().getName(), EmployeeDto::setPhotoFile));

        return modelMapper;
    }
}
