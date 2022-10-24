package com.ercanbeyen.springbootfirstrestapi.service.impl;

import com.ercanbeyen.springbootfirstrestapi.entity.Salary;
import com.ercanbeyen.springbootfirstrestapi.exception.ResourceNotFound;
import com.ercanbeyen.springbootfirstrestapi.repository.SalaryRepository;
import com.ercanbeyen.springbootfirstrestapi.service.SalaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SalaryServiceImpl implements SalaryService {
    @Autowired
    private final SalaryRepository salaryRepository;


    @Override
    public Salary createSalary(Salary salary) { // Look at one-to-one relation
        Salary newSalary = new Salary();

        newSalary.setCurrency(salary.getCurrency());
        newSalary.setAmount(salary.getAmount());
        newSalary.setLatestChangeAt(new Date());
        newSalary.setLatestChangeBy("Admin");

        return newSalary;
    }

    @Override
    public Salary getSalary(int id) {
        return salaryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("Salary with id " + id + " is not found")
        );
    }

    @Override
    public List<Salary> getSalaries() {
        return salaryRepository.findAll();
    }

    @Override
    public Salary updateSalary(int id, Salary salary) {
        Salary salaryInDb = salaryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("Salary with id " + id + " is not found")
        );

        salaryInDb.setCurrency(salary.getCurrency());
        salaryInDb.setAmount(salary.getAmount());
        salaryInDb.setLatestChangeAt(new Date());
        salaryInDb.setLatestChangeBy("Admin");

        return salaryRepository.save(salaryInDb);
    }
}
