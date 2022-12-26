package com.ercanbeyen.employeemanagementsystem.config;

import com.ercanbeyen.employeemanagementsystem.dto.CommentDto;
import com.ercanbeyen.employeemanagementsystem.dto.EmployeeDto;

import com.ercanbeyen.employeemanagementsystem.dto.PaymentDto;
import com.ercanbeyen.employeemanagementsystem.dto.TicketDto;
import com.ercanbeyen.employeemanagementsystem.entity.Comment;
import com.ercanbeyen.employeemanagementsystem.entity.Employee;

import com.ercanbeyen.employeemanagementsystem.entity.Payment;
import com.ercanbeyen.employeemanagementsystem.entity.Ticket;
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
        employeePropertyMapper.addMappings(mapper -> mapper.map(src -> src.getJobTitle().getName(), EmployeeDto::setJobTitle));
        employeePropertyMapper.addMappings(mapper -> mapper.map(src -> src.getPhoto().getName(), EmployeeDto::setPhotoFile));

        TypeMap<Ticket, TicketDto> ticketPropertyMapper = modelMapper.createTypeMap(Ticket.class, TicketDto.class);
        ticketPropertyMapper.addMappings(mapper -> mapper.map(src -> src.getRequester().getEmail(), TicketDto::setRequesterEmail));
        ticketPropertyMapper.addMappings(mapper -> mapper.map(src -> src.getAssignee().getEmail(), TicketDto::setAssigneeEmail));

        TypeMap<Payment, PaymentDto> paymentPropertyMapper = modelMapper.createTypeMap(Payment.class, PaymentDto.class);
        paymentPropertyMapper.addMappings(mapper -> mapper.map(src -> src.getEmployee().getEmail(), PaymentDto::setEmail));

        TypeMap<Comment, CommentDto> commentPropertyMapper = modelMapper.createTypeMap(Comment.class, CommentDto.class);
        commentPropertyMapper.addMappings(mapper -> mapper.map(src -> src.getEmployee().getId(), CommentDto::setEmployeeId));
        commentPropertyMapper.addMappings(mapper -> mapper.map(src -> src.getTicket().getId(), CommentDto::setTicketId));

        return modelMapper;
    }
}
