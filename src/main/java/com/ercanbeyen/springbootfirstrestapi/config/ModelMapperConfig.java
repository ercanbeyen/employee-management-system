package com.ercanbeyen.springbootfirstrestapi.config;

import com.ercanbeyen.springbootfirstrestapi.dto.EmployeeDto;
import com.ercanbeyen.springbootfirstrestapi.dto.RoleDto;
import com.ercanbeyen.springbootfirstrestapi.entity.Department;
import com.ercanbeyen.springbootfirstrestapi.entity.Employee;
import com.ercanbeyen.springbootfirstrestapi.entity.Role;
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

        //typeMap.addMappings(mapper -> mapper.map(src -> src.getCustomer().getAge(), PersonDTO::setAge));

        //modelMapper.typeMap(Employee.class, EmployeeDto.class).addMapping(Employee::getDepartment::getName, EmployeeDto::setDepartment);
        //modelMapper.typeMap(Role.class, EmployeeDto.class).addMapping(Role::getName, EmployeeDto::setRole);

        TypeMap<Employee, EmployeeDto> employeePropertyMapper = modelMapper.createTypeMap(Employee.class, EmployeeDto.class);
        employeePropertyMapper.addMappings(mapper -> mapper.map(src -> src.getDepartment().getName(), EmployeeDto::setDepartment));
        employeePropertyMapper.addMappings(mapper -> mapper.map(src -> src.getRole().getName(), EmployeeDto::setRole));

        /*
        TypeMap<Role, RoleDto> rolePropertyMapper = modelMapper.createTypeMap(Role.class, RoleDto.class);
        rolePropertyMapper.addMappings(mapper -> mapper.map(src -> src.getEmployees().forEach(role -> {
            role
        })))
         */


        return modelMapper;
    }
}
