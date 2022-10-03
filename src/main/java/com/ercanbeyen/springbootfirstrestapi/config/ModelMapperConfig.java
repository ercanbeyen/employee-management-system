package com.ercanbeyen.springbootfirstrestapi.config;

import com.ercanbeyen.springbootfirstrestapi.dto.UserDto;
import com.ercanbeyen.springbootfirstrestapi.entity.User;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
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
        //modelMapper.getConfiguration().setFieldMatchingEnabled(true);
        //modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        //modelMapper.getConfiguration().setSkipNullEnabled(true); // Tried without this as well
        //modelMapper().typeMap(User.class, UserDto.class);
        return modelMapper;
    }
}
