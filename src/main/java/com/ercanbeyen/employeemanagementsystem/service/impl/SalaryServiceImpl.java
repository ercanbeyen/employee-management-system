package com.ercanbeyen.employeemanagementsystem.service.impl;

import com.ercanbeyen.employeemanagementsystem.constants.messages.Messages;
import com.ercanbeyen.employeemanagementsystem.dto.SalaryDto;
import com.ercanbeyen.employeemanagementsystem.entity.Salary;
import com.ercanbeyen.employeemanagementsystem.exception.DataNotFound;

import com.ercanbeyen.employeemanagementsystem.repository.SalaryRepository;
import com.ercanbeyen.employeemanagementsystem.service.AuthenticationService;
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
    @Autowired
    private final AuthenticationService authenticationService;

    @Override
    public Salary createSalary(Salary salary) {
        Salary newSalary = new Salary();

        newSalary.setCurrency(salary.getCurrency());
        newSalary.setAmount(salary.getAmount());

        newSalary.setLatestChangeAt(new Date());
        String email = authenticationService.getEmail();
        newSalary.setLatestChangeBy(email);

        return salaryRepository.save(newSalary);
    }

    @Override
    public SalaryDto getSalary(int id) {
        Salary salary = salaryRepository
                .findById(id)
                .orElseThrow(
                        () -> new DataNotFound(String.format(Messages.NOT_FOUND, "Salary", id))
        );

        return modelMapper.map(salary, SalaryDto.class);
    }

    @Override
    public List<SalaryDto> getSalaries() {
        List<Salary> salaries = salaryRepository.findAll();
        return modelMapper.map(salaries, new TypeToken<List<SalaryDto>>(){}.getType());
    }

    @Override
    public void updateSalary(int id, SalaryDto salaryDto) {
        Salary salaryInDb = salaryRepository
                .findById(id)
                .orElseThrow(
                        () -> new DataNotFound(String.format(Messages.NOT_FOUND, "Salary", id))
                );

        salaryInDb.setCurrency(salaryDto.getCurrency());
        salaryInDb.setAmount(salaryDto.getAmount());

        salaryInDb.setLatestChangeAt(new Date());
        String email = authenticationService.getEmail();
        salaryInDb.setLatestChangeBy(email);

        salaryRepository.save(salaryInDb);
    }

    @Override
    public void updateSalaries(List<Salary> salaries, double percentage) {
        /* Update the salaries */
        salaries.forEach(
                salary -> {
                    double newAmount = SalaryUtils.updateSalaryAccordingToPercentage(salary.getAmount(), percentage);
                    salary.setAmount(newAmount);
                }
        );

        salaryRepository.saveAll(salaries);
    }

    @Override
    public List<Salary> getSalariesForStatistics() {
        return salaryRepository.findAll();
    }
}
