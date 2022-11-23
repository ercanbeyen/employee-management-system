package com.ercanbeyen.employeemanagementsystem.service.impl;

import com.ercanbeyen.employeemanagementsystem.dto.SalaryDto;
import com.ercanbeyen.employeemanagementsystem.dto.request.UpdateSalaryRequest;
import com.ercanbeyen.employeemanagementsystem.entity.Salary;
import com.ercanbeyen.employeemanagementsystem.exception.DataNotFound;

import com.ercanbeyen.employeemanagementsystem.repository.SalaryRepository;
import com.ercanbeyen.employeemanagementsystem.service.SalaryService;
import com.ercanbeyen.employeemanagementsystem.util.SalaryUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalaryServiceImpl implements SalaryService {
    @Autowired
    private final SalaryRepository salaryRepository;

    @Autowired
    private final ModelMapper modelMapper;

    @Override
    public Salary createSalary(Salary salary) {
        Salary newSalary = new Salary();

        newSalary.setCurrency(salary.getCurrency());
        newSalary.setAmount(salary.getAmount());
        newSalary.setLatestChangeAt(new Date());
        newSalary.setLatestChangeBy("Admin");

        return salaryRepository.save(newSalary);
    }

    @Override
    public SalaryDto getSalary(int id) {
        Salary salary = salaryRepository.findById(id).orElseThrow(
                () -> new DataNotFound("Salary with id " + id + " is not found")
        );

        return modelMapper.map(salary, SalaryDto.class);
    }

    @Override
    public List<SalaryDto> getSalaries() {
        List<Salary> salaries = salaryRepository.findAll();
        return modelMapper.map(salaries, new TypeToken<List<SalaryDto>>(){}.getType());
    }

    @Override
    public Salary updateSalary(int id, SalaryDto salaryDto) {
        Salary salaryInDb = salaryRepository.findById(id).orElseThrow(
                () -> new DataNotFound("Salary with id " + id + " is not found")
        );

        salaryInDb.setCurrency(salaryDto.getCurrency());
        salaryInDb.setAmount(salaryDto.getAmount());
        salaryInDb.setLatestChangeAt(new Date());
        salaryInDb.setLatestChangeBy("Admin");

        return salaryRepository.save(salaryInDb);
    }

    @Override
    public List<SalaryDto> updateSalaries(UpdateSalaryRequest request) {
        List<Salary> salaries = salaryRepository.findAll();

        if (request.getCurrency() != null) {
            salaries = salaries
                    .stream()
                    .filter(salary -> salary.getCurrency() == request.getCurrency())
                    .collect(Collectors.toList());
        }

        salaries.forEach(
                salary -> {
                    double newAmount = SalaryUtils.updateSalaryAccordingToPercentage(salary.getAmount(), request.getPercentage());
                    salary.setAmount(newAmount);
                });

        salaryRepository.saveAll(salaries);

        return modelMapper.map(salaries, new TypeToken<List<SalaryDto>>(){}.getType());
    }
}
